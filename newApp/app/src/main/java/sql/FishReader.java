package sql;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;


//class to parse csv file of fish data
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
			br = new BufferedReader(new StringReader("\"Channel Darter\", \"brown\", \"speckled\", \"fusiform\"\n\"Trout\", \"brown orange\", \"none\", \"fusiform\"\n\"Lake Sturgeon\", \"brown\", \"none\", \"fusiform\"\n\"Silver Chub\", \"silver\", \"none\", \"elongated compressed\"\n\"Minnow\", \"silver\", \"striped\", \"elongated\" \n\"Warmouth\", \"yellow\", \"striped\", \"elongated\"\n\"Eastern Sand Darter\", \"yellow silver\", \"spotted\", \"elongated\"\n\"Silver Shiner\", \"silver\", \"striped\", \"elongated\""));
			while ((entry = br.readLine()) != null) {
				String[] fish = entry.split(split);
				ar.add(fish);
				String name = fish[0];
				String color = fish[1];
				String pattern = fish[2];
				String shape = fish[3];
				System.out.println("name: " + name + " color: " + color + 
						" pattern: " + pattern + " shape: " + shape);
				
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
