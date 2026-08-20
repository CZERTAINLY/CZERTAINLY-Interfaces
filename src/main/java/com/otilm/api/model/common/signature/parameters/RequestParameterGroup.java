package com.otilm.api.model.common.signature.parameters;

import com.otilm.api.model.common.signature.SignatureParameterGroup;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The group a signature-parameter field belongs to, and the single authority on that membership.
 *
 * <p>
 * DTO nesting is a readability device; enforcement reads this annotation. A parameter field without one would ship
 * unenforced, so a contract test fails the build rather than letting that happen.
 * </p>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParameterGroup {

    SignatureParameterGroup value();
}
