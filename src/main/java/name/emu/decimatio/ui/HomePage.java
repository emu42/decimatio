package name.emu.decimatio.ui;

import java.time.Duration;
import name.emu.decimatio.GameLogic;
import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStateModel;
import name.emu.decimatio.model.Player;
import name.emu.decimatio.model.PlayerModel;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage implements GlobalRefreshCallback {

	public static final String GAME_SESSION_PARAM = "session";

	private static final long serialVersionUID = 1L;

	private final IModel<String> playerName;

	private final GamePanel gamePanel;

	private final LobbyPanel lobbyPanel;

	private final SignUpPanel signUpPanel;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		IModel<GameState> gameState = new GameStateModel();

		String gameSessionId = parameters.get(GAME_SESSION_PARAM).toOptionalString();
		Session.get().bind();
		String sessionId = Session.get().getId();
		Player player = GameSessionSingleton.findOrCreateForSessionId(sessionId);
		//String error = GameLogic.addPlayer(gameState.getObject(), player);
		IModel<Player> playerModel = new PlayerModel();
		player.setGameSessionId(gameSessionId);
		playerName = new PropertyModel<>(player, "name");

		signUpPanel = new SignUpPanel("signUpPanel", gameState, playerModel, this);
		lobbyPanel = new LobbyPanel("lobbyPanel", gameState, playerModel, this);
		gamePanel = new GamePanel("gamePanel", gameState, this);

		Label updater = new Label("updater", "");
		updater.add(new AjaxSelfUpdatingTimerBehavior(Duration.ofSeconds(1)) {
			@Override
			protected void onPostProcessTarget(final AjaxRequestTarget target) {
				super.onPostProcessTarget(target);

				Player player = GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());
				GameState gameState = GameSessionSingleton.findForGameSessionId(player.getGameSessionId());
				if (player != null && gameState!=null && player.getLastGameStateVersionRendered() != gameState.getVersion()) {
					System.out.println("state change");
					player.setLastGameStateVersionRendered(gameState.getVersion());
					globalRefresh(target);
				}

			}
		});
		add(updater);

		add(signUpPanel);
		add(lobbyPanel);
		add(gamePanel);

	}

	public void globalRefresh(AjaxRequestTarget target) {
		target.add(signUpPanel);
		target.add(gamePanel);
		target.add(lobbyPanel);
	}
}
