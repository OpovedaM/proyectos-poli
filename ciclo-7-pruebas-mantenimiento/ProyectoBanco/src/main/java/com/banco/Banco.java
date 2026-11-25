package com.banco;

import java.util.ArrayList;
import java.util.List;

public class Banco {

    private final List<Cliente> clientes = new ArrayList<>();
    private final List<String> operaciones = new ArrayList<>();

    /** Crea un cliente y lo registra en el banco. */
    public Cliente crearCliente(String nombre, double saldoInicial) {
        Cliente cliente = new Cliente(nombre, saldoInicial);
        clientes.add(cliente);
        return cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    /** Consigna a un cliente y registra el ingreso. */
    public void consignar(Cliente cliente, double valor) {
        cliente.consignar(valor);
        operaciones.add("INGRESO | " + cliente.getNombre() + " | " + valor);
    }

    /** Retira a un cliente y registra el egreso. */
    public void retirar(Cliente cliente, double valor) {
        cliente.retirar(valor);
        operaciones.add("EGRESO  | " + cliente.getNombre() + " | " + valor);
    }

    /** Caja total: suma del saldo de todos los clientes. */
    public double consultarCajaTotal() {
        double total = 0;
        for (Cliente c : clientes) {
            total += c.getSaldo();
        }
        return total;
    }

    /** Operaciones de ingreso y egreso realizadas. */
    public List<String> consultarOperaciones() {
        return operaciones;
    }
}
