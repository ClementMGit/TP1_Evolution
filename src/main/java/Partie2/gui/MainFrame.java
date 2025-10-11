package Partie2.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Analyse de projet Java - Spoon Metrics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Métriques", new MetricsPanel());
        tabbedPane.addTab("Graphe d'appel", new GraphPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
