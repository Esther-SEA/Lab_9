import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    // Question 20-21: private Item roomItem;
    private ArrayList<Item> items;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        //Question 22
        items = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    /**public void setItem(Item item)
    {
        this.roomItem = item;
    }
    
    /** Question 20-21 public Item getItem()
    {
        return roomItem;
    }*/
    
    //Question 22
    public void addItem(Item item){
        items.add(item);
    }
    
    public void removeItem(Item item)
    {
        items.remove(item);
    }
    
    public Item findItem (String name)
    {
        for (Item item : items)
        {
            if (item.getDescription().equals(name))
            {
                return item;
            }
        }
        return null;
    }
    
    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     *  
     */
    public String getLongDescription()
    {
        /**Question 20-21
        String itemDescription;
        if(roomItem != null){
            itemDescription = "There is an item here: " + roomItem.getDescription() + " " + roomItem.getWeight() + "kg";
        }
        
        else {
            itemDescription = "There are no items here";
        }*/
        
        //Question 22
        String itemDescription = "There are no items in this room";
        
        if(!items.isEmpty()){
            itemDescription = "Items: ";
            for(Item item : items){
                itemDescription += item.getDescription() + " " + item.getWeight() + "kg" + " ";
            }
        }
        return "You are " + description + ".\n" + getExitString() + "\n" + itemDescription;
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
}

