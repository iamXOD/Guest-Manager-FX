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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Country;
import models.Stay;

/**
 *
 * @author HAROLD
 */
public class StayDAO implements DAOInterface<Stay> {

    @Override
    public Stay get(String ID) {
        Connection con;
        GuestDAO g = new GuestDAO();
        DateDAO d = new DateDAO();
        Stay s = null;
        ResultSet rs;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from stay where stay_id = ?");
            pst.setString(1, ID);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = new Stay(g.get(rs.getString("guest_id")), d.get(rs.getString("indate")), d.get(rs.getString("outdate")), rs.getString("room_id"));
            }
            pst.close();
            rs.close();
            Connector.close();
            return s;
        } catch (SQLException ex) {
            Controller.setstatus(ex.getMessage());
            return Stay.NOT_FOUND;
        }
    }

    public ObservableList<Stay> getByGuest(String name, String surname, String CI) {
        Connection con;
        PreparedStatement pst;
        String statement = "select stay.* from stay, guest where stay.guest_id = guest.guest_id";
        GuestDAO g = new GuestDAO();
        DateDAO d = new DateDAO();
        Stay s;
        if (!name.isEmpty() || !surname.isEmpty() || !CI.isEmpty()) {
            statement += " and (";
            if (!name.isEmpty()) {
                statement += "guest.name like \"%" + name + "%\"";
                if (!surname.isEmpty() || !CI.isEmpty()) {
                    statement += " or ";
                }
            }
            if (!surname.isEmpty()) {
                statement += "guest.surname like \"%" + surname + "%\"";
                if (!CI.isEmpty()) {
                    statement += " or ";
                }
            }
            if (!CI.isEmpty()) {
                statement += "guest.ci like \"%" + CI + "%\"";
            }
            statement += ")";
        }
        statement += " order by stay.stay_id";
        ArrayList<Stay> aux = new ArrayList();
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement(statement);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = new Stay(g.get(rs.getString("guest_id")), d.get(rs.getString("indate")), d.get(rs.getString("outdate")), rs.getString("room_id"));
                aux.add(s);
            }
            pst.close();
            rs.close();
            Connector.close();
            return FXCollections.observableList(aux);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return FXCollections.observableArrayList(Collections.EMPTY_LIST);
        }
    }

    public ObservableList<Stay> getByCountry(String country_id) {
        Connection con;
        PreparedStatement pst;
        GuestDAO g = new GuestDAO();
        DateDAO d = new DateDAO();
        Stay s;
        ArrayList<Stay> aux = new ArrayList();
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select stay.*"
                    + "from stay, guest, country "
                    + "where stay.guest_id = guest.guest_id "
                    + "and guest.country_id = country.country_id "
                    + "and country.country_id = ?;");
            pst.setString(1, country_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = new Stay(g.get(rs.getString("guest_id")), d.get(rs.getString("indate")), d.get(rs.getString("outdate")), rs.getString("room_id"));
                s.toString();
                aux.add(s);
            }
            pst.close();
            rs.close();
            Connector.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
        }
        return FXCollections.observableList(aux);
    }

    public ObservableList<Stay> getByDate(String date) {
        Connection con;
        PreparedStatement pst;
        GuestDAO g = new GuestDAO();
        DateDAO d = new DateDAO();
        Stay s;
        ArrayList<Stay> aux = new ArrayList();
        ResultSet rs;
        date = date.replaceAll("-0", "-");
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from stay where indate = ? or outdate = ? order by stay_id");
            pst.setString(1, date);
            pst.setString(2, date);
            rs = pst.executeQuery();
            while (rs.next()) {
                s = new Stay(g.get(rs.getString("guest_id")), d.get(rs.getString("indate")), d.get(rs.getString("outdate")), rs.getString("room_id"));
                aux.add(s);
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

    public ObservableList<Stay> getByRoom(String room_id) {
        Connection con;
        PreparedStatement pst;
        GuestDAO g = new GuestDAO();
        DateDAO d = new DateDAO();
        Stay s;
        ArrayList<Stay> aux = new ArrayList();
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from stay where room_id like \"%" + room_id + "%\" order by stay_id");
            rs = pst.executeQuery();
            while (rs.next()) {
                s = new Stay(g.get(rs.getString("guest_id")), d.get(rs.getString("indate")), d.get(rs.getString("outdate")), rs.getString("room_id"));
                aux.add(s);
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

    public ObservableList<String> getAllRooms() {
        Connection con;
        PreparedStatement pst;
        ArrayList<String> aux = new ArrayList<>();
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select room_id from stay group by room_id order by room_id");
            rs = pst.executeQuery();
            while (rs.next()) {
                aux.add(rs.getString("room_id"));
            }
            pst.close();
            rs.close();
            Connector.close();
            return FXCollections.observableArrayList(aux);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return FXCollections.observableArrayList(Collections.EMPTY_LIST);
        }
    }

    public ObservableList<Country> getOrdenedCountries() {
        CountryDAO countries = new CountryDAO();
        ObservableList<Country> all = countries.getAll();
        ObservableList<Country> mostused = getMostUseCountries();
        boolean match = false;
        for (int i = 0; i < all.size(); i++) {
            for (int j = 0; j < mostused.size(); j++) {
                if (mostused.get(j).getID().equals(all.get(i).getID())) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                mostused.add(all.get(i));
            }
            match = false;
        }
        return mostused;
    }

    private ObservableList<Country> getMostUseCountries() {
        Connection con;
        PreparedStatement pst;
        CountryDAO c = new CountryDAO();
        ArrayList<Country> aux = new ArrayList<>();
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select country_id, count(*) from guest group by country_id order by count(*) DESC;");
            rs = pst.executeQuery();
            while (rs.next()) {
                aux.add(c.get(rs.getString("country_id")));
            }
            pst.close();
            rs.close();
            Connector.close();
            return FXCollections.observableArrayList(aux);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Controller.setstatus(ex.getMessage());
            return FXCollections.observableArrayList(Collections.EMPTY_LIST);
        }
    }

    @Override
    public ObservableList<Stay> getAll() {
        Connection con;
        PreparedStatement pst;
        GuestDAO g = new GuestDAO();
        DateDAO d = new DateDAO();
        Stay s;
        ArrayList<Stay> aux = new ArrayList();
        ResultSet rs;
        try {
            con = Connector.connect();
            pst = con.prepareStatement("select * from stay order by stay_id");
            rs = pst.executeQuery();
            while (rs.next()) {
                s = new Stay(g.get(rs.getString("guest_id")), d.get(rs.getString("indate")), d.get(rs.getString("outdate")), rs.getString("room_id"));
                aux.add(s);
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
    public boolean add(Stay s) {
        DateDAO d = new DateDAO();
        GuestDAO g = new GuestDAO();
        d.add(s.getInDate());
        d.add(s.getOutDate());
        g.add(s.getGuest());
        Connection con;
        PreparedStatement pst;
        try {
            con = Connector.connect();
            pst = con.prepareStatement(
                    "insert or replace into stay (stay_id, guest_id, indate, outdate, room_id) values (?, ?, ?, ?, ?)");
            pst.setString(1, s.getID());
            pst.setString(2, s.getGuest().getID());
            pst.setString(3, s.getInDate().getID());
            pst.setString(4, s.getOutDate().getID());
            pst.setString(5, s.getRoom());
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
    public boolean update(Stay s, String ID) {
        Connection con;
        PreparedStatement pst;
        DateDAO d = new DateDAO();
        GuestDAO g = new GuestDAO();
        d.update(s.getInDate(), get(ID).getInDate().getID());
        d.update(s.getOutDate(), get(ID).getOutDate().getID());
        g.update(s.getGuest(), get(ID).getGuest().getID());
        try {
            con = Connector.connect();
            pst = con.prepareStatement(
                    "update stay set stay_id = ?, guest_id = ?, indate = ?, outdate = ?, room_id = ? where stay_id = ?");
            pst.setString(1, s.getID());
            pst.setString(2, s.getGuest().getID());
            pst.setString(3, s.getInDate().getID());
            pst.setString(4, s.getOutDate().getID());
            pst.setString(5, s.getRoom());
            pst.setString(6, ID);
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
            pst = con.prepareStatement("delete from stay where stay_id = ?");
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
