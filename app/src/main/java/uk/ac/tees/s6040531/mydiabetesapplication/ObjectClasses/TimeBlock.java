package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

public class TimeBlock
{
   double start,end,ratio;

    public TimeBlock(double s, double e, double r)
    {
        start = s;
        end = e;
        ratio = r;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
