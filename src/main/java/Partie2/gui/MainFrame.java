package Partie2.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Analyse de projet Java - Spoon Metrics");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Plein écran fenêtré
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(false); // garde la barre de titre et les boutons
        setResizable(true);

        // Si tu veux que ça prenne vraiment tout l'écran (y compris barre de tâches) :
        // GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Métriques", new MetricsPanel());
        tabbedPane.addTab("Graphe d'appel", new GraphPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
