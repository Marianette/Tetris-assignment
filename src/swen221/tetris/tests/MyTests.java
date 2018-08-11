package swen221.tetris.tests;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import swen221.tetris.logic.Game;

public class MyTests {	
	@Test
	   void testValid_00() {
		String input =
		          "(I yellow; rotate -1)";
		     Game game = Game.fromString(5,5, input);
		     //
		     assertEquals(
		       "|_|_|Y|_|_|\n" + // 4
		       "|_|_|Y|_|_|\n" + // 3
		       "|_|_|Y|_|_|\n" + // 2
		       "|_|_|Y|_|_|\n" + // 1
		       "|_|_|_|_|_|\n",  // 0
		       game.getActiveBoard().toString());
	   }

	
}
