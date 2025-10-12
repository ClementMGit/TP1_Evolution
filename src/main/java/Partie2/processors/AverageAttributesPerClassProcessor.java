package Partie2.processors;

import Partie2.visitors.AverageAttributesPerClassScanner;
import spoon.reflect.CtModel;

public class AverageAttributesPerClassProcessor {

    public double computeAverageAttributesPerClass(CtModel model) {
        AverageAttributesPerClassScanner scanner = new AverageAttributesPerClassScanner();
        model.getRootPackage().accept(scanner);

        int totalClasses = scanner.getTotalClasses();
        int totalAttributes = scanner.getTotalAttributes();

        return totalClasses == 0 ? 0.0 : (double) totalAttributes / totalClasses;
    }
}
