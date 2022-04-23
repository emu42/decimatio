package name.emu.decimatio;

import org.apache.wicket.model.IModel;

public class GameStateModel implements IModel<GameState> {
    @Override
    public GameState getObject() {
        return GameSessionSingleton.getTheSingleton().getGameState();
    }
}
