package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Is;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数固定值校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Is")
public class IsValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param is 校验注解实例
     * @return 类名称
     */
    protected String getException(Is is) {
        try {
            return is.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Is is = (Is) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildIsExpression(maker, names, param, is.value());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(is), is.message(),
                param.name.toString(), is.value());
    }
}
