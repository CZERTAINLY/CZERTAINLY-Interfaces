package com.otilm.api.interfaces.core.web;

import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.listview.ListViewDto;
import com.otilm.api.model.core.listview.ListViewRequestDto;
import com.otilm.api.model.core.listview.ListViewUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the published wire shape of {@link ListViewController} against annotation values rather than prose. These paths
 * are what the frontend's generated client calls and what Core implements, so a typo in one of them has to fail a build
 * rather than ship.
 */
class ListViewControllerContractTest {

    private static final String UUID_PATH = "/{uuid}";

    @Test
    void isPublishedUnderItsDocumentedBasePath() {
        RequestMapping mapping = ListViewController.class.getAnnotation(RequestMapping.class);
        assertNotNull(mapping, "missing @RequestMapping on ListViewController");
        assertEquals(1, mapping.value().length, "expected exactly one base path");
        assertEquals("/v1/listViews", mapping.value()[0]);
    }

    @Test
    void isAuthenticationProtected() {
        // views are per user, so the endpoint that identifies the user must never be anonymous
        assertTrue(AuthProtectedController.class.isAssignableFrom(ListViewController.class));
    }

    @Test
    void isTaggedForTheGeneratedDocument() {
        Tag tag = ListViewController.class.getAnnotation(Tag.class);
        assertNotNull(tag, "missing @Tag on ListViewController");
        assertEquals("List View", tag.name());
        assertFalse(tag.description().isBlank(), "the tag needs a description for the published document");
    }

    @Test
    void declaresExactlyTheFourViewOperations() {
        assertEquals(Set.of("listViews", "createView", "editView", "deleteView"),
                Arrays
                        .stream(ListViewController.class.getDeclaredMethods())
                        .map(Method::getName)
                        .collect(Collectors.toSet()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"listViews", "createView", "editView", "deleteView"})
    void documentsEveryOperation(String name) {
        Operation operation = method(name).getAnnotation(Operation.class);
        assertNotNull(operation, "missing @Operation on " + name);
        assertFalse(operation.summary().isBlank(), "missing summary on " + name);
    }

    @Test
    void listingIsAGetOnTheBasePathFilteredByAnOptionalResource() {
        Method listing = method("listViews");
        assertNotNull(listing.getAnnotation(GetMapping.class), "expected @GetMapping on listViews");
        assertEquals(0, listing.getAnnotation(GetMapping.class).path().length, "listing sits on the base path");
        assertEquals(List.class, listing.getReturnType());

        RequestParam resource = parameterOfType(listing, Resource.class).getAnnotation(RequestParam.class);
        assertNotNull(resource, "listViews takes no @RequestParam");
        assertFalse(resource.required(), "the resource filter must stay optional so a bare GET lists every view");
    }

    @Test
    void creationIsAPostOnTheBasePathTakingTheCreateShape() {
        Method creation = method("createView");
        PostMapping mapping = creation.getAnnotation(PostMapping.class);
        assertNotNull(mapping, "expected @PostMapping on createView");
        assertEquals(0, mapping.path().length, "creation sits on the base path");
        assertEquals(ListViewRequestDto.class, creation.getParameterTypes()[0]);
        assertEquals(ListViewDto.class, creation.getReturnType());
        assertTrue(bodyIsValidated(creation, ListViewRequestDto.class), "the create body must be bean-validated");
    }

    @Test
    void editIsAPutOnTheViewTakingTheUpdateShape() {
        Method edit = method("editView");
        PutMapping mapping = edit.getAnnotation(PutMapping.class);
        assertNotNull(mapping, "expected @PutMapping on editView");
        assertEquals(1, mapping.path().length);
        assertEquals(UUID_PATH, mapping.path()[0]);

        // the update shape omits the resource, so an edit cannot repoint a view at another catalogue
        assertEquals(ListViewUpdateRequestDto.class, edit.getParameterTypes()[1]);
        assertEquals(ListViewDto.class, edit.getReturnType());
        assertTrue(bodyIsValidated(edit, ListViewUpdateRequestDto.class), "the update body must be bean-validated");
        assertNull(parameterOfType(edit, String.class).getAnnotation(Valid.class),
                "@Valid belongs on the body, not on the UUID path variable");
    }

    @ParameterizedTest
    @ValueSource(strings = {"createView", "editView"})
    void documentsTheValidationFailureOnEveryValidatedBody(String name) {
        // given — both write bodies are bean-validated, so 422 is a reachable response and belongs in the document
        ApiResponses responses = method(name).getAnnotation(ApiResponses.class);
        assertNotNull(responses, "missing @ApiResponses on " + name);
        assertTrue(Arrays.stream(responses.value()).anyMatch(response -> "422".equals(response.responseCode())),
                name + " can reject an invalid body but does not document the 422");
    }

    @Test
    void namesEveryHttpBoundParameterExplicitly() {
        // given — an implementation compiled without -parameters loses the inferred names, so they are pinned here
        assertEquals("resource",
                parameterOfType(method("listViews"), Resource.class).getAnnotation(RequestParam.class).name());
        assertEquals("uuid",
                parameterOfType(method("editView"), String.class).getAnnotation(PathVariable.class).value());
        assertEquals("uuid",
                parameterOfType(method("deleteView"), String.class).getAnnotation(PathVariable.class).value());
    }

    @Test
    void deletionIsADeleteOnTheViewReturningNoContent() {
        Method deletion = method("deleteView");
        DeleteMapping mapping = deletion.getAnnotation(DeleteMapping.class);
        assertNotNull(mapping, "expected @DeleteMapping on deleteView");
        assertEquals(1, mapping.path().length);
        assertEquals(UUID_PATH, mapping.path()[0]);
        assertEquals(void.class, deletion.getReturnType());
        assertEquals(HttpStatus.NO_CONTENT, deletion.getAnnotation(ResponseStatus.class).value());
    }

    private static Parameter parameterOfType(Method method, Class<?> type) {
        return Arrays
                .stream(method.getParameters())
                .filter(parameter -> parameter.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new AssertionError(
                        method.getName() + " takes no " + type.getSimpleName() + " parameter"));
    }

    private static boolean bodyIsValidated(Method method, Class<?> bodyType) {
        return parameterOfType(method, bodyType).getAnnotation(Valid.class) != null;
    }

    private Method method(String name) {
        return Arrays
                .stream(ListViewController.class.getDeclaredMethods())
                .filter(m -> m.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AssertionError("ListViewController declares no method named " + name));
    }
}
