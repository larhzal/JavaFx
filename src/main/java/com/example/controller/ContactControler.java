package com.example.controller;

import java.io.*;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ContactControler {

    private File file = new File("contacts.csv");
    FileWriter fileWriter;
    FileReader fileReader;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    int index = -1;
    private Vector<Contact> contacts = new Vector<>();

    @FXML
    private void initialize() {
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            loadContactsIntoVector();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadContactsIntoVector() {
        String line;
        Contact contact;
        try {
            line = bufferedReader.readLine();
            while (line != null && !line.isEmpty() && !line.isBlank()) {
                contact = parseContact(line);
                contacts.add(contact);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Contact parseContact(String line) {
        Contact contact = new Contact();
        String[] strings = line.split(";");
        contact.setName(strings[0]);
        contact.setPhone(strings[1]);
        contact.setEmail(strings[2]);
        contact.setAddress(strings[3]);
        return contact;
    }

    @FXML
    public void add1() {
        String name = nameText.getText();
        String email = emailText.getText();
        String phone = phoneText.getText();
        String address = addressText.getText();
        addBtn.setText("Save");
        contacts.add(new Contact(name, phone, email, address));
        clearFileds();
    }

    public void clearFileds() {
        nameText.clear();
        phoneText.clear();
        emailText.clear();
        addressText.clear();
    }

    public void saveToFile() {
        try {
            flushFile();
            fileWriter = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (Contact contact : contacts) {
                bufferedWriter.write(formatLine(contact));
            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private void flushFile() {
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String formatLine(Contact contact) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(contact.getName());
        stringBuilder.append(";");
        stringBuilder.append(contact.getPhone());
        stringBuilder.append(";");
        stringBuilder.append(contact.getEmail());
        stringBuilder.append(";");
        stringBuilder.append(contact.getAddress());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }


    @FXML
    void onAdd(ActionEvent event) {
        if (addBtn.getText().equals("Add")) {
            if (fieldsValid() && fieldsNotEmpty()) {
                String name = nameText.getText();
                String email = emailText.getText();
                String phone = phoneText.getText();
                String address = addressText.getText();
                addBtn.setText("Save");
                contacts.add(new Contact(name, phone, email, address));
                clearFileds();

            } else {
                if (!fieldsValid()) showAlert("Error, please check the fields", Alert.AlertType.ERROR);
                if (!fieldsNotEmpty()) showAlert("Please fill all the fields", Alert.AlertType.ERROR);
            }
        } else {
            saveToFile();
            addBtn.setText("Add");
        }
    }

    @FXML
    void onSearch(ActionEvent event) {
        Optional<Contact> foundContact = contacts.stream().filter(contact -> contact.getEmail().equals(emailText.getText())).findFirst();
        if (foundContact.isPresent()) displayContact(foundContact.get());
        else showAlert("Contact not found", Alert.AlertType.ERROR);
    }

    @FXML
    void onDelete(ActionEvent event) {
        Optional<Contact> foundContact = contacts.stream().filter(contact -> contact.getEmail().equals(emailText.getText())).findFirst();
        if (foundContact.isPresent()) {
            contacts.remove(foundContact.get());
            saveToFile();
            showAlert("Contact removed", Alert.AlertType.CONFIRMATION);
        } else {
            showAlert("Contact not found", Alert.AlertType.ERROR);
        }
    }

    private void displayContact(Contact foundContact) {
        emailText.setText(foundContact.getEmail());
        phoneText.setText(foundContact.getPhone());
        nameText.setText(foundContact.getName());
        addressText.setText(foundContact.getAddress());
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean fieldsValid() {
        return emailText.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") && phoneText.getText().matches("[0-9]{10}");
    }

    private boolean fieldsNotEmpty() {
        return !emailText.getText().isEmpty() && !phoneText.getText().isEmpty() && !nameText.getText().isEmpty() && !addressText.getText().isEmpty();
    }

    @FXML
    void onNext(ActionEvent event) {
        if (contacts.size() > (index + 1))
            displayContact(contacts.get(++index));
    }

    @FXML
    void onPrevious(ActionEvent event) {
        if (-1 < (index - 1))
            displayContact(contacts.get(--index));
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
}


