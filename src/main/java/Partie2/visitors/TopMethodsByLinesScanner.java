package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.CtScanner;

import java.util.HashMap;
import java.util.Map;

public class TopMethodsByLinesScanner extends CtScanner {

    private final Map<CtMethod<?>, Integer> methodLineCount = new HashMap<>();

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        for (CtMethod<?> method : ctClass.getMethods()) {
            int minLine = Integer.MAX_VALUE;
            int maxLine = Integer.MIN_VALUE;

            for (CtElement element : method.getElements(e -> true)) {
                if (element.getPosition() != null && element.getPosition().isValidPosition()) {
                    minLine = Math.min(minLine, element.getPosition().getLine());
                    maxLine = Math.max(maxLine, element.getPosition().getEndLine());
                }
            }

            if (minLine != Integer.MAX_VALUE && maxLine != Integer.MIN_VALUE) {
                int lines = maxLine - minLine + 1;
                methodLineCount.put(method, lines);
            } else {
                methodLineCount.put(method, 0);
            }
        }
        super.visitCtClass(ctClass);
    }

    public Map<CtMethod<?>, Integer> getMethodLineCount() {
        return methodLineCount;
    }
}
