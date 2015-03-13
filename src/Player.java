/**
 * @author Alex Aziz
 */
public class Player {
	private int number = 0;
	private String name = "";
	private long Score = 0;

	public Player(int no, String name) {
		setNumber(no);
		setName(name);
	}// End constructor

	/* Getters and Setters */

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getScore() {
		return Score;
	}

	public void setScore(long score) {
		Score = score;
	}

	/* End Getters and Setters */
}// End Player