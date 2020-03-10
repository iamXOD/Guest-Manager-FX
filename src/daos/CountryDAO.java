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
import models.Country;

/**
 *
 * @author HAROLD
 */
public class CountryDAO implements DAOInterface<Country> {

    @Override
    public Country get(String ID) {
        Country c = null;
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from country where country_id = ?");
            pst.setString(1, ID);
            rs = pst.executeQuery();
            while (rs.next()) {
                c = new Country(rs.getString("country_id"), rs.getString("name"));
            }
            pst.close();
            rs.close();
            Connector.close();
            return c;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return Country.NOT_FOUND;
        }
    }

    @Override
    public ObservableList<Country> getAll() {
        ArrayList<Country> aux = new ArrayList();
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from country order by name");
            rs = pst.executeQuery();
            while (rs.next()) {
                Country c = new Country(rs.getString("country_id"), rs.getString("name"));
                aux.add(c);
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
    public boolean add(Country c) {
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement(
                    "insert or replace into country(country_id, name) values (?, ?)");
            pst.setString(1, c.getID());
            pst.setString(2, c.getName());
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
    public boolean update(Country c, String ID) {
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement(
                    "update country set country_id = ?, name = ? where country_id = ?");
            pst.setString(1, c.getID());
            pst.setString(2, c.getName());
            pst.setString(3, ID);
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
            pst = con.prepareStatement("delete from country where country_id = ?");
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
