package Partie2;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;

public class LineCountVisitor extends AbstractCtVisitor {
    private int totalLines = 0;

    public int getLineCount() {
        return totalLines;
    }

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        int minLine = Integer.MAX_VALUE;
        int maxLine = Integer.MIN_VALUE;

        // Parcours de tous les éléments de la classe
        for (CtElement element : ctClass.getElements(e -> true)) {
            if (element.getPosition() != null && element.getPosition().isValidPosition()) {
                minLine = Math.min(minLine, element.getPosition().getLine());
                maxLine = Math.max(maxLine, element.getPosition().getEndLine());
            }
        }

        if (minLine != Integer.MAX_VALUE && maxLine != Integer.MIN_VALUE) {
            totalLines += (maxLine - minLine + 1);
        }
    }
}
