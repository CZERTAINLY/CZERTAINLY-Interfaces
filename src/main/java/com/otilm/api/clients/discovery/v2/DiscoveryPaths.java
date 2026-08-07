package com.otilm.api.clients.discovery.v2;

import com.otilm.api.model.core.auth.Resource;

import java.util.EnumSet;
import java.util.Set;

/**
 * The discovery v2 connector routes, defined once for both transports.
 *
 * <p>The REST and MQ clients address the same connector contract, so a route that differs between
 * them is a defect no test in either suite would catch. Both import these constants rather than
 * declaring their own, which is what makes divergence impossible rather than merely unlikely.
 *
 * <p>Streaming ({@code POST /v2/discoveryProvider/discoveries/stream}) is deliberately absent: no
 * client implements it yet. See {@link com.otilm.api.interfaces.client.v2.DiscoverySyncApiClient}.
 */
@SuppressWarnings("java:S1075") // contract paths, not configurable URIs
public final class DiscoveryPaths {

    public static final String BASE = "/v2/discoveryProvider";
    public static final String RESOURCES = BASE + "/resources";
    public static final String ATTRIBUTES = BASE + "/attributes";

    public static final String RUNS = BASE + "/discoveries";
    public static final String INITIATE = RUNS + "/initiate";
    public static final String STATUS = RUNS + "/status";
    public static final String RESULTS = RUNS + "/results";
    public static final String STOP = RUNS + "/stop";
    public static final String RESUME = RUNS + "/resume";
    public static final String CANCEL = RUNS + "/cancel";

    private static final String RESOURCE_ATTRIBUTES_SUFFIX = "/attributes";

    /**
     * The resources a discovery run can report, pinned by the {@code @JsonSubTypes} registered on
     * {@code DiscoveredItemPayloadDto} — a payload type exists for exactly these two.
     */
    private static final Set<Resource> DISCOVERABLE =
            EnumSet.of(Resource.CERTIFICATE, Resource.CRYPTOGRAPHIC_KEY);

    private DiscoveryPaths() {
    }

    /**
     * The per-resource attribute route, bound by the resource's wire code ({@code "certificates"},
     * {@code "keys"}) rather than its Java enum name.
     *
     * <p>Rejects a resource that discovery cannot report. Without the check, {@code Resource.DISCOVERY}
     * (code {@code "discoveries"}) would compose to {@code /v2/discoveryProvider/discoveries/attributes}
     * — a path inside the run-lifecycle namespace — so a caller mistake would surface as a puzzling 404
     * from what looks like a lifecycle route instead of as the argument error it is.
     *
     * @throws IllegalArgumentException if {@code resource} is not discoverable
     */
    public static String resourceAttributes(Resource resource) {
        if (!DISCOVERABLE.contains(resource)) {
            throw new IllegalArgumentException(
                    "Resource " + resource + " is not discoverable; expected one of " + DISCOVERABLE);
        }
        return BASE + "/" + resource.getCode() + RESOURCE_ATTRIBUTES_SUFFIX;
    }
}
