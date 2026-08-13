package com.otilm.api.model.client.signing.profile.validation;

import com.otilm.api.model.client.signing.profile.SigningProfileRequestDto;
import com.otilm.api.model.client.signing.profile.scheme.ManagedSigningRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.ContentSigningWorkflowRequestDto;
import com.otilm.api.model.client.signing.profile.workflow.TimestampingWorkflowRequestDto;
import com.otilm.api.model.common.signature.SignatureLevel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.UUID;

public class ManagedSignatureFormattingConnectorValidator
        implements
            ConstraintValidator<ValidManagedSignatureFormattingConnector, SigningProfileRequestDto> {

    @Override
    public boolean isValid(SigningProfileRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getSigningScheme() == null || dto.getWorkflow() == null) {
            return true;
        }
        if (!(dto.getSigningScheme() instanceof ManagedSigningRequestDto)) {
            return validateDelegatedWorkflow(dto, context);
        }

        boolean valid = true;

        UUID formattingConnectorUuid = null;

        if (dto.getWorkflow() instanceof TimestampingWorkflowRequestDto tsw) {
            formattingConnectorUuid = tsw.getSignatureFormattingConnectorUuid();
            if (Boolean.TRUE.equals(tsw.getQualifiedTimestamp()) && tsw.getTimeQualityConfigurationUuid() == null) {
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate(
                                "timeQualityConfigurationUuid must be provided when qualifiedTimestamp is true")
                        .addPropertyNode("workflow")
                        .addPropertyNode("timeQualityConfigurationUuid")
                        .addConstraintViolation();
                valid = false;
            }
        } else if (dto.getWorkflow() instanceof ContentSigningWorkflowRequestDto dsw) {
            formattingConnectorUuid = dsw.getSignatureFormattingConnectorUuid();
            boolean familyPresent = requireOnManagedWorkflow(context, "family", dsw.getFamily());
            boolean maxLevelPresent = requireOnManagedWorkflow(context, "maxLevel", dsw.getMaxLevel());
            valid = familyPresent && maxLevelPresent && requireTimestampSourceAboveSigned(context, dsw) && valid;
        } else {
            return true;
        }

        if (formattingConnectorUuid == null) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("workflow")
                    .addPropertyNode("signatureFormattingConnectorUuid")
                    .addConstraintViolation();
            valid = false;
        }
        return valid;
    }

    /**
     * Levels above SIGNED embed at least one timestamp, so the profile has to say where those timestamps come from. The
     * check lives here rather than on the workflow itself because it applies to ILM-managed signing only.
     */
    private boolean requireTimestampSourceAboveSigned(ConstraintValidatorContext context,
            ContentSigningWorkflowRequestDto workflow) {
        if (workflow.getMaxLevel() == null || workflow.getMaxLevel() == SignatureLevel.SIGNED
                || workflow.getTimestampSource() != null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(
                        "timestampSource is required when maxLevel is TIMESTAMPED or higher")
                .addPropertyNode("workflow")
                .addPropertyNode("timestampSource")
                .addConstraintViolation();
        return false;
    }

    /**
     * Delegated signing formats the signature outside the platform, so the ILM-managed fields have no effect. Rejecting
     * them names the mistake at the field the caller set, rather than storing configuration the platform never honours.
     */
    private boolean validateDelegatedWorkflow(SigningProfileRequestDto dto, ConstraintValidatorContext context) {
        if (!(dto.getWorkflow() instanceof ContentSigningWorkflowRequestDto workflow)) {
            return true;
        }
        boolean connectorAbsent = rejectOnDelegatedWorkflow(context, "signatureFormattingConnectorUuid",
                workflow.getSignatureFormattingConnectorUuid());
        boolean attributesAbsent = rejectOnDelegatedWorkflow(context, "signatureFormattingConnectorAttributes",
                emptyToNull(workflow.getSignatureFormattingConnectorAttributes()));
        boolean familyAbsent = rejectOnDelegatedWorkflow(context, "family", workflow.getFamily());
        boolean maxLevelAbsent = rejectOnDelegatedWorkflow(context, "maxLevel", workflow.getMaxLevel());
        boolean sourceAbsent = rejectOnDelegatedWorkflow(context, "timestampSource", workflow.getTimestampSource());
        return connectorAbsent && attributesAbsent && familyAbsent && maxLevelAbsent && sourceAbsent;
    }

    /** The attribute list defaults to an empty one, so only a populated list counts as the caller having set it. */
    private static Object emptyToNull(List<?> values) {
        return values == null || values.isEmpty() ? null : values;
    }

    private boolean rejectOnDelegatedWorkflow(ConstraintValidatorContext context, String property, Object value) {
        if (value == null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(property + " must be omitted for delegated signing")
                .addPropertyNode("workflow")
                .addPropertyNode(property)
                .addConstraintViolation();
        return false;
    }

    private boolean requireOnManagedWorkflow(ConstraintValidatorContext context, String property, Object value) {
        if (value != null) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(property + " is required for ILM-managed signing")
                .addPropertyNode("workflow")
                .addPropertyNode(property)
                .addConstraintViolation();
        return false;
    }
}
