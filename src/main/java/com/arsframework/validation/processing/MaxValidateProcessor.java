package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Max;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数最大值校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Max")
public class MaxValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param max 校验注解实例
     * @return 类名称
     */
    protected String getException(Max max) {
        try {
            return max.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Max max = (Max) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildMaxExpression(maker, names, param, max.value());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(max), max.message(),
                param.name.toString(), max.value());
    }
}
