package com.otilm.api.model.connector.signatures.formatting;

import com.otilm.api.model.client.signing.profile.workflow.SigningWorkflowType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Adds no fields beyond FormatDtbsRequestDto; this subtype reserves the content-signing extension point.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(title = "ContentSigningFormatDtbsRequestDto", description = "DTBS formatting request for Content Signing")
public class ContentSigningFormatDtbsRequestDto extends FormatDtbsRequestDto {

    public ContentSigningFormatDtbsRequestDto() {
        super(SigningWorkflowType.CONTENT_SIGNING);
    }
}
