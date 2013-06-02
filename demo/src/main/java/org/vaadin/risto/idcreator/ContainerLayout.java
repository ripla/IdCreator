package org.vaadin.risto.idcreator;

import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ContainerLayout extends VerticalLayout implements IdPathComponent {

    public ContainerLayout() {
        setSpacing(true);
        setMargin(true);
        // setSizeFull();

        for (int i = 0; i < 4; i++) {
            addComponent(new InnerLayout());
        }

        IdCreator.setId(this);
    }

    @Override
    public String idStringFromChild(Component child) {
        if (child != null) {
            return IdCreator.childString("container", getComponentIndex(child));
        } else {
            return "container";
        }
    }
}
