package com.wol.app;
/**
 * Type of any valid character behaviour of the game.
 * All character behaviours implement this interface.
 */
@FunctionalInterface
public interface Act 
{
    void enterRoom(Room r);
};
