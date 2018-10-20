package com.wol.app;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class Room - a room in a game.
 *
 * This class is part of the "World of London" application. 
 * "World of London" is a very simple, text based travel game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 */
public class Room 
{
    private String description;
    private Map<Direction, Room> exits;  // stores exits of this room.
    private Set<Character> chars;  // stores the characters that are in this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * Pre-condition: description is not null.
     */
    public Room(String description)
    {
        assert description != null : "Room.Room has null description";
        this.description = description;
        exits = new HashMap<Direction, Room>();
        chars = new HashSet<Character>();
        sane();
    }

    /**
     * Class invariant: getShortDescription() and getLongDescription() don't return null.
     */
    public void sane()
    {
        assert getShortDescription() != null : "Room has no short description" ;
        assert getLongDescription() != null : "Room has no long description" ;
    }
   
    /**
     * Define an exit from this room.
     * 
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     * 
     * Pre-condition: neither direction nor neighbor are null; 
     * there is no room in given direction yet.
     */
    public void setExit(Direction direction, Room neighbor) 
    {
        assert direction != null : "Room.setExit gets null direction";
        assert neighbor != null : "Room.setExit gets null neighbor";
        assert getExit(direction) == null : "Room.setExit set for direction that has neighbor";
        sane();
        exits.put(direction, neighbor);
        sane();
        assert getExit(direction) == neighbor : "Room.setExit has wrong neighbor";
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
     *     Items: map
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + getCharacterString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * 
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<Direction> keys = exits.keySet();
        for(Direction exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * 
     * @param direction The exit's direction.
     * @return The room in the given direction.
     * 
     * Pre-condition: direction is not null
     */
    public Room getExit(Direction direction) 
    {
        assert direction != null : "Room.getExit has null direction";
        sane();
        return exits.get(direction);
    }
    
    /**
     * Picks any of the exits of the room with equal probability.
     * If there is no exits in this room, return null.
     * 
     * @return The room based on the random direction.
     */
    public Room randomExit() 
    {
        if (exits.size() > 0) {
            ArrayList<Direction> directions = new ArrayList<>();
            directions.addAll(exits.keySet());
            
            Collections.shuffle(directions);
            return exits.get(directions.get(0));
        } else {
            return null;
        }
    }
    
    /**
     * Add given character to the room.
     * 
     * @param c The character to add.
     * 
     * Pre-condition: character is not null.
     * Pre-condition: character is not already in the room.
     */
    public void addCharacter(Character c)
    { 
        assert c != null : "Room.addCharacter has null character";
        assert !chars.contains(c) : "Room.addCharacter for existing character";
        sane();
        chars.add(c);
        sane();
    }
    
    /**
     * Remove given character from the room.
     * 
     * @param c The character to remove.
     * 
     * Pre-condition: character is not null.
     */
    public void removeCharacter(Character c)
    {
        assert c != null : "Room.removeCharacter has null character";
        chars.remove(c);
    }
    
    /**
     * Take given item from a character in the room.
     * 
     * @param item The item to take.
     * @return true if taking was successful, false otherwise
     * 
     * Pre-Condition: item is not null.
     */
    public boolean take(Item item)
    {
        assert item != null : "Room.take is given null item";
        sane();
        for (Character c : chars) {
            if (c.take(item)) {
                sane();
                return true;
            }
        }
        sane();
        return false;
    }
    
    /**
     * Return a set of characters in the room.
     */
    public Set<Character> getChars() 
    {
        return chars;
    }
       
    /**
     * Return a string listing the characters in the room.
     */
    private String getCharacterString()
    {
        if (chars.isEmpty()) {
            return "";
        } else {
            String returnString = "\nCharacters: ";
            for (Character c : chars) {
                returnString += c.toString() + "; ";
            }
            return returnString;
        }
    }
}
