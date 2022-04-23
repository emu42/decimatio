package name.emu.decimatio.model;

import name.emu.decimatio.GameSessionSingleton;
import org.apache.wicket.model.IModel;

public class GameStateModel implements IModel<GameState> {
    @Override
    public GameState getObject() {
        return GameSessionSingleton.getTheSingleton().getGameState();
    }
}
