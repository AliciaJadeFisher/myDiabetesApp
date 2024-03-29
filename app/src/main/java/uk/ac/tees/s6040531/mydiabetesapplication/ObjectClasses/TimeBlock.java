package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

import java.io.Serializable;

/**
 * TimeBlock Object Class
 */
public class TimeBlock implements Serializable
{
    // Class attributes
    private String start,end,ratio;

    /**
     * Empty constructor
     */
    public TimeBlock(){}

    /**
     * Main constructor
     * @param s - block start
     * @param e - block end
     * @param r - block ratio
     */
    public TimeBlock(String s, String e, String r)
    {
        start = s;
        end = e;
        ratio = r;
    }

    /**
     * Returns the start attribute
     * @return start
     */
    public String getStart() {
        return start;
    }

    /**
     * Returns the end attribute
     * @return end
     */
    public String getEnd() {
        return end;
    }

    /**
     * Returns the ratio attribute
     * @return ratio
     */
    public String getRatio() {
        return ratio;
    }

    /**
     * Sets the start attribute
     * @param start - block start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Sets the end attribute
     * @param end - block end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Sets the ratio attribute
     * @param ratio - block ratio
     */
    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
