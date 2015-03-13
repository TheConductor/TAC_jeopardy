import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author Alex Aziz
 */
public class EventHandler implements ActionListener {

	private GameBoard currentGameBoard;

	public EventHandler(GameBoard gb) {
		currentGameBoard = gb;
	}// End EventHandler

	public void actionPerformed(ActionEvent e) {
		String actionID = e.getActionCommand();
		if (actionID.equals("Exit")) {
			System.exit(0);
		}// If to determine if Exit was clicked
		else {
			Tile newTile = (Tile) e.getSource();
			if (newTile.isAvailable()) {
				if (newTile.isAvailable() == true) {
					int question = JOptionPane
							.showConfirmDialog(
									null,
									"For "
											+ newTile.getPointValue()
											+ " points.\n"
											+ newTile.getQuestion()
													.getCategory()
											+ ".\n"
											+ newTile.getQuestion()
													.getQuestionText()
											+ "\nDid "
											+ currentGameBoard.getTAClings()[currentGameBoard
													.getTurn()].getName()
											+ " answer correctly?");// Ask
																	// Question
					// in new window
					if (question == 0) {
						long score = currentGameBoard.getTAClings()[currentGameBoard
								.getTurn()].getScore()
								+ newTile.getPointValue();
						currentGameBoard.getTAClings()[currentGameBoard
								.getTurn()].setScore(score);
						JOptionPane
								.showMessageDialog(
										null,
										currentGameBoard.getTAClings()[currentGameBoard
												.getTurn()].getName()
												+ "'s score is "
												+ currentGameBoard
														.getTAClings()[currentGameBoard
														.getTurn()].getScore());
						newTile.getQuestion().setAnsweredBy(
								currentGameBoard.getTurn());
					} else if (question == 1) {
						long score = currentGameBoard.getTAClings()[currentGameBoard
								.getTurn()].getScore()
								- newTile.getPointValue();
						currentGameBoard.getTAClings()[currentGameBoard
								.getTurn()].setScore(score);
						JOptionPane
								.showMessageDialog(
										null,
										currentGameBoard.getTAClings()[currentGameBoard
												.getTurn()].getName()
												+ "'s score is "
												+ currentGameBoard
														.getTAClings()[currentGameBoard
														.getTurn()].getScore());
						newTile.getQuestion().setAttemptedBy(
								currentGameBoard.getTurn());
					} else if (question == 2) {

					}
					newTile.alreadyAsked();
					currentGameBoard.setMove(true);
				}// End if to set question asked.
			} else {
				int player = JOptionPane.showConfirmDialog(null, "Did "
						+ currentGameBoard.getTAClings()[newTile.getQuestion()
								.getAnsweredBy()].getName()
						+ " answer correctly?");
				Player oldPlayer = currentGameBoard.getTAClings()[newTile
						.getQuestion().getAnsweredBy()];
				if (player == 1) {
					// If player didn't get it right
					// Ask if someone else did
					player = JOptionPane.showConfirmDialog(null,
							"Did another TACling answer correctly?");
					if (player == 0) {
						// If someone else did
						// Ask Who
						Player newPlayer = oldPlayer;
						for (int i = 0; i < currentGameBoard.getTAClings().length; i++) {
							int correctPlayer = JOptionPane.showConfirmDialog(
									null,
									"Did another "
											+ currentGameBoard.getTAClings()[i]
													.getName()
											+ " answer correctly?");
							if (correctPlayer == 0) {
								newPlayer = currentGameBoard.getTAClings()[i];
								break;
							}
						}
						// Subtract pts from wrong players score
						oldPlayer.setScore(oldPlayer.getScore()
								- newTile.getPointValue());
						// Add pts to their score
						newPlayer.setScore(newPlayer.getScore()
								+ newTile.getPointValue());
						// Change answeredBy in Question
						newTile.getQuestion().setAnsweredBy(newPlayer.getNumber());
					}// End if
				}// End if
			}// End if
		}// End If Else
	}// End action Performed
}// End Class