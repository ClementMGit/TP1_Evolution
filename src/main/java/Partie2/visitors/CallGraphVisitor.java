package Partie2.visitors;

import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtExecutableReferenceExpression;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.reference.CtTypeReference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CallGraphVisitor extends CtScanner {

    private final Map<CtMethod<?>, Set<CtExecutableReference<?>>> callGraph = new HashMap<>();
    private CtMethod<?> currentMethod = null;
    private final Set<String> internalTypeQualifiedNames;

    public CallGraphVisitor(Set<String> internalTypeQualifiedNames) {
        this.internalTypeQualifiedNames = internalTypeQualifiedNames != null ? internalTypeQualifiedNames : new HashSet<>();
    }

    public Map<CtMethod<?>, Set<CtExecutableReference<?>>> getCallGraph() {
        return callGraph;
    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> method) {
        CtMethod<?> previous = currentMethod;
        currentMethod = method;
        callGraph.computeIfAbsent(method, k -> new HashSet<>());
        super.visitCtMethod(method);
        currentMethod = previous;
    }

    @Override
    public <T> void visitCtInvocation(CtInvocation<T> invocation) {
        collectExecutableReference(invocation.getExecutable());
        super.visitCtInvocation(invocation);
    }



    private void collectExecutableReference(CtExecutableReference<?> executableRef) {
        if (currentMethod == null || executableRef == null) {
            return;
        }

        // la déclaration doit être résolue (méthode interne visible dans le modèle)
        if (executableRef.getDeclaration() == null) {
            return;
        }

        // récupérer le type déclarant via le CtExecutableReference (CtTypeReference)
        CtTypeReference<?> declaringTypeRef = executableRef.getDeclaringType();
        if (declaringTypeRef == null) {
            return;
        }

        String qualifiedName = declaringTypeRef.getQualifiedName();
        if (qualifiedName == null) {
            return;
        }

        // n'ajouter que si le type déclarant appartient bien aux types internes
        if (!internalTypeQualifiedNames.contains(qualifiedName)) {
            return;
        }

        callGraph.computeIfAbsent(currentMethod, k -> new HashSet<>()).add(executableRef);
    }

    public void scanElement(CtElement element) {
        if (element != null) {
            element.accept(this);
        }
    }
}
