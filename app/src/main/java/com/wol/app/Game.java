package com.wol.app;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Arrays;

/**
 * This class is the central class of the "World of London" application. <br/>
 * "World of London" is a simple, text based travel game with two objectives:
 * 
 * <ol>
 *   <li>Walk around some scenery until you reach the goal location.</li>
 *   <li>Collect 3 consumable items from the characters you meet and consume them at once to end the game.</li>
 * </ol>
 * 
 * Within the game, you will also have a time limit based on the number of go commands used.
 * If you ran out of time, you lose and the game ends. You will meet various characters along the way.
 */

public class Game 
{
    private static final int TIME_LIMIT = 12;
    private int time;
    private Room currentRoom;
    private Room goalRoom;
    private boolean finished;
    private Map<Character,Room> CharLocation; // The room location of each character
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        finished = false;
        time         = 0;
        CharLocation = new HashMap<>();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room trafalgarSquare, chinatown, oxfordStreet, soho, coventGarden, 
        britishMuseum, stPancras, kingsCross, britishLibrary, leicesterSquare;

        // create the rooms
        trafalgarSquare = new Room("on Trafalgar Square");
        chinatown       = new Room("in Chinatown");
        oxfordStreet    = new Room("on Oxford Street");
        soho            = new Room("in Soho");
        coventGarden    = new Room("in Covent Garden");
        britishMuseum   = new Room("in the British Museum");
        stPancras       = new Room("in St Pancras");
        kingsCross      = new Room("in Kings Cross");
        britishLibrary  = new Room("in the British Library");
        leicesterSquare = new Room("on Leicester Square");

        // Initialise room exits
        kingsCross      .setExit(Direction.WEST,  stPancras);
        stPancras       .setExit(Direction.EAST,  kingsCross);
        stPancras       .setExit(Direction.WEST,  britishLibrary);
        britishLibrary  .setExit(Direction.EAST,  stPancras);
        britishLibrary  .setExit(Direction.SOUTH, britishMuseum);
        britishMuseum   .setExit(Direction.NORTH, britishLibrary);
        britishMuseum   .setExit(Direction.WEST,  oxfordStreet);
        oxfordStreet    .setExit(Direction.EAST,  britishMuseum);
        britishMuseum   .setExit(Direction.SOUTH, coventGarden);
        coventGarden    .setExit(Direction.NORTH, britishMuseum);
        oxfordStreet    .setExit(Direction.SOUTH, soho);
        soho            .setExit(Direction.NORTH, oxfordStreet);
        soho            .setExit(Direction.SOUTH, chinatown);
        chinatown       .setExit(Direction.NORTH, soho);
        chinatown       .setExit(Direction.SOUTH, leicesterSquare);
        leicesterSquare .setExit(Direction.NORTH, chinatown);
        leicesterSquare .setExit(Direction.EAST,  coventGarden);
        coventGarden    .setExit(Direction.WEST,  leicesterSquare);
        leicesterSquare .setExit(Direction.SOUTH, trafalgarSquare);
        trafalgarSquare .setExit(Direction.NORTH, leicesterSquare);
        
        // Adding characters
        britishLibrary  .addCharacter(Character.SALLY);
        oxfordStreet    .addCharacter(Character.LAURA);
        leicesterSquare .addCharacter(Character.ANDY);
        trafalgarSquare .addCharacter(Character.ALEX);
        
        // Adding New characters
        stPancras.addCharacter(Character.PLAYER);
        stPancras.addCharacter(Character.COOKIE_MONSTER);
        stPancras.addCharacter(Character.CRISP_GIVER);
        
        // Tracking character location
        CharLocation.put(Character.SALLY, britishLibrary);
        CharLocation.put(Character.LAURA, oxfordStreet);
        CharLocation.put(Character.ANDY,  leicesterSquare);
        CharLocation.put(Character.ALEX,  trafalgarSquare);
        CharLocation.put(Character.PLAYER,         stPancras);
        CharLocation.put(Character.COOKIE_MONSTER, stPancras);
        CharLocation.put(Character.CRISP_GIVER,    stPancras);
        
        currentRoom = CharLocation.get(Character.PLAYER);
        goalRoom    = trafalgarSquare;
    }

    /**
     * Current time is within time limit.
     */
    public boolean inTime()
    {
        return 0 <= time && time <= TIME_LIMIT;
    }

    /**
     * Return whether the game has finished or not.
     */
    public boolean finished()
    {
        return finished;
    }

    /**
     * Opening message for the player.
     */
    public String welcome()
    {
        return "\nWelcome to the World of London!\n" +
        "World of London is a new travel game.\n" +
        currentRoom.getLongDescription() + "\n";
    }

    // implementations of user commands:
    /**
     * Give some help information.
     */
    public String help() 
    {
        return "You are lost. You are alone. You wander around foggy London.\n";
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room and return its long description; otherwise return an error message.
     * 
     * If random is true, pick a random direction.
     * 
     * @param direction The direction in which to go.
     * @param random    Random room exit boolean trigger.
     * 
     * Pre-condition: direction is not null.
     */
    public String goRoom(Direction direction, boolean random) 
    {
        time++;
        // Move the characters each time we advance the clock
        moveCharacters();
        Room nextRoom;
        
        // Try to leave current room.
        if (random) {
            nextRoom = currentRoom.randomExit();
        } 
        else {
            assert direction != null : "Game.goRoom gets null direction";
            nextRoom = currentRoom.getExit(direction);
        }
        
        
        if (nextRoom == null) {
            return "There is no exit in that direction!";
        }
        else {
            currentRoom.removeCharacter(Character.PLAYER);
            nextRoom.addCharacter(Character.PLAYER);
            
            CharLocation.replace(Character.PLAYER, nextRoom);
            currentRoom = CharLocation.get(Character.PLAYER);
            
            String result = look();
            if (currentRoom == goalRoom) {
                result += "\nCongratulations! You reached the goal of the game.\n";
                result += quit();
            } else if (!inTime()) {
                result += "\nYou ran out of time. You have lost.\n";
                result += quit();
            }
            
            return result;
        }
    }

    /**
     * Execute quit command.
     */
    public String quit()
    {
        finished = true;
        return "Thank you for playing.  Good bye.";
    }

    /**
     * Execute look command.
     */
    public String look()
    {
        return currentRoom.getLongDescription();
    }

    /**
     * Execute take command.
     * 
     * Pre-condition: item is not null.
     */
    public String take(Item item)
    {
        assert item != null : "Game.take gets null item";
        if (currentRoom.take(item)) {
            Character.PLAYER.getList().add(item);
            return "Item taken.";
        } else {
            return "Item not in this room.";
        }
    }
    
    /**
     * Execute eat command.
     */
    public String eat()
    {
        final Set<Item> meal = 
            new HashSet<>(Arrays.asList(Item.SANDWICH, Item.DRINK, Item.CRISPS));
        
        if (Character.PLAYER.getList().containsAll(meal)) {
            return "Congratulations! You have won.\n" + quit();
        } else {
            return "You cannot eat yet.";
        }
    }
    
    /**
     * Move all characters in the game based on their move probability
     * and execute their default behaviours.
     */
    private void moveCharacters() 
    {
        double random = Math.random();
        
        CharLocation.forEach(
            (character, room) -> {
                if (! character.automove(random)) {
                    Room randRoom = room.randomExit();

                    room.removeCharacter(character);
                    character.enterRoom(randRoom); // Act before adding character.
                    randRoom.addCharacter(character);

                    CharLocation.replace(character, room, randRoom);
                }
            }
        );
    }
}
