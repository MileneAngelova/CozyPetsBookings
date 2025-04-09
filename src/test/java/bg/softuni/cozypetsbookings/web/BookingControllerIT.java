//package bg.softuni.cozypetsbookings.web;
//
//import bg.softuni.cozypetsbookings.models.entities.Booking;
//import bg.softuni.cozypetsbookings.repositories.BookingRepository;
//import com.jayway.jsonpath.JsonPath;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Testcontainers
//@SpringBootTest
//@AutoConfigureMockMvc
//@WithMockUser(
//        username = "admin@mail.com",
//        roles = {"USER", "ADMIN"}
//)
//public class BookingControllerIT {
//    @Container
//    @ServiceConnection
//    static MySQLContainer<?> mySQLContainer =
//            new MySQLContainer<>("mysql");
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @AfterEach
//    public void tearDown() {
//        bookingRepository.deleteAll();
//    }
//
//    @Test
//    public void testCreateOffer() throws Exception {
//        MvcResult result = mockMvc.perform(post("/offers")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                  {
//                    "firstName": "Test first name",
//                    "lastName": "Test last name",
//                    "email": "Test email",
//                    "petType": "Test pet type",
//                    "petName": "Test pet name",
//                    "numberOfPets": 1
//                  }
//                """)
//                ).andExpect(status().isCreated())
//                .andExpect(header().exists("Location"))
//                .andReturn();
//
//        String body = result.getResponse().getContentAsString();
//
//        int id = JsonPath.read(body, "$.id");
//
//        Optional<Booking> createdBookingOpt = bookingRepository.findById((long) id);
//
//        Assertions.assertTrue(createdBookingOpt.isPresent());
//
//        Booking createdBooking = createdBookingOpt.get();
//
//        Assertions.assertEquals("Test first name", createdBooking.getFirstName());
//        Assertions.assertEquals("Test last name", createdBooking.getLastName());
//        Assertions.assertEquals("Test email", createdBooking.getEmail());
//        Assertions.assertEquals("Test pet name", createdBooking.getPetName());
//        Assertions.assertEquals(1, createdBooking.getNumberOfPets());
//    }
//
//    @Test
//    public void testDeleteOffer() throws Exception {
//
//        Booking actualEntity = createTestBooking();
//
//        mockMvc.perform(delete("/bookings/{id}", actualEntity.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isNoContent());
//
//        Assertions.assertTrue(
//                bookingRepository.findById(actualEntity.getId()).isEmpty()
//        );
//    }
//
//    private Booking createTestBooking() {
//        return bookingRepository.save(
//                new Booking()
//                        .setId(1L)
//                        .setUserId("1")
//                        .setFirstName("Test first name")
//                        .setLastName("Test last name")
//                        .setEmail("testEmail")
//                        .setCheckIn(LocalDate.now())
//                        .setCheckOut(LocalDate.now())
//                        .setAdditionalInformation("Test additional")
//                        .setContactNumber("0888123456")
//                        .setBreed("TestBreed")
//                        .setPetType("dog")
//                        .setNumberOfPets(1)
//                        .setPetName("Bobby")
//        );
//    }
//}
