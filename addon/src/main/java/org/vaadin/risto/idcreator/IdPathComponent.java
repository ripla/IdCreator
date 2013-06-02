package org.vaadin.risto.idcreator;

import com.vaadin.ui.Component;

/**
 * Provides a string for the whole component id path.
 * 
 * @author Risto Yrjänä / Vaadin
 */
public interface IdPathComponent extends Component {

    /**
     * Create a string representing this component for the given child. The
     * child reference can be used to create a suitable index. It can be
     * discarded if indexes are not needed.
     * 
     * @param child
     *            child for which the id should be created or null id is for
     *            this component
     * @return
     */
    public String idStringFromChild(Component child);
}
