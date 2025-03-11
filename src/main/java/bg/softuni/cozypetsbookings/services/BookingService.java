package bg.softuni.cozypetsbookings.services;

import bg.softuni.cozypetsbookings.models.dtos.BookingDTO;
import bg.softuni.cozypetsbookings.models.entities.Booking;
import bg.softuni.cozypetsbookings.repositories.BookingRepository;
import bg.softuni.cozypetsbookings.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    //    private final Period retentionPeriod;
    private final static Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    public BookingService(BookingRepository bookingRepository
//                          @Value("${bookings.}") Period retentionPeriod
    ) {
        this.bookingRepository = bookingRepository;
        this.modelMapper = new ModelMapper();
//        this.retentionPeriod = retentionPeriod;
    }

    public void makeBooking(BookingDTO addBookingDTO) {
        LOGGER.info("Going to create new booking...");
            Booking newBooking = this.modelMapper.map(addBookingDTO, Booking.class);
            this.bookingRepository.save(newBooking);
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .collect(Collectors.toList());
    }

//@PreAuthorize("@bookingService.isOwner(#userDetails, bookingId")
    public void cancelBooking(Long bookingId) {
        this.bookingRepository.deleteById(bookingId);
    }

    //    public void CleanOldBookings() {
//        Instant now = Instant.now();
//        Instant before = now.minus(retentionPeriod);
//        LOGGER.info("Removing all bookings older than " + before);
//        bookingRepository.deleteOldBookings(before);
//        LOGGER.info("Old bookings were removed");
//    }
    public BookingDTO getBookingById(Long id) {
        return this.bookingRepository
                .findById(id)
                .map(booking -> modelMapper.map(booking, BookingDTO.class))
                .orElseThrow(ObjectNotFoundException::new);
    }

    private static Booking mapToBooking(BookingDTO addBookingDTO) {
        return new Booking()
                .setId(addBookingDTO.getId())
                .setBreed(addBookingDTO.getBreed())
                .setEmail(addBookingDTO.getEmail())
                .setAdditionalInformation(addBookingDTO.getAdditionalInformation())
                .setCheckIn(addBookingDTO.getCheckIn())
                .setCheckOut(addBookingDTO.getCheckOut())
                .setContactNumber(addBookingDTO.getContactNumber())
                .setFirstName(addBookingDTO.getFirstName())
                .setLastName(addBookingDTO.getLastName())
                .setNumberOfPets(addBookingDTO.getNumberOfPets())
                .setPetName(addBookingDTO.getPetName())
                .setPetType(addBookingDTO.getPetType());
    }
}
