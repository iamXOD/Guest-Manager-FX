/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author HAROLD
 */
public class Country {

    public static Country NOT_FOUND = new Country("Not Found", "Not Found");

    private SimpleStringProperty ID;
    private SimpleStringProperty Name;

    public Country(String ID, String Name) {
        this.ID = new SimpleStringProperty(ID);
        this.Name = new SimpleStringProperty(Name);
    }

    public String getID() {
        return ID.get();
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
        this.Name.set(Name);
    }

    @Override
    public String toString() {
        return this.Name.get();
    }
}
