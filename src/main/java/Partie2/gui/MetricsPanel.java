package Partie2.gui;

import Partie2.processors.*;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class MetricsPanel extends JPanel {

    private JLabel linesLabel, classesLabel, methodsLabel, packagesLabel;
    private JLabel avgAttributesLabel, avgMethodsLabel, avgLinesLabel;
    private JLabel topAttributesLabel, topMethodsLabel, intersectionLabel;
    private JLabel xMethodsLabel, topLongMethodsLabel, maxParamsLabel;
    private JTextField xInputField;
    private JButton recalcXButton, chooseFolderButton, analyzeButton;
    private File selectedFolder;
    private static CtModel model;



    public MetricsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Barre du haut ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        chooseFolderButton = new JButton("Choisir un dossier");
        analyzeButton = new JButton("Analyser le projet");
        topPanel.add(chooseFolderButton);
        topPanel.add(analyzeButton);
        add(topPanel, BorderLayout.NORTH);

        // --- Panneau principal ---
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Résultats d'analyse"));
        infoPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
        int row = 0;

        // --- Statistiques globales ---
        addLabel(infoPanel, gbc, row++, "Nombre total de lignes :", linesLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Nombre total de classes :", classesLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Nombre total de méthodes :", methodsLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Nombre total de packages :", packagesLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Nombre maximal de paramètres :", maxParamsLabel = new JLabel("–"), labelFont);

        row++;
        addSectionTitle(infoPanel, gbc, row++, "Moyennes");
        addLabel(infoPanel, gbc, row++, "Attributs par classe :", avgAttributesLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Méthodes par classe :", avgMethodsLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Lignes par méthode :", avgLinesLabel = new JLabel("–"), labelFont);

        row++;

        // --- Partie + de X méthodes ---
        JPanel xPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel xTextLabel = new JLabel("Classes avec plus de ");
        xInputField = new JTextField("2", 4);
        JLabel methodsSuffix = new JLabel(" méthodes : ");
        recalcXButton = new JButton("Recalculer");
        xMethodsLabel = new JLabel("<html>–</html>");
        xMethodsLabel.setVerticalAlignment(SwingConstants.TOP);
        xMethodsLabel.setFont(labelFont);

        JScrollPane xScroll = new JScrollPane(xMethodsLabel);
        xScroll.setBorder(null);
        xScroll.setPreferredSize(new Dimension(500, 200));

        xTextLabel.setFont(labelFont);
        xInputField.setFont(labelFont);
        methodsSuffix.setFont(labelFont);
        xMethodsLabel.setFont(labelFont);

        xPanel.add(xTextLabel);
        xPanel.add(xInputField);
        xPanel.add(methodsSuffix);
        xPanel.add(recalcXButton);
        xPanel.add(xScroll);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        infoPanel.add(xPanel, gbc);

        row++;
        // --- Section Top 10% ---
        addSectionTitle(infoPanel, gbc, row++, "Top 10%");
        addLabel(infoPanel, gbc, row++, "Classes avec plus d’attributs :", topAttributesLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Classes avec plus de méthodes :", topMethodsLabel = new JLabel("–"), labelFont);
        addLabel(infoPanel, gbc, row++, "Présentes dans les deux catégories :", intersectionLabel = new JLabel("–"), labelFont);

        // Top méthodes longues encapsulé dans un JScrollPane pour alignement en haut
        topLongMethodsLabel = new JLabel("Plus grand nombre de lignes de code (par classe) :");
        topLongMethodsLabel.setVerticalAlignment(SwingConstants.TOP);
        topLongMethodsLabel.setFont(labelFont);

        JScrollPane topLongScroll = new JScrollPane(topLongMethodsLabel);
        topLongScroll.setBorder(null);
        topLongScroll.setPreferredSize(new Dimension(500, 200));
        topLongScroll.getVerticalScrollBar().setUnitIncrement(16);
        topLongScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        topLongScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        topLongScroll.getViewport().setViewPosition(new Point(0,0));

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;

        infoPanel.add(topLongScroll, gbc);

        // --- Encapsuler infoPanel dans JScrollPane principal ---
        JScrollPane scrollPane = new JScrollPane(infoPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        // --- Actions ---
        chooseFolderButton.addActionListener(e -> chooseFolder());
        analyzeButton.addActionListener(e -> analyzeProject());
        recalcXButton.addActionListener(e -> recalcWithNewX());
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc, int row, String title, JLabel label, Font font) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(font);
        panel.add(titleLabel, gbc);

        gbc.gridx = 1;
        label.setFont(font);
        panel.add(label, gbc);
    }

    private void addSectionTitle(JPanel panel, GridBagConstraints gbc, int row, String title) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleLabel.setForeground(new Color(40, 70, 120));
        panel.add(titleLabel, gbc);
    }

    private void chooseFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFolder = chooser.getSelectedFile();
            JOptionPane.showMessageDialog(this, "Dossier sélectionné : " + selectedFolder.getAbsolutePath());
        }
    }

    private void analyzeProject() {
        String path ="/home/clementwt/Cours/M1/backup/ArchiTP3-main";
//        if (selectedFolder == null) {
//            JOptionPane.showMessageDialog(this, "Veuillez d'abord choisir un dossier de projet.");
//            return;
//        }

        Launcher launcher = new Launcher();
        launcher.addInputResource(path);
        launcher.buildModel();
        model = launcher.getModel();

        // --- Graphe d'appels ---
        GraphPanel graphPanel = (GraphPanel) ((JTabbedPane) getParent()).getComponentAt(1);
        graphPanel.displayCallGraph(model);

        // --- Graphe de couplage ---
        CouplingPanel couplingPanel = (CouplingPanel) ((JTabbedPane) getParent()).getComponentAt(2);
        couplingPanel.displayCouplingGraph(model);

        // --- Statistiques diverses ---
        linesLabel.setText(String.valueOf(new LineCountProcessor().computeTotalLines(model)));
        classesLabel.setText(String.valueOf(new ClassCountProcessor().computeClassCount(model)));
        methodsLabel.setText(String.valueOf(new MethodCountProcessor().computeTotalMethods(model)));
        packagesLabel.setText(String.valueOf(new PackageCountProcessor().computePackageCount(model)));

        avgAttributesLabel.setText(String.format("%.2f", new AverageAttributesPerClassProcessor().computeAverageAttributesPerClass(model)));
        avgMethodsLabel.setText(String.format("%.2f", new AverageMethodsPerClassProcessor().computeAverageMethodsPerClass(model)));
        avgLinesLabel.setText(String.format("%.2f", new AverageLinesPerMethodProcessor().computeAverageLinesPerMethod(model)));

        maxParamsLabel.setText(String.valueOf(new MaxParametersProcessor().computeMaxParameters(model)));

        recalcWithNewX();

        Map<CtClass<?>, Integer> topAttr = new TopAttributeClassesProcessor().computeTop10PercentMap(model);
        topAttributesLabel.setText(formatList(topAttr, "attributs"));

        Map<CtClass<?>, Integer> topMeth = new TopMethodClassesProcessor().computeTop10PercentMap(model);
        topMethodsLabel.setText(formatList(topMeth, "méthodes"));

        List<CtClass<?>> intersection = new IntersectionTopClassesProcessor()
                .computeIntersection(List.copyOf(topAttr.keySet()), List.copyOf(topMeth.keySet()));
        intersectionLabel.setText(formatListInline(intersection));

        Map<CtClass<?>, List<CtMethod<?>>> topLines = new TopMethodsByLinesProcessor()
                .computeTop10PercentMethodsByLines(model);
        topLongMethodsLabel.setText(formatTopMethods(topLines));
    }


    private void recalcWithNewX() {
        if (model == null) {
            JOptionPane.showMessageDialog(this, "Veuillez d'abord analyser un projet.");
            return;
        }
        try {
            int x = Integer.parseInt(xInputField.getText());
            List<CtClass<?>> xClasses = new ClassesWithMoreThanXMethodsProcessor()
                    .computeClassesWithMoreThanXMethods(model, x);
            xMethodsLabel.setText(formatListInline(xClasses));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide pour X.");
        }
    }

    private String formatList(Map<CtClass<?>, Integer> map, String unit) {
        if (map.isEmpty()) return "<html>Aucune</html>";
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<b>").append(map.size()).append(" classes dans le top 10%</b><br>");
        for (Map.Entry<CtClass<?>, Integer> entry : map.entrySet()) {
            sb.append("• ").append(entry.getKey().getSimpleName())
                    .append(" (").append(entry.getValue()).append(" ").append(unit).append(")<br>");
        }
        sb.append("</html>");
        return sb.toString();
    }

    private String formatListInline(List<CtClass<?>> list) {
        if (list.isEmpty()) return "<html>Aucune</html>";
        StringBuilder sb = new StringBuilder("<html>");
        sb.append("<b>").append(list.size()).append(" classes</b><br>");
        for (CtClass<?> c : list) sb.append("• ").append(c.getSimpleName()).append("<br>");
        sb.append("</html>");
        return sb.toString();
    }

    private String formatTopMethods(Map<CtClass<?>, List<CtMethod<?>>> map) {
        if (map.isEmpty()) return "<html>Aucune</html>";
        StringBuilder sb = new StringBuilder("<html>");

        // Label unique en haut
        sb.append("<b>Plus grand nombre de lignes de code (par classe) :</b><br>");

        for (Map.Entry<CtClass<?>, List<CtMethod<?>>> entry : map.entrySet()) {
            CtClass<?> ctClass = entry.getKey();
            List<CtMethod<?>> methods = entry.getValue();

            sb.append("<b>").append(ctClass.getSimpleName()).append("</b><br>");
            for (CtMethod<?> m : methods) {
                int lines = (m.getBody() != null) ? m.getBody().getStatements().size() : 0;
                sb.append("• ").append(m.getSimpleName())
                        .append(" (").append(lines).append(" lignes)<br>");
            }
        }
        sb.append("</html>");
        return sb.toString();
    }


}
