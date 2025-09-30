package Partie2.processors;

import Partie2.visitors.MaxParametersScanner;
import spoon.reflect.CtModel;

public class MaxParametersProcessor {

    public int computeMaxParameters(CtModel model) {
        MaxParametersScanner scanner = new MaxParametersScanner();
        model.getRootPackage().accept(scanner);
        return scanner.getMaxParameters();
    }
}
