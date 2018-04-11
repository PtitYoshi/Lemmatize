import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ApplicationLemmatizer {
	
	/* TODO : vérifier grace à l'api jdm que les mots générés existent */

	public static void main(String[] args) {
		// Les regles sont regroupées par la terminaison du prédicat.
		// Il suffit donc de parcourir les clés de la Map pour avoir toutes les terminaisons qu'on peut transformer.
		Map<String, ArrayList<Rule>> categorie = new HashMap<String, ArrayList<Rule>>();
		String nom = "regles";
		
		generateRule(categorie, nom);
//		System.out.println("\n- - - - - - - - - - Création des regles");
//		System.out.println(categorie);
		
		String mot = "manger";
		ArrayList<String> lstDerivation = editWord(categorie, mot);
		System.out.println("\n- - - - - - - - - - Création des mots dérivés de '" + mot + "'");
		System.out.println(lstDerivation);
		
		System.out.println("\n- - - - - - - - - - Vérification de l'existance des mots");
	}
	
	private static void generateRule(Map<String, ArrayList<Rule>> c, String nom) {
		File f = new File(nom);
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
	
	private static ArrayList<String> editWord(Map<String, ArrayList<Rule>> c, String mot) {
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
			}
		}
		return lstNewWords;
	}
}
