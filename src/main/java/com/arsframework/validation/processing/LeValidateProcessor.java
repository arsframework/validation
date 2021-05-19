package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Le;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数小于等于校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Le")
public class LeValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param le 校验注解实例
     * @return 类名称
     */
    protected String getException(Le le) {
        try {
            return le.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Le le = (Le) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildLeExpression(maker, names, param, le.value());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(le), le.message(),
                param.name.toString(), le.value());
    }
}
