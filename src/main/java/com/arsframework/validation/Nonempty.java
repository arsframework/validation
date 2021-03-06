package com.arsframework.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数非空校验注解，适用于除基本数据类型以外的参数
 *
 * @author woody
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER})
public @interface Nonempty {
    /**
     * 默认异常信息
     */
    String DEFAULT_EXCEPTION_MESSAGE = "The value of argument '%s' must not be empty";

    /**
     * 针对字符串参数是否允许空白
     *
     * @return true/false
     */
    boolean blank() default true;

    /**
     * 参数验证失败消息，格式化参数:参数名称
     *
     * @return 消息字符串
     */
    String message() default DEFAULT_EXCEPTION_MESSAGE;

    /**
     * 参数验证失败异常类型
     *
     * @return 异常类型
     */
    Class<? extends Throwable> exception() default IllegalArgumentException.class;
}
