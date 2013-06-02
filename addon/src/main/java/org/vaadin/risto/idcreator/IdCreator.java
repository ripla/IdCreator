package org.vaadin.risto.idcreator;

import static java.lang.String.format;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Component;

/**
 * <p>
 * Helper for creating debug id paths from components that implement
 * {@link IdPathComponent} to a component that implements {@link IdPathRoot}.
 * </p>
 * 
 * <p>
 * For example, given the tree of components (and matching ids)
 * <code>"master" -> "leftLayout" -> "selectionTree"</code> the call
 * <code>IdCreator.createId(leftLayout)</code> would return the string
 * "master-leftLayout-selectionTree".
 * </p>
 * 
 * @author Risto Yrjänä / Vaadin
 */
public class IdCreator {

    private static boolean idCreatingAllowed = true;

    private static final char IDSEPARATOR = '-';

    /**
     * Build an id string from the given leaf to a {@link IdPathRoot}
     * 
     * @param leaf
     * @return
     */
    public static String createId(final IdPathComponent leaf) {
        if (!idCreatingAllowed) {
            return null;
        }

        if (leaf == null) {
            throw new IllegalArgumentException("Leaf cannot be null");
        }

        final Deque<String> idPathStack = new ArrayDeque<String>();
        idPathStack.push(leaf.idStringFromChild(null));

        Component item = leaf;
        IdPathComponent lastIdPathComponent = leaf;
        while (item.getParent() != null && !(item instanceof IdPathRoot)) {
            if (item.getParent() instanceof IdPathComponent) {
                IdPathComponent idPathParent = (IdPathComponent) item
                        .getParent();
                String idStringForChild = idPathParent
                        .idStringFromChild(lastIdPathComponent);

                if (idStringForChild == null) {
                    throw new IllegalStateException(format(
                            "Got null id string for %s from parent %s",
                            idStringForChild, idPathParent));
                }

                idPathStack.push(idStringForChild);
                lastIdPathComponent = idPathParent;
            }
            item = item.getParent();
        }

        return join(idPathStack);
    }

    /**
     * Create the id like in {@link #createId(IdPathComponent)} and set it for
     * the component. The invocation is performed when the component is attached
     * to the component tree.
     * 
     * @param component
     */
    @SuppressWarnings("serial")
    public static void setId(final IdPathComponent component) {
        if (!idCreatingAllowed) {
            return;
        }

        // if component has parent, assume attached
        if (component.getParent() != null) {
            component.setId(createId(component));
        } else {
            component.addAttachListener(new AttachListener() {

                @Override
                public void attach(AttachEvent event) {
                    component.setId(createId(component));

                }
            });
        }
    }

    /**
     * Create the id like in {@link #createId(IdPathComponent)}, and set it on
     * the component with the String parameter as a last id. This method assumes
     * that the component has a parent somewhere up the component tree that
     * implements {@link biz.ifint.sps.ui.common.util.IdPathComponent}. The
     * invocation is performed when the component is attached to the component
     * tree.
     * 
     * @param component
     * @param name
     */
    @SuppressWarnings("serial")
    public static void setId(final Component component, final String name) {
        if (!idCreatingAllowed) {
            return;
        }

        if (component == null) {
            throw new IllegalArgumentException(format(
                    "Null component for name %s", name));
        }

        // if component has parent, assume attached
        if (component.getParent() != null) {
            setIdWithName(component, name);

        } else {
            component.addAttachListener(new AttachListener() {

                @Override
                public void attach(AttachEvent event) {
                    setIdWithName(component, name);
                }
            });
        }
    }

    /**
     * Whether creating ids is allowed.
     * 
     * @param allowed
     */
    public static void setIdCreatingAllowed(final boolean allowed) {
        idCreatingAllowed = allowed;
    }

    public static boolean isIdCreatingAllowed() {
        return idCreatingAllowed;
    }

    /**
     * Create a string for a child id, e.g. <code>parentId-childId-1-3-2</code>.
     * This is just a helper, the format it creates is not required in any way.
     * 
     * @param parent
     * @param childIndexes
     * @return
     */
    public static String childString(final String parent,
            final int... childIndexes) {
        for (final int childIndex : childIndexes) {
            if (childIndex < 0) {
                throw new IllegalArgumentException(
                        "Child indexes should start from 0, got " + childIndex);
            }
        }

        final Object[] chilComponentsdArray = new Object[childIndexes.length + 2];
        // first two parts of the parent-child id string are parent name and
        // "child"
        chilComponentsdArray[0] = parent;
        chilComponentsdArray[1] = "child";

        for (int i = 2; i < chilComponentsdArray.length; i++) {
            final int childIndex = childIndexes[i - 2];
            chilComponentsdArray[i] = Integer.valueOf(childIndex);
        }

        return join(chilComponentsdArray);
    }

    private static void setIdWithName(final Component component,
            final String name) {
        final IdPathComponent parent = findClosestIdPathParent(component);
        if (parent == null) {
            throw new IllegalStateException(
                    "No IdPathComponent parent found for " + component);
        }
        component.setId(join(createId(parent), name));
    }

    private static String join(Collection<? extends Object> collection) {
        if (collection.size() == 0) {
            return "";

        } else if (collection.size() == 1) {
            return collection.iterator().next().toString();

        } else {
            Iterator<? extends Object> iterator = collection.iterator();
            StringBuilder builder = new StringBuilder().append(iterator.next());
            while (iterator.hasNext()) {
                builder.append(IDSEPARATOR);
                builder.append(iterator.next());
            }

            return builder.toString();
        }

    }

    private static String join(Object... strings) {
        return join(Arrays.asList(strings));
    }

    private static IdPathComponent findClosestIdPathParent(final Component leaf) {
        Component item = leaf;

        while (item.getParent() != null) {
            if (item.getParent() instanceof IdPathComponent) {
                return (IdPathComponent) item.getParent();
            }
            item = item.getParent();
        }

        return null;
    }
}
