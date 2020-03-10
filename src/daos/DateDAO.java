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
import models.Date;

/**
 *
 * @author HAROLD
 */
public class DateDAO implements DAOInterface<Date> {

    @Override
    public Date get(String date_id) {
        Connection con;
        Date d = null;
        PreparedStatement pst;
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from date where date_id = ?");
            pst.setString(1, date_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                d = new Date(rs.getInt("year"),
                        rs.getInt("month"),
                        rs.getInt("day"));
            }
            pst.close();
            rs.close();
            Connector.close();
            return d;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return Date.NOT_FOUND;
        }
    }

    @Override
    public ObservableList<Date> getAll() {
        ArrayList<Date> aux = new ArrayList();
        Connection con;
        PreparedStatement pst;
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from date order by date_id");
            rs = pst.executeQuery();
            while (rs.next()) {
                Date d = new Date(rs.getInt("year"),
                        rs.getInt("month"),
                        rs.getInt("day"));
                aux.add(d);
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
    public boolean add(Date d) {
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement(
                    "insert or replace into date (date_id, year, month, day) values (?, ?, ?, ?)");
            pst.setString(1, d.getID());
            pst.setInt(2, d.getYear());
            pst.setInt(3, d.getMonth());
            pst.setInt(4, d.getDay());
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
    public boolean update(Date d, String ID) {
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("update date set date_id = ?, year = ?, month = ?, day = ? where date_id = ?");
            pst.setString(1, d.getID());
            pst.setInt(2, d.getYear());
            pst.setInt(3, d.getMonth());
            pst.setInt(4, d.getDay());
            pst.setString(5, ID);
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
            pst = con.prepareStatement("delete from date where date_id = ?");
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
