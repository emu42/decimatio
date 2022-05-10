package name.emu.decimatio.ui;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public class CachingImage extends Image {
    public CachingImage(final String id) {
        super(id);
    }

    public CachingImage(final String id, final ResourceReference resourceReference, final ResourceReference... resourceReferences) {
        super(id, resourceReference, resourceReferences);
    }

    public CachingImage(final String id, final ResourceReference resourceReference, final PageParameters resourceParameters, final ResourceReference... resourceReferences) {
        super(id, resourceReference, resourceParameters, resourceReferences);
    }

    public CachingImage(final String id, final IResource imageResource, final IResource... imageResources) {
        super(id, imageResource, imageResources);
    }

    public CachingImage(final String id, final IModel<?> model) {
        super(id, model);
    }

    public CachingImage(final String id, final String string) {
        super(id, string);
    }

    @Override
    protected boolean shouldAddAntiCacheParameter() {
        return false;
    }
}
