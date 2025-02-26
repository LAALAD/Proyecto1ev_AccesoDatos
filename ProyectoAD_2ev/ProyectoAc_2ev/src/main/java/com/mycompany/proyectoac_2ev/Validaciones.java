/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoac_2ev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Clase que proporciona métodos de validación para entrada de datos del
 * usuario. Incluye validaciones para enteros, fechas pasadas y futuras, y
 * manejo de fechas bisiestas.
 *
 * @author DAM2_02
 */
public class Validaciones {

    public static Scanner sc = new Scanner(System.in);

    /**
     * Solicita al usuario que ingrese un número entero y lo valida. El método
     * sigue pidiendo una entrada hasta que el usuario ingrese un valor válido
     * (un número entero). Si el usuario ingresa un valor no numérico, se
     * muestra un mensaje de error y se solicita nuevamente.
     *
     * @return El valor entero ingresado por el usuario.
     */
    public static int asignarEntero() {
        int salida; // Variable donde se almacenará el valor ingresado por el usuario
        do {
            try {
                // Solicitar al usuario que ingrese un número entero
                salida = Integer.valueOf(sc.nextLine()); // Intentamos convertir la entrada a un número entero
                return salida;// Si la conversión es exitosa, retornamos el número
            } catch (NumberFormatException e) {
                // Si la entrada no es un número válido (excepción), mostramos un mensaje de error
                System.out.println("Valor invalido...");
                System.out.println("El valor inrtoducido no es numérico");
            }
        } while (true); // Repetir el proceso hasta que se ingrese un número entero válido
    }

    // Método que valida el día y mes de la fecha
    /**
     * Valida si una fecha ingresada por el usuario tiene un formato correcto
     * (dd/MM/yyyy).
     *
     * @param fechaStr La fecha en formato de cadena de texto.
     * @return `true` si la fecha es válida, `false` en caso contrario.
     */
    public static boolean esFechaValida(String fechaStr) {
        // Separamos el día, mes y año
        String[] partesFecha = fechaStr.split("/");

        // Comprobamos que la fecha tenga exactamente 3 partes (dd, mm, yyyy)
        if (partesFecha.length != 3) {
            System.out.println("Formato incorrecto. Debe ser dd/MM/yyyy.");
            return false;  // Fecha inválida
        }

        // Intentamos convertir los valores de día, mes y año
        try {
            int dia = Integer.valueOf(partesFecha[0]);
            int mes = Integer.valueOf(partesFecha[1]);
            int ano = Integer.valueOf(partesFecha[2]);

            // Validar que el mes esté entre 1 y 12
            if (mes < 1 || mes > 12) {
                System.out.println("Mes inválido. El mes debe estar entre 01 y 12.");
                return false;  // Fecha inválida
            }

            // Validar que el día esté dentro de los rangos posibles para ese mes
            if (!esDiaValido(dia, mes, ano)) {
                return false;  // Fecha inválida
            }

        } catch (NumberFormatException e) {
            System.out.println("El día, mes o año no es un número válido. Intenta nuevamente.");
            return false;  // Fecha inválida
        }

        // Si pasa todas las validaciones
        return true;
    }

    // Método auxiliar para validar el día según el mes y el año
    /**
     * Valida si el día ingresado es correcto según el mes y año.
     *
     * @param dia Día del mes.
     * @param mes Mes del año.
     * @param ano Año correspondiente.
     * @return `true` si el día es válido, `false` en caso contrario.
     */
    public static boolean esDiaValido(int dia, int mes, int ano) {
        // Definir los días máximos por mes
        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        // Si es un año bisiesto, febrero tiene 29 días
        if (esBisiesto(ano)) {
            diasPorMes[1] = 29;  // Febrero (índice 1) tiene 29 días en años bisiestos
        }

        // Comprobar si el día es válido para el mes
        //mes-1 porque el mes 1 enero -> es la posicion 0 del array
        if (dia < 1 || dia > diasPorMes[mes - 1]) { // si el dia es negativo o mas de los particulares de ese mes
            System.out.println("Día inválido. El mes " + mes + " tiene " + diasPorMes[mes - 1] + " días.");
            return false;  // Fecha inválida
        }

        // Si el día es válido para ese mes
        return true;
    }

    // Método para verificar si un año es bisiesto
    /**
     * Determina si un año es bisiesto.
     *
     * @param ano Año a evaluar.
     * @return `true` si el año es bisiesto, `false` en caso contrario.
     */
    public static boolean esBisiesto(int ano) {
        if (ano % 4 == 0) {  // Verificamos si es divisible por 4
            if (ano % 100 == 0) {  // Verificamos si es divisible por 100
                if (ano % 400 == 0) {  // Verificamos si también es divisible por 400
                    return true;  // Si es divisible por 400, es bisiesto
                } else {
                    return false;  // Si no es divisible por 400, no es bisiesto
                }
            }
            return true;  // Si es divisible por 4 pero no por 100, es bisiesto
        }
        return false;  // Si no es divisible por 4, no es bisiesto
    }

    // Método estático que solicita y valida una fecha de nacimiento introducida por el usuario
    /**
     * Solicita y valida una fecha pasada (anterior a la fecha actual).
     *
     * @return La fecha ingresada por el usuario en formato `Date`.
     */
    public static Date validarFechaPasada() {
        // Declaramos una variable de tipo Date para almacenar la fecha que será validada
        Date fecha = null;

        // Creamos un objeto SimpleDateFormat que se encargará de convertir la cadena de texto en un objeto Date
        // El formato de la fecha esperado es día/mes/año, por ejemplo: 23/02/2025
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Variable booleana que indica si la fecha ingresada es válida. Se inicializa como falsa.
        boolean fechaValida = false;

        // Iniciamos un bucle while que seguirá pidiendo la fecha hasta que el formato sea correcto
        while (!fechaValida) {
            try {
                // Solicitamos la fecha de nacimiento al usuario
                System.out.print("Introduce la fecha de nacimiento (dd/MM/yyyy): ");

                // Leemos la entrada del usuario como una cadena de texto (String)
                String fechaStr = sc.nextLine();

                // Llamamos al método que valida el día y mes
                if (!esFechaValida(fechaStr)) {
                    continue;  // Si la fecha no es válida, volvemos a pedirla
                }

                // Intentamos convertir la cadena leída en un objeto Date usando el formato esperado
                fecha = sdf.parse(fechaStr);

                // Obtener la fecha actual
                Date fechaActual = new Date();

                // Comprobar si la fecha ingresada es anterior a la fecha actual
                if (fecha.before(fechaActual)) {
                    // Si la fecha es válida (es anterior a la actual), se establece como válida
                    fechaValida = true;
                } else {
                    // Si la fecha no es anterior a la actual, se informa al usuario
                    System.out.println("La fecha ingresada no puede ser futura. Intenta nuevamente.");
                }
                //Si ocurre un error al intentar convertir la fecha (por ejemplo, si el formato es incorrecto),
                // entra en el bloque catch y muestra un mensaje de error
            } catch (ParseException e) {
                System.out.println("Formato de fecha incorrecto. Intenta nuevamente.");
            }
        }

        // Cuando la fecha es válida, la devolvemos
        return fecha;
    }

    // Método estático que solicita y valida una fecha de nacimiento introducida por el usuario
    /**
     * Solicita y valida una fecha futura (posterior a la fecha actual).
     *
     * @return La fecha ingresada por el usuario en formato `Date`.
     */
    public static Date validarFechaFutura() {
        // Declaramos una variable de tipo Date para almacenar la fecha que será validada
        Date fecha = null;

        // Creamos un objeto SimpleDateFormat que se encargará de convertir la cadena de texto en un objeto Date
        // El formato de la fecha esperado es día/mes/año, por ejemplo: 23/02/2025
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Variable booleana que indica si la fecha ingresada es válida. Se inicializa como falsa.
        boolean fechaValida = false;

        // Iniciamos un bucle while que seguirá pidiendo la fecha hasta que el formato sea correcto
        while (!fechaValida) {
            try {
                // Solicitamos la fecha de nacimiento al usuario
                System.out.print("Introduce la fecha (dd/MM/yyyy): ");

                // Leemos la entrada del usuario como una cadena de texto (String)
                String fechaStr = sc.nextLine();

                // Llamamos al método que valida el día y mes
                if (!esFechaValida(fechaStr)) {
                    continue;  // Si la fecha no es válida, volvemos a pedirla
                }

                // Intentamos convertir la cadena leída en un objeto Date usando el formato esperado
                fecha = sdf.parse(fechaStr);

                // Obtener la fecha actual
                Date fechaActual = new Date();

                // Comprobar si la fecha ingresada es posterior a la fecha actual
                if (fecha.after(fechaActual)) {
                    // Si la fecha es posterior a la actual, es válida
                    fechaValida = true;
                } else {
                    // Si la fecha no es posterior a la actual, mostramos un mensaje
                    System.out.println("La fecha debe ser posterior a la fecha actual. Intenta nuevamente.");
                }
                //Si ocurre un error al intentar convertir la fecha (por ejemplo, si el formato es incorrecto),
                // entra en el bloque catch y muestra un mensaje de error
            } catch (ParseException e) {
                System.out.println("Formato de fecha incorrecto. Intenta nuevamente.");
            }
        }

        // Cuando la fecha es válida, la devolvemos
        return fecha;
    }

    /*public static boolean validarEmail(String email){
        String regex ="";
        if(email.matches(regex)){
            return 
        }
    }*/
}
