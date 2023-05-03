package com.czertainly.api.clients;

import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.model.scheduler.SchedulerJobHistory;
import com.czertainly.api.model.scheduler.SchedulerRequestDto;
import com.czertainly.api.model.scheduler.SchedulerResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@NoArgsConstructor
public class SchedulerApiClient extends CzertainlyBaseApiClient{

    @Value("${scheduler.base-url}")
    private String schedulerBaseUrl;

    private static final String SCHEDULER_CREATE = "/v1/scheduler/create";
    private static final String SCHEDULER_HISTORY = "/v1/scheduler/history";

    public SchedulerResponseDto schedulerCreate(SchedulerRequestDto schedulerDto) throws ConnectorException {
        final WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST);
        return processRequest(r -> r
                        .uri(schedulerBaseUrl + SCHEDULER_CREATE)
                        .body(Mono.just(schedulerDto), SchedulerRequestDto.class)
                        .retrieve()
                        .toEntity(SchedulerResponseDto.class)
                        .block().getBody(),
                request);
    }

    public void informJobHistory(final SchedulerJobHistory schedulerHistory) throws ConnectorException {
        final WebClient.RequestBodyUriSpec request = prepareRequest(HttpMethod.POST);
        processRequest(r -> r
                        .uri(schedulerBaseUrl + SCHEDULER_HISTORY)
                        .body(Mono.just(schedulerHistory), SchedulerJobHistory.class)
                        .retrieve()
                        .toEntity(Void.class)
                        .block().getBody(),
                request);
    }


    @Override
    protected String getServiceUrl() {
        return schedulerBaseUrl;
    }
}
