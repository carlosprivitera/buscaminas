package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.io.IOException;

import java.net.URI;

import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class VentaPrincipal extends JFrame {
    private ImageIcon ImageIcon = new ImageIcon(getClass().getResource("mina.png"));
    private Image imagen01 =  ImageIcon.getImage();
    private int FILAS = 20;
    private int COLUMNAS = 20;
    private JPanel jPanel1 = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private Celda celda[][] = new Celda[FILAS][COLUMNAS];
    private JToolBar jToolBar1 = new JToolBar();
    private JButton jButton1 = new JButton();
    private JLabel jLabel1 = new JLabel();
    private JLabel jLabel2 = new JLabel();
    private JSeparator jSeparator1 = new JSeparator();
    private JSplitPane jSplitPane1 = new JSplitPane();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JTextArea jTextArea1 = new JTextArea();
    private BorderLayout borderLayout1 = new BorderLayout();
    private JButton jButton2 = new JButton();
    private JButton jButton3 = new JButton();

    public VentaPrincipal() {
        try {
            jbInit();
            mi_jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout(borderLayout1);
        this.setSize( new Dimension(800, 600) );
        this.setTitle("Busca minas para profesores y estudiantes");
        this.setIconImage(imagen01);
        this.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    this_componentResized(e);
                }
            });
        jPanel1.setLayout(gridLayout1);
        gridLayout1.setColumns(COLUMNAS);
        gridLayout1.setRows(FILAS);
        jButton1.setText("Nuevo Juego");
        jButton1.setOpaque(true);
        jButton1.setToolTipText("Nuevo juego tradicional");
        jButton1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton1_actionPerformed(e);
                }
            });
        jLabel1.setText("jLabel1");
        jLabel2.setText("jLabel2");
        jSeparator1.setOrientation(SwingConstants.VERTICAL);
        jSeparator1.setSize(new Dimension(4, 20));
        jSeparator1.setBounds(new Rectangle(104, 1, 4, 20));
        jSplitPane1.setDividerLocation(300);
        jTextArea1.setFont(new Font("Courier New", 0, 12));
        jButton2.setText("Modo nocturno");
        jButton2.setToolTipText("Nuevo juego en modo nocturno, investigar");
        jButton2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton2_actionPerformed(e);
                }
            });
        jButton3.setText("GitHub");
        jButton3.setToolTipText("Código fuente");
        jButton3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jButton3_actionPerformed(e);
                }
            });
        jSplitPane1.add(jPanel1, JSplitPane.LEFT);
        jScrollPane1.getViewport().add(jTextArea1, BorderLayout.SOUTH);
        jSplitPane1.add(jScrollPane1, JSplitPane.RIGHT);
        this.getContentPane().add(jSplitPane1, BorderLayout.CENTER);
        jToolBar1.add(jButton1, null);
        jToolBar1.add(jButton2, null);
        jToolBar1.add(jButton3, null);
        jToolBar1.add(jLabel1, null);
        jToolBar1.add(jSeparator1, null);
        jToolBar1.add(jLabel2, null);
        this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
    }
    private void mi_jbInit() {
        ////////////////////////////////////////////
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
        nuevoJuego();
        /////////////////////////////////////////////
    }
    private void nuevoJuego() {
        //miNivel=0;
        Celda.setNBombasTablero(0);
        Celda.setNCeldasLimpiadas(0);
        this.jTextArea1.setText("");
        this.jLabel1.setText("");
        this.jLabel2.setText("");
        for(int f=0; f<FILAS; f++) {
            for(int c=0; c<COLUMNAS; c++) {
                celda[f][c].setNBombasAlrededor(0);
                celda[f][c].setEstaDespejado(false);
                celda[f][c].setProtejida(false);
                celda[f][c].setBomba();
                celda[f][c].setBackground(Color.LIGHT_GRAY);
            }   
        }
        this.jLabel1.setText("Cantidad de minas = " + Celda.getNBombasTablero());
        for(int f=0; f<FILAS; f++) { //Pone en cada celda la cantidad de minas al rededor
            for(int c=0; c<COLUMNAS; c++) {
              if(celda[f][c].isHayBomba()== false) {//Si no es mina, calcular las minas al rededor
                nBombasAlRededor(f, c);
              }  
            }   
        }
        
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        nuevoJuego();
    }
    private void celda_actionPerformed(ActionEvent e) {
        //miNivel=0;
        this.jTextArea1.setText("Recorrido de la función recursiva:\n");
        Celda celdaXY = (Celda)e.getSource(); //Obtener el objeto sobre el que se hizo click/Tecla
        int y = celdaXY.getFilaY();
        int x = celdaXY.getColumnaX();
        if(celdaXY.isProtejida()==false) {
          if(celdaXY.isHayBomba()==true) { //Fin del juego
            mostrarBombas();
            JOptionPane.showMessageDialog(this, "Había una mina en " + y + ":" + x);
            nuevoJuego();
          }else { //Bien! continuar con el juego
            if(celdaXY.getNBombasAlrededor()>0){ //Pregunta se la celdaXY tiene minas al rededor                                              
              if(celdaXY.isEstaDespejado()==false) {
                celdaXY.setText(""+celdaXY.getNBombasAlrededor());
                celdaXY.setBackground(Color.ORANGE);
                celdaXY.setEstaDespejado(true);
                Celda.setNCeldasLimpiadas(Celda.getNCeldasLimpiadas() + 1);
                this.jLabel2.setText(" Terreno limpiado = " + Celda.getNCeldasLimpiadas());
                if((Celda.getNBombasTablero() + Celda.getNCeldasLimpiadas()) == (FILAS * COLUMNAS) ) {
                  JOptionPane.showMessageDialog(this, "Bien! ha ganado...");
                  nuevoJuego();
                }  
              }else{
                  JOptionPane.showMessageDialog(this, "La celda con minas al rededor ya está despejada");  
              }
            }else { //Si no tiene minas al rededor, limpiar el tablero recursivamente 
                    //  todas las celdasXY contiguas que no tengas minas al rededor
                if(celdaXY.isEstaDespejado()==false) {
                  limpiarRecursiva(y, x, 0); //limpiarRecursiva(y,x) es llamada si no tiene minas al rededor
                 // Celda.setNCeldasLimpiadas(Celda.getNCeldasLimpiadas() + celdasLimpiadas);
                  this.jLabel2.setText(" Terreno limpiado = " + Celda.getNCeldasLimpiadas());
                  if((Celda.getNBombasTablero() + Celda.getNCeldasLimpiadas()) == (FILAS * COLUMNAS) ) {
                    JOptionPane.showMessageDialog(this, "Bien! ha ganado...");
                    nuevoJuego();
                  }  
                }else{
                  JOptionPane.showMessageDialog(this, "La celda sin minas al rededor ya está despejada");
                }
            }       
         }
      }    
    }
    private int p[] = {0,-1, 1,-1, 1,0, 1,1, 0,1, -1,1, -1,0, -1,-1};
    private void limpiarRecursiva(int y, int x, int nivelX) { //limpiarRecursiva(y,x) es llamada/rellamada si no tiene minas al rededor
        int miNivel = nivelX + 1; 
        String s=""; 
        for(int z=0; z<miNivel; z++){
            s = s + "_";
        }
        if(celda[y+0][x-0].isEstaDespejado()==false) { //Cualquier tarea se hace sobre una celda no despejada
          //try{
          this.jTextArea1.setText(this.jTextArea1.getText() + s + "*Entrando al terreno N° " + nivelX + " en: " + y + ":" + x + "\n");  
          celda[y+0][x-0].setEstaDespejado(true);  
          celda[y+0][x-0].setBackground(Color.GREEN);
          Celda.setNCeldasLimpiadas(Celda.getNCeldasLimpiadas() + 1);
            //}catch(Exception e00){ }
            if(celda[y+0][x-0].getNBombasAlrededor()==0) { //???? es garantia que no hay minas al rededor
              for(int i=0; i<16; i=i+2) {
                  try{ //Prueba que celda[y][x] no se salga del tablero
                      if(celda[y+p[i]][x+p[i+1]].isEstaDespejado()==false) {  
                        if(celda[y+p[i]][x+p[i+1]].getNBombasAlrededor()==0){  //Pregunta si no tiene minas al rededor
                          this.jTextArea1.setText(this.jTextArea1.getText() + s +"__Despejando en: " + (y+p[i]) + ":" + (x+p[i+1]) + "\n");
                          limpiarRecursiva(y+p[i],x+p[i+1], miNivel); //limpiarRecursiva(y,x) es llamada si no tiene minas al rededor
                        }else{ //Si tiene se indica la cantidad de minas al rededor
                            this.jTextArea1.setText(this.jTextArea1.getText() + s + "__FIN en " + (y+p[i]) + ":" + (x+p[i+1]) + 
                                                    " con " + celda[y+p[i]][x+p[i+1]].getNBombasAlrededor() + " mina/s encontrada/s" + "\n");
                            if(celda[y+p[i]][x+p[i+1]].isEstaDespejado()==false) { //Pregunta si no fué destapada anteriormente  
                              celda[y+p[i]][x+p[i+1]].setText(""+celda[y+p[i]][x+p[i+1]].getNBombasAlrededor());
                              celda[y+p[i]][x+p[i+1]].setBackground(Color.ORANGE);
                              celda[y+p[i]][x+p[i+1]].setEstaDespejado(true);
                              Celda.setNCeldasLimpiadas(Celda.getNCeldasLimpiadas() + 1);
                            }  
                        }
                      }else {
                        this.jTextArea1.setText(this.jTextArea1.getText() + s + "__Ya visitado: " + (y+p[i]) + ":" + (x+p[i+1]) + "\n");    
                      }
                  }catch(Exception e01){ //Captura cuando se sale del tablero y retorna a la celda[][] anterior 
                                         // de la pila de llamas de la función limpiarRecursiva(int y, int x)
                     this.jTextArea1.setText(this.jTextArea1.getText() + s + "__Fuera del tablero en: " + y + ":" + x + "\n");
                  }
              }
           }
           this.jTextArea1.setText(this.jTextArea1.getText() + s + "*Saliendo del terreno N° " + nivelX + " en: " + y + ":" + x + "\n");  
        }else{
          this.jTextArea1.setText(this.jTextArea1.getText() + "Error en función recursiva!! " + y + ":" + x + "\n");
        }
    }
    private void nBombasAlRededor(int y, int x) {
        int nBombas=0;
        for(int i=0; i<16; i=i+2) {
              try{
                if(celda[y+p[i]][x+p[i+1]].isHayBomba()==true) nBombas++; 
              }catch(Exception e01){ }
        }
            celda[y][x].setNBombasAlrededor(nBombas);
            if(nBombas > 0) {  
              //celda[y][x].setBackground(Color.RED);
              //celda[y][x].setText(""+nBombas);
            }else{
              //celda[y][x].setText("0");
              //celda[y][x].setBackground(Color.YELLOW);
        }
    }
    private void mostrarBombas() {
        for(int f=0; f<FILAS; f++) {
            for(int c=0; c<COLUMNAS; c++) {
                if(celda[f][c].isHayBomba()==true) {
                    celda[f][c].setText("B"); 
                }
            }   
        }
    }
    private void mostrarTableroNocturno() {
        for(int f=0; f<FILAS; f++) {
            for(int c=0; c<COLUMNAS; c++) {
                if(celda[f][c].isHayBomba()==true) {
                    if(celda[f][c].isHayBomba()==true) {
                       celda[f][c].setText("B");
                       celda[f][c].setBackground(Color.DARK_GRAY);
                    }

                }
                if(celda[f][c].isHayBomba()==false) {
                    celda[f][c].setText(""+celda[f][c].getNBombasAlrededor());
                    if(celda[f][c].getNBombasAlrededor()>0) {
                        celda[f][c].setBackground(Color.GRAY);
                    }else{
                        celda[f][c].setBackground(Color.LIGHT_GRAY); 
                    }
                }                
            }   
        }
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

    private void this_componentResized(ComponentEvent e) {
        this.jSplitPane1.setDividerLocation(0.7d);
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        nuevoJuego();
        mostrarTableroNocturno();
    }

    private void jButton3_actionPerformed(ActionEvent e) {
        try {
             Desktop.getDesktop().browse(new URI("https://github.com/carlosprivitera/buscaminas"));
        } catch (URISyntaxException e01) {
        }catch(IOException e02){
        }catch(Exception e03) { }
    }

}
