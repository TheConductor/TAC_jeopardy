import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Alex Aziz
 */
public class GameBoard extends JFrame {
	private static final long serialVersionUID = 1L;
	private Tile[][] myTiles;
	private int numRows = 6;
	private int numCols = 5;
	private int Turn = 0; // Used to determine whose turn it is
	private int numberOfPlayers = 0; // Set by Teacher
	private int playerWhoWon = 0; // Used to determine who won
	private int winner = 0; // Used to check for a winner basically a boolean in
	// int form
	private int roundNumber = 1; // Used to adjust point values
	private int tilesClicked = 0; // Used to end round/game when all tiles have
	// been clicked
	private boolean move = false; // Used to change turn by being changed after
	// a player moves
	private Player[] TAClings; // used to keep tack of the people playing
	private Question[][] questions = new Question[5][5];
	EventHandler eh = new EventHandler(this);
	private Container c = getContentPane();

	public static void main(String[] args) throws FileNotFoundException {
		GameBoard gb = new GameBoard();
		gb.play();
	}// End main

	public GameBoard() {
		myTiles = new Tile[numRows][numCols];
		setSize(800, 800);
		c.removeAll();
		c.setBackground(Color.GREEN);
		c.setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("TAC Jeopardy");

		JPanel tilePanel = buildTilePanel();

		c.add(tilePanel, BorderLayout.CENTER);

		JMenuBar menuBar = buildMenu();
		setJMenuBar(menuBar);

		setVisible(true);
		boolean validInput = false;
		do {
			validInput = true;
			new JOptionPane();
			String getNumberOfPlayers = JOptionPane
					.showInputDialog("How many people will be players?");
			try {
				Integer.parseInt(getNumberOfPlayers);
			} catch (NumberFormatException e) {
				validInput = false;
			}
			if (validInput == true && Integer.parseInt(getNumberOfPlayers) >= 1) {
				numberOfPlayers = Integer.parseInt(getNumberOfPlayers);
				break;
			}
		} while (validInput == false);
		TAClings = new Player[numberOfPlayers];
		for (int i = 0; i < TAClings.length; i++) {// Adds 1 to prevent there
			// from being a player 0
			// which is the default
			// player number
			new JOptionPane();
			String name = JOptionPane.showInputDialog("What is player "
					+ (i + 1) + "'s name?");
			Player newTACling = new Player(i, name);
			TAClings[i] = newTACling;
		}// End for
		@SuppressWarnings("unused")
		Scoreboard sb = new Scoreboard(TAClings);
	}// End Constructor

	private JPanel buildTilePanel() {
		JPanel tilePanel = new JPanel();
		tilePanel.setBackground(Color.BLUE);
		tilePanel.setPreferredSize(new Dimension(800, 600));
		tilePanel.setLayout(new GridLayout(numRows, numCols));

		FileReader fr = new FileReader(this);
		while (fr.isArrayFull() == false) {
			System.out.println();
		}
		String categories[] = new String[5];
		for (int i = 0; i < categories.length; i++) {
			categories[i] = fr.getCategories()[i];
		}
		fr.disposeFrame();
		for (int i = 0; i < categories.length; i++) {
			QuestionSelector qs = new QuestionSelector(categories[i]);
			while (qs.isArrayFull() == false) {
				System.out.println();
			}// End while
			for (int j = 0; j < categories.length; j++) {
				// JOptionPane.showConfirmDialog(null,
				// qs.getQuestions()[j].getDiffcultyLevel());
				questions[i][j] = qs.getQuestions()[j];
			}// End for
		}// End for
		for (int i = 0; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[0].length; j++) {
				if (i == 0) {
					String category = categories[j];// category gets read in
					// here from category array
					Tile newTile = new Tile(0, category);
					myTiles[i][j] = newTile;
					myTiles[i][j].setText(category);
					tilePanel.add(myTiles[i][j]);
				} else {
					Question question = questions[j][i - 1];// questions get
					// read in here from
					// questions array
					Tile newTile = new Tile((200 * roundNumber * i), question);
					myTiles[i][j] = newTile;
					myTiles[i][j].addActionListener(eh);
					String pointsOnTile = Integer.toString(myTiles[i][j]
							.getPointValue());
					myTiles[i][j].setText(pointsOnTile);
					tilePanel.add(myTiles[i][j]);
				}// End If-Else
			}// End For j
		}// End For i
		return tilePanel;
	}// End build TilePanel

	private JMenuBar buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		/*
		 * JMenuItem menuItem = new JMenuItem("New Game");
		 * menuItem.addActionListener(eh); fileMenu.add(menuItem);
		 */
		JMenuItem menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);
		menuBar.add(fileMenu);
		return menuBar;
	}// End buildMenuBar

	private void play() throws FileNotFoundException {
		int continuePlaying = 0;
		while (continuePlaying == 0) {
			while (tilesClicked < ((numRows - 1) * numCols)) {
				turn();
				tilesClicked++;
				if (Turn == TAClings.length - 1) {
					Turn = 0;
				} else {
					Turn++;
				}// End if
			}// End While to see if someone has won
			continuePlaying++;
		}// End While
		int tie = 0;
		ArrayList<Integer> tiedPlayers = new ArrayList<Integer>();
		for (int i = 0; i < TAClings.length; i++) {
			System.out.println(TAClings[playerWhoWon].getScore() + " "
					+ TAClings[i].getScore());
			if (TAClings[playerWhoWon].getScore() < TAClings[i].getScore()) {
				System.out.println("True");
				playerWhoWon = i;
				tie = 0;
			} else if (TAClings[playerWhoWon].getScore() < TAClings[i]
					.getScore()) {
				tie = 1;
				tiedPlayers.add(i);
			}// End if else
		}// End for
		if (tie == 0) {
			JOptionPane.showMessageDialog(null,
					TAClings[playerWhoWon].getName() + " is the winner!");
		} else {
			String listOfPlayers = "";
			for (int i = 0; i < tiedPlayers.size(); i++) {
				if (i < tiedPlayers.size() - 3) {
					listOfPlayers = listOfPlayers + tiedPlayers.get(i) + ", ";
				} else if (i < tiedPlayers.size() - 2) {
					listOfPlayers = listOfPlayers + tiedPlayers.get(i)
							+ " and ";
				} else {
					listOfPlayers = listOfPlayers + tiedPlayers.get(i);
				}// End if else
			}// End for
			JOptionPane.showMessageDialog(null, listOfPlayers + " Tied.");
		}// End if else

		// File Output
		File outputFile = new File("Output.txt");
		java.io.PrintStream ps = new java.io.PrintStream(outputFile);
		for (int i = 0; i < TAClings.length; i++) {
			ps.println("TACling # " + i + " Name: " + TAClings[i].getName()
					+ " Score: " + TAClings[i].getScore());
		}// End For
		ps.println();
		for (int i = 1; i < myTiles.length; i++) {
			for (int j = 0; j < myTiles[i].length; j++) {
				if (myTiles[i][j].getQuestion().getAnsweredBy() != -1) {
					ps.println(TAClings[myTiles[i][j].getQuestion()
							.getAnsweredBy()].getName()
							+ " answered a "
							+ myTiles[i][j].getQuestion().getCategory()
							+ " question correctly. The question was '"
							+ myTiles[i][j].getQuestion().getQuestionText()
							+ "'");
				} else {
					ps.println(TAClings[myTiles[i][j].getQuestion()
							.getAttemptedBy()].getName()
							+ " answered a "
							+ myTiles[i][j].getQuestion().getCategory()
							+ " question correctly. The question was  '"
							+ myTiles[i][j].getQuestion().getQuestionText()
							+ "'.");
				}
			}
		}
		ps.close();
		// End File Output
	}// End play

	public void turn() {
		Player currentPlayer = TAClings[this.getTurn()];
		JOptionPane.showMessageDialog(null,
				"It is player " + currentPlayer.getName()
						+ "'s turn.\nClick the tile you want to claim.");
		// Used to allow time for user to click
		do {
			System.out.println("");
		} while (this.isMove() == false); // End do
		this.setMove(false);
	}// End turn

	// Getters and Setters

	public int getTurn() {
		return Turn;
	}

	public void setTurn(int Turn) {
		this.Turn = Turn;
	}

	public boolean isMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public int getPlayerWhoWon() {
		return playerWhoWon;
	}

	public void setPlayerWhoWon(int playerWhoWon) {
		this.playerWhoWon = playerWhoWon;
	}

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public Player[] getTAClings() {
		return TAClings;
	}

	public void setTAClings(Player[] tAClings) {
		TAClings = tAClings;
	}

	// End Getters and Setters

}// End Class