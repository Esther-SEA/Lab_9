/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    //private Room currentRoom;
    // Question 28
    private Player player;
    private Room outside;
    //Question 23
    private Room previousRoom;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        player = new Player("Player 1", outside, 10);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room theater, pub, lab, office;
                
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        
        //Question 20-21-22
        outside.addItem(new Item("statue",10000));
        outside.addItem(new Item("bench",50));
        
        theater.addItem(new Item("microphone",0.5));
        pub.addItem(new Item("bar",800));
        lab.addItem(new Item("tower",0.2));
        lab.addItem(new Item("magic cookie", 0.2));
        
        office.addItem(new Item("documents",1));
        office.addItem(new Item("computer",2));
        
        
        //currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getCurrentRoom().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("back")){
            goBack();
        }
         else if (commandWord.equals("take")){
            takeItem(command);
        }
         else if (commandWord.equals("drop")){
            dropItem(command);
        }
        else if (commandWord.equals("eat")){
            eatCookie(command);
        }
         else if (commandWord.equals("inventory")){
            System.out.println(player.getCarriedItemsDescription());
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        //Room item
        
        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);
        
        
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            previousRoom = player.getCurrentRoom();
            player.setCurrentRoom(nextRoom);
            System.out.println(player.getCurrentRoom().getLongDescription());
        }
    }

    //Question 23-24-25
    private void goBack(){
        player.setCurrentRoom(previousRoom);
        System.out.println(player.getCurrentRoom().getLongDescription());
    }
    
    //Question 29-30
    private void takeItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String itemName = command.getSecondWord();
        Item itemToTake = player.getCurrentRoom().findItem(itemName);
        
        if (itemToTake == null)
        {
            System.out.println("There is no such item here");
        }
        else if (!player.canCarry(itemToTake))
        {
            System.out.println("This item is way to heavy. You can't carry it!!");
        }
        else
        {
            player.getCurrentRoom().removeItem(itemToTake);
            player.takeItem(itemToTake);
            System.out.println("You have taken: "+ itemToTake.getDescription());
        }
    }
    
    private void dropItem(Command command)
    {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        String itemName = command.getSecondWord();
        Item droppedItem = player.dropItem(itemName);
        
        if( droppedItem == null)
        {
            System.out.println("You are not carrying that item");
        }
        else
        {
            player.getCurrentRoom().addItem(droppedItem);
            System.out.println("You have dropped: " + droppedItem.getDescription());
        }
    }
    
    private void eatCookie(Command command)
    {
        if (!command.hasSecondWord())
        {
            System.out.println("Eat what?");
            return;
        }
        String itemName = command.getSecondWord();
        if(!itemName.equalsIgnoreCase("magic cookie"))
        {
            System.out.println("You can't eat that!!!");
            return;
        }
        
        player.setMaxWeight(player.getMaxWeight() + 200);
        System.out.println("You ate the magic cookie! Your carry weight has increased by a 100 ");
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    
    /**
     * 19. the model view ontroller pattern is a design that separates the software and the user interface.
     * 33. I tried but i wasnt fully able to make it work i think its because of the space between magic and cookie. if it is only one word it works
     */
}

