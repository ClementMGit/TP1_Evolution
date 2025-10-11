package Partie2.processors;

import Partie2.visitors.TopAttributeClassesScanner;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;

import java.util.*;
import java.util.stream.Collectors;

public class TopAttributeClassesProcessor {

    /**
     * Retourne la liste des classes du top 10% (ancien comportement)
     */
    public List<CtClass<?>> computeTop10Percent(CtModel model) {
        return new ArrayList<>(computeTop10PercentMap(model).keySet());
    }

    /**
     * Retourne une Map (classe → nombre d'attributs) pour le top 10%
     */
    public Map<CtClass<?>, Integer> computeTop10PercentMap(CtModel model) {
        TopAttributeClassesScanner scanner = new TopAttributeClassesScanner();
        model.getRootPackage().accept(scanner);

        Map<CtClass<?>, Integer> attributeCount = scanner.getClassAttributeCount();
        if (attributeCount.isEmpty()) return Collections.emptyMap();

        int topN = Math.max(1, (int) (attributeCount.size() * 0.1));

        return attributeCount.entrySet().stream()
                .sorted(Map.Entry.<CtClass<?>, Integer>comparingByValue().reversed())
                .limit(topN)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new // pour garder l’ordre
                ));
    }
}
