package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;
import java.util.Date;

/**
 * BloodSugarEntry Object Class
 */
public class BloodSugarEntry implements Serializable
{
    // Class attributes
    private Date date;
    private String time;
    private double bs;
    private double carbs;
    private double insulin_f;
    private double insulin_c;
    private double insulin_t;
    private String meal;
    private String notes;

    /**
     * Empty constructor
     */
    public BloodSugarEntry(){}

    /**
     * Returns the date attribute
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date attribute
     * @param date - entry date
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
     * @param time - entry time
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
     * @param bs - blood sugar
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
     * @param carbs - carbohydrates
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
     * @param insulin_f - insulin for food
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
     * @param insulin_c - insulin for correction
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
     * @param insulin_t - total insuliln
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
     * @param meal - meal type
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
     * @param notes - entry notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
}