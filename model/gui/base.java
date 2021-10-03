package model.gui;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;

import exceptions.InvalidPowerUseException;
import exceptions.OccupiedCellException;
import exceptions.UnallowedMovementException;
import exceptions.WrongTurnException;
import model.game.Cell;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.Piece;
import model.pieces.heroes.ActivatablePowerHero;
import model.pieces.heroes.Medic;
import model.pieces.heroes.Ranged;
import model.pieces.heroes.Super;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class base extends JFrame implements ActionListener,KeyListener{

	public static ArrayList<ButtonCells> Buttons = new ArrayList<ButtonCells>();
	private static Game game;
	private static JPanel p = new JPanel(new GridLayout(7,6));
	private static JLabel display = new JLabel();
	private static JLabel Board = new JLabel();
	private static JLabel addOns = new JLabel();
	private static JPanel dead = new JPanel(new GridLayout(0,24));
	protected static ButtonCells currentButton;
	protected static JButton usePower = new JButton();
	protected static ButtonCells target;
	protected static ButtonCells newCell;
	private static JButton currentPlayer;
	private JProgressBar payLoadBarP1;
	private JProgressBar payLoadBarP2;
	private static Player currentOwner;
	private static ButtonCells deadChar;
	//power
	private static boolean firstClick;
	private static boolean secondClick;
	private static ArrayList<Point> newPos = new ArrayList<Point>();
	private static Point currPoint;
	private static Direction currDirection;

	@SuppressWarnings("static-access")
	public base(Game game) throws IOException {
		super("Gurren Lagann Chess - By Abdallah Akram");
		this.game = game;
		currentOwner = game.getCurrentPlayer();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1280,720);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		Image bg_addOns =  ImageIO.read(new File("banner3.png"));
		Image bg_board = ImageIO.read(new File("background4.png"));
		Board.setIcon(new ImageIcon(bg_board.getScaledInstance(1280, 720, Image.SCALE_SMOOTH)));
		addOns.setIcon(new ImageIcon(bg_addOns));
		this.add(Board,BorderLayout.CENTER);
		Board.setLayout(new BorderLayout());
		Board.setBorder(new LineBorder(Color.BLACK));
		Board.add(createCells(game), BorderLayout.CENTER);

		this.add(addOns,BorderLayout.NORTH);
		addOns.setLayout(new BorderLayout());
		addOns.setBorder(new LineBorder(Color.BLACK));
		addOns.add(addOns(game),BorderLayout.NORTH);
		addOns.setPreferredSize(new Dimension(1280,125));

		Board.add(addOns,BorderLayout.NORTH);
		Board.add(payLoad1(game),BorderLayout.WEST);
		Board.add(payLoad2(game),BorderLayout.EAST);


		Board.add(deadHeros1(game),BorderLayout.SOUTH);



		Board.setVisible(true);
		addOns.setVisible(true);
		this.setVisible(true);	





	}
	
	public JPanel createCells(Game game) throws IOException {
		for(ButtonCells i:Buttons) {
			i.setVisible(false);
		}
		Buttons.clear();
		p.removeAll();
		newPos.clear();
		for(int i =0;i<game.getBoardHeight();i++) {
			for(int j=0;j<game.getBoardWidth();j++) {
				Cell c = game.getCellAt(i, j);
				ButtonCells b = new ButtonCells(c);
				b.setContentAreaFilled(false);    
				b.setOpaque(false);
				b.addActionListener(this);
				b.addKeyListener(this);
				Buttons.add(b);
				b.setToolTipText(c.toString());
				p.add(b);
				newPos.add(new Point(i,j));


			}
		}
		p.setOpaque(false);
		return p;

	}

	public String updateCurrentPlayer(Game game) {
		if(game.getCurrentPlayer().equals(game.getPlayer1()))
			return game.getPlayer1().getName();
		return game.getPlayer2().getName();

	}

	public void updateCurrentPlayerLabel() {
		currentPlayer.setText("Current Player :" +updateCurrentPlayer(game));
	}

	public JLabel addOns(Game game) throws IOException {
		currentPlayer = new JButton();
		JLabel hint = new JLabel();
		Image power = ImageIO.read(new File("power.png"));
		usePower.setActionCommand("powerUsed");
		usePower.setIcon(new ImageIcon(power));
		usePower.setToolTipText("Use Spiral Power");
		usePower.setPreferredSize(new Dimension(200,150));
		hint.setPreferredSize(new Dimension(700,80));
		hint.setText(hint.getText()+"Hint:Use the number pad to move/use power in the desired direction(Make sure that the number pad is ON!!!)");
		currentPlayer.setPreferredSize(new Dimension(440,700));
		currentPlayer.setText("Current Player :" +updateCurrentPlayer(game));
		display.setLayout(new BorderLayout());
		display.setPreferredSize(new Dimension(150,150));
		display.add(currentPlayer,BorderLayout.EAST);
		display.add(hint,BorderLayout.SOUTH);
		display.add(usePower,BorderLayout.WEST);
		hint.setFont(new Font("Chiller", Font.ITALIC + Font.BOLD, 22));
		hint.setForeground(Color.BLACK);
		currentPlayer.setFont(new Font("Chiller", Font.ITALIC + Font.BOLD, 20));
		currentPlayer.setForeground(Color.WHITE);
		currentPlayer.setContentAreaFilled(false);    
		currentPlayer.setOpaque(false);
		currentPlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		usePower.addKeyListener(this);
		usePower.addActionListener(this);
		usePower.setOpaque(false);
		usePower.setContentAreaFilled(false);

		

		return display;
	}

	public JPanel deadHeros1(Game game) throws IOException {
		dead.setPreferredSize(new Dimension(720,100));
		
		for(Piece p1:game.getPlayer1().getDeadCharactersHelper()) {
			deadChar = new ButtonCells(p1);
			deadChar.setToolTipText(p1.toString());
			deadChar.setBorderPainted(false);
			deadChar.setContentAreaFilled(false);
			deadChar.setOpaque(false);
			deadChar.setForeground(Color.white);
			deadChar.addActionListener(this);
			deadChar.addKeyListener(this);
			dead.add(deadChar);
			game.getPlayer1().getDeadCharactersHelper().clear();
		}


		
		dead.setBorder(new LineBorder(Color.BLACK));	
		dead.setToolTipText("Fallen Mechas Graveyard");
		dead.setOpaque(false);
		return dead;
	}


	public JPanel deadHeros2(Game game) throws IOException {
		dead.setPreferredSize(new Dimension(720,100));
		
		for(Piece p1:game.getPlayer2().getDeadCharactersHelper()) {
			deadChar = new ButtonCells(p1);
			deadChar.setToolTipText(p1.toString());
			deadChar.setBorderPainted(false);
			deadChar.setContentAreaFilled(false);
			deadChar.setOpaque(false);
			deadChar.setForeground(Color.white);
			deadChar.addActionListener(this);
			deadChar.addKeyListener(this);
			dead.add(deadChar);
			game.getPlayer2().getDeadCharactersHelper().clear();
		}


		
		dead.setBorder(new LineBorder(Color.BLACK));	
		dead.setToolTipText("Fallen Mechas Graveyard");
		dead.setOpaque(false);
		return dead;
	}

	public JProgressBar payLoad1(Game game) { 
		payLoadBarP1 = new JProgressBar(JProgressBar.VERTICAL,0,6);
		payLoadBarP1.setPreferredSize(new Dimension(20,20));
		payLoadBarP1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		payLoadBarP1.setBackground(Color.GRAY);
		payLoadBarP1.setToolTipText(game.getPlayer1().getName() + "'s Winning Progress");
		payLoadBarP1.setOpaque(false);
		return payLoadBarP1;
	}

	public JProgressBar payLoad2(Game game) { 
		payLoadBarP2 = new JProgressBar(JProgressBar.VERTICAL,0,6);
		payLoadBarP2.setPreferredSize(new Dimension(20,20));
		payLoadBarP2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		payLoadBarP2.setBackground(Color.GRAY);
		payLoadBarP2.setToolTipText(game.getPlayer2().getName() + "'s Winning Progress");
		payLoadBarP2.setOpaque(false);
		return payLoadBarP2;
	}

	public void updateProgressBar() {
		payLoadBarP1.setValue(game.getPlayer1().getPayloadPos());
		payLoadBarP2.setValue(game.getPlayer2().getPayloadPos());
		try {
			winner();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	public static void powerSFX() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioInputStream audioIn = null;
		audioIn = AudioSystem.getAudioInputStream(new File("powerSFX.wav").getAbsoluteFile());
		Clip clip = null;
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();

	}
	public static void GLsFX() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioInputStream audioIn = null;
		audioIn = AudioSystem.getAudioInputStream(new File("GLsFX.wav").getAbsoluteFile());
		Clip clip = null;
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();

	}
	public static void errorSFX() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioInputStream audioIn = null;
		audioIn = AudioSystem.getAudioInputStream(new File("error.wav").getAbsoluteFile());
		Clip clip = null;
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();

	}

	public static void popSFX() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		AudioInputStream audioIn = null;
		audioIn = AudioSystem.getAudioInputStream(new File("popSFX.wav").getAbsoluteFile());
		Clip clip = null;
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
	}

	public static void backGroundSFX() throws UnsupportedAudioFileException, IOException, LineUnavailableException { 

		AudioInputStream audioIn = null;
		audioIn = AudioSystem.getAudioInputStream(new File("background.wav").getAbsoluteFile());
		Clip clip = null;
		clip = AudioSystem.getClip();
		clip.open(audioIn);
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("powerUsed")) {
			if(currentButton.getC().getPiece() instanceof Medic) {
				try { 
					((ActivatablePowerHero) currentButton.getC().getPiece()).usePower(currDirection, ((target.getC().getPiece() == null)?null:target.getC().getPiece()),currPoint);
					updateCurrentPlayerLabel();
					updateProgressBar();
					try {
						createCells(game);
						powerSFX();
					} catch (IOException e2) {}

					if(game.getCurrentPlayer().equals(game.getPlayer1()))
						try {
							deadHeros1(game);
						} catch (IOException e1) {}
					else
						try {
							deadHeros2(game);
						} catch (IOException e1) {}
				} catch (InvalidPowerUseException | WrongTurnException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				} catch (UnsupportedAudioFileException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (LineUnavailableException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}
			try { 
				((ActivatablePowerHero) currentButton.getC().getPiece()).usePower(currDirection, ((target.getC().getPiece() == null)?null:target.getC().getPiece()),currPoint);
				updateCurrentPlayerLabel();
				updateProgressBar();
				try {
					createCells(game);
					powerSFX();
				} catch (IOException e2) {}

				if(game.getCurrentPlayer().equals(game.getPlayer1()))
					try {
						deadHeros1(game);
					} catch (IOException e1) {}
				else
					try {
						deadHeros2(game);
					} catch (IOException e1) {}
			} catch (InvalidPowerUseException | WrongTurnException e1) {
				try {
					errorSFX();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
					e2.printStackTrace();
				}
				JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
			} catch (UnsupportedAudioFileException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (LineUnavailableException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}

		}
		else {
			if(!currentOwner.equals(game.getCurrentPlayer())) {
				firstClick = false;
				secondClick = false;
				currDirection = null;
				currentOwner = game.getCurrentPlayer();
				currentButton = null;
				currPoint = null;
			}

			if(firstClick == false && secondClick == false) {
				firstClick = true;
				//get currentPlayer
				for(ButtonCells b : Buttons) 
					b.setBorder(BorderFactory.createLineBorder(Color.GRAY));


				currentButton =  Buttons.get(Buttons.indexOf(e.getSource()));
				currentButton.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
			else if(firstClick == true && secondClick == false) {
				secondClick = true;
				//getTaret
				target =  Buttons.get(Buttons.indexOf(e.getSource()));
				target.setBorder(BorderFactory.createLineBorder(Color.blue));
				
			}
			else {
				firstClick = false;
				secondClick = false;
				//getPoint
				Buttons.get(Buttons.indexOf(e.getSource())).setBorder(BorderFactory.createLineBorder(Color.MAGENTA));;
				currPoint = newPos.get(Buttons.indexOf(e.getSource()));

			}
		}
	}



	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if(currentButton==null|| currentButton.getC().getPiece()==null|| stopGame() == true)
			return;

		if(secondClick == true) {

			if(keyCode == KeyEvent.VK_NUMPAD1) 		currDirection = Direction.DOWNLEFT;
			else if(keyCode == KeyEvent.VK_NUMPAD2) currDirection = Direction.DOWN;
			else if(keyCode == KeyEvent.VK_NUMPAD3) currDirection = Direction.DOWNRIGHT;
			else if(keyCode == KeyEvent.VK_NUMPAD4) currDirection = Direction.LEFT;
			else if(keyCode == KeyEvent.VK_NUMPAD6) currDirection = Direction.RIGHT;
			else if(keyCode == KeyEvent.VK_NUMPAD7) currDirection = Direction.UPLEFT;
			else if(keyCode == KeyEvent.VK_NUMPAD8) currDirection = Direction.UP;
			else if(keyCode == KeyEvent.VK_NUMPAD9) currDirection = Direction.UPRIGHT;
		}else {
			if(keyCode == KeyEvent.VK_NUMPAD1)
				try {
					currentButton.getC().getPiece().move(Direction.DOWNLEFT);
					updateProgressBar();
					createCells(game);
					updateCurrentPlayerLabel();
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				}
			else if(keyCode == KeyEvent.VK_NUMPAD2)
				try {
					currentButton.getC().getPiece().move(Direction.DOWN);
					updateCurrentPlayerLabel();
					updateProgressBar();
					createCells(game);
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				}
			else if(keyCode == KeyEvent.VK_NUMPAD3)
				try {
					currentButton.getC().getPiece().move(Direction.DOWNRIGHT);
					updateCurrentPlayerLabel();
					updateProgressBar();
					createCells(game);
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				}
			else if(keyCode == KeyEvent.VK_NUMPAD4)
				try {
					currentButton.getC().getPiece().move(Direction.LEFT);
					updateCurrentPlayerLabel();
					updateProgressBar();
					createCells(game);
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(),e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				}
			else if(keyCode == KeyEvent.VK_NUMPAD6)
				try {
					currentButton.getC().getPiece().move(Direction.RIGHT);
					updateCurrentPlayerLabel();
					updateProgressBar();
					createCells(game);
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				}
			else if(keyCode == KeyEvent.VK_NUMPAD7)
				try {
					currentButton.getC().getPiece().move(Direction.UPLEFT);
					updateCurrentPlayerLabel();
					updateProgressBar();
					createCells(game);
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				}
			else if(keyCode == KeyEvent.VK_NUMPAD8)
				try {
					currentButton.getC().getPiece().move(Direction.UP);
					updateProgressBar();
					updateCurrentPlayerLabel();
					createCells(game);
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			else if(keyCode == KeyEvent.VK_NUMPAD9)
				try {
					currentButton.getC().getPiece().move(Direction.UPRIGHT);
					updateProgressBar();
					updateCurrentPlayerLabel();
					createCells(game);
					try {
						popSFX();
					} catch (UnsupportedAudioFileException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					if(game.getCurrentPlayer().equals(game.getPlayer1())) 
						deadHeros1(game);
					else
						deadHeros2(game);
				} catch (UnallowedMovementException | OccupiedCellException | WrongTurnException | IOException e1) {
					try {
						errorSFX();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e2) {
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(new JFrame(), e1.getMessage(), "Unable to perform action",JOptionPane.ERROR_MESSAGE);
				}
		}


	}

	public static void winner() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		if(stopGame() == true) {
			//backGroundSFX();
			GLsFX();
			JOptionPane.showMessageDialog(new JFrame(), game.getCurrentPlayer().getName() + "! Congratulations, you are the winner!" , "Winner:",JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static boolean stopGame() {
		if(game.checkWinner() == true)
			return true;
		return false;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}


	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		String P1 = null;
		String P2 = null;
		while(true) {
			P1 = JOptionPane.showInputDialog(null ,"Please enter Player1's name here:","Player1's Name",JOptionPane.INFORMATION_MESSAGE);
			P2 = JOptionPane.showInputDialog(null ,"Please enter Player2's name here:","Player2's Name",JOptionPane.INFORMATION_MESSAGE);
			if(!(P1.equals(P2)))
				break;
			else
				JOptionPane.showMessageDialog(new JFrame(), "Players cannot have similar names!!", "WARNING!",JOptionPane.WARNING_MESSAGE);
		}


		backGroundSFX();
		new base(new Game(new Player(P1),new Player(P2)));


	}

	public static Game getGame() {
		return game;
	}
}