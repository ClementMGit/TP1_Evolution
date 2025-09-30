package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.CtScanner;

public class ClassCountScanner extends CtScanner {

    private int classCount = 0;

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        classCount++;
        super.visitCtClass(ctClass); // Continue le scan
    }

    public int getClassCount() {
        return classCount;
    }
}
