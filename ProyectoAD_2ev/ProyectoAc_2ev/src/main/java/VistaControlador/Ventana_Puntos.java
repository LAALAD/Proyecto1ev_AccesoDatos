/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VistaControlador;

/**
 *
 * @author Usuario
 */
public class Ventana_Puntos extends javax.swing.JFrame {

    /**
     * Creates new form Ventana_Puntos
     */
    private static int turnos;
    private  MenuMinijuegos mm;
    public Ventana_Puntos(int turnos, MenuMinijuegos mm) {
        initComponents();
        this.mm = mm;
        this.turnos = turnos;
        msg.setText("¡GANASTE EN " + turnos + " TURNOS!");
        
    }
    public Ventana_Puntos(int turnos) {
        initComponents();
        this.turnos = turnos;
        msg.setText("¡GANASTE EN " + turnos + " TURNOS!");
        mm.setVisible(true);
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FONDO = new javax.swing.JPanel();
        msg = new javax.swing.JLabel();
        btm_salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        FONDO.setBackground(new java.awt.Color(216, 194, 170));
        FONDO.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        msg.setFont(new java.awt.Font("Bauhaus 93", 1, 18)); // NOI18N
        msg.setForeground(new java.awt.Color(35, 103, 114));
        msg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        msg.setText("texto ganador");

        btm_salir.setBackground(new java.awt.Color(246, 232, 198));
        btm_salir.setFont(new java.awt.Font("Bauhaus 93", 1, 24)); // NOI18N
        btm_salir.setForeground(new java.awt.Color(218, 91, 4));
        btm_salir.setText("SALIR");
        btm_salir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btm_salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btm_salir.setFocusPainted(false);
        btm_salir.setPreferredSize(new java.awt.Dimension(180, 50));
        btm_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btm_salirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout FONDOLayout = new javax.swing.GroupLayout(FONDO);
        FONDO.setLayout(FONDOLayout);
        FONDOLayout.setHorizontalGroup(
            FONDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FONDOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(msg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(FONDOLayout.createSequentialGroup()
                .addGap(179, 179, 179)
                .addComponent(btm_salir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );
        FONDOLayout.setVerticalGroup(
            FONDOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FONDOLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btm_salir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(FONDO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(FONDO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btm_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btm_salirActionPerformed

        this.dispose();
        mm.setVisible(true);
    }//GEN-LAST:event_btm_salirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ventana_Puntos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ventana_Puntos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ventana_Puntos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ventana_Puntos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ventana_Puntos(turnos).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FONDO;
    private javax.swing.JButton btm_salir;
    private javax.swing.JLabel msg;
    // End of variables declaration//GEN-END:variables
}
