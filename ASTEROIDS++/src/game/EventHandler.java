package game;

import java.awt.event.*;
import javax.swing.SwingUtilities;
import movable.Spaceship;

// class which takes care of user input to game
public class EventHandler extends MainGame implements ActionListener,
		KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	private boolean music = false;
	private Spaceship player1;
	private Spaceship player2;
	private MainGame main;

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	private int mouseX = 0;
	private int mouseY = 0;
	private Randomizer r = new Randomizer();

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
			main.getBg().loop();
			music = true;

		} else if (e.getActionCommand().equals("Multiplayer")) {
			main.setMultiPlayer(true);
			main.setGamePaused(false);
			main.setMouse(false);
			main.getBg().loop();
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
			main.getBg().loop();
			main.setGamePaused(false);
			main.setShowPauseMenu(false);
		} else if (e.getActionCommand().equals("Quit")) {
			main.getBg().stop();
			main.setGameOver(false);
			main.setLives(3);
			main.setP2Lives(3);
			main.setShieldValue(3);
			main.setP2ShieldValue(3);
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
				main.getPause().play();
				main.getBg().stop();
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
				main.getHyper().play();
				main.getFirstPlayer().setLocation(r.nextDouble(0,800),(double)r.nextDouble(0,600));
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
					main.getHyper().play();
				main.getFirstPlayer().setLocation(r.nextDouble(0,800),(double)r.nextDouble(0,600));
			}
			// second player
			if (main.isMultiPlayer()) {
				if (main.getLivesCounter() <= 0 && main.getP2Lives() > 1) {
					if (event.getKeyCode() == KeyEvent.VK_C) {
						main.setLives(main.getLives()+1);
						main.setP2Lives(main.getP2Lives()-1);
						main.setLivesCounter(1);

					}
				}
				if (main.getP2livesCounter() <= 0 && main.getLives() > 1) {
					if (event.getKeyCode() == KeyEvent.VK_C) {
						main.setP2Lives(main.getP2Lives()+1);
						main.setLives(main.getLives()-1);
						main.setP2LivesCounter(1);

					}
				}
				if (event.getKeyCode() == KeyEvent.VK_W) {
					player2.setAccelerating(true);
					main.getBoost().play();
				} else if (event.getKeyCode() == KeyEvent.VK_D) {
					player2.setTurningRight(true);
				} else if (event.getKeyCode() == KeyEvent.VK_A) {
					player2.setTurningLeft(true);
				} else if (event.getKeyCode() == KeyEvent.VK_S){
					main.getHyper().play();
					main.getSecondPlayer().setLocation(r.nextDouble(0,800),(double)r.nextDouble(0,600));
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
				main.getBg().stop();
				music = false;
			} else {
				main.getBg().loop();
			}
		}

		// SHIELD TEST
		if (event.getKeyCode() == KeyEvent.VK_Z) // FIX
		{
			main.getShieldUp().play();
			main.setShieldValue(3);
		}
		if (event.getKeyCode() == KeyEvent.VK_X) // FIX
		{
			if (main.getShieldValue() != 0) {
				main.getShieldHit().play();
				main.setShieldValue(main.getShieldValue()-1);
			}
			if (main.getShieldValue() == 0) {
				main.getShieldDown().play();
				main.setShieldValue(main.getShieldValue()-1);
			}
		}
		if (event.getKeyCode() == KeyEvent.VK_N) {
			main.getBg().loop();
		}

		// If ship is moving, play thrusters sound
		if (player1.isAccelerating()) {
			main.getBoost().play();
		}
	}

	// events which happen when key is released
	public void keyReleased(KeyEvent event) {
		if (main.isMouse()) {
			if (event.getKeyCode() == KeyEvent.VK_W) {
				player1.setAccelerating(false);
				main.getBoost().stop();
			}

			else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
				main.setPlayer1Shooting(false);
			}
		} else {
			if (event.getKeyCode() == KeyEvent.VK_UP) {
				player1.setAccelerating(false);
				main.getBoost().stop();
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
