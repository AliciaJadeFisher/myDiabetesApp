package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class BloodSugarEntry implements Serializable
{
    Date date;
    Time time;
    double bs;
    double carbs;
    double insulin_f;
    double insulin_c;
    String meal;
    String notes;

    public void BloodSugarEntry(Date d, Time t, double b, double c, double inf, double inc, String m, String n)
    {
        date = d;
        time = t;
        bs = b;
        carbs = c;
        insulin_f = inf;
        insulin_c = inc;
        meal = m;
        notes = n;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getBs() {
        return bs;
    }

    public void setBs(double bs) {
        this.bs = bs;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getInsulin_f() {
        return insulin_f;
    }

    public void setInsulin_f(double insulin_f) {
        this.insulin_f = insulin_f;
    }

    public double getInsulin_c() {
        return insulin_c;
    }

    public void setInsulin_c(double insulin_c) {
        this.insulin_c = insulin_c;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
