package com.okhotelbooking;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReceiptController implements Initializable {

    @FXML
    private TableView<Booking> ReceiptTable;

    @FXML
    private TableColumn<Booking, String> NameColumn;

    @FXML
    private TableColumn<Booking, String> CheckInColumn;
    @FXML
    private TableColumn<Booking, String> CheckOutColumn;
    @FXML
    private Label OnePrice;

    @FXML
    private Label ServiceTax;

    @FXML
    private Label TotalPrice;

    @FXML
    private Label TourismTax;

    @FXML
    private Label checkin;

    @FXML
    private Label checkout;

    @FXML
    private Label email;

    @FXML
    private Label gender;

    @FXML
    private Label ic;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label room;

    @FXML
    private Label subtotal;

    @FXML
    private Label total;

    @FXML
    private Label totaldate;

    ArrayList<Booking> bookings = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                if (bookingData.getStatus().equals("CheckOut")) {
                    bookings.add(bookingData);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        ObservableList<Booking> bookingTable = ReceiptTable.getItems();
        bookingTable.addAll(bookings);
        ReceiptTable.setItems(bookingTable);

        ReceiptTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Booking>() {
            @Override
            public void onChanged(Change<? extends Booking> change) {
                Booking booking = ReceiptTable.getSelectionModel().getSelectedItem();
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
                    OnePrice.setText("350");
                    int totalDay = Integer.parseInt(totaldate.getText());
                    int taxRate = 10;
                    double ServiceTaxRate = 0.10;
                    double tax = totalDay * taxRate;
                    int price = Integer.parseInt(OnePrice.getText());
                    TourismTax.setText(String.valueOf(tax));
                    subtotal.setText(String.valueOf(price * totalDay));
                    total.setText(String.valueOf(price * totalDay));
                    ServiceTax.setText(String.valueOf((price * ServiceTaxRate * totalDay)));
                    double totalPrice = Double.parseDouble(subtotal.getText()) + Double.parseDouble(TourismTax.getText()) + Double.parseDouble(ServiceTax.getText());
                    TotalPrice.setText(String.valueOf(totalPrice));
                }
            }
        });
    }

    @FXML
    public void printReceipt(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Receipt.fxml"));
            Parent root = loader.load();

            Stage windowStage = new Stage();
            windowStage.setScene(new Scene(root));
            Receipt receipt = loader.getController();
            Booking selectedBooking = ReceiptTable.getSelectionModel().getSelectedItem();
            if (selectedBooking != null) {
                receipt.setReceiptData(selectedBooking);
            }
            windowStage.setTitle("Receipt");
            Image icon = new Image("icon.png");
            windowStage.getIcons().add(icon);
            windowStage.setResizable(false);
            windowStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}



