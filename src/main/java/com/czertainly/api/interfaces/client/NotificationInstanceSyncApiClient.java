package com.czertainly.api.interfaces.client;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.common.attribute.v2.DataAttribute;
import com.czertainly.api.model.connector.notification.NotificationProviderInstanceDto;
import com.czertainly.api.model.connector.notification.NotificationProviderInstanceRequestDto;
import com.czertainly.api.model.connector.notification.NotificationProviderNotifyRequestDto;
import com.czertainly.api.model.core.connector.ConnectorDto;

import java.util.List;

/**
 * Synchronous API client interface for Notification Instance operations.
 * This interface provides an abstraction layer allowing both REST and MQ implementations.
 */
public interface NotificationInstanceSyncApiClient {

    /**
     * List all notification instances.
     *
     * @param connector Connector to use
     * @return List of notification instances
     * @throws ConnectorException If there is an error communicating with the connector
     */
    List<NotificationProviderInstanceDto> listNotificationInstances(ConnectorDto connector) throws ConnectorException;

    /**
     * Get a specific notification instance.
     *
     * @param connector Connector to use
     * @param uuid Notification instance UUID
     * @return Notification instance details
     * @throws ConnectorException If there is an error communicating with the connector
     */
    NotificationProviderInstanceDto getNotificationInstance(ConnectorDto connector, String uuid) throws ConnectorException;

    /**
     * Create a new notification instance.
     *
     * @param connector Connector to use
     * @param requestDto Notification instance creation request
     * @return Created notification instance
     * @throws ConnectorException If there is an error communicating with the connector
     */
    NotificationProviderInstanceDto createNotificationInstance(ConnectorDto connector, NotificationProviderInstanceRequestDto requestDto) throws ConnectorException;

    /**
     * Update an existing notification instance.
     *
     * @param connector Connector to use
     * @param uuid Notification instance UUID
     * @param requestDto Notification instance update request
     * @return Updated notification instance
     * @throws ConnectorException If there is an error communicating with the connector
     */
    NotificationProviderInstanceDto updateNotificationInstance(ConnectorDto connector, String uuid, NotificationProviderInstanceRequestDto requestDto) throws ConnectorException;

    /**
     * Remove a notification instance.
     *
     * @param connector Connector to use
     * @param uuid Notification instance UUID
     * @throws ConnectorException If there is an error communicating with the connector
     */
    void removeNotificationInstance(ConnectorDto connector, String uuid) throws ConnectorException;

    /**
     * Send a notification.
     *
     * @param connector Connector to use
     * @param uuid Notification instance UUID
     * @param requestDto Notification request
     * @throws ConnectorException If there is an error communicating with the connector
     */
    void sendNotification(ConnectorDto connector, String uuid, NotificationProviderNotifyRequestDto requestDto) throws ConnectorException;

    /**
     * List mapping attributes for a notification kind.
     *
     * @param connector Connector to use
     * @param kind Notification provider kind
     * @return List of data attributes
     * @throws ConnectorException If there is an error communicating with the connector
     */
    List<DataAttribute> listMappingAttributes(ConnectorDto connector, String kind) throws ConnectorException;
}
