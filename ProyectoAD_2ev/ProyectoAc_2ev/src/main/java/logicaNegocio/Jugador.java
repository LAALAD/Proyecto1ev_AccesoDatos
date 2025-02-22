package logicaNegocio;

import Factory.FactoriaItems;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Clase que representa a un jugador dentro de un juego. Cada jugador tiene un
 * ID, un nombre, y un historial de partidas jugadas y ganadas, tanto
 * globalmente como dentro de un torneo. Además, puede seleccionar su cara (CARA
 * o CRUZ) y tiene un ítem asociado (moneda o dado) que puede usar durante el
 * juego.
 *
 * Esta clase implementa la interfaz {@link Comparable} para permitir la
 * comparación entre jugadores basada en las partidas ganadas en un torneo.
 *
 * @author adria
 */
@Entity
public class Jugador implements Comparable<Jugador>, Serializable {

    // Atributos del jugador
    @Id //Id -->PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK autogenerada por la BBDD por secuencias (auto o secuence)
    private int id_j;
    @Basic //nombre y apellido son campos sin cosas especiales, no es necesario indicarlo.
    private String nombre;
    @Basic //nombre y apellido son campos sin cosas especiales, no es necesario indicarlo.
    private double partidasGanadas = 0;
    @Basic //nombre y apellido son campos sin cosas especiales, no es necesario indicarlo.
    private double partidasJugadas = 0;
    
    // Relación 1:N con TorneoXJugador
    @OneToMany(mappedBy = "jugador")
    private ArrayList<TorneoXJugador> torneos = new ArrayList<>();
    
    @Transient // no se guardará en la base de datos 
    private double partidasGanadasTorneo = 0;
    @Transient
    private double partidasJugadasTorneo = 0;
    @Transient
    private int seleccion = 0; // Selección: 2 --> cruz / 1 --> cara
    @Transient
    private Item item;
    @Transient
    private ArrayList<Carta> mano;

    public Jugador() { }

    
    /**
     * Constructor de la clase Jugador.
     *
     * @param id_j Identificador único del jugador.
     * @param nombre Nombre del jugador.
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    /**
     * Método que devuelve un ítem de tipo moneda utilizando la factoria de
     * items.
     *
     * @return Un objeto de tipo {@link Item} correspondiente a una moneda.
     */
    public Item cogerMoneda() {
        //return FactoriaItems.getItem("moneda");
        return FactoriaItems.getItem(FactoriaItems.ItemTipo.MONEDA);
    }

    /**
     * Método que devuelve un ítem de tipo dado utilizando la factoria de items.
     *
     * @return Un objeto de tipo {@link Item} correspondiente a un dado.
     */
    public Item cogerDado() {
        //return FactoriaItems.getItem("dado");
        return FactoriaItems.getItem(FactoriaItems.ItemTipo.DADO);
    }

    /**
     * Getter del ítem asociado al jugador.
     *
     * @return El ítem que el jugador tiene actualmente.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Setter para el id del jugador.
     *
     * @param id_j El nuevo id del jugador.
     */
    public void setId_j(int id_j) {
        this.id_j = id_j;
    }

    /**
     * Setter para el ítem del jugador.
     *
     * @param item El nuevo ítem que el jugador utilizará.
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /*public void seleccionarCara() {

        Scanner sc = new Scanner(System.in);

        System.out.println("Seleccione Cara(1) o Cruz(2)");
        String selec = sc.nextLine();
        if (selec.equals("1")) {
            seleccion = 1;
        } 
    }*/
    /**
     * Método que permite al jugador seleccionar entre cara (1) o cruz (2). La
     * selección se realiza mediante un input desde la consola. Si se ingresa un
     * valor no válido, se solicita de nuevo la entrada hasta que sea válida.
     */
    public void seleccionarCara() {
        Scanner sc = new Scanner(System.in);

        String selec = "";
        boolean entradaValida = false;
        do {
            System.out.println("Seleccione Cara(1) o Cruz(2)");
            selec = sc.nextLine();

            switch (selec) {
                case "1":
                    entradaValida = true;
                    seleccion = 1;
                    break;
                case "2":
                    entradaValida = true;
                    seleccion = 2;
                    break;
                default:
                    System.out.println("Elija Cara(1) o Cruz(2)");
            }
        } while (!entradaValida);
    }

    public void seleccionarPiedraPapelTijera() {
        Scanner sc = new Scanner(System.in);

        String selec = "";
        boolean entradaValida = false;
        do {
            System.out.println("Seleccione Piedra(1), Papel(2) o Tijera(3)");
            selec = sc.nextLine();

            switch (selec) {
                case "1":
                    entradaValida = true;
                    seleccion = 1;
                    break;
                case "2":
                    entradaValida = true;
                    seleccion = 2;
                    break;
                case "3":
                    entradaValida = true;
                    seleccion = 3;
                    break;
                default:
                    System.out.println("Elija Piedra(1), Papel(2)o Tijera(3)");
            }
        } while (!entradaValida);
    }

    /**
     * Método para obtener la puntuación del jugador en formato de array. El
     * primer valor es el total de partidas ganadas y el segundo es el
     * porcentaje de victorias (win rate).
     *
     * @return Un arreglo de tipo {@code double} con las estadísticas del
     * jugador: partidas ganadas y porcentaje de victorias.
     */
    public double[] obtenerPuntuacion() {//getter
        double[] puntuacion = new double[2];
        puntuacion[0] = partidasGanadas;
        puntuacion[1] = (partidasGanadas / partidasJugadas) * 100; //winRate
        //printf tiene funciones para mostrar solo dos decimales
        return puntuacion;
    }

    /**
     * Método que calcula el porcentaje de victorias del jugador en un torneo.
     *
     * @return El porcentaje de victorias en el torneo, calculado como (partidas
     * ganadas / partidas jugadas) * 100.
     */
    public double calcularWinRate() {
        return (partidasGanadasTorneo / partidasJugadasTorneo) * 100;
    }

    /**
     * Método para actualizar la puntuación del jugador después de un juego,
     * incrementando las partidas ganadas si el jugador ha ganado.
     *
     * @param ganador Booleano que indica si el jugador ha ganado (true) o no
     * (false).
     */
    public void actualizarPuntuacion(boolean ganador) {
        if (ganador) {
            partidasGanadas++;
        }
        partidasJugadas++;
    }

    /**
     * Método para actualizar la puntuación del jugador en un torneo,
     * incrementando las partidas ganadas en el torneo si el jugador ha ganado.
     *
     * @param ganador Booleano que indica si el jugador ha ganado en el torneo
     * (true) o no (false).
     */
    public void actualizarPuntuacionTorneo(boolean ganador) {
        if (ganador) {
            partidasGanadasTorneo++;
        }
        partidasJugadasTorneo++;
    }

    /**
     * Método que reinicia las puntuaciones del jugador en el torneo actual.
     */
    public void resetearPuntuacionTorneo() {
        partidasJugadasTorneo = 0;
        partidasGanadasTorneo = 0;
    }

    /**
     * Getter del ID del jugador.
     *
     * @return El ID del jugador.
     */
    public int getId_j() {
        return id_j;
    }

    /**
     * Getter del nombre del jugador.
     *
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter para las partidas ganadas en el torneo.
     *
     * @param partidasGanadasTorneo El número de partidas ganadas en el torneo.
     */
    public void setPartidasGanadasTorneo(double partidasGanadasTorneo) {
        this.partidasGanadasTorneo = partidasGanadasTorneo;
    }

    /**
     * Setter para las partidas jugadas en el torneo.
     *
     * @param partidasJugadasTorneo El número de partidas jugadas en el torneo.
     */
    public void setPartidasJugadasTorneo(double partidasJugadasTorneo) {
        this.partidasJugadasTorneo = partidasJugadasTorneo;
    }

    /**
     * Getter de las partidas ganadas en el torneo.
     *
     * @return Las partidas ganadas en el torneo.
     */
    public double getPartidasGanadasTorneo() {
        return partidasGanadasTorneo;
    }

    /**
     * Getter de las partidas jugadas en el torneo.
     *
     * @return Las partidas jugadas en el torneo.
     */
    public double getPartidasJugadasTorneo() {
        return partidasJugadasTorneo;
    }

    /**
     * Getter de las partidas ganadas globalmente.
     *
     * @return El número total de partidas ganadas.
     */
    public double getPartidasGanadas() {
        return partidasGanadas;
    }

    /**
     * Getter de las partidas jugadas globalmente.
     *
     * @return El número total de partidas jugadas.
     */
    public double getPartidasJugadas() {
        return partidasJugadas;
    }

    /**
     * Getter de la selección del jugador (CARA o CRUZ). Getter del resultado
     * del lanzamiento del dado del jugador (1-6).
     *
     * @return La selección del jugador: 1 para CARA y 2 para CRUZ || El numero
     * de dado que ha salido (1-6)
     */
    public int getSeleccion() {
        return seleccion;
    }

    /**
     * Setter para el nombre del jugador.
     *
     * @param nombre El nuevo nombre del jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Setter para las partidas ganadas.
     *
     * @param partidasGanadas El nuevo número de partidas ganadas.
     */
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    /**
     * Setter para las partidas jugadas.
     *
     * @param partidasJugadas El nuevo número de partidas jugadas.
     */
    public void setPartidasJugadas(int partidasJugadas) {
        this.partidasJugadas = partidasJugadas;
    }

    /**
     * Setter para la selección del jugador (CARA o CRUZ). El valor obtenido al
     * lanzar su dado
     *
     * @param seleccion El valor de selección: 1 para CARA y 2 para CRUZ || El
     * numero de dado que ha salido (1-6)
     */
    public void setSeleccion(int seleccion) {
        this.seleccion = seleccion;
    }

    /**
     * Método que genera una representación en formato string del jugador,
     * incluyendo su ID, nombre y estadísticas de partidas ganadas y jugadas.
     *
     * @return La representación en cadena del jugador.
     */
    @Override
    public String toString() {
        return "ID: " + id_j + " | NOMBRE: " + nombre + " | partidasGanadas=" + partidasGanadas + " | partidasJugadas=" + partidasJugadas;
    }

    //Se haría con object si no hubiera implementado la interfaz indicando Jugador implements Comparable<Jugador> vs Comparable 
    /*@Override
    public int compareTo(Object o){
        if(o  == null){
            throw new NullPointerException();
        }
   
        //getClass es un equals, el instance of lo necesitammos para interfaces. Porque no sabes que clase va a implementar la interfaz  
        if(o.getClass().equals(Jugador.class)){
            throw new ClassCastException();
        }
    }*/
    /**
     * Método que compara dos jugadores en función de las partidas ganadas en el
     * torneo. Este método es parte de la implementación de la interfaz
     * {@link Comparable}.
     *
     * @param entrada El jugador con el que se va a comparar.
     * @return Un valor positivo si el jugador actual ha ganado más partidas en
     * el torneo, un valor negativo si ha ganado menos, o 0 si tienen el mismo
     * número de victorias.
     */
    @Override
    public int compareTo(Jugador entrada) {
        //1->mayor 0->igual -1->menor
        if (entrada == null) {
            throw new NullPointerException();
        }

        // Comparar por partidas ganadas en torneo
        if (this.partidasGanadasTorneo > entrada.partidasGanadasTorneo) { //cuando this > entrada 1
            return 1; // El jugador actual ha ganado más
        } else if (this.partidasGanadasTorneo < entrada.partidasGanadasTorneo) {
            return -1; // El jugador comparado ha ganado más
        } else {
            return 0; // Empate
        }

        /*if (this.partidasGanadas > entrada.partidasGanadas) { //cuando this > entrada 1
            return 1;
        } else{
            return -1;
        }

        return 0;*/
    }
    
    public void resetMano() {
        mano.clear();
    }

    public void recibirCarta(Carta carta) {
        mano.add(carta);
    }

    public String mostrarMano() {
        String s_mano="";
        for (Carta carta : mano) {
            s_mano += (carta.toString())+ (", ");
        }
        if (s_mano.length() > 0) {
            return s_mano.substring(0, s_mano.length() - 2); // Pone el string sin la ultima ,
        }else{
            return "Sin cartas";
        }
        
        
    }

    public int calcularPuntaje() {
        int puntaje = 0;
        int ases = 0;

        for (Carta carta : mano) {
            int valor = carta.getValor();
            if (valor == 1) { // As
                ases++;
                puntaje += 11;
            } else if (valor >= 11 && valor <= 13) { // J, Q, K
                puntaje += 10;
            } else {
                puntaje += valor;
            }
        }

        // Ajustar el valor de los ases si el puntaje es mayor a 21
        while (puntaje > 21 && ases > 0) {
            puntaje -= 10; // Convertir un As de 11 a 1
            ases--;
        }

        return puntaje;
    }

    public int decidir() {
        // Lógica simple para decidir: pide carta si el puntaje es menor a 17
        int puntajeActual = calcularPuntaje();
        if (puntajeActual < 17) {
            return 1; // Pedir carta
        } else {
            return 2; // Plantarse
        }
    }

}
