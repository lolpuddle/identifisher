import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


//class to parse csv file of fish data
public class FishReader {
	public static void main(String[] args){
		FishReader fish = new FishReader();
		fish.read();
	}
	
	public void read(){
		String csvFile = "src/fishcsv.txt";
		BufferedReader br = null;
		String entry = "";
		String split = ",";
		
		try{
			br = new BufferedReader(new FileReader(csvFile));
			while ((entry = br.readLine()) != null) {
				String[] fish = entry.split(split);
				String name = fish[0];
				String color = fish[1];
				String pattern = fish[2];
				String shape = fish[3];
				System.out.println("name: " + name + " color: " + color + 
						" pattern: " + pattern + " shape: " + shape);
				
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
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
	}
}
