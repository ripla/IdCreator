package org.vaadin.risto.idcreator;

import static org.junit.Assert.assertEquals;
import static org.vaadin.risto.idcreator.IdCreator.createId;
import static org.vaadin.risto.idcreator.IdCreator.setId;

import org.junit.Test;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

public class IdCreatorTest {

    @Test
    public void correctIdForRootCreated() {
        final IdPathVisitableLayout stub = new IdPathVisitableLayout();
        stub.setIdStringForChild("test");

        assertEquals("Wrong id created for root", "test", createId(stub));
    }

    @Test
    public void correctIdForSmallHierarchySet() {
        final Button child = new Button();

        final IdPathVisitableLayout leaf = new IdPathVisitableLayout();
        leaf.setIdStringForChild("leaf");

        final IdPathVisitableLayoutRoot root = new IdPathVisitableLayoutRoot();
        root.setIdStringForChild("root");

        root.addComponent(leaf);
        leaf.addComponent(child);

        setId(child, "child");

        assertEquals("Wrong id set for hierarchy", "root-leaf-child",
                child.getId());
    }

    @Test
    public void correctIdForLargeHierarchySet() {
        final Button child = new Button();

        final IdPathVisitableLayout cell1 = new IdPathVisitableLayout();
        cell1.setIdStringForChild("cell1");

        final IdPathVisitableLayout cell2 = new IdPathVisitableLayout();
        cell2.setIdStringForChild("cell2");

        final IdPathVisitableLayout cell3 = new IdPathVisitableLayout();
        cell3.setIdStringForChild("cell3");

        final IdPathVisitableLayoutRoot root = new IdPathVisitableLayoutRoot();
        root.setIdStringForChild("root");

        root.addComponent(cell1);
        cell1.addComponent(cell2);
        cell2.addComponent(cell3);
        cell3.addComponent(child);

        setId(child, "child");

        assertEquals("Wrong id set for hierarchy",
                "root-cell1-cell2-cell3-child", child.getId());
    }

    @Test
    public void correctIdForLargeHierarchyCreated() {
        final IdPathVisitableLayout cell1 = new IdPathVisitableLayout();
        cell1.setIdStringForChild("cell1");

        final IdPathVisitableLayout cell2 = new IdPathVisitableLayout();
        cell2.setIdStringForChild("cell2");

        final IdPathVisitableLayout cell3 = new IdPathVisitableLayout();
        cell3.setIdStringForChild("cell3");

        final IdPathVisitableLayoutRoot root = new IdPathVisitableLayoutRoot();
        root.setIdStringForChild("root");

        root.addComponent(cell1);
        cell1.addComponent(cell2);
        cell2.addComponent(cell3);

        assertEquals("Wrong id created for hierarchy",
                "root-cell1-cell2-cell3", createId(cell3));
    }

    @Test
    public void correctIdForPartialHierarchyCreated() {
        final VerticalLayout cell1 = new VerticalLayout();

        final VerticalLayout cell2 = new VerticalLayout();

        final IdPathVisitableLayout cell3 = new IdPathVisitableLayout();
        cell3.setIdStringForChild("cell3");

        final IdPathVisitableLayoutRoot root = new IdPathVisitableLayoutRoot();
        root.setIdStringForChild("root");

        root.addComponent(cell1);
        cell1.addComponent(cell2);
        cell2.addComponent(cell3);

        assertEquals("Wrong id created for hierarchy", "root-cell3",
                createId(cell3));
    }

    @Test
    public void correctIdForRootlesHierarchyCreated() {
        final IdPathVisitableLayout cell1 = new IdPathVisitableLayout();
        cell1.setIdStringForChild("cell1");

        final IdPathVisitableLayout cell2 = new IdPathVisitableLayout();
        cell2.setIdStringForChild("cell2");

        final IdPathVisitableLayout cell3 = new IdPathVisitableLayout();
        cell3.setIdStringForChild("cell3");

        final IdPathVisitableLayout root = new IdPathVisitableLayout();
        root.setIdStringForChild("root");

        root.addComponent(cell1);
        cell1.addComponent(cell2);
        cell2.addComponent(cell3);

        assertEquals("Wrong id created for hierarchy",
                "root-cell1-cell2-cell3", createId(cell3));
    }

    @Test
    public void correctIdForDistantFirstParentCreatedWhenLeafIsIdPathComponent() {
        final VerticalLayout parent = new VerticalLayout();
        final IdPathVisitableLayout leaf = new IdPathVisitableLayout();
        final IdPathVisitableLayoutRoot root = new IdPathVisitableLayoutRoot();

        root.setIdStringForChild("root");
        leaf.setIdStringForChild("leaf");

        root.addComponent(parent);
        parent.addComponent(leaf);

        setId(leaf);

        assertEquals("Wrong id created for hierachy", "root-leaf", leaf.getId());
    }

    @Test
    public void correctIdForDistantFirstParentCreatedWhenLeafIsVaadinComponent() {
        final VerticalLayout parent = new VerticalLayout();
        final VerticalLayout leaf = new VerticalLayout();
        final IdPathVisitableLayoutRoot root = new IdPathVisitableLayoutRoot();

        root.setIdStringForChild("root");

        root.addComponent(parent);
        parent.addComponent(leaf);

        setId(leaf, "leaf");

        assertEquals("Wrong id created for hierachy", "root-leaf", leaf.getId());
    }

    public static class IdPathVisitableLayout extends VerticalLayout implements
            IdPathComponent {

        private static final long serialVersionUID = 2085523428431505587L;
        private String id;

        public void setIdStringForChild(final String id) {
            this.id = id;
        }

        @Override
        public String idStringFromChild(final Component child) {
            return id;
        }
    }

    public static class IdPathVisitableLayoutRoot extends IdPathVisitableLayout
            implements IdPathRoot {
        private static final long serialVersionUID = 8422404616851610152L;
    }
}
