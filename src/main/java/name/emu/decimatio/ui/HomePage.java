package name.emu.decimatio.ui;

import java.time.Duration;
import name.emu.decimatio.GameLogic;
import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStateModel;
import name.emu.decimatio.model.Player;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	private IModel<String> playerName;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		IModel<GameState> gameState = new GameStateModel();
		GamePanel gamePanel;
		LobbyPanel lobbyPanel;
		SignUpPanel signUpPanel;

		Session.get().bind();
		String sessionId = Session.get().getId();
		Player player = GameSessionSingleton.findOrCreateForSessionId(sessionId);
		String error = GameLogic.addPlayer(gameState.getObject(), player);

		playerName = new PropertyModel(player, "name");

		if (error != null) {
			System.err.println(error);
		}

		signUpPanel = new SignUpPanel("signUpPanel", gameState, playerName);
		lobbyPanel = new LobbyPanel("lobbyPanel", gameState, playerName);
		gamePanel = new GamePanel("gamePanel", gameState);

		Label updater = new Label("updater", "");
		updater.add(new AjaxSelfUpdatingTimerBehavior(Duration.ofSeconds(1)) {
			@Override
			protected void onPostProcessTarget(final AjaxRequestTarget target) {
				super.onPostProcessTarget(target);

				Player player = GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());
				GameState gameState = GameSessionSingleton.getTheSingleton().getGameState();
				if (player != null && player.getLastGameStateVersionRendered() != gameState.getVersion()) {
					target.add(signUpPanel);
					target.add(gamePanel);
					target.add(lobbyPanel);
					player.setLastGameStateVersionRendered(gameState.getVersion());
				}

			}
		});
		add(updater);


		add(signUpPanel);
		add(lobbyPanel);
		add(gamePanel);

	}
}
