package chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientProxy implements Runnable, Protokoll
{
	private Socket aSocket;
	private Server aServer;
	private DataInputStream in;
	private DataOutputStream out;
	private String nickname;
	private Thread t;
	private boolean accepted = true;

	public ClientProxy(Server aServer, Socket aSocket)
	{
		this.aServer = aServer;
		this.aSocket = aSocket;

		try
		{
			out = new DataOutputStream(aSocket.getOutputStream());
			in = new DataInputStream(aSocket.getInputStream());
			nickname = in.readUTF();
			t = new Thread(this);
			t.start();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sendeNachricht(String s)
	{
		try
		{
			if (out != null)
			{
				out.writeUTF(s);
			}
		}
		catch (SocketException e)
		{

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void empfangeNachricht()
	{
		try
		{
			bearbeiteNachricht(in.readUTF());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			aServer.zeigeEvent("Verbindung unterbrochen " + this.toString());
			aServer.abmeldenClient(this);
			try
			{
				abmeldenClientProxy();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void bearbeiteNachricht(String s)
	{
		String message[] = s.split(SEPARATOR);
		if (accepted == true)
		{
			if (message[0].equals(CHANGENICK))
			{
				if (aServer.pruefeNickname(message[1]))
				{
					aServer.aendereNickname(this.toString(), message[1]);
					nickname = message[1];
				}
				else
				{
					sendeNachricht(NICKREFUSED);
				}

			}
			if (message[0].equals(MESSAGE))
			{
				if (message[1].equals(PUBLIC))
				{
					aServer.publicRoom.verteileNachricht(message[2], this);
				}
				ChatRoom r = aServer.findeChatRoom(message[1]);
				if (r != null)
				{
					r.verteileNachricht(message[2], this);
				}
			}
			if (message[0].equals(NEWROOM))
			{
				ChatRoom r = aServer.findeChatRoom(message[1]);
				if (r == null)
				{
					r = aServer.erstelleRaum(message[1], this);
					r.hinzufuegenTeilnehmer(this, this);
				}
				else
				{
					sendeNachricht(ROOMREFUSED);
				}
			}
			if (message[0].equals(ADDNICK))
			{
				ChatRoom r = aServer.findeChatRoom(message[1]);
				if (r != null)
				{
					ClientProxy c = aServer.findeNick(message[2]);
					if (c != null)
					{
						r.hinzufuegenTeilnehmer(c, this);
					}
				}
			}
			if (message[0].equals(ADDADMIN))
			{
				ChatRoom r = aServer.findeChatRoom(message[1]);
				if (r != null)
				{
					ClientProxy c = aServer.findeNick(message[2]);
					if (c != null)
					{
						r.addAdmin(c, this);
					}
				}
			}
			if (message[0].equals(DELADMIN))
			{
				ChatRoom r = aServer.findeChatRoom(message[1]);
				if (r != null)
				{
					ClientProxy c = aServer.findeNick(message[2]);
					if (c != null)
					{
						r.delAdmin(c, this);
					}
				}
			}
		}
		if (message[0].equals(DELNICK))
		{

			ChatRoom r = aServer.findeChatRoom(message[1]);
			ClientProxy c = null;
			if (r != null)
			{
				c = r.findeNick(message[2]);
				if (c != null)
				{
					r.entferneTeilnehmer(c, this);
				}
			}
		}
		if (message[0].equals(LOGOUT))
		{
			aServer.abmeldenClient(this);
			try
			{
				sendeNachricht(LOGOUT);
				abmeldenClientProxy();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	private void abmeldenClientProxy() throws IOException
	{
		if (t.isAlive())
			t.interrupt();
		if (in != null)
			in.close();
		if (out != null)
			out.close();
		if (aSocket != null)
			aSocket.close();
		aServer.zeigeEvent("Client removed " + this.toString());
	}

	protected void beendeVerbindung(String s)
	{
		sendeNachricht(s);

		try
		{
			abmeldenClientProxy();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			aServer.entferneClientproxy(this, this, aServer.findeChatRoom(PUBLIC));
		}

	}

	public String getNickname()
	{
		return nickname;
	}

	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	@Override
	public String toString()
	{
		return nickname;
	}

	@Override
	public void run()
	{
		while (!t.isInterrupted())
		{
			try
			{
				Thread.sleep(100);
				empfangeNachricht();
			}
			catch (InterruptedException e)
			{
				t.interrupt();
			}

		}
	}

}
