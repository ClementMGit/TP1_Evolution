package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.CtScanner;

import java.util.HashMap;
import java.util.Map;

public class ClassesWithMoreThanXMethodsScanner extends CtScanner {

    private final Map<CtClass<?>, Integer> classMethodCount = new HashMap<>();

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        classMethodCount.put(ctClass, ctClass.getMethods().size());
        super.visitCtClass(ctClass); // continue le scan
    }

    public Map<CtClass<?>, Integer> getClassMethodCount() {
        return classMethodCount;
    }
}
