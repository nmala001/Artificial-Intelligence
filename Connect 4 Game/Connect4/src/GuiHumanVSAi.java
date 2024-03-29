import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class GuiHumanVSAi {
	
	static Board board = new Board();
	static JFrame frameMainWindow;
	static JFrame frameGameOver;
	
	static JPanel panelBoardNumbers;
	static JLayeredPane layeredGameBoard;
	
	//Menu bars and items.
	static JMenuBar menuBar;
	static JMenu menu1;
	static JMenuItem item11;
	static JMenuItem item12;
	static JMenuItem item13;
	static JMenu menu2;
	static JMenuItem item21;
	static JMenuItem item22;

	static GameParameters game_params = new GameParameters();
	static int maxDepth = game_params.getMaxDepth();
	static String player1Color = game_params.getPlayer1Color();
	static String player2Color = game_params.getPlayer2Color();
	
//	static GamePlayer ai = new GamePlayer();
	static MinimaxAI ai = new MinimaxAI(maxDepth, Board.X);
	
	//	Human player letter -> X. He plays First
	//	Minimax AI letter -> O.
	
	public GuiHumanVSAi() {
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		createNewGame();
	}
		
	// o kurios pinakas tou Connect-4
	public static JLayeredPane createLayeredBoard() {
		layeredGameBoard = new JLayeredPane();
		layeredGameBoard.setPreferredSize(new Dimension(570, 490));
		layeredGameBoard.setBorder(BorderFactory.createTitledBorder("Connect-4"));

		ImageIcon imageBoard = new ImageIcon(ResourceLoader.load("images/Board.gif"));
		JLabel imageBoardLabel = new JLabel(imageBoard);

		imageBoardLabel.setBounds(20, 20, imageBoard.getIconWidth(), imageBoard.getIconHeight());
		layeredGameBoard.add(imageBoardLabel, new Integer (0), 1);

		return layeredGameBoard;
	}
	
	// kaleitai otan xekinaei to paixnidi apo thn arxh
	public static void createNewGame() {
		board = new Board();
		
		// set the new difficulty setting
		ai.setMaxDepth(maxDepth);
		
		// debugging
//		System.out.println("difficulty:" + gp.getDifficulty());
//		System.out.println("color:" + gp.getColor());
             
		if (frameMainWindow != null) frameMainWindow.dispose();
		frameMainWindow = new JFrame("Minimax Connect-4");
		centerWindow(frameMainWindow, 570, 490); // to kurio parathuro tha emfanizetai sto kentro
		Component compMainWindowContents = createContentComponents();
		frameMainWindow.getContentPane().add(compMainWindowContents, BorderLayout.CENTER);

		frameMainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
			System.exit(0);
			}
		});
		
		frameMainWindow.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
				String button = KeyEvent.getKeyText(e.getKeyCode());
				
				if (button.equals("1")) {
					board.makeMove(0, Board.X);
				} else if (button.equals("2")) {
					board.makeMove(1, Board.X);
				} else if (button.equals("3")) {
					board.makeMove(2, Board.X);
				} else if (button.equals("4")) {
					board.makeMove(3, Board.X);
				} else if (button.equals("5")) {
					board.makeMove(4, Board.X);
				} else if (button.equals("6")) {
					board.makeMove(5, Board.X);
				} else if (button.equals("7")) {
					board.makeMove(6, Board.X);
				}
				
				if (button.equals("1") || button.equals("2") || button.equals("3") || button.equals("4")
						|| button.equals("5") || button.equals("6") || button.equals("7")) {
					game();
					aiMove();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				//System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
			}
		});
		
		frameMainWindow.setFocusable(true);
		
		// show window
		frameMainWindow.pack();
		frameMainWindow.setVisible(true);

		if (board.getLastLetterPlayed() == Board.X) {
			Move aiMove = ai.MiniMax(board);
			board.makeMove(aiMove.getCol(), Board.O);
			game();
		}

	}
	
	
	// kentrarei to parathuro sthn othonh
	public static void centerWindow(Window frame, int width, int height) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) (((dimension.getWidth() - frame.getWidth()) / 2) - (width/2));
	    int y = (int) (((dimension.getHeight() - frame.getHeight()) / 2) - (height/2));
	    frame.setLocation(x, y);
	}
	
	// topothetei checker ston pinaka
	public static void placeChecker(String color, int row, int col) {
		int xOffset = 75 * col;
		int yOffset = 75 * row;
		ImageIcon checkerIcon = new ImageIcon(ResourceLoader.load("images/" + color + ".gif"));
		JLabel checkerLabel = new JLabel(checkerIcon);
		checkerLabel.setBounds(27 + xOffset, 27 + yOffset, checkerIcon.getIconWidth(),checkerIcon.getIconHeight());
		layeredGameBoard.add(checkerLabel, new Integer(0), 0);
		frameMainWindow.paint(frameMainWindow.getGraphics());
	}
	
	// kaleitai kathe fora pou eisagetai mia kinhsh ston pinaka
	public static void game() {
	
		int row = board.getLastMove().getRow();
		int col = board.getLastMove().getCol();

		int currentPlayer = board.getLastLetterPlayed();
		
		if (currentPlayer == Board.X) {
			// topothetei checker sto [row][col] tou GUI
			placeChecker(game_params.getPlayer1Color(), row, col);
		}
			
		if (currentPlayer == Board.O) {
			// topothetei checker sto [row][col] tou GUI
			placeChecker(game_params.getPlayer2Color(), row, col);
		}
		
		if (board.checkGameOver()) {
			board.setGameOver(true);
			gameOver();
		}
		

	}
	
	// kaleitai meta thn kinhsh tou human player, wste na paixei o computer AI
	public static void aiMove(){

		if (!board.isGameOver()) {
			// check if human player played last
			if (board.getLastLetterPlayed() == Board.X) {
				Move aiMove = ai.MiniMax(board);
				board.makeMove(aiMove.getCol(), Board.O);
				game();
			}
		}

	}
	
	/**
	 * Returns a component to be drawn by main window.
	 * This function creates the main window components.
	 * Kalei ton actionListener otan ginetai click me to pontiki panw se button.
	 */
	public static Component createContentComponents() {
		// dhmiourgia panel gia na organwsoume ta buttons tou pinaka
		panelBoardNumbers = new JPanel();
		panelBoardNumbers.setLayout(new GridLayout(1, 7, 6, 4));
		panelBoardNumbers.setBorder(BorderFactory.createEmptyBorder(2, 22, 2, 22));
		
		JButton col1_button = new JButton("1");
		col1_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.makeMove(0, Board.X);
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col2_button = new JButton("2");
		col2_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.makeMove(1, Board.X);
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col3_button = new JButton("3");
		col3_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.makeMove(2, Board.X);
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col4_button = new JButton("4");
		col4_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.makeMove(3, Board.X);
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col5_button = new JButton("5");
		col5_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.makeMove(4, Board.X);
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col6_button = new JButton("6");
		col6_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.makeMove(5, Board.X);
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		JButton col7_button = new JButton("7");
		col7_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.makeMove(6, Board.X);
				game();
				aiMove();
				frameMainWindow.requestFocusInWindow();
//				board.print();
			}
		});
		
		panelBoardNumbers.add(col1_button);
		panelBoardNumbers.add(col2_button);
		panelBoardNumbers.add(col3_button);
		panelBoardNumbers.add(col4_button);
		panelBoardNumbers.add(col5_button);
		panelBoardNumbers.add(col6_button);
		panelBoardNumbers.add(col7_button);

		// dhmiourgia tou kuriou pinaka tou Connect-4, mazi me ta buttons 
		layeredGameBoard = createLayeredBoard();

		// dhmiourgia panel gia na krathsoume ola ta stoixeia tou pinaka
		JPanel panelMain = new JPanel();
		panelMain.setLayout(new BorderLayout());
		panelMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// pros8hkh antikeimenwn sto panelMain 
		panelMain.add(panelBoardNumbers, BorderLayout.NORTH);
		panelMain.add(layeredGameBoard, BorderLayout.CENTER);

		frameMainWindow.setResizable(false);
		return panelMain;
	}
	
	public static void gameOver() {
		        		        
//		panelBoardNumbers.setVisible(false);
		frameGameOver = new JFrame("Game over!");
		frameGameOver.setBounds(620, 400, 350, 128);
		centerWindow(frameGameOver, 0, 0);
		JPanel winPanel = new JPanel(new BorderLayout());
		winPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
		
//		ImageIcon winIcon = new ImageIcon(ResourceLoader.load("images/win.gif"));
//		JLabel winLabel = new JLabel(winIcon);
		JLabel winLabel;
		board.checkWinState();
		if (board.getWinner() == Board.X) {
			winLabel = new JLabel("You win! Start a new game?");
			winPanel.add(winLabel);
		} else if (board.getWinner() == Board.O) {
			winLabel = new JLabel("Computer AI wins! Start a new game?");
			winPanel.add(winLabel);
		} else {
			winLabel = new JLabel("It's a draw! Start a new game?");
			winPanel.add(winLabel);
		}
		winLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		winPanel.add(winLabel, BorderLayout.NORTH);
		
		JButton yesButton = new JButton("Yes");
		yesButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		yesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameGameOver.setVisible(false);
				createNewGame();
			}
		});
		
		JButton quitButton = new JButton("Quit");
		quitButton.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 30));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameGameOver.setVisible(false);
				System.exit(0);
			}
		});
		
		JPanel subPanel = new JPanel();
		subPanel.add(yesButton);
		subPanel.add(quitButton);
		
		winPanel.add(subPanel, BorderLayout.CENTER);
		frameGameOver.getContentPane().add(winPanel, BorderLayout.CENTER);
		frameGameOver.setResizable(false);
		frameGameOver.setVisible(true);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args){
		GuiHumanVSAi Connect4 = new GuiHumanVSAi();
	}
	
}
