package gui;

import java.awt.Color;
import figuren.Stein;
import  figuren.Einfach;
import  figuren.Dame;

/**
 * Created by erfeb on 12.07.2017.
 */
public class Feld extends javax.swing.JButton{
    private Brett brett;
    private Stein stein = null;
    private boolean istSchwarz;
    private int zeile;
    private int spalte;

    public Feld(Brett brett, boolean schwarz, int x, int y)
    {
        this.brett = brett;
        istSchwarz = schwarz;
        zeile = x;
        spalte = y;
    }

    public Stein getStein()
    {
        return stein;
    }

    public void setStein(Stein stein)
    {
        setStein(stein, false);
    }

    public void setStein(Stein stein, boolean init)
    {
        if (!init && (stein.getClass().getCanonicalName().equals("figuren.Einfach")) && (spalte == 0 || spalte == 9))
        {
            stein = new Dame(this, stein.getSchwarz());
        }

        this.stein = stein;
        stein.setFeld(this);
        setForeground(stein.getSchwarz() ? Color.black : Color.white);
        this.setFont(new java.awt.Font("Dialog", 1, 14));
        setText(stein.getSymbol());
    }

    public void entferneStein()
    {
        stein = null;
        brett.merkeBeginn();
        setText("");
    }

    public Brett getBrett()
    {
        return brett;
    }

    public int getZeile()
    {
        return zeile;
    }

    public int getSpalte()
    {
        return spalte;
    }
}
