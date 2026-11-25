/* author: Estudiante
 * version: 1.0
 */
package com.banco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    @Test // Identifica el método como una prueba
    @DisplayName("Test1 -> consultarSaldo devuelve el saldo inicial.")
    public void test1() {
        // AL PREPARAR (Arrange)
        Cliente cliente = new Cliente("Ana", 1000);
        double saldoEsperado = 1000;
        // AL EJECUTAR (Act)
        double saldoReal = cliente.consultarSaldo();
        // AL COMPROBAR (Assert)
        assertEquals(saldoEsperado, saldoReal);
    }

    @Test
    @DisplayName("Test2 -> consignar aumenta el saldo.")
    public void test2() {
        Cliente cliente = new Cliente("Ana", 1000);
        double saldoEsperado = 1500;
        // AL EJECUTAR
        cliente.consignar(500);
        // AL COMPROBAR
        assertEquals(saldoEsperado, cliente.consultarSaldo());
    }

    @Test
    @DisplayName("Test3 -> retirar disminuye el saldo.")
    public void test3() {
        Cliente cliente = new Cliente("Ana", 1000);
        double saldoEsperado = 600;
        // AL EJECUTAR
        cliente.retirar(400);
        // AL COMPROBAR
        assertEquals(saldoEsperado, cliente.consultarSaldo());
    }

    @Test
    @DisplayName("Test4 -> retirar más del saldo lanza excepción.")
    public void test4() {
        Cliente cliente = new Cliente("Ana", 1000);
        // AL EJECUTAR y COMPROBAR
        assertThrows(IllegalArgumentException.class, () -> cliente.retirar(5000));
    }

    @Test
    @DisplayName("Test5 -> getters y setters de nombre y saldo.")
    public void test5() {
        Cliente cliente = new Cliente("Ana", 1000);
        // AL EJECUTAR
        cliente.setNombre("Luis");
        cliente.setSaldo(2000);
        // AL COMPROBAR
        assertEquals("Luis", cliente.getNombre());
        assertEquals(2000, cliente.getSaldo());
    }
}
