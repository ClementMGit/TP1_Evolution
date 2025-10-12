package Partie2.gui;

import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.CtModel;
import Partie2.processors.CallGraphProcessor;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphPanel extends JPanel {

    private mxGraphComponent graphComponent;

    public GraphPanel() {
        setLayout(new BorderLayout());
    }

    public void displayCallGraph(CtModel model) {
        removeAll(); // supprime l'ancien graphe
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();

        try {
            CallGraphProcessor processor = new CallGraphProcessor(model);
            Map<CtMethod<?>, Set<CtExecutableReference<?>>> callGraph = processor.computeCallGraph();

            // Construire l'ensemble complet des méthodes à représenter : callers + callees résolus internes
            Set<CtMethod<?>> allMethods = new HashSet<>();
            allMethods.addAll(callGraph.keySet());
            for (Set<CtExecutableReference<?>> refs : callGraph.values()) {
                if (refs == null) continue;
                for (CtExecutableReference<?> ref : refs) {
                    if (ref == null || ref.getDeclaration() == null) continue;
                    CtTypeReference<?> declTypeRef = ref.getDeclaringType();
                    if (declTypeRef == null) continue;
                    String qname = declTypeRef.getQualifiedName();
                    if (qname == null) continue;
                    boolean isInternal = model.getAllTypes().stream()
                            .anyMatch(t -> qname.equals(t.getQualifiedName()));
                    if (!isInternal) continue;
                    @SuppressWarnings("unchecked")
                    CtMethod<?> calleeMethod = (CtMethod<?>) ref.getDeclaration();
                    if (calleeMethod != null) allMethods.add(calleeMethod);
                }
            }

            // Créer un vertex pour chaque méthode impliquée
            Map<CtMethod<?>, Object> methodVertices = new HashMap<>();
            for (CtMethod<?> method : allMethods) {
                String label = method.getDeclaringType().getSimpleName() + "." + method.getSimpleName();
                Object v = graph.insertVertex(parent, null, label, 0, 0, 160, 36,
                        "fillColor=#EAF2FF;strokeColor=#3465A4;fontColor=#000000;rounded=1");
                methodVertices.put(method, v);
            }

            // Créer les arêtes pour chaque relation caller -> callee (si les deux sommets existent)
            for (Map.Entry<CtMethod<?>, Set<CtExecutableReference<?>>> entry : callGraph.entrySet()) {
                CtMethod<?> caller = entry.getKey();
                Object callerVertex = methodVertices.get(caller);
                if (callerVertex == null) continue;

                for (CtExecutableReference<?> calleeRef : entry.getValue()) {
                    if (calleeRef == null || calleeRef.getDeclaration() == null) continue;
                    CtTypeReference<?> declTypeRef = calleeRef.getDeclaringType();
                    if (declTypeRef == null) continue;
                    String qname = declTypeRef.getQualifiedName();
                    if (qname == null) continue;
                    boolean isInternal = model.getAllTypes().stream()
                            .anyMatch(t -> qname.equals(t.getQualifiedName()));
                    if (!isInternal) continue;

                    @SuppressWarnings("unchecked")
                    CtMethod<?> callee = (CtMethod<?>) calleeRef.getDeclaration();
                    Object calleeVertex = methodVertices.get(callee);
                    if (calleeVertex != null) {
                        graph.insertEdge(parent, null, "", callerVertex, calleeVertex,
                                "strokeColor=#3465A4;endArrow=classic;strokeWidth=2");
                    }
                }
            }

        } finally {
            graph.getModel().endUpdate();
        }

        // Choix et configuration du layout
        int nodeCount = graph.getChildCells(graph.getDefaultParent(), true, false).length;
        if (nodeCount == 0) {
            // rien à afficher
            revalidate();
            repaint();
            return;
        }

        if (nodeCount < 20) {
            // pour petit graphe, layout circulaire propre
            mxCircleLayout circle = new mxCircleLayout(graph);
            circle.setX0(40);
            circle.setY0(40);
            circle.setRadius(200);
            circle.execute(graph.getDefaultParent());
        } else {
            // layout organique bien espacé pour grands graphes
            mxFastOrganicLayout organic = new mxFastOrganicLayout(graph);
            organic.setForceConstant(150.0); // force d'expansion (augmente l'espace entre nœuds)
            organic.setMinDistanceLimit(60);
            organic.setMaxIterations(1000); // itérations pour stabiliser
            organic.setResetEdges(true);
            organic.execute(graph.getDefaultParent());
        }

        // Composant graphique
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setConnectable(false);
        graphComponent.setToolTips(true);

// accélérer le scroll
        graphComponent.getVerticalScrollBar().setUnitIncrement(30);
        graphComponent.getHorizontalScrollBar().setUnitIncrement(30);

// améliorer le rendu
        graphComponent.getViewport().setBackground(Color.WHITE);
        add(graphComponent, BorderLayout.CENTER);
        revalidate();
        repaint();

    }

}
