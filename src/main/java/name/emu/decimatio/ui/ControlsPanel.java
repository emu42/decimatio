package name.emu.decimatio.ui;

import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStateModel;
import name.emu.decimatio.model.GameStatus;
import name.emu.decimatio.model.Legionnaire;
import name.emu.decimatio.model.Move;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class ControlsPanel extends Panel {

    private IModel<Legionnaire> legionnaireModel;

    private IModel<GameState> gameStateModel = new GameStateModel();

    public ControlsPanel(final String id, final IModel<Legionnaire> model) {
        super(id, model);
        this.legionnaireModel = model;

        Form form = new Form("form");
        AjaxButton shoveLeftButton = new AjaxButton("shoveLeft") {

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setEnabled(gameStateModel.getObject().getStatus() == GameStatus.INPUT && legionnaireModel.getObject()!=null && legionnaireModel.getObject().getUpcomingMove() == Move.NONE);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                legionnaireModel.getObject().setUpcomingMove(Move.PUSH_LEFT);
                target.add(form);
            }
        };

        AjaxButton shoveRightButton = new AjaxButton("shoveRight") {
            @Override
            protected void onConfigure() {
                super.onConfigure();
                setEnabled(gameStateModel.getObject().getStatus() == GameStatus.INPUT && legionnaireModel.getObject()!=null && legionnaireModel.getObject().getUpcomingMove() == Move.NONE);
            }

            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                legionnaireModel.getObject().setUpcomingMove(Move.PUSH_RIGHT);
                target.add(form);
            }
        };

        form.add(shoveLeftButton);
        form.add(shoveRightButton);
        add(form);
    }
}
