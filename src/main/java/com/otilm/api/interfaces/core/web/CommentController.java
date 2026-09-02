package com.otilm.api.interfaces.core.web;

import com.otilm.api.exception.NotFoundException;
import com.otilm.api.interfaces.AuthProtectedController;
import com.otilm.api.model.client.comment.CommentCreateRequestDto;
import com.otilm.api.model.client.comment.CommentDto;
import com.otilm.api.model.client.comment.CommentResponseDto;
import com.otilm.api.model.common.ErrorMessageDto;
import com.otilm.api.model.core.auth.Resource;
import com.otilm.api.model.core.scheduler.PaginationRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/v1/comments")
@Tag(name = "Comments", description = "Comments API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "404", description = "Not Found",
                content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))})
public interface CommentController extends AuthProtectedController {

    @Operation(summary = "List comment threads for an object",
            description = "Pages over thread roots; each root carries its reply count. Replies are paged separately. "
                    + "Pass anchorUuid to open the page holding a particular comment's thread instead of the first "
                    + "page; the anchor may be a root or a reply.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment threads retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @GetMapping(path = "/{resource}/{objectUuid}", produces = {"application/json"})
    CommentResponseDto listComments(
            @Parameter(description = "Resource", required = true) @PathVariable Resource resource,
            @Parameter(description = "Object UUID", required = true) @PathVariable UUID objectUuid,
            @Parameter(description = "Return the page holding this comment's thread; ignored when the comment no "
                    + "longer exists") @RequestParam(required = false) UUID anchorUuid,
            PaginationRequestDto pagination) throws NotFoundException;

    @Operation(summary = "List replies of a comment thread",
            description = "Pages over the thread root's replies in creation order. Pass anchorUuid to open the page "
                    + "holding a particular reply instead of the first page.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thread replies retrieved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @GetMapping(path = "/{uuid}/replies", produces = {"application/json"})
    CommentResponseDto listReplies(@Parameter(description = "Comment UUID") @PathVariable UUID uuid, @Parameter(
            description = "Return the page holding this reply; ignored when the reply no longer exists") @RequestParam(
                    required = false) UUID anchorUuid,
            PaginationRequestDto pagination) throws NotFoundException;

    @Operation(summary = "Post a comment or a reply on an object",
            description = "A request without parentUuid starts a new thread; with parentUuid it replies to that "
                    + "thread root. Threads are one level deep.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment created"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PostMapping(path = "/{resource}/{objectUuid}", consumes = {"application/json"}, produces = {"application/json"})
    CommentDto createComment(@Parameter(description = "Resource", required = true) @PathVariable Resource resource,
            @Parameter(description = "Object UUID", required = true) @PathVariable UUID objectUuid,
            @Valid @RequestBody CommentCreateRequestDto request) throws NotFoundException;

    @Operation(summary = "Resolve a comment thread", description = "Thread roots only.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment thread resolved"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/{uuid}/resolve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void resolveComment(@Parameter(description = "Comment UUID") @PathVariable UUID uuid) throws NotFoundException;

    @Operation(summary = "Reopen a resolved comment thread", description = "Thread roots only.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment thread reopened"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = {@ExampleObject(value = "[\"Error Message 1\",\"Error Message 2\"]")}))})
    @PatchMapping(path = "/{uuid}/unresolve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unresolveComment(@Parameter(description = "Comment UUID") @PathVariable UUID uuid) throws NotFoundException;

    @Operation(summary = "Delete a comment",
            description = "Deleting a thread root that has replies also deletes the replies.")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Comment deleted")})
    @DeleteMapping(path = "/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(@Parameter(description = "Comment UUID") @PathVariable UUID uuid) throws NotFoundException;
}
