package Partie2;

import spoon.reflect.declaration.CtClass;

public class ClassCountVisitor extends AbstractCtVisitor {
    private int count = 0;

    public int getCount() {
        return count;
    }

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        count++;
    }
}