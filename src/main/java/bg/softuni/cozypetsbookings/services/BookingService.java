package bg.softuni.cozypetsbookings.services;

import bg.softuni.cozypetsbookings.models.dtos.BookingDTO;
import bg.softuni.cozypetsbookings.models.entities.Booking;
import bg.softuni.cozypetsbookings.repositories.BookingRepository;
import bg.softuni.cozypetsbookings.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final Period retentionPeriod;
    private final static Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    public BookingService(BookingRepository bookingRepository,
                          @Value("${bookings.retention.period}") Period retentionPeriod) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = new ModelMapper();
        this.retentionPeriod = retentionPeriod;
    }

    public BookingDTO makeBooking(BookingDTO addBookingDTO, String userId) {
        Booking newBooking = this.modelMapper.map(addBookingDTO, Booking.class).setUserId(userId);
        bookingRepository.save(newBooking);
        return this.modelMapper.map(newBooking, BookingDTO.class);
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository
                .findAll()
                .stream()
                .map(BookingService::mapToDTO)
                .toList();

    }

    public List<BookingDTO> getUserBookings(String userId) {
        return bookingRepository
                .findByUserId(userId)
                .stream()
                .map(BookingService::mapToDTO)
                .toList();
    }

    public void cancelBooking(Long bookingId) {
        this.bookingRepository.deleteById(bookingId);
    }

    public BookingDTO getBookingById(Long id) {
        return this.bookingRepository
                .findById(id)
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    public void deleteOldBookings() {
        LocalDate now = LocalDate.now();
        LocalDate deleteBefore = now.minus(retentionPeriod);
        LOGGER.info("Removing all bookings older than " + deleteBefore);
        bookingRepository.deleteOldBookings(deleteBefore);
        LOGGER.info("Old bookings were removed");
    }

    private static BookingDTO mapToDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getFirstName(),
                booking.getLastName(),
                booking.getEmail(),
                booking.getContactNumber(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getPetType(),
                booking.getNumberOfPets(),
                booking.getPetName(),
                booking.getBreed(),
                booking.getAdditionalInformation(),
                booking.getUserId()
        );
    }

}
