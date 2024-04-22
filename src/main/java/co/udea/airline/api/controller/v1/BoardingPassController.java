package co.udea.airline.api.controller.v1;

import co.udea.airline.api.model.jpa.model.user.BoardingPass;
import co.udea.airline.api.services.boardingpass.service.IBoardingPassService;
import co.udea.airline.api.utils.common.StandardResponse;
import co.udea.airline.api.utils.exception.BusinessException;
import co.udea.airline.api.utils.exception.DataDuplicatedException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller class for managing boarding pass operations.
 */
@RestController
@RequestMapping("/v1/boarding-pass")
public class BoardingPassController {

    private final IBoardingPassService boardingPassService;

    public BoardingPassController(IBoardingPassService boardingPassService) {
        this.boardingPassService = boardingPassService;
    }

    /**
     * Endpoint to create a new boarding pass.
     *
     * @param boardingPass The boarding pass object to be created.
     * @return ResponseEntity containing the created boarding pass if successful (HTTP status 200),
     *         or an appropriate error response otherwise (HTTP status 409 if the boarding pass already exists,
     *         or HTTP status 500 for internal server error).
     */
    @Operation(summary = "Create a boarding pass")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Boarding pass created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BoardingPass.class)) }),
        @ApiResponse(responseCode = "409", description = "Conflict - Boarding pass already exists", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = StandardResponse.class)) }),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = RuntimeException.class)) })
    })
    @PostMapping
    public ResponseEntity<BoardingPass> createBoardingPass(
        @Parameter(description = "Boarding pass object to be created", required = true)
        @RequestBody BoardingPass boardingPass) {
            try {
                return boardingPassService.createBoardingPass(boardingPass);
            } catch (DataDuplicatedException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
            } catch (BusinessException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create boarding pass", e);
            }
    }
}
