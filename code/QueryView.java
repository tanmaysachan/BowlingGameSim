/* QueryView.java
 *
 *  Version:
 * 		 $Id$
 * 
 
 */

/**
 * Class for GUI components need to get a queries like top player and highest score
 */

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

/**
 * Constructor for GUI used to Query
 *  
 */

public class QueryView implements ActionListener {

	private int maxSize;

	private JFrame win;
	private JButton abort, finished;
	private JLabel nickLabel, fullLabel, emailLabel;
	private JTextField nickField, fullField, emailField;
	private String nick, full, email;
	public JList partyList;
	public Vector party;

	private boolean done;

	private String selectedNick, selectedMember;
	private ControlDeskView addParty;

	public QueryView(ControlDeskView v) {

		addParty=v;	
		done = false;

		win = new JFrame("Search");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new BorderLayout());
		JPanel partyPanel = new JPanel();

		party = new Vector();
		Vector empty = new Vector();
		empty.add("(Empty)");
//
		partyList = new JList(empty);
		partyList.setFixedCellWidth(200);
		partyList.setVisibleRowCount(5);

		JScrollPane partyPane = new JScrollPane(partyList);

		partyPanel.add(partyPane);
		
		// Patron Panel
		JPanel patronPanel = new JPanel();
		patronPanel.setLayout(new GridLayout(3, 1));
		patronPanel.setBorder(new TitledBorder("Enter Details"));

		JPanel nickPanel = new JPanel();
		nickPanel.setLayout(new FlowLayout());
		nickLabel = new JLabel("Nick Name");
		nickField = new JTextField("", 15);
		nickPanel.add(nickLabel);
		nickPanel.add(nickField);

		patronPanel.add(nickPanel);

		// Button Panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(4, 1));

		Insets buttonMargin = new Insets(4, 4, 4, 4);

		finished = new JButton("Search");
		JPanel finishedPanel = new JPanel();
		finishedPanel.setLayout(new FlowLayout());
		finished.addActionListener(this);
		finishedPanel.add(finished);

		abort = new JButton("Abort");
		JPanel abortPanel = new JPanel();
		abortPanel.setLayout(new FlowLayout());
		abort.addActionListener(this);
		abortPanel.add(abort);

		buttonPanel.add(abortPanel);
		buttonPanel.add(finishedPanel);

		// Clean up main panel
		colPanel.add(partyPanel, "West");
		colPanel.add(patronPanel, "Center");
		colPanel.add(buttonPanel, "East");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(abort)) {
			done = true;
			win.hide();
		}
		if (e.getSource().equals(finished)) {
			String result;
			nick = nickField.getText();
			try {
			    String res = ScoreHistoryFile.top_score(nick);
			    result = nick + "'s Highest Score = " + res;


			} catch (Exception e1) {
				System.err.println("File Error");
				result = "File Error";
			}
			party.add(result);
		    partyList.setListData(party);
		    
		    Vector vec = new Vector();
		    
		    try {
		    	vec = ScoreHistoryFile.getlastScores(nick);
		    }
		    catch (Exception e1) {
				result = "File Error";
				System.err.println("File Error wow");
			}
		    for (int i=0;i<vec.size();i++) {
		    	String s = vec.elementAt(i).toString();
				party.add(s);
			    partyList.setListData(party);
		    }
			try {
			    String res = ScoreHistoryFile.high_score();
			    result = "Top Player : " + res;
//			    System.out.println("Top Player = " + res[1]);
			    
			} catch (Exception e1) {
				result = "File Error";
				System.err.println("File Error");
			}
			party.add(result);
		    partyList.setListData(party);
		}

	}


}
