package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User Object Class
 */
public class User implements Serializable
{
    // Class attributes
    private String id, name, bs_m, cb_m,hypo, bottom, top, hyper, duration, precision, correction,portion;
    private List<TimeBlock> time_blocks;
    private List<BloodSugarEntry> blood_sugars = new ArrayList<>();

    /**
     * Empty constructor
     */
    public User()
    {}

    /**
     * Returns the id attribute
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name attribute
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the blood sugar measurement attribute
     * @return bs_m
     */
    public String getBs_m() {
        return bs_m;
    }

    /**
     * Returns the carbohydrate measurement attribute
     * @return cb_m
     */
    public String getCb_m() {
        return cb_m;
    }

    /**
     * Returns the hypo attribute
     * @return hypo
     */
    public String getHypo() {
        return hypo;
    }

    /**
     * Returns the bottom attribute
     * @return bottom
     */
    public String getBottom() {
        return bottom;
    }

    /**
     * Returns the top attribute
     * @return top
     */
    public String getTop() {
        return top;
    }

    /**
     * Returns the hyper attribute
     * @return hyper
     */
    public String getHyper() {
        return hyper;
    }

    /**
     * Returns the duration attribute
     * @return duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Returns the precision attribute
     * @return precision
     */
    public String getPrecision() {
        return precision;
    }

    /**
     * Returns the correction attribute
     * @return correction
     */
    public String getCorrection() {
        return correction;
    }

    /**
     * Returns the portion attribute
     * @return portion
     */
    public String getPortion() {
        return portion;
    }

    /**
     * Returns the time blocks attribute
     * @return time_blocks
     */
    public List<TimeBlock> getTime_blocks() {
        return time_blocks;
    }

    /**
     * Returns the blood sugars attribute
     * @return blood_sugars
     */
    public List<BloodSugarEntry> getBlood_sugars() {
        return blood_sugars;
    }

    /**
     * Sets the id attribute
     * @param id - user id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the name attribute
     * @param name - user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the blood sugar measurement attribute
     * @param bs_m - user blood sugar measurement
     */
    public void setBs_m(String bs_m) {
        this.bs_m = bs_m;
    }

    /**
     * Sets the carbohydrate measurement attribute
     * @param cb_m - user carbohydrate measurement
     */
    public void setCb_m(String cb_m) {
        this.cb_m = cb_m;
    }

    /**
     * Sets the hypo attribute
     * @param hypo - user hypo limit
     */
    public void setHypo(String hypo) {
        this.hypo = hypo;
    }

    /**
     * Sets the bottom attribute
     * @param bottom - user target bottom
     */
    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    /**
     * Sets the top attribute
     * @param top - user target top
     */
    public void setTop(String top) {
        this.top = top;
    }

    /**
     * Sets the hyper attribute
     * @param hyper - user hyper limit
     */
    public void setHyper(String hyper) {
        this.hyper = hyper;
    }

    /**
     * Sets the duration attribute
     * @param duration - user insulin duration
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * Sets the precision attribute
     * @param precision - user insulin precision
     */
    public void setPrecision(String precision) {
        this.precision = precision;
    }

    /**
     * Sets the correction attribute
     * @param correction - user correction dosage
     */
    public void setCorrection(String correction) {
        this.correction = correction;
    }

    /**
     * Sets the portion attribute
     * @param portion - user carbohydrate portion
     */
    public void setPortion(String portion) {
        this.portion = portion;
    }

    /**
     * Sets the time blocks attribute
     * @param time_blocks - user ratio time blocks
     */
    public void setTime_blocks(List<TimeBlock> time_blocks) {
        this.time_blocks = time_blocks;
    }

    /**
     * Sets the blood sugars attribute
     * @param blood_sugars - user blood sugars list
     */
    public void setBlood_sugars(List<BloodSugarEntry> blood_sugars) {
        this.blood_sugars = blood_sugars;
    }

    /**
     * Adds a new blood sugar entry
     * @param newEntry - new blood sugar entry
     */
    public void addBlood_sugar(BloodSugarEntry newEntry)
    {
        blood_sugars.add(newEntry);
    }
}