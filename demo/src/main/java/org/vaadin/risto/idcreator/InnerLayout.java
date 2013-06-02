package org.vaadin.risto.idcreator;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class InnerLayout extends HorizontalLayout implements IdPathComponent {

    public InnerLayout() {
        setSpacing(true);
        setMargin(true);
        setHeight("100%");

        TextField field = new TextField("Textfield");
        Button button = new Button("Button");
        WildCustomComponent custom = new WildCustomComponent();

        // set the same id for the components
        // still the path will be different = unique id
        IdCreator.setId(field, "textfield");
        IdCreator.setId(button, "button");
        IdCreator.setId(custom);

        addComponents(field, button, custom);

        setComponentAlignment(field, Alignment.BOTTOM_LEFT);
        setComponentAlignment(button, Alignment.BOTTOM_LEFT);
        setComponentAlignment(custom, Alignment.BOTTOM_LEFT);

        // no id for the innerlayout itself
    }

    @Override
    public String idStringFromChild(Component child) {
        return "innerlayout";
    }
}
