package name.emu.decimatio.ui.model;

import name.emu.decimatio.model.Commander;
import name.emu.decimatio.model.CommanderStatus;
import name.emu.decimatio.ui.GamePanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

public class CommanderImageModel implements IModel<ResourceReference> {

    private final IModel<Commander> commanderModel;

    public CommanderImageModel(final IModel<Commander> commanderModel) {
        this.commanderModel = commanderModel;
    }

    @Override
    public ResourceReference getObject() {
        final String image;
        Commander commander = commanderModel.getObject();
        if (commander != null && commander.getStatus()!= CommanderStatus.ABSENT) {
            String action;
            switch (commander.getStatus()) {
                case FACING_TROOPS:
                    action = "lookingtroops";
                    break;
                case FACING_AWAY:
                    action = "lookingaway";
                    break;
                case STABBING:
                    action = "stab";
                    break;
                default:
                    action = null;
                    break;
            }

            image = "imgs/commander_" + action + ".png";
        } else {
            image = "imgs/blank.png";
        }
        return new PackageResourceReference(GamePanel.class, image);
    }
}
