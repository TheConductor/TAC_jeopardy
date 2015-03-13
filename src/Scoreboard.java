import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Alex Aziz
 */
public class Scoreboard extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Scoreboard");
	private Player[] TAClings;

	public Scoreboard(Player[] players) {
		TAClings = players;
		createAndShowGUI();
	}// End makeScoreboard

	void createAndShowGUI() {
		// Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Create and set up the content pane.
		JComponent newContentPane = createButtons();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}// End createAndShowGUI

	private JComponent createButtons() {
		JPanel scoreboard = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < TAClings.length; i++) {
			JButton player = new JButton(TAClings[i].getName());
			player.setActionCommand(i + "");
			player.addActionListener(this);
			scoreboard.add(player);
		}// End for
		add(scoreboard, BorderLayout.LINE_START);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		return scoreboard;
	}// End createButtons

	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if (Integer.parseInt(source) < TAClings.length) {
			JOptionPane.showConfirmDialog(null,
					TAClings[Integer.parseInt(source)].getName() + " "
							+ TAClings[Integer.parseInt(source)].getScore());
		} // End if
	}// End actionPerformed
}// End Class
