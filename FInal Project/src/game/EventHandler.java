package game;

import java.awt.event.*;
import javax.swing.SwingUtilities;
import movable.Spaceship;

// class which takes care of user input to game
public class EventHandler extends MainGame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	boolean music = false;
	private Spaceship player1;
	private Spaceship player2;
	private MainGame main;
	public static int mouseX = 0;
	public static int mouseY = 0;
	Randomizer r = new Randomizer();

	// Passes information from the method into the variables of this class
	public EventHandler(Spaceship player1, Spaceship player2, MainGame main) {
		this.player1 = player1;
		this.player2 = player2;
		this.main = main;
	}

	// Checks which button is pressed and performs certain action associated
	// with the click
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Single Player")) {
			main.setGamePaused(false);
			bg.loop();
			music = true;

		} else if (e.getActionCommand().equals("Multiplayer")) {
			main.setMultiPlayer(true);
			main.setGamePaused(false);
			main.setMouse(false);
			bg.loop();
			music = true;
		} else if (e.getActionCommand().equals("Options")) {
			main.setShowMainMenu(false);
			main.setShowHelpMenu(true);
		}
		// listen for Options button
		else if (e.getActionCommand().equals("Help")) {
			main.setShowMainMenu(false);
			main.setShowHelpMenu(true);
		} else if (e.getActionCommand().equals("Back")) {
			main.setShowHelpMenu(false);
			main.setShowMainMenu(true);
		} else if (e.getActionCommand().equals("Resume")) {
			bg.loop();
			main.setGamePaused(false);
			main.setShowPauseMenu(false);
		} else if (e.getActionCommand().equals("Quit")) {
			bg.stop();
			gameOver = false;
			lives = 3;
			p2lives = 3;
			shieldValue = 3;
			p2shieldValue = 3;
			main.setShowPauseMenu(false);
			main.setShowMainMenu(true);
			main.initializeGame();

		} else if (e.getActionCommand().equals("Mouse Aiming: off")
				|| e.getActionCommand().equals("Mouse Aiming: on")) {
			if (e.getActionCommand().equals("Mouse Aiming: off")) {
				main.useMouseAiming(("Mouse Aiming: on"));
				main.setMouse(true);
			} else {
				main.useMouseAiming(("Mouse Aiming: off"));
				main.setMouse(false);
			}
		}
	}

	// perform certain things upon key pressed
	public void keyPressed(KeyEvent event) {
		// activate or deactivate pause menu
		if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (!main.isGamePaused()) {
				pause.play();
				bg.stop();
				main.setGamePaused(true);
				main.setShowPauseMenu(true);
			}
		}
		// IF MOUSE GAMEPLAY STYLE ACTIVATED, W,S, SPACEBAR, AND MOUSE
		if (main.isMouse()) {
			if (event.getKeyCode() == KeyEvent.VK_W) {
				player1.setAccelerating(true);
			} else if (event.getKeyCode() == KeyEvent.VK_S) {

				// Randomly generate new ship location
				hyper.play();
				main.firstPlayer.setLocation(r.nextDouble(0,800),(double)r.nextDouble(0,600));
			}
		} else {
			if (event.getKeyCode() == KeyEvent.VK_UP) {
				player1.setAccelerating(true);
			} else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
				player1.setTurningRight(true);
			} else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
				player1.setTurningLeft(true);
			} else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
				main.setPlayer1Shooting(true);
			} else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
					// Randomly generate new ship location
					hyper.play();
					main.firstPlayer.setLocation(r.nextDouble(0,800),(double)r.nextDouble(0,600));
			}
			// second player
			if (main.isMultiPlayer()) {
				if (main.livesCounter <= 0 && main.p2lives > 1) {
					if (event.getKeyCode() == KeyEvent.VK_C) {
						main.lives++;
						main.p2lives--;
						main.livesCounter = 1;

					}
				}
				if (main.p2livesCounter <= 0 && main.lives > 1) {
					if (event.getKeyCode() == KeyEvent.VK_C) {
						main.p2lives++;
						main.lives--;
						main.p2livesCounter = 1;

					}
				}
				if (event.getKeyCode() == KeyEvent.VK_W) {
					player2.setAccelerating(true);
					boost.play();
				} else if (event.getKeyCode() == KeyEvent.VK_D) {
					player2.setTurningRight(true);
				} else if (event.getKeyCode() == KeyEvent.VK_A) {
					player2.setTurningLeft(true);
				} else if (event.getKeyCode() == KeyEvent.VK_S){
					hyper.play();
					main.secondPlayer.setLocation(r.nextDouble(0,800),(double)r.nextDouble(0,600));
				}
				else if (event.getKeyCode() == KeyEvent.VK_SHIFT) {
					main.setPlayer2Shooting(true);
				}
			}
		}
		// UNIVERSAL COMMANDS
		if (event.getKeyCode() == KeyEvent.VK_B) {
			if (main.isMouse())
				main.setMouse(false);
			else {
				main.setMouse(true);
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_P) {
			setGamePaused(true);
		}
		if (event.getKeyCode() == KeyEvent.VK_M) // FIX
		{
			if (music = true) {
				bg.stop();
				music = false;
			} else {
				bg.loop();
			}
		}

		// SHIELD TEST
		if (event.getKeyCode() == KeyEvent.VK_Z) // FIX
		{
			MainGame.shieldUp.play();
			shieldValue = 3;
		}
		if (event.getKeyCode() == KeyEvent.VK_X) // FIX
		{
			if (shieldValue != 0) {
				MainGame.shieldHit.play();
				shieldValue--;
			}
			if (shieldValue == 0) {
				MainGame.shieldDown.play();
				shieldValue--;
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_N) {
			bg.loop();
		}

		// If ship is moving, play thrusters sound
		if (player1.isAccelerating()) {
			boost.play();
		}
	}

	// events which happen when key is released
	public void keyReleased(KeyEvent event) {
		if (main.isMouse()) {
			if (event.getKeyCode() == KeyEvent.VK_W) {
				player1.setAccelerating(false);
				boost.stop();
			}

			else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
				main.setPlayer1Shooting(false);
			}
		} else {
			if (event.getKeyCode() == KeyEvent.VK_UP) {
				player1.setAccelerating(false);
				boost.stop();
			} else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
				player1.setTurningRight(false);
			} else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
				player1.setTurningLeft(false);
			} else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
				main.setPlayer1Shooting(false);
			}
			if (main.isMultiPlayer()) {
				if (event.getKeyCode() == KeyEvent.VK_W) {
					player2.setAccelerating(false);
				} else if (event.getKeyCode() == KeyEvent.VK_D) {
					player2.setTurningRight(false);
				} else if (event.getKeyCode() == KeyEvent.VK_A) {
					player2.setTurningLeft(false);
				} else if (event.getKeyCode() == KeyEvent.VK_SHIFT) {
					main.setPlayer2Shooting(false);
				}
			}
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	// do certain actions upon mouse clicks
	public void mousePressed(MouseEvent e) {
		if (main.isMouse() && SwingUtilities.isLeftMouseButton(e)) {
			main.setPlayer1Shooting(true);
		}
	}

	// Events which occur for when mouse button is released
	public void mouseReleased(MouseEvent e) {
		main.setPlayer1Shooting(false);
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

}
