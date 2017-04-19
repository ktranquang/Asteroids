package game;

import java.awt.*;


// class which animates various explosions
public class Explosion {
	private int numFrames = 23;
	private int currentFrame = 0;
	public static boolean active = false;
	public static boolean active2 = false;

	private int numAstFrames = 4;
	private int currentAstFrame = 0;
	public static boolean Astactive = false;
	public static boolean Astactive2 = false;
	public static boolean UFOactive = false;
	public static boolean UFOactiveHit = false;

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
			Astactive2 = false;
		}
	}

	// animation for UFO taking damage
	public void playUFOHit(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentAstFrame], (int) x, (int) y, null);
		currentAstFrame++;
		if (currentAstFrame >= numAstFrames) {
			currentAstFrame = 0;
			UFOactiveHit = false;
		}
	}

	// animation for when an asteroid takes damage
	public void playAstExplosion(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentAstFrame], (int) x, (int) y, null);
		currentAstFrame++;
		if (currentAstFrame >= numAstFrames) {
			currentAstFrame = 0;
			Astactive = false;
		}
	}

	// animation for when a UFO spaceship gets destroyed
	public void playUFOExplosion(Graphics g, Image[] frames, double x, double y) {
		g.drawImage(frames[currentFrame], (int) x, (int) y, null);
		currentFrame++;
		if (currentFrame >= numFrames) {
			currentFrame = 0;
			UFOactive = false;
		}
	}

}