package name.emu.decimatio.ui;

import java.util.ArrayList;
import java.util.List;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.Legionnaire;
import name.emu.decimatio.model.LegionnaireImageModel;
import name.emu.decimatio.model.LegionnairePositionModel;
import name.emu.decimatio.model.PlayerLegionnaireModel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class GamePanel extends Panel {

    private IModel<GameState> gameState;

    private List<WebMarkupContainer> imgContainers = new ArrayList<>();

    public GamePanel(final String id, IModel<GameState> gameState) {
        super(id, gameState);
        this.gameState = gameState;

        RepeatingView legionnaireImageRow = new RepeatingView("imageRow");
        RepeatingView legionnaireNameRow = new RepeatingView("nameRow");

        for (int i=0; i<10; i++) {
            WebMarkupContainer container = new WebMarkupContainer(legionnaireImageRow.newChildId());
            container.add(new Image("legionnaireImage", new LegionnaireImageModel(new LegionnairePositionModel(gameState, i))));
            legionnaireImageRow.add(container);

            IModel<Legionnaire> legionairePositionModel = new LegionnairePositionModel(gameState, i);
            legionnaireNameRow.add(new Label(legionnaireNameRow.newChildId(), new PropertyModel(legionairePositionModel, "name")));
        }

        add(legionnaireImageRow);
        add(legionnaireNameRow);
        add(new ControlsPanel("controls", new PlayerLegionnaireModel()));
    }
}
