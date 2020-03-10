/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author HAROLD
 */
public class Guest {

    public static Guest NOT_FOUND = new Guest("0", "Not Found", "Not Found", "0", 0, "Not Found", "Not Found");
    public static Guest NEW = new Guest("0", "New", "Guest", "0", 0, "Not Found", "Not Found");

    private SimpleStringProperty ID;
    private SimpleStringProperty Name;
    private SimpleStringProperty SurName;
    private SimpleStringProperty CI;
    private SimpleIntegerProperty Age;
    private Country country;

    public Guest(String Name, String Surname, String CI, Country c) {
        this.Name = new SimpleStringProperty(Name);
        this.SurName = new SimpleStringProperty(Surname);
        this.CI = new SimpleStringProperty(CI);
        this.ID = new SimpleStringProperty(CI);
        this.country = c;
        setAge();
    }

    public Guest(String ID, String Name, String Surname, String CI, String countryID, String countryName) {
        this.Name = new SimpleStringProperty(Name);
        this.SurName = new SimpleStringProperty(Surname);
        this.CI = new SimpleStringProperty(CI);
        this.ID = new SimpleStringProperty(ID);
        this.country = new Country(countryID, countryName);
        setAge();
    }

    public Guest(String ID, String Name, String SurName, String CI, int Age, String countryID, String countryName) {
        this.Name = new SimpleStringProperty(Name);
        this.SurName = new SimpleStringProperty(SurName);
        this.CI = new SimpleStringProperty(CI);
        this.ID = new SimpleStringProperty(ID);
        this.Age = new SimpleIntegerProperty(Age);
        this.country = new Country(countryID, countryName);
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
        this.Name.set(Name);
    }

    public String getSurName() {
        return SurName.get();
    }

    public void setSurName(String SurName) {
        this.SurName.set(SurName);
    }

    public String getCI() {
        return CI.get();
    }

    public void setCI(String CI) {
        this.CI.set(CI);
        this.ID.set(CI);
        setAge();
    }

    public int getAge() {
        return Age.get();
    }

    public void setAge(int Age) {
        this.Age.set(Age);
    }

    public Country getCountry() {
        return this.country;
    }

    public void setCountry(Country c) {
        this.country = c;
    }

    public String getID() {
        return ID.get();
    }

    public static boolean validCI(String CI) {
        return CI.length() == 11 && CI.substring(0, 2).matches("\\d{2}");
    }

    private void setAge() {
        this.Age = new SimpleIntegerProperty();
        if (validCI(this.CI.get())) {
            int thisyear = java.time.LocalDate.now().getYear();
            int yearofborn = Integer.parseInt(this.CI.get().substring(0, 2));
            if (thisyear % 100 <= yearofborn) {
                yearofborn = (((thisyear / 100) - 1) * 100) + yearofborn;
                this.Age.set(thisyear - yearofborn);
            } else {
                this.Age.set((thisyear - yearofborn) % 100);
            }
        } else {
            this.Age.set(0);
        }
    }

    @Override
    public String toString() {
        return this.Name.get() + " " + this.SurName.get();
    }
}
