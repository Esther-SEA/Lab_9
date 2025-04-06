import java.util.ArrayList;
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private String name;
    private Room currentRoom;
    private ArrayList<Item> carriedItems;
    private double maxWeight;
    public Player(String name, Room startingRoom, double maxWeight)
    {
        this.name = name;
        this.currentRoom = startingRoom;
        this.carriedItems = new ArrayList<>();
        this.maxWeight = maxWeight;
    }
    
    public String getName()
    {
        return name;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    public void setCurrentRoom(Room room)
    {
        this.currentRoom = room;    
    }
    
    //Question 31
    public double getTotalWeight()
    {
        double totalWeight = 0;
        for(Item item : carriedItems)
        {
            totalWeight += item.getWeight();
        }
        return totalWeight;
    }
    
    public boolean canCarry(Item item)
    {
        return (getTotalWeight() + item.getWeight())<= maxWeight;
    }
    
      public boolean takeItem(Item item)
    {
        if (canCarry(item))
        {
            carriedItems.add(item);
            return true;
        }
        return false;
    }
    
    public Item dropItem(String itemName){
        for(Item item : carriedItems)
        {
            if (item.getDescription().equals(itemName))
            {
                carriedItems.remove(item);
                return item;
            }
        }
        return null;
    }
    
    //Question 32
    public String getCarriedItemsDescription()
    {
        if (carriedItems.isEmpty())
        {
            return "You are not carrying any item";
        }
        
        String description = "You are carrying: ";
        for(int i = 0; i < carriedItems.size(); i++)
        {
            description += carriedItems.get(i).getDescription();
            if (i < carriedItems.size() -1)
            {
                description += ", ";
            }
        }
        description +="\nTotal weight: " + getTotalWeight() + "kg" + "\nMaximum carry weight: " + maxWeight;
        return description;
    }
    
    public double getMaxWeight()
    {
        return maxWeight;
    }
    
    public void setMaxWeight(double maxWeight)
    {
        this.maxWeight = maxWeight;
    }
}
