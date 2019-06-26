package juego;

import datos.Celda;

import datos.DatosCompartidos;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Tablero implements DatosCompartidos {
    private int p[] = {0,-1, 1,-1, 1,0, 1,1, 0,1, -1,1, -1,0, -1,-1};
    private int nBombasTablero=0; //Cantidad de bombas en el tablero
    private int nCeldasLimpiadas=0; //El juego termina cuando: nBombasTablero + nCeldasLimpiadas = FILAS * COLUMNAS
    private int FILAS = FILAS_Y;
    private int COLUMNAS = this.COLUMNAS_X;
    private Celda celda[][] = new Celda[FILAS][COLUMNAS];
    private JPanel jPanel1 = new JPanel();
    private JTextArea jTextArea1 = new JTextArea();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();    
    public Tablero(JPanel jPanel1, JTextArea jTextArea1, JLabel jLabel1, JLabel jLabel2) {
        super();
        this.jPanel1=jPanel1;
        this.jTextArea1=jTextArea1;
        this.jLabel1=jLabel1;
        this.jLabel2=jLabel2;
    }
    public void nuevoJuego() {
        nBombasTablero=0; 
        nCeldasLimpiadas=0;
        jTextArea1.setText("");
        jLabel1.setText("");
        jLabel2.setText("");
        iniciarTablero();
        jLabel1.setText("Cantidad de minas = " + nBombasTablero);
    }    
    public void crearTablero(){
        for(int f=0; f<FILAS; f++) {
            for(int c=0; c<COLUMNAS; c++) {
                celda[f][c] = new Celda(f,c);
                celda[f][c].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            celda_actionPerformed(e);
                        }
                    });
                celda[f][c].addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            jButton1_mouseClicked(e);
                        }
                    });
                celda[f][c].setMargin(new Insets(1, 1, 1, 1));
                celda[f][c].setToolTipText(""+f+":"+c);
                jPanel1.add(celda[f][c], null);
            }   
        }
    }
    public void iniciarTablero() {
        for(int f=0; f<FILAS; f++) {
            for(int c=0; c<COLUMNAS; c++) {
                celda[f][c].setNMinasAlrededor(0);
                celda[f][c].setEstaDespejado(false);
                celda[f][c].setProtejida(false);
                celda[f][c].ponerMina();
                if(celda[f][c].isHayMina()==true){
                    nBombasTablero=nBombasTablero+1;    
                }
                celda[f][c].setBackground(Color.LIGHT_GRAY);
            }   
        }
        for(int f=0; f<FILAS; f++) { //Pone en cada celda la cantidad de minas alrededor
            for(int c=0; c<COLUMNAS; c++) {
              if(celda[f][c].isHayMina()== false) {//Si no es mina, calcular las minas alrededor
                nBombasAlRededor(f, c);
              }  
            }   
        }
    }
    private void nBombasAlRededor(int y, int x) {
        int nBombas=0;
        for(int i=0; i<16; i=i+2) {
              try{
                if(celda[y+p[i]][x+p[i+1]].isHayMina()==true) nBombas++; 
              }catch(Exception e01){ }
        }
        celda[y][x].setNMinasAlrededor(nBombas);

    }    

    private void jButton1_mouseClicked(MouseEvent e) {
        Celda celdaXY = (Celda)e.getSource(); //Obtener el objeto sobre el que se hizo click
        if(celdaXY.isEstaDespejado()==false) {
          if(e.getButton() == e.BUTTON3) {
              if(celdaXY.getText().equals("")){
                  celdaXY.setText("X");
                  celdaXY.setProtejida(true);
              }else{
                  if(celdaXY.getText().equals("X")){
                      celdaXY.setText("?");
                      celdaXY.setProtejida(true);
                  }else{
                      celdaXY.setText("");
                      celdaXY.setProtejida(false);
                  }
              }
          }
       }  
    }    
    private void celda_actionPerformed(ActionEvent e) {
        informe = "Recorrido de la función recursiva:" + "\n";
        Celda celdaXY = (Celda)e.getSource(); //Obtener el objeto sobre el que se hizo click/Tecla
        int y = celdaXY.getFilaY();
        int x = celdaXY.getColumnaX();
        if(celdaXY.isProtejida()==false) {
          if(celdaXY.isHayMina()==true) { //Fin del juego
            mostrarMinas();
            JOptionPane.showMessageDialog(null, "Había una mina en " + y + ":" + x);
            nuevoJuego();
          }else { //Bien! continuar con el juego
            if(celdaXY.getNMinasAlrededor()>0){ //Pregunta se la celdaXY tiene minas alrededor                                              
              if(celdaXY.isEstaDespejado()==false) {
                celdaXY.setText(""+celdaXY.getNMinasAlrededor());
                celdaXY.setBackground(Color.ORANGE);
                celdaXY.setEstaDespejado(true);
                nCeldasLimpiadas = nCeldasLimpiadas + 1;
                this.jLabel2.setText(" Terreno limpiado = " + nCeldasLimpiadas);
                if((nBombasTablero + nCeldasLimpiadas) == (FILAS * COLUMNAS) ) {
                  JOptionPane.showMessageDialog(null, "Bien! ha ganado...");
                  nuevoJuego();
                }  
              }else{
                  JOptionPane.showMessageDialog(null, "La celda con minas alrededor ya está despejada");  
              }
            }else { //Si no tiene minas alrededor, limpiar el tablero recursivamente 
                    //  todas las celdasXY contiguas que no tengas minas alrededor
                if(celdaXY.isEstaDespejado()==false) {
                  limpiarTerrenoRecursiva(y, x, 0); //limpiarRecursiva(y,x) es llamada si no tiene minas alrededor
                  jTextArea1.setText(informe);
                  this.jLabel2.setText(" Terreno limpiado = " + nCeldasLimpiadas);
                  if((nBombasTablero + nCeldasLimpiadas) == (FILAS * COLUMNAS) ) {
                    JOptionPane.showMessageDialog(null, "Bien! ha ganado...");
                    nuevoJuego();
                  }  
                }else{
                  JOptionPane.showMessageDialog(null, "La celda sin minas alrededor ya está despejada");
                }
            }       
         }
      }    
    }
    private void mostrarMinas() {
        for(int f=0; f<FILAS; f++) {
            for(int c=0; c<COLUMNAS; c++) {
                if(celda[f][c].isHayMina()==true) {
                    celda[f][c].setText("B"); 
                }
            }   
        }
    } 
    private String informe = ""; 
    private void limpiarTerrenoRecursiva(int y, int x, int nivelX) { //limpiarTerrenoRecursiva(y,x) es llamada/rellamada si no tiene minas alrededor
        int miNivel = nivelX + 1; 
        String s=""; 
        for(int z=0; z<miNivel; z++){ s = s + "_"; }
        if(celda[y+0][x-0].isEstaDespejado()==false) { // ????? Cualquier tarea se hace sobre una celda no despejada
          //try{
          informe = informe.concat(s + "*Entrando al terreno N° " + nivelX + " en: " + y + ":" + x + "\n");  
          celda[y+0][x-0].setEstaDespejado(true);  
          celda[y+0][x-0].setBackground(Color.GREEN);
          nCeldasLimpiadas = nCeldasLimpiadas + 1;
            //}catch(Exception e00){ }
            if(celda[y+0][x-0].getNMinasAlrededor()==0) { //???? es garantia que no hay minas alrededor
              for(int i=0; i<16; i=i+2) {
                  try{ //Prueba que celda[y][x] no se salga del tablero
                      if(celda[y+p[i]][x+p[i+1]].isEstaDespejado()==false) {  
                        if(celda[y+p[i]][x+p[i+1]].getNMinasAlrededor()==0){  //Pregunta si no tiene minas alrededor
                          informe = informe.concat(s +"__Despejando en: " + (y+p[i]) + ":" + (x+p[i+1]) + "\n");
                          limpiarTerrenoRecursiva(y+p[i],x+p[i+1], miNivel); //limpiarRecursiva(y,x) es llamada si no tiene minas alrededor
                        }else{ //Si tiene se indica la cantidad de minas alrededor
                            informe = informe.concat(s + "__FIN en " + (y+p[i]) + ":" + (x+p[i+1]) + 
                                                    " con " + celda[y+p[i]][x+p[i+1]].getNMinasAlrededor() + " mina/s encontrada/s" + "\n");
                            if(celda[y+p[i]][x+p[i+1]].isEstaDespejado()==false) { //Pregunta si no fué despejada anteriormente  
                              celda[y+p[i]][x+p[i+1]].setText(""+celda[y+p[i]][x+p[i+1]].getNMinasAlrededor());
                              celda[y+p[i]][x+p[i+1]].setBackground(Color.ORANGE);
                              celda[y+p[i]][x+p[i+1]].setEstaDespejado(true);
                              nCeldasLimpiadas = nCeldasLimpiadas + 1;
                            }  
                        }
                      }else {
                        informe = informe.concat(s + "__Ya visitado: " + (y+p[i]) + ":" + (x+p[i+1]) + "\n");    
                      }
                  }catch(Exception e01){ //Captura cuando se sale del tablero y retorna a la celda[][] anterior 
                                         // de la pila de llamas de la función limpiarTerrenoRecursiva(int y, int x)
                     informe = informe.concat(s + "__Fuera del tablero en: " + y + ":" + x + "\n");
                  }
              }
            }else{
              informe = informe.concat("Error en función recursiva!! " + y + ":" + x + "\n");  
            }
           informe = informe.concat(s + "*Saliendo del terreno N° " + nivelX + " en: " + y + ":" + x + "\n");  
        }else{
          informe = informe.concat("Error en función recursiva!! " + y + ":" + x + "\n");
        }
    }
    public void mostrarTableroNocturno() {
        for(int f=0; f<FILAS; f++) {
            for(int c=0; c<COLUMNAS; c++) {
                if(celda[f][c].isHayMina()==true) {
                       celda[f][c].setText("B");
                       celda[f][c].setBackground(Color.DARK_GRAY);
                }else {
                    celda[f][c].setText(""+celda[f][c].getNMinasAlrededor());
                    if(celda[f][c].getNMinasAlrededor()>0) {
                        celda[f][c].setBackground(Color.GRAY);
                    }else{
                        celda[f][c].setBackground(Color.LIGHT_GRAY); 
                    }
                }
            }   
        }
    }    
}
