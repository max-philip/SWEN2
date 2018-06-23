import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.Stack;
import org.newdawn.slick.Graphics;

/**
 * Controls the operations of the sprites that make up each game level. Handles
 * sprite movement, sprite history and rendering.
 * The code provided in the sample Project 1 was used as a base.
 */
public abstract class Sprite {

	// Used to decide what direction an object is moving
	public static final int DIR_NONE = 0;
	public static final int DIR_LEFT = 1;
	public static final int DIR_RIGHT = 2;
	public static final int DIR_UP = 3;
	public static final int DIR_DOWN = 4;
	
	// Distance moved by sprite in one movement (tile size).
	public static final int SPEED = 32;
	
	// Stack containing the past information of this sprite.
	private Stack<SpriteHistory> historyStack;
	
	// Sprite information
	private Image image = null;
	private int x;
	private int y;
	private String image_name = null;
	private boolean doRender;
	private boolean isSolid;
	private boolean isActive;
	
	/**
	 * Constructor for the Sprite class.
	 * @param image_src		Image source file.
	 * @param image_name	Name of the sprite image.
	 * @param x				The x position.
	 * @param y				The y position.
	 * @param solid			Whether the sprite is solid.
	 */
	public Sprite(String image_src, String image_name, int x, int y, boolean solid) {
		try {
			image = new Image(image_src);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.image_name = image_name;
		this.doRender = true;
		this.isSolid = solid;
		this.isActive = false;
		this.historyStack = new Stack<SpriteHistory>();
	}
	
	/**
	 * Update the game state for a frame.
	 * @param	input	Handles keyboard, mouse and controller input.
	 * @param	delta	Time passed since last frame (milliseconds).
	 */
	public void update(Input input, int delta) {
	}
	
	 /**
     * Render the entire screen, so it reflects the current game state.
     * @param 	g	The Slick graphics object, used for drawing.
     */
	public void render(Graphics g) {
		image.drawCentered(x, y);
	}
	
	/**
	 * Checks whether a sprite is a block.
	 * @param x			The x position.
	 * @param y			The y position.
	 * @param images	String array of image names to be compared with.
	 * @return			The sprite at the input location.
	 */
	public Sprite checkSprite(int x, int y, String[] images) {
		for (Sprite sprite : World.sprites) {
			if ((sprite.getX() == x) && (sprite.getY() == y)) {
				for (String image : images) {
					
					// Typecasts each sprite when returning.
					if (compareSprite(sprite, image)) {
						if (compareSprite(sprite, "ice")) {
							return (Ice) sprite;
						} else if (compareSprite(sprite, "tnt")) {
							return (Tnt) sprite;
						} else if (compareSprite(sprite, "stone")) {
							return (Stone) sprite;
						}
					}
				}
			}
		}
		return null;
	}
		
	/**
	 * Moves the sprite, returns true if a move is made, otherwise returns false
	 * @param dir	Direction of movement.
	 * @return		Whether a move is made.
	 */
	public boolean moveToDest(int dir) {
		// Translate the direction to an x and y displacement
		int delta_x = 0, delta_y = 0;
		switch (dir) {
			case DIR_LEFT:
				delta_x = -SPEED;
				break;
			case DIR_RIGHT:
				delta_x = SPEED;
				break;
			case DIR_UP:
				delta_y = -SPEED;
				break;
			case DIR_DOWN:
				delta_y = SPEED;
				break;
		}
		
		// Checks whether the location being moved into contains a block, and whether the sprite
		// that is moving can push blocks (player or rogue).
		Sprite pushed = checkSprite(this.getX()+delta_x, this.getY()+delta_y, Loader.blocks);
		if ((pushed != null) && (pushed.getIsSolid()) && ((compareSprite(this, "player"))
				|| (compareSprite(this, "rogue")))  && pushed.getIsSolid()) {
			
			// Case for the Ice block, continuous movement after a single push.
			if (compareSprite(pushed, "ice")) {
				pushed.setDirection(dir);
				pushed.setIsActive(true);
				pushed.moveToDest(dir);
			} else {
				pushed.moveToDest(dir);
			}
		}
		
		// Gets the adjacent sprite.
		Sprite nextSprite = null;
		for (Sprite sprite : World.sprites) {
			if ((sprite.getX() == (this.getX() + delta_x)) &&
				(sprite.getY()) == (this.getY() + delta_y)){
				nextSprite= sprite;
			}
		}
				
		// Makes sure the position isn't occupied.
		if (!Loader.isBlocked(this.getX() + delta_x, this.getY() + delta_y)) {
			this.setX(this.getX() + delta_x);
			this.setY(this.getY() + delta_y);
			return true;
		} else {
			
			// Completely stop the Ice block at any solid sprite except for the player.
			if (compareSprite(this, "ice") && !compareSprite(nextSprite, "player")) {
				this.setIsActive(false);
			}
			return false;
		}
	}
	
	/**
	 * Compares whether two sprites are of the same sprite type, returns true if 
	 * they are the same type.
	 * @param sprite	The input sprite.
	 * @param name		The name of the sprite used to compare.
	 * @return			Whether the sprites are the same type.
	 */
	public static boolean compareSprite(Sprite sprite, String name) {
		return sprite.image_name.equals(name);
	}
	
	/**
	 * Pushes the history object to the stack containing the sprite's history.
	 * @param history	The history object.
	 */
	public void pushHistoryStack(SpriteHistory history) {
		historyStack.push(history);
	}
	
	/**
	 * Pops the last element stored in the sprite's history stack, removing and returning
	 * the last element.
	 * @return		The popped element.
	 */
	public SpriteHistory popHistoryStack() {
		SpriteHistory last = historyStack.pop();
		return last;
	}
	
	/**
	 * Returns the last element of the sprite's history stack, without removing it.
	 * @return		The most recent sprite history object.
	 */
	public SpriteHistory peekHistoryStack() {
		return historyStack.peek();
	}
	
	
	/**
	 * Gets the x position of this sprite.
	 * @return		The x position.
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Sets a new x position for this sprite.
	 * @param newX		The new x position.
	 */
	public void setX(int newX) {
		this.x = newX;
	}
	
	/**
	 * Gets the y position of this sprite.
	 * @return		The y position.
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Sets a new y position for this sprite.
	 * @param newY		The new y position.
	 */
	public void setY(int newY) {
		this.y = newY;
	}
	
	/**
	 * Gets the name of the sprite's image.
	 * @return		The image name.
	 */
	public String getImageName() {
		return this.image_name;
	}
	
	/**
	 * Gets whether the sprite is covered by another sprite.
	 * @return		Whether the sprite is covered.
	 */
	public boolean getIsCovered() {
		return false;
	}
	
	/**
	 * Sets the sprite to be rendered when the map is updated.
	 * @param render	Whether the sprite will be rendered.
	 */
	public void setDoRender(boolean render) {
		this.doRender = render;
	}
	
	/**
	 * Gets whether the sprite is rendered on the map.
	 * @return		Whether the sprite is to be rendered.
	 */
	public boolean getDoRender() {
		return doRender;
	}
	
	/**
	 * Sets whether the sprite should be considered solid to other sprites.
	 * @param solid		If the sprite is considered solid.
	 */
	public void setIsSolid(boolean solid) {
		this.isSolid = solid;
	}
	
	/**
	 * Gets whether the sprite is solid for interaction with other sprites.
	 * @return			Whether the sprite is solid.
	 */
	public boolean getIsSolid() {
		return isSolid;
	}
	
	/**
	 * Sets the direction of movement for the sprite.
	 * @param direction		The new direction.
	 */
	public void setDirection(int direction) {
	}
	
	/**
	 * Gets the direction of the sprites movement. Default is set to no
	 * direction.
	 * @return		The sprite direction.
	 */
	public int getDirection() {
		return DIR_NONE;
	}
	
	/**
	 * Sets whether the sprite is currently active (in motion).
	 * @param active	Whether the sprite is in motion.
	 */
	public void setIsActive(boolean active) {
	}
	
	/**
	 * Gets whether the sprite is currently active (in motion).
	 * @return		Whether the sprite is in motion.
	 */
	public boolean getIsActive() {
		return isActive;
	}
}
