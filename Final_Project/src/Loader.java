import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The class that loads and contains the main level information. Creates the
 * sprite objects from information in the source files and stores them.
 * The code provided in the sample Project 1 was used as a base.
 */
public class Loader {
	
	// Values used to centre the level.
	private static int world_width;
	private static int world_height;
	private static int offset_x;
	private static int offset_y;
	
	/** String array containing the names of sprites that are fully solid */
	public static final String[] solids = {"wall", "stone", "player",
										"cracked", "tnt", "ice", "door"};
	
	/** String array containing the names of blocks that can be moved */
	public static final String[] blocks = {"stone", "ice", "tnt"};
	
	/** String array containing the names of tiles that can be passed */
	public static final String[] passable = {"floor", "target", "switch"};
	
	/**
	 * Only the player and the three types of blocks should be returned to the previous
	 * position when the undo button is pressed.
	 */
	public static final String[] undoable = {"player", "stone", "ice", "tnt"};
	
	/**
	 * Create the appropriate sprite given a name and location.
	 * @param name	The name of the sprite.
	 * @param x		The x position.
	 * @param y		The y position.
	 * @return		The sprite object.
	 */
	private static Sprite createSprite(String name, int x, int y) {
		switch (name) {
			case "player":
				return new Player(x, y);
			case "wall":
				return new Wall(x, y);
			case "floor":
				return new Floor(x, y);
			case "stone":
				return new Stone(x, y);
			case "target":
				return new Target(x, y);
			case "tnt":
				return new Tnt(x, y);
			case "cracked":
				return new Cracked(x, y);
			case "switch":
				return new Switch(x, y);
			case "door":
				return new Door(x, y);
			case "ice":
				return new Ice(x, y);
			case "mage":
				return new Mage(x, y);
			case "rogue":
				return new Rogue(x, y);
			case "skeleton":
				return new Skeleton(x, y);
			case "explosion":
				return new Explosion(x,y);
		}
		return null;
	}
		
	/**
	 * Converts a world coordinate to a tile coordinate, and returns if that
	 * location is a blocked tile.
	 * @param  x	The x position.
	 * @param  y	The y position.
	 * @return		Whether the position is blocked.
	 */
	public static boolean isBlocked(int x, int y) {
		
		// Continues past non-blocking sprite to ensure locations with two
		// or more sprites are checked.
		for (Sprite sprite : World.sprites) {
			if ((sprite.getX() == x) && (sprite.getY() == y)) {
				if (Arrays.asList(passable).contains(sprite.getImageName())
					|| !sprite.getIsSolid()) {
					continue;
				}
				if (sprite.getIsSolid()) {
					return true;
				}
			}
		}
		// Default to unblocked
		return false;
	}
		
	/**
	 * Loads the sprites from a given file.
	 * @param filename	The name of the level file.
	 * @return			ArrayList containing all Sprite objects made from the file.
	 */
	public static ArrayList<Sprite> loadSprites(String filename) {
		ArrayList<Sprite> list = new ArrayList<>();
		
		// Open the file
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			
			// Find the world size
			line = reader.readLine();
			String[] parts = line.split(",");
			world_width = Integer.parseInt(parts[0]);
			world_height = Integer.parseInt(parts[1]);
			
			// Calculate the top left of the tiles so that the level is centred
			offset_x = (App.SCREEN_WIDTH - world_width * App.TILE_SIZE) / 2;
			offset_y = (App.SCREEN_HEIGHT - world_height * App.TILE_SIZE) / 2;

			// Loop over every line of the file
			while ((line = reader.readLine()) != null) {
				String name;
				int x, y;
				
				// Split the line into parts
				parts = line.split(",");
				name = parts[0];
				x = Integer.parseInt(parts[1]);
				y = Integer.parseInt(parts[2]);
				
				// Adjust for the grid
				x = offset_x + x * App.TILE_SIZE;
				y = offset_y + y * App.TILE_SIZE;
				
				// Create the sprite
				Sprite newSprite = createSprite(name, x, y);
				list.add(newSprite);
			}
			
			// Create an explosion sprite in case the level requires an explosion. First
			// set to not be rendered.
			Sprite newSprite = createSprite("explosion", 0, 0);
			newSprite.setIsSolid(false);
			newSprite.setDoRender(false);
			list.add(newSprite);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
