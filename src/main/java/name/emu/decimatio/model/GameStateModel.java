package name.emu.decimatio.model;

import name.emu.decimatio.GameSessionSingleton;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;

public class GameStateModel implements IModel<GameState> {
    @Override
    public GameState getObject() {
        return GameSessionSingleton.findForGameSessionId(GameSessionSingleton.findOrCreateForSessionId(Session.get().getId()).getGameSessionId());
    }

    @Override
    public void setObject(final GameState object) {
        GameSessionSingleton.findOrCreateForSessionId(Session.get().getId()).setGameSessionId(object.getGameSessionId());
    }
}
