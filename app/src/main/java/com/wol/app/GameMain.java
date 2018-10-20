package com.wol.app;
/**
 * This class is the main class of the "World of London" application. 
 * It provids a text-based user interface to the "World of London".<br>
 * 
 * "World of London" is a simple, text based travel game with two objectives:
 * 
 * <ol>
 *   <li>Walk around some scenery until you reach the goal location.</li>
 *   <li>Collect 3 consumable items from the characters you meet and consume them at once to end the game.</li>
 * </ol>
 * 
 * Within the game, you will also have a time limit based on the number of go commands used.
 * If you ran out of time, you lose and the game ends. You will meet various characters along the way.
 * 
 * To play this game, create an instance of this class and call the "play"
 * method, or alternatively call the static main method of the class.
 */ 
public class GameMain
{
    private Game game;
    private Parser parser;
   
    /**
     * Initialise.
     */
    public GameMain()
    {
        game = new Game();
        parser = new Parser();
    }
    
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        System.out.println(game.welcome());
        System.out.println(getHelp());
                
        while (!game.finished()) {
            Command command = parser.getCommand();
            System.out.println(command.process(this, game));
        }
    }
    
    /**
     * Main method; used if the game is standalone, outside BlueJ.
     */
    public static void main(String[] args)
    {
        GameMain gameMain = new GameMain();
        gameMain.play();
    }
    
    /**
     * Tell player how to obtain help.
     */
    public String getHelp()
    {
        return "Type '" + parser.help() + "' if you need help.";
    }
    
    /**
     * Tell player available commands.
     */
    public String getCommands()
    {
        return "Your commands are: " + parser.commands();
    }
}
