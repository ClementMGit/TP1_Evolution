package Partie2.processors;

import Partie2.visitors.MethodCountScanner;
import spoon.reflect.CtModel;

public class MethodCountProcessor {

    public int computeTotalMethods(CtModel model) {
        MethodCountScanner scanner = new MethodCountScanner();
        model.getRootPackage().accept(scanner);
        return scanner.getTotalMethods();
    }
}
