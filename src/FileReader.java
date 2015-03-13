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
public class FileReader extends JPanel implements ItemListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame frame = new JFrame("Category Selector");
	private ArrayList<String> availableCategories = new ArrayList<String>();
	private String[] categories = new String[5];
	private Question[][] questions = new Question[5][5];
	private int selectorLimit = 0;
	private boolean arrayFull = false;
	private ArrayList<Question> category0 = new ArrayList<Question>();
	private ArrayList<Question> category1 = new ArrayList<Question>();
	private ArrayList<Question> category2 = new ArrayList<Question>();
	private ArrayList<Question> category3 = new ArrayList<Question>();
	private ArrayList<Question> category4 = new ArrayList<Question>();

	/**
	 * Turn into constructor and return the questions array in TAC Jeopardy
	 * 
	 * @param args
	 */
	public FileReader(GameBoard gb) {
		for (int i = 0; i < this.categories.length; i++) {
			this.categories[i] = "";
		}// End for
		this.createAndShowGUI();
	}// End main

	/**
	 * Read in available category names from file "Available Category"
	 */
	private void getAvailableCategoryNames() {
		String fileName = "Available Categories.txt";
		File myFile = new File(fileName);
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(myFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}// End try catch
		while (inputFile.hasNext()) {
			String category = inputFile.nextLine();
			availableCategories.add(category);
		}
	}// End getAvailableCategoryNames

	private void createAndShowGUI() {
		// Create and set up the window.
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Create and set up the content pane.
		JComponent newContentPane = createButtons();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public JComponent createButtons() {
		this.getAvailableCategoryNames();
		JPanel checkPanel = new JPanel(new GridLayout(0, 1));
		for (int i = 0; i < availableCategories.size(); i++) {
			JCheckBox category = new JCheckBox(availableCategories.get(i));
			category.setSelected(false);
			category.addItemListener(this);
			checkPanel.add(category);
		}// End for
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(this);
		checkPanel.add(doneButton);
		add(checkPanel, BorderLayout.LINE_START);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		return checkPanel;
	}// End FileReader

	/**
	 * Item Listener for Check Boxes
	 */
	public void itemStateChanged(ItemEvent e) {
		JCheckBox source = (JCheckBox) e.getItemSelectable();
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			String searchfor = source.getText();
			for (int i = 0; i < categories.length; i++) {
				if (categories[i].equals(searchfor)) {
					categories[i] = "";
					selectorLimit--;
				}// End if
			}// End for
			/*
			 * Reorganize an array. Put all non empty strings in Queue. Add
			 * strings from queue back into array until empty then fill in array
			 * with "".
			 */
			Queue<String> myQueue = new LinkedList<String>();
			for (int i = 0; i < categories.length; i++) {
				if (!categories[i].equals("")) {
					myQueue.add(categories[i]);
				}
			}// End for
			int i = 0;
			while (!myQueue.isEmpty()) {
				categories[i] = myQueue.poll();
				i++;
			}// End while
			for (int j = i; j < categories.length; j++) {
				categories[j] = "";
			}// End for
		} else {
			if (selectorLimit < categories.length) {
				categories[selectorLimit] = source.getText();
				selectorLimit++;
			} else {
				source.setSelected(false);
			}// End if else
		}// End if else
	}// End itemStateChange
	
	public void disposeFrame(){
		frame.dispose();
	}// End disposeFrame

	public void actionPerformed(ActionEvent e) {
		String actionID = e.getActionCommand();
		if (actionID.equals("Done")&&selectorLimit==5) {
			/*for (int i = 0; i < this.categories.length; i++) {
				this.readQuestions(this.categories[i], i);
			}*/ // End for
			/*for (int i = 0; i < this.categories.length; i++) {
				for(int j=0; j<5; j++){
					System.out.print(j+" ");
					questions[i][j] = getCorrectCategoryArray(i).get(j);
				}
				System.out.println();
				QuestionSelector qs;
				qs = new QuestionSelector(getCorrectCategoryArray(i), this, i);
				qs.setName(getCorrectCategoryArray(i).get(0).getCategory());
			
				// NEED A BETTER WAY TO WAIT UNTIL THE ARRAY IS FULL
				while(qs.isArrayFull()==false){

				}/ // End while
			}*/ // End for
			arrayFull = true;
		}// End if
	}// End action Performed

	public ArrayList<Question> getCorrectCategoryArray(int index) {
		ArrayList<Question> returnValue = null;
		if (index == 0) {
			returnValue = category0;
		} else if (index == 1) {
			returnValue = category1;
		} else if (index == 2) {
			returnValue = category2;
		} else if (index == 3) {
			returnValue = category3;
		} else if (index == 4) {
			returnValue = category4;
		}// End if else
		return returnValue;
	}// End getCorrectCategoryArray
	
	// Getters and Setters

	public Question[][] getQuestions() {
		return questions;
	}

	public String[] getCategories() {
		return categories;
	}

	public void setCategories(String[] categories) {
		this.categories = categories;
	}

	public void setQuestions(Question[][] questions) {
		this.questions = questions;
	}

	public boolean isArrayFull() {
		return arrayFull;
	}

	public void setArrayFull(boolean arrayFull) {
		this.arrayFull = arrayFull;
	}
	
	// End Getters and Setters
	
}// End Class
