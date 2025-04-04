package bg.softuni.cozypetsbookings.services;

import bg.softuni.cozypetsbookings.models.dtos.BookingDTO;
import bg.softuni.cozypetsbookings.models.entities.Booking;
import bg.softuni.cozypetsbookings.repositories.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private BookingService bookingService;
    private static final String TEST_EMAIL = "user@example.com";
    private Booking booking;
    private BookingDTO bookingDTO;
    private UUID userId;


    @BeforeEach
    void setUp() {
        booking = new Booking();
        userId = UUID.randomUUID();

        booking
                .setId(1L)
                .setBreed("newBreed")
                .setUserId(String.valueOf(userId))
                .setEmail(TEST_EMAIL)
                .setCheckIn(LocalDate.now())
                .setCheckOut(LocalDate.now())
                .setAdditionalInformation(" ")
                .setContactNumber("0123456789")
                .setFirstName("Petar")
                .setLastName("Petrov")
                .setNumberOfPets(1)
                .setPetName("Boby")
                .setBreed("Labrador")
                .setPetType("dog");


        bookingDTO = new BookingDTO(
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

    private void assertBookingDTOEqual(BookingDTO expected, BookingDTO actual) {
        assertEquals(expected.getAdditionalInformation(), actual.getAdditionalInformation());
        assertEquals(expected.getBreed(), actual.getBreed());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getContactNumber(), actual.getContactNumber());
        assertEquals(expected.getPetName(), actual.getPetName());
        assertEquals(expected.getPetType(), actual.getPetType());
        assertEquals(expected.getCheckIn(), actual.getCheckIn());
        assertEquals(expected.getCheckOut(), actual.getCheckOut());
        assertEquals(expected.getNumberOfPets(), actual.getNumberOfPets());
    }

    @Test
    void create_Booking_shouldReturnBookingDTO() {
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setId(1L);
            return booking;
        });

        BookingDTO result = bookingService.makeBooking(bookingDTO, String.valueOf(userId));
        assertNotNull(result);

        assertEquals(bookingDTO.getNumberOfPets(), result.getNumberOfPets());
        assertEquals(bookingDTO.getCheckOut(), result.getCheckOut());
        assertEquals(bookingDTO.getCheckIn(), result.getCheckIn());
        assertEquals(bookingDTO.getId(), result.getId());
        assertEquals(bookingDTO.getPetName(), result.getPetName());
        assertEquals(bookingDTO.getPetType(), result.getPetType());
        assertEquals(bookingDTO.getEmail(), result.getEmail());
        assertEquals(bookingDTO.getContactNumber(), result.getContactNumber());
        assertEquals(bookingDTO.getBreed(), result.getBreed());
        assertEquals(bookingDTO.getFirstName(), result.getFirstName());
        assertEquals(bookingDTO.getLastName(), result.getLastName());
        assertEquals(bookingDTO.getAdditionalInformation(), result.getAdditionalInformation());
        assertEquals(bookingDTO.getUserId(), result.getUserId());

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void getAllBookings_shouldReturnListOfBookingDTOs() {
        when(bookingRepository.findAll()).thenReturn(Collections.singletonList(booking));

        List<BookingDTO> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertBookingDTOEqual(bookingDTO, result.get(0));
        verify(bookingRepository, times(1)).findAll();
    }

}
