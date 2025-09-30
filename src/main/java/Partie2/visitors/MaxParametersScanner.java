package Partie2.visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.CtScanner;

public class MaxParametersScanner extends CtScanner {

    private int maxParameters = 0;

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        for (CtMethod<?> method : ctClass.getMethods()) {
            int paramCount = method.getParameters().size();
            if (paramCount > maxParameters) {
                maxParameters = paramCount;
            }
        }
        super.visitCtClass(ctClass);
    }

    public int getMaxParameters() {
        return maxParameters;
    }
}
