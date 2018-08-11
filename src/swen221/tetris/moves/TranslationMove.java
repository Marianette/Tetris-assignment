package swen221.tetris.moves;

import swen221.tetris.logic.Board;

import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.I_Tetromino;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a translation move.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class TranslationMove implements Move {
	/**
	 * Amount to translate x-coordinate.
	 */
	private int dx;
	/**
	 * Amount to translate y-coordinate.
	 */
	private int dy;

	/**
	 * Construct new TranslationMove for a given amount of horizontal and vertical
	 * translation.
	 *
	 * @param dx
	 *            Amount to translate in horizontal direction.
	 * @param dy
	 *            Amount to translate in vertical direction.
	 */
	public TranslationMove(int dx, int dy) {
		this.dx = dx; //previously negative, no reason for it to move opposite desired direction
		this.dy = dy;
	}

	/**
	 * Ensures that a tetromino is not being moved past the board's edges or into another tetromino
	 */
	@Override
	public boolean isValid(Board board) {
		
		Tetromino t = board.getTetromino().translate(dx, dy);
		
		
		if(t.getBoundingBox().getMinX() < 0 || t.getBoundingBox().getMaxX() >= board.getWidth() || t.getBoundingBox().getMinY() < 0) {
			return false;
		}
	
		//check it won't hit another tetromino when rotating
		for (int i = t.getBoundingBox().getMinX(); i <= t.getBoundingBox().getMaxX(); i++) {
			
			for(int nest = t.getBoundingBox().getMinY(); nest <= t.getBoundingBox().getMaxY(); nest++) {
				
				if(t.isWithin(i, nest) && board.getPlacedTetrominoAt(i, nest) != null) {
					return false;
				}
				
			}

		}
		
		return true;
}
			
	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Apply translation for this move
		ActiveTetromino tetromino = board.getTetromino().translate(dx, dy);
		// Apply the move to the new board.
		board.updateTetromino(tetromino);
		// Return updated version of board
		return board;
	}

	@Override
	public String toString() {
		if (dx > 0) {
			return "right " + dx;
		} else if (dx < 0) {
			return "left " + dx;
		} else {
			return "drop " + dy;
		}
	}
}
