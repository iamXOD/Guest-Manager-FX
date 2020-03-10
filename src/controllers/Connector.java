/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HAROLD
 */
public class Connector {

    private static Connection connect;

    public static String url = "storage.db";

    public static Connection connect() {
        connect = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:" + url);
            if (connect != null) {

            }
        } catch (SQLException ex) {
            Controller.setstatus("No se ha podido conectar a la base de datos" + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
            Controller.setstatus("Class Not Found");
        }
        return connect;
    }

    public static void createDB() {
        createDateTable();
        createCountryTable();
        createGuestTable();
        createStayTable();
        createIndexes();
        insertCountryData();
    }

    public static void drop() {
        Connection con;
        PreparedStatement pst;
        try {
            con = connect();
            pst = con.prepareStatement(
                    "DROP TABLE IF EXISTS stay;"
                    + "DROP TABLE IF EXISTS date;"
                    + "DROP TABLE IF EXISTS guest;"
                    + "DROP TABLE IF EXISTS country;"
                    + "DROP INDEX IF EXISTS country_id;"
                    + "DROP INDEX IF EXISTS date_data;"
                    + "DROP INDEX IF EXISTS date_id;"
                    + "DROP INDEX IF EXISTS guest_data;"
                    + "DROP INDEX IF EXISTS guest_id;"
                    + "DROP INDEX IF EXISTS stay_indate;"
                    + "DROP INDEX IF EXISTS stay_outdate;"
                    + "DROP INDEX IF EXISTS stay_id;"
                    + "DROP INDEX IF EXISTS stay_room;");
            pst.executeUpdate();
            pst.close();
            close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void close() {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void createDateTable() {
        Connection con;
        PreparedStatement pst;
        try {
            con = connect();
            pst = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS date ("
                    + "	date_id	TEXT NOT NULL,"
                    + "	year	INTEGER NOT NULL,"
                    + "	month	INTEGER NOT NULL,"
                    + "	day	INTEGER NOT NULL,"
                    + "	PRIMARY KEY(date_id)"
                    + ") WITHOUT ROWID;");
            pst.executeUpdate();
            pst.close();
            close();
        } catch (SQLException ex) {
            Controller.setstatus(ex.getMessage());
        }
    }

    private static void createCountryTable() {
        Connection con;
        PreparedStatement pst;
        try {
            con = connect();
            pst = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS country ("
                    + "	country_id	TEXT NOT NULL UNIQUE,"
                    + "	name	TEXT NOT NULL,"
                    + "	PRIMARY KEY(country_id)"
                    + ") WITHOUT ROWID;");
            pst.executeUpdate();
            pst.close();
            close();
        } catch (SQLException ex) {
            Controller.setstatus(ex.getMessage());
        }
    }

    private static void createGuestTable() {
        Connection con;
        PreparedStatement pst;
        try {
            con = connect();
            pst = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS guest ("
                    + "	guest_id	TEXT NOT NULL,"
                    + "	name	TEXT NOT NULL,"
                    + "	surname	TEXT NOT NULL,"
                    + "	ci	TEXT NOT NULL,"
                    + "	age	INTEGER NOT NULL,"
                    + "	country_id	TEXT NOT NULL,"
                    + "	PRIMARY KEY(guest_id),"
                    + "	FOREIGN KEY(country_id) REFERENCES country(country_id) ON UPDATE CASCADE ON DELETE CASCADE"
                    + ") WITHOUT ROWID;");
            pst.executeUpdate();
            pst.close();
            close();
        } catch (SQLException ex) {
            Controller.setstatus(ex.getMessage());
        }
    }

    private static void createStayTable() {
        Connection con;
        PreparedStatement pst;
        try {
            con = connect();
            pst = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS stay ("
                    + "	stay_id	TEXT NOT NULL,"
                    + "	guest_id	TEXT NOT NULL,"
                    + "	indate	TEXT NOT NULL,"
                    + "	outdate	TEXT NOT NULL,"
                    + "	room_id	TEXT NOT NULL,"
                    + "	PRIMARY KEY(stay_id),"
                    + "	FOREIGN KEY(guest_id) REFERENCES guest(guest_id) ON UPDATE CASCADE ON DELETE CASCADE,"
                    + "	FOREIGN KEY(indate) REFERENCES date(date_id) ON UPDATE CASCADE ON DELETE CASCADE,"
                    + "	FOREIGN KEY(outdate) REFERENCES date(date_id) ON UPDATE CASCADE ON DELETE CASCADE"
                    + ") WITHOUT ROWID;");

            pst.executeUpdate();
            pst.close();
            close();
        } catch (SQLException ex) {
            Controller.setstatus(ex.getMessage());
        }
    }

    private static void createIndexes() {
        Connection con;
        PreparedStatement pst;
        try {
            con = connect();
            pst = con.prepareStatement(
                    " CREATE INDEX IF NOT EXISTS stay_room ON stay ("
                    + "	room_id"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS stay_indate ON stay ("
                    + "	indate"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS stay_outdate ON stay ("
                    + "	outdate"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS stay_id ON stay ("
                    + "	stay_id"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS guest_name ON guest ("
                    + "	name"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS guest_surname ON guest ("
                    + "	surname"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS guest_ci ON guest ("
                    + "	ci"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS guest_id ON guest ("
                    + "	guest_id"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS country_id ON country ("
                    + "	country_id"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS date_data ON date ("
                    + "	year,"
                    + "	month,"
                    + "	day"
                    + ");"
                    + ""
                    + "CREATE INDEX IF NOT EXISTS date_id ON date ("
                    + "	date_id"
                    + ");");
            pst.executeUpdate();
            pst.close();
            close();
        } catch (SQLException ex) {
            Controller.setstatus(ex.getMessage());
        }
    }

    private static void insertCountryData() {
        Connection con;
        PreparedStatement pst;
        try {
            con = connect();
            pst = con.prepareStatement(
                    "INSERT OR REPLACE INTO country (country_id,name) VALUES ('.ad','Andorra'),"
                    + " ('.ae','Emiratos arabes Unidos'),"
                    + " ('.af','Afganistan'),"
                    + " ('.ag','Antigua y Barbuda'),"
                    + " ('.al','Albania'),"
                    + " ('.am','Armenia'),"
                    + " ('.ao','Angola'),"
                    + " ('.ar','Argentina'),"
                    + " ('.at','Austria'),"
                    + " ('.au','Australia'),"
                    + " ('.az','Azerbaiyan'),"
                    + " ('.ba','Bosnia-Herzegovina'),"
                    + " ('.bb','Barbados'),"
                    + " ('.bd','Banglades'),"
                    + " ('.be','Belgica'),"
                    + " ('.bf','Burkina Faso'),"
                    + " ('.bg','Bulgaria'),"
                    + " ('.bh','Barein'),"
                    + " ('.bi','Burundi'),"
                    + " ('.bj','Benin'),"
                    + " ('.bm','Bermudas'),"
                    + " ('.bn','Brunei'),"
                    + " ('.bo','Bolivia'),"
                    + " ('.br','Brasil'),"
                    + " ('.bs','Bahamas'),"
                    + " ('.bt','Butan'),"
                    + " ('.bw','Botsuana'),"
                    + " ('.by','Bielorrusia'),"
                    + " ('.bz','Belice'),"
                    + " ('.ca','Canada'),"
                    + " ('.cd','Republica Democratica del Congo'),"
                    + " ('.cf','Republica Centroafricana'),"
                    + " ('.cg','Republica del Congo'),"
                    + " ('.ch','Suiza'),"
                    + " ('.ci','Costa de Marfil'),"
                    + " ('.cl','Chile'),"
                    + " ('.cm','Camerun'),"
                    + " ('.cn','Republica Popular China'),"
                    + " ('.co','Colombia'),"
                    + " ('.cr','Costa Rica'),"
                    + " ('.cu','Cuba'),"
                    + " ('.cv','Cabo Verde'),"
                    + " ('.cy','Chipre'),"
                    + " ('.cz','Chequia'),"
                    + " ('.de','Alemania'),"
                    + " ('.dj','Yibuti'),"
                    + " ('.dk','Dinamarca'),"
                    + " ('.dm','Dominica'),"
                    + " ('.do','Republica Dominicana'),"
                    + " ('.dz','Argelia'),"
                    + " ('.ec','Ecuador'),"
                    + " ('.ee','Estonia'),"
                    + " ('.eg','Egipto'),"
                    + " ('.er','Eritrea'),"
                    + " ('.es','España'),"
                    + " ('.et','Etiopia'),"
                    + " ('.fi','Finlandia'),"
                    + " ('.fj','Fiyi'),"
                    + " ('.fk','Islas Malvinas'),"
                    + " ('.fm','Estados Federados de Micronesia'),"
                    + " ('.fr','Francia'),"
                    + " ('.ga','Gabon'),"
                    + " ('.gd','Granada'),"
                    + " ('.ge','Georgia'),"
                    + " ('.gf','Guayana Francesa'),"
                    + " ('.gh','Ghana'),"
                    + " ('.gi','Gibraltar'),"
                    + " ('.gl','Groenlandia'),"
                    + " ('.gm','Gambia'),"
                    + " ('.gn','Guinea'),"
                    + " ('.gq','Guinea Ecuatorial'),"
                    + " ('.gr','Grecia'),"
                    + " ('.gt','Guatemala'),"
                    + " ('.gw','Guinea-Bisau'),"
                    + " ('.gy','Guyana'),"
                    + " ('.hk','Hong Kong'),"
                    + " ('.hn','Honduras'),"
                    + " ('.hr','Croacia'),"
                    + " ('.ht','Haiti'),"
                    + " ('.hu','Hungria'),"
                    + " ('.id','Indonesia'),"
                    + " ('.ie','Irlanda'),"
                    + " ('.il','Israel'),"
                    + " ('.in','India'),"
                    + " ('.iq','Irak'),"
                    + " ('.ir','Iran'),"
                    + " ('.is','Islandia'),"
                    + " ('.it','Italia'),"
                    + " ('.jm','Jamaica'),"
                    + " ('.jo','Jordania'),"
                    + " ('.jp','Japon'),"
                    + " ('.ke','Kenia'),"
                    + " ('.kg','Kirguistan'),"
                    + " ('.kh','Camboya'),"
                    + " ('.ki','Kiribati'),"
                    + " ('.km','Comoras'),"
                    + " ('.kn','San Cristobal y Nieves'),"
                    + " ('.kp','Corea del Norte'),"
                    + " ('.kr','Corea del Sur'),"
                    + " ('.kw','Kuwait'),"
                    + " ('.kz','Kazajistan'),"
                    + " ('.la','Laos'),"
                    + " ('.lb','Libano'),"
                    + " ('.lc','Santa Lucia'),"
                    + " ('.li','Liechtenstein'),"
                    + " ('.lk','Sri Lanka'),"
                    + " ('.lr','Liberia'),"
                    + " ('.ls','Lesoto'),"
                    + " ('.lt','Lituania'),"
                    + " ('.lu','Luxemburgo'),"
                    + " ('.lv','Letonia'),"
                    + " ('.ly','Libia'),"
                    + " ('.ma','Marruecos'),"
                    + " ('.mc','Monaco'),"
                    + " ('.md','Moldavia'),"
                    + " ('.me','Montenegro'),"
                    + " ('.mg','Madagascar'),"
                    + " ('.mh','Islas Marshall'),"
                    + " ('.mk','Macedonia del Norte'),"
                    + " ('.ml','Mali'),"
                    + " ('.mm','Birmania'),"
                    + " ('.mn','Mongolia'),"
                    + " ('.mp','Islas Marianas del Norte'),"
                    + " ('.mq','Martinica'),"
                    + " ('.mr','Mauritania'),"
                    + " ('.mt','Malta'),"
                    + " ('.mu','Mauricio'),"
                    + " ('.mv','Maldivas'),"
                    + " ('.mw','Malaui'),"
                    + " ('.mx','Mexico'),"
                    + " ('.my','Malasia'),"
                    + " ('.mz','Mozambique'),"
                    + " ('.na','Namibia'),"
                    + " ('.ne','Niger'),"
                    + " ('.ng','Nigeria'),"
                    + " ('.ni','Nicaragua'),"
                    + " ('.nl','Paises Bajos'),"
                    + " ('.no','Noruega'),"
                    + " ('.np','Nepal'),"
                    + " ('.nr','Nauru'),"
                    + " ('.nz','Nueva Zelanda'),"
                    + " ('.om','Oman'),"
                    + " ('.pa','Panama'),"
                    + " ('.pe','Peru'),"
                    + " ('.pg','Papua Nueva Guinea'),"
                    + " ('.ph','Filipinas'),"
                    + " ('.pk','Pakistan'),"
                    + " ('.pl','Polonia'),"
                    + " ('.pr','Puerto Rico'),"
                    + " ('.ps','Palestina'),"
                    + " ('.pt','Portugal'),"
                    + " ('.pw','Palaos'),"
                    + " ('.py','Paraguay'),"
                    + " ('.qa','Catar'),"
                    + " ('.ro','Rumania'),"
                    + " ('.rs','Serbia'),"
                    + " ('.ru','Rusia'),"
                    + " ('.rw','Ruanda'),"
                    + " ('.sa','Arabia Saudita'),"
                    + " ('.sb','Islas Salomon'),"
                    + " ('.sc','Seychelles'),"
                    + " ('.sd','Sudan'),"
                    + " ('.se','Suecia'),"
                    + " ('.sg','Singapur'),"
                    + " ('.si','Eslovenia'),"
                    + " ('.sk','Eslovaquia'),"
                    + " ('.sl','Sierra Leona'),"
                    + " ('.sm','San Marino'),"
                    + " ('.sn','Senegal'),"
                    + " ('.so','Somalia'),"
                    + " ('.sr','Surinam'),"
                    + " ('.st','Santo Tome y Principe'),"
                    + " ('.sv','El Salvador'),"
                    + " ('.sy','Siria'),"
                    + " ('.sz','Suazilandia'),"
                    + " ('.tc','Islas Turcas y Caicos'),"
                    + " ('.td','Chad'),"
                    + " ('.tg','Togo'),"
                    + " ('.th','Tailandia'),"
                    + " ('.tj','Tayikistan'),"
                    + " ('.tk','Tokelau'),"
                    + " ('.tl','Timor Oriental'),"
                    + " ('.tm','Turkmenistan'),"
                    + " ('.tn','Tunez'),"
                    + " ('.to','Tonga'),"
                    + " ('.tr','Turquia'),"
                    + " ('.tt','Trinidad y Tobago'),"
                    + " ('.tv','Tuvalu'),"
                    + " ('.tw','Taiwan'),"
                    + " ('.tz','Tanzania'),"
                    + " ('.ua','Ucrania'),"
                    + " ('.ug','Uganda'),"
                    + " ('.uk','Reino Unido'),"
                    + " ('.us','Estados Unidos'),"
                    + " ('.uy','Uruguay'),"
                    + " ('.uz','Uzbekistan'),"
                    + " ('.va','Ciudad del Vaticano'),"
                    + " ('.vc','San Vicente y las Granadinas'),"
                    + " ('.ve','Venezuela'),"
                    + " ('.vg','Islas Virgenes Britanicas'),"
                    + " ('.vi','Islas Virgenes de los Estados Unidos'),"
                    + " ('.vn','Vietnam'),"
                    + " ('.vu','Vanuatu'),"
                    + " ('.ws','Samoa'),"
                    + " ('.ye','Yemen'),"
                    + " ('.za','Sudafrica'),"
                    + " ('.zm','Zambia'),"
                    + " ('.zw','Zimbabue');");
            pst.executeUpdate();
            pst.close();
            close();
        } catch (SQLException ex) {
            Controller.setstatus(ex.getMessage());
        }
    }

}
