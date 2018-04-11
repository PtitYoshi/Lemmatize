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
		editWord(categorie, "manquer");
	}
	
	public static void generateRule(Map<String, ArrayList<Rule>> c, File f) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] regle = sCurrentLine.split(";");
				String[] predicat = regle[0].split(":");
				String[] conclusion = regle[1].split(":");
				if (c.get(predicat[1]) == null) {
					c.put(predicat[1], new ArrayList<Rule>());
				}
				ArrayList<String> exc = new ArrayList<String>();
				if (regle.length == 3) {
					String[] exception = regle[2].split(",");
					for (String s : exception) {
						exc.add(s);
					}
				}
				c.get(predicat[1]).add(new Rule(predicat[0], predicat[1], conclusion[0], conclusion[1], exc));
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
		
		for (Entry<String, ArrayList<Rule>> entry : c.entrySet()) {
			if (mot.endsWith(entry.getKey())) {
				String base = mot.substring(0, mot.length()-entry.getKey().length());
				for (Rule r : entry.getValue()) {
					boolean finExc = false;
					for (String s : r.getLstException()) {
						if (base.endsWith(s)) finExc = true;
					}
					if (!finExc) {
						String newWord = base + r.getNewSuffixe();
						lstNewWords.add(newWord);
					}
				}
				// pour chaque regle de entry.getValue(), ajouter le nouveau mot à la liste lstNewWords
			}
		}
		
		System.out.println(lstNewWords);
	}
}
