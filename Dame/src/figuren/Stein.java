package figuren;

import gui.Feld;

/**
 * Created by erfeb on 12.07.2017.
 */

import gui.Feld;

public abstract class Stein {
    private Feld feld;
    private boolean istSchwarz;

    public Stein(Feld feld, boolean schwarz)
    {
        this.feld = feld;
        istSchwarz = schwarz;
    }

    public void setFeld(Feld feld)
    {
        this.feld = feld;
        feld.getBrett().merkeEnde(this);
    }

    public boolean getSchwarz()
    {
        return istSchwarz;
    }

    public Feld getFeld()
    {
        return feld;
    }

    abstract public boolean akzeptiertZug(Feld f);

    abstract public String getSymbol();
}
