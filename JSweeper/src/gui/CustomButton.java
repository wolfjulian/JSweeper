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
    
    private static javax.swing.ImageIcon FLAG = new javax.swing.ImageIcon("flag.png");
    private static javax.swing.ImageIcon QUESTION = new javax.swing.ImageIcon("question.png");
    private static javax.swing.ImageIcon BOMB = new javax.swing.ImageIcon("bomb.png");
    private static javax.swing.ImageIcon EXPLOSION = new javax.swing.ImageIcon("explosion.png");
    private static javax.swing.ImageIcon CHECKED = new javax.swing.ImageIcon("checked.png");
    
    
    
    public CustomButton (int reihe, int spalte)
    {
        setPreferredSize(new java.awt.Dimension(3,3));
        setSize(new java.awt.Dimension(3,3));
        java.awt.Insets insets = new java.awt.Insets(0,0,0,0);
        this.setMargin(insets);
        setToolTipText("Hier könnte eine Mine sein?!");
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
    
    public boolean markieren()
    {
        if (!istMarkiert)
        {
            setIcon(FLAG);
            istMarkiert = true;
            return true;
        }
        else
        {
            setIcon(null);
            istMarkiert = false;
            return false;
        }
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
    
    
    public void lost()
    {
       setIcon(EXPLOSION);
    }
    
    public void beenden()
    {
        if (istMine && istMarkiert)
            setIcon(CHECKED);
        else if (istMine)
            setIcon(BOMB);
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