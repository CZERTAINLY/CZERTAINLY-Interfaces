package com.czertainly.api.interfaces.connector.secrets;

import com.czertainly.api.exception.NotFoundException;
import com.czertainly.api.interfaces.AuthProtectedConnectorController;
import com.czertainly.api.model.client.attribute.RequestAttribute;
import com.czertainly.api.model.common.attribute.common.BaseAttribute;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/secretsProvider/vaults")
@Tag(name = "Vault Instance Management", description = "Vault Instance API")
public interface VaultInstanceController extends AuthProtectedConnectorController {



    @PostMapping(consumes = {"application/json"})
    @Operation(
            summary = "Check connection to Vault Instance"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Vault instance connected"
                    )
            })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void checkVaultConnection(@Parameter(description = "Vault attributes") @RequestBody List<RequestAttribute> attributes) throws NotFoundException;

    @Operation(
            summary = "List Vault Attributes"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Vault Attributes retrieved"
                    )
            })
    @GetMapping(path = "/attributes", produces = {"application/json"})
    List<BaseAttribute> listVaultAttributes() throws NotFoundException;

}