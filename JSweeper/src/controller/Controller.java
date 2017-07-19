package controller;

/**
 * Created by WolfJulian@DV-Schulen on 19.07.2017.
 */

import gui.CustomButton;
import gui.jSweeper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class Controller {
    private final jSweeper gui;
    private ArrayList <CustomButton> spielfeld = new ArrayList<>();
    private int reihen;
    private int spalten;
    private int minen;
    private int markiert=0;
    private BoardListener bl;
    
    public Controller (jSweeper gui, int reihenAnzahl, int spaltenAnzahl, int minenAnzahl)
    {
        this.gui = gui;
        reihen = reihenAnzahl;
        spalten = spaltenAnzahl;
        minen = minenAnzahl;
        bl = new BoardListener();
        initializeBrett();
        verteileMinen();
        setzeAnzahlUmgebendeMinen();
        
    }
    
    private void setzeAnzahlUmgebendeMinen()
    {
        for (CustomButton c : spielfeld)
        {
            int r = c.getReihe();
            int s = c.getSpalte();
            
            for (CustomButton u : spielfeld)
            {
                int uR = u.getReihe();
                int uS = u.getSpalte();
                
                if ( ((uR >= (r - 1)) && (uR  <= (r + 1))) && ((uS >= (s - 1)) && (uS <= (s + 1))))
                {
                    if (u.getMine())
                    {
                        c.erhoeheUmgebendeMinen();
                        System.out.println("Mine C:" + c.toString() + " U: "+ u.toString());
                    }
                }
            }
        }
    }
    
    private void initializeBrett()
    {
        for (int r = 0; r < reihen; r++)
        {
            for (int s = 0; s < spalten; s++)
            {
                CustomButton customButton = new CustomButton(r,s);
                spielfeld.add(customButton);
                customButton.addMouseListener(bl);
            }
        }
        gui.zeigeBrett(reihen, spalten, spielfeld);
    }
    
    private void verteileMinen()
    {
        int verteilteMinen = 0;
        while (verteilteMinen < minen)
        {
            CustomButton customButton = spielfeld.get(random());
            if (!customButton.getMine())
            {
                customButton.setMine(true);
                verteilteMinen++;
                System.out.println("Mine auf: " + customButton.toString() + " positioniert!");
            }
        }
    }
    private void gameOver()
    {
        gui.enableTF();
        JOptionPane.showMessageDialog(null, "G A M E    O V E R");
        spielfeld.removeAll(spielfeld);
        bl = null;
    }
    
    private int random()
    {
        int max = reihen * spalten;
        return (int) (0 + Math.round( Math.random() * (max -0) ));
    }
    
    public class BoardListener implements java.awt.event.MouseListener
    {
        @Override
        public void mousePressed(java.awt.event.MouseEvent e)
        {
            if (SwingUtilities.isLeftMouseButton(e))
            {
                //CustomButton identifizieren
                for (CustomButton c:spielfeld)
                {
                    if (e.getSource() == c)
                    {
                        int reihe = c.getReihe();
                        int spalte = c.getSpalte();
                        pruefeFeld(c, reihe, spalte);
                        System.out.println(c.toString());
                        return;
                    }
                }
            }
            if (SwingUtilities.isRightMouseButton(e))
            {
                //CustomButton identifizieren
                for (CustomButton c:spielfeld)
                {
                    if (e.getSource() == c)
                    {
                        int reihe = c.getReihe();
                        int spalte = c.getSpalte();
                        c.markieren();
                        System.out.println(c.toString());
                        return;
                    }
                }
            }
            pruefeGewinn();
        }
    
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e)
        {}
        
        @Override
        public void mouseReleased(java.awt.event.MouseEvent e)
        {}
    
        @Override
        public void mouseEntered(java.awt.event.MouseEvent e)
        {}
    
        @Override
        public void mouseExited(java.awt.event.MouseEvent e)
        {}
    
        private void pruefeGewinn()
        {
            int gefundeneMinen = 0;
            int aufgedeckteFelder = 0;
            
            
            for (CustomButton c:spielfeld)
            {
                if (c.getMine() && (c.getIstMarkiert()))
                    gefundeneMinen++;
                if (c.getAufgedeckt())
                {
                    aufgedeckteFelder++;
                }
            }
    
            // Alle Minen wurden markiert
            if (gefundeneMinen == minen && minen == markiert)
            {
                gewonnen();
            }
            
            // Alle Felder ohne Minen wurden aufgedeckt
            if (aufgedeckteFelder == (reihen * spalten - minen))
            {
            gewonnen();
            }
            
        }
    
        private void gewonnen()
        {
            aufdecken();
            javax.swing.JOptionPane.showMessageDialog(null, "G E W O N N E N ! ! !");
        }
    
        private void aufdecken()
        {
            for (CustomButton c:spielfeld)
            {
                c.beenden();
            }
        }
        
        private void pruefeFeld (CustomButton c, int r, int s)
        {
            if (!c.getMine())
            {
                c.aufdecken();
                if (c.getUmgebendeMinen() == 0)
                {
                    for (CustomButton nachbarfeld : spielfeld)
                    {
                        int nR = nachbarfeld.getReihe();
                        int nS = nachbarfeld.getSpalte();
                        if (((nR == r - 1) && (nS == s)) || ((nR == r + 1) && (nS == s)) || ((nS == s - 1) && (nR == r) || (nS == s + 1) && (nR == r)))
                        {
                            if (!nachbarfeld.getMine() && !nachbarfeld.getAufgedeckt() && nachbarfeld.getUmgebendeMinen() == 0)
                            {
                                pruefeFeld(nachbarfeld, nachbarfeld.getReihe(), nachbarfeld.getSpalte());
                            } else if (!nachbarfeld.getMine() && !nachbarfeld.getAufgedeckt())
                            {
                                nachbarfeld.aufdecken();
                            }
                        }
                    }
                }
            }
            else
            {
                for (CustomButton customButton: spielfeld)
                {
                    customButton.beenden();
                }
                c.lost();
                gameOver();
            }
        }

    }
}

