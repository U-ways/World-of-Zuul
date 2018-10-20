package com.wol.app;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class GameTest.
 */
public class GameTest {
	private Game game;

	/**
	 * Default constructor for test class GameTest
	 */
	public GameTest() {
	}

	/**
	 * Sets up the test fixture.
	 *
	 * Called before every test case method.
	 */
	@Before
	public void setUp() {
		game = new Game();
	}

	/**
	 * Tears down the test fixture.
	 *
	 * Called after every test case method.
	 */
	@After
	public void tearDown() {
	}

	/**
	 * Go to Trafalgar Square and check if the game finished. (Goal)
	 */
	@Test
	public void testToTrafalgarSquare() {
		// NOTE: Be careful from non-satic method errors:
		// https://stackoverflow.com/a/290911
		game.goRoom(Direction.WEST, false);
		game.goRoom(Direction.SOUTH, false);
		game.goRoom(Direction.WEST, false);
		game.goRoom(Direction.SOUTH, false);
		game.goRoom(Direction.SOUTH, false);
		game.goRoom(Direction.SOUTH, false);

		System.out.println(game.goRoom(Direction.SOUTH, false));
		assertEquals(true, game.finished());
	}

	/**
	 * Test the time-out condition
	 */
	@Test
	public void testTimeOut() {
		int n = 6;

		while (n != 0) {
			game.goRoom(Direction.WEST, false);
			game.goRoom(Direction.EAST, false);
			n--;
		}

		System.out.println(game.goRoom(Direction.WEST, false));
		assertEquals(true, game.finished());
	}

	/**
	 * Test the take & eat command - Spamming the take command should not end the
	 * game after eating.
	 */
	@Test
	public void testTakeSpam() {
		game.take(Item.DRINK);
		game.take(Item.DRINK);
		game.take(Item.DRINK);
		System.out.println(game.eat());
		assertEquals(false, game.finished());
	}

	@Test
	public void testDescription() {
		// test everything
		// use your old tests as well.
	}
}
