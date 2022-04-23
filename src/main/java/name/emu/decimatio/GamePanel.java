package name.emu.decimatio;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class GamePanel extends Panel {

    private IModel<GameState> gameState;

    public GamePanel(final String id, IModel<GameState> gameState) {
        super(id, gameState);
        this.gameState = gameState;

        RepeatingView legionaireImageRow = new RepeatingView("imageRow");
        for (Legionaire legionaire: gameState.getObject().getLegionaires()) {
            WebMarkupContainer container = new WebMarkupContainer(legionaireImageRow.newChildId());
            container.add(new Image("legionaireImage", "imgs/legionaire01_idle.png"));
            legionaireImageRow.add(container);
        }
        add(legionaireImageRow);
        RepeatingView legionaireNameRow = new RepeatingView("nameRow");
        int i=0;
        for (Legionaire legionaire: gameState.getObject().getLegionaires()) {
            IModel<Legionaire> legionairePositionModel = new LegionairePositionModel(gameState, i);
            legionaireNameRow.add(new Label(legionaireNameRow.newChildId(), new PropertyModel(legionairePositionModel, "name")));
            i++;
        }
        add(legionaireNameRow);
    }
}
