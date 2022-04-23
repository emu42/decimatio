package name.emu.decimatio;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.WebPage;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		GameState gameState;
		List<Legionaire> legionaires = new ArrayList<>();
		for (int i=0; i<10; i++) {
			legionaires.add(Legionaire.builder().name("L"+i).playerCharacter(false).upcomingMove(Move.NONE).build());
		}
		gameState = GameState.builder().legionaires(legionaires).build();

		add(new GamePanel("gamePanel", Model.of(gameState)));

		// TODO Add your page's components here

	}
}
