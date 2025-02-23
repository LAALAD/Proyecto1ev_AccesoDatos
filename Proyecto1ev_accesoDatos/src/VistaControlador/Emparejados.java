/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package VistaControlador;

import Modelo.Jugador;
import Modelo.Torneo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author DAM2_02
 */
public class Emparejados extends javax.swing.JFrame {

    /**
     * Creates new form Emparejados
     */
    private static Torneo torneo;
    private static final int NUM_CARTAS = 16;  // Número total de cartas
    private ArrayList<JButton> cartas;        // Lista de botones para las cartas
    private ArrayList<String> cartasValores;   // Valores de las cartas (para comparar)
    private JButton carta1, carta2;           // Para almacenar las cartas que se están volteando
    private int turnos = 0;                   // Contador de turnos
    private boolean comparando = false;       // Para saber si estamos comparando dos cartas

    public Emparejados(Torneo torneo) {
        initComponents();
        this.torneo = torneo;
        cartas = new ArrayList<>();
        cartasValores = new ArrayList<>();
        inicializarCartas();
        agregarCartas();
    }

    // Inicializa las cartas con valores
    private void inicializarCartas() {
        String[] valores = {"A", "B", "C", "D", "E", "F", "G", "H"};
        // Duplica los valores para que haya dos de cada uno
        for (String valor : valores) {
            cartasValores.add(valor);
            cartasValores.add(valor);
        }
        Collections.shuffle(cartasValores);  // Mezcla los valores para que aparezcan aleatorios
    }
    // Asocia los botones del GUI con la lista de cartas

    private void agregarCartas() {
        // Aquí se asocia cada botón del JFrame con los valores de las cartas
        // Agrega a la lista de cartas los botones del GUI
        cartas.add(c1);
        cartas.add(c2);
        cartas.add(c3);
        cartas.add(c4);
        cartas.add(c5);
        cartas.add(c6);
        cartas.add(c7);
        cartas.add(c8);
        cartas.add(c9);
        cartas.add(c10);
        cartas.add(c11);
        cartas.add(c12);
        cartas.add(c13);
        cartas.add(c14);
        cartas.add(c15);
        cartas.add(c16);
    }

    // Maneja el clic en cualquier carta
    private void manejarClic(JButton carta) { //necesario para que nose pueda pulsar otras cartas mientras se comprueba la coincidencia
        if (comparando) { //mientras se esta comparando
            return;  // Evita que se haga clic mientras se comparan cartas
        }
        mostrarCarta(carta);
    }
    // Muestra el valor de la carta (la voltea)
    private void mostrarCarta(JButton carta) {
        int indice = cartas.indexOf(carta); //posicion del boton en el arrayList de botones de cartas
        carta.setText(cartasValores.get(indice));  // Muestra el valor en la carta
        carta.setEnabled(false);  // Deshabilita el botón para evitar clics repetidos

        if (carta1 == null) {
            carta1 = carta;  // Guarda la primera carta volteada
        } else if (carta2 == null) {
            carta2 = carta;  // Guarda la segunda carta volteada
            comparando = true;  // Activa la comparación
            //verificarPareja();  // Compara las cartas inmediatamente
            // Pausa la comparación para mostrar la segunda carta antes de verificar
            /*OPCION CON LAMBDA
            // Crea un temporizador que se ejecute después de 500 ms
            Timer timer = new Timer(500, e -> verificarPareja());  // Acción definida con una expresión lambda

            // Asegúrate de que no repita la acción
            timer.setRepeats(false);

            // Inicia el temporizador
            timer.start();
             */

            /*Defino el timer y luego le digo que no se repita y lo arranco con el start, no arranca segun lo creo*/
            Timer timer = new Timer(500, new ActionListener() { //el constructor recibe dos params-> retraso de 500 milisegundos,  objeto que escucha un evento de acción (en este caso, el temporizador completado) y define qué debe hacer cuando el temporizador expire 
                @Override
                public void actionPerformed(ActionEvent e) { //aqui dentro indico que hacer cuando acabe el timer
                    verificarPareja();  // Verifica la pareja después de un pequeño retraso
                }
            });
            /*Por defecto, un Timer repite su acción cada vez que se cumple el tiempo especificado. 
            En este caso, se quiere que el Timer se ejecute solo una vez (es decir, que no repita la acción). 
            Por lo tanto, se establece false para que solo se ejecute una vez después de 500 ms.*/
            timer.setRepeats(false);
            
            /* Finalmente, se llama a este método para iniciar el temporizador. Esto comienza a contar los 500 ms, 
            y una vez transcurrido ese tiempo, ejecutará el código dentro de actionPerformed().*/
            timer.start();

        }
    }

    // Verifica si las dos cartas volteadas son iguales
    private void verificarPareja() {
        int indice1 = cartas.indexOf(carta1);
        int indice2 = cartas.indexOf(carta2);

        if (cartasValores.get(indice1).equals(cartasValores.get(indice2))) {
            // Si son iguales, dejarlas descubiertas
            carta1.setEnabled(false);
            carta2.setEnabled(false);
        } else {
            // Si no son iguales, voltearlas nuevamente
            carta1.setText("");
            carta2.setText("");
            carta1.setEnabled(true);
            carta2.setEnabled(true);
        }

        // Restablece las cartas
        carta1 = null;
        carta2 = null;
        comparando = false;
        turnos++;
        comprobarVictoria();
    }

    // Comprobar si el jugador ha ganado (todas las cartas descubiertas)
    private void comprobarVictoria() {
        for (JButton carta : cartas) {
            if (carta.isEnabled()) {
                return;  // Si hay alguna carta volteada, el juego continúa
            }
        }
        JOptionPane.showMessageDialog(this, "¡Ganaste en " + turnos + " turnos!");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        c1 = new javax.swing.JButton();
        c2 = new javax.swing.JButton();
        c3 = new javax.swing.JButton();
        c4 = new javax.swing.JButton();
        c5 = new javax.swing.JButton();
        c6 = new javax.swing.JButton();
        c7 = new javax.swing.JButton();
        c8 = new javax.swing.JButton();
        c9 = new javax.swing.JButton();
        c10 = new javax.swing.JButton();
        c11 = new javax.swing.JButton();
        c12 = new javax.swing.JButton();
        c13 = new javax.swing.JButton();
        c14 = new javax.swing.JButton();
        c15 = new javax.swing.JButton();
        c16 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(450, 450));
        setSize(new java.awt.Dimension(450, 450));
        getContentPane().setLayout(null);

        c1.setBackground(new java.awt.Color(249, 232, 218));
        c1.setMaximumSize(new java.awt.Dimension(50, 50));
        c1.setMinimumSize(new java.awt.Dimension(50, 50));
        c1.setPreferredSize(new java.awt.Dimension(50, 50));
        c1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c1ActionPerformed(evt);
            }
        });

        c2.setBackground(new java.awt.Color(249, 232, 218));
        c2.setMaximumSize(new java.awt.Dimension(50, 50));
        c2.setMinimumSize(new java.awt.Dimension(50, 50));
        c2.setPreferredSize(new java.awt.Dimension(50, 50));
        c2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c2ActionPerformed(evt);
            }
        });

        c3.setBackground(new java.awt.Color(249, 232, 218));
        c3.setMaximumSize(new java.awt.Dimension(50, 50));
        c3.setMinimumSize(new java.awt.Dimension(50, 50));
        c3.setPreferredSize(new java.awt.Dimension(50, 50));
        c3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c3ActionPerformed(evt);
            }
        });

        c4.setBackground(new java.awt.Color(249, 232, 218));
        c4.setMaximumSize(new java.awt.Dimension(50, 50));
        c4.setMinimumSize(new java.awt.Dimension(50, 50));
        c4.setPreferredSize(new java.awt.Dimension(50, 50));
        c4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c4ActionPerformed(evt);
            }
        });

        c5.setBackground(new java.awt.Color(249, 232, 218));
        c5.setMaximumSize(new java.awt.Dimension(50, 50));
        c5.setMinimumSize(new java.awt.Dimension(50, 50));
        c5.setPreferredSize(new java.awt.Dimension(50, 50));
        c5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c5ActionPerformed(evt);
            }
        });

        c6.setBackground(new java.awt.Color(249, 232, 218));
        c6.setMaximumSize(new java.awt.Dimension(50, 50));
        c6.setMinimumSize(new java.awt.Dimension(50, 50));
        c6.setPreferredSize(new java.awt.Dimension(50, 50));
        c6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c6ActionPerformed(evt);
            }
        });

        c7.setBackground(new java.awt.Color(249, 232, 218));
        c7.setMaximumSize(new java.awt.Dimension(50, 50));
        c7.setMinimumSize(new java.awt.Dimension(50, 50));
        c7.setPreferredSize(new java.awt.Dimension(50, 50));
        c7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c7ActionPerformed(evt);
            }
        });

        c8.setBackground(new java.awt.Color(249, 232, 218));
        c8.setMaximumSize(new java.awt.Dimension(50, 50));
        c8.setMinimumSize(new java.awt.Dimension(50, 50));
        c8.setPreferredSize(new java.awt.Dimension(50, 50));
        c8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c8ActionPerformed(evt);
            }
        });

        c9.setBackground(new java.awt.Color(249, 232, 218));
        c9.setMaximumSize(new java.awt.Dimension(50, 50));
        c9.setMinimumSize(new java.awt.Dimension(50, 50));
        c9.setPreferredSize(new java.awt.Dimension(50, 50));
        c9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c9ActionPerformed(evt);
            }
        });

        c10.setBackground(new java.awt.Color(249, 232, 218));
        c10.setMaximumSize(new java.awt.Dimension(50, 50));
        c10.setMinimumSize(new java.awt.Dimension(50, 50));
        c10.setPreferredSize(new java.awt.Dimension(50, 50));
        c10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c10ActionPerformed(evt);
            }
        });

        c11.setBackground(new java.awt.Color(249, 232, 218));
        c11.setMaximumSize(new java.awt.Dimension(50, 50));
        c11.setMinimumSize(new java.awt.Dimension(50, 50));
        c11.setPreferredSize(new java.awt.Dimension(50, 50));
        c11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c11ActionPerformed(evt);
            }
        });

        c12.setBackground(new java.awt.Color(249, 232, 218));
        c12.setMaximumSize(new java.awt.Dimension(50, 50));
        c12.setMinimumSize(new java.awt.Dimension(50, 50));
        c12.setPreferredSize(new java.awt.Dimension(50, 50));
        c12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c12ActionPerformed(evt);
            }
        });

        c13.setBackground(new java.awt.Color(249, 232, 218));
        c13.setMaximumSize(new java.awt.Dimension(50, 50));
        c13.setMinimumSize(new java.awt.Dimension(50, 50));
        c13.setPreferredSize(new java.awt.Dimension(50, 50));
        c13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c13ActionPerformed(evt);
            }
        });

        c14.setBackground(new java.awt.Color(249, 232, 218));
        c14.setMaximumSize(new java.awt.Dimension(50, 50));
        c14.setMinimumSize(new java.awt.Dimension(50, 50));
        c14.setPreferredSize(new java.awt.Dimension(50, 50));
        c14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c14ActionPerformed(evt);
            }
        });

        c15.setBackground(new java.awt.Color(249, 232, 218));
        c15.setMaximumSize(new java.awt.Dimension(50, 50));
        c15.setMinimumSize(new java.awt.Dimension(50, 50));
        c15.setPreferredSize(new java.awt.Dimension(50, 50));
        c15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c15ActionPerformed(evt);
            }
        });

        c16.setBackground(new java.awt.Color(249, 232, 218));
        c16.setMaximumSize(new java.awt.Dimension(50, 50));
        c16.setMinimumSize(new java.awt.Dimension(50, 50));
        c16.setPreferredSize(new java.awt.Dimension(50, 50));
        c16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(c5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(c9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(c13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(c16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(c1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(c5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(c9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(c13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(c16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(120, 90, 240, 250);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void c2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c2ActionPerformed
        manejarClic(c2);
    }//GEN-LAST:event_c2ActionPerformed

    private void c3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c3ActionPerformed
        manejarClic(c3);
    }//GEN-LAST:event_c3ActionPerformed

    private void c1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c1ActionPerformed
        manejarClic(c1);
    }//GEN-LAST:event_c1ActionPerformed

    private void c5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c5ActionPerformed
        manejarClic(c5);
    }//GEN-LAST:event_c5ActionPerformed

    private void c6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c6ActionPerformed
        manejarClic(c6);
    }//GEN-LAST:event_c6ActionPerformed

    private void c4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c4ActionPerformed
        manejarClic(c4);
    }//GEN-LAST:event_c4ActionPerformed

    private void c8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c8ActionPerformed
        manejarClic(c8);
    }//GEN-LAST:event_c8ActionPerformed

    private void c9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c9ActionPerformed
        manejarClic(c9);
    }//GEN-LAST:event_c9ActionPerformed

    private void c7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c7ActionPerformed
        manejarClic(c7);
    }//GEN-LAST:event_c7ActionPerformed

    private void c10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c10ActionPerformed
        manejarClic(c10);
    }//GEN-LAST:event_c10ActionPerformed

    private void c11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c11ActionPerformed
        manejarClic(c11);
    }//GEN-LAST:event_c11ActionPerformed

    private void c12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c12ActionPerformed
        manejarClic(c12);
    }//GEN-LAST:event_c12ActionPerformed

    private void c13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c13ActionPerformed
        manejarClic(c13);
    }//GEN-LAST:event_c13ActionPerformed

    private void c14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c14ActionPerformed
        manejarClic(c14);
    }//GEN-LAST:event_c14ActionPerformed

    private void c15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c15ActionPerformed
        manejarClic(c15);
    }//GEN-LAST:event_c15ActionPerformed

    private void c16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c16ActionPerformed
        manejarClic(c16);
    }//GEN-LAST:event_c16ActionPerformed

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
            java.util.logging.Logger.getLogger(Emparejados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Emparejados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Emparejados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Emparejados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Emparejados(torneo).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton c1;
    private javax.swing.JButton c10;
    private javax.swing.JButton c11;
    private javax.swing.JButton c12;
    private javax.swing.JButton c13;
    private javax.swing.JButton c14;
    private javax.swing.JButton c15;
    private javax.swing.JButton c16;
    private javax.swing.JButton c2;
    private javax.swing.JButton c3;
    private javax.swing.JButton c4;
    private javax.swing.JButton c5;
    private javax.swing.JButton c6;
    private javax.swing.JButton c7;
    private javax.swing.JButton c8;
    private javax.swing.JButton c9;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
