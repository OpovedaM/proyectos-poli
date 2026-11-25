package com.banco;

public class Main {

    public static void main(String[] args) {
        Banco banco = new Banco();
        Cliente ana = banco.crearCliente("Ana", 100000);
        Cliente luis = banco.crearCliente("Luis", 50000);

        banco.consignar(ana, 25000);
        banco.retirar(luis, 20000);

        System.out.println("Saldo Ana: " + ana.consultarSaldo());
        System.out.println("Saldo Luis: " + luis.consultarSaldo());
        System.out.println("Caja total: " + banco.consultarCajaTotal());
        banco.consultarOperaciones().forEach(System.out::println);
    }
}
