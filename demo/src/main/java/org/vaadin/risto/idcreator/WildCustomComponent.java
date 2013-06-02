package org.vaadin.risto.idcreator;

import com.vaadin.data.Property;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
public class WildCustomComponent extends CheckBox implements IdPathComponent {

    public WildCustomComponent() {
        addValueChangeListener(new Property.ValueChangeListener() {

            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Notification.show("Value is " + event.getProperty().getValue());
            }
        });
    }

    // this way custom components can handle their own ids
    @Override
    public String idStringFromChild(Component child) {
        return "wildrover";
    }

}
