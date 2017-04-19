package movable;

import java.util.Random;

//this class represents an asteroid which can be different sizes, shapes, and have different damage levels
public class Asteroid extends MovableObject {

	private static final long serialVersionUID = 1L;
	private int shotsLeft;
	private int splitNum;
	private double astAng = 0;
    private int type;

	// constructor
	public Asteroid(double x, double y, double radius, double vel,
			int shotsLeft, int splitNum, double angle, int type) {

		super(x, y, vel * Math.cos((new Random()).nextInt()), vel
				* Math.sin((new Random()).nextInt()), radius);
		this.shotsLeft = shotsLeft;
		this.splitNum = splitNum;
		this.astAng = angle;
		setType(type);
	}

	// returns the number of shots that the asteroid can take before being
	// destroyed
	public int getshotsLeft() {
		return shotsLeft;
	}

	// returns the number of asteroids this asteroid will split into
	public int getsplitNum() {
		return splitNum;
	}

	// returns whether this asteroid can take more shots or should be destroyed
	public boolean areShotsLeft() {
		return getshotsLeft() >= 0;
	}

	// takes damage from a shot
	public void takeShot(Shot shot) {
		shotsLeft -= shot.getType() + 1;
		System.out.println(shotsLeft);
	}

	// moves the asteroid the amount needed for 1 frame
	public void move(int scrnWidth, int scrnHeight) {
		offset(getVelocity());

		if (getX() < 0 - getRadius()) {
			offsetX((scrnWidth + 2 * getRadius()));
		} else if (getX() > scrnWidth + getRadius()) {
			offsetX(-(scrnWidth + 2 * getRadius()));
		}

		if (getY() < 0 - getRadius()) {
			offsetY((scrnHeight + 2 * getRadius()));
		} else if (getY() > scrnHeight + getRadius()) {
			offsetY(-(scrnHeight + 2 * getRadius()));
		}
	}

	// returns the type of asteroid
	public int getType() {
		return type;
	}

	// sets the type of asteroid to the specified number
	public void setType(int num) {
		type = num;
	}

	// returns the angle of the asteroid
	public double getAstAng() {
		return astAng;
	}

	// sets the angle of the asteroid to the specified angle
	public void setAstAng(double angle) {
		this.astAng = angle;
	}
}
