import org.newdawn.slick.Input;

/**
 * Handles the updating of attacking enemies, timing of their movements, and how the
 * enemies hit and kill the player.
 */
public abstract class Attacker extends Sprite{
	
	/**
	 * Constructor for the Sprite class.
	 * @param image_src		Image source file.
	 * @param image_name	Name of the sprite image.
	 * @param x				The x position.
	 * @param y				The y position.
	 * @param solid			Whether the sprite is solid.
	 */
	public Attacker(String image_src, String image_name, int x, int y, boolean solid) {
		super(image_src, image_name, x, y, solid);
		
		// timing starts at 0.
		this.activeTime = 0;
		
		// initially has no direction.
		this.dir = DIR_NONE;
		
		// initially not moving.
		this.isActive = false;
	}
	
	protected int dir;
	protected long activeTime;
	protected boolean isActive;
	
	 /**
     * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
     * @param 	delta 	Time passed since last frame (milliseconds).
     */
	@Override
	public void update(Input input, int delta) {
	}
	
	/**
	 * Checks whether the enemy has hit the player on the y axis.
	 * @return		Whether the player has been hit.
	 */
	public boolean checkHitPlayerY() {
		for (Sprite sprite : World.sprites) {
			if (compareSprite(sprite, "player")) {
				if ((sprite.getX() == this.getX()) &&
						((sprite.getY() == this.getY() + SPEED) ||
								(sprite.getY() == this.getY() - SPEED))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/** Changes the direction of movement on the y axis. */
	public void changeDirectionY() {
		if (dir == DIR_UP) {
			dir = DIR_DOWN;
		} else if (dir == DIR_DOWN) {
			dir = DIR_UP;
		}
	}

	/**
	 * Checks whether the enemy has hit the player on the x axis.
	 * @return		Whether the player has been hit.
	 */
	public boolean checkHitPlayerX() {
		int speed = 32;
		for (Sprite sprite : World.sprites) {
			if (compareSprite(sprite, "player")) {
				if ((sprite.getY() == this.getY()) &&
						((sprite.getX() == this.getX() + speed) ||
								(sprite.getX() == this.getX() - speed))) {
					return true;
				}
			}
		}
		return false;
	}
	
	/** Changes the direction of movement on the y axis. */
	public void changeDirectionX() {
		if (dir == DIR_RIGHT) {
			dir = DIR_LEFT;
		} else if (dir == DIR_LEFT) {
			dir = DIR_RIGHT;
		}
	}
	
	/** Kills the player if an enemy and the player occupy the same space. */
	public void checkKillPlayer() {
		for (Sprite sprite : World.sprites) {
			if ((sprite.getX() == this.getX()) && (sprite.getY() == this.getY()) && (compareSprite(sprite, "player"))) {
				World.killPlayer();
			}
		}
	}
	
	/**
	 * Sets whether the sprite is currently active (in motion).
	 * @param active	Whether the sprite is in motion.
	 */
	public void setIsActive(boolean active) {
		this.isActive = active;
	}
	
	/**
	 * Sets the timing of an attacker.
	 * @param time		The new time.
	 */
	public void setActiveTime(long time) {
		this.activeTime = time;
	}
	
	/**
	 * Gets the current timing of an attacker.
	 * @return			The current time.
	 */
	public long getActiveTime() {
		return this.activeTime;
	}
	
	/**
	 * Sets the direction of movement for the sprite.
	 * @param direction		The new direction.
	 */
	public void setDirection(int direction) {
		this.activeTime = direction;
	}
	
	/**
	 * Gets the direction of the sprites movement.
	 * @return		The sprite direction.
	 */
	public int getDirection() {
		return this.dir;
	}
}


