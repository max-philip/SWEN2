import org.newdawn.slick.Input;

/**
 * Controls the updates of the skeleton sprite which determines movement and
 * whether it kills the player. Skeleton moves once every second, and moves
 * only on the y axis.
 */
public class Skeleton extends Attacker {
	
	/**
	 * Constructor for the Skeleton class.
	 * @param x				The x position.
	 * @param y				The y position.
	 */
	public Skeleton(int x, int y) {
		super("res/skull.png", "skeleton", x, y, false);
		
		// timing starts at 0.
		activeTime = 0;
		
		// moves on the y axis, initially going up
		dir = DIR_UP;
		
		// is always active, so initially active
		isActive = true;
	}
	
	/**
     * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
     * @param 	delta 	Time passed since last frame (milliseconds).
     */
	@Override
	public void update(Input input, int delta) {
		
		// Adds the time since the last frame on every update
		activeTime += delta;
		
		// Moves when 1000ms has passed (1 second), changes direction when
		// a move cannot be made.
		if (activeTime > 1000){
			if (!moveToDest(dir)) {
				changeDirectionY();
				if (checkHitPlayerY()) {
					World.killPlayer();
				}
			}
			// Resets the time after every move.
			activeTime = 0;
		}
		// Checks if the player is hit on every update.
		checkKillPlayer();
	}
}

