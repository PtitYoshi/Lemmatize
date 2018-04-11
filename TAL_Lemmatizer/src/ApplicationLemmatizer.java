import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ApplicationLemmatizer {

	public static void main(String[] args) {
		// Les regles sont regroupées par la terminaison du prédicat.
		// Il suffit donc de parcourir les clés de la Map pour avoir toutes les terminaisons qu'on peut transformer.
		Map<String, ArrayList<Rule>> categorie = new HashMap<String, ArrayList<Rule>>();
		File f = new File("regles");
		
		generateRule(categorie, f);
		editWord(categorie, "manger");
	}
	
	public static void generateRule(Map<String, ArrayList<Rule>> c, File f) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] regle = sCurrentLine.split(";");
				String[] part1 = regle[0].split(":");
				String[] part2 = regle[1].split(":");
				if (c.get(part1[1]) == null) {
					c.put(part1[1], new ArrayList<Rule>());
				}
				c.get(part1[1]).add(new Rule(part1[0], part1[1], part2[0], part2[1]));
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/* TODO : generer (grace aux regles) les différentes possibilité
	 * puis vérifier grace à jeux de mot qu'il existe (récupérer l'api)
	 * */
	
	private static void editWord(Map<String, ArrayList<Rule>> c, String mot) {
		ArrayList<String> lstNewWords = new ArrayList<String>();
		
		for(Entry<String, ArrayList<Rule>> entry : c.entrySet()) {
			if (mot.endsWith(entry.getKey())) {
				//enlever la terminaison (la clés ci-dessus) du mot
				// pour chaque regle de entry.getValue(), ajouter le nouveau mot à la liste lstNewWords
			}
		}
	}
}
