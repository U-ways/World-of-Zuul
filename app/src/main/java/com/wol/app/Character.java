package com.wol.app;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.*;

/**
 * Enumeration class Character
 * A character in the game.
 */
public enum Character
{
    LAURA("Laura", Item.SANDWICH, 0.5, r -> {}),
    SALLY("Sally", Item.CRISPS  , 0.5, r -> {}),
    ANDY("Andy"  , Item.DRINK   , 0.5, r -> {}),
    ALEX("Alex"  , null         , 0.5, r -> {}),
    PLAYER("The player (Me)"      , null, 0.0, r -> {}),
    // The CookieMonster takes crisps from everyone in the room if they are available.
    COOKIE_MONSTER("CookieMonster", null, 1.0, r -> r.getChars().forEach(c -> c.take(Item.CRISPS))),
    // The CrispGiver gives crisps to everyone in the room.
    CRISP_GIVER("CrispGiver"      , null, 1.0, r -> r.getChars().forEach(c -> c.receive(Item.CRISPS)));
    
    private String description;
    // Characters actions
    private Act act;
    // Characters move probability
    private final double moveProbability;
    // Characters items inventory
    private Set<Item> itemList;
    
    /**
     * Constructor initialising description and item.
     */
    private Character(String desc, Item it, double moveProb, Act enterRoom)
    {
        itemList = new HashSet<>();
        itemList.add(it);
        act             = enterRoom;
        description     = desc;
        moveProbability = moveProb;
    }
    
    /**
     * Return the description and description of item if it exists.
     */
    public String toString()
    {
        itemList.remove(null);
        
        if (itemList.isEmpty()) {
            return description;
        } else {
            String items = itemList.stream()
                .map(Item::toString)
                .collect(Collectors.joining(", "));
            
            return description + " having the item[s]: " + items;
        }
    }
    
    /**
     * Take the given item from the character if it has that item.
     * Return whether item was taken.
     * 
     * @param The item to take away.
     * @returns true if the character had the item before the call.
     */
    public boolean take(Item it)
    {
        if (itemList.contains(it)) {
            itemList.remove(it);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Give an item to the character.
     * Return whether item was given.
     * 
     * @param The item to take away.
     * @returns true if the character had received the item.
     */
    public boolean receive(Item e)
    {
        if (!itemList.contains(e)) {
            itemList.add(e);
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Gets the item list from the character.
     * @returns the item list from the character.
     */
    public Set<Item> getList() 
    {
        return itemList;
    }
    
    /**
     * Exhibit the character's default behaviours.
     * 
     * @param The room entered.
     */
    public void enterRoom(Room r) 
    {
        act.enterRoom(r);
    }
    
    /**
     * Compares the character move probability against a given probability.
     * 
     * @param random move probability 
     * @returns true iff the character move probability is smaller than d.
     */
    public boolean automove(double d)
    {
        return (moveProbability < d) ? true : false;
    }
}
