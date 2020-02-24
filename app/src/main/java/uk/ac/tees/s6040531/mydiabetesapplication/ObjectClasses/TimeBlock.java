package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;

public class TimeBlock implements Serializable
{
   String start,end,ratio;

    public TimeBlock(){}

    public TimeBlock(String s, String e, String r)
    {
        start = s;
        end = e;
        ratio = r;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
