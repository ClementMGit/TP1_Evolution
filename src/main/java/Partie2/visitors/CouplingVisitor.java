package Partie2.visitors;

import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtScanner;

import java.util.HashMap;
import java.util.Map;

public class CouplingVisitor extends CtScanner {

    // Map<Classe appelante, Map<Classe appelée, nombre d'appels>>
    private final Map<CtTypeReference<?>, Map<CtTypeReference<?>, Integer>> couplingMap = new HashMap<>();

    @Override
    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
        CtMethod<?> caller = invocation.getParent(CtMethod.class);
        if (caller == null) return;

        // Classe appelante
        CtType<?> callerDeclaringType = caller.getDeclaringType();
        CtTypeReference<?> callerDeclaringTypeRef =
                callerDeclaringType != null ? callerDeclaringType.getReference() : null;

        if (callerDeclaringTypeRef == null) return;

        // Classe appelée
        CtExecutable<?> calleeExecutable = invocation.getExecutable().getDeclaration();
        CtTypeReference<?> calleeDeclaringTypeRef = null;

        if (calleeExecutable instanceof CtMethod<?> calleeMethod) {
            CtType<?> calleeType = calleeMethod.getDeclaringType();
            if (calleeType != null) {
                calleeDeclaringTypeRef = calleeType.getReference();
            }
        }

        // On ne compte que les appels entre classes différentes du projet
        if (calleeDeclaringTypeRef != null
                && !callerDeclaringTypeRef.equals(calleeDeclaringTypeRef)
                && isProjectClass(calleeDeclaringTypeRef)) {

            couplingMap
                    .computeIfAbsent(callerDeclaringTypeRef, k -> new HashMap<>())
                    .merge(calleeDeclaringTypeRef, 1, Integer::sum);
        }

        super.visitCtInvocation(invocation);
    }

    private boolean isProjectClass(CtTypeReference<?> typeRef) {
        // Filtrer les classes externes (java.*, javax.*, org.*, etc.)
        if (typeRef == null) return false;
        String name = typeRef.getQualifiedName();
        return !(name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("org."));
    }

    public Map<CtTypeReference<?>, Map<CtTypeReference<?>, Integer>> getCouplingMap() {
        return couplingMap;
    }
}
