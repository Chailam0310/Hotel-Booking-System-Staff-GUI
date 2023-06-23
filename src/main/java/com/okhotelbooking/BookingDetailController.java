package com.okhotelbooking;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingDetailController implements Initializable {

    @FXML
    private TableView<Booking> BookingTable;

    @FXML
    private TableColumn<Booking, String> NameColumn;

    @FXML
    private TableColumn<Booking, String> CheckInColumn;
    @FXML
    private TableColumn<Booking, String> CheckOutColumn;
    @FXML
    private Button DeleteButton;
    @FXML
    private TextField name;
    @FXML
    private TextField ic;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField gender;
    @FXML
    private TextField room;
    @FXML
    private TextField checkin;
    @FXML
    private TextField checkout;
    @FXML
    private TextField totaldate;

    ArrayList<Booking> bookings = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NameColumn.setCellValueFactory(new PropertyValueFactory<Booking,String>("name"));
        CheckInColumn.setCellValueFactory(new PropertyValueFactory<Booking,String>("checkin"));
        CheckOutColumn.setCellValueFactory(new PropertyValueFactory<Booking,String>("checkout"));

        try {
            FileReader fileReader = new FileReader("src/main/resources/Text/booking.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] booking = line.split(",");
                Booking bookingData = new Booking(booking[0],booking[1],booking[2],booking[3],booking[4],booking[5]
                ,booking[6],booking[7],booking[8],booking[9]);
                if (bookingData.getStatus().equals("CheckIn")) {
                    bookings.add(bookingData);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Booking> bookingTable = BookingTable.getItems();
        bookingTable.addAll(bookings);
        BookingTable.setItems(bookingTable);

        BookingTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Booking>() {
            @Override
            public void onChanged(Change<? extends Booking> change) {
                Booking booking = BookingTable.getSelectionModel().getSelectedItem();
                if(booking != null){
                    name.setText(booking.getName());
                    ic.setText(booking.getIc());
                    phone.setText(booking.getPhone());
                    email.setText(booking.getEmail());
                    gender.setText(booking.getGender());
                    room.setText(booking.getRoom());
                    checkin.setText(booking.getCheckin());
                    checkout.setText(booking.getCheckout());
                    totaldate.setText(booking.getTotaldate());
                }
            }
        });
    }

    @FXML
    void delete(){
        Booking booking = BookingTable.getSelectionModel().getSelectedItem();
        System.out.println(booking.toString());
        if(booking != null) {
            bookings.remove(booking);
            BookingTable.getItems().clear();

            ObservableList<Booking> bookingTable = BookingTable.getItems();
            bookingTable.addAll(bookings);
            BookingTable.setItems(bookingTable);
        }


        ArrayList<String> line = new ArrayList<>();
        for (Booking booking1 : bookings) {
            String bookingline = booking1.getName() + "," + booking1.getIc()
                    + "," + booking1.getPhone() + "," + booking1.getEmail() + "," + booking1.getGender()
                    + "," + booking1.getRoom() + "," + booking1.getCheckin() + "," + booking1.getCheckout()
                    + "," + booking1.getTotaldate() + ",CheckIn";
            line.add(bookingline);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < line.size(); i++) {
            boolean isLastLine = i == line.size() - 1; // Check if it's the last line
            stringBuilder.append(line.get(i));

            if (!isLastLine) {
                stringBuilder.append("\n"); // Add newline for all lines except the last one
            }
        }
        String path = "src/main/resources/Text/booking.txt";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));){
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void modify() {
        Booking booking = BookingTable.getSelectionModel().getSelectedItem();
        if (booking != null) {
            // Update the selected booking with the new values from the text fields
            booking.setName(name.getText());
            booking.setIc(ic.getText());
            booking.setPhone(phone.getText());
            booking.setEmail(email.getText());
            booking.setGender(gender.getText());
            booking.setRoom(room.getText());
            booking.setCheckin(checkin.getText());
            booking.setCheckout(checkout.getText());
            booking.setTotaldate(totaldate.getText());

            // Update the Booking.txt file with the modified booking data
            try {
                String filePath = "src/main/resources/Text/booking.txt";
                File file = new File(filePath);
                Path path = file.toPath();
                ArrayList<String> datas = new ArrayList<>();


                // Read all lines from the file and update the modified booking line
                Files.lines(path).forEach(line -> {
                    String[] bookingData = line.split(",");
                        line = booking.getName() + "," + booking.getIc() + "," + booking.getPhone() + "," + booking.getEmail()
                                + "," + booking.getGender() + "," + booking.getRoom() + "," + booking.getCheckin()
                                + "," + booking.getCheckout() + "," + booking.getTotaldate() + ",CheckIn";
                    datas.add(line);
                });

                try {
                    FileWriter fileWriter = new FileWriter(filePath);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                    // Write all bookings back to the file
                    for (int i = 0; i < bookings.size(); i++) {
                        Booking b = bookings.get(i);
                        String bookingLine = b.getName() + "," + b.getIc() + "," + b.getPhone() + "," + b.getEmail()
                                + "," + b.getGender() + "," + b.getRoom() + "," + b.getCheckin()
                                + "," + b.getCheckout() + "," + b.getTotaldate() + ",CheckIn";
                        bufferedWriter.write(bookingLine);
                        if (i < bookings.size()- 1) {
                            bufferedWriter.newLine();
                        }
                    }

                    bufferedWriter.close();

                    // Clear the table and repopulate it with the updated data
                    BookingTable.getItems().clear();
                    ObservableList<Booking> bookingTable = BookingTable.getItems();
                    bookingTable.addAll(bookings);
                    BookingTable.setItems(bookingTable);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void checkout() {
        Booking selectedBooking = BookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            selectedBooking.setStatus("CheckOut");
            try {
                File inputFile = new File("src/main/resources/Text/booking.txt");
                Path filePath = inputFile.toPath();
                ArrayList<String> lines = new ArrayList<>(Files.readAllLines(filePath));

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    String[] booking = line.split(",");
                    String status = booking[booking.length - 1];

                    if (status.equals("CheckIn") && booking[0].equals(selectedBooking.getName()) &&
                            booking[1].equals(selectedBooking.getIc()) && booking[6].equals(selectedBooking.getCheckin()) &&
                            booking[7].equals(selectedBooking.getCheckout())) {
                        booking[9] = "CheckOut"; // Update the status in the array
                        line = String.join(",", booking); // Convert the array back to a string
                        lines.set(i, line); // Update the line in the ArrayList
                        break;
                    }
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < lines.size(); i++) {
                    boolean isLastLine = i == lines.size() - 1; // Check if it's the last line
                    stringBuilder.append(lines.get(i));

                    if (!isLastLine) {
                        stringBuilder.append("\n"); // Add newline for all lines except the last one
                    }
                }
                String path = "src/main/resources/Text/booking.txt";
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));){
                    bufferedWriter.write(stringBuilder.toString());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                String transactionFilePath = "src/main/resources/Text/Transaction.txt";
                FileWriter fileWriter = new FileWriter(transactionFilePath, true); // true to append to the file
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                String transactionLine = selectedBooking.getName() + "," + selectedBooking.getIc() + "," +
                        selectedBooking.getRoom() + "," + selectedBooking.getCheckin() + "," +
                        selectedBooking.getCheckout() + "," + selectedBooking.getTotaldate() + "," +
                        calculateTotalAmount(selectedBooking.getTotaldate()) + ",Cash";
                bufferedWriter.write(transactionLine);
                bufferedWriter.newLine();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            BookingTable.getItems().clear();
            bookings.clear();

            try {
                FileReader fileReader = new FileReader("src/main/resources/Text/booking.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] booking = line.split(",");
                    Booking bookingData = new Booking(booking[0], booking[1], booking[2], booking[3], booking[4], booking[5],
                            booking[6], booking[7], booking[8], booking[9]);
                    if (bookingData.getStatus().equals("CheckIn")) {
                        bookings.add(bookingData);
                    }
                }
                bufferedReader.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            ObservableList<Booking> bookingTable = BookingTable.getItems();
            bookingTable.addAll(bookings);
            BookingTable.setItems(bookingTable);

        }
    }
    private String calculateTotalAmount(String totalDate) {
        int numberOfDays = Integer.parseInt(totalDate);
        int roomRate = 350;
        int additionalCharges = 10;
        double taxPercentage = 0.1;

        double totalAmount = (roomRate * numberOfDays) + (additionalCharges * numberOfDays) +
                ((roomRate * numberOfDays) * taxPercentage);
        return String.format("RM%.2f", totalAmount);
    }
}
