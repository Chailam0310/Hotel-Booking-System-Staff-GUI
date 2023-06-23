package com.okhotelbooking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Receipt implements Initializable {

    @FXML
    private Label OnePrice;

    @FXML
    private Label ServiceTax;

    @FXML
    private Label Subtotal;

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
    private AnchorPane pane;

    @FXML
    private Label phone;

    @FXML
    private Label room;

    @FXML
    private Label total;

    @FXML
    private Label totaldate;

    @FXML
    private Label totalprice;

    public void setReceiptData(Booking booking) {
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
        Subtotal.setText(String.valueOf(price * totalDay));
        total.setText(String.valueOf(price * totalDay));
        ServiceTax.setText(String.valueOf((price * ServiceTaxRate * totalDay)));
        double totalPrice = Double.parseDouble(Subtotal.getText()) + Double.parseDouble(TourismTax.getText()) + Double.parseDouble(ServiceTax.getText());
        totalprice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void printPNG(ActionEvent event){
        Scene scene = name.getScene();

        // Specify the desired filename
        String filename = "receipt.png";
        String directory = "C:\\Users\\MSI-I7\\Desktop\\Java\\Java-Assignment\\src\\main\\resources\\com\\okhotelbooking\\Receipt";


        try {
            saveImage(scene, filename, directory);
            System.out.println("Image saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
        }
    }



    @FXML
    public void Close(){
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
    }

    public void saveImage(Scene scene, String filename, String directory) throws IOException{
        WritableImage image = new WritableImage((int) scene.getWidth(),(int) scene.getHeight());
        scene.snapshot(image);

        BufferedImage bufferedImage = new BufferedImage(
                (int) image.getWidth(),
                (int) image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        for (int x = 0; x < bufferedImage.getWidth(); x++) {
            for (int y = 0; y < bufferedImage.getHeight(); y++) {
                bufferedImage.setRGB(x,y,image.getPixelReader().getArgb(x,y));
            }
        }

        File file = new File(directory,filename);

        ImageIO.write(bufferedImage,"png",file);
    }
}
