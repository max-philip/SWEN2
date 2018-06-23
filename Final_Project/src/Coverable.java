import org.newdawn.slick.Input;

/**
 * Handles the covering operations of sprites that are covered by other sprites 
 * to effect the game. These sprites are Switch and Target.
 */
public abstract class Coverable extends Sprite {
	
	/**
	 * Constructor for the Coverable class.
	 * @param image_src		Image source file.
	 * @param image_name	Name of the sprite image.
	 * @param x				The x position.
	 * @param y				The y position.
	 * @param solid			Whether the sprite is solid.
	 */
	public Coverable(String image_src, String image_name, int x, int y, boolean solid) {
		super(image_src, image_name, x, y, solid);
	}
	
	// True if the sprite is currently covered.
	protected boolean isCovered;
	
	/**
	 * Checks whether a sprite is currently has a block on it.
	 * @param sprite	The input sprite.
	 * @return			Whether the sprite is covered by a block.
	 */
	private boolean checkBlock(Sprite sprite) {
		boolean isBlock = false;
		for (String block : Loader.blocks) {
			if (compareSprite(sprite, block)) {
				isBlock = true;
			}
		}
		return isBlock;
	}
	
	/**
	 * Sets the sprite to be covered if it is covered by a block in the sprite
	 * ArrayList.
	 */
	private void makeCovered() {
		for (Sprite sprite : World.sprites) {
			if ((sprite.getX() == this.getX()) &&
					(sprite.getY() == this.getY()) && checkBlock(sprite)){
				isCovered = true;
				return;
			}
		}
		isCovered = false;
	}
	
	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	public void update(Input input, int delta) {
		makeCovered();
	}
	
	/**
	 * Gets whether the sprite is currently covered by a block.
	 * @return 		Whether the sprite is covered.
	 */
	public boolean getIsCovered() {
		return isCovered;
	}
}
