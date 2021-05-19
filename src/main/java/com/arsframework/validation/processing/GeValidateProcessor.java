package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Ge;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数大于等于校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Ge")
public class GeValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param ge 校验注解实例
     * @return 类名称
     */
    protected String getException(Ge ge) {
        try {
            return ge.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Ge ge = (Ge) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildGeExpression(maker, names, param, ge.value());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(ge), ge.message(),
                param.name.toString(), ge.value());
    }
}
