package pizza;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PizzaGUI extends JFrame
{

	private JPanel contentPane;
	private JLabel lblPizza;
	private JLabel lblGre;
	private JLabel lblExtras;
	private JList bestellung;
	private JButton addButton;
	private JButton deleteButton;
	private JButton changeButton;
	private JLabel lblGesamtpreis;
	private JLabel bill;
	private JButton btnHardcopy;
	private JButton btnSpeichern;
	private JButton btnLaden;
	private JComboBox changePizza;
	private JComboBox changeSize;
	private JComboBox changeExtra;
	private JLabel extra;
	private JLabel pizza;
	private JButton button;
	private JLabel error;
	private Component glue;

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
					PizzaGUI frame = new PizzaGUI();
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
	public PizzaGUI()
	{
		setTitle("FI11 - Pizzabestellung");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 343);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, -48, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_lblPizza = new GridBagConstraints();
		gbc_lblPizza.anchor = GridBagConstraints.WEST;
		gbc_lblPizza.insets = new Insets(0, 0, 5, 5);
		gbc_lblPizza.gridx = 0;
		gbc_lblPizza.gridy = 0;
		contentPane.add(getLblPizza(), gbc_lblPizza);
		GridBagConstraints gbc_changePizza = new GridBagConstraints();
		gbc_changePizza.gridwidth = 3;
		gbc_changePizza.insets = new Insets(0, 0, 5, 5);
		gbc_changePizza.fill = GridBagConstraints.HORIZONTAL;
		gbc_changePizza.gridx = 1;
		gbc_changePizza.gridy = 0;
		contentPane.add(getChangePizza(), gbc_changePizza);
		GridBagConstraints gbc_lblGre = new GridBagConstraints();
		gbc_lblGre.anchor = GridBagConstraints.EAST;
		gbc_lblGre.insets = new Insets(0, 0, 5, 5);
		gbc_lblGre.gridx = 5;
		gbc_lblGre.gridy = 0;
		contentPane.add(getLblGre(), gbc_lblGre);
		GridBagConstraints gbc_changeSize = new GridBagConstraints();
		gbc_changeSize.insets = new Insets(0, 0, 5, 0);
		gbc_changeSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_changeSize.gridx = 6;
		gbc_changeSize.gridy = 0;
		contentPane.add(getChangeSize(), gbc_changeSize);
		GridBagConstraints gbc_lblExtras = new GridBagConstraints();
		gbc_lblExtras.anchor = GridBagConstraints.WEST;
		gbc_lblExtras.insets = new Insets(0, 0, 5, 5);
		gbc_lblExtras.gridx = 0;
		gbc_lblExtras.gridy = 1;
		contentPane.add(getLblExtras(), gbc_lblExtras);
		GridBagConstraints gbc_changeExtra = new GridBagConstraints();
		gbc_changeExtra.gridwidth = 3;
		gbc_changeExtra.insets = new Insets(0, 0, 5, 5);
		gbc_changeExtra.fill = GridBagConstraints.HORIZONTAL;
		gbc_changeExtra.gridx = 1;
		gbc_changeExtra.gridy = 1;
		contentPane.add(getChangeExtra(), gbc_changeExtra);
		GridBagConstraints gbc_extra = new GridBagConstraints();
		gbc_extra.insets = new Insets(0, 0, 5, 5);
		gbc_extra.gridx = 4;
		gbc_extra.gridy = 1;
		contentPane.add(getExtra(), gbc_extra);
		GridBagConstraints gbc_pizza = new GridBagConstraints();
		gbc_pizza.insets = new Insets(0, 0, 5, 0);
		gbc_pizza.gridx = 6;
		gbc_pizza.gridy = 1;
		contentPane.add(getPizza(), gbc_pizza);
		GridBagConstraints gbc_bestellung = new GridBagConstraints();
		gbc_bestellung.gridheight = 8;
		gbc_bestellung.gridwidth = 6;
		gbc_bestellung.insets = new Insets(0, 0, 5, 5);
		gbc_bestellung.fill = GridBagConstraints.BOTH;
		gbc_bestellung.gridx = 0;
		gbc_bestellung.gridy = 2;
		contentPane.add(getBestellung(), gbc_bestellung);
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addButton.insets = new Insets(0, 0, 5, 0);
		gbc_addButton.gridx = 6;
		gbc_addButton.gridy = 2;
		contentPane.add(getAddButton(), gbc_addButton);
		GridBagConstraints gbc_deleteButton = new GridBagConstraints();
		gbc_deleteButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_deleteButton.insets = new Insets(0, 0, 5, 0);
		gbc_deleteButton.gridx = 6;
		gbc_deleteButton.gridy = 3;
		contentPane.add(getDeleteButton(), gbc_deleteButton);
		GridBagConstraints gbc_changeButton = new GridBagConstraints();
		gbc_changeButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_changeButton.insets = new Insets(0, 0, 5, 0);
		gbc_changeButton.gridx = 6;
		gbc_changeButton.gridy = 4;
		contentPane.add(getChangeButton(), gbc_changeButton);
		GridBagConstraints gbc_btnSpeichern = new GridBagConstraints();
		gbc_btnSpeichern.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSpeichern.insets = new Insets(0, 0, 5, 0);
		gbc_btnSpeichern.gridx = 6;
		gbc_btnSpeichern.gridy = 5;
		contentPane.add(getBtnSpeichern(), gbc_btnSpeichern);
		GridBagConstraints gbc_btnLaden = new GridBagConstraints();
		gbc_btnLaden.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLaden.insets = new Insets(0, 0, 5, 0);
		gbc_btnLaden.gridx = 6;
		gbc_btnLaden.gridy = 6;
		contentPane.add(getBtnLaden(), gbc_btnLaden);
		GridBagConstraints gbc_btnHardcopy = new GridBagConstraints();
		gbc_btnHardcopy.insets = new Insets(0, 0, 5, 0);
		gbc_btnHardcopy.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHardcopy.gridx = 6;
		gbc_btnHardcopy.gridy = 7;
		contentPane.add(getBtnHardcopy(), gbc_btnHardcopy);
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.fill = GridBagConstraints.HORIZONTAL;
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 6;
		gbc_button.gridy = 8;
		contentPane.add(getButton(), gbc_button);
		GridBagConstraints gbc_glue = new GridBagConstraints();
		gbc_glue.insets = new Insets(0, 0, 5, 0);
		gbc_glue.gridx = 6;
		gbc_glue.gridy = 9;
		contentPane.add(getGlue(), gbc_glue);
		GridBagConstraints gbc_lblGesamtpreis = new GridBagConstraints();
		gbc_lblGesamtpreis.anchor = GridBagConstraints.WEST;
		gbc_lblGesamtpreis.insets = new Insets(0, 0, 0, 5);
		gbc_lblGesamtpreis.gridx = 0;
		gbc_lblGesamtpreis.gridy = 10;
		contentPane.add(getLblGesamtpreis(), gbc_lblGesamtpreis);
		GridBagConstraints gbc_error = new GridBagConstraints();
		gbc_error.fill = GridBagConstraints.HORIZONTAL;
		gbc_error.gridwidth = 4;
		gbc_error.insets = new Insets(0, 0, 0, 5);
		gbc_error.gridx = 2;
		gbc_error.gridy = 10;
		contentPane.add(getError(), gbc_error);
		GridBagConstraints gbc_bill = new GridBagConstraints();
		gbc_bill.insets = new Insets(0, 0, 0, 5);
		gbc_bill.gridx = 3;
		gbc_bill.gridy = 10;
		contentPane.add(getBill(), gbc_bill);
	}

	private JLabel getLblPizza()
	{
		if (lblPizza == null)
		{
			lblPizza = new JLabel("Pizza:");
		}
		return lblPizza;
	}

	private JLabel getLblGre()
	{
		if (lblGre == null)
		{
			lblGre = new JLabel("Gr\u00F6\u00DFe:");
		}
		return lblGre;
	}

	private JLabel getLblExtras()
	{
		if (lblExtras == null)
		{
			lblExtras = new JLabel("Extras:");
		}
		return lblExtras;
	}

	private JList getBestellung()
	{
		if (bestellung == null)
		{
			bestellung = new JList();
		}
		return bestellung;
	}

	private JButton getAddButton()
	{
		if (addButton == null)
		{
			addButton = new JButton("Hinzuf\u00FCgen");
		}
		return addButton;
	}

	private JButton getDeleteButton()
	{
		if (deleteButton == null)
		{
			deleteButton = new JButton("Entfernen");
		}
		return deleteButton;
	}

	private JButton getChangeButton()
	{
		if (changeButton == null)
		{
			changeButton = new JButton("\u00C4ndern");
		}
		return changeButton;
	}

	private JLabel getLblGesamtpreis()
	{
		if (lblGesamtpreis == null)
		{
			lblGesamtpreis = new JLabel("Gesamtpreis:");
		}
		return lblGesamtpreis;
	}

	private JLabel getBill()
	{
		if (bill == null)
		{
			bill = new JLabel("");
		}
		return bill;
	}

	private JButton getBtnHardcopy()
	{
		if (btnHardcopy == null)
		{
			btnHardcopy = new JButton("Hardcopy");
		}
		return btnHardcopy;
	}

	private JButton getBtnSpeichern()
	{
		if (btnSpeichern == null)
		{
			btnSpeichern = new JButton("Speichern");
		}
		return btnSpeichern;
	}

	private JButton getBtnLaden()
	{
		if (btnLaden == null)
		{
			btnLaden = new JButton("Laden");
		}
		return btnLaden;
	}

	private JComboBox getChangePizza()
	{
		if (changePizza == null)
		{
			changePizza = new JComboBox();
		}
		return changePizza;
	}

	private JComboBox getChangeSize()
	{
		if (changeSize == null)
		{
			changeSize = new JComboBox();
		}
		return changeSize;
	}

	private JComboBox getChangeExtra()
	{
		if (changeExtra == null)
		{
			changeExtra = new JComboBox();
		}
		return changeExtra;
	}

	private JLabel getExtra()
	{
		if (extra == null)
		{
			extra = new JLabel("");
		}
		return extra;
	}

	private JLabel getPizza()
	{
		if (pizza == null)
		{
			pizza = new JLabel("");
		}
		return pizza;
	}

	private JButton getButton()
	{
		if (button == null)
		{
			button = new JButton("Drucken");
		}
		return button;
	}

	private JLabel getError()
	{
		if (error == null)
		{
			error = new JLabel("");
		}
		return error;
	}

	private Component getGlue()
	{
		if (glue == null)
		{
			glue = Box.createGlue();
		}
		return glue;
	}
}
