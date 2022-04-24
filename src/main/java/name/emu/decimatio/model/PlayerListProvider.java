package name.emu.decimatio.model;

import java.util.Iterator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class PlayerListProvider extends SortableDataProvider<Player, String> {

    private IModel<GameState> gameState;

    public PlayerListProvider(final IModel<GameState> gameState) {
        this.gameState = gameState;
    }

    @Override
    public Iterator<? extends Player> iterator(final long first, final long count) {
        // ignore paging
        return gameState.getObject().getPlayers().iterator();
    }

    @Override
    public long size() {
        return gameState.getObject()!=null ? gameState.getObject().getPlayers().size() : 0;
    }

    @Override
    public IModel<Player> model(final Player object) {
        return Model.of(object);
    }
}
