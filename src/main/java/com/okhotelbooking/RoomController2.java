package com.okhotelbooking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class RoomController2 implements Initializable {

    @FXML
    private Button RoomB1;

    @FXML
    private Button RoomB10;

    @FXML
    private Button RoomB2;

    @FXML
    private Button RoomB3;

    @FXML
    private Button RoomB4;

    @FXML
    private Button RoomB5;

    @FXML
    private Button RoomB6;

    @FXML
    private Button RoomB7;

    @FXML
    private Button RoomB8;

    @FXML
    private Button RoomB9;
    @FXML
    private Button nextbutton;

    @FXML
    private Pane pane;

    @FXML
    private Button previousButton;
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

                RoomB1.setStyle("-fx-background-color: BLUE;");
                RoomB2.setStyle("-fx-background-color: BLUE;");
                RoomB3.setStyle("-fx-background-color: BLUE;");
                RoomB4.setStyle("-fx-background-color: BLUE;");
                RoomB5.setStyle("-fx-background-color: BLUE;");
                RoomB6.setStyle("-fx-background-color: BLUE;");
                RoomB7.setStyle("-fx-background-color: BLUE;");
                RoomB8.setStyle("-fx-background-color: BLUE;");
                RoomB9.setStyle("-fx-background-color: BLUE;");
                RoomB10.setStyle("-fx-background-color: BLUE;");

                RoomB1.setDisable(false);
                RoomB2.setDisable(false);
                RoomB3.setDisable(false);
                RoomB4.setDisable(false);
                RoomB5.setDisable(false);
                RoomB6.setDisable(false);
                RoomB7.setDisable(false);
                RoomB8.setDisable(false);
                RoomB9.setDisable(false);
                RoomB10.setDisable(false);

                while ((line = bufferedReader.readLine()) != null) {
                    String[] booking = line.split(",");
                    LocalDate startDate = LocalDate.parse(booking[6].trim());
                    LocalDate endDate = LocalDate.parse(booking[7].trim());
                    String status = booking[9].trim();

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
            case "B1":
                return RoomB1;
            case "B2":
                return RoomB2;
            case "B3":
                return RoomB3;
            case "B4":
                return RoomB4;
            case "B5":
                return RoomB5;
            case "B6":
                return RoomB6;
            case "B7":
                return RoomB7;
            case "B8":
                return RoomB8;
            case "B9":
                return RoomB9;
            case "B10":
                return RoomB10;
            default:
                return null;
        }
    }

    @FXML
    public void Refresh(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Room2.fxml"));
            Parent root = loader.load();

            // Replace the existing content in the pane
            pane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        changeColorByDate();
    }
}
