package name.emu.decimatio;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class LobbyPanel extends Panel {

    private IModel<GameState> gameState;

    public LobbyPanel(final String id, IModel<GameState> gameState) {
        super(id, gameState);
        this.gameState = gameState;
        List<IColumn<Player, String>> columns = new ArrayList<>();

        columns.add(new PropertyColumn<Player, String>(Model.of("Player"), "name"));
        DefaultDataTable<Player, String> playerTable = new DefaultDataTable<Player, String>("players", columns, new PlayerListProvider(gameState), Integer.MAX_VALUE);
        add(playerTable);
        Form form = new Form("form");
        AjaxButton launchButton = new AjaxButton("launchBtn") {

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                GameLogic.launchGame(gameState.getObject());

            }

        };
        add(form);
        form.add(launchButton);
    }


}
