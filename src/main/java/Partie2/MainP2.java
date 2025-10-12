package Partie2;

import Partie2.gui.MainFrame;

import javax.swing.*;

public class MainP2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
