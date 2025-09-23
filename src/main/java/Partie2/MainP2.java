package Partie2;

import spoon.Launcher;
import spoon.reflect.CtModel;

public class MainP2 {
    public static void main(String[] args) {
        String inputPath = "src/main/java/Partie2/";
        System.out.println("Partie2 input path: " + inputPath);
        Launcher launcher = new Launcher();
        launcher.addInputResource(inputPath);
        launcher.buildModel();

        CtModel model = launcher.getModel();

        visitors.Partie2.Processor processor = new visitors.Partie2.Processor(model);

        System.out.println("Nombre de classes : " + processor.getTotalClasses());
        System.out.println("Nombre total de méthodes : " + processor.getTotalMethods());
        System.out.println("Nombre total de packages : " + processor.getTotalPackages());
        System.out.println("Nombre total de lignes : " + processor.getTotalLines());

        System.out.println("Nombre moyen de méthodes par classe : " + processor.getAverageMethodsPerClass());
        System.out.println("Nombre moyen d'attributs par classe : " + processor.getAverageAttributesPerClass());
        System.out.println("Nombre moyen de lignes par méthode : " + processor.getAverageLinesPerMethod());

        System.out.println("=== Top 10% des classes avec le plus de méthodes ===");
        processor.getTop10PercentMethods().forEach(c ->
                System.out.println(c.getSimpleName() + " (" + c.getMethods().size() + " méthodes)"));

        System.out.println("=== Top 10% des classes avec le plus d'attributs ===");
        processor.getTop10PercentAttributes().forEach(c ->
                System.out.println(c.getSimpleName() + " (" + c.getFields().size() + " attributs)"));

        System.out.println("=== Classes présentes dans les deux catégories ===");
        processor.getIntersectionTopClasses().forEach(c ->
                System.out.println(c.getSimpleName()));
    }

}
