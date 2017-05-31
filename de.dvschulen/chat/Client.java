package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client extends Thread implements Protokoll
{
	final ClientGUI aClientGUI;
	private Socket aSocket;
	private DataInputStream in;
	private DataOutputStream out;
	private CopyOnWriteArrayList<PrivatChat> privateChats;
	String nick;
	private TreeSet<String> teilnehmerList = new TreeSet<>();
	private boolean verbunden;

	public Client(ClientGUI aClientGUI, String nick)
	{
		this.aClientGUI = aClientGUI;
		this.nick = nick;
		privateChats = new CopyOnWriteArrayList<PrivatChat>();
	}

	private void empfangeNachricht()
	{
		while (!isInterrupted())
		{

			try
			{
				bearbeiteNachricht(in.readUTF());
			}
			catch (IOException e)
			{
				interrupt();
				if (!aSocket.isConnected())
				{
					try
					{
						trenne();
					}
					catch (IOException e1)
					{
						aClientGUI.zeigeNachricht("Verbindung zum Server verloren");
					}
				}
			}

		}
	}

	private void bearbeiteNachricht(String s)
	{
		String message[] = s.split(SEPARATOR);
		if (message[0].equals(MESSAGE))
		{
			if (message[1].equals(PUBLIC))
			{
				aClientGUI.zeigeNachricht(message[2]);
			}
			else
			{
				// System.out.println(message[1] + message[2]);
				PrivatChat p = findePrivatChat(message[1]);
				if (p != null)
				{
					p.zeigeNachricht(message[2]);
				}
			}
		}
		if (message[0].equals(GETNICK))
		{
			this.nick = message[1];
		}
		if (message[0].equals(ADDNICK))
		{
			if (message[1].equals(PUBLIC))
			{
				teilnehmerList.add(message[2]);
				aClientGUI.updateTeilnehmer(teilnehmerList);
			}
			else
			{
				PrivatChat p = findePrivatChat(message[1]);
				if (p != null)
				{
					p.ergaenzeTeilnehmer(message[2]);
				}
			}
		}
		if (message[0].equals(CHANGENICK))
		{
			String alterNick = message[1];
			String neuerNick = message[2];

			if (nick.equals(alterNick))
			{
				this.nick = neuerNick;
			}
			teilnehmerList.remove(alterNick);
			teilnehmerList.add(neuerNick);
			aClientGUI.updateTeilnehmer(teilnehmerList);
			aClientGUI.zeigeNachricht(alterNick + " hat seinen Nickname in " + neuerNick + " geändert.");

			for (PrivatChat p : privateChats)
			{
				p.updateTeilnehmer(alterNick, neuerNick);
			}

		}

		if (message[0].equals(DELNICK))
		{
			if (message[1].equals(PUBLIC))
			{
				teilnehmerList.remove(message[2]);
				aClientGUI.updateTeilnehmer(teilnehmerList);
			}
			else
			{
				PrivatChat c = findePrivatChat(message[1]);
				if (c != null)
				{
					c.entferneTeilnehmer(message[2]);
				}
			}
		}
		if (message[0].equals(NEWROOM))
		{
			privateChats.add(new PrivatChat(this, message[1]));
			aClientGUI.ergaenzeRaum(message[1]);
		}
		if (message[0].equals(DELROOM))
		{
			PrivatChat p = findePrivatChat(message[1]);
			if (p != null)
			{
				privateChats.remove(p);
				p.beenden();
				aClientGUI.entferneRaum(message[1]);
			}
			if (message[1].equals(PUBLIC))
				message[0] = LOGOUT;
		}
		if (message[0].equals(LOGOUT))
		{
			try
			{
				trenne();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private PrivatChat findePrivatChat(String s)
	{
		for (PrivatChat p : privateChats)
		{
			if (p.toString().equals(s))
			{
				return p;
			}
		}
		return null;
	}

	protected void sendeNachricht(String s)
	{
		try
		{
			if (out != null && aSocket.isConnected())
			{
				out.writeUTF(s);
			}
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void aendereNick(String nick)
	{

		sendeNachricht(CHANGENICK + SEPARATOR + nick);
	}

	public void erstelleRaum(String s)
	{
		sendeNachricht(NEWROOM + SEPARATOR + s);
	}

	protected void verbinde(String ip, int port, String nick) throws UnknownHostException, IOException
	{
		aSocket = new Socket(ip, port);

		if (aSocket != null)
		{
			in = new DataInputStream(aSocket.getInputStream());
			out = new DataOutputStream(aSocket.getOutputStream());
			sendeNachricht(nick);
			verbunden = true;
			aClientGUI.switchButton();
		}
	}

	private void trenne() throws IOException
	{
		this.interrupt();
		in.close();
		out.close();
		aSocket.close();
		aSocket = null;
		for (PrivatChat p : privateChats)
		{
			p.beenden();
		}
		privateChats.removeAll(privateChats);
		aClientGUI.beende();
		System.out.println("Client Ende");
		if (aSocket == null)
		{
			verbunden = false;
			aClientGUI.switchButton();
		}
	}

	protected String getNick()
	{
		return nick;
	}

	protected boolean isVerbunden()
	{
		return verbunden;
	}

	@Override
	public void run()
	{
		empfangeNachricht();
	}

}
