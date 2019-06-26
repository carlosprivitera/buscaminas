package app;

import datos.DatosCompartidos;

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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import juego.Tablero;

public class VentaPrincipal extends JFrame implements DatosCompartidos {
    private ImageIcon ImageIcon = new ImageIcon(getClass().getResource("/recursos/mina.png"));
    private Image imagen01 =  ImageIcon.getImage();
    private JPanel jPanel1 = new JPanel();
    private GridLayout gridLayout1 = new GridLayout();
    private Tablero tablero = null; 
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
        gridLayout1.setColumns(FILAS_Y);
        gridLayout1.setRows(COLUMNAS_X);
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
        jButton2.setToolTipText("Nuevo juego en modo nocturno para profesores y alumnos");
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
        tablero = new Tablero(jPanel1, jTextArea1, jLabel1, jLabel2);
        tablero.crearTablero();
        tablero.nuevoJuego();
    }

    private void jButton1_actionPerformed(ActionEvent e) {
        tablero.nuevoJuego();
    }

    private void this_componentResized(ComponentEvent e) {
        this.jSplitPane1.setDividerLocation(0.7d);
    }

    private void jButton2_actionPerformed(ActionEvent e) {
        tablero.nuevoJuego();
        tablero.mostrarTableroNocturno();
    }

    private void jButton3_actionPerformed(ActionEvent e) {
        try {
             Desktop.getDesktop().browse(new URI("https://github.com/carlosprivitera/buscaminas"));
        } catch (URISyntaxException e01) {
        }catch(IOException e02){
        }catch(Exception e03) { }
    }

}
