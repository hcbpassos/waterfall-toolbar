package com.hugocastelani.waterfalltoolbar.library.annotation;

import android.support.annotation.IntRange;

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
 * Time: 19:37
 *
 * Denotes that an integer parameter, field or method return value is expected
 * to represent a percentage.
 */
@Retention(CLASS)
@Target({METHOD, PARAMETER, FIELD, LOCAL_VARIABLE})
@IntRange(from = 0, to = Integer.MAX_VALUE)
public @interface Percentage {
}
