package name.emu.decimatio.ui;

import java.util.ArrayList;
import java.util.List;
import name.emu.decimatio.GameLogic;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStatus;
import name.emu.decimatio.model.Player;
import name.emu.decimatio.model.PlayerListProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

public class LobbyPanel extends Panel {

    private IModel<GameState> gameState;

    private IModel<String> playerName;

    public LobbyPanel(final String id, IModel<GameState> gameState, IModel<String> playerName) {
        super(id, gameState);
        this.gameState = gameState;
        List<IColumn<Player, String>> columns = new ArrayList<>();
        DefaultDataTable<Player, String> playerTable;
        this.setOutputMarkupPlaceholderTag(true);

        this.playerName = playerName;
        columns.add(new PropertyColumn<>(Model.of("Player"), "name"));
        columns.add(new PropertyColumn<>(Model.of("Score"), "score"));
        playerTable = new DefaultDataTable<>("players", columns, new PlayerListProvider(gameState), Integer.MAX_VALUE);
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

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(!Strings.isEmpty(playerName.getObject()) && (gameState.getObject().getStatus() == GameStatus.LOBBY || gameState.getObject().getStatus() == GameStatus.SCORE));
    }
}
