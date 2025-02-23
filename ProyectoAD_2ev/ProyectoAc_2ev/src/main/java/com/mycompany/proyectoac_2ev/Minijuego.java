
package com.mycompany.proyectoac_2ev;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Minijuego extends JFrame {
    private static final int NUM_CARTAS = 16; // Número total de cartas
    private ArrayList<JButton> cartas;      // Lista de botones para las cartas
    private ArrayList<String> cartasValores; // Valores de las cartas (para comparar)
    private JButton carta1, carta2;         // Para almacenar las cartas que se están volteando
    private int turnos = 0;                 // Contador de turnos
    private boolean comparando = false;     // Para saber si estamos comparando dos cartas

    public Minijuego() {
        setTitle("Juego de Memoria");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 4));  // Organiza las cartas en una cuadrícula 4x4
        cartas = new ArrayList<>();
        cartasValores = new ArrayList<>();
        System.out.println("estoycansado jefe" + this.getTitle());
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

    // Agrega las cartas (botones) al JFrame
    private void agregarCartas() {
        for (int i = 0; i < NUM_CARTAS; i++) {
            JButton carta = new JButton();
            carta.setFont(new Font("Arial", Font.PLAIN, 20));
            carta.setBackground(Color.LIGHT_GRAY);
            carta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (comparando) return;  // Evita que se haga clic mientras se comparan cartas
                    mostrarCarta(carta);
                }
            });
            cartas.add(carta);
            add(carta);
        }
    }

    // Muestra el valor de la carta (la voltea)
    private void mostrarCarta(JButton carta) {
        int indice = cartas.indexOf(carta);
        carta.setText(cartasValores.get(indice));
        carta.setEnabled(false);  // Deshabilita el botón para evitar clics repetidos

        if (carta1 == null) {
            carta1 = carta;  // Guarda la primera carta volteada
        } else if (carta2 == null) {
            carta2 = carta;  // Guarda la segunda carta volteada
            comparando = true;  // Activa la comparación
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    verificarPareja();
                }
            });
            timer.setRepeats(false);
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

    public static void main(String[] args) {
        Minijuego juego = new Minijuego();
        juego.setVisible(true);
        
    }
}


