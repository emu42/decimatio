package name.emu.decimatio.ui;

import java.util.ArrayList;
import java.util.List;
import name.emu.decimatio.GameLogic;
import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStatus;
import name.emu.decimatio.model.Player;
import name.emu.decimatio.model.PlayerListProvider;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

public class LobbyPanel extends Panel {

    private IModel<GameState> gameState;

    private IModel<Player> player;

    private GlobalRefreshCallback refreshCallback;

    public LobbyPanel(final String id, IModel<GameState> gameState, IModel<Player> player, GlobalRefreshCallback refreshCallback) {
        super(id, gameState);
        this.gameState = gameState;
        this.refreshCallback = refreshCallback;
        List<IColumn<Player, String>> columns = new ArrayList<>();
        DefaultDataTable<Player, String> playerTable;
        this.setOutputMarkupPlaceholderTag(true);
        TextField joinLink = new TextField("joinLink", new JoinLinkModel());
        WebMarkupContainer setupContainer, playerListContainer;

        setupContainer = new WebMarkupContainer("setupContainer") {

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setVisible(gameState.getObject() == null);
            }
        };


        Form form2 = new Form("form");
        AjaxButton newGame = new AjaxButton("createGameBtn") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                gameState.setObject(GameSessionSingleton.createGameSession());
                player.getObject().setGameSessionId(gameState.getObject().getGameSessionId());
                player.getObject().setHoster(true);
                GameLogic.addPlayer(gameState.getObject(), player.getObject());
                refreshCallback.globalRefresh(target);
            }
        };


        playerListContainer = new WebMarkupContainer("playerListContainer") {

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setVisible(gameState.getObject() != null);
            }
        };


        this.player = player;
        columns.add(new PropertyColumn<>(Model.of("Player"), "name"));
        columns.add(new PropertyColumn<>(Model.of("Score"), "score"));
        playerTable = new DefaultDataTable<>("players", columns, new PlayerListProvider(gameState), Integer.MAX_VALUE);

        Form form = new Form("form");
        AjaxButton launchButton = new AjaxButton("launchBtn") {

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                GameLogic.launchGame(gameState.getObject());

            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setEnabled(player.getObject().isHoster());
            }
        };

        form2.add(newGame);
        setupContainer.add(form2);
        add(setupContainer);

        form.add(launchButton);
        playerListContainer.add(form);
        playerListContainer.add(joinLink);
        playerListContainer.add(playerTable);

        add(playerListContainer);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(!Strings.isEmpty(player.getObject().getName()) && (gameState.getObject()==null || gameState.getObject().getStatus() == GameStatus.LOBBY || gameState.getObject().getStatus() == GameStatus.SCORE));
    }
}
