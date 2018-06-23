
/** Contains target sprite information. When all targets are 
 * covered by a block, the win condition is satisfied.
 */
public class Target extends Coverable {
	
	/**
	 * Constructor for the Target class.
	 * @param x		The x position.
	 * @param y		The y position.
	 */
	public Target(int x, int y) {
		super("res/Target.png", "target", x, y, false);
		
		// initially uncovered.
		isCovered = false;
	}
	
}
