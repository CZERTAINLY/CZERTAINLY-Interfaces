package com.czertainly.api.core.interfaces.web;

import java.util.List;

import com.czertainly.api.exception.AlreadyExistException;
import com.czertainly.api.exception.ConnectorException;
import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.exception.ValidationException;
import com.czertainly.api.model.connector.ForceDeleteMessageDto;
import com.czertainly.api.model.raprofile.AddRaProfileRequestDto;
import com.czertainly.api.model.raprofile.EditRaProfileRequestDto;
import com.czertainly.api.model.raprofile.RaProfileDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.czertainly.api.core.modal.ClientDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/raprofiles")
@Tag(name = "Ra Profile Management API", description = "Ra Management Management API")

public interface RAProfileManagementController {
	@Operation(summary = "List of availabe RA Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "RA Profile list retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
	public List<RaProfileDto> listRaProfiles();

	@Operation(summary = "List of availabe RA Profiles by Status")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "RA Profile list retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.GET, params = { "isEnabled" }, produces = {"application/json"})
	public List<RaProfileDto> listRaProfiles(@RequestParam Boolean isEnabled);
	
	@Operation(summary = "Add a new RA Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "RA Profile Added"),
			@ApiResponse(responseCode = "400", description ="Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	public ResponseEntity<?> addRaProfile(@RequestBody AddRaProfileRequestDto request)
			throws AlreadyExistException, ValidationException, NotFoundException, ConnectorException;
	
	@Operation(summary = "Detail of an RA Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "RA Profile details retrieved"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.GET, produces = {"application/json"})
	public RaProfileDto getRaProfile(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Edit an RA Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile edited"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public RaProfileDto editRaProfile(@PathVariable String uuid, @RequestBody EditRaProfileRequestDto request)
            throws NotFoundException, ConnectorException;
	
	@Operation(summary = "Delete an RA Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile removed"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeRaProfile(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Disable an RA Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile Disabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/disable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void disableRaProfile(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "Enable an RA Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profile enabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/enable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void enableRaProfile(@PathVariable String uuid) throws NotFoundException;
	
	@Operation(summary = "List Clients of an RA Profile")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "List of clients of an RA Profile"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/{uuid}/listclients", method = RequestMethod.GET, produces = {"application/json"})
	public List<ClientDto> listClients(@PathVariable String uuid) throws NotFoundException;

	@Operation(summary = "Delete multiple Ra Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profiles deleted"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "422", description = "Unprocessible Entity", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkRemoveRaProfile(@RequestBody List<String> uuids) throws NotFoundException, ValidationException;

	@Operation(summary = "Disable RA Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profiles Disabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/disable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkDisableRaProfile(@RequestBody List<String> uuids) throws NotFoundException;

	@Operation(summary = "Enable RA Profiles")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "RA Profiles enabled"),
			@ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Not found", content = @Content) })
	@RequestMapping(path = "/enable", method = RequestMethod.PUT, consumes = {"application/json"})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bulkEnableRaProfile(@RequestBody List<String> uuids) throws NotFoundException;

}
