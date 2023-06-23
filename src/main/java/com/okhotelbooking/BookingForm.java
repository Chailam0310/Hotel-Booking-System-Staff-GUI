package com.okhotelbooking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class BookingForm implements Initializable{
    @FXML
    private TextField Name;
    @FXML
    private TextField IC;
    @FXML
    private TextField Phone;
    @FXML
    private TextField Email;
    @FXML
    private TextField RoomInput;
    @FXML
    private TextField TotalDate;
    @FXML
    private DatePicker CheckInDate;
    @FXML
    private DatePicker CheckOutDate;
    @FXML
    private Label ICLabel;
    @FXML
    private Label PhoneLabel;
    @FXML
    private Label EmailLabel;
    @FXML
    private Label NameLabel;
    @FXML
    private Label GenderLabel;
    @FXML
    private Label CheckInLabel;
    @FXML
    private Label CheckOutLabel;

    @FXML
    private TextField payment;
    @FXML
    private ChoiceBox<String> GenderchoiceBox;
    private String[] Gender = {"Male", "Female"};

    private LocalDate SelectedDate;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RoomInput.setText("pop");
        GenderchoiceBox.getItems().addAll(Gender);
        CheckOutDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            calculateDateRange();
        });

        if(SelectedDate != null){
            CheckInDate.setValue(SelectedDate);
        }
    }



    public boolean validator() {
        boolean validated = true;
        if (Name.getText() == null || Name.getText().isEmpty()) {
            NameLabel.setText("Invalid Input");
            validated = false;
        }
        if (IC.getText() == null || IC.getText().isEmpty() || IC.getText().length() != 12){
            ICLabel.setText("Invalid Input");
            validated = false;
        }
        if (Phone.getText() == null || Phone.getText().isEmpty() || Phone.getText().length() < 10){
            PhoneLabel.setText("Invalid Input");
            validated = false;
        }

        if (Email.getText() == null || Email.getText().isEmpty() || !Email.getText().matches(".+@gmail\\.com")){
            EmailLabel.setText("Invalid Input");
            validated = false;
        }
        if (GenderchoiceBox.getValue() == null){
            GenderLabel.setText("Invalid Input");
            validated = false;
        }
        if (CheckInDate.getValue() == null){
            CheckInLabel.setText("Invalid Input");
            validated = false;
        }
        if (CheckOutDate.getValue() == null){
            CheckOutLabel.setText("Invalid Input");
            validated = false;
        }
        return validated;
    }

    public void calculateDateRange() {
        LocalDate checkin = CheckInDate.getValue();
        LocalDate checkout = CheckOutDate.getValue();
        if (checkin != null && checkout != null && checkout.isAfter(checkin)) {
            String totaldate = String.valueOf(ChronoUnit.DAYS.between(checkin,checkout));
            TotalDate.setText(totaldate);
            System.out.println(totaldate);
        } else {
            TotalDate.setText("");
        }

    }
    public void setDate(LocalDate date){
        CheckInDate.setValue(date);
    }


    public void setRoomInput(String roomNumber) {
        RoomInput.setText(roomNumber);
    }

    @FXML
    public void RoomLock(ActionEvent event) {
        RoomInput.setDisable(true);
    }


    public void changecolor() {
        try {
            FileReader fileReader = new FileReader("src/main/resources/Text/Room.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder fileContent = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String roomNumber = parts[0].trim();
                String status = parts[1].trim();

                if(status == "Unavailable"){

                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void formValidationAndComfirm() throws IOException {

        // 1. process into a line
        String name = Name.getText();
        String ic = IC.getText();
        String phone = Phone.getText();
        String email = Email.getText();
        String gender = GenderchoiceBox.getValue();
        String room = RoomInput.getText();
        LocalDate checkIn = CheckInDate.getValue();
        LocalDate checkOut = CheckOutDate.getValue();
        String totaldate = String.valueOf(ChronoUnit.DAYS.between(checkIn,checkOut));

        String BookingDetails = name + "," + ic + "," + phone + "," + email + "," + gender
                + "," + room + "," + checkIn + "," + checkOut + "," + totaldate + ",CheckIn";

        // 2. save into file(change Userfile state)
        String path = "src/main/resources/Text/booking.txt";
        if (validator()) {
            String existingContent = Files.readString(Path.of(path));
            String combinedContent = existingContent + System.lineSeparator() + BookingDetails;
            Files.writeString(Path.of(path), combinedContent, StandardOpenOption.WRITE);

            try {
                FileReader fileReader = new FileReader("src/main/resources/Text/Room.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                StringBuilder fileContent = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String roomNumber = parts[0].trim();
                    String status = parts[1].trim();

                    if (roomNumber.equals(room)) {
                        status = "Unavailable";
                    }

                    fileContent.append(roomNumber).append(", ").append(status).append("\n");
                }

                bufferedReader.close();

                FileWriter fileWriter = new FileWriter("src/main/resources/Text/Room.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(fileContent.toString());
                bufferedWriter.close();
                closeForm();

                RoomController roomController = new RoomController();
                Button selectedroom = roomController.getRoomButtonByNumber(room);
                changeButtonColorAndDisable(selectedroom);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void closeForm(){
    Stage stage = (Stage) Name.getScene().getWindow();
    stage.close();
    }

    public void changeButtonColorAndDisable(Button selectedRoom) {
        if (selectedRoom != null) {
            selectedRoom.setStyle("-fx-background-color: #bbbb00;");
            String style = selectedRoom.getStyle();
            String desiredStyle = "-fx-background-color: #bbbb00;";
            if (style.equals(desiredStyle)) {
                selectedRoom.setDisable(true);
            }
        }
    }
}
