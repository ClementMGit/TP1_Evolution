package Partie2.processors;


import Partie2.visitors.ClassCountScanner;
import spoon.reflect.CtModel;

public class ClassCountProcessor {

    public int computeClassCount(CtModel model) {
        ClassCountScanner scanner = new ClassCountScanner();
        model.getRootPackage().accept(scanner);
        return scanner.getClassCount();
    }
}
