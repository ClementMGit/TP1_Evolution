package visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.code.CtInvocation;

public class InvocationVisitor extends AbstractCtVisitor {

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        System.out.println("Classe : " + ctClass.getSimpleName());

        for (CtMethod<?> method : ctClass.getMethods()) {
            System.out.println("  Méthode : " + method.getSimpleName());

            var invocations = method.getElements(e -> e instanceof CtInvocation)
                    .stream()
                    .map(e -> (CtInvocation<?>) e)
                    .toList();

            if (invocations.isEmpty()) {
                System.out.println("    (aucun appel)");
            } else {
                for (CtInvocation<?> invocation : invocations) {
                    String calledMethod = invocation.getExecutable().getSimpleName();
                    String receiver = "implicit this";
                    if (invocation.getTarget() != null && invocation.getTarget().getType() != null) {
                        receiver = invocation.getTarget().getType().getQualifiedName();
                    }
                    System.out.println("    -> " + calledMethod + " (receveur : " + receiver + ")");
                }
            }
        }

        System.out.println("-----------------------------------");
    }
}
