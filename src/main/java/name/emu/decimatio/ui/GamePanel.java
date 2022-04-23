package name.emu.decimatio.ui;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.model.CommanderImageModel;
import name.emu.decimatio.model.CommanderPositionModel;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.Legionnaire;
import name.emu.decimatio.model.LegionnaireImageModel;
import name.emu.decimatio.model.LegionnairePositionModel;
import name.emu.decimatio.model.Player;
import name.emu.decimatio.model.PlayerLegionnaireModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class GamePanel extends Panel {

    private IModel<GameState> gameState;

    private List<WebMarkupContainer> imgContainers = new ArrayList<>();

    public GamePanel(final String id, IModel<GameState> gameState) {
        super(id, gameState);
        this.gameState = gameState;

        RepeatingView legionnaireImageRow = new RepeatingView("imageRow");
        RepeatingView legionnaireNameRow = new RepeatingView("nameRow");
        RepeatingView numeralRow = new RepeatingView("numeralRow");

        setOutputMarkupId(true);
        Label updater = new Label("updater", "");
        updater.add(new AjaxSelfUpdatingTimerBehavior(Duration.ofSeconds(1)) {
            @Override
            protected void onPostProcessTarget(final AjaxRequestTarget target) {
                super.onPostProcessTarget(target);

                Player player = GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());
                GameState gameState = GameSessionSingleton.getTheSingleton().getGameState();
                if (player != null && player.getLastGameStateVersionRendered() != gameState.getVersion()) {
                    target.add(GamePanel.this);
                    player.setLastGameStateVersionRendered(gameState.getVersion());
                }

            }
        });
        add(updater);

        for (int i=0; i<10; i++) {
            WebMarkupContainer container = new WebMarkupContainer(legionnaireImageRow.newChildId());
            container.add(new CachingImage("legionnaireImage", new LegionnaireImageModel(new LegionnairePositionModel(gameState, i))));
            container.add(new CachingImage("commanderImage", new CommanderImageModel(new CommanderPositionModel(gameState, i))));
            legionnaireImageRow.add(container);

            IModel<Legionnaire> legionnairePositionModel = new LegionnairePositionModel(gameState, i);
            legionnaireNameRow.add(new Label(legionnaireNameRow.newChildId(), new PropertyModel(legionnairePositionModel, "name")));

            int posNum = (10 + i - (gameState.getObject().getTenthSlotPos())+1) % 10;
            Label posLabel = new Label(numeralRow.newChildId(), Model.of(toRomanNumeral(posNum)));
            if (posNum == 0) {
                posLabel.add(new AttributeModifier("class", "numeral decimatio"));
            }
            numeralRow.add(posLabel);
        }

        add(legionnaireImageRow);
        add(legionnaireNameRow);
        add(new ControlsPanel("controls", new PlayerLegionnaireModel()));
        add(numeralRow);
    }

    private String toRomanNumeral(final int posNum) {
        String str;

        switch (posNum) {
            case 0:
                str = "X";
                break;
            case 1:
                str = "I";
                break;
            case 2:
                str = "II";
                break;
            case 3:
                str = "III";
                break;
            case 4:
                str = "IV";
                break;
            case 5:
                str = "V";
                break;
            case 6:
                str = "VI";
                break;
            case 7:
                str = "VII";
                break;
            case 8:
                str = "VIII";
                break;
            case 9:
                str = "IX";
                break;
            default:
                str = null;
        }
        return str;
    }
}
