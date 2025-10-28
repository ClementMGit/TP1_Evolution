package Partie2.gui;

import Partie2.processors.CouplingProcessor;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.camera.Camera;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import spoon.reflect.CtModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CouplingGraphWindow {

    public static void showGraph(CtModel model) {
        System.setProperty("org.graphstream.ui", "swing");
        CouplingProcessor processor = new CouplingProcessor(model);
        Map<String, Double> coupling = processor.computeNormalizedCoupling();

        if (coupling.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aucun couplage détecté.");
            return;
        }

        Graph graph = new MultiGraph("Live long and prosper");
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

        // --- Étape 1 : Collecter tous les noms simples pour détecter les doublons ---
        Map<String, Set<String>> simpleNameToFullNames = new HashMap<>();
        for (String key : coupling.keySet()) {
            String[] classes = key.split("↔");
            for (String clsFull : classes) {
                String simple = clsFull.substring(clsFull.lastIndexOf('.') + 1);
                simpleNameToFullNames
                        .computeIfAbsent(simple, k -> new HashSet<>())
                        .add(clsFull);
            }
        }

        // --- Étape 2 : Créer les nœuds ---
        for (String key : coupling.keySet()) {
            String[] classes = key.split("↔");
            for (String clsFull : classes) {
                String simple = clsFull.substring(clsFull.lastIndexOf('.') + 1);
                boolean isDuplicate = simpleNameToFullNames.get(simple).size() > 1;

                String label = isDuplicate
                        ? simple + "\n(" + getPackageName(clsFull) + ")"
                        : simple;

                if (graph.getNode(clsFull) == null) {
                    graph.addNode(clsFull).setAttribute("ui.label", label);
                    graph.getNode(clsFull).setAttribute("layout.mass", 2.0);
                }
            }
        }

        // --- Étape 3 : Créer les arêtes ---
        for (Map.Entry<String, Double> entry : coupling.entrySet()) {
            String[] classes = entry.getKey().split("↔");
            String source = classes[0];
            String target = classes[1];
            String edgeId = source + "->" + target;

            if (graph.getEdge(edgeId) == null && graph.getEdge(target + "->" + source) == null) {
                Edge e = graph.addEdge(edgeId, source, target, true);
                e.setAttribute("ui.label", String.format("%.2f", entry.getValue()));
                e.setAttribute("weight", entry.getValue());
            }
        }

        // --- Étape 4 : Créer le viewer à partir du graph ---
        Viewer viewer = graph.display(false);
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);

        // Récupération de la vue principale
        View view = viewer.getDefaultView();
        Camera cam = view.getCamera();
        cam.setViewPercent(1);

        // --- Ajout du zoom à la molette ---
        ((Component) view).addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                e.consume();
                int i = e.getWheelRotation();
                double factor = Math.pow(1.25, i);
                double zoom = cam.getViewPercent() * factor;

                Point2 pxCenter = cam.transformGuToPx(cam.getViewCenter().x, cam.getViewCenter().y, 0);
                Point3 guClicked = cam.transformPxToGu(e.getX(), e.getY());
                double newRatioPx2Gu = cam.getMetrics().ratioPx2Gu / factor;
                double x = guClicked.x + (pxCenter.x - e.getX()) / newRatioPx2Gu;
                double y = guClicked.y - (pxCenter.y - e.getY()) / newRatioPx2Gu;
                cam.setViewCenter(x, y, 0);
                cam.setViewPercent(zoom);
            }
        });

        // --- Étape 5 : Appliquer le layout SpringBox ---
        Layout layout = new SpringBox(false); // false = 2D
        graph.addSink(layout);
        layout.addAttributeSink(graph);

        layout.setStabilizationLimit(0.4);
        layout.setForce(4.0);
    }

    /** Retourne uniquement le nom du package d’une classe fully qualified. */
    private static String getPackageName(String qualifiedName) {
        int lastDot = qualifiedName.lastIndexOf('.');
        return (lastDot == -1) ? "(default)" : qualifiedName.substring(0, lastDot);
    }
}
