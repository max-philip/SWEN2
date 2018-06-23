import org.newdawn.slick.Input;

/**
 * Handles the timing of the ice block movement and contains
 * the ice sprite information.
 */
public class Ice extends Sprite {
	
	/**
	 * Constructor for the Ice class.
	 * @param x				The x position.
	 * @param y				The y position.
	 */
	public Ice(int x, int y) {
		super("res/ice.png", "ice", x, y, true);
		
		// set the timer to 0.
		activeTime = 0;
		
		// is initially not active.
		isActive = false;
		
		// not initially active, so it has no direction
		dir = DIR_NONE;
	}
	
	private long activeTime;
	private boolean isActive;
	private int dir;
	
	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	@Override
	public void update(Input input, int delta) {
		
		// Continues moving while it is active.
		if (isActive) {
			activeTime += delta;
			
			// Moves once after 250ms (0.25 seconds)
			if (activeTime > 250){
				moveToDest(dir);
				
				// Reset the timer
				activeTime = 0;
			}
		}
	}

	/**
	 * Sets the direction of movement for the sprite.
	 * @param direction		The new direction.
	 */
	public void setDirection(int direction) {
		dir = direction;
	}
	
	/**
	 * Gets the direction of the sprites movement. Default is set to no
	 * direction.
	 * @return		The sprite direction.
	 */
	public int getDirection() {
		return this.dir;
	}
	
	/**
	 * Sets whether the sprite is currently active (in motion).
	 * @param active	Whether the sprite is in motion.
	 */
	public void setIsActive(boolean active) {
		isActive = active;
	}
	
	/**
	 * Gets whether the sprite is currently active (in motion).
	 * @return		Whether the sprite is in motion.
	 */
	public boolean getIsActive() {
		return isActive;
	}
	
}
