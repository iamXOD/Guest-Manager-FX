/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.*;

/**
 *
 * @author HAROLD
 */
public class Controller implements Initializable {

//Properties
    //Data
    private static SimpleStringProperty status;
    private Stay SelectedStay;
    private Guest SelectedGuest;
    private Country SelectedCountry;
    private String SelectedRoom;
    private StayDAO stays;
    private GuestDAO guests;
    private CountryDAO countries;

    //Menu
    //Master
    //View
    @FXML
    private TableView<Stay> stayTable;

    @FXML
    private TableColumn<String, Stay> nameColumn;

    @FXML
    private TableColumn<String, Stay> surnameColumn;

    @FXML
    private TableColumn<String, Stay> dateColumn;

    @FXML
    private TableColumn<String, Stay> roomColumn;

    //Details
    @FXML
    private ComboBox<Guest> guestSelect;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField CITextField;

    @FXML
    private ImageView flagImage;

    @FXML
    private ComboBox<Country> countrySelect;

    @FXML
    private ComboBox roomSelect;

    @FXML
    private TextField roomTextField;

    @FXML
    private DatePicker inDate;

    @FXML
    private DatePicker outDate;

    @FXML
    private Button saveButton;

    //Status
    @FXML
    private Label statusLabel;

//Methods
    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Connector.url = "st.db";
        Connector.createDB();
        stays = new StayDAO();
        guests = new GuestDAO();
        countries = new CountryDAO();
        status = new SimpleStringProperty();
        MainBuilder();
        statusLabel.textProperty().bind(status);
    }

    //Builders
    private void MainBuilder() {
        tableBuilder();
        guestSelectBuilder();
        countrySelectBuilder();
        roomSelectBuilder();
        datePickerBuilder();
    }

    private void tableBuilder() {
        stayTable.setItems(stays.getAll());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("inDate"));
        roomColumn.setCellValueFactory(new PropertyValueFactory<>("room"));
        stayTable.getSelectionModel()
                .selectedItemProperty()
                .addListener(tableItemSelecter());
    }

    private void guestSelectBuilder() {
        guestSelect.setItems(guests.getAll());
        guestSelect.getItems().add(0, Guest.NEW);
        guestSelect.getSelectionModel().selectFirst();
        guestSelect.getSelectionModel()
                .selectedItemProperty()
                .addListener(guestSelecter());
        guestSelect.setDisable(true);
    }

    private void countrySelectBuilder() {
        countrySelect.setItems(stays.getOrdenedCountries());
        countrySelect.getSelectionModel().select(0);
        setFlagImage();
        countrySelect.setDisable(true);
        countrySelect.getSelectionModel().selectedItemProperty().addListener(countrySelecter());
    }

    private void roomSelectBuilder() {
        roomSelect.setItems(stays.getAllRooms());
        roomSelect.getItems().add(0, "New");
        roomSelect.getSelectionModel()
                .selectedItemProperty()
                .addListener(roomSelecter());
        roomSelect.setDisable(true);
    }

    private void datePickerBuilder() {
        setDateData(LocalDate.now(), LocalDate.now(), false);
    }

    //Listeners
    private ChangeListener<Stay> tableItemSelecter() {
        ChangeListener<Stay> aux = new ChangeListener() {
            @Override
            public void changed(
                    ObservableValue observable,
                    Object oldValue,
                    Object newValue) {
                SelectedStay = (Stay) newValue;
                if (SelectedStay != null) {
                    guestSelect.getSelectionModel()
                            .select(SelectedStay.getGuest());
                    countrySelect.getSelectionModel()
                            .select(SelectedStay.getGuest().getCountry());
                    roomSelect.getSelectionModel()
                            .select(SelectedStay.getRoom());
                    guestSelect.setDisable(true);
                    countrySelect.setDisable(true);
                    roomSelect.setDisable(true);
                    saveButton.setText("Save");
                    setDateData(
                            SelectedStay.getLocalInDate(),
                            SelectedStay.getLocalOutDate(), false);
                    nameTextField.setEditable(false);
                    surnameTextField.setEditable(false);
                    CITextField.setEditable(false);
                    roomTextField.setEditable(false);
                }
            }
        };
        return aux;
    }

    private ChangeListener<Guest> guestSelecter() {
        ChangeListener<Guest> aux = new ChangeListener<Guest>() {
            @Override
            public void changed(
                    ObservableValue observable,
                    Guest oldValue,
                    Guest newValue) {
                SelectedGuest = (Guest) newValue;
                if (SelectedGuest != null) {
                    if (SelectedGuest.equals(Guest.NEW)) {
                        nameTextField.setEditable(true);
                        surnameTextField.setEditable(true);
                        CITextField.setEditable(true);
                        countrySelect.setDisable(false);
                        nameTextField.setText("");
                        surnameTextField.setText("");
                        CITextField.setText("");
                        nameTextField.requestFocus();
                        countrySelect.setDisable(false);
                    } else {
                        setGuestData(SelectedGuest);
                    }
                }
            }
        };
        return aux;
    }

    private ChangeListener<Country> countrySelecter() {
        ChangeListener<Country> aux = new ChangeListener<Country>() {
            @Override
            public void changed(
                    ObservableValue observable,
                    Country oldValue,
                    Country newValue) {
                SelectedCountry = (Country) newValue;
                if (SelectedCountry != null) {
                    setFlagImage();
                }
            }
        };
        return aux;
    }

    private ChangeListener<String> roomSelecter() {
        ChangeListener<String> aux = new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue observable,
                    String oldValue,
                    String newValue) {
                SelectedRoom = (String) newValue;
                if (SelectedRoom != null) {
                    if (SelectedRoom.equals("New")) {
                        roomTextField.setEditable(true);
                        roomTextField.setText("");
                        roomTextField.requestFocus();
                    } else {
                        setRoomData(SelectedRoom);
                    }
                }
            }
        };
        return aux;
    }

    //Data
    private void setGuestData(Guest g) {
        nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        CITextField.setEditable(false);
        nameTextField.setText(g.getName());
        surnameTextField.setText(g.getSurName());
        CITextField.setText(g.getCI());
        countrySelect.setValue(g.getCountry());
    }

    private void setRoomData(String room) {
        roomTextField.setEditable(false);
        roomTextField.setText(room);
    }

    private void setFlagImage() {
        String id = countrySelect.getValue().getID().substring(1);
        flagImage.setImage(new Image("/assets/flags/" + id + "_60x42.png"));
    }

    private void setDateData(
            LocalDate indate,
            LocalDate outdate,
            boolean editable) {
        inDate.setEditable(editable);
        outDate.setEditable(editable);
        inDate.setOnMouseClicked(e -> {
            if (!inDate.isEditable()) {
                inDate.hide();
            }
        });
        outDate.setOnMouseClicked(e -> {
            if (!outDate.isEditable()) {
                outDate.hide();
            }
        });
        inDate.setValue(indate);
        outDate.setValue(outdate);
    }

    private Stay getStayData() {
        Guest g = new Guest(nameTextField.getText(),
                surnameTextField.getText(),
                CITextField.getText(),
                countrySelect.getValue());
        Date indate = new Date(inDate.getValue());
        Date outdate = new Date(outDate.getValue());
        return new Stay(g, indate, outdate, roomTextField.getText());
    }

    private void pointEmptyFields() {
        if (nameTextField.getText().equals("")) {
            nameTextField.setStyle("-fx-border: red");
        }
        if (surnameTextField.getText().equals("")) {
            surnameTextField.setStyle("-fx-border: red");
        }
        if (CITextField.getText().equals("")) {
            CITextField.setStyle("-fx-border: red");
        }
        if (roomTextField.getText().equals("")) {
            roomTextField.setStyle("-fx-border: red");
        }
    }

    //Menu
    @FXML
    private void newDatabase(ActionEvent e) {

    }

    @FXML
    private void openDatabase(ActionEvent e) {

    }

    @FXML
    private void saveDatabaseAs(ActionEvent e) {

    }

    @FXML
    private void quitApp(ActionEvent e) {
        System.exit(0);
    }

    //Master
    //Actions
    @FXML
    private void add(ActionEvent e) {
        nameTextField.setEditable(true);
        surnameTextField.setEditable(true);
        CITextField.setEditable(true);
        roomTextField.setEditable(true);
        inDate.setEditable(true);
        outDate.setEditable(true);
        roomSelect.setDisable(false);
        guestSelect.setDisable(false);
        countrySelect.setDisable(false);
        roomSelect.getSelectionModel().select(0);
        guestSelect.getSelectionModel().select(0);
        countrySelect.getSelectionModel().select(countries.get(".cu"));
        setDateData(LocalDate.now(), LocalDate.now(), true);
        saveButton.setText("Add");
        nameTextField.requestFocus();

    }

    @FXML
    private void edit(ActionEvent e) {
        if (SelectedStay != null) {
            setGuestData(SelectedStay.getGuest());
            setRoomData(SelectedStay.getRoom());
            setDateData(SelectedStay.getLocalInDate(),
                    SelectedStay.getLocalOutDate(), true);
            nameTextField.setEditable(true);
            surnameTextField.setEditable(true);
            CITextField.setEditable(true);
            roomTextField.setEditable(true);
            inDate.setEditable(true);
            outDate.setEditable(true);
            countrySelect.setDisable(false);
            roomSelect.setDisable(false);
            guestSelect.setDisable(false);
            saveButton.setText("Edit");
            nameTextField.requestFocus();
        } else {
            setstatus("Select a Item");
        }
    }

    @FXML
    private void delete(ActionEvent e) {
        if (SelectedStay != null) {
            if (stays.delete(SelectedStay.getID())) {
                SelectedStay = null;
                MainBuilder();
                stayTable.requestFocus();
                setstatus("Deleted!");
            }
        } else {
            setstatus("Select a Item");
        }
    }

    //Search
    @FXML
    private void searchByGuest(ActionEvent e) {
        guestSelect.setDisable(false);
        guestSelect.getSelectionModel().selectFirst();
        nameTextField.setEditable(true);
        surnameTextField.setEditable(true);
        CITextField.setEditable(true);
        countrySelect.setDisable(true);
        roomTextField.setEditable(false);
        inDate.setEditable(false);
        outDate.setEditable(false);
        roomSelect.setDisable(true);
        saveButton.setText("Search Guest");
        guestSelect.requestFocus();
    }

    @FXML
    private void searchByCountry(ActionEvent e) {
        guestSelect.setDisable(true);
        nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        CITextField.setEditable(false);
        roomTextField.setEditable(false);
        inDate.setEditable(false);
        outDate.setEditable(false);
        roomSelect.setDisable(true);
        countrySelect.setDisable(false);
        saveButton.setText("Search Country");
        countrySelect.requestFocus();
    }

    @FXML
    private void searchByDate(ActionEvent e) {
        guestSelect.setDisable(true);
        nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        CITextField.setEditable(false);
        roomTextField.setEditable(false);
        inDate.setEditable(true);
        outDate.setEditable(false);
        roomSelect.setDisable(true);
        countrySelect.setDisable(true);
        saveButton.setText("Search Date");
        inDate.requestFocus();
    }

    @FXML
    private void searchByRoom(ActionEvent e) {
        guestSelect.setDisable(true);
        nameTextField.setEditable(false);
        surnameTextField.setEditable(false);
        CITextField.setEditable(false);
        roomTextField.setEditable(true);
        inDate.setEditable(false);
        outDate.setEditable(false);
        roomSelect.setDisable(false);
        countrySelect.setDisable(true);
        saveButton.setText("Search Room");
        roomTextField.requestFocus();
    }

    @FXML
    private void searchAll(ActionEvent e) {
        stayTable.setItems(stays.getAll());
    }

    //View
    //Details
    @FXML
    private void save(ActionEvent e) {
        switch (saveButton.getText()) {
            case "Add":
                if (nameTextField.getText().equals("")
                        || surnameTextField.getText().equals("")
                        || CITextField.getText().equals("")
                        || roomTextField.getText().equals("")) {
                    pointEmptyFields();
                    setstatus("Fill all Fields");
                } else {
                    if (stays.add(getStayData())) {
                        guestSelect.setDisable(true);
                        nameTextField.setEditable(false);
                        surnameTextField.setEditable(false);
                        CITextField.setEditable(false);
                        countrySelect.setDisable(true);
                        roomSelect.setDisable(true);
                        roomTextField.setEditable(false);
                        inDate.setEditable(false);
                        outDate.setEditable(false);
                        saveButton.setText("Save");
                        MainBuilder();
                        stayTable.requestFocus();
                        setstatus("Added!");
                        setDateData(LocalDate.now(), LocalDate.now(), false);

                    }
                }
                break;

            case "Edit":
                if (nameTextField.getText().equals("")
                        || surnameTextField.getText().equals("")
                        || CITextField.getText().equals("")
                        || roomTextField.getText().equals("")) {
                    pointEmptyFields();
                    setstatus("Fill all Fields");
                } else {
                    String ID = SelectedStay.getID();
                    if (stays.update(getStayData(), ID)) {
                        guestSelect.setDisable(true);
                        nameTextField.setEditable(false);
                        surnameTextField.setEditable(false);
                        CITextField.setEditable(false);
                        countrySelect.setDisable(true);
                        roomSelect.setDisable(true);
                        roomTextField.setEditable(false);
                        inDate.setEditable(false);
                        outDate.setEditable(false);
                        saveButton.setText("Save");
                        MainBuilder();
                        stayTable.requestFocus();
                        setstatus("Edited!");
                    }
                }
                break;
            case "Search Guest":
                if (nameTextField.getText().equals("")
                        && surnameTextField.getText().equals("")
                        && CITextField.getText().equals("")) {
                    pointEmptyFields();
                    setstatus("Fill at least one Field");
                } else {
                    stayTable.setItems(
                            stays.getByGuest(
                                    nameTextField.getText(),
                                    surnameTextField.getText(),
                                    CITextField.getText()));
                }
                break;
            case "Search Country":
                stayTable.setItems(
                        stays.getByCountry(
                                countrySelect.getValue().getID()));
                break;
            case "Search Room":
                if (roomTextField.getText().equals("")) {
                    pointEmptyFields();
                    setstatus("Fill all Fields");
                } else {
                    stayTable.setItems(
                            stays.getByRoom(
                                    roomTextField.getText()));
                }
                break;
            case "Search Date":
                stayTable.setItems(
                        stays.getByDate(
                                inDate.getValue().toString()));
                break;
            default:
                setstatus("Select a Operation");
        }
    }

    //Status
    public static void setstatus(String s) {
        status.set(s);
    }
}
