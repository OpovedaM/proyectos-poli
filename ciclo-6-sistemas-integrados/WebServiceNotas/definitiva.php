<?php
// ===========================================================================
// PROYECTO CONSTRUIDO POR:
//   Soleny Guarin Quintero
//   Jeff Alan Paladines Ordonez
//   Omar Armando Poveda Mahecha
//
// Caso de Estudio Integrador - Sistemas Integrados
// Politecnico Internacional - Tecnologia en Desarrollo de Software y
// Aplicativos Moviles. Docente: Leonel Andres Lopez Arias. 2026.
//
// definitiva.php - Web Service RESTful que calcula la nota definitiva.
// ===========================================================================

// ---------------------------------------------------------------------------
// 1) CONFIGURACION DE LA CABECERA HTTP
//    Le decimos al navegador/cliente que la respuesta es JSON en UTF-8.
// ---------------------------------------------------------------------------
header('Content-Type: application/json; charset=utf-8');
header('Access-Control-Allow-Origin: *');

// Algunas instalaciones (por ejemplo XAMPP) traen configurado un
// 'serialize_precision' alto, y json_encode imprimiria 4.2 como
// 4.2000000000000001776356839400250464677810668945312. Con el valor -1 PHP
// usa la representacion mas corta y exacta, asi el JSON se ve igual en
// cualquier servidor donde se despliegue la solucion.
ini_set('serialize_precision', '-1');

// ---------------------------------------------------------------------------
// 2) PARAMETROS DE NEGOCIO 
//
//    NOTA DE DISENO: el enunciado del caso NO especifica la ponderacion de
//    la nota definitiva, por lo que se asume 30% + 30% + 40% (esquema usual
//    de tres cortes). Al declararla como constantes, cambiar la ponderacion
//    solo requiere modificar estos tres valores, sin tocar la logica.
// ---------------------------------------------------------------------------
const PESO_NOTA1   = 0.30;
const PESO_NOTA2   = 0.30;
const PESO_NOTA3   = 0.40;

const NOTA_MINIMA   = 0.0; // Escala de notas del Politecnico (minimo)
const NOTA_MAXIMA   = 5.0; // Escala de notas del Politecnico (maximo)
const NOTA_APRUEBA  = 3.0; // Nota minima para aprobar

/**
 * Envia una respuesta JSON y termina la ejecucion.
 * Centralizar la salida evita repetir codigo y garantiza un formato uniforme.
 *
 * @param array $datos  Contenido de la respuesta.
 * @param int   $codigo Codigo de estado HTTP (200 correcto, 400 error de cliente).
 */
function responder(array $datos, int $codigo = 200): void
{
    http_response_code($codigo);
    // JSON_UNESCAPED_UNICODE conserva tildes y ñ; PRETTY_PRINT lo hace legible.
    echo json_encode($datos, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT);
    exit;
}

/**
 * Valida y convierte una nota recibida por parametro.
 * Devuelve un arreglo: [ 'ok' => bool, 'valor' => float|null, 'error' => string|null ]
 *
 * Reglas de robustez que aplica:
 *   - El parametro debe existir.
 *   - No puede venir vacio.
 *   - Debe ser numerico (rechaza letras / texto).
 *   - Debe estar dentro de la escala permitida (0.0 a 5.0).
 * Acepta coma o punto como separador decimal (1,3 o 1.3).
 *
 * @param string $nombre Nombre del parametro (para mensajes de error claros).
 * @param mixed  $valor  Valor crudo recibido en la URL.
 */
function validarNota(string $nombre, $valor): array
{
    // (a) ¿El parametro fue enviado?
    if ($valor === null) {
        return ['ok' => false, 'valor' => null, 'error' => "Falta el parametro '$nombre'."];
    }

    // (b) ¿Llego como arreglo? Ocurre si la URL trae "?nota1[]=2".
    //     Hay que descartarlo ANTES de convertir a texto, porque convertir un
    //     arreglo a string genera un warning de PHP que dañaria el JSON.
    if (is_array($valor)) {
        return ['ok' => false, 'valor' => null,
                'error' => "El parametro '$nombre' debe ser un unico valor numerico."];
    }

    // (c) ¿Viene vacio? (quitamos espacios en blanco antes de evaluar)
    $valor = trim((string)$valor);
    if ($valor === '') {
        return ['ok' => false, 'valor' => null, 'error' => "El parametro '$nombre' esta vacio."];
    }

    // (d) Normalizamos: aceptamos coma decimal (formato Colombia) o punto.
    $normalizado = str_replace(',', '.', $valor);

    // (e) ¿Es realmente un numero? is_numeric rechaza letras y textos como "abc".
    if (!is_numeric($normalizado)) {
        return ['ok' => false, 'valor' => null,
                "error" => "El parametro '$nombre' debe ser un numero (se recibio: '$valor')."];
    }

    $nota = (float)$normalizado;

    // (f) ¿Esta dentro de la escala 0.0 - 5.0?
    if ($nota < NOTA_MINIMA || $nota > NOTA_MAXIMA) {
        $rango = NOTA_MINIMA . " y " . NOTA_MAXIMA;
        return ['ok' => false, 'valor' => null,
                'error' => "El parametro '$nombre' debe estar entre $rango (se recibio: $nota)."];
    }

    return ['ok' => true, 'valor' => $nota, 'error' => null];
}

// ---------------------------------------------------------------------------
// 3) FLUJO PRINCIPAL
// ---------------------------------------------------------------------------

// (Opcional) Solo aceptamos peticiones GET, que es como las envia el navegador.
if ($_SERVER['REQUEST_METHOD'] !== 'GET') {
    responder([
        'estado'  => 'error',
        'mensaje' => 'Metodo no permitido. Use GET.',
    ], 405);
}

// Leemos los tres parametros esperados. El operador ?? evita "warnings" si no vienen.
$entrada = [
    'nota1' => $_GET['nota1'] ?? null,
    'nota2' => $_GET['nota2'] ?? null,
    'nota3' => $_GET['nota3'] ?? null,
];

// Validamos las tres notas y ACUMULAMOS todos los errores encontrados,
// asi el cliente recibe de una vez todo lo que debe corregir.
$notas   = [];
$errores = [];

foreach ($entrada as $nombre => $crudo) {
    $r = validarNota($nombre, $crudo);
    if ($r['ok']) {
        $notas[$nombre] = $r['valor'];
    } else {
        $errores[] = $r['error'];
    }
}

// Si hubo cualquier error de validacion, respondemos con detalle y codigo 400.
if (!empty($errores)) {
    responder([
        'estado'   => 'error',
        'definitiva' => null,
        'mensaje'  => 'No fue posible calcular la nota definitiva. Revise los datos enviados.',
        'errores'  => $errores,
        'ejemplo'  => 'definitiva.php?nota1=1.3&nota2=1.5&nota3=4.9',
    ], 400);
}

// ---------------------------------------------------------------------------
// 4) CALCULO DE LA NOTA DEFINITIVA (ponderado configurable)
// ---------------------------------------------------------------------------
$definitiva = ($notas['nota1'] * PESO_NOTA1)
            + ($notas['nota2'] * PESO_NOTA2)
            + ($notas['nota3'] * PESO_NOTA3);

$definitiva = round($definitiva, 2); // Redondeo a 2 decimales

// Formato con coma decimal (estilo colombiano), como en el ejemplo del enunciado.
$definitivaTexto = number_format($definitiva, 2, ',', '');

// ---------------------------------------------------------------------------
// 5) RESPUESTA EXITOSA
// ---------------------------------------------------------------------------
responder([
    'estado'      => 'correcto',
    'definitiva'  => $definitivaTexto,      // "3,17"  (texto, como el ejemplo)
    'definitiva_num' => $definitiva,        // 3.17    (numero, util para otros clientes)
    'aprobo'      => $definitiva >= NOTA_APRUEBA,
    'mensaje'     => 'Calculo realizado correctamente.',
    'detalle'     => [
        'nota1' => $notas['nota1'],
        'nota2' => $notas['nota2'],
        'nota3' => $notas['nota3'],
        'ponderacion' => '30% / 30% / 40%',
        'escala'      => NOTA_MINIMA . ' a ' . NOTA_MAXIMA,
    ],
], 200);
