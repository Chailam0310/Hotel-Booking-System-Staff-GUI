package com.okhotelbooking;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomePageController implements Initializable {


    @FXML
    private Pane pane;
    @FXML
    private ImageView image;

    private static final String[] IMAGE_PATHS = {
            "img.png",
            "img_1.png",
            "img_2.png",
    };

    private int currentIndex = 0;


    @FXML
    void LogOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout");
        alert.setContentText("Do you want to logout?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            Main logout = new Main();
            logout.changeScene("Login.fxml");
        }
    }
    @FXML
    void loadHomePage() throws IOException {
        Main Home = new Main();
        Home.changeScene("HomePage.fxml");
    }

    public void loadRoomPage(){loadContent("Room.fxml",pane);}
    public void loadBookingDetailsPage(){loadContent("BookingDetail.fxml",pane);}
    public void loadReceiptPage(){loadContent("ReceiptPage.fxml",pane);}
    public void loadTransacrtionPage(){loadContent("Transaction.fxml",pane);}

    public void loadContent(String fxmlfile, Pane pane){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
            Pane content = loader.load();
            pane.getChildren().setAll(content);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void changephoto(){
        String imagePath = IMAGE_PATHS[currentIndex];
        Image image1 = new Image(imagePath);
        image.setImage(image1);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changephoto();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), event -> {
                    currentIndex = (currentIndex + 1) % IMAGE_PATHS.length;
                    changephoto();
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
