import org.newdawn.slick.Input;

/**
 * Contains door sprite information. Handles the checking of
 * switch coverage.
 */
public class Door extends Sprite {
	
	/**
	 * Constructor for the Door class.
	 * @param x			The x position.
	 * @param y			The y position.
	 */
	public Door(int x, int y) {
		super("res/door.png", "door", x, y, true);
	}
	
	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	@Override
	public void update(Input input, int delta) {
		
		// Iterates over the sprites to find the switch and checks if it is
		// covered. Does not render and sets the door to not solid if the
		// switch is not covered.
		for (Sprite sprite : World.sprites) {
			if (compareSprite(sprite, "switch")) {
				if (sprite.getIsCovered()) {
					this.setIsSolid(false);
					this.setDoRender(false);
				} else {
					this.setIsSolid(true);
					this.setDoRender(true);
				}
			}
		}	
	}
}
