package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.CtScanner;

public class MethodCountScanner extends CtScanner {

    private int totalMethods = 0;

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        // On additionne toutes les méthodes de chaque classe
        totalMethods += ctClass.getMethods().size();

        // On continue le scan
        super.visitCtClass(ctClass);
    }

    public int getTotalMethods() {
        return totalMethods;
    }
}
