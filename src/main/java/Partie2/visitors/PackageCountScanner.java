package Partie2.visitors;

import spoon.reflect.declaration.CtPackage;
import spoon.reflect.visitor.CtScanner;

public class PackageCountScanner extends CtScanner {

    private int packageCount = 0;

    @Override
    public void visitCtPackage(CtPackage ctPackage) {
        // Incrémente le compteur pour chaque package rencontré
        packageCount++;
        super.visitCtPackage(ctPackage); // Continue le scan récursif
    }

    public int getPackageCount() {
        return packageCount;
    }
}
