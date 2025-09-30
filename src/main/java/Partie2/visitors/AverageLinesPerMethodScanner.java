package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;

public class AverageLinesPerMethodScanner extends CtScanner {

    private int totalMethods = 0;
    private int totalLines = 0;

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        for (CtMethod<?> method : ctClass.getMethods()) {
            totalMethods++;
            int minLine = Integer.MAX_VALUE;
            int maxLine = Integer.MIN_VALUE;

            for (CtElement element : method.getElements(e -> true)) {
                if (element.getPosition() != null && element.getPosition().isValidPosition()) {
                    minLine = Math.min(minLine, element.getPosition().getLine());
                    maxLine = Math.max(maxLine, element.getPosition().getEndLine());
                }
            }

            if (minLine != Integer.MAX_VALUE && maxLine != Integer.MIN_VALUE) {
                totalLines += (maxLine - minLine + 1);
            }
        }

        super.visitCtClass(ctClass); // Continue le scan
    }

    public int getTotalMethods() {
        return totalMethods;
    }

    public int getTotalLines() {
        return totalLines;
    }
}
