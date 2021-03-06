package name.emu.decimatio.model;

import name.emu.decimatio.GameSessionSingleton;
import name.emu.decimatio.ui.GamePanel;
import org.apache.wicket.Session;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class LegionnaireImageModel implements IModel<ResourceReference> {

    private final IModel<Legionnaire> legionnaireModel;

    public LegionnaireImageModel(final IModel<Legionnaire> legionnaireModel) {
        this.legionnaireModel = legionnaireModel;
    }

    @Override
    public ResourceReference getObject() {
        final String image;
        Legionnaire legionnaire = legionnaireModel.getObject();
        if (legionnaire != null) {
            Player player = GameSessionSingleton.findOrCreateForSessionId(Session.get().getId());
            boolean isPlayerCharacter = player.getCharacter() != null && player.getCharacter().equals(legionnaire);
            boolean isNemesis = player.getCharacter() != null && legionnaire.equals(player.getCharacter().getNemesis());
            String numPart;
            String action = "idle";
            if (isPlayerCharacter) {
                numPart = "02";
            } else if (isNemesis) {
                numPart = "03";
            } else {
                numPart = "01";
            }

            GameStatus status = GameSessionSingleton.findForGameSessionId(player.getGameSessionId()).getStatus();
            if (status == GameStatus.MOVING) {
                switch (legionnaire.getUpcomingMove()) {
                    case PUSH_LEFT:
                        action = "pushleft";
                        break;
                    case PUSH_RIGHT:
                        action = "pushright";
                        break;
                    default:
                        action = "idle";
                        break;
                }
            } else if (status == GameStatus.ENDROUND || status == GameStatus.SCORE) {
                switch (legionnaire.getUpcomingMove()) {
                    case BEING_STABBED:
                        action = "stabbed";
                        break;
                    case DEAD:
                        action = "dead";
                        break;
                    default:
                        action = "idle";
                        break;
                }
            }

            image = "imgs/legionnaire" + numPart + "_" + action + ".png";
        } else {
            image = "imgs/blank.png";
        }
        return new PackageResourceReference(GamePanel.class, image);
    }
}
