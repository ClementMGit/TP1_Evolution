package Partie2.gui;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {
    public GraphPanel() {
        setLayout(new BorderLayout());
        JLabel placeholder = new JLabel("Le graphe d'appel sera affiché ici.", SwingConstants.CENTER);
        add(placeholder, BorderLayout.CENTER);
    }
}
