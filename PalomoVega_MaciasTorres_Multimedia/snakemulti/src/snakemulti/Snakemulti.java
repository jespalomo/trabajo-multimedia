/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//Autores: Jesús Manuel Palomo Vega y Celia Macías Torres.


package snakemulti;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;


import java.applet.AudioClip;



/**
 *
 * @author Jesús
 */
public class Snakemulti implements ActionListener, KeyListener{

    public static Snakemulti serpi;
    public JFrame jframe;
    public panel panel;
    public Timer reloj = new Timer(20, this);
    public AudioClip clip;
   

    public ArrayList<Point> partes = new ArrayList<Point>();
    public static int ARRIBA = 0, ABAJO = 1, IZQ = 2, DER = 3, SCALE = 10;
    public int ticks = 0, direccion = DER, puntuacion, longitudSerpi = 15, tiempo;

    public Point bocaSerpi, bola;
    public Random rand;
    public boolean terminado = false, pausado;
    public Dimension dimension;

    public Snakemulti() {
        dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Snake Trabajo Multimedia");
        jframe.setVisible(true);
        jframe.setSize(805, 700);
        jframe.setResizable(false);
        jframe.setLocation(dimension.width / 2 - jframe.getWidth() / 2, dimension.height / 2 - jframe.getHeight() / 2);
        jframe.add(panel = new panel());
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addKeyListener(this);
        arrancarPartida();
    }
//Inicializamos los atributos por defecto de la partida y ejecutamos la pista musical de fondo. 
    public void arrancarPartida() {
        sonido("fondo");
        terminado = false;
        pausado = false;
        tiempo = 0;
        puntuacion = 0;
        longitudSerpi = 14;
        ticks = 0;
        direccion = ABAJO;
        bocaSerpi = new Point(0, -1);
        rand = new Random();
        partes.clear();
        bola = new Point(rand.nextInt(79), rand.nextInt(64));
        reloj.start();
    }
//Funcion que dependiendo del movimiento que esté siguiendo la serpiente comprobará los parámetros de cada
//casilla a la que se desplace para comprobar que no excede el rango del juego o no se "enrosca" como se
//comprueba en una funcion auxiliar. 
    @Override
    public void actionPerformed(ActionEvent arg0) {
        panel.repaint();
        ++ticks;
        if (ticks % 2 == 0 && bocaSerpi != null && !terminado && !pausado) {
            ++tiempo;
            partes.add(new Point(bocaSerpi.x, bocaSerpi.y));
            if (direccion == ARRIBA) {
                if (bocaSerpi.y - 1 >= 0 && noEnroscado(bocaSerpi.x, bocaSerpi.y - 1)) {
                    bocaSerpi = new Point(bocaSerpi.x, bocaSerpi.y - 1);
                } else {
                    terminado = true;
                  sonido("gameover");
                }
            }
            if (direccion == ABAJO) {
                if (bocaSerpi.y + 1 < 67 && noEnroscado(bocaSerpi.x, bocaSerpi.y + 1)) {
                    bocaSerpi = new Point(bocaSerpi.x, bocaSerpi.y + 1);
                } else {
                    terminado = true;
                    sonido("gameover");
                }
            }
            if (direccion == IZQ) {
                if (bocaSerpi.x - 1 >= 0 && noEnroscado(bocaSerpi.x - 1, bocaSerpi.y)) {
                    bocaSerpi = new Point(bocaSerpi.x - 1, bocaSerpi.y);
                } else {
                    terminado = true;
                    sonido("gameover");
                }
            }
            if (direccion == DER) {
                if (bocaSerpi.x + 1 < 80 && noEnroscado(bocaSerpi.x + 1, bocaSerpi.y)) {
                    bocaSerpi = new Point(bocaSerpi.x + 1, bocaSerpi.y);
                } else {
                    sonido("gameover");
                    terminado = true;
                }
            }
            //Comprobacion de que la longitud real de la serpiente cuadre con la puntuación en "longitud"
            if (partes.size() > longitudSerpi) {
                partes.remove(0);
            }
            //Cuando la situación de la cabeza de la serpiente coincide con la de la "bola", sumamos un punto
            //a la puntuación y aumentamos la longitud de la serpiente a la vez que reproducimos un clip de
            //sonido indicándolo. Hacemos que la bola reaparezca en un lugar aleatorio del mapa.
            if (bola != null) {
                if (bocaSerpi.equals(bola)) {
                    sonido("comebola");
                    ++puntuacion;
                    ++longitudSerpi;
                    bola.setLocation(rand.nextInt(79), rand.nextInt(66));
                }
            }
        }
    }
//Función que comprueba con un for extendido que la casilla donde queremos que se mueva la cabeza de la
//serpiente no está ocupada por una parte del cuerpo de la misma.
    public boolean noEnroscado(int x, int y) {
        for (Point punto : partes) {
            if (punto.equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        serpi = new Snakemulti();
    }
//Función que organiza y ejecuta el movimiento de la serpiente en funcion de la entrada por teclado
//restringiendo el movimiento opuesto al actual.
    @Override
    public void keyPressed(KeyEvent p) {
        int i = p.getKeyCode();
        if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direccion != DER) {
            direccion = IZQ;
        }
        if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direccion != IZQ) {
            direccion = DER;
        }
        if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direccion != ABAJO) {
            direccion = ARRIBA;
        }
        if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direccion != ARRIBA) {
            direccion = ABAJO;
        }
        if (i == KeyEvent.VK_SPACE) {
            if (terminado) {
                arrancarPartida();
            } else {
                pausado = !pausado;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent p) {
    }

    @Override
    public void keyTyped(KeyEvent p) {
    }

//Funcion que nos permitirá reproducir clips de sonido que será llamada cada vez que se produzca un evento
//en el juego.
    public void sonido(String archivo){
            clip=java.applet.Applet.newAudioClip(getClass().getResource("/Sonidos/"+archivo+".wav"));
            clip.play();
    }

}
