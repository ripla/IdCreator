package org.vaadin.risto.idcreator;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author Risto Yrjänä / Vaadin
 */
@SuppressWarnings("serial")
public class IdCreatorDemoUI extends UI implements IdPathRoot {

    private ContainerLayout fieldLayout;

    @Override
    protected void init(VaadinRequest request) {
        Page.getCurrent().setTitle("IdCreator demo");

        fieldLayout = new ContainerLayout();
        fieldLayout.setSpacing(true);
        fieldLayout.setMargin(true);

        // not part of the id paths
        Panel contentPanel = new Panel("IdCreator demo");
        VerticalLayout fullscreenLayout = new VerticalLayout();
        VerticalLayout panelLayout = new VerticalLayout();
        Label info = new Label(
                "The IdCreator helps setting meaningful and unique id's to components. The id generation uses the component tree to build a path that is used as a unique id for the component. Try inspecting the ids of the fields below with FireBug or Developer tools and compare that to the id's set in the source code.");

        panelLayout.setMargin(true);
        panelLayout.setSpacing(true);
        panelLayout.setSizeFull();
        contentPanel.setWidth("50%");
        contentPanel.setHeight("80%");
        fullscreenLayout.setSizeFull();

        panelLayout.addComponents(info, fieldLayout);
        contentPanel.setContent(panelLayout);
        fullscreenLayout.addComponent(contentPanel);
        setContent(fullscreenLayout);

        fullscreenLayout.setComponentAlignment(contentPanel,
                Alignment.MIDDLE_CENTER);
        panelLayout.setExpandRatio(fieldLayout, 1.0f);
    }

    @Override
    public String idStringFromChild(Component child) {
        return "demoUI";
    }

}
