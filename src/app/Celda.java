package app;

import javax.swing.JButton;

public class Celda extends JButton {
    private static int nBombasTablero=0; //Cantidad de bombas en el tablero
    private static int nCeldasLimpiadas=0; //El juego termina cuando la suma (nBombasTablero + nCeldasLimpiadas) 
    private int filaY=0, columnaX=0;                  // es igual a la cantidad de celdas en el tablero
    private boolean hayBomba = false; //Verdadero si hay una bomba en la celda
    private boolean estaDespejado = false; //Verdadero si la celda esta despejada
    private int nBombasAlrededor=0; //Si no hay una bomba contendra la cantidad de bombas al rededor
    private boolean protejida = false; //protejida por la bandera o ? 
    public Celda(int f, int c) {
        super(); 
        this.filaY=f; 
        this.columnaX=c; 
        setBomba();
    }

    public boolean isHayBomba() {
        return hayBomba;
    }

    public void setBomba() {
        int x = (int)Math.floor(Math.random()*6+1);
        this.setText("");        
        this.hayBomba=false;
        if(x==3) {
            //this.setText("");
            this.hayBomba = true;
            nBombasAlrededor=0;
            this.nBombasTablero++;
        }
    }

    public static int getNBombasTablero() {
        return nBombasTablero;
    }

    public static void setNBombasTablero(int nBombas) {
        Celda.nBombasTablero = nBombas;
    }

    public int getFilaY() {
        return filaY;
    }

    public int getColumnaX() {
        return columnaX;
    }

    public void setEstaDespejado(boolean despejado) {
        this.estaDespejado = despejado;
    }

    public boolean isEstaDespejado() {
        return estaDespejado;
    }

 
    public int getNBombasAlrededor() {
        return nBombasAlrededor;
    }

    public void setNBombasAlrededor(int nBombasAlrededor) {
        this.nBombasAlrededor = nBombasAlrededor;
    }

    public static void setNCeldasLimpiadas(int nCeldasLimpiadas) {
        Celda.nCeldasLimpiadas = nCeldasLimpiadas;
    }

    public static int getNCeldasLimpiadas() {
        return nCeldasLimpiadas;
    }

    public void setProtejida(boolean protejida) {
        this.protejida = protejida;
    }

    public boolean isProtejida() {
        return protejida;
    }
}
