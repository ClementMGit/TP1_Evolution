package Partie2.processors;

import Partie2.visitors.TopMethodsByLinesScanner;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.*;
import java.util.stream.Collectors;

public class TopMethodsByLinesProcessor {

    /**
     * Retourne les 10% des méthodes les plus longues (en lignes) par classe
     */
    public Map<CtClass<?>, List<CtMethod<?>>> computeTop10PercentMethodsByLines(CtModel model) {
        TopMethodsByLinesScanner scanner = new TopMethodsByLinesScanner();
        model.getRootPackage().accept(scanner);

        Map<CtMethod<?>, Integer> methodLineCount = scanner.getMethodLineCount();

        // Grouper les méthodes par classe
        Map<CtClass<?>, List<CtMethod<?>>> classToMethods = new HashMap<>();
        for (CtMethod<?> method : methodLineCount.keySet()) {
            CtClass<?> declaringClass = method.getParent(CtClass.class);
            if (declaringClass != null) {
                classToMethods.computeIfAbsent(declaringClass, k -> new ArrayList<>()).add(method);
            }
        }

        // Pour chaque classe, trier les méthodes par nombre de lignes et prendre top 10%
        Map<CtClass<?>, List<CtMethod<?>>> topMethodsPerClass = new HashMap<>();
        for (Map.Entry<CtClass<?>, List<CtMethod<?>>> entry : classToMethods.entrySet()) {
            CtClass<?> clazz = entry.getKey();
            List<CtMethod<?>> methods = entry.getValue();

            int topN = Math.max(1, (int) Math.ceil(methods.size() * 0.1)); // top 10%

            List<CtMethod<?>> topMethods = methods.stream()
                    .sorted((m1, m2) -> methodLineCount.get(m2) - methodLineCount.get(m1)) // tri décroissant
                    .limit(topN)
                    .collect(Collectors.toList());

            topMethodsPerClass.put(clazz, topMethods);
        }

        return topMethodsPerClass;
    }

    /**
     * Retourne le nombre de lignes d'une méthode
     */
    public int getMethodLineCount(CtMethod<?> method, Map<CtMethod<?>, Integer> methodLineCountMap) {
        return methodLineCountMap.getOrDefault(method, 0);
    }
}
