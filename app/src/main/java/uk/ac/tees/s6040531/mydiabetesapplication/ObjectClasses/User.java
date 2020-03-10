package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable
{
    String id, name, bs_m, cb_m,hypo, bottom, top, hyper, duration, precision, correction,portion;
    List<TimeBlock> time_blocks;
    List<BloodSugarEntry> blood_sugars = new ArrayList<>();

    public User()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBs_m() {
        return bs_m;
    }

    public void setBs_m(String bs_m) {
        this.bs_m = bs_m;
    }

    public String getCb_m() {
        return cb_m;
    }

    public void setCb_m(String cb_m) {
        this.cb_m = cb_m;
    }

    public String getHypo() {
        return hypo;
    }

    public void setHypo(String hypo) {
        this.hypo = hypo;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getHyper() {
        return hyper;
    }

    public void setHyper(String hyper) {
        this.hyper = hyper;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public String getCorrection() {
        return correction;
    }

    public void setCorrection(String correction) {
        this.correction = correction;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public List<TimeBlock> getTime_blocks()
    {
        return time_blocks;
    }

    public void setTime_blocks(List<TimeBlock> time_blocks)
    {
        this.time_blocks = time_blocks;
    }

    public List<BloodSugarEntry> getBlood_sugars() {
        return blood_sugars;
    }

    public void setBlood_sugars(List<BloodSugarEntry> blood_sugars) {
        this.blood_sugars = blood_sugars;
    }
}
