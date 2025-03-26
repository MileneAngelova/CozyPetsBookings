package bg.softuni.cozypetsbookings.repositories;

import bg.softuni.cozypetsbookings.models.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Booking b WHERE b.checkIn < :olderThan")
    void deleteOldBookings(Instant olderThan);

//    BookingDTO findByCheckOut(LocalDate checkout);
    List<Booking> findByUserId(UUID userId);
    List<Booking> findByEmail(String email);
}
