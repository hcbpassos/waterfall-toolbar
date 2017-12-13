package com.hugocastelani.waterfalltoolbar.library.annotation;

import android.support.annotation.Dimension;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Created by Hugo Castelani
 * Date: 19/09/17
 * Time: 20:48
 *
 * Denotes that an integer parameter, field or method return value is expected
 * to represent a dp dimension.
 */
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})
@Dimension(unit = Dimension.DP)
public @interface Dp {
}
