package com.okhotelbooking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button LoginButton;
    @FXML
    private TextField Password;
    @FXML
    private Label loginValidation;

    @FXML
    private TextField Username;

    private String filename = "src/main/resources/Text/User.txt";

    @FXML
    void Login(ActionEvent event) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            Main Home = new Main();
            while ((line = reader.readLine()) != null) {
                String[] account = line.split(",");
                if(Username.getText().equals(account[0]) && Password.getText().equals(account[1])){
                    loginValidation.setText("Login Successful");
                    Home.changeScene("HomePage.fxml");
                }
                else if(Username.getText().isEmpty() && Password.getText().isEmpty()){
                    loginValidation.setText("Fields cannot left blank");
                }
                else{
                    loginValidation.setText("Input Username or Password");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
