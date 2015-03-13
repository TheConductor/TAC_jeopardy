import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;

/**
 * @author Alex Aziz
 */
public class Tile extends JButton {
	private static final long serialVersionUID = 1L;
	private static Font f = new Font("Arial", Font.BOLD, 20);
	private int pointValue = 0; // point value for the tile (will be multiplied
	// by y position of tile)
	private boolean available = true; // if the question has been asked the tile
	// the not is not available
	private String category;
	private Question question;

	// Used for category Tiles
	public Tile(int pointValue, String text) {
		setPreferredSize(new Dimension(10, 10));
		setBackground(Color.BLUE);
		//setForeground(Color.YELLOW);
		setPointValue(pointValue);
		setCategory(text);
		setFont(f);
	}

	// Used for question Tiles
	public Tile(int pointValue, Question text) {
		setPreferredSize(new Dimension(10, 10));
		setBackground(Color.BLUE);
		//setForeground(Color.YELLOW);
		setPointValue(pointValue);
		setQuestion(text);
		setFont(f);
	}

	public void alreadyAsked() {
		setBackground(Color.BLUE);
		paintImmediately(0, 0, getWidth(), getHeight());
		setText("");
		getParent().repaint();
		this.setAvailable(false);
	}

	public void reset() {
		paintImmediately(0, 0, getWidth(), getHeight());
		this.setAvailable(false);
	}

	/* Getters and Setters */

	public boolean isAvailable() {
		return available;
	}

	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	/* End Getters and Setters */
}// End Class