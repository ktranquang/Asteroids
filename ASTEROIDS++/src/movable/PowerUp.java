package movable;

//Class which allows for the implementation of power ups in the game. 
//The type in this class helps determine what to drop.
public class PowerUp extends MovableObject {
	private static final long serialVersionUID = 1L;
	private int type;

	// CONSTRUCTOR
	public PowerUp(int type, double x, double y) {
		super(x, y, 0, 0, 10);
		this.type = type;
	}

	// 1 --- laser
	// 2 --- penetrating laser
	// 2 --- shield
	// 3 --- bomb
	// returns the type of power up
	public int getType() {
		return type;
	}
}
