package com.banco;

public class Cliente {

    private String nombre;
    private double saldo;

    public Cliente(String nombre, double saldo) {
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /** Consulta del saldo. */
    public double consultarSaldo() {
        return getSaldo();
    }

    /** Consignar: aumenta el saldo. */
    public void consignar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor a consignar debe ser mayor que cero");
        }
        setSaldo(getSaldo() + valor);
    }

    /** Retirar: disminuye el saldo. */
    public void retirar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El valor a retirar debe ser mayor que cero");
        }
        if (valor > getSaldo()) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        setSaldo(getSaldo() - valor);
    }
}
