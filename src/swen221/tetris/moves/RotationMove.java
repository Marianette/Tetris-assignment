package swen221.tetris.moves;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a rotation move which is either clockwise or anti-clockwise.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class RotationMove implements Move {
	/**
	 * Rotate a given number of steps in a clockwise direction (if positive), or an
	 * anti-clockwise direction (if negative).
	 */
	private final int rotation;

	public RotationMove(int steps) {
		//should start at 0, not 1; we may call rotate of 0
		if(steps == 0) {
			this.rotation = 0;
		}
		//if it's a negative number, we want to move anti-clockwise
		else if(steps < 0) {
			this.rotation = (0 - steps);
		}
		//if it's positive, we move clockwise
		else {
			this.rotation = (0 + steps);
		}
	}

	/**
	 * Ensures that a tetromino is not being rotated past the board's edges or into another tetromino
	 */
	@Override
	public boolean isValid(Board board) {
		
			Tetromino t = board.getTetromino().rotate(this.rotation);
			
			
			if(t.getBoundingBox().getMinX() < 0 || t.getBoundingBox().getMaxX() >= board.getWidth() || t.getBoundingBox().getMinY() < 0) {
				return false;
			}
			
			//check it won't hit another tetromino when rotating
			for (int i = t.getBoundingBox().getMinX(); i <= t.getBoundingBox().getMaxX(); i++) {
				
				for(int nest = t.getBoundingBox().getMinY(); nest <= t.getBoundingBox().getMaxY(); nest++) {
					
					if(nest < board.getHeight() && t.isWithin(i, nest) && board.getPlacedTetrominoAt(i, nest) != null) {
						return false;
					}
					
				}
			}
			
			return true;
	}

	/**
	 * Get the number of rotation steps in this move.
	 *
	 * @return
	 */
	public int getRotation() {
		return rotation;
	}

	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Create a copy of this board which will be updated.
		ActiveTetromino tetromino = board.getTetromino().rotate(rotation);
		
		// Apply the move to the new board, rather than to this board.
		board.updateTetromino(tetromino);
		// Return updated version of this board.
		return board;
	}

	@Override
	public String toString() {
		return "rotate " + rotation;
	}

}
