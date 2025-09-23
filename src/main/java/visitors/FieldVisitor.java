package visitors;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;

public class FieldVisitor extends AbstractCtVisitor {

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {
        System.out.println("Classe : " + ctClass.getSimpleName());
        System.out.println("  Attributs :");

        if (ctClass.getFields().isEmpty()) {
            System.out.println("    (aucun)");
        } else {
            for (CtField<?> field : ctClass.getFields()) {
                String visibility = (field.getVisibility() != null)
                        ? field.getVisibility().toString()
                        : "package-private";
                System.out.println("    - " + field.getSimpleName() + " (" + visibility + ")");
            }
        }

        System.out.println("-----------------------------------");
    }
}
