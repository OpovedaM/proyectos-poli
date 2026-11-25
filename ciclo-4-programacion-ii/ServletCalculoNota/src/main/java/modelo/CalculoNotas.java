/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

// Archivo creado por: Omar Poveda  - Modelo JavaBean

package modelo;

import java.io.Serializable;

public class CalculoNotas implements Serializable {

    private double corte1;
    private double corte2;
    private double corte3;
    private double definitiva;

    public CalculoNotas() {
    }

    public double getCorte1() {
        return corte1;
    }

    public void setCorte1(double corte1) {
        this.corte1 = corte1;
    }

    public double getCorte2() {
        return corte2;
    }

    public void setCorte2(double corte2) {
        this.corte2 = corte2;
    }

    public double getCorte3() {
        return corte3;
    }

    public void setCorte3(double corte3) {
        this.corte3 = corte3;
    }

    public double getDefinitiva() {
        return definitiva;
    }

    public void calcularNotaDefinitiva() {
        this.definitiva = (corte1 * 0.35) + (corte2 * 0.35) + (corte3 * 0.30);
    }
}



    
