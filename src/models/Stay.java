/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author HAROLD
 */
public class Stay {

    public static Stay NOT_FOUND = new Stay("Not Found", "Not Found", "Not Found", "Not Found", "Not Found", 0, 0, 0, 0, 0, 0, "Not Found", "Not Found");

    private SimpleStringProperty ID;
    public SimpleStringProperty room;
    private Guest guest;
    private Date inDate;
    private Date outDate;

    public Stay(Guest g, Date id, Date od, String room) {
        this.guest = g;
        this.inDate = id;
        this.outDate = od;
        this.room = new SimpleStringProperty(room);
        updateID();
    }

    public Stay(Guest g, int y, int m, int d, int oy, int om, int od, String room) {
        this.guest = g;
        this.inDate = new Date(y, m, d);
        this.outDate = new Date(oy, om, od);
        this.room = new SimpleStringProperty(room);
        updateID();
    }

    public Stay(String Name, String SurName, String CI, Country c, int y, int m, int d, int oy, int om, int od, String room) {
        this.guest = new Guest(Name, SurName, CI, c);
        this.inDate = new Date(y, m, d);
        this.outDate = new Date(oy, om, od);
        this.room = new SimpleStringProperty(room);
        updateID();
    }

    public Stay(String Name, String SurName, String CI, String countryID, String countryName, int y, int m, int d, int oy, int om, int od, String room) {
        this.guest = new Guest(Name, SurName, CI, new Country(countryID, countryName));
        this.inDate = new Date(y, m, d);
        this.outDate = new Date(oy, om, od);
        this.room = new SimpleStringProperty(room);
        updateID();
    }

    public Stay(String Name, String SurName, String CI, String countryID, String countryName, int y, int m, int d, int oy, int om, int od, String ID, String room) {
        this.guest = new Guest(Name, SurName, CI, new Country(countryID, countryName));
        this.inDate = new Date(y, m, d);
        this.outDate = new Date(oy, om, od);
        this.ID = new SimpleStringProperty(ID);
        this.room = new SimpleStringProperty(room);
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
        updateID();

    }

    public Date getInDate() {
        return inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public LocalDate getLocalInDate() {
        return LocalDate.of(inDate.getYear(), inDate.getMonth(), inDate.getDay());
    }

    public LocalDate getLocalOutDate() {
        return LocalDate.of(outDate.getYear(), outDate.getMonth(), outDate.getDay());
    }

    public void setInDate(Date date) {
        this.inDate = date;
        updateID();
    }

    public void setOutDate(Date date) {
        this.outDate = date;
        updateID();
    }

    public String getRoom() {
        return room.get();
    }

    public void setRoom(String room) {
        this.room.set(room);
    }

    public String getID() {
        return this.ID.get();
    }

    public StringProperty nameProperty() {
        return new SimpleStringProperty(guest.getName());
    }

    public StringProperty surnameProperty() {
        return new SimpleStringProperty(guest.getSurName());
    }

    public StringProperty inDateProperty() {
        return new SimpleStringProperty(inDate.toString());
    }

    public StringProperty outDateProperty() {
        return new SimpleStringProperty(outDate.toString());
    }

    public StringProperty roomProperty() {
        return room;
    }

    public boolean equals(Stay d) {
        return this.ID.get().equals(d.getID());
    }

    @Override
    public String toString() {
        return this.guest.toString() + " " + this.inDate.toString();
    }

    private void updateID() {
        this.ID = new SimpleStringProperty(inDate.toString() + "-" + guest.getCI());
    }
}
