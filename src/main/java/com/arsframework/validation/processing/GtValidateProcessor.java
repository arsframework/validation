package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Gt;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数大于校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Gt")
public class GtValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param gt 校验注解实例
     * @return 类名称
     */
    protected String getException(Gt gt) {
        try {
            return gt.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Gt gt = (Gt) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildGtExpression(maker, names, param, gt.value());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(gt), gt.message(),
                param.name.toString(), gt.value());
    }
}
