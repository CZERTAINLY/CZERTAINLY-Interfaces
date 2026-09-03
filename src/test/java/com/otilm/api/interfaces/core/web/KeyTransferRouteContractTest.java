package com.otilm.api.interfaces.core.web;

import com.otilm.api.model.common.enums.IPlatformEnum;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Named.named;

/**
 * Holds the routes of the controllers the key-transfer journeys extend to one meaning per request. A literal segment
 * beside a path variable is fine until the literal becomes a legal value of that variable, at which point one route
 * silently swallows the other and nothing reports it.
 */
class KeyTransferRouteContractTest {

    private static final String TEMPLATE = "{";

    /** What the compiler names a parameter when the class carries no parameter names. */
    private static final Pattern SYNTHETIC_PARAMETER_NAME = Pattern.compile("arg\\d+");

    static Stream<Named<Class<?>>> extendedControllers() {
        return Stream
                .of(named("CryptographicKeyController", CryptographicKeyController.class),
                        named("CertificateController", CertificateController.class));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("extendedControllers")
    void noLiteralSegmentIsAlsoALegalValueOfTheVariableItCompetesWith(Class<?> controller) {
        List<Route> routes = routes(controller);

        List<String> shadowed = new ArrayList<>();
        for (Route literal : routes) {
            for (Route templated : routes) {
                if (literal == templated) {
                    continue;
                }
                literal
                        .shadowedSegment(templated)
                        .ifPresent(segment -> shadowed
                                .add(literal + " and " + templated + " both match a request where the "
                                        + segment.variable() + " is \"" + segment.value() + "\""));
            }
        }

        assertTrue(shadowed.isEmpty(), () -> "routes that cannot be told apart: " + shadowed);
    }

    private static List<Route> routes(Class<?> controller) {
        RequestMapping base = controller.getAnnotation(RequestMapping.class);
        String prefix = base == null || base.value().length == 0 ? "" : base.value()[0];

        List<Route> routes = new ArrayList<>();
        for (Method method : controller.getDeclaredMethods()) {
            mapping(method)
                    .forEach(mapped -> routes.add(new Route(mapped.verb(), prefix + mapped.path(), mapped.method())));
        }
        return routes;
    }

    private static List<Mapped> mapping(Method method) {
        List<Mapped> mapped = new ArrayList<>();
        collect(mapped, "GET", method.getAnnotation(GetMapping.class), method);
        collect(mapped, "POST", method.getAnnotation(PostMapping.class), method);
        collect(mapped, "PUT", method.getAnnotation(PutMapping.class), method);
        collect(mapped, "PATCH", method.getAnnotation(PatchMapping.class), method);
        collect(mapped, "DELETE", method.getAnnotation(DeleteMapping.class), method);
        return mapped;
    }

    private static void collect(List<Mapped> into, String verb, Annotation annotation, Method method) {
        if (annotation == null) {
            return;
        }
        for (String path : paths(annotation)) {
            into.add(new Mapped(verb, path, method));
        }
    }

    private static Set<String> paths(Annotation annotation) {
        Set<String> paths = new LinkedHashSet<>();
        for (String attribute : List.of("path", "value")) {
            try {
                paths
                        .addAll(Arrays
                                .asList((String[]) annotation
                                        .annotationType()
                                        .getMethod(attribute)
                                        .invoke(annotation)));
            } catch (ReflectiveOperationException e) {
                throw new IllegalStateException("cannot read " + attribute + " from " + annotation, e);
            }
        }
        if (paths.isEmpty()) {
            paths.add("");
        }
        return paths;
    }

    private record Mapped(String verb, String path, Method method) {
    }

    private record Collision(String variable, String value) {
    }

    private record Route(String verb, String path, Method method) {

        /**
         * The segment where this route's literal is also a legal value of the other route's path variable, if there is
         * one. Both routes must otherwise be the same shape, since only then can one request match both.
         */
        private Optional<Collision> shadowedSegment(Route other) {
            if (!verb.equals(other.verb)) {
                return Optional.empty();
            }
            String[] mine = path.split("/");
            String[] theirs = other.path.split("/");
            if (mine.length != theirs.length) {
                return Optional.empty();
            }

            Collision collision = null;
            for (int i = 0; i < mine.length; i++) {
                boolean mineIsTemplate = mine[i].startsWith(TEMPLATE);
                boolean theirsIsTemplate = theirs[i].startsWith(TEMPLATE);
                if (mine[i].equals(theirs[i]) || (mineIsTemplate && theirsIsTemplate)) {
                    continue;
                }
                if (mineIsTemplate || !theirsIsTemplate) {
                    return Optional.empty();
                }
                if (collision != null) {
                    return Optional.empty();
                }
                String variable = theirs[i].substring(1, theirs[i].length() - 1);
                if (!legalValues(other.method, variable).contains(mine[i])) {
                    return Optional.empty();
                }
                collision = new Collision(variable, mine[i]);
            }
            return Optional.ofNullable(collision);
        }

        @Override
        public String toString() {
            return verb + " " + path;
        }
    }

    /** The values a path variable accepts, when its type is an enum. Anything else accepts any segment. */
    private static Set<String> legalValues(Method method, String variable) {
        for (Parameter parameter : method.getParameters()) {
            PathVariable annotation = parameter.getAnnotation(PathVariable.class);
            if (annotation == null) {
                continue;
            }
            String name = annotation.value().isEmpty() ? annotation.name() : annotation.value();
            if (name.isEmpty()) {
                name = parameter.getName();
                if (SYNTHETIC_PARAMETER_NAME.matcher(name).matches()) {
                    // Compiled without parameter names, so an unnamed path variable reads "arg0", matches no route
                    // template, and would leave this guard passing while checking nothing.
                    throw new IllegalStateException(method.getName() + " declares a path variable whose name this "
                            + "guard cannot resolve; the build must compile with parameter names");
                }
            }
            if (!name.equals(variable) || !parameter.getType().isEnum()) {
                continue;
            }
            Set<String> values = new LinkedHashSet<>();
            for (Object constant : parameter.getType().getEnumConstants()) {
                values.add(((Enum<?>) constant).name());
                if (constant instanceof IPlatformEnum platformEnum) {
                    values.add(platformEnum.getCode());
                }
            }
            return values;
        }
        return Set.of();
    }
}
