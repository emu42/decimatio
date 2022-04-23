package name.emu.decimatio;

import java.util.List;
import org.apache.wicket.model.IModel;

public class LegionnairePositionModel implements IModel<Legionnaire> {

    private IModel<GameState> gameState;

    private int pos;
    public LegionnairePositionModel(final IModel<GameState> gameState, final int pos) {
        this.gameState = gameState;
        this.pos = pos;
    }

    @Override
    public Legionnaire getObject() {
        List<Legionnaire> legionnaireList = gameState.getObject().getLegionnaires();
        return legionnaireList.size() > pos ? gameState.getObject().getLegionnaires().get(pos) : null;
    }
}
