package com.otilm.api.model.connector.discovery.v2;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.otilm.api.model.connector.discovery.v2.event.DiscoveryErrorEvent;
import com.otilm.api.model.connector.discovery.v2.event.DiscoveryHeartbeatEvent;
import com.otilm.api.model.connector.discovery.v2.event.DiscoveryProgressEvent;
import com.otilm.api.model.connector.discovery.v2.event.DiscoveryResultBatchEvent;
import com.otilm.api.model.connector.discovery.v2.event.DiscoveryStateChangedEvent;
import io.swagger.v3.oas.annotations.media.DiscriminatorMapping;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * One event on a held-open NDJSON discovery stream — one flat JSON object per line — also used as the payload of the
 * AMQP {@code discovery.event} message.
 *
 * <p>
 * {@code type} is this interface's own discriminator, declared on each subtype rather than on a wrapper. Do not add a
 * {@code {type, payload}} wrapper around it — that would only duplicate the discriminator and let the two copies
 * disagree.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DiscoveryProgressEvent.class, name = DiscoveryEventType.Codes.PROGRESS),
        @JsonSubTypes.Type(value = DiscoveryResultBatchEvent.class, name = DiscoveryEventType.Codes.RESULT_BATCH),
        @JsonSubTypes.Type(value = DiscoveryStateChangedEvent.class, name = DiscoveryEventType.Codes.STATE_CHANGED),
        @JsonSubTypes.Type(value = DiscoveryHeartbeatEvent.class, name = DiscoveryEventType.Codes.HEARTBEAT),
        @JsonSubTypes.Type(value = DiscoveryErrorEvent.class, name = DiscoveryEventType.Codes.ERROR)})
@Schema(name = "DiscoveryEvent", description = "One flat discovery stream/AMQP event; type selects the concrete shape.",
        type = "object", discriminatorProperty = "type",
        discriminatorMapping = {
                @DiscriminatorMapping(value = DiscoveryEventType.Codes.PROGRESS, schema = DiscoveryProgressEvent.class),
                @DiscriminatorMapping(value = DiscoveryEventType.Codes.RESULT_BATCH,
                        schema = DiscoveryResultBatchEvent.class),
                @DiscriminatorMapping(value = DiscoveryEventType.Codes.STATE_CHANGED,
                        schema = DiscoveryStateChangedEvent.class),
                @DiscriminatorMapping(value = DiscoveryEventType.Codes.HEARTBEAT,
                        schema = DiscoveryHeartbeatEvent.class),
                @DiscriminatorMapping(value = DiscoveryEventType.Codes.ERROR, schema = DiscoveryErrorEvent.class)},
        oneOf = {
                DiscoveryProgressEvent.class,
                DiscoveryResultBatchEvent.class,
                DiscoveryStateChangedEvent.class,
                DiscoveryHeartbeatEvent.class,
                DiscoveryErrorEvent.class})
public interface DiscoveryEvent {

    DiscoveryEventType getType();
}
