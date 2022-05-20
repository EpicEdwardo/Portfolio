package photo_renamer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PhotoRenamer {
	private final JFrame frame;
	private final JPanel mainPanel;
	private final JPanel panelForButtons;
	private final JPanel panelForTags;
	private final JTextField input;
	private final JTextArea listOfFiles;
	private final JTextArea listOfTags;
	private final JComboBox<String> previousTags;
	private final JButton add;
	private final JButton delete;
	private final JButton undo;
	
	private PhotoRenamer() {
		frame = new JFrame("Photo Renamer");	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(500,300));
		
		add = new JButton("Add tag");
		//ActionListener addListener 
		add.setEnabled(false);
		
		delete = new JButton("Delete tag");
		//ActionListener deleteListener 
		delete.setEnabled(false);

		undo = new JButton("Undo tag");
		//ActionListener undoListener 
		undo.setEnabled(false);
		
		// Set JTextArea with row, column height
		listOfFiles = new JTextArea(15,20);
		listOfFiles.setEditable(false);
		
		listOfTags = new JTextArea(15,10);
		listOfTags.setEditable(false);

		input = new JTextField(10);
		input.setEnabled(false);
		
		String[] oldTags = {"dank", "memes", "hi", "darwin"};
		previousTags = new JComboBox<String>(oldTags);
		previousTags.setEnabled(true);
		previousTags.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				input.setText((String)previousTags.getSelectedItem());
			}
		});
		
		

		Container content = frame.getContentPane();

		panelForButtons = new JPanel();
		panelForButtons.add(add, BorderLayout.LINE_START);
		panelForButtons.add(delete, BorderLayout.CENTER);
		panelForButtons.add(undo, BorderLayout.LINE_END);
		panelForButtons.add(input, BorderLayout.PAGE_START);
		mainPanel.add(panelForButtons, BorderLayout.PAGE_END);
	
		panelForTags = new JPanel();
		panelForTags.add(listOfTags, BorderLayout.LINE_START);
		panelForTags.add(previousTags, BorderLayout.PAGE_START);

		mainPanel.add(panelForTags, BorderLayout.LINE_END);
		mainPanel.add(listOfFiles, BorderLayout.LINE_START);
		
		
		mainPanel.add(panelForTags);
		
		content.add(mainPanel);
		
	}

	
	private void createAndShowGui() {
		// The following three lines will be included in basically every GUI
		// you see. If you don't include EXIT_ON_CLOSE, your application won't
		// close properly!
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		PhotoRenamer r = new PhotoRenamer();
		r.createAndShowGui();
	}
	
		 	
}
