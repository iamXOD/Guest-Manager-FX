/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import controllers.Connector;
import controllers.Controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Guest;

/**
 *
 * @author HAROLD
 */
public class GuestDAO implements DAOInterface<Guest> {

    @Override
    public Guest get(String ID) {
        Guest g = null;
        CountryDAO c = new CountryDAO();
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from guest where guest_id = ?");
            pst.setString(1, ID);
            rs = pst.executeQuery();
            while (rs.next()) {
                g = new Guest(rs.getString("name"), rs.getString("surname"), rs.getString("ci"), c.get(rs.getString("country_id")));
            }
            pst.close();
            rs.close();
            Connector.close();
            return g;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return Guest.NOT_FOUND;
        }
    }

    public ObservableList<Guest> getByCountry(String country_id) {
        Connection con;
        PreparedStatement pst;
        CountryDAO c = new CountryDAO();
        Guest g;
        ArrayList<Guest> aux = new ArrayList();
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from guest where country_id = ? order by age");
            pst.setString(1, country_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                g = new Guest(rs.getString("name"), rs.getString("surname"), rs.getString("ci"), c.get(rs.getString("country_id")));
                aux.add(g);
            }
            pst.close();
            rs.close();
            Connector.close();
            return FXCollections.observableList(aux);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return FXCollections.observableArrayList(Collections.EMPTY_LIST);
        }
    }

    @Override
    public ObservableList<Guest> getAll() {
        ArrayList<Guest> aux = new ArrayList();
        CountryDAO c = new CountryDAO();
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from guest order by age");
            rs = pst.executeQuery();
            while (rs.next()) {
                Guest g = new Guest(rs.getString("name"), rs.getString("surname"), rs.getString("ci"), c.get(rs.getString("country_id")));
                aux.add(g);
            }
            pst.close();
            rs.close();
            Connector.close();
            return FXCollections.observableList(aux);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return FXCollections.observableList(Collections.EMPTY_LIST);
        }
    }

    @Override
    public boolean add(Guest g) {
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement(
                    "insert or replace into guest (guest_id, name, surname, ci, age, country_id) values (?, ?, ?, ?, ?, ?)");
            pst.setString(1, g.getID());
            pst.setString(2, g.getName());
            pst.setString(3, g.getSurName());
            pst.setString(4, g.getCI());
            pst.setInt(5, g.getAge());
            pst.setString(6, g.getCountry().getID());
            pst.executeUpdate();
            pst.close();
            Connector.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Guest g, String ID) {
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement(
                    "update guest set guest_id = ?, name = ?, surname = ?, ci = ?, age = ?, country_id = ? where guest_id = ?");
            pst.setString(1, g.getID());
            pst.setString(2, g.getName());
            pst.setString(3, g.getSurName());
            pst.setString(4, g.getCI());
            pst.setInt(5, g.getAge());
            pst.setString(6, g.getCountry().getID());
            pst.setString(7, ID);
            pst.executeUpdate();
            pst.close();
            Connector.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String ID) {
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("delete from guest where guest_id = ?");
            pst.setString(1, ID);
            pst.executeUpdate();
            pst.close();
            Connector.close();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return false;
        }
    }
}
