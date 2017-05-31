package chat;

interface Protokoll
{
	public static String SEPARATOR = "\u001e";
	public static String LOGOUT = "BYE";
	public static String GETNICK = "NG";
	public static String CHANGENICK = "NC";
	public static String ADDNICK = "NA";
	public static String DELNICK = "ND";
	public static String NEWROOM = "RA";
	public static String DELROOM = "RD";
	public static String ADDADMIN = "AA";
	public static String DELADMIN = "AD";

	public static String MESSAGE = "MSG";
	public static String PUBLIC = "FI11";

	final String s = MESSAGE + SEPARATOR + PUBLIC + SEPARATOR;

	public static String NICKREFUSED = s + "Der Server untersagt das Anmelden mit dem angegebenen Nicknamen!";
	public static String ROOMREFUSED = s + "Der Server untersagt das Erstellen eines derartigen Raumes!";
	public static String IPREFUSED = s + "Ihre IP wurde gesperrt!";
	public static String SPAMREFUSED = s + "Du bist schlimmer als ein SPAMBOT!";
	public static String CONNECTIONERROR = s + "Die Verbindung wurde unterbrochen";
}
