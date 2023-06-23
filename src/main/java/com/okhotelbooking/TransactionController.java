package com.okhotelbooking;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {

    @FXML
    private TableColumn<Transaction, String> CheckInColumn;

    @FXML
    private TableColumn<Transaction, String> CheckOutColumn;

    @FXML
    private TableColumn<Transaction, String> ICColumn;

    @FXML
    private TableColumn<Transaction, String> NameColumn;

    @FXML
    private TableColumn<Transaction, String> PaymentColumn;

    @FXML
    private TableColumn<Transaction, String> RoomColumn;

    @FXML
    private TableColumn<Transaction, String> TotalDateColumn;
    @FXML
    private TableColumn<Transaction, String> PriceColumn;

    @FXML
    private TableView<Transaction> TransactionTable;
    ArrayList<Transaction> transactions = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NameColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("name"));
        ICColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("ic"));
        RoomColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("room"));
        CheckInColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("checkin"));
        CheckOutColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("checkout"));
        TotalDateColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("totaldate"));
        PriceColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("price"));
        PaymentColumn.setCellValueFactory(new PropertyValueFactory<Transaction, String>("payment"));

        try {
            FileReader fileReader = new FileReader("src/main/resources/Text/Transaction.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] transaction = line.split(",");
                Transaction TransactionData = new Transaction(transaction[0], transaction[1],
                        transaction[2], transaction[3], transaction[4], transaction[5]
                        , transaction[6], transaction[7]);
                transactions.add(TransactionData);
            }

            ObservableList<Transaction> transactionTable = TransactionTable.getItems();
            transactionTable.addAll(transactions);
            TransactionTable.setItems(transactionTable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
