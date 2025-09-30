package Partie2.processors;

import Partie2.visitors.TopAttributeClassesScanner;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopAttributeClassesProcessor {

    public List<CtClass<?>> computeTop10Percent(CtModel model) {
        TopAttributeClassesScanner scanner = new TopAttributeClassesScanner();
        model.getRootPackage().accept(scanner);

        Map<CtClass<?>, Integer> attributeCount = scanner.getClassAttributeCount();
        int topN = Math.max(1, (int) (attributeCount.size() * 0.1));

        return attributeCount.entrySet().stream()
                .sorted(Map.Entry.<CtClass<?>, Integer>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
