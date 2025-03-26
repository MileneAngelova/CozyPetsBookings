package bg.softuni.cozypetsbookings.services;

import bg.softuni.cozypetsbookings.models.dtos.BookingDTO;
import bg.softuni.cozypetsbookings.models.entities.Booking;
import bg.softuni.cozypetsbookings.repositories.BookingRepository;
import bg.softuni.cozypetsbookings.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
//    private final Period retentionPeriod;
    private final static Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    public BookingService(BookingRepository bookingRepository
//                          @Value("${bookings.retention.period}")
//                          Period retentionPeriod
    ) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = new ModelMapper();
//        this.retentionPeriod = retentionPeriod;
    }

    public void makeBooking(BookingDTO addBookingDTO) {
        Booking newBooking = this.modelMapper.map(addBookingDTO, Booking.class);
        bookingRepository.save(newBooking);
    }

    public PagedModel<BookingDTO> getAllBookings(Pageable pageable) {
        return new PagedModel<>(bookingRepository
                .findAll(pageable)
                .map(BookingService::mapToDTO));

    }

    public List<BookingDTO> getUserBookings(String email) {
        return bookingRepository
                .findByEmail(email)
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
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

//    public void deleteOldBookings() {
//        Instant now = Instant.now();
//        Instant deleteBefore = now.minus(retentionPeriod);
//        LOGGER.info("Removing all offers older than " + deleteBefore);
//        bookingRepository.deleteOldBookings(deleteBefore);
//        LOGGER.info("Old orders were removed");
//    }

    private static BookingDTO mapToDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getUserId(),
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
                booking.getAdditionalInformation()
        );
    }

}
