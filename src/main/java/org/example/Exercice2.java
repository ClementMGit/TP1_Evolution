package org.example;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

import java.util.List;
import java.util.stream.Collectors;

public class Exercice2 {
    public static void main(String[] args) {
        // Créer et configurer Spoon
        Launcher launcher = new Launcher();
        launcher.addInputResource("src/main/java"); // chemin vers code source
        launcher.buildModel();

        // Récupérer le modèle AST complet
        CtModel model = launcher.getModel();

        // Parcourir toutes les classes
        for (CtType<?> ctType : model.getAllTypes()) {
            if (ctType instanceof CtClass) {
                CtClass<?> ctClass = (CtClass<?>) ctType;

                System.out.println("Classe : " + ctClass.getSimpleName());

                // --- Attributs ---
                System.out.println("  Attributs :");
                for (CtField<?> field : ctClass.getFields()) {
                    String visibility = field.getVisibility() != null
                            ? field.getVisibility().toString()
                            : "package-private";
                    System.out.println("    - " + field.getSimpleName() + " (" + visibility + ")");
                }

                // --- Méthodes ---
                System.out.println("  Méthodes :");
                for (CtMethod<?> method : ctClass.getMethods()) {
                    System.out.println("    - " + method.getSimpleName());
                    List<CtInvocation<?>> invocations = method.getElements(e -> e instanceof CtInvocation)
                            .stream()
                            .map(e -> (CtInvocation<?>) e)
                            .collect(Collectors.toList());

                    System.out.println("      Method calls:");
                    for (CtInvocation<?> invocation : invocations) {
                        // Récupérer le type du receveur (target) s'il existe
                        String receiverType = "implicit this";
                        if (invocation.getTarget() != null && invocation.getTarget().getType() != null) {
                            receiverType = invocation.getTarget().getType().getQualifiedName();
                        }
                        System.out.println("        * " + invocation.getExecutable().getSimpleName()
                                + " sur objet de type : " + receiverType);
                    }
                }

                // --- Super classes ---
                System.out.println("  Super-classes :");
                CtTypeReference<?> superClass = ctClass.getSuperclass();
                while (superClass != null) {
                    System.out.println("    - " + superClass.getQualifiedName());
                    superClass = superClass.getSuperclass();
                }

                System.out.println("-----------------------------------");
            }
        }
    }
}
