package models;

import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Date {

    public static Date NOT_FOUND = new Date("Not Found", 0, 0, 0);

    private SimpleStringProperty ID;
    private SimpleIntegerProperty Year;
    private SimpleIntegerProperty Month;
    private SimpleIntegerProperty DayofMonth;

    public Date(int Year, int Month, int DayofMonth) {
        this.Year = new SimpleIntegerProperty(Year);
        this.Month = new SimpleIntegerProperty(Month);
        this.DayofMonth = new SimpleIntegerProperty(DayofMonth);
        UpdateID();
    }

    public Date(String ID, int Year, int Month, int DayofMonth) {
        this.Year = new SimpleIntegerProperty(Year);
        this.Month = new SimpleIntegerProperty(Month);
        this.DayofMonth = new SimpleIntegerProperty(DayofMonth);
        this.ID = new SimpleStringProperty(ID);
    }

    public Date(String stringdate) {
        if (stringdate.matches("[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}")) {
            String[] s = stringdate.split("-", 3);
            this.Year = new SimpleIntegerProperty(Integer.parseInt(s[0]));
            this.Month = new SimpleIntegerProperty(Integer.parseInt(s[1]));
            this.DayofMonth = new SimpleIntegerProperty(Integer.parseInt(s[2]));
            UpdateID();
        }
    }

    public Date(LocalDate d) {
        this.Year = new SimpleIntegerProperty(d.getYear());
        this.Month = new SimpleIntegerProperty(d.getMonthValue());
        this.DayofMonth = new SimpleIntegerProperty(d.getDayOfMonth());
        UpdateID();
    }

    public int getYear() {
        return Year.get();
    }

    public void setYear(int year) {
        Year.set(year);
        UpdateID();
    }

    public int getMonth() {
        return Month.get();
    }

    public void setMonth(int month) {
        Month.set(month);
        UpdateID();
    }

    public int getDay() {
        return DayofMonth.get();
    }

    public void setDay(int dayofMonth) {
        DayofMonth.set(dayofMonth);
        UpdateID();
    }

    public String getID() {
        return this.ID.get();
    }

    private void UpdateID() {
        this.ID = new SimpleStringProperty(Integer.toString(Year.get()) + "-" + Integer.toString(Month.get()) + "-" + Integer.toString(DayofMonth.get()));
    }

    @Override
    public String toString() {
        return Year.get() + "-" + Month.get() + "-" + DayofMonth.get();
    }
}
