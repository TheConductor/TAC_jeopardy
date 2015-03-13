import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Alex Aziz
 */
public class QuestionSelector extends JPanel implements ItemListener,
		ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Question Selector");
	private int index = 0;
	private Question[] questions = new Question[5];
	private ArrayList<Question> availableQuestions = new ArrayList<Question>();
	private int selectorLimit = 0;
	private Question emptyQuestion = new Question("", "", "", "", "", 0);
	private boolean arrayFull = false;

	public QuestionSelector(String fileName) {
		availableQuestions = readQuestions(fileName);
		this.createAndShowGUI();
	}// End Constructor

	/**
	 * Read in Q's from file
	 */
	private ArrayList<Question> readQuestions(String fileName) {
		ArrayList<Question> questionsFromFile = new ArrayList<Question>();
		fileName = fileName + ".txt";
		File myFile = new File(fileName);
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(myFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// End try catch
		while (inputFile.hasNext()) {
			String questionFields = inputFile.nextLine();
			String[] fields = new String[questionFields.length()];
			fields = questionFields.split("~");
			Question newQuestion = new Question(fields[0], fields[1],
					fields[2], fields[3], fields[4],
					Integer.parseInt(fields[5]));
			questionsFromFile.add(newQuestion);
		}// End While
		inputFile.close();
		return questionsFromFile;
	}// End readQuestions

	public JComponent createQuestionButtons() {
		selectorLimit = 0;
		JPanel checkPanel = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < availableQuestions.size(); i++) {
			JCheckBox question = new JCheckBox(availableQuestions.get(i)
					.getQuestionText() + "\n" + index + " , " + i);
			question.setSelected(false);
			question.setName(i + "");
			question.addItemListener(this);
			checkPanel.add(question);
		}// End for
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		checkPanel.add(doneButton);
		add(checkPanel, BorderLayout.LINE_START);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		return checkPanel;
	}// End FileReader

	public void createAndShowGUI() {
		// Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Create and set up the content pane.
		JComponent newContentPane = createQuestionButtons();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}// End createAndShowGUI

	public void actionPerformed(ActionEvent e) {
		String actionID = e.getActionCommand();
		if (actionID.equals("Done") && selectorLimit == 5) {
			for(int i = 0; i < questions.length; i++){
				sort(i);
			}// End For
			arrayFull = true;
			frame.dispose();
		} // End if
	}// End actionPerformed

	public void sort(int indexToBeSorted) {
		int smallest = indexToBeSorted;
		Question temp;
		for (int i = smallest+1; i < questions.length; i++) {
			if (questions[smallest].getDiffcultyLevel() > questions[i]
					.getDiffcultyLevel()) {
				smallest = i;
			}// End if
		}// End for
		temp = questions[indexToBeSorted];
		questions[indexToBeSorted] = questions[smallest];
		questions[smallest] = temp;
	}// End sort

	public void itemStateChanged(ItemEvent e) {
		JCheckBox source = (JCheckBox) e.getItemSelectable();
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			int questionIndex = Integer.parseInt(source.getName().toString());
			Question searchfor = availableQuestions.get(questionIndex);
			for (int i = 0; i < questions.length; i++) {
				if (questions[i] == (searchfor)) {
					questions[i] = emptyQuestion;
					selectorLimit--;
				}// End if
			}// End for

			/*
			 * Reorganize an array. Put all non empty strings in Queue. Add
			 * strings from queue back into array until empty then fill in array
			 * with "".
			 */
			Queue<Question> myQueue = new LinkedList<Question>();
			for (int i = 0; i < questions.length; i++) {
				if (questions[i] != (emptyQuestion)) {
					myQueue.add(questions[i]);
				}
			}// End for
			int i = 0;
			while (!myQueue.isEmpty()) {
				questions[i] = myQueue.poll();
				i++;
			}// End while
			for (int j = i; j < questions.length; j++) {
				questions[j] = emptyQuestion;
			}// End for
		} else {
			if (selectorLimit < questions.length) {
				questions[selectorLimit] = availableQuestions.get(Integer
						.parseInt(source.getName()));
				selectorLimit++;
			} else {
				source.setSelected(false);
			}// End if else
		}// End if else
	}// End itemStateChange

	// Getters and Setters

	public boolean isArrayFull() {
		return arrayFull;
	}

	public void setArrayFull(boolean arrayFull) {
		this.arrayFull = arrayFull;
	}

	public Question[] getQuestions() {
		return questions;
	}

	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}

	// End Getters and Setters
}// End Class
