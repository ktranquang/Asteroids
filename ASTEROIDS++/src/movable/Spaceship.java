package movable;

// This class represents a spaceship object which could either be a player, or a UFO
// spaceships can shoot, get power ups, and take damage
public class Spaceship extends MovableObject {

	private static final long serialVersionUID = 1L;
	public double theta;
	public double xVelocity;
	public double yVelocity;
	double acceleration;
	double deceleration;
	double rotationalSpeed;
	public static int shotDelay;
	int shotDelayLeft = 0;
	boolean turningRight = false;
	boolean turningLeft = false;
	boolean accelerating = false;
	double ufoAng = 0;
	private int shotLevel;
	private int playerNumber;
	private int score = 0;
	private int numShotsHit = 0;
	private int numShotsTaken = 0;

	// constructor
	public Spaceship(double x, double y, double radius, double theta,
			double acceleration, double deceleration, double rotationalSpeed,
			int shotDelay) {
		super(x, y, 0, 0, radius);
		this.theta = theta;
		this.ufoAng = theta;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.rotationalSpeed = rotationalSpeed;
		this.shotDelay = shotDelay;
		setShotLevel(0);
	}

	// increments the variable that counts how many shots from this spaceship
	// have successfully hit an enemy
	public void shotHit() {
		numShotsHit++;
	}

	// returns the spaceship's accuracy as an int from 0 to 100
	public int getAccuracy() {
		if (numShotsTaken == 0) {
			return 0;
		}
		return (int) (((double) numShotsHit / numShotsTaken) * 100);
	}

	// returns the current angle of the UFO spaceship
	public double getUfoAng() {
		return ufoAng;
	}

	// sets the ufo angle to the specified angle
	public void setUfoAng(double ufoAng) {
		this.ufoAng = ufoAng;
	}

	// creates a Shot object that is tied to this spaceship with a flag
	public Shot shoot() {
		this.numShotsTaken++;
		shotDelayLeft = shotDelay;
		Shot shot = new Shot(getX(), getY(), theta, 40, getShotLevel());
		shot.setPlayer(this.getPlayerNumber());
		return shot;
	}

	// creates a Shot object moving in the direction of theta
	public Shot shoot(double theta) {
		shotDelayLeft = shotDelay;
		return new Shot(getX(), getY(), theta, 40, getShotLevel());
	}

	// returns an int representing what type of shots the spaceship currently
	// has
	public int getShotLevel() {
		return shotLevel;
	}

	// changes the shot level to the specified level
	public void setShotLevel(int level) {
		this.shotLevel = level;
	}

	// moves the spaceship for 1 frame
	public void move(int scrnWidth, int scrnHeight) {
		if (shotDelayLeft > 0) {
			shotDelayLeft--;
		}
		if (turningRight) {
			theta -= rotationalSpeed;
		}
		if (turningLeft) {
			theta += rotationalSpeed;
		}
		if (theta > 2 * Math.PI) {
			theta = theta % (2 * Math.PI);
		} else if (theta < 0) {
			theta = (theta % (2 * Math.PI))/* + (2 * Math.PI) */;
		}
		if (accelerating) {
			xVelocity -= acceleration * Math.sin(theta);
			yVelocity -= acceleration * Math.cos(theta);
		}

		offsetX(xVelocity);
		offsetY(yVelocity);

		// slow down ship
		if (deceleration != 0) {
			xVelocity = xVelocity * deceleration;
			yVelocity = yVelocity * deceleration;
		}

		if (getX() < 0) {
			offsetX(scrnWidth);
		} else if (getX() > scrnWidth) {
			offsetX(-scrnWidth);
		}

		if (getY() < 0) {
			offsetY(scrnHeight);
		} else if (getY() > scrnHeight) {
			offsetY(-scrnHeight);
		}
	}

	// starts turning the spaceship to the right
	public void setTurningRight(boolean turningRight) {
		this.turningRight = turningRight;
	}

	// starts turning the spaceship to the left
	public void setTurningLeft(boolean turningLeft) {
		this.turningLeft = turningLeft;
	}

	// starts accelerating
	public void setAccelerating(boolean accelerating) {
		this.accelerating = accelerating;
	}

	// returns whether the spaceship is accelerating or not
	public boolean isAccelerating() {
		return accelerating;
	}

	// returns the angle of the spaceship
	public double getTheta() {
		return theta;
	}

	// sets the angle of the spaceship
	public void setTheta(double theta) {
		this.theta = theta;
	}

	// sets the velocity of the spaceship
	public void setVelocity(double x, double y) {
		this.xVelocity = x;
		this.yVelocity = y;
	}

	// returns whether the spaceship is able to shoot
	public boolean canShot() {
		if (shotDelayLeft > 0) {
			return false;
		}
		return true;
	}

	// returns either player 1 or player 2
	public int getPlayerNumber() {
		return playerNumber;
	}

	// sets the player number to either 1 or 2
	public void setPlayerNumber(int number) {
		this.playerNumber = number;
	}

	// returns the current score
	public int getScore() {
		return score;
	}

	// sets the score to the specified score
	public void setScore(int score) {
		this.score = score;
	}

	// add the specified points to the current score
	public void addScore(int points) {
		setScore(getScore() + points);
	}
}
