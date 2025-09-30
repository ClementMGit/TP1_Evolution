package Partie2.processors;

import Partie2.visitors.AverageLinesPerMethodScanner;
import spoon.reflect.CtModel;

public class AverageLinesPerMethodProcessor {

    public double computeAverageLinesPerMethod(CtModel model) {
        AverageLinesPerMethodScanner scanner = new AverageLinesPerMethodScanner();
        model.getRootPackage().accept(scanner);

        int totalMethods = scanner.getTotalMethods();
        int totalLines = scanner.getTotalLines();

        return totalMethods == 0 ? 0.0 : (double) totalLines / totalMethods;
    }
}
