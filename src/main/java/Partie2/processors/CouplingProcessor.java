package Partie2.processors;

import Partie2.visitors.CouplingVisitor;
import spoon.reflect.CtModel;
import spoon.reflect.reference.CtTypeReference;

import java.util.HashMap;
import java.util.Map;

public class CouplingProcessor {

    private final CtModel model;

    public CouplingProcessor(CtModel model) {
        this.model = model;
    }

    /**
     * Calcule la matrice de couplage normalisée entre toutes les classes du projet.
     * @return Map<String, Double> où la clé est "ClasseA↔ClasseB" et la valeur est le couplage normalisé
     */
    public Map<String, Double> computeNormalizedCoupling() {
        CouplingVisitor visitor = new CouplingVisitor();
        model.getAllTypes().forEach(t -> t.accept(visitor));

        Map<CtTypeReference<?>, Map<CtTypeReference<?>, Integer>> rawCoupling = visitor.getCouplingMap();
        Map<String, Double> normalizedCoupling = new HashMap<>();

        for (Map.Entry<CtTypeReference<?>, Map<CtTypeReference<?>, Integer>> entry : rawCoupling.entrySet()) {
            CtTypeReference<?> caller = entry.getKey();
            Map<CtTypeReference<?>, Integer> calleeMap = entry.getValue();

            int totalCalls = calleeMap.values().stream().mapToInt(Integer::intValue).sum();
            if (totalCalls == 0) continue;

            for (Map.Entry<CtTypeReference<?>, Integer> calleeEntry : calleeMap.entrySet()) {
                CtTypeReference<?> callee = calleeEntry.getKey();
                int calls = calleeEntry.getValue();
                double normalized = (double) calls / totalCalls;

                String key = caller.getSimpleName() + "↔" + callee.getSimpleName();
                normalizedCoupling.put(key, normalized);
            }
        }

        return normalizedCoupling;
    }
}
