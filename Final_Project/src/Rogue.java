import org.newdawn.slick.Input;

/**
 * Controls the updates of the rogue sprite which determines movement and
 * whether it kills the player. Rogue moves once on every player move, and
 * moves only on the x axis.
 */
public class Rogue extends Attacker {
	
	/**
	 * Constructor for the Rogue class.
	 * @param x				The x position.
	 * @param y				The y position.
	 */
	public Rogue(int x, int y) {
		super("res/rogue.png", "rogue", x, y, false);
		
		// initially is not moving
		isActive = false;
		
		// moves on the x axis, starts going left
		dir = DIR_LEFT;
	}
	
	 /**
     * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
     * @param 	delta 	Time passed since last frame (milliseconds).
     */
	@Override
	public void update(Input input, int delta) {
		
		if (isActive) {
			
			// Changes direction if a move cannot be made.
			if (!moveToDest(dir)) {
				changeDirectionX();
				
				// Kills the player on hit.
				if (checkHitPlayerX()) {
					World.killPlayer();
				}
			}
			// Makes a single move when the player moves.
			isActive = false;
		}
		// Check if the player is hit on every update.
		checkKillPlayer();
	}
}