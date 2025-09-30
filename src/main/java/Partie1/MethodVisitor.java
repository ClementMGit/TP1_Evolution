package Partie1;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class MethodVisitor extends AbstractCtVisitor {

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        System.out.println("Classe : " + ctClass.getSimpleName());
        System.out.println("  Méthodes :");

        if (ctClass.getMethods().isEmpty()) {
            System.out.println("    (aucune)");
        } else {
            for (CtMethod<?> method : ctClass.getMethods()) {
                System.out.println("    - " + method.getSimpleName());
            }
        }

        System.out.println("-----------------------------------");
    }
}

