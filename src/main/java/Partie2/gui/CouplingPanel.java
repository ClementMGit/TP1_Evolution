package Partie2.gui;

import Partie2.processors.CouplingProcessor;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import spoon.reflect.CtModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CouplingPanel extends JPanel {

    private mxGraphComponent graphComponent;

    public CouplingPanel() {
        setLayout(new BorderLayout());
    }

    public void displayCouplingGraph(CtModel model) {
        removeAll();

        CouplingProcessor processor = new CouplingProcessor(model);
        Map<String, Double> coupling = processor.computeNormalizedCoupling();

        if (coupling.isEmpty()) {
            add(new JLabel("Aucun couplage détecté."), BorderLayout.CENTER);
            revalidate();
            repaint();
            return;
        }

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        try {
            Map<String, Object> classNodes = new HashMap<>();

            // --- Création des sommets ---
            for (String key : coupling.keySet()) {
                String[] classes = key.split("↔");
                for (String cls : classes) {
                    classNodes.computeIfAbsent(cls, c ->
                            graph.insertVertex(parent, null, c, 0, 0, 120, 40,
                                    "fillColor=#E8F5E9;strokeColor=#388E3C;rounded=1;fontColor=#000000;fontSize=12"));
                }
            }

            // --- Création des arêtes pondérées ---
            for (Map.Entry<String, Double> entry : coupling.entrySet()) {
                String[] classes = entry.getKey().split("↔");
                Object a = classNodes.get(classes[0]);
                Object b = classNodes.get(classes[1]);
                double weight = entry.getValue();
                String label = String.format("%.2f", weight);

                graph.insertEdge(parent, null, label, a, b,
                        "strokeColor=#388E3C;fontColor=#1B5E20;strokeWidth=" + (1 + weight * 10));
            }

        } finally {
            graph.getModel().endUpdate();
        }

        // --- Layout organique pour une bonne lisibilité ---
        mxFastOrganicLayout layout = new mxFastOrganicLayout(graph);
        layout.setForceConstant(100.0); // force d'expansion
        layout.setMinDistanceLimit(50.0);
        layout.setMaxIterations(1000); // plus d'itérations pour stabiliser
        layout.setResetEdges(true);
        layout.execute(graph.getDefaultParent());

        // --- Composant graphique ---
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        graphComponent.getViewport().setOpaque(true);
        graphComponent.getViewport().setBackground(Color.WHITE);

        // Zoom automatique et centrage
        graphComponent.zoomAndCenter();

        add(graphComponent, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
