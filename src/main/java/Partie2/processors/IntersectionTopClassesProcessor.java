package Partie2.processors;

import spoon.reflect.declaration.CtClass;

import java.util.List;
import java.util.stream.Collectors;

public class IntersectionTopClassesProcessor {

    /**
     * Retourne la liste des classes présentes à la fois dans
     * topMethodClasses et topAttributeClasses
     */
    public List<CtClass<?>> computeIntersection(List<CtClass<?>> topMethodClasses,
                                                List<CtClass<?>> topAttributeClasses) {
        return topMethodClasses.stream()
                .filter(topAttributeClasses::contains)
                .collect(Collectors.toList());
    }
}
