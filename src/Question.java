/**
 * @author Alex Aziz
 */
public class Question {
	private String category = "";
	private String questionText = "";
	private String rightAnswer = "";
	private String wrongAnswer1 = "";
	private String wrongAnswer2 = "";
	private int diffcultyLevel = 0; // level of a real q is btw 1 and 5
	// inclusive
	private int answeredBy = -1; // Player No. of who answered correctly
	private int attemptedBy = -1; // keeps track who answered wrong

	public Question(String category, String questionText, String rightAnswer,
			String wrongAnswer1, String wrongAnswer2, int diffcultyLevel) {
		setCategory(category);
		setQuestionText(questionText);
		setRightAnswer(rightAnswer);
		setWrongAnswer1(wrongAnswer1);
		setWrongAnswer1(wrongAnswer2);
		setDiffcultyLevel(diffcultyLevel);
	}

	/* Getters and Setters */

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getWrongAnswer1() {
		return wrongAnswer1;
	}

	public void setWrongAnswer1(String wrongAnswer1) {
		this.wrongAnswer1 = wrongAnswer1;
	}

	public String getWrongAnswer2() {
		return wrongAnswer2;
	}

	public void setWrongAnswer2(String wrongAnswer2) {
		this.wrongAnswer2 = wrongAnswer2;
	}

	public int getDiffcultyLevel() {
		return diffcultyLevel;
	}

	public void setDiffcultyLevel(int diffcultyLevel) {
		this.diffcultyLevel = diffcultyLevel;
	}

	public int getAnsweredBy() {
		return answeredBy;
	}

	public void setAnsweredBy(int answeredBy) {
		this.answeredBy = answeredBy;
	}

	public int getAttemptedBy() {
		return attemptedBy;
	}

	public void setAttemptedBy(int attemptedBy) {
		this.attemptedBy = attemptedBy;
	}

	/* End Getters and Setters */
}