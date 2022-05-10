package name.emu.decimatio.ui.model;

import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.Legionnaire;
import name.emu.decimatio.model.Player;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;

public class PlayerLegionnaireModel implements IModel<Legionnaire> {
    @Override
    public Legionnaire getObject() {
        Player player = GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());

        return player!=null ? player.getCharacter() : null;
    }
}
