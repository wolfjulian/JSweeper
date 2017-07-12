package figuren;

/**
 * Created by erfeb on 12.07.2017.
 */

import gui.Feld;
public class Einfach extends Stein{
    final String symbol = "\u25ce";

    public Einfach(Feld feld, boolean schwarz)
    {
        super(feld, schwarz);
    }

    @Override
    public boolean akzeptiertZug(Feld f)
    {
        int abstandZeilen = f.getSpalte() - getFeld().getSpalte();
        int abstandSpalten = f.getZeile() - getFeld().getZeile();

        if (Math.abs(abstandSpalten) != Math.abs(abstandZeilen))
        {
            return false;
        }
        else if (Math.abs(abstandSpalten) == 0 || Math.abs(abstandSpalten) > 2)
        {
            return false;
        }
        else if (getSchwarz() && abstandZeilen > 0 || !getSchwarz() && abstandZeilen < 0)
        {
            return false;
        }
        return true;
    }

    @Override
    public String getSymbol()
    {
        return symbol;
    }
}
