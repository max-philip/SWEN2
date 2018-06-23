import org.newdawn.slick.Input;

/**
 * Controls the updates of the mage sprite which determines movement and
 * whether it kills the player. Mage moves once on every player move, and
 * has its next move determined by Algorithm 1.
 */
public class Mage extends Attacker {
	
	/**
	 * Constructor for the Mage class.
	 * @param x				The x position.
	 * @param y				The y position.
	 */
	public Mage(int x, int y) {
		super("res/mage.png", "mage", x, y, false);
		
		// initially not moving.
		isActive = false;
	}

	 /**
     * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
     * @param 	delta 	Time passed since last frame (milliseconds).
     */
	@Override
	public void update(Input input, int delta) {		
		
		// Moves the mage while active, and checks if the player is
		// hit on every update.
		if (isActive) {
			isActive = false;
			moveToDest(nextMove());
		}
		checkKillPlayer();
	}
		
	/**
	 * The mage's next move is calculated using Algorithm 1.
	 * @return		The next direction of movement.
	 */
	private int nextMove() {
		int playerX = 0, playerY = 0, mageX = 0, mageY = 0;
		
		// Gets the x and y positions of both the player
		// and mage sprites.
		for (Sprite sprite : World.sprites) {
			if (compareSprite(sprite, "player")) {
				playerX = sprite.getX();
				playerY = sprite.getY();
			}
			if (compareSprite(sprite, "mage")) {
				mageX = sprite.getX();
				mageY = sprite.getY();
			}
		}
		
		// Calculates the direction based on position differences
		// between the units.
		
		int distX = Math.abs(playerX - mageX);
		int distY = Math.abs(playerY - mageY);
		int signX = (int) Math.signum(playerX - mageX);
		
		if (signX > 0) {
			if (distX > distY) {
				return DIR_RIGHT;
			} else {
				return DIR_UP;
			}
		} else{
			if (distX > distY) {
				return DIR_LEFT;
			} else {
				return DIR_DOWN;
			}
		}
	}
}
