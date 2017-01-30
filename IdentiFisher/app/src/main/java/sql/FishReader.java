package sql;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


/**
 * Data Processing Class
 * This Class parses a file (or string) to derive data about all the Fish we need to identify
 *
 * Author: Shani Murray
 *
 * Edits: Christopher McDonald - Android does not like the file directory, used a String Reader instead
 * 							   - Changed the return type of method read() so we can use the information
 * 							   - Removed any sysout operations, cannot be used in Android Studio
 *
 * TODO Make the FileReader work, Chris had trouble actually finding the file
 */
public class FishReader {
	public static void main(String[] args){
		FishReader fish = new FishReader();
		fish.read();
	}
	
	public String[][] read(){
		String csvFile = "../fishcsv.txt";
		BufferedReader br = null;
		String entry = "";
		String split = ",";
		ArrayList<String[]> ar = new ArrayList<String[]>();
		
		try{
			br = new BufferedReader(new StringReader("" +
					"Channel Darter,Brown,Speckled,Fusiform\n" +
					"Trout,Brown Orange,None,Fusiform\n" +
					"Lake Sturgeon,Brown,none,Fusiform\n" +
					"Silver Chub,Silver,None,Elongated Compressed\n" +
					"Minnow,Silver,Striped,Elongated\n" +
					"Warmouth,Yellow,Striped,Elongated\n" +
					"Eastern Sand Darter,Yellow Silver,Spotted,Elongated\n" +
					"Silver Shiner,Silver,Striped,Elongated"));
			while ((entry = br.readLine()) != null) {
				String[] fish = entry.split(split);
				ar.add(fish);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e){
					e.printStackTrace();
				}
			}
		}
		return ar.toArray(new String[ar.size()][4]);
	}
}
