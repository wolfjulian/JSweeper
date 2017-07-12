package figuren;

import gui.Feld;

/**
 * Created by erfeb on 12.07.2017.
 */
public class Dame extends Stein {
    final String symbol = "\u25c9";

    public Dame(Feld feld, boolean schwarz)
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
        else if (Math.abs(abstandSpalten) == 0)
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
