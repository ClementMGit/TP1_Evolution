package Partie2.processors;

import Partie2.visitors.AverageMethodsPerClassScanner;
import spoon.reflect.CtModel;

public class AverageMethodsPerClassProcessor {

    public double computeAverageMethodsPerClass(CtModel model) {
        AverageMethodsPerClassScanner scanner = new AverageMethodsPerClassScanner();
        model.getRootPackage().accept(scanner);

        int totalClasses = scanner.getTotalClasses();
        int totalMethods = scanner.getTotalMethods();

        return totalClasses == 0 ? 0.0 : (double) totalMethods / totalClasses;
    }
}
