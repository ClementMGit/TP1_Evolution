package Partie2;


import spoon.reflect.declaration.CtPackage;

public class PackageCountVisitor extends AbstractCtVisitor {
    private int count = 0;

    @Override
    public void visitCtPackage(CtPackage ctPackage) {
        count++;
    }

    public int getCount() {
        return count;
    }
}