package swen221.tetris.tetromino;

import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.Tetromino.Orientation;

/**
 * Represents a tetromino which can only perform one rotation operation.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class StickyTetromino implements Tetromino {

	Tetromino stickyBoi;
	int rotations;
	
	/**
	 * The StickyTetromino class will allow a user to rotate a Tetromino up to three times before it becomes "stuck".
	 * A stuck tetromino will be unable to rotate any further and become Dark Gray.
	 * 
	 * @param count = number of steps to be rotated
	 * @param tetromino = tetromino that is sticky
	 */
	public StickyTetromino(int count, Tetromino tetromino) {
		this.stickyBoi = tetromino;
		this.rotations = count;
	}

	/**
	 * if there are remaining rotations, return the usual color, otherwise return dark gray
	 */
	@Override
	public Color getColor() {
		
		if (rotations == 0) {
			return Color.DARK_GRAY;
		}
		
		return stickyBoi.getColor();
	}

	/**
	 * will return the orientation of the tetromino seen as "sticky"
	 */
	@Override
	public Orientation getOrientation() {
		return stickyBoi.getOrientation();
	}

	/**
	 * refers to the boundaries of the Tetromino within the bounding box (passed to the StickyTetromino class from the original Tetromino)
	 */
	@Override
	public boolean isWithin(int x, int y) {			
		return stickyBoi.isWithin(x, y);
	}

	/**
	 * refers to the bounding of the tetromino that is passed (from the original tetromino) to the StickyTetromino class
	 */
	@Override
	public Rectangle getBoundingBox() {
		return stickyBoi.getBoundingBox();
		
	}
	
	/**
	 * Will rotate the sticky tetromino the specified number of steps,
	 * or the maximum number that it is allowed to rotate
	 */
	@Override
	public Tetromino rotate(int steps) {
		
		if(rotations > steps) {
			return new StickyTetromino(rotations - steps, stickyBoi.rotate(steps));	
		}
		else {
			return new StickyTetromino(0, stickyBoi.rotate(rotations));
		}

	}

}


