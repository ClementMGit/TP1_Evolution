package Partie2;

import spoon.reflect.declaration.CtClass;

import java.util.*;

public class TopMethodClassesVisitor extends AbstractCtVisitor {
    public final Map<CtClass<?>, Integer> classMethodCount = new HashMap<>();
    private List<? extends CtClass<?>> topClasses = new ArrayList<>();

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        classMethodCount.put(ctClass, ctClass.getMethods().size());
    }

    public void calculateTop10Percent() {
        int topN = Math.max(1, (int) (classMethodCount.size() * 0.1));
        topClasses = classMethodCount.entrySet().stream()
                .sorted(Map.Entry.<CtClass<?>, Integer>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<CtClass<?>> getTopClasses() {
        return (List<CtClass<?>>) topClasses;
    }
}
