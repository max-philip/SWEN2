import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/** 
 * Main class for the game. Handles initialisation, input and rendering.
 * The code provided in the sample Project 1 was used as a base.
 * 
 * @author Max Philip
 */
public class App extends BasicGame
{
 	/** screen width, in pixels */
    public static final int SCREEN_WIDTH = 800;
    /** screen height, in pixels */
    public static final int SCREEN_HEIGHT = 600;
    /** size of the tiles, in pixels */
    public static final int TILE_SIZE = 32;
    
    /** create the world */
    private World world;
    
    /** source files of the game levels */
    private final String[] levels = {"res/levels/0.lvl",
    		"res/levels/1.lvl", "res/levels/2.lvl", "res/levels/3.lvl",
    		"res/levels/4.lvl", "res/levels/5.lvl"};
    
    /** current level, updated after a level is finished */
    private int curr_level = 0;
    
    /** Constructor for the App class. */
    public App()
    {    	
        super("Shadow Blocks");
    }
    
    /**
     * Initializes the game by starting the current level.
     * @param	gc	The Slick game container object.
     * @throws	SlickException
     */
    @Override
    public void init(GameContainer gc) throws SlickException {
    	startLevel();
    }
    
    /**
     * Starts level by creating a new world object at the current level.
     * @throws	SlickException
     */
    public void startLevel() throws SlickException {
    	world = new World(levels[curr_level]);
    }

    /**
     * Update the game state for a frame.
     * @param	gc		The Slick game container object.
     * @param 	delta 	Time passed since last frame (milliseconds).
     */
    @Override
    public void update(GameContainer gc, int delta)
    throws SlickException
    {
        // Get data about the current input (keyboard state).
        Input input = gc.getInput();
        world.update(input, delta);
        
        // Restart level if R key is pressed or the player dies.
        if (input.isKeyPressed(Input.KEY_R) || World.getPlayerDeath()) {
        	startLevel();
        }
        
        // Increment the current level and start it when a level is won.
        if (world.getGameWon()) {
        	curr_level += 1;
        	startLevel();
        }
    }

    /**
     * Render the entire screen, so it reflects the current game state.
     * @param 	gc	The Slick game container object.
     * @param 	g	The Slick graphics object, used for drawing.
     */
    public void render(GameContainer gc, Graphics g)
    throws SlickException
    {
    	world.render(g);
    }

    /**
     * Start-up method. Creates the game and runs it.
     * @param	args	Command-line arguments (ignored).
     */
    public static void main(String[] args)
    throws SlickException
    {
        AppGameContainer app = new AppGameContainer(new App());
        app.setShowFPS(false);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        app.start();
    }
}