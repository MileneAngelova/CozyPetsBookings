package bg.softuni.cozypetsbookings.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RetentionScheduler {
    private final Logger LOGGER = LoggerFactory.getLogger(RetentionScheduler.class);
    private final BookingService bookingService;

    public RetentionScheduler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Scheduled(cron = "1 0 * * * *")
    public void deleteOldRecords() {
        LOGGER.info("Start deleting old bookings...");
        bookingService.deleteOldBookings();
        LOGGER.info("Old bookings deleted.");
    }

}
