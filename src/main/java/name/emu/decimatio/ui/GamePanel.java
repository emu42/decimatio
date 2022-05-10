package name.emu.decimatio.ui;

import java.util.ArrayList;
import java.util.List;

import name.emu.decimatio.ui.model.*;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStatus;
import name.emu.decimatio.model.Legionnaire;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class GamePanel extends Panel {

    private IModel<GameState> gameState;

    private List<WebMarkupContainer> imgContainers = new ArrayList<>();

    private GlobalRefreshCallback refreshCallback;

    public GamePanel(final String id, IModel<GameState> gameState, GlobalRefreshCallback refreshCallback) {
        super(id, gameState);
        this.gameState = gameState;
        this.refreshCallback = refreshCallback;
        RepeatingView legionnaireImageRow = new RepeatingView("imageRow");
        RepeatingView legionnaireNameRow = new RepeatingView("nameRow");
        RepeatingView numeralRow = new RepeatingView("numeralRow");

        this.setOutputMarkupPlaceholderTag(true);

        for (int i=0; i<10; i++) {
            WebMarkupContainer container = new WebMarkupContainer(legionnaireImageRow.newChildId());
            container.add(new CachingImage("legionnaireImage", new LegionnaireImageModel(new LegionnairePositionModel(gameState, i))));
            container.add(new CachingImage("commanderImage", new CommanderImageModel(new CommanderPositionModel(gameState, i))));
            legionnaireImageRow.add(container);

            IModel<Legionnaire> legionnairePositionModel = new LegionnairePositionModel(gameState, i);
            legionnaireNameRow.add(new Label(legionnaireNameRow.newChildId(), new PropertyModel(legionnairePositionModel, "name")));

            IModel<String> posLabelModel = new PosNumeralModel(gameState, i);
            Label posLabel = new Label(numeralRow.newChildId(), posLabelModel);
            posLabel.add(new AttributeModifier("class", new NumeralCssClassModel(gameState, i)));
            numeralRow.add(posLabel);
        }

        add(legionnaireImageRow);
        add(legionnaireNameRow);
        add(new ControlsPanel("controls", new PlayerLegionnaireModel()));
        add(numeralRow);
    }



    @Override
    protected void onConfigure() {
        super.onConfigure();
        setVisible(gameState.getObject()!=null && (gameState.getObject().getStatus() == GameStatus.INPUT || gameState.getObject().getStatus() == GameStatus.SCORE || gameState.getObject().getStatus() == GameStatus.MOVING || gameState.getObject().getStatus() == GameStatus.ENDROUND));
    }
}
