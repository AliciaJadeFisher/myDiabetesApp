package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * BloodSugarEntry Object Class
 */
public class BloodSugarEntry implements Serializable
{
    // Class attributes
    Date date;
    String time;
    double bs;
    double carbs;
    double insulin_f;
    double insulin_c;
    double insulin_t;
    String meal;
    String notes;

    /**
     * Empty constructor
     */
    public BloodSugarEntry(){}

    /**
     * Main constructor
     * @param d
     * @param t
     * @param b
     * @param c
     * @param inf
     * @param inc
     * @param m
     * @param n
     */
    public void BloodSugarEntry(Date d, String t, double b, double c, double inf, double inc, String m, String n)
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

    /**
     * Returns the date attribute
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date attribute
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Returns the time attribute
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the time attribute
     * @param time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Returns the blood sugar attribute
     * @return bs
     */
    public double getBs() {
        return bs;
    }

    /**
     * Sets the blood sugar attribute
     * @param bs
     */
    public void setBs(double bs) {
        this.bs = bs;
    }

    /**
     * Returns the carbs attribute
     * @return carbs
     */
    public double getCarbs() {
        return carbs;
    }

    /**
     * Sets the carbs attribute
     * @param carbs
     */
    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    /**
     * Returns the food insulin attribute
     * @return insulin_f
     */
    public double getInsulin_f() {
        return insulin_f;
    }

    /**
     * Sets the food insulin attribute
     * @param insulin_f
     */
    public void setInsulin_f(double insulin_f) {
        this.insulin_f = insulin_f;
    }

    /**
     * Returns the correction insulin attribute
     * @return insulin_c
     */
    public double getInsulin_c() {
        return insulin_c;
    }

    /**
     * Sets the correction insulin attribute
     * @param insulin_c
     */
    public void setInsulin_c(double insulin_c) {
        this.insulin_c = insulin_c;
    }

    /**
     * Returns the total insulin attribute
     * @return insulin_t
     */
    public double getInsulin_t() {
        return insulin_t;
    }

    /**
     * Sets the total insulin attribute
     * @param insulin_t
     */
    public void setInsulin_t(double insulin_t) {
        this.insulin_t = insulin_t;
    }

    /**
     * Returns the meal attribute
     * @return meal
     */
    public String getMeal() {
        return meal;
    }

    /**
     * Sets the meal attribute
     * @param meal
     */
    public void setMeal(String meal) {
        this.meal = meal;
    }

    /**
     * Returns the notes attribute
     * @return notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes attribute
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}