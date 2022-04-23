package name.emu.decimatio;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ControlsPanel extends Panel {

    private IModel<Legionnaire> legionnaireModel;

    public ControlsPanel(final String id, final IModel<Legionnaire> model) {
        super(id, model);
        this.legionnaireModel = model;

        Form form = new Form("form");
        AjaxButton shoveLeftButton = new AjaxButton("shoveLeft") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                legionnaireModel.getObject().setUpcomingMove(Move.PUSH_LEFT);
            }
        };

        AjaxButton shoveRightButton = new AjaxButton("shoveRight") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                legionnaireModel.getObject().setUpcomingMove(Move.PUSH_RIGHT);
            }
        };

        form.add(shoveLeftButton);
        form.add(shoveRightButton);
        add(form);
    }
}
