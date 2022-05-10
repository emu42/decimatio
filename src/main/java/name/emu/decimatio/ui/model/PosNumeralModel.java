package name.emu.decimatio.ui.model;

import name.emu.decimatio.model.GameState;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class PosNumeralModel implements IModel<String> {

    private IModel<GameState> gameStateModel;

    private int idx;

    public PosNumeralModel(final IModel<GameState> gameState, final int i) {
        this.gameStateModel = gameState;
        idx = i;
    }

    @Override
    public String getObject() {
        int posNum = (10 + idx - (gameStateModel.getObject().getTenthSlotPos())+1) % 10;

        return toRomanNumeral(posNum);
    }

    private String toRomanNumeral(final int posNum) {
        String str;

        switch (posNum) {
            case 0:
                str = "X";
                break;
            case 1:
                str = "I";
                break;
            case 2:
                str = "II";
                break;
            case 3:
                str = "III";
                break;
            case 4:
                str = "IV";
                break;
            case 5:
                str = "V";
                break;
            case 6:
                str = "VI";
                break;
            case 7:
                str = "VII";
                break;
            case 8:
                str = "VIII";
                break;
            case 9:
                str = "IX";
                break;
            default:
                str = null;
        }
        return str;
    }

}
