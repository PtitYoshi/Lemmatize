import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ApplicationLemmatizer {

	public static void main(String[] args) {
		ArrayList<Rule> rules = new ArrayList<Rule>();
		File f = new File("regles");
		
		generateRule(rules, f);
	}
	
	public static void generateRule(ArrayList<Rule> r, File f) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] regle = sCurrentLine.split(";");
				String[] part1 = regle[0].split(":");
				String[] part2 = regle[1].split(":");
				r.add(new Rule(part1[0], part1[1], part2[0], part2[1]));
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	// Notes : generer (grace aux regles) les différentes possibilité
	//         puis vérifier grace à jeux de mot qu'il existe
}
