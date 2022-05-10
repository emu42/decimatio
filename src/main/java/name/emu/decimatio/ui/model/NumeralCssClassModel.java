package name.emu.decimatio.ui.model;

import name.emu.decimatio.model.GameState;
import org.apache.wicket.model.IModel;

public class NumeralCssClassModel implements IModel<String> {

    private IModel<GameState> gameStateModel;

    private int idx;

    public NumeralCssClassModel(final IModel<GameState> gameState, final int i) {
        this.gameStateModel = gameState;
        idx = i;
    }

    @Override
    public String getObject() {
        int posNum = (10 + idx - (gameStateModel.getObject().getTenthSlotPos())+1) % 10;

        return posNum == 0 ? "numeral decimatio" : "numeral";
    }
}
