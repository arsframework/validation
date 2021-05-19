package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Format;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数格式校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Format")
public class FormatValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param format 校验注解实例
     * @return 类名称
     */
    protected String getException(Format format) {
        try {
            return format.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Format format = (Format) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildFormatExpression(maker, names, param, format.value());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(format),
                format.message(),
                param.name.toString(), format.value());
    }
}
