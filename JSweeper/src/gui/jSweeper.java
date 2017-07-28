package gui;

/**
 * Created by WolfJulian@DV-Schulen on 19.07.2017.
 */

import controller.Controller;

public class jSweeper
{
    private javax.swing.JPanel jSweeperWindow;
    private javax.swing.JButton start;
    private javax.swing.JTextField breiteTF;
    private javax.swing.JTextField hoeheTF;
    private javax.swing.JTextField minenTF;
    private javax.swing.JPanel board;
    private Controller controller;
    private static javax.swing.JFrame frame;
    
    
    public jSweeper()
    {
        start.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                leereBoard();
                erzeugeBoard();
            }
        });
        breiteTF.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                hoeheTF.requestFocus();
            }
        });
        hoeheTF.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                minenTF.requestFocus();
            }
        });
        minenTF.addActionListener(new java.awt.event.ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                start.requestFocus();
            }
        });
    }
    
    public static void main(String[] args)
    {
        frame = new javax.swing.JFrame("jSweeper");
        frame.setContentPane(new jSweeper().jSweeperWindow);
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    private void erzeugeBoard()
    {
        int breite;
        int hoehe;
        int minen;
        try
        {
            breite = Integer.valueOf(breiteTF.getText());
            hoehe = Integer.valueOf(hoeheTF.getText());
            minen = Integer.valueOf(minenTF.getText());
            controller = new Controller(this,breite, hoehe,minen);
            disableTF();

            frame.setPreferredSize(new java.awt.Dimension(1920,1080));
            frame.pack();
            frame.revalidate();
            frame.repaint();
        }
        catch (Exception e)
        {
            javax.swing.JOptionPane.showMessageDialog(null,"Bitte nur sinnvolle Werte eingeben!");
        }
    }
    
    public void leereBoard()
    {
        board.removeAll();
        controller = null;
        frame.setPreferredSize(new java.awt.Dimension(693,60));
        frame.pack();
        frame.revalidate();
        frame.repaint();
        enableTF();
    }
    
    public void zeigeBrett(int reihen, int spalten, java.util.ArrayList<gui.CustomButton> spielfeld)
    {
        board.removeAll();
        board.setLayout(new java.awt.GridLayout(reihen, spalten, 0,0));
        
        for (CustomButton c:spielfeld)
        {
            board.add(c);
        }
    }
    
    public void disableTF()
    {
        breiteTF.setEnabled(false);
        hoeheTF.setEnabled(false);
        minenTF.setEnabled(false);
        start.setEnabled(false);
    }
    public void enableTF()
    {
        breiteTF.setEnabled(true);
        hoeheTF.setEnabled(true);
        minenTF.setEnabled(true);
        start.setEnabled(true);
    }
    
}
