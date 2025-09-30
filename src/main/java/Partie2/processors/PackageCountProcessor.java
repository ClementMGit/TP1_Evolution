package Partie2.processors;

import Partie2.visitors.PackageCountScanner;
import spoon.reflect.CtModel;

public class PackageCountProcessor {

    public int computePackageCount(CtModel model) {
        PackageCountScanner scanner = new PackageCountScanner();
        model.getRootPackage().accept(scanner);
        return scanner.getPackageCount();
    }
}
