package game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import movable.Asteroid;
import movable.PowerUp;
import movable.Shot;
import movable.Spaceship;
import static java.lang.Math.PI;

//this class will refresh the game screen every frame (around 25 times per second)
public class DrawGraphics extends MainGame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Font data.
	Font font = new Font("Helvetica", Font.BOLD, 12);
	FontMetrics fm;
	int fontWidth;
	int fontHeight;

	Image img;
	public BufferedImage player1;
	private BufferedImage player1Acc;
	public BufferedImage player2;
	private BufferedImage player2Acc;
	private BufferedImage help;
	BufferedImage accUserSpaceshipLeft1;
	BufferedImage accUserSpaceshipRight1;
	BufferedImage lives;
	BufferedImage p2lives;
	public BufferedImage asteroid1;
	public BufferedImage asteroid2;
	public BufferedImage asteroid3;
	BufferedImage laser;
	BufferedImage dbLaserb;
	BufferedImage dbLaserr;
	BufferedImage penLaser;
	BufferedImage shield33;
	BufferedImage shield23;
	BufferedImage shield13;
	BufferedImage shield03;
	public BufferedImage ufo;
	BufferedImage background1;
	BufferedImage background2;
	BufferedImage background3;
	BufferedImage background4;
	BufferedImage background5;
	private Explosion explosion;

	public BufferedImage shieldPick;
	BufferedImage laserPick;
	BufferedImage penLaserPick;
	private BufferedImage gameOver;

	public BufferedImage cursor;
	public Graphics2D g;
	Spaceship firstPlayer;
	private Spaceship secondPlayer;
	ArrayList<Shot> shots;
	ArrayList<Shot> ufoShots;
	ArrayList<Asteroid> asteroids;
	ArrayList<Spaceship> UFOs;
	public Image[] explosionFrames = new Image[23];
	public Image[] AstexplosionFrames = new Image[4];
	float targetAng;
	public MainGame main;

	// constructor of graphic class
	public DrawGraphics(Image img, Spaceship firstPlayer,
			Spaceship secondPlayer, ArrayList<Shot> shots,
			ArrayList<Asteroid> asteroids, ArrayList<Spaceship> UFOs,
			ArrayList<Shot> ufoShots, MainGame main) {
		this.img = img;
		g = (Graphics2D) img.getGraphics();
		this.main = main;
		this.firstPlayer = firstPlayer;
		this.secondPlayer = secondPlayer;
		this.shots = shots;
		this.asteroids = asteroids;
		this.UFOs = UFOs;
		this.ufoShots = ufoShots;
		this.explosion = new Explosion();
		loadImages();
	}

	// initialize all the images required for the game
	public void loadImages() {
		try {
			for (int i = 0; i < 23; i++) {
				explosionFrames[i] = loadImage("frame" + (i + 1) + ".png");
			}
			for (int i = 0; i < 4; i++) {
				AstexplosionFrames[i] = loadImage("aframe" + (i + 1) + ".png");
			}
			player1 = loadImage("Player1.png");
			player1Acc = loadImage("Player1Acc.png");
			player2 = loadImage("Player2.png");
			player2Acc = loadImage("Player2Acc.png");
		

			shield33 = loadImage("shield33.png");
			shield13 = loadImage("shield13.png");
			shield23 = loadImage("shield23.png");
			shield03 = loadImage("shield03.png");

			shieldPick = loadImage("shieldPick.png");
			laserPick = loadImage("laserPick.png");
			penLaserPick = loadImage("penLaserPick.png");

			lives = loadImage("lives.png");
			p2lives = loadImage("livesP2.png");
			ufo = loadImage("ufo.png");
			asteroid1 = loadImage("Asteroid1.png");
			asteroid2 = loadImage("Asteroid2.png");
			asteroid3 = loadImage("Asteroid3.png");
			laser = loadImage("laser.png");
			dbLaserr = loadImage("dbLaserr.png");
			dbLaserb = loadImage("dbLaserb.png");
			penLaser = loadImage("penLaser2.png");

			cursor = loadImage("cursor.png");

			background1 = loadImage("Background1.jpg");
			background2 = loadImage("Background2.jpg");
			background3 = loadImage("Background3.jpg");
			background4 = loadImage("background4.jpg");
			background5 = loadImage("background5.jpg");
			gameOver = loadImage("gameOver.png");
			help = loadImage("Help.png");
		} catch (Exception e) {
			System.out.println("Failed to load the image: ");
		}
	}

	// load the images of the game
	public BufferedImage loadImage(String name) {
		String test = this.getClass().getClassLoader()
				.getResource("resources/" ).getPath();
		System.out.println(test);
		try {
			URL url = this.getClass().getClassLoader()
					.getResource("resources/" + name);

			return ImageIO.read(new File(url.getFile()));
		} catch (IOException e) {
			System.out.println("Failed to load the image: " + name);
			return null;
		}

	}

	// this method gets class every frame. It will redraw all the objects of the
	// game:
	// Spaceships, Asteroids, UFO's, shots, drops, powers
	public void refreshGraphics(Graphics gfx) {
		g.fillRect(0, 0, 800, 600);
		g.setColor(Color.black);
		if (!main.isGamePaused()) {
			g.drawImage(background5, 0, 0, null); // main game screen
			if (firstPlayer.isAccelerating()) {
				drawSpaceship(firstPlayer, player1Acc);
			} else {
				drawSpaceship(firstPlayer, player1);
			}
			if (main.isMultiPlayer()) {
				if (secondPlayer.isAccelerating()) {
					drawSpaceship(secondPlayer, player2Acc);
				} else {
					drawSpaceship(secondPlayer, player2);
				}
			}
			drawShots(shots);
			drawShots(ufoShots);
			drawAsteroids(asteroids);
			drawUFOs(UFOs);
			drawPowerUps(powerUps);
			if (main.isGameOver()) {
				g.drawImage(gameOver, 0, 0, null);
			}
			g.setFont(font);
			g.setColor(Color.red);

			if (!main.isMultiPlayer()) {
				g.drawString("Score: " + firstPlayer.getScore(), 20, 40);
				g.drawString("Level: " + MainGame.getLevel(), 20, 20);

				g.drawImage(lives, null, WIDTH - 80, 5);
				g.drawString("x" + "    " + main.lives, WIDTH - 50, 20);
				g.drawString(
						"Accuracy: " + Math.round(firstPlayer.getAccuracy())
								+ "%", 20, 60);

				if (shieldValue == 3) {
					g.drawImage(shield33, null, WIDTH - 95, 30);
				}
				if (shieldValue == 2) {
					g.drawImage(shield23, null, WIDTH - 95, 30);
				}
				if (shieldValue == 1) {
					g.drawImage(shield13, null, WIDTH - 95, 30);
				}
				if (shieldValue == 0) {
					g.drawImage(shield03, null, WIDTH - 95, 30);
				}
			}
			// Multiplayer
			if (main.isMultiPlayer()) {
				g.drawString("Score: " + firstPlayer.getScore(), 12, 65);
				g.drawString(
						"Accuracy: " + Math.round(firstPlayer.getAccuracy())
								+ "%", 12, 85);
				if (main.livesCounter <= 0 && main.p2lives > 1) {

					g.drawString("PLAYER 1", 40, 570);
					g.drawString("Press C to continue", 12, 590);
				}

				g.drawString("Level: " + MainGame.getLevel(), WIDTH / 2, 20);

				g.drawImage(lives, null, WIDTH - 775, 5);
				g.drawString("x" + "    " + main.lives, WIDTH - 745, 20);

				if (shieldValue == 3) {
					g.drawImage(shield33, null, WIDTH - 790, 30);
				}
				if (shieldValue == 2) {
					g.drawImage(shield23, null, WIDTH - 790, 30);
				}
				if (shieldValue == 1) {
					g.drawImage(shield13, null, WIDTH - 790, 30);
				}
				if (shieldValue == 0) {
					g.drawImage(shield03, null, WIDTH - 790, 30);
				}

				g.drawString("Score: " + secondPlayer.getScore(), WIDTH - 92,
						65);
				g.drawString(
						"Accuracy: " + Math.round(secondPlayer.getAccuracy())
								+ "%", WIDTH - 92, 85);
				if (main.p2livesCounter <= 0 && main.lives > 1) {

					g.drawString("PLAYER 2", WIDTH - 90, 570);
					g.drawString("Press C to continue", WIDTH - 120, 590);
				}

				g.drawImage(p2lives, null, WIDTH - 80, 5);
				g.drawString("x" + "    " + main.p2lives, WIDTH - 50, 20);

				// accuracy p2
				if (p2shieldValue == 3) {
					g.drawImage(shield33, null, WIDTH - 95, 30);
				}
				if (p2shieldValue == 2) {
					g.drawImage(shield23, null, WIDTH - 95, 30);
				}
				if (p2shieldValue == 1) {
					g.drawImage(shield13, null, WIDTH - 95, 30);
				}
				if (p2shieldValue == 0) {
					g.drawImage(shield03, null, WIDTH - 95, 30);
				}
			}
			if (firstPlayer.getShotLevel() == 3 && shot3Ammo > 0) {
				g.setColor(Color.orange);
				g.drawString("Ammo: " + shot3Ammo,
						(int) firstPlayer.getX() - 25,
						(int) firstPlayer.getY() + 35);
			}
			if (secondPlayer.getShotLevel() == 3 && shot3Ammo2 > 0) {
				g.setColor(Color.orange);
				g.drawString("Ammo: " + shot3Ammo2,
						(int) secondPlayer.getX() - 25,
						(int) secondPlayer.getY() + 35);
			}

			if (Explosion.active) {
				explosion.playExplosion(g, explosionFrames, boomX, boomY);
			}
			if (Explosion.active2) {
				explosion.playExplosion2(g, explosionFrames, boomX2, boomY2);
			}
			if (Explosion.Astactive) {
				Astexplosion.playAstExplosion(g, AstexplosionFrames, boomAstX,
						boomAstY);
			}
			if (Explosion.Astactive2) {
				explosion.playAstExplosion2(g, explosionFrames, boomAstX1,
						boomAstY1);
			}
			if (Explosion.UFOactive) {
				explosion.playUFOExplosion(g, explosionFrames, boomUFOX,
						boomUFOY);
			}
			if (Explosion.UFOactiveHit) {
				explosion.playUFOHit(g, AstexplosionFrames, boomUFOX, boomUFOY);
			}
			gfx.drawImage(img, 0, 0, this);

		} else if (main.isShowHelpMenu()) {
			g.drawImage(help, 0, 0, null);
		} else {
			g.drawImage(background4, 0, 0, null);
		}
		gfx.drawImage(img, 0, 0, this);

		// ANGLE DATA for mouse aim
		targetAng = (float) getTargetAngle(
				firstPlayer.getX() - player1.getWidth() / 2 + 3,
				firstPlayer.getY() - player1.getHeight() / 2,
				EventHandler.mouseX, EventHandler.mouseY);


		if (main.isMouse()) {
			firstPlayer.setTheta(-targetAng - PI / 2);
		}

	}


	// paint shoots in the screen
	public void drawShots(ArrayList<Shot> shots) {
		Iterator<Shot> iterator = shots.iterator();
		while (iterator.hasNext()) {
			drawShot(iterator.next());
		}
	}

	// paint asteroids in the screen
	public void drawAsteroids(ArrayList<Asteroid> asteroids) {
		for (int i = 0; i < asteroids.size(); i++) {
			drawAsteroid(asteroids.get(i));
		}
	}

	// draw power ups
	public void drawPowerUps(ArrayList<PowerUp> powerUps) {
		for (int i = 0; i < powerUps.size(); i++) {
			drawPowerUp(powerUps.get(i));
		}
	}

	// paint UFO's in the screen
	public void drawUFOs(ArrayList<Spaceship> UFOs) {
		for (Spaceship ufo : UFOs) {
			drawUFO(ufo);
		}
	}

	// paint power ups in the screen
	public void drawPowerUp(PowerUp powerup) {
		BufferedImage image = laserPick;
		if (powerup.getType() == 1) {
			image = laserPick;
		}
		if (powerup.getType() == 2) {
			image = shieldPick;
		}
		if (powerup.getType() == 3) {
			image = penLaserPick;
		}

		g.drawImage(image, null, (int) powerup.getX(), (int) powerup.getY());
	}

	// paint spaceships in the screen
	public void drawSpaceship(Spaceship spaceship, BufferedImage player) {

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(spaceship.getX() - player1.getWidth() / 2,
				spaceship.getY() - player1.getHeight() / 2);
		affineTransform.rotate(-spaceship.getTheta(), player1.getWidth() / 2,
				player1.getHeight() / 2);
		g.drawImage(player, affineTransform, this);
	}

	// paint UFO's in the screen
	public void drawUFO(Spaceship ufo) {
		double ufoAng = ufo.getUfoAng() + 0.1;
		ufo.setUfoAng(ufoAng);

		AffineTransform affineTransform = new AffineTransform();
		affineTransform.translate(ufo.getX() - this.ufo.getWidth() / 2,
				ufo.getY() - this.ufo.getHeight() / 2);
		affineTransform.rotate(-ufoAng, this.ufo.getWidth() / 2,
				this.ufo.getHeight() / 2);
		g.drawImage(this.ufo, affineTransform, this);
	}

	// paint shoots in the screen
	private void drawShot(Shot shot, BufferedImage image) {
		AffineTransform affineTransform = new AffineTransform();

		firstPlayer.setShotDelay(12);
		affineTransform.translate(shot.getX() - image.getWidth() / 2,
				shot.getY() - image.getHeight() / 2);
		affineTransform.rotate(-shot.getTheta(), image.getWidth() / 2,
				image.getHeight() / 2);

		g.drawImage(image, affineTransform, this);
	}

	// paint shoots in the screen
	public void drawShot(Shot shot) {
		if (shot.getType() == 0) {
			drawShot(shot, laser);
		} else if (shot.getType() == 1) {
			drawShot(shot, dbLaserb);
		} else if (shot.getType() == 2) {
			drawShot(shot, dbLaserr);
		} else if (shot.getType() == 3) {
			AffineTransform affineTransform = new AffineTransform();
			firstPlayer.setShotDelay(20);
			affineTransform.translate(shot.getX() - penLaser.getWidth() / 2,
					shot.getY() - penLaser.getHeight() / 2);
			affineTransform.rotate(-shot.getTheta(), penLaser.getWidth() / 2,
					penLaser.getHeight() / 2);

			g.drawImage(penLaser, affineTransform, this);
		}
	}

	// paint Asteroids in the screen
	public void drawAsteroid(Asteroid asteroid) {
		if (asteroid.getType() == 1) {
			drawAsteroid(asteroid, asteroid1);
		}
		if (asteroid.getType() == 2) {
			drawAsteroid(asteroid, asteroid3);
		}
		if (asteroid.getType() == 3) {
			drawAsteroid(asteroid, asteroid2);
		}
	}

	// paint Asteroids in the screen
	public void drawAsteroid(Asteroid asteroid, BufferedImage image) {
		if (asteroid != null) {

			double astAng = asteroid.getAstAng() + 0.01;
			asteroid.setAstAng(astAng);

			AffineTransform affineTransform = new AffineTransform();
			affineTransform.translate(asteroid.getX() - image.getWidth() / 2,
					asteroid.getY() - image.getHeight() / 2);

			affineTransform.rotate(-astAng, image.getWidth() / 2,
					image.getHeight() / 2);
			g.drawImage(image, affineTransform, this);
		}
	}

	// method is used to calculated the angle between a certain point and an
	// object. Mouse aim uses the value from this method
	public float getTargetAngle(double d, double e, float targetX, float targetY) {
		double dx = targetX - d;
		double dy = targetY - e;

		return (float) (Math.atan2(dy, dx));
	}

}
