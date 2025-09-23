package Partie1;

import spoon.Launcher;
import spoon.reflect.CtModel;

public class Main {
    public static void main(String[] args) {
        String inputPath = "src/main/java"; // ou args[0] si tu veux passer le chemin

        Launcher launcher = new Launcher();
        launcher.addInputResource(inputPath);
        launcher.buildModel();

        CtModel model = launcher.getModel();

        System.out.println("=== Classes et super-classes ===");
        model.getAllTypes().forEach(t -> t.accept(new ClassVisitor()));

        System.out.println("\n=== Attributs ===");
        model.getAllTypes().forEach(t -> t.accept(new FieldVisitor()));

        System.out.println("\n=== Méthodes ===");
        model.getAllTypes().forEach(t -> t.accept(new MethodVisitor()));

        System.out.println("\n=== Appels de méthodes ===");
        model.getAllTypes().forEach(t -> t.accept(new InvocationVisitor()));
    }
}

