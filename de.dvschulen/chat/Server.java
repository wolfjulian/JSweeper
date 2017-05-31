package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements Protokoll
{
	private listenThread t = null;
	private CopyOnWriteArrayList<ChatRoom> raumList;
	final ServerGUI aServerGUI;
	final TreeSet<String> nicknames = new TreeSet<String>();
	final ChatRoom publicRoom = new ChatRoom(PUBLIC, null, this);

	public Server(ServerGUI aServerGui)
	{
		this.aServerGUI = aServerGui;
		initialize();

	}

	private void initialize()
	{
		nicknames.add("admin");

	}

	protected void starten(int port)
	{
		t = new listenThread(this, port);
		t.start();
		raumList = new CopyOnWriteArrayList<ChatRoom>();
		// Frage ja / nein / vielleicht? raumList.add(publicRoom);
	}

	protected void beenden()
	{
		if (t != null)
		{
			t.interrupt();
			t = null;
		}

		for (ClientProxy p : publicRoom.getTeilnehmerList())
		{
			abmeldenClient(p);
		}
		raumList = null;
	}

	protected boolean pruefeNickname(String s)
	{
		if (nicknames.contains(s.toLowerCase()))
			return false;
		return true;
	}

	protected void aendereNickname(String alterNick, String neuerNick)
	{
		nicknames.remove(alterNick.toLowerCase());
		nicknames.add(neuerNick.toLowerCase());
		publicRoom.verteileNachricht(CHANGENICK + SEPARATOR + alterNick + SEPARATOR + neuerNick);
	}

	protected void abmeldenClient(ClientProxy c)
	{
		for (ChatRoom r : raumList)
		{
			if (r.getTeilnehmerList().contains(c))
			{
				r.entferneTeilnehmer(c, c);
			}
		}
		publicRoom.entferneTeilnehmer(c, c);
		nicknames.remove(c.toString());
		aServerGUI.zeigeTeilnehmerliste(nicknames);
	}

	protected void pruefeClient(ClientProxy c)
	{
		aServerGUI.zeigeEvent("Client arrived");
		if (pruefeNickname(c.toString()))
		{
			akzeptiereClient(c);
		}
		else
		{
			ablehnenClient(c);
		}
	}

	protected void zeigeEvent(String s)
	{
		if (aServerGUI != null)
			aServerGUI.zeigeEvent(s);
	}

	protected void akzeptiereClient(ClientProxy c)
	{
		aServerGUI.zeigeEvent("Client accepted");
		nicknames.add(c.toString().toLowerCase());
		publicRoom.hinzufuegenTeilnehmer(c);
		aServerGUI.zeigeTeilnehmerliste(nicknames);
		c.sendeNachricht(GETNICK + SEPARATOR + c.toString());
	}

	protected void ablehnenClient(ClientProxy c)
	{
		aServerGUI.zeigeEvent("Client rejected");
		c.beendeVerbindung(NICKREFUSED);
	}

	protected void hinzufuegenClientProxy(ClientProxy c, ClientProxy sender, ChatRoom r)
	{
		r.hinzufuegenTeilnehmer(c, sender);
	}

	protected void entferneClientproxy(ClientProxy c, ClientProxy sender, ChatRoom r)
	{
		r.entferneTeilnehmer(c, sender);
	}

	protected ChatRoom findeChatRoom(String s)
	{
		ChatRoom r = null;
		for (ChatRoom cr : raumList)
		{
			if (cr.toString().equals(s))
				r = cr;
		}
		return r;
	}

	protected ChatRoom erstelleRaum(String name, ClientProxy admin)
	{
		ChatRoom r = new ChatRoom(name, admin, this);
		raumList.add(r);
		aServerGUI.zeigeRaumliste(raumList);
		return r;
	}

	protected void entferneRaum(ChatRoom r)
	{
		raumList.remove(r);
		aServerGUI.zeigeRaumliste(raumList);
	}

	protected ClientProxy findeNick(String nick)
	{
		for (ClientProxy c : publicRoom.getTeilnehmerList())
		{
			if (c.getNickname().equals(nick))
			{
				return c;
			}
		}
		return null;
	}

}

class listenThread extends Thread
{
	private Server s;
	private int port;

	public listenThread(Server s, int port)
	{
		this.s = s;
		this.port = port;
	}

	@Override
	public void run()
	{
		ServerSocket socket = null;
		try
		{
			socket = new ServerSocket(port);
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		while (!isInterrupted() && socket != null)
		{
			try
			{
				Thread.sleep(100);
				socket.setSoTimeout(100);
				s.pruefeClient(new ClientProxy(s, socket.accept()));
			}
			catch (SocketTimeoutException e)
			{
				// um Thread zu beenden!
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				interrupt();
				try
				{
					socket.close();
				}
				catch (IOException ex)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try
		{
			socket.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket = null;
	}
}
