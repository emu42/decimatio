package name.emu.decimatio;

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
		GameLogic.addPlayer(gameState.getObject(), player);

		add(new LobbyPanel("lobbyPanel", gameState));
		add(new GamePanel("gamePanel", gameState));

	}
}
