package name.emu.decimatio.ui;

import name.emu.decimatio.model.GameState;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;

public class SignUpPanel extends Panel {

    private IModel<GameState> gameState;

    private IModel<String> playerName;

    private IModel<String> inquiryModel;

    private GlobalRefreshCallback refreshCallback;

    public SignUpPanel(final String id, IModel<GameState> gameState, IModel<String> playerName, GlobalRefreshCallback refreshCallback) {
        super(id);
        Form form = new Form("form");
        this.gameState = gameState;
        this.playerName = playerName;
        this.refreshCallback = refreshCallback;

        this.setOutputMarkupPlaceholderTag(true);
        inquiryModel = Model.of("What is your name, legionnaire?");

        Label inquiry = new Label("inquiry", inquiryModel);
        inquiry.setOutputMarkupId(true);
        TextField<String> name = new TextField("name", playerName);
        AjaxButton submit = new AjaxButton("submit") {
            @Override
            protected void onSubmit(final AjaxRequestTarget target) {
                super.onSubmit(target);
                String name = playerName.getObject();
                String newInquiry = null;
                if (Strings.isEmpty(name)) {
                    newInquiry = "Silence will not help you! I will have your name or your head, soldier!";
                } else if (name.length() > 10) {
                    newInquiry = "I won't remember that. Try something shorter! (max 10 characters)";
                }

                if (newInquiry != null) {
                    inquiryModel.setObject(newInquiry);
                    target.add(inquiry);
                } else {
                    refreshCallback.globalRefresh(target);
                }
            }
        };
        form.add(inquiry);
        form.add(name);
        form.add(submit);
        add(form);
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        setVisible(Strings.isEmpty(playerName.getObject()));
    }
}