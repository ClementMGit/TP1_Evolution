package Partie2.processors;

import Partie2.visitors.ClassesWithMoreThanXMethodsScanner;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClassesWithMoreThanXMethodsProcessor {

    /**
     * Retourne la liste des classes dont le nombre de méthodes est > X
     */
    public List<CtClass<?>> computeClassesWithMoreThanXMethods(CtModel model, int X) {
        ClassesWithMoreThanXMethodsScanner scanner = new ClassesWithMoreThanXMethodsScanner();
        model.getRootPackage().accept(scanner);

        Map<CtClass<?>, Integer> methodCount = scanner.getClassMethodCount();

        return methodCount.entrySet().stream()
                .filter(e -> e.getValue() > X)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
