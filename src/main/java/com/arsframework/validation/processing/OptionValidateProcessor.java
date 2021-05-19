package com.arsframework.validation.processing;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.type.MirroredTypeException;

import com.arsframework.validation.Option;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;

/**
 * 参数选项校验注解处理器
 *
 * @author woody
 */
@SupportedAnnotationTypes("com.arsframework.validation.Option")
public class OptionValidateProcessor extends AbstractValidateProcessor {
    /**
     * 获取异常类名称
     *
     * @param option 校验注解实例
     * @return 类名称
     */
    protected String getException(Option option) {
        try {
            return option.exception().getCanonicalName();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror().toString();
        }
    }

    @Override
    protected JCTree.JCIf buildValidateCondition(Symbol.VarSymbol param, Class<? extends Annotation> annotation) {
        Option option = (Option) Validates.lookupAnnotation(param, annotation);
        JCTree.JCExpression condition = Validates.buildOptionExpression(maker, names, param, option.value());
        return Validates.buildValidateException(maker, names, param, condition, this.getException(option),
                option.message(),
                param.name.toString(), Arrays.toString(option.value()));
    }
}
