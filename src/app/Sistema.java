package app;

import java.awt.Dimension;

public class Sistema {
    public Sistema() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static VentaPrincipal vp = new VentaPrincipal();
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        vp.setDefaultCloseOperation(vp.EXIT_ON_CLOSE);
        vp.setExtendedState(vp.MAXIMIZED_BOTH);
        vp.setLocationRelativeTo(null);
        vp.setVisible(true);
    }

    private void jbInit() throws Exception {
        vp.setSize(new Dimension(669, 450));
    }
}
