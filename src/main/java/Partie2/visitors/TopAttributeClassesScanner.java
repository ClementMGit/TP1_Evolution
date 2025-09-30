package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.CtScanner;

import java.util.HashMap;
import java.util.Map;

public class TopAttributeClassesScanner extends CtScanner {

    private final Map<CtClass<?>, Integer> classAttributeCount = new HashMap<>();

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        classAttributeCount.put(ctClass, ctClass.getFields().size());
        super.visitCtClass(ctClass); // Continue le scan
    }

    public Map<CtClass<?>, Integer> getClassAttributeCount() {
        return classAttributeCount;
    }
}
