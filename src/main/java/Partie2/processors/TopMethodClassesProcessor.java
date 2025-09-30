package Partie2.processors;

import Partie2.visitors.TopMethodClassesScanner;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TopMethodClassesProcessor {

    public List<CtClass<?>> computeTop10Percent(CtModel model) {
        TopMethodClassesScanner scanner = new TopMethodClassesScanner();
        model.getRootPackage().accept(scanner);

        Map<CtClass<?>, Integer> methodCount = scanner.getClassMethodCount();
        int topN = Math.max(1, (int) (methodCount.size() * 0.1));

        return methodCount.entrySet().stream()
                .sorted(Map.Entry.<CtClass<?>, Integer>comparingByValue().reversed())
                .limit(topN)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
