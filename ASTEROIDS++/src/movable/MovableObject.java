package movable;

import java.awt.geom.*;

//this is the super class for any object that needs a location, radius and velocity
//Asteroid, PowerUp, Shot, and Spaceship all inherit from this class
public class MovableObject extends Point2D.Double {
	private static final long serialVersionUID = 1L;
	private double radius;
	private Double location;
	private Double velocity;

	// creates a movableobject with the specified location, velocity, and radius
	public MovableObject(double x, double y, double xVel, double yVel,
			double radius) {
		setLocation(x, y);
		setVelocity(xVel, yVel);
		this.radius = radius;
	}

	// secondary constructor
	public MovableObject(Double location, Double velocity, double radius) {
		this.location = location;
		this.velocity = velocity;
		this.radius = radius;
	}

	// returns this object's X coordinate
	public double getX() {
		return location.getX();
	}

	// returns this object's Y coordinate
	public double getY() {
		return location.getY();
	}

	// returns the location of this object
	public Double getLocation() {
		return location;
	}

	// sets the location of this object to the specified coordinates
	public void setLocation(double x, double y) {
		location = new Point2D.Double(x, y);
	}

	// sets the X coordinate
	public void setX(double x) {
		location.setLocation(x, getY());
	}

	// sets the Y coordinate
	public void setY(double y) {
		location.setLocation(getX(), y);
	}

	// offsets the position by the specified amounts
	public void offset(double x, double y) {
		offsetX(x);
		offsetY(y);
	}

	// secondary offest function
	public void offset(Double point) {
		offsetX(point.getX());
		offsetY(point.getY());
	}

	// offset the X coordinate by the specified amount
	public void offsetX(double x) {
		setX(getX() + x);
	}

	// offset the Y coordinate by the specified amount
	public void offsetY(double y) {
		setY(getY() + y);
	}

	// returns the velocity of this object
	public Double getVelocity() {
		return velocity;
	}

	// sets the velocity to the specified values
	public void setVelocity(double x, double y) {
		if (velocity == null) {
			velocity = new Point2D.Double(x, y);
		} else {
			velocity.setLocation(x, y);
		}
	}

	// secondary velocity setter
	public void setVelocity(Double velocity) {
		if (velocity != null) {
			this.velocity = velocity;
		}
	}

	// offsets the velocity by the specified amounts
	public void offsetVelocity(double x, double y) {
		setVelocity(getVelocity().getX() + x, getVelocity().getY());
	}

	// calculates the distance between this object and the specified
	// MovableObject
	public double distance(MovableObject second) {
		return Math.sqrt(Math.pow(this.getX() - second.getX(), 2)
				+ Math.pow(this.getY() - second.getY(), 2));
	}

	// returns the radius of this object
	public double getRadius() {
		return radius;
	}

	// sets the radius to the specified radius
	public void setRadius(double radius) {
		this.radius = radius;
	}

	// returns whether a collision has occured between this object and the
	// specified object
	public boolean isCollision(MovableObject second) {
		double distance = distance(second);
		return (distance < getRadius() + second.getRadius());
	}
}
