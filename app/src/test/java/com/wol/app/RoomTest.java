package com.wol.app;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RoomTest.
 */
public class RoomTest {
	private Room room;

	/**
	 * Default constructor for test class RoomTest
	 */
	public RoomTest() {
	}

	/**
	 * Sets up the test fixture.
	 *
	 * Called before every test case method.
	 */
	@Before
	public void setUp() {
		room = new Room("library");
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
	 * Test that exits are set correctly.
	 */
	@Test
	public void checkExits() {
		room.setExit(Direction.NORTH, room);
		room.setExit(Direction.WEST, room);
		assertEquals(room, room.getExit(Direction.NORTH));
		assertEquals(room, room.getExit(Direction.WEST));
		assertEquals(null, room.getExit(Direction.EAST));
	}

	/**
	 * Test that description, both short and long are correct.
	 */
	@Test
	public void checkDescription() {
		assertEquals("library", room.getShortDescription());
		assertEquals("You are library.\nExits:", room.getLongDescription());
	}
}
