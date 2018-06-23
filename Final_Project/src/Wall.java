/** Contains wall sprite information */
public class Wall extends Sprite {
	
	/**
	 * Constructor for the Wall class.
	 * @param x			The x position.
	 * @param y			The y position.
	 */
	public Wall(int x, int y) {
		super("res/wall.png", "wall", x, y, true);
	}
}
