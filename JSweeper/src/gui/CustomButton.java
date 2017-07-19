package gui;

/**
 * Created by WolfJulian@DV-Schulen on 19.07.2017.
 */

public class CustomButton extends javax.swing.JButton
{
    private int reihe;
    private int spalte;
    private boolean istMine = false;
    private boolean istMarkiert = false;
    private int umgebendeMinen = 0;
    private boolean aufgedeckt = false;
    
    public CustomButton (int reihe, int spalte)
    {
        this.setPreferredSize(new java.awt.Dimension(3,3));
        this.reihe = reihe;
        this.spalte = spalte;
        this.setVisible(true);
    }
    
    
    
    public int getReihe()
    {
        return reihe;
    }
    public boolean getIstMarkiert()
    {
        return istMarkiert;
    }
    public void setIstMarkiert (boolean markiert)
    {
        istMarkiert = markiert;
    }
    public int getSpalte()
    {
        return spalte;
    }
    public boolean getMine()
    {
        return istMine;
    }
    public void setMine(boolean mine)
    {
        istMine = mine;
        setText("X");
    }
    public void erhoeheUmgebendeMinen ()
    {
        umgebendeMinen++;
    }
    public int getUmgebendeMinen()
    {
        return umgebendeMinen;
    }
    public boolean getAufgedeckt()
    {
        return aufgedeckt;
    }
    
    public void aufdecken()
    {
        if (!aufgedeckt)
        {
            aufgedeckt = true;
            
            if (istMine)
            {
                setBackground(java.awt.Color.RED);
            }
            if (umgebendeMinen > 0)
            {
                setText(umgebendeMinen + "");
            }
            if (umgebendeMinen == 0)
            {
                setVisible(false);
            }
        }
    }
    
    public void beenden()
    {
        if (istMine)
            setText("*");
        else
            setVisible(false);
    }
    
    public String toString()
    {
        int ausgabeReihe = reihe + 1;
        int ausgabeSpalte = spalte + 1;
        return ("Reihe: " + ausgabeReihe + ", Spalte: " + ausgabeSpalte + " umgebende Minen: " + umgebendeMinen);
    }
    
    
}