package Partie2.gui;

import Partie2.processors.CouplingProcessor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import spoon.reflect.CtModel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CouplingGraphWindow {

    public static void showGraph(CtModel model) {
        System.setProperty("org.graphstream.ui", "swing");

        CouplingProcessor processor = new CouplingProcessor(model);
        Map<String, Double> coupling = processor.computeNormalizedCoupling();

        if (coupling.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun couplage détecté.");
            return;
        }

        Graph graph = new SingleGraph("Graphe de couplage");

        graph.setAttribute("ui.stylesheet", """
            node {
                fill-color: #E8F5E9;
                size-mode: fit;
                padding: 15px, 15px;
                shape: box;
                stroke-mode: plain;
                stroke-color: #388E3C;
                text-alignment: center;
                text-size: 12;
            }
            edge {
                fill-color: #388E3C;
                text-alignment: above;
                text-background-mode: rounded-box;
                text-background-color: white;
                size: 2px;
            }
        """);

        for (String key : coupling.keySet()) {
            String[] classes = key.split("↔");
            for (String cls : classes) {
                if (graph.getNode(cls) == null) {
                    graph.addNode(cls).setAttribute("ui.label", cls);
                }
            }
        }

        for (Map.Entry<String, Double> entry : coupling.entrySet()) {
            String[] classes = entry.getKey().split("↔");
            String edgeId = classes[0] + "->" + classes[1];

            if (graph.getEdge(edgeId) == null && graph.getEdge(classes[1] + "->" + classes[0]) == null) {
                Edge e = graph.addEdge(edgeId, classes[0], classes[1], true);
                e.setAttribute("ui.label", String.format("%.2f", entry.getValue()));
                e.setAttribute("weight", entry.getValue());
            }
        }

        // Affiche le graphe
        Viewer viewer = graph.display();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }
}
