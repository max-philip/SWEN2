/**
 * Contains the information stored as sprite history. Stores the
 * x position, y position, isSolid, doRender, isActive and
 * direction.
 */
public class SpriteHistory {
	
	// Information of past maps that is required.
	private int x;
	private int y;
	private boolean isSolid;
	private boolean doRender;
	private boolean isActive;
	private int dir;
	
	/**
	 * Constructor for the SpriteHistory class.
	 * @param x				The x position.
	 * @param y				The y position.
	 * @param solid			Whether the sprite is solid.
	 * @param render		Whether the sprite is rendered on the map.
	 * @param active		Whether the sprite is currently active.
	 * @param direction		The direction of movement.
	 */
	public SpriteHistory(int x, int y, boolean solid, boolean render, boolean active, int direction) {
		this.x = x;
		this.y = y;
		this.doRender = render;
		this.isSolid = solid;
		this.isActive = active;
		this.dir = direction;
	}
	
	/**
	 * Gets the x position.
	 * @return		The x position.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Gets the y position.
	 * @return		The y position.
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Gets whether the sprite is solid for interaction with other sprites.
	 * @return			Whether the sprite is solid.
	 */
	public boolean getIsSolid() {
		return this.isSolid;
	}
	
	/**
	 * Gets whether the sprite is to be rendered on the game map.
	 * @return			Whether the sprite is to be rendered.
	 */
	public boolean getDoRender() {
		return this.doRender;
	}
	
	/**
	 * Gets whether the sprite is currently active in the game.
	 * @return			Whether the sprite is active.
	 */
	public boolean getIsActive() {
		return this.isActive;
	}
	
	/**
	 * Gets the direction of the sprites movement. Default is set to no
	 * direction.
	 * @return		The sprite direction.
	 */
	public int getDirection() {
		return this.dir;
	}
}

