package com.otilm.api.model.connector.discovery.v2;

import lombok.ToString;

/**
 * Body for the discovery v2 status/stop/resume/cancel calls. These calls carry no payload beyond the identity and
 * configuration inherited from the scoped base.
 */
@ToString(callSuper = true)
public class DiscoveryRunRequestDto extends DiscoveryV2ScopedRequestDto {
}
