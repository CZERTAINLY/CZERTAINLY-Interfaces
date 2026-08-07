package com.otilm.api.testsupport;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Owns a bean-validation {@link ValidatorFactory} so a test class does not have to. A
 * {@link ValidatorFactory} is {@link AutoCloseable}, so building one and keeping only the
 * {@link Validator} leaks it; declaring this as an {@code @AutoClose} static field gets it closed
 * without a {@code @BeforeAll}/{@code @AfterAll} pair repeated in every validation test:
 *
 * <pre>{@code
 * @AutoClose
 * private static final ValidatorFixture VALIDATORS = new ValidatorFixture();
 * private static final Validator VALIDATOR = VALIDATORS.validator();
 * }</pre>
 */
public final class ValidatorFixture implements AutoCloseable {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public Validator validator() {
        return this.factory.getValidator();
    }

    @Override
    public void close() {
        this.factory.close();
    }
}
