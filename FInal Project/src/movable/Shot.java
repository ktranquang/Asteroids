package movable;

//this class represents a shot from a spaceship
public class Shot extends MovableObject {

	private static final long serialVersionUID = 1L;
	int shotLife;
	double angle;
	private int type;
	private int player;

	// constructor
	public Shot(double x, double y, double angle, int shotLife, int type) {
		super(x, y, 12 * Math.sin(angle), 12 * Math.cos(angle), 1);
		this.shotLife = shotLife;
		this.angle = angle;
		setType(type);
	}

	// returns the lifespan of this shot
	public int getShotLife() {
		return shotLife;
	}

	// moves the shot the required amount for 1 frame
	public void moveShot(int widthScreen, int heightScreen) {
		shotLife--;

		offset(-getVelocity().getX(), -getVelocity().getY());

		if (getX() < 0) {
			offsetX(widthScreen);
		} else if (getX() > widthScreen) {
			offsetX(-widthScreen);
		}
		if (getY() < 0) {
			offsetY(heightScreen);
		} else if (getY() > heightScreen) {
			offsetY(-heightScreen);
		}
	}

	// returns the angle that the shot is heading
	public double getTheta() {
		return angle;
	}

	// returns the type of shot
	public int getType() {
		return type;
	}

	// sets the type of shot to the specified number
	public void setType(int number) {
		this.type = number;
	}

	// sets the player to either firstPlayer or secondPlayer so it knows which
	// player shot
	public void setPlayer(int player) {
		this.player = player;
	}

	// returns either player 1 or 2
	public int getPlayer() {
		return player;
	}
}



