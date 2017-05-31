package chat;

import java.awt.Button;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ServerGUI extends JFrame
{

	private JPanel contentPane;
	private TextField textField;
	private Label label;
	private Button startButton;
	private List eventListe;
	private List raumListe;
	private List TeilnehmerListe;
	private Label label_1;
	private Label label_2;
	private Label label_3;
	private Button stopButton;
	private List adminListe;
	private Label label_4;
	private List banListe;
	private Label label_5;
	private final Server aServer;
	private int port;

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
					ServerGUI frame = new ServerGUI();
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
	public ServerGUI()
	{
		setTitle("FI 2015 - Chat-Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 278);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		contentPane.add(getLabel(), gbc_label);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPane.add(getTextField(), gbc_textField);
		GridBagConstraints gbc_startButton = new GridBagConstraints();
		gbc_startButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_startButton.insets = new Insets(0, 0, 5, 5);
		gbc_startButton.gridx = 2;
		gbc_startButton.gridy = 0;
		contentPane.add(getStartButton(), gbc_startButton);
		GridBagConstraints gbc_stopButton = new GridBagConstraints();
		gbc_stopButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_stopButton.insets = new Insets(0, 0, 5, 5);
		gbc_stopButton.gridx = 3;
		gbc_stopButton.gridy = 0;
		contentPane.add(getStopButton(), gbc_stopButton);
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 1;
		contentPane.add(getLabel_1(), gbc_label_1);
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 1;
		contentPane.add(getLabel_2(), gbc_label_2);
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 1;
		contentPane.add(getLabel_3(), gbc_label_3);
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 3;
		gbc_label_4.gridy = 1;
		contentPane.add(getLabel_4(), gbc_label_4);
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 0);
		gbc_label_5.gridx = 4;
		gbc_label_5.gridy = 1;
		contentPane.add(getLabel_5(), gbc_label_5);
		GridBagConstraints gbc_eventListe = new GridBagConstraints();
		gbc_eventListe.insets = new Insets(0, 0, 0, 5);
		gbc_eventListe.gridx = 0;
		gbc_eventListe.gridy = 2;
		contentPane.add(getEventListe(), gbc_eventListe);
		GridBagConstraints gbc_raumListe = new GridBagConstraints();
		gbc_raumListe.insets = new Insets(0, 0, 0, 5);
		gbc_raumListe.gridx = 1;
		gbc_raumListe.gridy = 2;
		contentPane.add(getRaumListe(), gbc_raumListe);
		GridBagConstraints gbc_TeilnehmerListe = new GridBagConstraints();
		gbc_TeilnehmerListe.insets = new Insets(0, 0, 0, 5);
		gbc_TeilnehmerListe.gridx = 2;
		gbc_TeilnehmerListe.gridy = 2;
		contentPane.add(getTeilnehmerListe(), gbc_TeilnehmerListe);
		GridBagConstraints gbc_adminListe = new GridBagConstraints();
		gbc_adminListe.insets = new Insets(0, 0, 0, 5);
		gbc_adminListe.gridx = 3;
		gbc_adminListe.gridy = 2;
		contentPane.add(getAdminListe(), gbc_adminListe);
		GridBagConstraints gbc_banListe = new GridBagConstraints();
		gbc_banListe.gridx = 4;
		gbc_banListe.gridy = 2;
		contentPane.add(getBanListe(), gbc_banListe);

		aServer = new Server(this);
		this.initialize();

	}

	private void initialize()
	{
		port = 8008;
	}

	private void starteServer()
	{

		aServer.starten(port);
		leereGUI();
		zeigeEvent("Server gestartet");
		startButton.setEnabled(false);
		stopButton.setEnabled(true);

	}

	private void leereGUI()
	{
		getAdminListe().removeAll();
		getTeilnehmerListe().removeAll();
		getEventListe().removeAll();
		getBanListe().removeAll();
		getRaumListe().removeAll();
		getTextField().setText("");
	}

	private void beendeServer()
	{

		aServer.beenden();
		zeigeEvent("Server angehalten");
		startButton.setEnabled(true);
		stopButton.setEnabled(false);

	}

	protected void zeigeEvent(String s)
	{
		getEventListe().add(s);
		getEventListe().select(getEventListe().getItemCount() - 1);
	}

	protected void zeigeRaumliste(CopyOnWriteArrayList<ChatRoom> liste)
	{
		getRaumListe().removeAll();
		for (ChatRoom c : liste)
		{
			getRaumListe().add(c.toString());
		}
	}

	protected void zeigeTeilnehmerliste(TreeSet<String> nicknames)
	{
		getTeilnehmerListe().removeAll();
		for (String s : nicknames)
		{
			getTeilnehmerListe().add(s);
		}
	}

	private TextField getTextField()
	{
		if (textField == null)
		{
			textField = new TextField();
		}
		return textField;
	}

	private Label getLabel()
	{
		if (label == null)
		{
			label = new Label("Port:");
		}
		return label;
	}

	private Button getStartButton()
	{
		if (startButton == null)
		{
			startButton = new Button("Starte Server");
			startButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					starteServer();
				}
			});
		}
		return startButton;
	}

	private List getEventListe()
	{
		if (eventListe == null)
		{
			eventListe = new List();
		}
		return eventListe;
	}

	private List getRaumListe()
	{
		if (raumListe == null)
		{
			raumListe = new List();
		}
		return raumListe;
	}

	private List getTeilnehmerListe()
	{
		if (TeilnehmerListe == null)
		{
			TeilnehmerListe = new List();
		}
		return TeilnehmerListe;
	}

	private Label getLabel_1()
	{
		if (label_1 == null)
		{
			label_1 = new Label("Eventliste");
		}
		return label_1;
	}

	private Label getLabel_2()
	{
		if (label_2 == null)
		{
			label_2 = new Label("Raumliste");
		}
		return label_2;
	}

	private Label getLabel_3()
	{
		if (label_3 == null)
		{
			label_3 = new Label("Teilnehmerliste");
		}
		return label_3;
	}

	private Button getStopButton()
	{
		if (stopButton == null)
		{
			stopButton = new Button("Beende Server");
			stopButton.setEnabled(false);
			stopButton.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					beendeServer();
				}
			});
		}
		return stopButton;
	}

	private List getAdminListe()
	{
		if (adminListe == null)
		{
			adminListe = new List();
		}
		return adminListe;
	}

	private Label getLabel_4()
	{
		if (label_4 == null)
		{
			label_4 = new Label("Adminliste");
		}
		return label_4;
	}

	private List getBanListe()
	{
		if (banListe == null)
		{
			banListe = new List();
		}
		return banListe;
	}

	private Label getLabel_5()
	{
		if (label_5 == null)
		{
			label_5 = new Label("Ban-Liste");
		}
		return label_5;
	}
}
