/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Autores: Jesús Manuel Palomo Vega y Celia Macías Torres.

package snakemulti;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;


/**
 *
 * @author Jesús
 */
public class panel extends JPanel{
    
    @Override
    //Funcion que nos permitirá asignar colores a los diferentes elementos del juego como son la serpiente,
    //la bola, el fondo y el HUD y notificaciones que nos informa de la puntuación, tiempo y longitud y cuando el juego
    //está en pausa o ha terminado, cambiarán cada vez que la puntuación aumente, exceptuando el HUD y las notificaciones.
    protected void paintComponent(Graphics p){
        super.paintComponent(p);
        Snakemulti serpi=Snakemulti.serpi;
        
        //Coloracion fondo
        if(serpi.puntuacion%2==0)
            p.setColor(Color.GREEN);
        else
            p.setColor(Color.WHITE);
        p.fillRect(0, 0, 800, 700);
        
        //Coloracion serpiente
        if(serpi.puntuacion%2==0)
            p.setColor(Color.BLUE);
        else
            p.setColor(Color.GREEN);
        for(Point punto : serpi.partes)
            p.fillRect(punto.x*Snakemulti.SCALE, punto.y*Snakemulti.SCALE, Snakemulti.SCALE, Snakemulti.SCALE);
        p.fillRect(serpi.bocaSerpi.x*Snakemulti.SCALE, serpi.bocaSerpi.y*Snakemulti.SCALE, Snakemulti.SCALE, Snakemulti.SCALE);
        
        //Coloracion bola
        if(serpi.puntuacion%2==0)
            p.setColor(Color.RED);
        else
            p.setColor(Color.BLUE);
        p.fillRect(serpi.bola.x*Snakemulti.SCALE, serpi.bola.y*Snakemulti.SCALE, Snakemulti.SCALE, Snakemulti.SCALE);
        String resultado= "Puntuacion: " + serpi.puntuacion + "  Longitud de la serpiente: " + serpi.longitudSerpi + "  Tiempo: " + serpi.tiempo / 20;
        
        //Coloracion HUD y notificaciones
        p.setColor(Color.BLACK);
        p.drawString(resultado, (int)(getWidth()/2-resultado.length()*2.5f), 10);
        resultado= "Has Perdido.";
        if(serpi.terminado)
            p.drawString(resultado, (int) (getWidth()/2 - resultado.length()*2.5f), (int) serpi.dimension.getHeight()/4);
        resultado= "En pausa.";
        if(serpi.pausado && !serpi.terminado)
            p.drawString(resultado, (int) (getWidth()/2 - resultado.length()*2.5f), (int) serpi.dimension.getHeight()/4);
    }
}
