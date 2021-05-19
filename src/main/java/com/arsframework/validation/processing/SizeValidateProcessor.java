package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Size;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数值大小校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Size")
public class SizeValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param size 校验注解实例
     * @return 类名称
     */
    protected String getException(Size size) {
        try {
            return size.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Size size = (Size) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildSizeExpression(maker, names, param, size.min(), size.max());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(size), size.message(),
                param.name.toString(), size.min(), size.max());
    }
}
