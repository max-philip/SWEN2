import org.newdawn.slick.Input;

/**
 * Handles the Explosion sprite information and updates to check
 * for contact with a cracked wall. Renders the explosion
 * sprite on contact.
 */
public class Explosion extends Sprite{
	
	/**
	 * Constructor for the Explosion class.
	 * @param x				The x position.
	 * @param y				The y position.
	 */
	public Explosion(int x, int y) {
		super("res/explosion.png", "explosion", x, y, false);
		
		// set the timer to 0.
		activeTime = 0;
		
		// is not initially active.
		isActive = false;
	}
	private long activeTime;
	private boolean isActive;
	
	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	@Override
	public void update(Input input, int delta) {
		
		// Set to render when active. Render for 400ms (0.4s).
		if (isActive) {
			setDoRender(true);
			activeTime += delta;
			if (activeTime > 400){
				activeTime = 0;
				isActive = false;
				setDoRender(false);
			}
		}
	}
	
	/**
	 * Sets whether the sprite is currently active (in motion).
	 * @param active	Whether the sprite is in motion.
	 */
	public void setIsActive(boolean active) {
		isActive = active;
	}

}