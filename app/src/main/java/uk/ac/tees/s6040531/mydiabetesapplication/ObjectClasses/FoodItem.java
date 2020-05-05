package uk.ac.tees.s6040531.mydiabetesapplication.ObjectClasses;

/**
 * FoodItem Object Class
 */
public class FoodItem
{
    // Class attributes
    private String name;
    private int serving;
    private double carbs_100, carbs_serv;

    /**
     * Main constructor
     * @param n - food name
     * @param s - serving size
     * @param c - carbohyrdates per 100g
     * @param cs - carbohydrates per serving
     */
    public FoodItem(String n, int s, double c, double cs)
    {
        name = n;
        serving = s;
        carbs_100 = c;
        carbs_serv = cs;
    }

    /**
     * Returns the name attribute
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the serving attribute
     * @return serving
     */
    public int getServing() {
        return serving;
    }

    /**
     * Returns the carbs per 100g attribute
     * @return carbs_100
     */
    public double getCarbs_100() {
        return carbs_100;
    }

    /**
     * Returns the carbs per serving attribute
     * @return carbs_serv
     */
    public double getCarbs_serv() {
        return carbs_serv;
    }
}
