package Partie2.gui;

import Partie2.processors.CoupledModulesIdentifier;
import Partie2.processors.CoupledModulesIdentifier.ClusterNode;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import spoon.reflect.CtModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ModuleGraphPanel extends JPanel {

    private mxGraphComponent graphComponent;

    public ModuleGraphPanel() {
        setLayout(new BorderLayout());
    }

    public void displayModulesGraph(CtModel model, double cp) {
        removeAll();

        CoupledModulesIdentifier identifier = new CoupledModulesIdentifier(model, cp);
        ClusterNode root = identifier.buildDendrogram();

        if (root == null) {
            JOptionPane.showMessageDialog(this, "Aucune donnée de couplage disponible.");
            return;
        }

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        try {
            Map<ClusterNode, Object> nodeMap = new HashMap<>();
            buildGraphRec(graph, parent, root, nodeMap, true);
        } finally {
            graph.getModel().endUpdate();
        }

        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.setOrientation(SwingConstants.NORTH);
        layout.setInterHierarchySpacing(80);
        layout.execute(graph.getDefaultParent());

        graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        graphComponent.getViewport().setBackground(Color.WHITE);
        graphComponent.getVerticalScrollBar().setUnitIncrement(30);
        graphComponent.getHorizontalScrollBar().setUnitIncrement(30);

        add(graphComponent, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Construire le graphe récursivement
     * @param isRoot indique si on est au niveau racine pour filtrer les enfants
     */
    private Object buildGraphRec(mxGraph graph, Object parent, ClusterNode node,
                                 Map<ClusterNode, Object> nodeMap, boolean isRoot) {
        if (node == null) return null;

        String label = node.isLeaf() ? node.name : node.name + String.format(" (%.2f)", node.coupling);
        String style = node.isLeaf()
                ? "fillColor=#EAF2FF;strokeColor=#3465A4;rounded=1"
                : "fillColor=#CCE5FF;strokeColor=#3366CC;rounded=1;fontStyle=1";

        Object v = graph.insertVertex(parent, null, label, 0, 0, 150, 40, style);
        nodeMap.put(node, v);

        if (node.children != null) {
            for (ClusterNode child : node.children) {
                // **Ne pas afficher les fils directs de la racine qui n'ont pas "Cluster" dans leur nom**
                if (isRoot && !child.name.startsWith("Cluster")) continue;

                Object childVertex = buildGraphRec(graph, parent, child, nodeMap, false);
                if (childVertex != null) {
                    graph.insertEdge(parent, null, "", v, childVertex, "strokeColor=#888888;endArrow=none");
                }
            }
        }

        return v;
    }
}
