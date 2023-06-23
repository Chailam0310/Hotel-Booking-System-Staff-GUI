package com.okhotelbooking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class RoomController implements Initializable {


    @FXML
    private Button RoomA1;

    @FXML
    private Button RoomA10;

    @FXML
    private Button RoomA2;

    @FXML
    private Button RoomA3;

    @FXML
    private Button RoomA4;

    @FXML
    private Button RoomA5;

    @FXML
    private Button RoomA6;

    @FXML
    private Button RoomA7;

    @FXML
    private Button RoomA8;

    @FXML
    private Button RoomA9;

    @FXML
    private Pane pane;

    @FXML
    private DatePicker datePicker;

    public LocalDate getSelectedDateFromDatePicker() {
        return datePicker.getValue();
    }

    @FXML
    void changeRoomPage1(){
        HomePageController roomPage1 = new HomePageController();
        roomPage1.loadContent("Room.fxml",pane);
    }
    @FXML
    void changeRoomPage2(){
        HomePageController roomPage2 = new HomePageController();
        roomPage2.loadContent("Room2.fxml",pane);
    }

    @FXML
    public void Refresh(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Room.fxml"));
            Parent root = loader.load();

            // Replace the existing content in the pane
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void popForm(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingForm.fxml"));
            Parent root = loader.load();

            Stage windowStage = new Stage();
            windowStage.setScene(new Scene(root));
            windowStage.setTitle("Booking Form");
            Image icon = new Image("icon.png");
            windowStage.getIcons().add(icon);
            windowStage.setResizable(false);


            String roomNumber = ((Button) event.getSource()).getText();

            BookingForm bookingForm = loader.getController();
            bookingForm.setRoomInput(roomNumber);

            LocalDate selectedDate = getSelectedDateFromDatePicker();
            if (bookingForm != null) {
                bookingForm.setDate(selectedDate);
            }

            windowStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void changeColorByDate() {
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate != null) {
            try {
                FileReader fileReader = new FileReader("src/main/resources/Text/booking.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;

                RoomA1.setStyle("-fx-background-color: BLUE;");
                RoomA2.setStyle("-fx-background-color: BLUE;");
                RoomA3.setStyle("-fx-background-color: BLUE;");
                RoomA4.setStyle("-fx-background-color: BLUE;");
                RoomA5.setStyle("-fx-background-color: BLUE;");
                RoomA6.setStyle("-fx-background-color: BLUE;");
                RoomA7.setStyle("-fx-background-color: BLUE;");
                RoomA8.setStyle("-fx-background-color: BLUE;");
                RoomA9.setStyle("-fx-background-color: BLUE;");
                RoomA10.setStyle("-fx-background-color: BLUE;");

                RoomA1.setDisable(false);
                RoomA2.setDisable(false);
                RoomA3.setDisable(false);
                RoomA4.setDisable(false);
                RoomA5.setDisable(false);
                RoomA6.setDisable(false);
                RoomA7.setDisable(false);
                RoomA8.setDisable(false);
                RoomA9.setDisable(false);
                RoomA10.setDisable(false);
                while ((line = bufferedReader.readLine()) != null) {
                    String[] booking = line.split(",");
                    LocalDate startDate = LocalDate.parse(booking[6].trim());
                    LocalDate endDate = LocalDate.parse(booking[7].trim());

                    if (selectedDate.isEqual(startDate)||
                            selectedDate.isAfter(startDate) && selectedDate.isBefore(endDate)) {
                       String roomNumber = booking[5].trim();

                       System.out.println("Room " + roomNumber + " is booked on " + selectedDate);
                       Button selectedroom = getRoomButtonByNumber(roomNumber);


                       if (selectedroom != null) {
                           System.out.println(selectedroom);
                           selectedroom.setStyle("-fx-background-color: #bbbb00;");
                           String style = selectedroom.getStyle();
                           System.out.println(style);
                           String desiredStyle = "-fx-background-color: #bbbb00;";
                           if (style.equals(desiredStyle)) {
                               selectedroom.setDisable(true);
                           }
                       }
                    }
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Button getRoomButtonByNumber(String roomNumber) {
        switch (roomNumber) {
            case "A1":
                return RoomA1;
            case "A2":
                return RoomA2;
            case "A3":
                return RoomA3;
            case "A4":
                return RoomA4;
            case "A5":
                return RoomA5;
            case "A6":
                return RoomA6;
            case "A7":
                return RoomA7;
            case "A8":
                return RoomA8;
            case "A9":
                return RoomA9;
            case "A10":
                return RoomA10;
            default:
                return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        changeColorByDate();
    }
}
