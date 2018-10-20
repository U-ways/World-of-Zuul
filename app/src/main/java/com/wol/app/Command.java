package com.wol.app;
/**
 * Type of any valid command of the game.
 * All commands implement this interface.
 */
public interface Command
{
    /**
     * Process, that is, execute the command in the game.
     * @param ui  The user interface of the game.
     * @param game The actual game.
     * @return Message to the player.
     */
    public String process(GameMain ui, Game game); 
}
