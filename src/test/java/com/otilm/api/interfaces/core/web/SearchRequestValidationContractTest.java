package com.otilm.api.interfaces.core.web;

import com.otilm.api.model.client.certificate.SearchRequestDto;
import jakarta.validation.Valid;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Every listing endpoint takes a {@link SearchRequestDto}, and that shape now carries bean-validation constraints on
 * its sort and column fields. Spring runs none of them unless the body parameter is annotated {@code @Valid} as well as
 * {@code @RequestBody}, so a constraint added to the shape is inert on any endpoint that forgot it — the request binds,
 * the service is invoked, and a sort naming no field reaches the query builder as if it were well formed. The scan
 * below fails the build instead of letting a new listing endpoint reintroduce that gap.
 */
class SearchRequestValidationContractTest {

    private static final Path CONTROLLER_ROOT = controllerRoot();

    @Test
    void everySearchRequestBodyIsBeanValidated() {
        List<String> unvalidated = new ArrayList<>();
        int bodies = 0;

        for (Class<?> controller : controllers()) {
            for (Method method : controller.getDeclaredMethods()) {
                for (Parameter parameter : method.getParameters()) {
                    if (!isSearchRequestBody(parameter)) {
                        continue;
                    }
                    bodies++;
                    if (parameter.getAnnotation(Valid.class) == null) {
                        unvalidated.add(controller.getSimpleName() + "." + method.getName());
                    }
                }
            }
        }

        assertTrue(bodies >= 17, "expected the scan to reach every listing endpoint, found only " + bodies);
        assertEquals(List.of(), unvalidated,
                "these listing endpoints bind a search request without validating it: " + unvalidated);
    }

    private static boolean isSearchRequestBody(Parameter parameter) {
        return parameter.getAnnotation(RequestBody.class) != null
                && SearchRequestDto.class.isAssignableFrom(parameter.getType());
    }

    private static List<Class<?>> controllers() {
        try (Stream<Path> tree = Files.walk(CONTROLLER_ROOT)) {
            return tree
                    .filter(path -> path.getFileName().toString().endsWith("Controller.class"))
                    .map(SearchRequestValidationContractTest::load)
                    .filter(Class::isInterface)
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Class<?> load(Path classFile) {
        String relative = CONTROLLER_ROOT.relativize(classFile).toString();
        String name = ListViewController.class.getPackageName() + "."
                + relative
                        .substring(0, relative.length() - ".class".length())
                        .replace(classFile.getFileSystem().getSeparator(), ".");
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new AssertionError("compiled class not on the test classpath: " + name, e);
        }
    }

    private static Path controllerRoot() {
        try {
            Path classes = Path
                    .of(ListViewController.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            Path root = classes.resolve(ListViewController.class.getPackageName().replace('.', '/'));
            assertTrue(Files.isDirectory(root), "compiled controllers not found at " + root);
            return root;
        } catch (URISyntaxException e) {
            throw new AssertionError("cannot locate the compiled controllers", e);
        }
    }

    @Test
    void scansTheControllersItClaimsTo() {
        // a scan that quietly matched nothing would pass the assertion above forever
        assertTrue(controllers().stream().anyMatch(c -> c.equals(CertificateController.class)),
                "the scan missed CertificateController, so it is not reading the compiled controllers");
        assertTrue(controllers().size() > 30, "expected the scan to see every controller in the package");
    }

    @Test
    void reachesControllersInNestedPackages() {
        // ConnectorController v2 lives one package down; a non-recursive walk would skip it
        assertTrue(
                controllers()
                        .stream()
                        .anyMatch(c -> c.getName().equals("com.otilm.api.interfaces.core.web.v2.ConnectorController")),
                "the scan does not descend into nested controller packages");
    }

    @Test
    void ignoresBodiesThatAreNotSearchRequests() {
        // the rule is scoped to the search shape, so an unrelated body must not be swept in
        Method create = Arrays
                .stream(ListViewController.class.getDeclaredMethods())
                .filter(m -> m.getName().equals("createView"))
                .findFirst()
                .orElseThrow();
        assertTrue(Arrays
                .stream(create.getParameters())
                .noneMatch(SearchRequestValidationContractTest::isSearchRequestBody));
    }
}
