package name.emu.decimatio;

import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;

public class PlayerLegionnaireModel implements IModel<Legionnaire> {
    @Override
    public Legionnaire getObject() {
        Player player = GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());

        return player!=null ? player.getCharacter() : null;
    }
}
