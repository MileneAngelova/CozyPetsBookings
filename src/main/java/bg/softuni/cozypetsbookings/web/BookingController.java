package bg.softuni.cozypetsbookings.web;

import bg.softuni.cozypetsbookings.models.dtos.BookingDTO;
import bg.softuni.cozypetsbookings.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Bookings",
        description = "The Controller responsible for the bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking details",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookingDTO.class))}
            ),
            @ApiResponse(responseCode = "404", description = "If the booking was not found",
                    content = {@Content(mediaType = "application/json")})}
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookingDTO> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<PagedModel<BookingDTO>> getAllBookings(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(
                    size = 5,
                    sort = "id",
                    direction = Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                bookingService.getAllBookings(pageable));
    }

    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    @PostMapping
    public ResponseEntity<BookingDTO> makeReservation(
            @RequestBody BookingDTO bookingDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        this.bookingService.makeBooking(bookingDTO, userDetails.getUsername());
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/id")
                        .buildAndExpand(bookingDTO)
                        .toUri()
        ).body(bookingDTO);
    }

    @Operation(security = @SecurityRequirement(name = "bearer-token"))
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BookingDTO> deleteById(@PathVariable("id") Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.noContent().build();
    }

}
