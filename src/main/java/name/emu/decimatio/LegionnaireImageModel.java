package name.emu.decimatio;

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
            boolean isPlayerCharacter = player.getCharacter().equals(legionnaire);
            boolean isNemesis = false;
            String numPart;

            if (isPlayerCharacter) {
                numPart = "02";
            } else if (isNemesis) {
                numPart = "03";
            } else {
                numPart = "01";
            }

            image = "imgs/legionnaire" + numPart + "_idle.png";
        } else {
            image = "imgs/blank.png";
        }
        return new PackageResourceReference(GamePanel.class, image);
    }
}
