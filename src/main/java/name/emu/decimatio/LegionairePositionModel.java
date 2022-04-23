package name.emu.decimatio;

import org.apache.wicket.model.IModel;

public class LegionairePositionModel implements IModel<Legionaire> {

    private IModel<GameState> gameState;

    private int pos;
    public LegionairePositionModel(final IModel<GameState> gameState, final int pos) {
        this.gameState = gameState;
        this.pos = pos;
    }

    @Override
    public Legionaire getObject() {
        return gameState.getObject().getLegionaires().get(pos);
    }
}
