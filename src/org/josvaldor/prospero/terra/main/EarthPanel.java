 package org.josvaldor.prospero.terra.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;

import org.josvaldor.prospero.terra.Earth;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EarthPanel extends JPanel implements MouseWheelListener, KeyListener, Runnable {
    // constants

    public static final int WIDTH = 360;//1600
    public static final int HEIGHT = 180;//WIDTH / 16 * 9;
    private Earth earth;// = new Earth(new GregorianCalendar());
    private Graphics2D g2;
    int increment = 1;
    int type = Calendar.DATE;
    boolean run = true;
    boolean realTime = true;
    Thread thread = new Thread(this);

    public EarthPanel(){
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addMouseWheelListener(this);
        addKeyListener(this);

    }
    
    public void setEarth(Earth earth){
        this.earth = earth;
//        thread.start();
    }
 
    public void setTime(Calendar c) {
        this.earth.setTime(c);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(WIDTH / 2.0, HEIGHT / 2.0);
        earth.draw(g2d);
        g.dispose();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        Calendar c = this.earth.getTime();
        int notches = e.getWheelRotation();
        if (notches < 0) {
            c.add(this.type, increment);
            this.earth.setTime(c);
            this.realTime = false;

        } else {
            c.add(this.type, -increment);
            this.earth.setTime(c);
            this.realTime = false;
        }
        draw(this.getGraphics());

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'y') {
            this.type = Calendar.DATE;
            this.increment = 365;
        }
        if (e.getKeyChar() == 'm') {
            this.type = Calendar.DATE;
            this.increment = 31;
        }
        if (e.getKeyChar() == 'd') {
            this.type = Calendar.DATE;
            this.increment = 1;
        }
        if (e.getKeyChar() == 'h') {
            this.type = Calendar.HOUR;
            this.increment = 1;
        }
        if (e.getKeyChar() == 's') {
            this.realTime = true;
        }
        
        Calendar c = this.earth.getTime();
        if (e.getKeyCode()==KeyEvent.VK_UP) {
            c.add(this.type, increment);
            this.earth.setTime(c);
            this.realTime = false;

        } 
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            c.add(this.type, -increment);
            this.earth.setTime(c);
            this.realTime = false;
        }
        draw(this.getGraphics());
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        this.increment = 1;
        this.type = Calendar.DATE;
    }

//    @Override
//    public void keyTyped(KeyEvent e) {
//        double scale = this.earth.scale;
//        if (e.getKeyChar() == '+' || e.getKeyChar() == '=') {
//            if (scale < 1.024E-5) {
//                this.earth.setScale(scale * 2);
//            }
//
//        }
//        if (e.getKeyChar() == '-' || e.getKeyChar() == '_') {
//            if (scale > 8.0E-8) {
//                this.earth.setScale(scale / 2);
//            }
//        }
//        draw(this.getGraphics());
//
//    }

    @Override
    public void run() {
        while (this.run) {
            while (this.realTime) {
                try {
                    this.setTime(new GregorianCalendar());
                    sleep(1000);
                    draw(this.getGraphics());
                } catch (InterruptedException ex) {
                    Logger.getLogger(EarthPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(EarthPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
