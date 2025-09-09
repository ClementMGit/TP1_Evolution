package org.example;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtTypeReference;

public class Exercice2 {
    public static void main(String[] args) {
        // 1. Créer et configurer Spoon
        Launcher launcher = new Launcher();
        launcher.addInputResource("src/main/java"); // chemin vers code source
        launcher.buildModel();

        // 2. Récupérer le modèle AST complet
        CtModel model = launcher.getModel();

        // 3. Parcourir toutes les classes
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
