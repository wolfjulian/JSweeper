package chat;

import java.util.concurrent.CopyOnWriteArrayList;

public class ChatRoom implements Protokoll
{
	private CopyOnWriteArrayList<ClientProxy> teilnehmerList;
	private String roomname;
	private CopyOnWriteArrayList<ClientProxy> adminList;
	private Server aServer;

	public ChatRoom(String roomname, ClientProxy admin, Server aServer)
	{
		this.roomname = roomname;
		teilnehmerList = new CopyOnWriteArrayList<ClientProxy>();
		adminList = new CopyOnWriteArrayList<ClientProxy>();
		adminList.add(admin);
		this.aServer = aServer;
	}

	protected void hinzufuegenTeilnehmer(ClientProxy c)
	// für Server zunächst ohne ADMIN!
	{
		if (roomname.equals(PUBLIC))
		{
			for (ClientProxy tn : teilnehmerList)
			{
				// Neuem CP alle aktuellen Clients senden
				c.sendeNachricht(ADDNICK + SEPARATOR + this.toString() + SEPARATOR + tn.getNickname());
			}
			// Neuen CP in den Chat aufnehmen
			teilnehmerList.add(c);

			// Allen Teilnehmern den neuen CP senden
			verteileNachricht(ADDNICK + SEPARATOR + this.toString() + SEPARATOR + c.getNickname());

			// Nachricht an Alle senden
			verteileNachricht(MESSAGE + SEPARATOR + this.toString() + SEPARATOR + c.toString() + " betritt den Chat "
					+ this.toString() + ".");
		}
	}

	protected void hinzufuegenTeilnehmer(ClientProxy c, ClientProxy sender)
	// Generell für alle Räume mit ADMIN!
	{
		if (adminList.contains(sender))
		{
			c.sendeNachricht(NEWROOM + SEPARATOR + this.toString());
			for (ClientProxy tn : teilnehmerList)
			{
				// Neuem CP alle aktuellen Clients senden
				c.sendeNachricht(ADDNICK + SEPARATOR + this.toString() + SEPARATOR + tn.getNickname());
			}
			// Neuen CP in den Chat aufnehmen
			teilnehmerList.add(c);

			// Allen Teilnehmern den neuen CP senden
			verteileNachricht(ADDNICK + SEPARATOR + this.toString() + SEPARATOR + c.getNickname());

			// Nachricht an Alle senden
			verteileNachricht(MESSAGE + SEPARATOR + this.toString() + SEPARATOR + c.toString() + " betritt den Chat "
					+ this.toString() + ".");
		}

	}

	protected void entferneTeilnehmer(ClientProxy c, ClientProxy sender)
	{
		String s = null;
		// Client meldet sich selbst ab:
		if (c == sender)
		{
			// Client ist letzter Admin => Raum beenden
			if ((adminList.contains(sender)) && (adminList.size() == 1))
			{
				verteileNachricht(MESSAGE + SEPARATOR + PUBLIC + SEPARATOR + c.toString()
						+ " hat den privaten Chatraum: " + this.toString() + " beendet.");

				verteileNachricht(DELROOM + SEPARATOR + this.toString());
				aServer.entferneRaum(this);
			}
			// Ansonsten Client einfach abmelden
			else
			{
				s = SEPARATOR + c.toString() + " hat den Chat " + this.toString() + " verlassen.";
			}
		}
		// Client wird von Admin entfernt
		else if (adminList.contains(sender))
		{
			s = SEPARATOR + c.toString() + " wurde aus dem Chat " + this.toString() + " entfernt.";
			s = MESSAGE + SEPARATOR + this.toString() + s;
		}

		if (s != null)
		{
			c.sendeNachricht(DELROOM + SEPARATOR + this.toString());
			c.sendeNachricht(MESSAGE + SEPARATOR + PUBLIC + s);
			teilnehmerList.remove(c);
			if (adminList.contains(c))
			{
				adminList.remove(c);
			}
			verteileNachricht(DELNICK + SEPARATOR + this.toString() + SEPARATOR + c.toString());
			verteileNachricht(MESSAGE + SEPARATOR + this.toString() + s);

		}
	}

	protected void beendeTeilnehmer(ClientProxy c)
	{
		teilnehmerList.remove(c);
		verteileNachricht(MESSAGE + SEPARATOR + this.toString() + SEPARATOR + c.toString() + " hat den Chatserver "
				+ PUBLIC + " verlassen");
	}

	protected ClientProxy findeNick(String nick)
	{
		for (ClientProxy c : teilnehmerList)
		{
			if (c.getNickname().equals(nick))
			{
				return c;
			}
		}
		return null;
	}

	// Befehle
	protected void verteileNachricht(String s)
	{
		for (ClientProxy c : teilnehmerList)
		{
			c.sendeNachricht(s);
		}
	}

	// Chatten
	protected void verteileNachricht(String nachricht, ClientProxy sender)
	{
		if (teilnehmerList.contains(sender))
		{
			String nick;
			if (adminList.contains(sender))
			{
				nick = "[" + sender.toString() + "] : ";
			}
			else
			{
				nick = sender.toString() + ": ";
			}
			for (ClientProxy c : teilnehmerList)
			{
				c.sendeNachricht(MESSAGE + SEPARATOR + this.toString() + SEPARATOR + nick + nachricht);
			}
		}
	}

	protected void aendereNickname(String alt, String neu, ClientProxy c)
	{
		if (teilnehmerList.contains(c))
		{
			for (ClientProxy tn : teilnehmerList)
			{
				tn.sendeNachricht(DELNICK + SEPARATOR + this.toString() + SEPARATOR + alt);
				tn.sendeNachricht(ADDNICK + SEPARATOR + this.toString() + SEPARATOR + neu);
				tn.sendeNachricht(MESSAGE + SEPARATOR + this.toString() + SEPARATOR + alt + " hat seinen Nick zu " + neu
						+ " geändert");
			}
		}
	}

	protected void addAdmin(ClientProxy c, ClientProxy sender)
	{
		if (adminList.contains(sender))
		{
			if (!adminList.contains(c))
			{
				adminList.add(c);
				verteileNachricht(
						MESSAGE + SEPARATOR + this.toString() + SEPARATOR + c.toString() + " wurde zum Admin gekrönt!");
			}

		}
	}

	protected void delAdmin(ClientProxy c, ClientProxy sender)
	{
		if (adminList.contains(c) && adminList.contains(sender))
		{
			if (adminList.size() > 1)
			{
				adminList.remove(c);
				verteileNachricht(
						MESSAGE + SEPARATOR + this.toString() + SEPARATOR + c.toString() + " wurde ent-Adminisiert!");
			}
			else
			{
				sender.sendeNachricht(MESSAGE + SEPARATOR + this.toString() + SEPARATOR
						+ "Admin-Rechte können nicht entzogen werden: Sie sind der einzige Admin, jeder Raum benötigt mind. 1 Admin!");
			}
		}
	}

	@Override
	public String toString()
	{
		return roomname;
	}

	protected CopyOnWriteArrayList<ClientProxy> getTeilnehmerList()
	{
		return teilnehmerList;
	}
}
