package swen221.tetris.moves;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a "landing" move. That is, when the tetromino is placed on the
 * board properly.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class LandingMove implements Move {
	private int lines;

	public LandingMove() {
		
		this.lines = -1;
	}

	public LandingMove(int lines) {
		this.lines = lines;
	}

	/**
	 * Will ensure that a Landing move is done either on top of another tetromino
	 * or the base of the board.
	 * Will also indicate if the number of lines to be removed does not match the expected
	 * rows to remove that the argument has been passed
	 */
	@Override
	public boolean isValid(Board board) {		
		
		Tetromino t = board.getTetromino();
		Tetromino tet2 = board.getTetromino().translate(0, -1);
		Rectangle boundingBox = new Rectangle(t.getBoundingBox());
		
		//CHECK FOR LINES TO BE REMOVED
		if (lines != -1) {
			
			int linesToBeRemoved = 0;
			
			// for each existing row of cells, if that row is full, we want to clear it and increment our counter
			for (int i = 0; i < board.getHeight(); i++) {
				
				if (isFullRow(i, board)) {	
					linesToBeRemoved++;
				}
			}
			
			if (lines != linesToBeRemoved) {
				return false;
			}
		}
		
		
		//NORMAL LANDING CONDITIONS
		//if it is landing on the base row (0)
		if (board.getTetromino().getBoundingBox().getMinY() == 0) {
			return true;
		}
		
		//check it won't hit another tetromino when rotating
		for (int i = tet2.getBoundingBox().getMinX(); i <= tet2.getBoundingBox().getMaxX(); i++) {
			
			for(int nest = tet2.getBoundingBox().getMinY(); nest <= tet2.getBoundingBox().getMaxY(); nest++) {
				
				if(tet2.isWithin(i, nest) && board.getPlacedTetrominoAt(i, nest) != null) {
					return true;
				}
				
			}
		}
		
		return false;
	}

	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Place tetromino on board
		board.put(board.getTetromino());
		// Reset active tetromino
		board.updateTetromino(null);
		// Remove any full rows
		removeFullRows(board);
		// Done!
		return board;
	}

	/**
	 * Looks at a given row to see if it is completely occupied by tetrominos
	 * 
	 * @param rowNumber = row to check
	 * @param board = game area
	 * @return true if full, false if not full
	 */
	private boolean isFullRow(int rowNumber, Board board) {

		for (int i = 0; i < board.getWidth(); i++) {
			// if there is a tetromino there, the return will not be null
			if (board.getTetrominoAt(i, rowNumber) == null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Will shift all rows above a specified row down by one
	 * @param rowNumber = all rows above this specified row will be shifted down
	 * @param board = game area
	 */
	private void shiftRowDown(int rowNumber, Board board) {

		//if it's the final row (i.e. the top row), there will be no 20th row to retrieve
		if (rowNumber == board.getHeight()-1) {
			for (int i = 0; i < board.getWidth(); i++) {
				board.setPlacedTetromino(i, rowNumber, null);
			}
		} 
		//otherwise, retrieve the tetromino in the position above it
		//and set it to current cell position
		else {
			for (int i = 0; i < board.getWidth(); i++) {
				board.setPlacedTetromino(i, rowNumber, board.getPlacedTetrominoAt(i, rowNumber + 1));

			}
		}
	}
	
	
	/**
	 * Remove any rows on the board which are now full.
	 *
	 * @param board
	 * @return
	 */
	private void removeFullRows(Board board) {
		// for each existing row of cells, if that row is full, we want to clear it and
		// shift the
		// rows above it down by one
		for (int i = 0; i < board.getHeight(); i++) {

			if (isFullRow(i, board)) {

				for (int j = i; j < board.getHeight(); j++) {

					shiftRowDown(j, board);
				}
				// then we need to recheck that row for fullness so decrement the counter
				i--;
			}

		}

	}
	
	@Override
	public String toString() {
		return "landing " + lines;
	}
}
