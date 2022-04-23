package name.emu.decimatio.ui;

import name.emu.decimatio.GameLogic;
import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStateModel;
import name.emu.decimatio.model.Player;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		IModel<GameState> gameState = new GameStateModel();

		Session.get().bind();
		String sessionId = Session.get().getId();
		Player player = GameSessionSingleton.findOrCreateForSessionId(sessionId);
		String error = GameLogic.addPlayer(gameState.getObject(), player);

		if (error != null) {
			System.err.println(error);
		}

		add(new LobbyPanel("lobbyPanel", gameState));
		add(new GamePanel("gamePanel", gameState));

	}
}
