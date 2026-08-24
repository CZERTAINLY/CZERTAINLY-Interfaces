package com.otilm.api.model.common.signature;

import lombok.Getter;
import lombok.Setter;

/**
 * A parameter object declared outside the parameters package, for the group-contract sweep to recurse into. Container
 * detection that tests the package name classifies this as a value and lets every field inside it ship unenforced.
 */
@Getter
@Setter
public class AnotherPackageParameterFixture {

    private String forgottenParameter;
}
