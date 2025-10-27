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
     * Les clés incluent le nom complet de chaque classe ("pkg.ClassA↔pkg.ClassB").
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

                // 🔹 On garde le nom complet pour distinguer les classes d’un même nom
                String callerName = caller.getQualifiedName();
                String calleeName = callee.getQualifiedName();
                String key = callerName + "↔" + calleeName;

                normalizedCoupling.put(key, normalized);
            }
        }

        return normalizedCoupling;
    }
}
