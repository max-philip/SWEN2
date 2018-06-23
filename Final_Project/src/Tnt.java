import org.newdawn.slick.Input;

/**
 * Handles the Tnt sprite information and updates to check
 * for contact with a cracked wall. Renders the explosion
 * sprite on contact.
 */
public class Tnt extends Sprite {
	
	/**
	 * Constructor for the Tnt class.
	 * @param x				The x position.
	 * @param y				The y position.
	 */
	public Tnt(int x, int y) {
		super("res/tnt.png", "tnt", x, y, true);
	}
	
	/**
	 * Checks if there is contact with a cracked wall on any of
	 * the tnt sprite's sides
	 * @return		Whether there is contact between the tnt and cracked sprites.
	 */
	private boolean checkNextToCracked() {
		
		boolean isNextToCracked = false;
		
		// Makes comparisons on all sides of the tnt sprite.
		for (Sprite sprite : World.sprites) {
			if ( (((sprite.getX() == this.getX() + SPEED) && (sprite.getY() == this.getY()))
				|| ((sprite.getX() == this.getX() - SPEED) && (sprite.getY() == this.getY()))
				|| ((sprite.getX() == this.getX()) && (sprite.getY() == this.getY() + SPEED))
				|| ((sprite.getX() == this.getX()) && (sprite.getY() == this.getY() - SPEED)))
					&& compareSprite(sprite, "cracked") ) {
				isNextToCracked = true;
			}
		}
		return isNextToCracked;
	}
	
	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	@Override
	public void update(Input input, int delta) {
		
		// Remove the cracked wall and TNT blocks from the game on collision,
		// and show an explosion on the TNT block's former position
		if (checkNextToCracked()) {
			for (Sprite sprite : World.sprites) {
				if (compareSprite(sprite, "cracked") && sprite.getIsSolid()) {
					
					// Set cracked wall to not be solid and not render
					sprite.setDoRender(false);
					sprite.setIsSolid(false);
				}
				if (compareSprite(sprite, "tnt") && sprite.getIsSolid()) {
					
					// Create an explosion at the TNT block's location
					Explosion explosion = (Explosion) World.sprites.get(World.sprites.size()-1);
					explosion.setX(sprite.getX());
					explosion.setY(sprite.getY());
					explosion.setIsActive(true);
					
					// Remove the TNT block from the map on explosion
					sprite.setDoRender(false);
					sprite.setIsSolid(false);
					sprite.setX(0);
					sprite.setY(0);
				}
			}
		}
	}
}