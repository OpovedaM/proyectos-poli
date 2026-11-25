/* author: Estudiante
 * version: 1.0
 */
package com.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BancoTest {

    @Test
    @DisplayName("Test1 -> crearCliente agrega el cliente al banco.")
    public void test1() {
        // AL PREPARAR (Arrange)
        Banco banco = new Banco();
        // AL EJECUTAR (Act)
        Cliente cliente = banco.crearCliente("Ana", 1000);
        // AL COMPROBAR (Assert)
        assertEquals(1, banco.getClientes().size());
        assertEquals("Ana", cliente.getNombre());
    }

    @Test
    @DisplayName("Test2 -> la caja total suma el saldo de todos los clientes.")
    public void test2() {
        Banco banco = new Banco();
        banco.crearCliente("Ana", 1000);
        banco.crearCliente("Luis", 2500);
        double cajaEsperada = 3500;
        // AL EJECUTAR
        double cajaReal = banco.consultarCajaTotal();
        // AL COMPROBAR
        assertEquals(cajaEsperada, cajaReal);
    }

    @Test
    @DisplayName("Test3 -> la caja total cambia con ingresos y egresos.")
    public void test3() {
        Banco banco = new Banco();
        Cliente ana = banco.crearCliente("Ana", 1000);
        double cajaEsperada = 1300;
        // AL EJECUTAR
        banco.consignar(ana, 500);
        banco.retirar(ana, 200);
        // AL COMPROBAR
        assertEquals(cajaEsperada, banco.consultarCajaTotal());
    }

    @Test
    @DisplayName("Test4 -> consultarOperaciones registra ingresos y egresos.")
    public void test4() {
        Banco banco = new Banco();
        Cliente ana = banco.crearCliente("Ana", 1000);
        // AL EJECUTAR
        banco.consignar(ana, 500);
        banco.retirar(ana, 200);
        // AL COMPROBAR
        assertEquals(2, banco.consultarOperaciones().size());
        assertTrue(banco.consultarOperaciones().get(0).startsWith("INGRESO"));
        assertTrue(banco.consultarOperaciones().get(1).startsWith("EGRESO"));
    }
}
