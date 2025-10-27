package Partie2.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private MetricsPanel metricsPanel;
    private GraphPanel callGraphPanel;
    private ModuleGraphPanel moduleGraphPanel;

    public MainFrame() {
        setTitle("Analyse de projet Java - Spoon Metrics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);

        // Onglets
        JTabbedPane tabbedPane = new JTabbedPane();

        metricsPanel = new MetricsPanel();
        callGraphPanel = new GraphPanel();
        moduleGraphPanel = new ModuleGraphPanel();

        tabbedPane.addTab("Métriques", metricsPanel);
        tabbedPane.addTab("Graphe d'appel", callGraphPanel);
        tabbedPane.addTab("Modules couplés", moduleGraphPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Lien entre MetricsPanel et les autres pour accéder au modèle et aux graphes
        metricsPanel.setGraphPanels(callGraphPanel, moduleGraphPanel);
    }
}
