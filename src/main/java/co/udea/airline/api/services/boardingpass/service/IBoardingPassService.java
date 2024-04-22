package co.udea.airline.api.services.boardingpass.service;

import co.udea.airline.api.model.jpa.model.user.BoardingPass;
import org.springframework.http.ResponseEntity;

/**
 * Service interface for managing boarding passes.
 */
public interface IBoardingPassService {

    /**
     * Creates a new boarding pass.
     *
     * @param boardingPass The boarding pass to create.
     * @return The created boarding pass.
     */
    public ResponseEntity<BoardingPass> createBoardingPass(BoardingPass boardingPass);

    /**
     * Retrieves an existing boarding pass.
     *
     * @param userId The boarding pass to retrieve.
     * @return The retrieved boarding pass.
     */
    public ResponseEntity<BoardingPass> getBoardingPass(int userId);
}
