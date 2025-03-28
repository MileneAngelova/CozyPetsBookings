package bg.softuni.cozypetsbookings.repositories;

import bg.softuni.cozypetsbookings.models.dtos.BookingDTO;
import bg.softuni.cozypetsbookings.models.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Booking b WHERE b.checkIn < :olderThan")
    void deleteOldBookings(Instant olderThan);

    BookingDTO findByUserIdAndCheckOut(String userId, LocalDate checkout);
    List<Booking> findByUserId(String userId);
    List<Booking> findByEmail(String email);
}
