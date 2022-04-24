package name.emu.decimatio.ui;

import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.Player;
import org.apache.wicket.Session;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class JoinLinkModel implements org.apache.wicket.model.IModel<String> {
    @Override
    public String getObject() {
        Player player = GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());
        String url = null;
        if (player != null) {
            PageParameters params = new PageParameters();

            if (player.getGameSessionId() != null) {
                params.set(HomePage.GAME_SESSION_PARAM, player.getGameSessionId());
                url = RequestCycle.get().getUrlRenderer().renderFullUrl(RequestCycle.get().mapUrlFor(HomePage.class, params));
            }
        }
        return url;
    }
}
