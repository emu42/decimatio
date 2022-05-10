package name.emu.decimatio.ui.model;

import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.Player;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;

public class PlayerModel implements IModel<Player> {

    @Override
    public Player getObject() {
        return GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());
    }
}
