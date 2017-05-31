package chat;

import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PrivatChat extends JFrame implements Protokoll
{

	private JPanel contentPane;
	private List chatListe;
	private List teilnehmerListe;
	private TextField nachrichtTF;
	private Button sendenButton;
	private String roomName = "newROOM";
	private Client aClient;
	private TreeSet<String> teilnehmerList = new TreeSet<>();
	private Button verlassenButton;
	private Button remNickButton;
	private Button delAdminButton;
	private Button addAdminButton;
	private Panel panel;

	/**
	 * Create the frame.
	 */

	public PrivatChat(Client client, String roomname)
	{
		this.aClient = client;
		changeName(roomname);
		this.setVisible(true);
		setTitle(PUBLIC + " Raum: " + roomName);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 512, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_chatListe = new GridBagConstraints();
		gbc_chatListe.gridwidth = 4;
		gbc_chatListe.fill = GridBagConstraints.BOTH;
		gbc_chatListe.insets = new Insets(0, 0, 5, 5);
		gbc_chatListe.gridx = 0;
		gbc_chatListe.gridy = 0;
		contentPane.add(getChatListe(), gbc_chatListe);
		GridBagConstraints gbc_teilnehmerListe = new GridBagConstraints();
		gbc_teilnehmerListe.fill = GridBagConstraints.VERTICAL;
		gbc_teilnehmerListe.insets = new Insets(0, 0, 5, 0);
		gbc_teilnehmerListe.gridx = 4;
		gbc_teilnehmerListe.gridy = 0;
		contentPane.add(getTeilnehmerListe(), gbc_teilnehmerListe);
		GridBagConstraints gbc_nachrichtTF = new GridBagConstraints();
		gbc_nachrichtTF.gridwidth = 4;
		gbc_nachrichtTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_nachrichtTF.insets = new Insets(0, 0, 5, 5);
		gbc_nachrichtTF.gridx = 0;
		gbc_nachrichtTF.gridy = 1;
		contentPane.add(getNachrichtTF(), gbc_nachrichtTF);
		GridBagConstraints gbc_sendenButton = new GridBagConstraints();
		gbc_sendenButton.insets = new Insets(0, 0, 5, 0);
		gbc_sendenButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_sendenButton.gridx = 4;
		gbc_sendenButton.gridy = 1;
		contentPane.add(getSendenButton(), gbc_sendenButton);
		GridBagConstraints gbc_addAdminButton = new GridBagConstraints();
		gbc_addAdminButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addAdminButton.insets = new Insets(0, 0, 0, 5);
		gbc_addAdminButton.gridx = 0;
		gbc_addAdminButton.gridy = 2;
		contentPane.add(getAddAdminButton(), gbc_addAdminButton);
		GridBagConstraints gbc_delAdminButton = new GridBagConstraints();
		gbc_delAdminButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_delAdminButton.insets = new Insets(0, 0, 0, 5);
		gbc_delAdminButton.gridx = 1;
		gbc_delAdminButton.gridy = 2;
		contentPane.add(getDelAdminButton(), gbc_delAdminButton);
		GridBagConstraints gbc_remNickButton = new GridBagConstraints();
		gbc_remNickButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_remNickButton.insets = new Insets(0, 0, 0, 5);
		gbc_remNickButton.gridx = 2;
		gbc_remNickButton.gridy = 2;
		contentPane.add(getRemNickButton(), gbc_remNickButton);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 2;
		contentPane.add(getPanel(), gbc_panel);
		GridBagConstraints gbc_verlassenButton = new GridBagConstraints();
		gbc_verlassenButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_verlassenButton.gridx = 4;
		gbc_verlassenButton.gridy = 2;
		contentPane.add(getVerlassenButton(), gbc_verlassenButton);
	}

	protected void zeigeNachricht(String s)
	{
		getChatListe().add(s);
		getChatListe().select(getChatListe().getItemCount() - 1);

	}

	private void sendeNachricht()
	{

		String s = getNachrichtTF().getText();
		if (!s.equals(""))
		{
			aClient.sendeNachricht(MESSAGE + SEPARATOR + this.toString() + SEPARATOR + s);
		}
		getNachrichtTF().setText("");
	}

	protected void ergaenzeTeilnehmer(String nick)
	{
		teilnehmerList.add(nick);
		zeigeTeilnehmerListe();
	}

	private void zeigeTeilnehmerListe()
	{
		teilnehmerListe.removeAll();
		for (String s : teilnehmerList)
		{
			teilnehmerListe.add(s);
		}
	}

	protected void entferneTeilnehmer(String nick)
	{
		teilnehmerList.remove(nick);
		zeigeTeilnehmerListe();
	}

	protected void raumVerlassen()
	{
		aClient.sendeNachricht(DELNICK + SEPARATOR + this.toString() + SEPARATOR + aClient.getNick());
	}

	public void changeName(String string)
	{
		this.roomName = string;
		setTitle(PUBLIC + " Raum: " + roomName);
	}

	protected void entferneNick()
	{
		String nick = getTeilnehmerListe().getSelectedItem();
		aClient.sendeNachricht(DELNICK + SEPARATOR + this.toString() + SEPARATOR + nick);
	}

	private void ernenneAdmin()
	{
		String nick = getTeilnehmerListe().getSelectedItem();
		aClient.sendeNachricht(ADDADMIN + SEPARATOR + this.toString() + SEPARATOR + nick);

	}

	private void entlasseAdmin()
	{
		String nick = getTeilnehmerListe().getSelectedItem();
		aClient.sendeNachricht(DELADMIN + SEPARATOR + this.toString() + SEPARATOR + nick);

	}

	@Override
	public String toString()
	{
		return roomName;
	}

	private List getChatListe()
	{
		if (chatListe == null)
		{
			chatListe = new List();
		}
		return chatListe;
	}

	private List getTeilnehmerListe()
	{
		if (teilnehmerListe == null)
		{
			teilnehmerListe = new List();
		}
		return teilnehmerListe;
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

	protected void beenden()
	{
		this.dispose();
	}

	private Button getVerlassenButton()
	{
		if (verlassenButton == null)
		{
			verlassenButton = new Button("Raum verlassen");
			verlassenButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					raumVerlassen();
				}
			});
		}
		return verlassenButton;
	}

	private Button getRemNickButton()
	{
		if (remNickButton == null)
		{
			remNickButton = new Button("Nick rauswerfen");
			remNickButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					entferneNick();
				}
			});
		}
		return remNickButton;
	}

	private Button getDelAdminButton()
	{
		if (delAdminButton == null)
		{
			delAdminButton = new Button("Admin entlassen");
			delAdminButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					entlasseAdmin();
				}
			});
		}
		return delAdminButton;
	}

	private Button getAddAdminButton()
	{
		if (addAdminButton == null)
		{
			addAdminButton = new Button("Admin ernennen");
			addAdminButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					ernenneAdmin();
				}
			});
		}
		return addAdminButton;
	}

	private Panel getPanel()
	{
		if (panel == null)
		{
			panel = new Panel();
		}
		return panel;
	}

	protected void updateTeilnehmer(String alterNick, String neuerNick)
	{
		if (teilnehmerList.contains(alterNick))
		{
			teilnehmerList.remove(alterNick);
			teilnehmerList.add(neuerNick);
			zeigeNachricht(alterNick + " hat seinen Nickname in " + neuerNick + " geändert.");
			zeigeTeilnehmerListe();

		}
	}
}
