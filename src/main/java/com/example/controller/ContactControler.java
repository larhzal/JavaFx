package com.example.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ContactControler {

    private File file = new File("contacts.csv");
    private Vector<Contacts> contacts = new Vector<>();

    @FXML
    public void add1(){
        String name = nameText.getText();
        String email = emailText.getText();
        String phone = phoneText.getText();
        String address = addressText.getText();
        addBtn.setText("Save");
        contacts.add(new Contacts(name, phone, email, address));
        clearFileds();

    }

    public void clearFileds() {
        nameText.clear();
        phoneText.clear();
        emailText.clear();
        addressText.clear();
    }

    public void saveToFile(){
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (Contacts contact: contacts) {
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    
    @FXML
    void onAdd(ActionEvent event) {
        String name = nameText.getText();
        String email = emailText.getText();
        String phone = phoneText.getText();
        String address = addressText.getText();
        addBtn.setText("Save");
        contacts.add(new Contacts(name, phone, email, address));
        clearFileds();
    }

    @FXML
    private Button addBtn;

    @FXML
    private Label addressLbl;

    @FXML
    private TextField addressText;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label emailLbl;

    @FXML
    private TextField emailText;

    @FXML
    private GridPane gridPane;

    @FXML
    private HBox hBox;

    @FXML
    private Label nameLbl;

    @FXML
    private TextField nameText;

    @FXML
    private Button nextBtn;

    @FXML
    private Label phoneLbl;

    @FXML
    private TextField phoneText;

    @FXML
    private Button prevBtn;

    @FXML
    private Button searchBtn;


    @FXML
    void onDelete(ActionEvent event) {

    }

    @FXML
    void onNext(ActionEvent event) {

    }

    @FXML
    void onPrevious(ActionEvent event) {

    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    }


