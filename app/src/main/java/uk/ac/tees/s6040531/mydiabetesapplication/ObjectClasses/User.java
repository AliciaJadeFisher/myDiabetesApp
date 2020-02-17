package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable
{
    String id, name, bs_m, cb_m;
    double hypo, bottom, top, hyper, duration, precision, correction;
    int portion;
    List<TimeBlock> time_blocks;

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

    public double getHypo() {
        return hypo;
    }

    public void setHypo(double hypo) {
        this.hypo = hypo;
    }

    public double getBottom() {
        return bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getHyper() {
        return hyper;
    }

    public void setHyper(double hyper) {
        this.hyper = hyper;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    public double getCorrection() {
        return correction;
    }

    public void setCorrection(double correction) {
        this.correction = correction;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
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
}
