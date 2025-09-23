package Partie2;


import spoon.reflect.declaration.CtClass;

public class AverageMethodsPerClassVisitor extends AbstractCtVisitor {
    private int totalClasses = 0;
    private int totalMethods = 0;


    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        totalClasses++;
        totalMethods += ctClass.getMethods().size();
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getTotalMethods() {
        return totalMethods;
    }

    public void setTotalMethods(int totalMethods) {
        this.totalMethods = totalMethods;
    }
}
