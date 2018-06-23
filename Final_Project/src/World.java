import java.util.ArrayList;
import java.util.Arrays;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

/** 
 * Represents the world of the game. Stores the ArrayList of sprites and handles rendering,
 * winning, player death, move counts and undoing.
 * The code provided in the sample Project 1 was used as a base.
 */
public class World {
	public static ArrayList<Sprite> sprites;
	
	// whether or not the level was been completed.
	private boolean gameWon;
	
	// counts the moves made by the player.
	private static int moveCount;
	
	// whether the player has been hit by an enemy.
	private static boolean playerDeath;
	
	/**
	 * Constructor for the World class.
	 * @param	level_src	The level text source file.
	 */
	public World(String level_src) {
		sprites = Loader.loadSprites(level_src);
		gameWon = false;
		moveCount = 0;
		playerDeath = false;
	}
	
	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	public void update(Input input, int delta) {
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.update(input, delta);
			}
		}
		
		// check if all the targets in the map have been covered by a block.
		boolean winPossible = true;
		boolean hasTargets = false;
		for (Sprite sprite : sprites) {
			if (Sprite.compareSprite(sprite, "target")) {
				hasTargets = true;
				if (!sprite.getIsCovered()) {
					winPossible = false;
					break;
				}
			}
			winPossible = true;
		}
		
		// win level if the map has at least one target, and all targets are covered.
		if (winPossible && hasTargets) {
			gameWon = true;
		}
		
		// cannot undo if no moves have been made.
		if (input.isKeyPressed(Input.KEY_Z) && (moveCount > 0)) {
			undo();
		}

	}
	
	/**
	 * Render the entire screen, so it reflects the current game state.
     * @param	g	The Slick graphics object, used for drawing.
     */
	public void render(Graphics g) {
		for (Sprite sprite : sprites) {
			if ((sprite != null) && sprite.getDoRender()) {
				sprite.render(g);
			}
		}
		
		// draw the number of moves to the screen.
		g.drawString("Move Count: " + moveCount, 10, 10);
	}
	
	/**
	 * Refresh the input sprite to contain the information stored at the top of
	 * this sprite's history stack.
	 * @param	sprite	The sprite that gets updated.
	 */
	private void refreshSprite(Sprite sprite) {
		
		// The TNT block should not be able to be undone after the explosion, as per the specification.
		// This method of exclusion allows for a change such that the explosion could be undone if desired.
		if (Sprite.compareSprite(sprite, "tnt") && !sprite.getDoRender()) {
			return;
		}
		
		// Pop the history stack and reset the sprite information.
		SpriteHistory current = sprite.popHistoryStack();
		sprite.setX(current.getX());
		sprite.setY(current.getY());
		sprite.setIsSolid(current.getIsSolid());
		sprite.setDoRender(current.getDoRender());
		sprite.setDirection(current.getDirection());
	}
	
	/** Undo the last player move. */
	private void undo() {
		
		// Only undo the movement of the player and blocks.
		for (Sprite sprite : sprites) {
			if (Arrays.asList(Loader.undoable).contains(sprite.getImageName())) {
				refreshSprite(sprite);
			}
		}
		moveCount--;
	}
	
	/**
	 * Returns a boolean that states whether a level is completed.
	 * @return		Whether the level is won.	
	 */
	public boolean getGameWon() {
		return gameWon;
	}
	
	/** Increases the number of moves made by one. */
	public static void increaseMoveCount() {
		moveCount++;
	}
	
	/** Sets the player to be in the dead state. */
	public static void killPlayer() {
		playerDeath = true;
	}
	
	/**
	 * Returns a boolean that states whether the player has died.
	 * @return		Whether the player is in the dead state.
	 */
	public static boolean getPlayerDeath() {
		return playerDeath;
	}
}
