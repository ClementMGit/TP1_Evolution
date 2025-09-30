package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.CtScanner;

public class AverageAttributesPerClassScanner extends CtScanner {

    private int totalClasses = 0;
    private int totalAttributes = 0;

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        totalClasses++;
        totalAttributes += ctClass.getFields().size();
        super.visitCtClass(ctClass); // Continue le scan
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public int getTotalAttributes() {
        return totalAttributes;
    }
}
