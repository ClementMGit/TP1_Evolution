package Partie1;

import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.*;
import spoon.reflect.visitor.CtVisitor;

import java.lang.annotation.Annotation;

public abstract class AbstractCtVisitor implements CtVisitor {
    @Override
    public <A extends Annotation> void visitCtAnnotation(CtAnnotation<A> ctAnnotation) {

    }

    @Override
    public <T> void visitCtCodeSnippetExpression(CtCodeSnippetExpression<T> ctCodeSnippetExpression) {

    }

    @Override
    public void visitCtCodeSnippetStatement(CtCodeSnippetStatement ctCodeSnippetStatement) {

    }

    @Override
    public <A extends Annotation> void visitCtAnnotationType(CtAnnotationType<A> ctAnnotationType) {

    }

    @Override
    public void visitCtAnonymousExecutable(CtAnonymousExecutable ctAnonymousExecutable) {

    }

    @Override
    public <T> void visitCtArrayRead(CtArrayRead<T> ctArrayRead) {

    }

    @Override
    public <T> void visitCtArrayWrite(CtArrayWrite<T> ctArrayWrite) {

    }

    @Override
    public <T> void visitCtArrayTypeReference(CtArrayTypeReference<T> ctArrayTypeReference) {

    }

    @Override
    public <T> void visitCtAssert(CtAssert<T> ctAssert) {

    }

    @Override
    public <T, A extends T> void visitCtAssignment(CtAssignment<T, A> ctAssignment) {

    }

    @Override
    public <T> void visitCtBinaryOperator(CtBinaryOperator<T> ctBinaryOperator) {

    }

    @Override
    public <R> void visitCtBlock(CtBlock<R> ctBlock) {

    }

    @Override
    public void visitCtBreak(CtBreak ctBreak) {

    }

    @Override
    public <S> void visitCtCase(CtCase<S> ctCase) {

    }

    @Override
    public void visitCtCatch(CtCatch ctCatch) {

    }

    @Override
    public <T> void visitCtClass(CtClass<T> ctClass) {

    }

    @Override
    public void visitCtTypeParameter(CtTypeParameter ctTypeParameter) {

    }

    @Override
    public <T> void visitCtConditional(CtConditional<T> ctConditional) {

    }

    @Override
    public <T> void visitCtConstructor(CtConstructor<T> ctConstructor) {

    }

    @Override
    public void visitCtContinue(CtContinue ctContinue) {

    }

    @Override
    public void visitCtDo(CtDo ctDo) {

    }

    @Override
    public <T extends Enum<?>> void visitCtEnum(CtEnum<T> ctEnum) {

    }

    @Override
    public <T> void visitCtExecutableReference(CtExecutableReference<T> ctExecutableReference) {

    }

    @Override
    public <T> void visitCtField(CtField<T> ctField) {

    }

    @Override
    public <T> void visitCtEnumValue(CtEnumValue<T> ctEnumValue) {

    }

    @Override
    public <T> void visitCtThisAccess(CtThisAccess<T> ctThisAccess) {

    }

    @Override
    public <T> void visitCtFieldReference(CtFieldReference<T> ctFieldReference) {

    }

    @Override
    public <T> void visitCtUnboundVariableReference(CtUnboundVariableReference<T> ctUnboundVariableReference) {

    }

    @Override
    public void visitCtFor(CtFor ctFor) {

    }

    @Override
    public void visitCtForEach(CtForEach ctForEach) {

    }

    @Override
    public void visitCtIf(CtIf ctIf) {

    }

    @Override
    public <T> void visitCtInterface(CtInterface<T> ctInterface) {

    }

    @Override
    public <T> void visitCtInvocation(CtInvocation<T> ctInvocation) {

    }

    @Override
    public <T> void visitCtLiteral(CtLiteral<T> ctLiteral) {

    }

    @Override
    public void visitCtTextBlock(CtTextBlock ctTextBlock) {

    }

    @Override
    public <T> void visitCtLocalVariable(CtLocalVariable<T> ctLocalVariable) {

    }

    @Override
    public <T> void visitCtLocalVariableReference(CtLocalVariableReference<T> ctLocalVariableReference) {

    }

    @Override
    public <T> void visitCtCatchVariable(CtCatchVariable<T> ctCatchVariable) {

    }

    @Override
    public <T> void visitCtCatchVariableReference(CtCatchVariableReference<T> ctCatchVariableReference) {

    }

    @Override
    public <T> void visitCtMethod(CtMethod<T> ctMethod) {

    }

    @Override
    public <T> void visitCtAnnotationMethod(CtAnnotationMethod<T> ctAnnotationMethod) {

    }

    @Override
    public <T> void visitCtNewArray(CtNewArray<T> ctNewArray) {

    }

    @Override
    public <T> void visitCtConstructorCall(CtConstructorCall<T> ctConstructorCall) {

    }

    @Override
    public <T> void visitCtNewClass(CtNewClass<T> ctNewClass) {

    }

    @Override
    public <T> void visitCtLambda(CtLambda<T> ctLambda) {

    }

    @Override
    public <T, E extends CtExpression<?>> void visitCtExecutableReferenceExpression(CtExecutableReferenceExpression<T, E> ctExecutableReferenceExpression) {

    }

    @Override
    public <T, A extends T> void visitCtOperatorAssignment(CtOperatorAssignment<T, A> ctOperatorAssignment) {

    }

    @Override
    public void visitCtPackage(CtPackage ctPackage) {

    }

    @Override
    public void visitCtPackageReference(CtPackageReference ctPackageReference) {

    }

    @Override
    public <T> void visitCtParameter(CtParameter<T> ctParameter) {

    }

    @Override
    public <T> void visitCtParameterReference(CtParameterReference<T> ctParameterReference) {

    }

    @Override
    public <R> void visitCtReturn(CtReturn<R> ctReturn) {

    }

    @Override
    public <R> void visitCtStatementList(CtStatementList ctStatementList) {

    }

    @Override
    public <S> void visitCtSwitch(CtSwitch<S> ctSwitch) {

    }

    @Override
    public <T, S> void visitCtSwitchExpression(CtSwitchExpression<T, S> ctSwitchExpression) {

    }

    @Override
    public void visitCtSynchronized(CtSynchronized ctSynchronized) {

    }

    @Override
    public void visitCtThrow(CtThrow ctThrow) {

    }

    @Override
    public void visitCtTry(CtTry ctTry) {

    }

    @Override
    public void visitCtTryWithResource(CtTryWithResource ctTryWithResource) {

    }

    @Override
    public void visitCtTypeParameterReference(CtTypeParameterReference ctTypeParameterReference) {

    }

    @Override
    public void visitCtWildcardReference(CtWildcardReference ctWildcardReference) {

    }

    @Override
    public <T> void visitCtIntersectionTypeReference(CtIntersectionTypeReference<T> ctIntersectionTypeReference) {

    }

    @Override
    public <T> void visitCtTypeReference(CtTypeReference<T> ctTypeReference) {

    }

    @Override
    public <T> void visitCtTypeAccess(CtTypeAccess<T> ctTypeAccess) {

    }

    @Override
    public <T> void visitCtUnaryOperator(CtUnaryOperator<T> ctUnaryOperator) {

    }

    @Override
    public <T> void visitCtVariableRead(CtVariableRead<T> ctVariableRead) {

    }

    @Override
    public <T> void visitCtVariableWrite(CtVariableWrite<T> ctVariableWrite) {

    }

    @Override
    public void visitCtWhile(CtWhile ctWhile) {

    }

    @Override
    public <T> void visitCtAnnotationFieldAccess(CtAnnotationFieldAccess<T> ctAnnotationFieldAccess) {

    }

    @Override
    public <T> void visitCtFieldRead(CtFieldRead<T> ctFieldRead) {

    }

    @Override
    public <T> void visitCtFieldWrite(CtFieldWrite<T> ctFieldWrite) {

    }

    @Override
    public <T> void visitCtSuperAccess(CtSuperAccess<T> ctSuperAccess) {

    }

    @Override
    public void visitCtComment(CtComment ctComment) {

    }

    @Override
    public void visitCtJavaDoc(CtJavaDoc ctJavaDoc) {

    }

    @Override
    public void visitCtJavaDocTag(CtJavaDocTag ctJavaDocTag) {

    }

    @Override
    public void visitCtImport(CtImport ctImport) {

    }

    @Override
    public void visitCtModule(CtModule ctModule) {

    }

    @Override
    public void visitCtModuleReference(CtModuleReference ctModuleReference) {

    }

    @Override
    public void visitCtPackageExport(CtPackageExport ctPackageExport) {

    }

    @Override
    public void visitCtModuleRequirement(CtModuleRequirement ctModuleRequirement) {

    }

    @Override
    public void visitCtProvidedService(CtProvidedService ctProvidedService) {

    }

    @Override
    public void visitCtUsedService(CtUsedService ctUsedService) {

    }

    @Override
    public void visitCtCompilationUnit(CtCompilationUnit ctCompilationUnit) {

    }

    @Override
    public void visitCtPackageDeclaration(CtPackageDeclaration ctPackageDeclaration) {

    }

    @Override
    public void visitCtTypeMemberWildcardImportReference(CtTypeMemberWildcardImportReference ctTypeMemberWildcardImportReference) {

    }

    @Override
    public void visitCtYieldStatement(CtYieldStatement ctYieldStatement) {

    }

    @Override
    public void visitCtTypePattern(CtTypePattern ctTypePattern) {

    }

    @Override
    public void visitCtRecord(CtRecord ctRecord) {

    }

    @Override
    public void visitCtRecordComponent(CtRecordComponent ctRecordComponent) {

    }

    @Override
    public void visitCtCasePattern(CtCasePattern ctCasePattern) {

    }

    @Override
    public void visitCtRecordPattern(CtRecordPattern ctRecordPattern) {

    }

    @Override
    public void visitCtReceiverParameter(CtReceiverParameter ctReceiverParameter) {

    }
}