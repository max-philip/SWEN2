/** 
 * Contains switch sprite information.When the switch in a level is 
 * covered by a block, then the door is unlocked. The is removed 
 * from the game while the switch block is pressed, and comes back
 * after uncovering the switch.
 */
public class Switch extends Coverable {
	
	/**
	 * Constructor for the Switch class.
	 * @param x		The x position.
	 * @param y		The y position.
	 */
	public Switch(int x, int y) {
		super("res/switch.png", "switch", x, y, false);
		
		// initially uncovered.
		isCovered = false;
	}	
}
