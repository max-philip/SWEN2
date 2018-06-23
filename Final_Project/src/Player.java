// The code provided in the sample Project 1 was used as a base.

import org.newdawn.slick.Input;

/**
 * Handles the update for the player, updates the sprite history stack,
 * controls enemies that move on player move, and controls player movement.
 */
public class Player extends Sprite {
		
	/**
	 * Constructor for the Player class.
	 * @param x			The x position.
	 * @param y			The y position.
	 */
	public Player(int x, int y) {
		super("res/player_left.png", "player", x, y, true);
	}

	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	@Override
	public void update(Input input, int delta) {
		
		// Takes input and sets the direction of the player sprite.
		int dir = DIR_NONE;
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dir = DIR_LEFT;
		}
		else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dir = DIR_RIGHT;
		}
		else if (input.isKeyPressed(Input.KEY_UP)) {
			dir = DIR_UP;
		}
		else if (input.isKeyPressed(Input.KEY_DOWN)) {
			dir = DIR_DOWN;
		}
		
		if (dir != DIR_NONE) {
			World.increaseMoveCount();
			

			// Push each sprite in the current map to their respective history stacks. Separate case for ice blocks
			// so that undoing while they are moving has consistent behaviour.
			for (Sprite sprite : World.sprites) {
				SpriteHistory currentState = null;
				
				if (compareSprite(sprite, "ice")) {
					currentState = new SpriteHistory(sprite.getX(), sprite.getY(), sprite.getIsSolid(),
													sprite.getDoRender(), sprite.getIsActive(), DIR_NONE);
				} else {
					currentState = new SpriteHistory(sprite.getX(), sprite.getY(), sprite.getIsSolid(),
											sprite.getDoRender(), sprite.getIsActive(), sprite.getDirection());
				}
				
				// Pushes to stack after checks are made.
				sprite.pushHistoryStack(currentState);
			}
			
			
			// Sets the rogue and mage sprites to active when the player moves.
			for (Sprite sprite : World.sprites) {
				if (compareSprite(sprite, "rogue")) {
					sprite.setIsActive(true);
				}
				if (compareSprite(sprite,"mage")) {
					sprite.setIsActive(true);
				}
			}
		}
		
		// Move to our destination
		moveToDest(dir);
	}
}
