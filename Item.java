
/**
 * Write a description of class Item here.
 *
 * @author (your name)
 * @version (a version number or a date)
 * Question 20-21
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String description;
    private String weight;

    /**
     * Constructor for objects of class Item
     */
    public Item(String description, String weight)
    {
        this.description = description;
        this.weight = weight;
    }

    public String getDescription()
    {
        return description;
    }
    
    public String getWeight()
    {
        return weight;
    }
}
