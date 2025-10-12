package Partie2.processors;

import Partie2.visitors.CallGraphVisitor;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CallGraphProcessor {

    private final CtModel model;

    public CallGraphProcessor(CtModel model) {
        this.model = model;
    }

    public Map<CtMethod<?>, Set<CtExecutableReference<?>>> computeCallGraph() {
        // ensemble des noms qualifiés des types définis dans le modèle (code du projet)
        Set<String> internalTypeNames = new HashSet<>();
        for (CtType<?> t : model.getAllTypes()) {
            if (t.getQualifiedName() != null) {
                internalTypeNames.add(t.getQualifiedName());
            }
        }

        CallGraphVisitor visitor = new CallGraphVisitor(internalTypeNames);

        // scanner chaque type interne
        for (CtType<?> type : model.getAllTypes()) {
            visitor.scanElement(type);
        }

        // ne garder que les méthodes qui appellent au moins une méthode interne
        Map<CtMethod<?>, Set<CtExecutableReference<?>>> raw = visitor.getCallGraph();
        Map<CtMethod<?>, Set<CtExecutableReference<?>>> result = new HashMap<>();
        for (Map.Entry<CtMethod<?>, Set<CtExecutableReference<?>>> e : raw.entrySet()) {
            Set<CtExecutableReference<?>> targets = e.getValue();
            if (targets != null && !targets.isEmpty()) {
                result.put(e.getKey(), new HashSet<>(targets));
            }
        }
        return result;
    }
}
