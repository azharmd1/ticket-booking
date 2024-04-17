package org.example.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static jdk.internal.org.jline.utils.InfoCmp.Capability.user1;

public class UserBookingService {
    private User user;
    private List<User> userList;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String USERS_PATH = "app/src/main/java/org.example/booking/localDb/users.json";

    public UserBookingService(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public List<User> loadUsers() throws IOException {
        File usersFile = new File(USERS_PATH);
        return objectMapper.readValue(usersFile, new TypeReference<List<User>>() {
        });
    }

    public boolean loginUser() {
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public boolean signUp(User user1) {
        try {
            userList.add(user1);
            saveUserListToFile();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking() {
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId) {
        Optional<Ticket> ticketToCancel = user.getTicketsBooked().stream()
                .filter(ticket -> ticket.getTicketId().equals(ticketId))
                .findFirst();
        if (ticketToCancel.isPresent()) {
            user.getTicketsBooked().remove(ticketToCancel.get());
            try {
                saveUserListToFile(); // Update the JSON file after removing the ticket
                return true; // Successfully canceled booking
            } catch (IOException ex) {
                System.err.println("Error while saving user list to file: " + ex.getMessage());
                return false; // Failed to update the JSON file
            }
        } else {
            System.out.println("Ticket with ID " + ticketId + " not found.");
            return false; // Ticket with the provided ID not found
        }
    }

    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        } catch (IOException ex) {
            return null;

        }
    }

    public List<List<Integer>> fetchSeats(Train trainSelectedForBooking) {
        return trainSelectedForBooking.getSeats();
    }

    public Boolean bookTrainSeat(Train trainSelectedForBooking, int row, int col) {
        List<List<Integer>> seats = trainSelectedForBooking.getSeats();
        if (row < 0 || row >= seats.size() || col < 0 || col >= seats.get(row).size()) {
            System.out.println("Invalid seat selection. Please try again.");
            return false;
        }
        return true;
    }
}
