/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

import java.util.*;
import java.io.*;

public class ScoreHistoryFile {

	private static String SCOREHISTORY_DAT = "SCOREHISTORY.DAT";

	public static void addScore(String nick, String date, String score)
		throws IOException, FileNotFoundException {

		String data = nick + "\t" + date + "\t" + score + "\n";

		RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
		out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
	}

	public static Vector getScores(String nick)
		throws IOException, FileNotFoundException {
		Vector scores = new Vector();

		BufferedReader in =
			new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String data;
		while ((data = in.readLine()) != null) {
			// File format is nick\tfname\te-mail
			String[] scoredata = data.split("\t");
			//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
			if (nick.equals(scoredata[0])) {
				scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
			}
		}
		return scores;
	}
	
	public static String top_score(String nick)
		throws IOException, FileNotFoundException {
		int multi = -1;
		 Writer writer 
         = new PrintWriter(System.out);
		BufferedReader in =
				new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String data;
		while ((data = in.readLine()) != null) {
			// File format is nick\tfname\te-mail
			String[] scoredata = data.split("\t");
			//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
			if (nick.equals(scoredata[0])) {
				int val = -1;
				try { 
		            val = Integer.parseInt(scoredata[2]);
		        }
		        catch (NumberFormatException e) { 
		  
		            // This is thrown when the String 
		            // contains characters other than digits 
		            System.out.println("Invalid String"); 
		        }
				if (val > multi) {
					multi = val;
				}
			}
		}
		return Integer.toString(multi);
	}
	
	public static String high_score()
		throws IOException, FileNotFoundException {
		int multi = -1;
		BufferedReader in =
				new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String data;
		while ((data = in.readLine()) != null) {
			// File format is nick\tfname\te-mail
			String[] scoredata = data.split("\t");
			//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
			int val = -1;
			try { 
	            val = Integer.parseInt(scoredata[2]);
	        }
	        catch (NumberFormatException e) { 
	  
	            // This is thrown when the String 
	            // contains characters other than digits 
	            System.out.println("Invalid String"); 
	        }
			if (val > multi) {
				multi = val;
			}
		}
		return Integer.toString(multi);
	}
	
	public static Vector getlastScores(String nick)
		throws IOException, FileNotFoundException {
		Vector scores = new Vector();
		Vector new_scores = new Vector();
		BufferedReader in =
			new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String data;
		while ((data = in.readLine()) != null) {
			// File format is nick\tfname\te-mail
			String[] scoredata = data.split("\t");
			//"Nick: scoredata[0] Date: scoredata[1] Score: scoredata[2]
			if (nick.equals(scoredata[0])) {
				String s = scoredata[1] + " \t " + scoredata[2] + "\t";
				scores.add(s);
			}
		}
	
		for(int i = scores.size() - 1; i >= 0; i--){
			new_scores.add(scores.elementAt(i));
		}
		return new_scores;
	}
}