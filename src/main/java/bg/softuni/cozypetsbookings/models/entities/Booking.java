package bg.softuni.cozypetsbookings.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String contactNumber;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "animal_type", nullable = false)
    private String petType;

    @Column(nullable = false)
    private int numberOfPets;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)
    private String breed;

    @Column(name = "additional_information")
    private String additionalInformation;

    private String userId;

    public Booking() {
    }

    public Long getId() {
        return id;
    }

    public Booking setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Booking setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Booking setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Booking setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public Booking setContactNumber(String phoneNumber) {
        this.contactNumber = phoneNumber;
        return this;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public Booking setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public Booking setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public int getNumberOfPets() {
        return numberOfPets;
    }

    public Booking setNumberOfPets(int numberOfPets) {
        this.numberOfPets = numberOfPets;
        return this;
    }

    public String getPetType() {
        return petType;
    }

    public Booking setPetType(String animalType) {
        this.petType = animalType;
        return this;
    }

    public String getPetName() {
        return petName;
    }

    public Booking setPetName(String petName) {
        this.petName = petName;
        return this;
    }

    public String getBreed() {
        return breed;
    }

    public Booking setBreed(String breed) {
        this.breed = breed;
        return this;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public Booking setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Booking setUserId(String userId) {
        this.userId = userId;
        return this;
    }
}
