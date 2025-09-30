package Partie2;
import Partie2.processors.*;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.List;
import java.util.Map;

public class MainP2 {
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.addInputResource("src/main/java/Partie2");
        launcher.buildModel();

        CtModel model = launcher.getModel();

        LineCountProcessor processor = new LineCountProcessor();
        int totalLines = processor.computeTotalLines(model);

        System.out.println("📏 Nombre total de lignes de code : " + totalLines);

        ClassCountProcessor classProcessor = new ClassCountProcessor();
        int totalClasses = classProcessor.computeClassCount(model);

        System.out.println("Nombre total de classes : " + totalClasses);

        MethodCountProcessor methodProcessor = new MethodCountProcessor();
        int totalMethods = methodProcessor.computeTotalMethods(model);
        System.out.println("Nombre total de méthodes : " + totalMethods);

        PackageCountProcessor packageProcessor = new PackageCountProcessor();
        int totalPackages = packageProcessor.computePackageCount(model);
        System.out.println("Nombre total de packages : " + totalPackages);

        AverageAttributesPerClassProcessor averageAttributesPerClassProcessor = new AverageAttributesPerClassProcessor();
        double avgAttributes = averageAttributesPerClassProcessor.computeAverageAttributesPerClass(model);
        System.out.println("Nombre moyen d'attributs par classe : " + avgAttributes);

        AverageMethodsPerClassProcessor averageMethodsPerClassProcessor = new AverageMethodsPerClassProcessor();
        double avgMethods = averageMethodsPerClassProcessor.computeAverageMethodsPerClass(model);
        System.out.println("Nombre moyen de méthodes par classe : " + avgMethods);

        AverageLinesPerMethodProcessor averageLinesPerMethodProcessor = new AverageLinesPerMethodProcessor();
        double avgLines = averageLinesPerMethodProcessor.computeAverageLinesPerMethod(model);
        System.out.println("Nombre moyen de lignes par méthode : " + avgLines);

        TopAttributeClassesProcessor topAttributeClassesProcessor = new TopAttributeClassesProcessor();
        List<CtClass<?>> topAttributesClasses = topAttributeClassesProcessor.computeTop10Percent(model);

        System.out.println("Top 10% des classes avec le plus d'attributs :");
        for (CtClass<?> cls : topAttributesClasses) {
            System.out.println("  - " + cls.getSimpleName() + " (" + cls.getFields().size() + " attributs)");
        }

        TopMethodClassesProcessor topMethodClassesProcessor = new TopMethodClassesProcessor();
        List<CtClass<?>> topMethodClasses = topMethodClassesProcessor.computeTop10Percent(model);

        System.out.println("Top 10% des classes avec le plus de méthodes :");
        for (CtClass<?> cls : topMethodClasses) {
            System.out.println("  - " + cls.getSimpleName() + " (" + cls.getMethods().size() + " méthodes)");
        }


        IntersectionTopClassesProcessor intersectionProcessor = new IntersectionTopClassesProcessor();
        List<CtClass<?>> intersection = intersectionProcessor.computeIntersection(topMethodClasses, topAttributesClasses);

        System.out.println("Classes présentes dans les deux top 10% :");
        for (CtClass<?> cls : intersection) {
            System.out.println("  - " + cls.getSimpleName());
        }

        int X = 1; // nombre minimal de méthodes
        ClassesWithMoreThanXMethodsProcessor classesWithMoreThanXMethodsProcessor = new ClassesWithMoreThanXMethodsProcessor();
        List<CtClass<?>> classes = classesWithMoreThanXMethodsProcessor.computeClassesWithMoreThanXMethods(model, X);

        System.out.println("Classes avec plus de " + X + " méthodes :");
        for (CtClass<?> cls : classes) {
            System.out.println("  - " + cls.getSimpleName() + " (" + cls.getMethods().size() + " méthodes)");
        }


        TopMethodsByLinesProcessor topMethodsByLinesProcessor = new TopMethodsByLinesProcessor();
        Map<CtClass<?>, List<CtMethod<?>>> topMethodsPerClass = topMethodsByLinesProcessor.computeTop10PercentMethodsByLines(model);

        System.out.println("Top 10% des méthodes les plus longues par classe :");
        for (CtClass<?> cls : topMethodsPerClass.keySet()) {
            System.out.println("Classe : " + cls.getSimpleName());
            for (CtMethod<?> method : topMethodsPerClass.get(cls)) {
                int lines = method.getBody() != null ? method.getBody().getStatements().size() : 0;
                System.out.println("  - Méthode : " + method.getSimpleName() + " (" + lines + " lignes approx.)");
            }
        }
        MaxParametersProcessor maxParamsProcessor = new MaxParametersProcessor();
        int maxParams = maxParamsProcessor.computeMaxParameters(model);
        System.out.println("Nombre maximal de paramètres parmi toutes les méthodes : " + maxParams);



    }
}
