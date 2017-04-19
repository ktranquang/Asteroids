package game;

import java.awt.*;


// class which animates various explosions
public class Explosion {
	private int numFrames = 23;
	private int currentFrame = 0;
    private boolean active = false;
	private boolean active2 = false;

	private int numAstFrames = 4;
	private int currentAstFrame = 0;
	private boolean astActive = false;

	public void setAstActive2(boolean astActive2) {
		this.astActive2 = astActive2;
	}

	private boolean astActive2 = false;
	private boolean ufoActive = false;
	private boolean ufoActiveHit = false;

	public  boolean getAstActive2() {
		return astActive2;
	}


	// explosion animation for ship collision
	public void playExplosion(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentFrame], (int) x, (int) y, null);
		currentFrame++;
		if (currentFrame >= numFrames) {
			currentFrame = 0;
			active = false;
		}
	}

	// player 2 explosion animation
	public void playExplosion2(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentFrame], (int) x, (int) y, null);
		currentFrame++;
		if (currentFrame >= numFrames) {
			currentFrame = 0;
			active2 = false;
		}
	}

	// asteroids explosion animation
	public void playAstExplosion2(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentFrame], (int) x, (int) y, null);
		currentFrame++;
		if (currentFrame >= numFrames) {
			currentFrame = 0;
			astActive2 = false;
		}
	}

	// animation for UFO taking damage
	public void playUFOHit(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentAstFrame], (int) x, (int) y, null);
		currentAstFrame++;
		if (currentAstFrame >= numAstFrames) {
			currentAstFrame = 0;
			ufoActiveHit = false;
		}
	}

	// animation for when an asteroid takes damage
	public void playAstExplosion(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentAstFrame], (int) x, (int) y, null);
		currentAstFrame++;
		if (currentAstFrame >= numAstFrames) {
			currentAstFrame = 0;
			astActive = false;
		}
	}

	// animation for when a UFO spaceship gets destroyed
	public void playUFOExplosion(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentFrame], (int) x, (int) y, null);
		currentFrame++;
		if (currentFrame >= numFrames) {
			currentFrame = 0;
			ufoActive = false;
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setActive2(boolean active2) {
		this.active2 = active2;
	}

	public void setAstActive(boolean astActive) {
		this.astActive = astActive;
	}

	public void setUfoActiveHit(boolean ufoActiveHit) {
		this.ufoActiveHit = ufoActiveHit;
	}

	public void setUfoActive(boolean ufoActive) {
		this.ufoActive = ufoActive;
	}

	public boolean getActive() {
		return active;
	}

	public boolean getActive2() {
		return active2;
	}

	public boolean getAstActive() {
		return astActive;
	}

	public boolean getUfoActive() {
		return ufoActive;
	}

	public boolean getUfoActiveHit() {
		return ufoActiveHit;
	}
}