package Partie2;

import spoon.reflect.declaration.CtClass;

public class AverageAttributesPerClassVisitor extends AbstractCtVisitor {
    private int totalClasses = 0;
    private int totalAttributes = 0;


    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        totalClasses++;
        totalAttributes += ctClass.getFields().size();
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }

    public int getTotalAttributes() {
        return totalAttributes;
    }

    public void setTotalAttributes(int totalAttributes) {
        this.totalAttributes = totalAttributes;
    }
}
