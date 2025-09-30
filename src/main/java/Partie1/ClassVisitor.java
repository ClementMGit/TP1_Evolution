package Partie1;

import spoon.reflect.declaration.CtClass;

public class ClassVisitor extends AbstractCtVisitor {

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        System.out.println("Classe : " + ctClass.getSimpleName());

        if (ctClass.getSuperclass() != null) {
            System.out.println("  Super-classes :");
            var superRef = ctClass.getSuperclass();
            while (superRef != null) {
                System.out.println("    - " + superRef.getQualifiedName());
                superRef = superRef.getSuperclass();
            }
        } else {
            System.out.println("  (aucune superclasse, hérite implicitement de java.lang.Object)");
        }

        System.out.println("-----------------------------------");
    }
}