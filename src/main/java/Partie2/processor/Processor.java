package visitors.Partie2;

import Partie2.*;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;

import java.util.*;
import java.util.stream.Collectors;

public class Processor {

    private final CtModel model;

    // Visitors qui collectent juste les données
    private final ClassCountVisitor classCountVisitor = new ClassCountVisitor();
    private final MethodCountVisitor methodCountVisitor = new MethodCountVisitor();
    private final PackageCountVisitor packageCountVisitor = new PackageCountVisitor();
    private final LineCountVisitor lineCountVisitor = new LineCountVisitor();
    private final AverageMethodsPerClassVisitor avgMethodsVisitor = new AverageMethodsPerClassVisitor();
    private final AverageAttributesPerClassVisitor avgAttributesVisitor = new AverageAttributesPerClassVisitor();
    private final AverageLinesPerMethodVisitor avgLinesVisitor = new AverageLinesPerMethodVisitor();
    private final TopMethodClassesVisitor topMethodsVisitor = new TopMethodClassesVisitor();
    private final TopAttributeClassesVisitor topAttributesVisitor = new TopAttributeClassesVisitor();

    private List<CtClass<?>> intersectionTopClasses = new ArrayList<>();

    public Processor(CtModel model) {
        this.model = model;
        compute();
    }

    private void compute() {
        for (CtType<?> ctType : model.getAllTypes()) {
            if (ctType instanceof CtClass) {
                CtClass<?> ctClass = (CtClass<?>) ctType;

                // ici tu peux appliquer tous tes visiteurs
                ctClass.accept(classCountVisitor);
                ctClass.accept(methodCountVisitor);
                ctClass.accept(avgMethodsVisitor);
                ctClass.accept(avgAttributesVisitor);
                ctClass.accept(avgLinesVisitor);
                ctClass.accept(topMethodsVisitor);
                ctClass.accept(topAttributesVisitor);
                ctClass.accept(lineCountVisitor);
            }
        }


        // Parcours des packages
        model.getAllPackages().forEach(p -> p.accept(packageCountVisitor));

        // Calcul des top 10 %
        topMethodsVisitor.calculateTop10Percent();
        topAttributesVisitor.calculateTop10Percent();

        // Intersection des deux catégories
        Set<CtClass<?>> topMethodsSet = new HashSet<>(topMethodsVisitor.getTopClasses());
        intersectionTopClasses = topAttributesVisitor.getTopClasses().stream()
                .filter(topMethodsSet::contains)
                .collect(Collectors.toList());
    }

    // --- Getters pour toutes les métriques ---

    public int getTotalClasses() {
        return classCountVisitor.getCount();
    }

    public int getTotalMethods() {
        return methodCountVisitor.getCount();
    }

    public int getTotalPackages() {
        return packageCountVisitor.getCount();
    }

    public int getTotalLines() {
        return lineCountVisitor.getLineCount();
    }

    public double getAverageMethodsPerClass() {
        return avgMethodsVisitor.getTotalClasses() == 0 ? 0 :
                (double) avgMethodsVisitor.getTotalMethods() / avgMethodsVisitor.getTotalClasses();
    }

    public double getAverageAttributesPerClass() {
        return avgAttributesVisitor.getTotalClasses() == 0 ? 0 :
                (double) avgAttributesVisitor.getTotalAttributes() / avgAttributesVisitor.getTotalClasses();
    }

    public double getAverageLinesPerMethod() {
        return avgLinesVisitor.getTotalMethods() == 0 ? 0 :
                (double) avgLinesVisitor.getTotalLines() / avgLinesVisitor.getTotalMethods();
    }

    public List<CtClass<?>> getTop10PercentMethods() {
        return topMethodsVisitor.getTopClasses();
    }

    public List<CtClass<?>> getTop10PercentAttributes() {
        return topAttributesVisitor.getTopClasses();
    }

    public List<CtClass<?>> getIntersectionTopClasses() {
        return intersectionTopClasses;
    }
}
