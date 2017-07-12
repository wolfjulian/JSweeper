package gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import figuren.Stein;
import figuren.Einfach;
/**
 * Created by erfeb on 12.07.2017.
 */
public class Brett extends JFrame {
    private JPanel contentPane;
    private JPanel panel;

    private Feld[][] feld = new Feld[10][10];

    private boolean istZugbeginn = true;

    /**
     * Bestimmung welche Farbe am Zug ist 1 = weiß // bin => 00000001 2 =
     * schwarz // bin => 00000010 3 = "beide" // bin => 00000011
     */
    private final int amZugWEISS = 1;
    private final int amZugSCHWARZ = 2;
    private int amZug = amZugWEISS;

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Brett frame = new Brett();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Brett()
    {
        setMinimumSize(new Dimension(500, 500));
        initializeGUI();

        initializeFields();
    }

    private void initializeFields()
    {
        getContentPane().removeAll();

        boolean schwarz = true;

        for (int y = 0; y < feld.length; y++)
        {
            for (int x = 0; x < feld[y].length; x++)
            {
                Feld f = new Feld(this, schwarz, x, y);
                f.addActionListener(fl);
                feld[x][y] = f;

                // Alternativ zur IF - ELSE
                // Ternärer Operator wenn schwarz==true darkGray sonst lightGray
                f.setBackground(schwarz ? Color.darkGray : Color.lightGray);

                if (schwarz)
                {
                    if (y <= 3)
                    {
                        f.setStein(new Einfach(f, false), true);
                    }
                    if (y >= 6)
                    {
                        f.setStein(new Einfach(f, true), true);
                    }
                }

                getPanel().add(f);

                schwarz = !schwarz;
            }
            schwarz = !schwarz;
        }
        contentPane.add(getPanel(), BorderLayout.CENTER);
    }

    private void initializeGUI()
    {
        setTitle("JDame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 414, 262);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.add(getPanel(), BorderLayout.CENTER);
        pack();
    }

    private JPanel getPanel()
    {
        if (panel == null)
        {
            panel = new JPanel();
            panel.setLayout(new GridLayout(10, 10, 0, 0));
        }
        return panel;
    }

    public boolean getZugbeginn()
    {
        return istZugbeginn;
    }

    public void merkeBeginn()
    {
        istZugbeginn = false;
    }

    public boolean pruefeZug(Stein stein, Feld ziel)
    {
        // Zielfeld besetzt
        if (ziel.getStein() != null)
        {
            return false;
        }

        int xSt = stein.getFeld().getZeile();
        int ySt = stein.getFeld().getSpalte();
        int xZ = ziel.getZeile();
        int yZ = ziel.getSpalte();

        System.out.println("xSt:" + xSt + " ySt:" + ySt + " xZ:" + xZ + " yZ:" + yZ);

        int abstandX = xZ - xSt;
        int abstandY = yZ - ySt;

        int richtungX = abstandX > 0 ? 1 : -1;
        int richtungY = abstandY > 0 ? 1 : -1;

        System.out.println("abstandX:" + abstandX + " abstandY:" + abstandY + " richtungX:" + richtungX + " richtungY:"
                + richtungY);

        // Letztes Feld
        Feld letztesFeld = feld[xZ - richtungX][yZ - richtungY];

        // letzter Stein
        Stein letzterStein;
        letzterStein = letztesFeld.getStein();

        // Zug länger als 1 Feld
        if (Math.abs(abstandX) > 1)
        {
            // Stein eigener Farbe darf nicht übersprungen werden
            if ((letzterStein != null) && ((letzterStein.getSchwarz()) == (stein.getSchwarz())))
                return false;
            // Einfacher Stein: leere Felder dürfen nicht übersprungen werden
            if ((letzterStein == null) && (stein.getClass().getCanonicalName().equals("figuren.Einfach")))
                return false;
            // Zug länger als 2 Felder
            if (Math.abs(abstandX) > 2)
            {
                int z = xSt + richtungX;
                int sp = ySt + richtungY;
                // Erstes bis vorletztes Feld:
                for (int i = 0; i < Math.abs(abstandX) - 2; i++)
                {
                    // Feld besetzt
                    if (feld[z][sp].getStein() != null)
                        return false;
                    z += richtungX;
                    sp += richtungY;
                }
                System.out.println("Z: " + letztesFeld.getZeile() + "S: " + letztesFeld.getSpalte());
            }
            letztesFeld.entferneStein();
        }
        return true;
    }

    public void merkeEnde(Stein stein)
    {
        if (stein.getClass().getCanonicalName().equals("figuren.Einfach"))
        {
            amZug = stein.getSchwarz() ? amZugWEISS : amZugSCHWARZ;
        }
        istZugbeginn = true;
    }

    private class FeldListener implements java.awt.event.ActionListener
    {
        Stein st = null;

        @Override
        public void actionPerformed(ActionEvent e)
        {
            findeFeld(e);
        }

        private void findeFeld(ActionEvent e)
        {
            Feld f = null;
            int amZugMarker = amZug;
            for (int y = 0; y < feld.length; y++)
            {
                for (int x = 0; x < feld[y].length; x++)
                {
                    if (e.getSource() == feld[x][y])
                    {
                        f = feld[x][y];
                        break;
                    }
                }
            }
            System.out.println("X:" + f.getZeile() + " Y:" + f.getSpalte());
            if (getZugbeginn())
            {
                if ((st = f.getStein()) != null)
                {
                    if ((st.getSchwarz() && (amZug == amZugSCHWARZ)) || (!st.getSchwarz() && (amZug == amZugWEISS)))
                    {
                        f.entferneStein();
                    }
                }
            }
            else if (st.akzeptiertZug(f) && pruefeZug(st, f))
            {
                f.setStein(st);
                st = f.getStein();
                if (st.getClass().getCanonicalName().equals("figuren.Dame"))
                    amZug = st.getSchwarz() ? amZugWEISS : amZugSCHWARZ;
            }
            else
            {
                st.getFeld().setStein(st);
                amZug = amZugMarker;
            }
        }
    }

    FeldListener fl = new FeldListener();
}
