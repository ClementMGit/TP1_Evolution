package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.CtScanner;

public class AverageMethodsPerClassScanner extends CtScanner {

    private int totalClasses = 0;
    private int totalMethods = 0;

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        totalClasses++;
        totalMethods += ctClass.getMethods().size();
        super.visitCtClass(ctClass); // continue le scan
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getTotalMethods() {
        return totalMethods;
    }
}
