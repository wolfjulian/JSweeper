package chat;

import java.awt.Button;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ClientGUI extends JFrame implements Protokoll
{
	private JPanel contentPane;
	private Choice serverChoice;
	private Button connectButton;
	private Button disconnectButton;
	private List chatListe;
	private TextField nachrichtTF;
	private Button sendenButton;
	private List teilnehmerListe;
	private Button erstelleRaumButton;
	private TextField nicknameTF;
	private Button changeNickButton;
	private TextField raumTF;
	private Choice raumChoice;
	private Client aClient;
	private Label label;
	private Button hinzufuegenButton;
	private Button entfernenButton;
	private TreeSet<String> raumListe;

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
					ClientGUI frame = new ClientGUI();
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
	public ClientGUI()
	{
		setTitle("Chat-Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		contentPane.add(getLabel(), gbc_label);
		GridBagConstraints gbc_serverChoice = new GridBagConstraints();
		gbc_serverChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_serverChoice.insets = new Insets(0, 0, 5, 5);
		gbc_serverChoice.gridx = 1;
		gbc_serverChoice.gridy = 0;
		contentPane.add(getServerChoice(), gbc_serverChoice);
		GridBagConstraints gbc_connectButton = new GridBagConstraints();
		gbc_connectButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_connectButton.insets = new Insets(0, 0, 5, 5);
		gbc_connectButton.gridx = 2;
		gbc_connectButton.gridy = 0;
		contentPane.add(getConnectButton(), gbc_connectButton);
		GridBagConstraints gbc_disconnectButton = new GridBagConstraints();
		gbc_disconnectButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_disconnectButton.insets = new Insets(0, 0, 5, 0);
		gbc_disconnectButton.gridx = 3;
		gbc_disconnectButton.gridy = 0;
		contentPane.add(getDisconnectButton(), gbc_disconnectButton);
		GridBagConstraints gbc_nicknameTF = new GridBagConstraints();
		gbc_nicknameTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nicknameTF.insets = new Insets(0, 0, 5, 5);
		gbc_nicknameTF.gridx = 0;
		gbc_nicknameTF.gridy = 1;
		contentPane.add(getNicknameTF(), gbc_nicknameTF);
		GridBagConstraints gbc_changeNickButton = new GridBagConstraints();
		gbc_changeNickButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_changeNickButton.insets = new Insets(0, 0, 5, 5);
		gbc_changeNickButton.gridx = 1;
		gbc_changeNickButton.gridy = 1;
		contentPane.add(getChangeNickButton(), gbc_changeNickButton);
		GridBagConstraints gbc_chatListe = new GridBagConstraints();
		gbc_chatListe.fill = GridBagConstraints.BOTH;
		gbc_chatListe.gridwidth = 3;
		gbc_chatListe.insets = new Insets(0, 0, 5, 5);
		gbc_chatListe.gridx = 0;
		gbc_chatListe.gridy = 2;
		contentPane.add(getChatListe(), gbc_chatListe);
		GridBagConstraints gbc_teilnehmerListe = new GridBagConstraints();
		gbc_teilnehmerListe.fill = GridBagConstraints.VERTICAL;
		gbc_teilnehmerListe.insets = new Insets(0, 0, 5, 0);
		gbc_teilnehmerListe.gridx = 3;
		gbc_teilnehmerListe.gridy = 2;
		contentPane.add(getTeilnehmerListe(), gbc_teilnehmerListe);
		GridBagConstraints gbc_nachrichtTF = new GridBagConstraints();
		gbc_nachrichtTF.gridwidth = 2;
		gbc_nachrichtTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nachrichtTF.insets = new Insets(0, 0, 5, 5);
		gbc_nachrichtTF.gridx = 0;
		gbc_nachrichtTF.gridy = 3;
		contentPane.add(getNachrichtTF(), gbc_nachrichtTF);
		GridBagConstraints gbc_sendenButton = new GridBagConstraints();
		gbc_sendenButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_sendenButton.insets = new Insets(0, 0, 5, 5);
		gbc_sendenButton.gridx = 2;
		gbc_sendenButton.gridy = 3;
		contentPane.add(getSendenButton(), gbc_sendenButton);
		GridBagConstraints gbc_hinzufuegenButton = new GridBagConstraints();
		gbc_hinzufuegenButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_hinzufuegenButton.insets = new Insets(0, 0, 5, 0);
		gbc_hinzufuegenButton.gridx = 3;
		gbc_hinzufuegenButton.gridy = 4;
		contentPane.add(getHinzufuegenButton(), gbc_hinzufuegenButton);
		GridBagConstraints gbc_raumTF = new GridBagConstraints();
		gbc_raumTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_raumTF.insets = new Insets(0, 0, 0, 5);
		gbc_raumTF.gridx = 0;
		gbc_raumTF.gridy = 5;
		contentPane.add(getRaumTF(), gbc_raumTF);
		GridBagConstraints gbc_erstelleRaumButton = new GridBagConstraints();
		gbc_erstelleRaumButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_erstelleRaumButton.insets = new Insets(0, 0, 0, 5);
		gbc_erstelleRaumButton.gridx = 1;
		gbc_erstelleRaumButton.gridy = 5;
		contentPane.add(getErstelleRaumButton(), gbc_erstelleRaumButton);
		GridBagConstraints gbc_raumChoice = new GridBagConstraints();
		gbc_raumChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_raumChoice.insets = new Insets(0, 0, 0, 5);
		gbc_raumChoice.gridx = 2;
		gbc_raumChoice.gridy = 5;
		contentPane.add(getRaumChoice(), gbc_raumChoice);
		GridBagConstraints gbc_entfernenButton = new GridBagConstraints();
		gbc_entfernenButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_entfernenButton.gridx = 3;
		gbc_entfernenButton.gridy = 5;
		contentPane.add(getEntfernenButton(), gbc_entfernenButton);
		initialize();
	}

	private void initialize()
	{
		serverChoice.add("127.0.0.1");
		raumListe = new TreeSet<String>();
		switchButton();
	}

	private void verbinde()
	{
		if (aClient == null)
		{
			// zu Testzwecken:
			String ip = "127.0.0.1";
			int port = 8008;
			String nick = nicknameTF.getText();

			if (!nick.equals(""))
			{
				try
				{
					aClient = new Client(this, nick);
					chatListe.removeAll();
					aClient.verbinde(ip, port, nick);
					aClient.start();
				}
				catch (UnknownHostException e)
				{
					zeigeNachricht("Verbindung nicht möglich: Unbekannte IP");
				}
				catch (IOException e)
				{
					zeigeNachricht("Fehler beim Verbindungsaufbau.");
					aClient = null;
					// switchButton();
				}
			}
			else
			{
				zeigeNachricht("Bitte Nickname angeben!");
				switchButton();
			}
		}
	}

	private void trenne()
	{
		aClient.sendeNachricht(LOGOUT);
	}

	protected void beende()
	{
		getChatListe().removeAll();
		getRaumChoice().removeAll();
		getTeilnehmerListe().removeAll();
		zeigeNachricht("VERBINDUNG BEENDET!");
		aClient = null;
	}

	protected void switchButton()
	{
		if (aClient != null && aClient.isVerbunden())
		{
			disconnectButton.setEnabled(true);
			connectButton.setEnabled(false);
		}
		else
		{
			disconnectButton.setEnabled(false);
			connectButton.setEnabled(true);
		}
	}

	private void sendeNachricht()
	{
		String s = getNachrichtTF().getText();
		if (!s.equals("") && pruefeVerbindung())
		{
			aClient.sendeNachricht(MESSAGE + SEPARATOR + PUBLIC + SEPARATOR + s);
		}
		getNachrichtTF().setText("");

	}

	private void aendereNick()
	{
		String s = nicknameTF.getText();
		if (!s.equals("") && pruefeVerbindung())
		{
			aClient.aendereNick(s);
		}

	}

	public void updateTeilnehmer(TreeSet<String> teilnehmerList)
	{
		teilnehmerListe.removeAll();
		for (String s : teilnehmerList)
		{
			teilnehmerListe.add(s);
		}

	}

	protected void zeigeNachricht(String s)
	{
		chatListe.add(s);
		chatListe.select(chatListe.getItemCount() - 1);
	}

	private void erstelleRaum()
	{
		String s = raumTF.getText();
		if (s.equals(""))

		{
			zeigeNachricht("Bitte Raumnamen angeben!");
		}
		else if (pruefeVerbindung())
		{
			aClient.erstelleRaum(s);
		}
	}

	private boolean pruefeVerbindung()
	{
		if (aClient == null)
		{
			zeigeNachricht("Zuerst mit einem Server verbinden!");
			return false;
		}
		return true;
	}

	protected void ergaenzeRaum(String raumname)
	{
		raumListe.add(raumname);
		updateRaumListe();
	}

	protected void entferneRaum(String raumname)
	{
		raumListe.remove(raumname);
		updateRaumListe();
	}

	private void updateRaumListe()
	{
		getRaumChoice().removeAll();
		for (String s : raumListe)
		{
			getRaumChoice().addItem(s);
		}
	}

	private void addToPrivate()
	{
		aClient.sendeNachricht(ADDNICK + SEPARATOR + getRaumChoice().getItem(getRaumChoice().getSelectedIndex())
				+ SEPARATOR + getTeilnehmerListe().getItem(getTeilnehmerListe().getSelectedIndex()));
	}

	private void delFromPrivate()
	{
		aClient.sendeNachricht(DELNICK + SEPARATOR + getRaumChoice().getItem(getRaumChoice().getSelectedIndex())
				+ SEPARATOR + getTeilnehmerListe().getItem(getTeilnehmerListe().getSelectedIndex()));
	}

	private Choice getServerChoice()
	{
		if (serverChoice == null)
		{
			serverChoice = new Choice();
		}
		return serverChoice;
	}

	private Button getConnectButton()
	{
		if (connectButton == null)
		{
			connectButton = new Button("anmelden");
			connectButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					verbinde();
				}
			});
		}
		return connectButton;
	}

	private Button getDisconnectButton()
	{
		if (disconnectButton == null)
		{
			disconnectButton = new Button("abmelden");
			disconnectButton.setEnabled(false);
			disconnectButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					trenne();
				}
			});
		}
		return disconnectButton;
	}

	private List getChatListe()
	{
		if (chatListe == null)
		{
			chatListe = new List();
		}
		return chatListe;
	}

	private TextField getNachrichtTF()
	{
		if (nachrichtTF == null)
		{
			nachrichtTF = new TextField();
			nachrichtTF.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					sendeNachricht();
				}
			});
		}
		return nachrichtTF;
	}

	private Button getSendenButton()
	{
		if (sendenButton == null)
		{
			sendenButton = new Button("senden");
			sendenButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					sendeNachricht();
				}
			});
		}
		return sendenButton;
	}

	private List getTeilnehmerListe()
	{
		if (teilnehmerListe == null)
		{
			teilnehmerListe = new List();
		}
		return teilnehmerListe;
	}

	private Button getErstelleRaumButton()
	{
		if (erstelleRaumButton == null)
		{
			erstelleRaumButton = new Button("Raum erstellen");
			erstelleRaumButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					erstelleRaum();
				}
			});
		}
		return erstelleRaumButton;
	}

	private TextField getNicknameTF()
	{
		if (nicknameTF == null)
		{
			nicknameTF = new TextField();
		}
		return nicknameTF;
	}

	private Button getChangeNickButton()
	{
		if (changeNickButton == null)
		{
			changeNickButton = new Button("Nick \u00E4ndern");
			changeNickButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					aendereNick();
				}
			});
		}
		return changeNickButton;
	}

	private TextField getRaumTF()
	{
		if (raumTF == null)
		{
			raumTF = new TextField();
		}
		return raumTF;
	}

	private Choice getRaumChoice()
	{
		if (raumChoice == null)
		{
			raumChoice = new Choice();
		}
		return raumChoice;
	}

	private Label getLabel()
	{
		if (label == null)
		{
			label = new Label("Server ausw\u00E4hlen:");
		}
		return label;
	}

	private Button getHinzufuegenButton()
	{
		if (hinzufuegenButton == null)
		{
			hinzufuegenButton = new Button("hinzuf\u00FCgen");
			hinzufuegenButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					addToPrivate();
				}
			});
		}
		return hinzufuegenButton;
	}

	private Button getEntfernenButton()
	{
		if (entfernenButton == null)
		{
			entfernenButton = new Button("entfernen");
			entfernenButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					delFromPrivate();
				}
			});
		}
		return entfernenButton;
	}

}
