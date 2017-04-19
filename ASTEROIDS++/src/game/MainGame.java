package game;

import static java.lang.Math.PI;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import movable.Asteroid;
import movable.MovableObject;
import movable.PowerUp;
import movable.Shot;
import movable.Spaceship;

public class MainGame extends Applet implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread thread; // used to run the game
	private Image img; // the back buffer
	private Graphics g;
	private long endTime, startTime, framePeriod;

	private Spaceship firstPlayer;
	private Spaceship secondPlayer;
	private ArrayList<Spaceship> UFOs;

	private ArrayList<Shot> shots;
	private ArrayList<Shot> ufoShots;
	private static ArrayList<Asteroid> asteroids;
	private static ArrayList<PowerUp> powerUps;
	private Randomizer r;

	private int numAsteroids; // the number of asteroids currently in the array
	private double astRadius;
	private int astNumHits, astNumSplit;
	private static int level; // the current level number
	private DrawGraphics graphics;
	private int user = 0;
	private int hyperCounter;
	private int UFOlife = 3; // set to 0 after

	private float targetAng;

	private boolean booster = false;
	private boolean split = false;
	private boolean mouse = false;
	private int shieldValue = 0;
	private int p2shieldValue = 0;

	// Player 1
	private static int shotLevelp1 = 0; // Basic shot///////////////////////
	private int shot3Ammo = 0;
	private int previousShotLevel;
	private int shot3Ammo2 = 0;
	private int p2previousShotLevel;

	private double rand;

	private int BIG_POINTS = 25; // Points for shooting different objects.
	private int MED_POINTS = 50;
	private int SMALL_POINTS = 75;
	private int UFO_POINTS = 250;
	private int MISSLE_POINTS = 500;

	private int NEW_SHIP_POINTS = 5000; // Number of points needed to earn
												// a new ship.
	private int NEW_UFO_POINTS = 2750; // Number of points between flying
											// saucers

	// Game data.
	private int highScore;
	private int newShipScore;
	private int p2newShipScore;
	private int newUfoScore;
	private int lives = 3;
	private  int livesCounter = 3;
	private int p2Lives = 3;
	private int p2LivesCounter = 3;

	private boolean loaded = false;
	private boolean gamePaused;
	private boolean showMainMenu;
	private boolean showHelpMenu;
	private boolean showPauseMenu;
	private boolean Player1Shooting;
	private boolean Player2Shooting;
	private boolean multiPlayer;
	private boolean gameOver;
	private boolean sound;
	private boolean detail;

	// Font data.

	private Font font = new Font("Helvetica", Font.BOLD, 12);
	private FontMetrics fm;
	private int fontWidth;
	private int fontHeight;

	private Explosion explosion = new Explosion();
	private Explosion Astexplosion = new Explosion();
	private Explosion Astexplosion2 = new Explosion();

	private Dimension appletSize;
	// Explosion Coordinates
	private double boomX;
	private double boomY;
	private double boomX2;
	private double boomY2;
	private double boomAstX;
	private double boomAstY;
	private double boomAstX1;
	private double boomAstY1;
	private double boomUFOX;
	private double boomUFOY;
	private double hitUFOX;
	private double hitUFOY;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int BUTTONS_WIDTH = 200;
	public static final int BUTTONS_HEIGHT = 50;
	public static final int HORIZONTAL_GAP = 80;

	// Sounds

	private AudioClip fire;
	private AudioClip doubleFire;
	private AudioClip doubleFire2;
	private AudioClip penFire;
	private  AudioClip boost;
	private AudioClip thrusters;
	private AudioClip hyper;
	private AudioClip astExplosion;
	private AudioClip shipCollision;
	private AudioClip shieldUp;
	private AudioClip shieldDown;
	private AudioClip shieldHit;
	private AudioClip laserPickup;
	private AudioClip penPickup;
	private AudioClip bg;
	private AudioClip pause;
	private AudioClip UFO;

	private Button singlePLayer;
	private Button multiPLayer;
	private Button help;
	private Button resume;
	private Button quit;
	private Button back;
	private Button mouseAiming;
	private EventHandler eventHandler;

	public ArrayList<PowerUp> getPowerUps() {
		return powerUps;
	}


	// initialize all variables and important methods before game starts
	public void init() {
		this.setSize(WIDTH, HEIGHT);
		loadSounds();
		setLevel(0); // will be incremented to 1 when first level is set up

		// Initialize variables
		numAsteroids = 10;
		astRadius = 60; // values used to create the asteroids
		astNumHits = 3;
		astNumSplit = 2;
		user = 0;
		shieldValue = 3;
		p2shieldValue = 3;
		shotLevelp1 = 0;
		shot3Ammo = 0;
		// Initialize game objects
		initializeGame();
		initializeInterface();
		// initialize main menu variables

		newShipScore = NEW_SHIP_POINTS;
		newUfoScore = NEW_UFO_POINTS;
		endTime = 0;
		startTime = 0;
		framePeriod = 25; // may be adjusted to change the speed that
		Player1Shooting = false;
		Player2Shooting = false;
		// the game runs.

		g = img.getGraphics();
		thread = new Thread(this); // create the thread that runs game
		thread.start(); // start the thread
	}

	// intialize buttons for main menu screen
	private void initializeInterface() {
		setLayout(null);
		singlePLayer = new Button("Single Player");
		multiPLayer = new Button("Multiplayer");
		help = new Button("Help");
		resume = new Button("Resume");
		quit = new Button("Quit");
		back = new Button("Back");
		mouseAiming = new Button("Mouse Aiming: off");

		// set properties of main menu components
		singlePLayer.addActionListener(eventHandler);
		multiPLayer.addActionListener(eventHandler);
		help.addActionListener(eventHandler);
		help.addActionListener(eventHandler);
		resume.addActionListener(eventHandler);
		quit.addActionListener(eventHandler);
		back.addActionListener(eventHandler);
		mouseAiming.addActionListener(eventHandler);
		singlePLayer.addMouseListener(eventHandler);
		help.setVisible(false);
		resume.setVisible(false);
		quit.setVisible(false);
		back.setVisible(false);

		// set position of components
		singlePLayer.setBounds((WIDTH - BUTTONS_WIDTH) / 2, 300, BUTTONS_WIDTH,
				BUTTONS_HEIGHT);
		multiPLayer.setBounds((WIDTH - BUTTONS_WIDTH) / 2, singlePLayer.getY()
				+ HORIZONTAL_GAP, BUTTONS_WIDTH, BUTTONS_HEIGHT);
		help.setBounds((WIDTH - BUTTONS_WIDTH) / 2, multiPLayer.getY()
				+ HORIZONTAL_GAP, BUTTONS_WIDTH, BUTTONS_HEIGHT);
		resume.setBounds((WIDTH - BUTTONS_WIDTH) / 2, 300, BUTTONS_WIDTH,
				BUTTONS_HEIGHT);
		quit.setBounds((WIDTH - BUTTONS_WIDTH) / 2, resume.getY()
				+ HORIZONTAL_GAP, BUTTONS_WIDTH, BUTTONS_HEIGHT);
		back.setBounds(0, 550, BUTTONS_WIDTH, BUTTONS_HEIGHT);
		mouseAiming.setBounds(0, 550, BUTTONS_WIDTH, BUTTONS_HEIGHT);

		// add components to the applet
		add(singlePLayer);
		add(multiPLayer);
		add(help);
		add(resume);
		add(quit);
		add(back);
		add(mouseAiming);
	}

	// initialize main objects of the game
	public void initializeGame() {
		gameOver = false;
		gamePaused = true;
		showMainMenu = true;
		multiPlayer = false;
		showHelpMenu = false;
		showPauseMenu = false;
		// create back buffer
		img = createImage(WIDTH, HEIGHT);
		UFOs = new ArrayList<Spaceship>();
		asteroids = new ArrayList<Asteroid>();
		shots = new ArrayList<Shot>();
		ufoShots = new ArrayList<Shot>();
		powerUps = new ArrayList<PowerUp>();
		// the line below creates a new spaceship object which represents first
		// player in the middle of the playing field

		firstPlayer = new Spaceship(WIDTH / 2 - 50, HEIGHT / 2, 19, 0, 0.23,
				0.98, 0.1, 12);
		firstPlayer.setPlayerNumber(1);
		secondPlayer = new Spaceship(WIDTH / 2 + 50, HEIGHT / 2, 19, 0, 0.23,
				0.98, 0.1, 12);
		secondPlayer.setPlayerNumber(2);

		eventHandler = new EventHandler(firstPlayer, secondPlayer, this);
		addKeyListener(eventHandler);
		addMouseListener(eventHandler);
		addMouseMotionListener(eventHandler);
		graphics = new DrawGraphics(img, firstPlayer, secondPlayer, shots,
				asteroids, UFOs, ufoShots, this, eventHandler);
	}

	// creates the specified number of asteroids in the playing field
	public void createAsteroids(int number) {
		for (int i = 0; i < number; i++) {
			Asteroid asteroid = new Asteroid(800, 600,
					graphics.getAsteroid1().getWidth() / 2, 0, 2, 2,
					Math.random() * 50, 1);
			Randomizer r = new Randomizer();
			asteroid.setVelocity(r.nextDouble(1, 5), r.nextDouble(1, 5));
			asteroids.add(asteroid);
		}
	}

	// creates the specified number of ufos in the playing field
	public void createUFOs(int number) {
		if (number > 0) {
			for (int i = 0; i < number; i++) {
				Spaceship ufo = new Spaceship(800, 600,
						graphics.getUfo().getWidth() / 2, Math.random() * 50, 0.23,
						0, 0.1, 12);

				Randomizer r = new Randomizer();
				ufo.setVelocity(r.nextDouble(-4, 4), r.nextDouble(-4, 4));
				UFOs.add(ufo);
			}
		}
	}

	// sets up the next level
	public void setUpNextLevel() {
		// increment level by 1
		setLevel(getLevel() + 1);

		// add spaceship
		if (getLevel() == 1) {
			firstPlayer.setLocation(400, 300);
		}

		// add asteroids
		asteroids.clear();
		createAsteroids(getLevel() + 3);

		// add UFOs
		UFOs.clear();
		// the UFO is too damn strong!
		// createUFOs(1);

		// clear shots
		shots.clear();
	}

	// load game sounds
	public void loadSounds() {
		try {
			fire = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "fire.wav"));
			doubleFire = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "dbLaserb.wav"));
			doubleFire2 = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "dbLaserr.wav"));
			penFire = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "penLaser.wav"));
			boost = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "boost.wav"));
			thrusters = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "thrusters.au"));
			hyper = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "hyper.wav"));
			astExplosion = getAudioClip(new URL(this.getClass()
					.getClassLoader().getResource("resources/"),
					"astExplosion.au"));
			shipCollision = getAudioClip(new URL(this.getClass()
					.getClassLoader().getResource("resources/"),
					"shipCollision.wav"));
			bg = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "bg.wav"));
			shieldUp = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "shieldUp.wav"));
			shieldDown = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "shieldDown.wav"));
			shieldHit = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "shieldHit.wav"));
			laserPickup = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "laserPickup.wav"));
			penPickup = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "penPickup.wav"));
			pause = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "pause.wav"));
			UFO = getAudioClip(new URL(this.getClass().getClassLoader()
					.getResource("resources/"), "UFO.wav"));
		} catch (MalformedURLException e) {
			System.out.println("Failed to load the sounds");
		}

		fire.play();
		fire.stop();
		doubleFire.play();
		doubleFire.stop();
		doubleFire2.play();
		doubleFire2.stop();
		penFire.play();
		penFire.stop();
		boost.play();
		boost.stop();
		thrusters.play();
		thrusters.stop();
		hyper.play();
		hyper.stop();
		astExplosion.play();
		astExplosion.stop();
		shipCollision.play();
		shipCollision.stop();
		bg.play();
		bg.stop();
		shieldUp.play();
		shieldUp.stop();
		shieldDown.play();
		shieldDown.stop();
		shieldHit.play();
		shieldHit.stop();
		laserPickup.play();
		laserPickup.stop();
		pause.play();
		pause.stop();
	}

	// paint the screen. this method is called at each frame
	@Override
	public void paint(Graphics gfx) {
		graphics.refreshGraphics(gfx);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString("test", 0, 0);
	}

	// starts the thread of the game
	public void run() {
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
				graphics.cursor, new Point(0, 0), "My Cursor");
		setCursor(cursor);
		// loadSounds();
		while (true) {
			startTime = System.currentTimeMillis();
			if (!gamePaused && !gameOver) {
				setFocusable(true);
				showPauseMenu(false);
				if (showMainMenu || showHelpMenu) {
					showMainMenu(false);
					showHelpMenu(false);
					showMainMenu = false;
					showHelpMenu = false;
				}

				// start next level if all asteroids have been destroyed
				if (asteroids.isEmpty()) {
					setUpNextLevel();
				}


				// move spaceships
				updateSpaceships();
				// move shots
				updateShots();
				// move asteroids
				updateAsteroids();
				// collision detection
				// updatePowerUps();
				// collisionDetection();
				// ufoCollisionDetection();
				shipCollisionDetection(firstPlayer);
				shipCollisionDetection(secondPlayer);
				shotCollisionDetection();
			} else if (showHelpMenu) {
				showPauseMenu(false);
				showMainMenu(false);
				showHelpMenu(true);
			} else if (showMainMenu) {
				showPauseMenu(false);
				showHelpMenu(false);
				showMainMenu(true);
			} else if (showPauseMenu && gamePaused) {
				showPauseMenu(true);
			}
			// CODE TO EXCUTE A FRAME OF THE GAME HERE
			repaint();

			// regulate the speed of the game
			try {
				endTime = System.currentTimeMillis();
				if (framePeriod - (endTime - startTime) > 0) {
					Thread.sleep(framePeriod - (endTime - startTime));
				}
			} catch (InterruptedException e) {

			}
			// Check score, update score, add new ship, or ufo ships
			if (firstPlayer.getScore() > highScore) {
				highScore = firstPlayer.getScore();
			}
			if (firstPlayer.getScore() > newShipScore) {
				newShipScore += NEW_SHIP_POINTS;
				lives++;
			}
			if (secondPlayer.getScore() > highScore) {
				highScore = secondPlayer.getScore();
			}
			if (secondPlayer.getScore() > newShipScore) {
				p2newShipScore += NEW_SHIP_POINTS;
				p2Lives++;
			}
			if (!isMultiPlayer()) {
				if (lives == 0) {
					gameOver = true;
				}
				
				if (multiPlayer) {
					if (lives == 0 && p2Lives == 0) {
						gameOver = true;
					}
				}

				// Add new UFO if score requirement is satisfied
				if (firstPlayer.getScore() > newUfoScore) {
					newUfoScore += NEW_UFO_POINTS;
					UFO.play();
					createUFOs(1);
					UFOlife = 5 + (level);
				}
				if (secondPlayer.getScore() > newUfoScore) {
					newUfoScore += NEW_UFO_POINTS;
					UFO.play();
					createUFOs(1);
					UFOlife = 5 + (level);
				}
			}
		}
	}

	// Collision detection for various entities
	private void shipCollisionDetection(Spaceship ship) {
		collisionDetection(ship, asteroids);
		collisionDetection(ship, ufoShots);
		collisionDetection(ship, UFOs);

		Iterator<PowerUp> iterator = powerUps.iterator();
		while (iterator.hasNext()) {
			PowerUp pu = iterator.next();

			if (firstPlayer.isCollision(pu)) {
				// remove power up
				iterator.remove();
				if (pu.getType() == 1) {
					laserPickup.play();
					if (firstPlayer.getShotLevel() == 0) {
						firstPlayer.setShotLevel(1);
					} else if (firstPlayer.getShotLevel() == 1) {
						firstPlayer.setShotLevel(2);
					}
				}
				// shield
				if (pu.getType() == 2) {
					shieldUp.play();
					shieldValue = 3;

				}
				// penetrating laser
				if (pu.getType() == 3) {
					penPickup.play();
					firstPlayer.setShotLevel(3);
					shot3Ammo += 20;
				}
			}

			// Player 2 power ups
			if (secondPlayer.isCollision(pu)) {
				// remove power up
				iterator.remove();
				if (pu.getType() == 1) {
					laserPickup.play();
					if (secondPlayer.getShotLevel() == 0) {
						secondPlayer.setShotLevel(1);
					} else if (secondPlayer.getShotLevel() == 1) {
						secondPlayer.setShotLevel(2);
					}
				}
				// shield
				if (pu.getType() == 2) {
					shieldUp.play();
					p2shieldValue = 3;

				}
				// penetrating laser
				if (pu.getType() == 3) {
					penPickup.play();
					secondPlayer.setShotLevel(3);
					shot3Ammo2 += 20;
				}
			}
		}
	}

	// Collision between ship and asteroids
	private void collisionDetection(Spaceship ship, ArrayList list) {
		Iterator<MovableObject> iterator = list.iterator();
		while (iterator.hasNext()) {
			MovableObject object = iterator.next();

			if (firstPlayer.isCollision(object)) {
				// remove object
				astExplosion.play();
				boomAstX1 = object.getX() - 25;
				boomAstY1 = object.getY() - 30;
				explosion.setAstActive2(true);
				iterator.remove();
				shipCollision();
			} else if (secondPlayer.isCollision(object)) {
				// remove object
				astExplosion.play();
				boomAstX1 = object.getX() - 25;
				boomAstY1 = object.getY() - 30;
				explosion.setAstActive2(true);
				iterator.remove();
				shipCollision2();
			}
		}
	}

	// Player 1 ship collision
	private void shipCollision() {
		if (shieldValue == 3 || shieldValue == 2) {
			shieldHit.play();
			shieldValue--;
		} else if (shieldValue == 1) {
			shieldDown.play();
			shieldValue--;
		} else if (shieldValue <= 0) {
			livesCounter--;
			shieldValue = 0;
			shipCollision.play();

			boomX = firstPlayer.getX()
					- (graphics.getExplosionFrames(0).getWidth(null) / 2 + 15);
			boomY = firstPlayer.getY()
					- (graphics.getExplosionFrames(0).getHeight(null) / 2 + 15);
			explosion.setActive(true);

			if (lives > 0) {
				lives--;
				firstPlayer.setLocation(WIDTH / 2, HEIGHT / 2);
				firstPlayer.setVelocity(0, 0);
				firstPlayer.setTheta(0);
				firstPlayer.setShotLevel(0);

				shot3Ammo = 0;
			}
		}
	}

	// Player 2 collision
	private void shipCollision2() {
		if (p2shieldValue == 3 || p2shieldValue == 2) {
			shieldHit.play();
			p2shieldValue--;
		} else if (p2shieldValue == 1) {
			shieldDown.play();
			p2shieldValue--;
		} else if (p2shieldValue <= 0) {
			p2LivesCounter--;
			p2shieldValue = 0;
			shipCollision.play();

			boomX2 = secondPlayer.getX()
					- (graphics.getExplosionFrames(0).getWidth(null) / 2 + 15);
			boomY2 = secondPlayer.getY()
					- (graphics.getExplosionFrames(0).getHeight(null) / 2 + 15);
			explosion.setActive2(true);

			if (p2Lives > 0) {

				p2Lives--;
				secondPlayer.setLocation(WIDTH / 2 + 50, HEIGHT / 2);
				secondPlayer.setVelocity(0, 0);
				secondPlayer.setTheta(0);
				secondPlayer.setShotLevel(0);

				shot3Ammo2 = 0;
			}
		}
	}

	// Collision between shots and asteroids
	private void shotCollisionDetection() {

		Iterator<Shot> shotIterator = shots.iterator();
		while (shotIterator.hasNext()) {
			Shot shot = shotIterator.next();

			ArrayList<Asteroid> tempAsteroids = new ArrayList<Asteroid>();

			// collision detection for shots and asteroids
			Iterator<Asteroid> asteroidIterator = asteroids.iterator();
			while (asteroidIterator.hasNext()) {
				Asteroid asteroid = asteroidIterator.next();

				if (shot.isCollision(asteroid)) {
					astExplosion.play();
					boomAstX = asteroid.getX() - 25;
					boomAstY = asteroid.getY() - 30;
					explosion.setAstActive(true);

					if (asteroid.getType() == 1) {
						if (shot.getPlayer() == 1) {
							firstPlayer.addScore(BIG_POINTS);
						}
						if (shot.getPlayer() == 2) {
							secondPlayer.addScore(BIG_POINTS);
						}
					}

					if (asteroid.getType() == 2) {
						if (shot.getPlayer() == 1) {
							firstPlayer.addScore(MED_POINTS);
						}
						if (shot.getPlayer() == 2) {
							secondPlayer.addScore(MED_POINTS);
						}
					}
					if (asteroid.getType() == 3) {
						if (shot.getPlayer() == 1) {
							firstPlayer.addScore(SMALL_POINTS);
						}
						if (shot.getPlayer() == 2) {
							secondPlayer.addScore(SMALL_POINTS);
						}
					}

					// penetrating laser
					if (shot.getPlayer() == 1) {
						if (firstPlayer.getShotLevel() != 3) {
							shotIterator.remove();
						}
						firstPlayer.shotHit();
					} else if (shot.getPlayer() == 2) {
						if (secondPlayer.getShotLevel() != 3) {
							shotIterator.remove();
						}
						secondPlayer.shotHit();
					}

					asteroid.takeShot(shot);
					if (!asteroid.areShotsLeft()) {
						// chance that a power up is spawned after an asteroid is
						// destroyed
						double rand = Math.random();
						if (rand < 0.02) {
							// penetrating laser cannon
							powerUps.add(new PowerUp(3, asteroid.getX(),
									asteroid.getY()));
						} else if (rand < 0.06 && rand > 0.02) {
							// shield
							powerUps.add(new PowerUp(2, asteroid.getX(),
									asteroid.getY()));
						} else if (rand < 0.120 && rand > 0.06) {
							// default dual laser
							powerUps.add(new PowerUp(1, asteroid.getX(),
									asteroid.getY()));
						}

						boomAstX1 = asteroid.getX() - 25;
						boomAstY1 = asteroid.getY() - 30;
						explosion.setAstActive2(true);
						asteroidIterator.remove();

						for (int i = 0; i < asteroid.getsplitNum(); i++) {
							Asteroid newAsteroid = new Asteroid(
									asteroid.getX(), asteroid.getY(),
									graphics.getAsteroid3().getWidth() / 2,
									Math.random() * 5, 1, 0,
									(double) Math.random(), 2);
							tempAsteroids.add(newAsteroid);
						}

					}
					break;
				}
			}
			asteroids.addAll(tempAsteroids);

			// collision detection for shots and asteroids
			Iterator<Spaceship> ufoIterator = UFOs.iterator();
			while (ufoIterator.hasNext()) {
				Spaceship ufo = ufoIterator.next();

				if (shot.isCollision(ufo)) {
					UFOlife--;
					boomUFOX = ufo.getX();
					boomUFOY = ufo.getY();
					shotIterator.remove();
					explosion.setUfoActiveHit(true);
					astExplosion.play();
					if (UFOlife == 0) {
						shipCollision.play();
						explosion.setUfoActive(true);
						ufoIterator.remove();
					}
				}
			}
		}
	}

	// move the spaceship depending on the user input
	private void updateSpaceships() {
		firstPlayer.move(WIDTH, HEIGHT);
		if (multiPlayer) {
			secondPlayer.move(WIDTH, HEIGHT);
		}
		for (Spaceship ufo : UFOs) {
			ufo.move(WIDTH, HEIGHT);
			if ((int) (Math.random() * 100) < 2) {
				// double theta = Math.random() * 2 * 3.1415926535;
				targetAng = (float) graphics.getTargetAngle(
						ufo.getX() - graphics.getUfo().getWidth() / 2,
						ufo.getY() - graphics.getUfo().getHeight() / 2,
						(float) firstPlayer.getX()
								- graphics.getPlayer1().getWidth() / 2,
						(float) firstPlayer.getY()
								- graphics.getPlayer1().getHeight() / 2);

				ufo.move(0, 0);
				ufoShots.add(ufo.shoot(-targetAng - PI / 2));
				fire.play();
			}
		}
	}

	// move asteroids around the screen
	private void updateAsteroids() {
		for (Asteroid asteroid : asteroids) {
			asteroid.move(WIDTH, HEIGHT);
		}
	}

	// move and destroy shots
	private void updateShots() {
		Iterator<Shot> iterator = shots.iterator();
		while (iterator.hasNext()) {
			Shot shot = iterator.next();
			shot.moveShot(WIDTH, HEIGHT);
			if (shot.getShotLife() <= 0) {
				iterator.remove();
			}
		}

		Iterator<Shot> ufoIterator = ufoShots.iterator();
		while (ufoIterator.hasNext()) {
			Shot shot = ufoIterator.next();
			shot.moveShot(WIDTH, HEIGHT);
			if (shot.getShotLife() <= 0) {
				ufoIterator.remove();
			}
		}

		if (Player1Shooting && firstPlayer.canShot()) {
			Shot temp = firstPlayer.shoot();
			shots.add(temp);

			if (firstPlayer.getShotLevel() == 0)
				fire.play();
			if (firstPlayer.getShotLevel() == 1)
				doubleFire.play();
			if (firstPlayer.getShotLevel() == 2)
				doubleFire2.play();
			if (firstPlayer.getShotLevel() == 3) {
				penFire.play();
				shot3Ammo--;
				if (shot3Ammo == 0)
					firstPlayer.setShotLevel(previousShotLevel);
			}
		}
		if (Player2Shooting && secondPlayer.canShot()) {
			Shot temp = secondPlayer.shoot();
			shots.add(temp);

			if (secondPlayer.getShotLevel() == 0)
				fire.play();
			if (secondPlayer.getShotLevel() == 1)
				doubleFire.play();
			if (secondPlayer.getShotLevel() == 2)
				doubleFire2.play();
			if (secondPlayer.getShotLevel() == 3) {
				penFire.play();
				shot3Ammo2--;
				if (shot3Ammo2 == 0)
					secondPlayer.setShotLevel(p2previousShotLevel);
			}
		}
	}

	// Display the main menu of the game
	private void showMainMenu(boolean show) {
		singlePLayer.setVisible(show);
		multiPLayer.setVisible(show);
		help.setVisible(show);
		mouseAiming.setVisible(show);
		showMainMenu = show;
	}

	// Display help menu of the game
	private void showHelpMenu(boolean show) {
		back.setVisible(show);
	}

	// Display pause menu
	private void showPauseMenu(boolean show) {
		resume.setVisible(show);
		quit.setVisible(show);
	}

	// //return true if the game is paused
	public boolean isGamePaused() {
		return gamePaused;
	}

	// return true if the help menu is being displayed
	public boolean isShowHelpMenu() {
		return showHelpMenu;
	}

	//
	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	// update the game graphics every frame
	@Override
	public void update(Graphics display) {
		paint(display);
	}

	public static int getShotLevelp1() {
		return shotLevelp1;
	}

	public void setShotLevelp1(int shotLevelp1) {
		MainGame.shotLevelp1 = shotLevelp1;
		if (shotLevelp1 == 3) {
			firstPlayer.setShotDelay(20);
		} else {
			firstPlayer.setShotDelay(12);
		}
	}

	public static int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	// true to display the pause menu; otherwise, false
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	// return true if main menu is being displayed
	public boolean isShowMainMenu() {
		return showMainMenu;
	}

	// true to display the main menu; otherwise, false
	public void setShowMainMenu(boolean showMainMenu) {
		this.showMainMenu = showMainMenu;
	}

	// return true if the player is shooting
	public boolean isPlayer1Shooting() {
		return Player1Shooting;
	}

	// set whether or not player1 is shooting
	public void setPlayer1Shooting(boolean shooting) {
		this.Player1Shooting = shooting;
	}

	// see whether or not player2 is shooting
	public boolean isPlayer2Shooting() {
		return Player2Shooting;
	}

	// set whether or not player2 is shooting
	public void setPlayer2Shooting(boolean player2Shooting) {
		Player2Shooting = player2Shooting;
	}

	// set to display or hide help menu
	public void setShowHelpMenu(boolean showHelpMenu) {
		this.showHelpMenu = showHelpMenu;
	}

	// return whether or not if pause menu is displayed
	public boolean isShowPauseMenu() {
		return showPauseMenu;
	}

	// set the boolean value for pause menu
	public void setShowPauseMenu(boolean showPauseMenu) {
		this.showPauseMenu = showPauseMenu;
	}

	// see whether or not multiplayer is active
	public boolean isMultiPlayer() {
		return multiPlayer;
	}

	// set multiplayer boolean value
	public void setMultiPlayer(boolean multiPlayer) {
		this.multiPlayer = multiPlayer;
	}

	// test to see if mouse aim is on or off
	public boolean isMouse() {
		return mouse;
	}

	// set mouse aim on or off
	public void setMouse(boolean mouse) {
		this.mouse = mouse;
	}

	// check to see if game is over or not
	public boolean isGameOver() {
		return gameOver;
	}

	// set string for mouse aim
	public void useMouseAiming(String string) {
		mouseAiming.setLabel(string);
	}

	public int getLives() {
		return lives;
	}

	public int getShieldValue() {
		return shieldValue;
	}

	public int getLivesCounter() {
		return livesCounter;
	}


	public int getP2Lives() {
		return p2Lives;
	}

	public int getP2LivesCounter() {
		return p2LivesCounter;
	}

	public int getP2shieldValue() {
		return p2shieldValue;
	}

	public int getShot3Ammo() {
		return shot3Ammo;
	}

	public int getShot3Ammo2() {
		return shot3Ammo2;
	}

	public double getBoomX() {
		return boomX;
	}

	public double getBoomY() {
		return boomY;
	}

	public double getBoomX2() {
		return boomX2;
	}

	public double getBoomY2() {
		return boomY2;
	}

	public double getBoomAstX() {
		return boomAstX;
	}

	public double getBoomAstY() {
		return boomAstY;
	}

	public double getBoomAstX1() {
		return boomAstX1;
	}

	public double getBoomAstY1() {
		return boomAstY1;
	}

	public double getBoomUFOX() {
		return boomUFOX;
	}

	public double getBoomUFOY() {
		return boomUFOY;
	}

	public AudioClip getBg() {
		return bg;
	}

	public boolean getGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void setP2Lives(int p2Lives) {
		this.p2Lives = p2Lives;
	}

	public void setShieldValue(int shieldValue) {
		this.shieldValue = shieldValue;
	}

	public void setP2ShieldValue(int p2ShieldValue) {
		this.p2shieldValue = p2ShieldValue;
	}

	public AudioClip getPause() {
		return pause;
	}

	public AudioClip getHyper() {
		return hyper;
	}

	public Spaceship getFirstPlayer() {
		return firstPlayer;
	}

	public void setLivesCounter(int livesCounter) {
		this.livesCounter = livesCounter;
	}

	public void setP2LivesCounter(int p2LivesCounter) {
		this.p2LivesCounter = p2LivesCounter;
	}

	public int getP2livesCounter() {
		return p2LivesCounter;
	}

	public AudioClip getBoost() {
		return boost;
	}

	public Spaceship getSecondPlayer() {
		return secondPlayer;
	}

	public AudioClip getShieldUp() {
		return shieldUp;
	}

	public AudioClip getShieldHit() {
		return shieldHit;
	}

	public AudioClip getShieldDown() {
		return shieldDown;
	}
}