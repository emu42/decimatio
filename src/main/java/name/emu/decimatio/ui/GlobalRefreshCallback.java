package name.emu.decimatio.ui;

import org.apache.wicket.ajax.AjaxRequestTarget;

public interface GlobalRefreshCallback {

    void globalRefresh(AjaxRequestTarget target);
}
