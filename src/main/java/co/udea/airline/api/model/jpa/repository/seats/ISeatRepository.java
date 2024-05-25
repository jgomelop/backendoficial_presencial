package co.udea.airline.api.model.jpa.repository.seats;

import co.udea.airline.api.model.jpa.model.seats.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ISeatRepository  extends JpaRepository<Seat,Long> {
    List<Seat> findAllByFlightId(Long id);
    Boolean existsSeatByFlightId(Long id);
    // statUs = 1 means AVAILABLE. See SeatStatusEnum
    @Query("SELECT s FROM Seat s WHERE s.status = 1")
    List<Seat> getAllAvailableStatus(Long id);

    @Query("SELECT s from Seat s where s.id =:seatId and s.flight.id=:flightId")
    Seat getBySeatIdAndAndFlightId(Long seatId, Long flightId);

}
