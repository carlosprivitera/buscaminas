package datos;

import java.awt.Dimension;

import javax.swing.JButton;

public class Celda extends JButton {
    private int filaY=0, columnaX=0;    
    private boolean hayMina = false; //Verdadero si hay una bomba en la celda
    private boolean estaDespejado = false; //Verdadero si la celda está despejada
    private int nMinasAlrededor=0; //Si no hay una bomba contendra la cantidad de bombas alrededor
    private boolean protejida = false; //protejida por la bandera o ? 
    public Celda(int f, int c) {
        super(); 
        this.filaY=f; 
        this.columnaX=c; 
        ponerMina();
    }

    public Celda() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isHayMina() {
        return hayMina;
    }

    public void ponerMina() {
        int x = (int)Math.floor(Math.random()*7+1); //Generador de números aleatorios
        setText("");        
        hayMina=false;
        if(x==4) {  //Si el número aleatorio es adivinado, poner una mina
            hayMina = true;
            nMinasAlrededor=0;
        }
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

 
    public int getNMinasAlrededor() {
        return nMinasAlrededor;
    }

    public void setNMinasAlrededor(int nBombasAlrededor) {
        this.nMinasAlrededor = nBombasAlrededor;
    }

    public void setProtejida(boolean protejida) {
        this.protejida = protejida;
    }

    public boolean isProtejida() {
        return protejida;
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(525, 445));
    }
}
