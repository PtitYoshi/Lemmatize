import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import RequeterRezo.Mot;
import RequeterRezo.RequeterRezo;
import RequeterRezo.Terme;

public class ApplicationLemmatizer {
	
	public static void main(String[] args) {
		RequeterRezo rezo = new RequeterRezo("72h", 5000);
		HashMap<String, ArrayList<String>> res = new HashMap<String, ArrayList<String>>();
		res.put("existe", new ArrayList<String>());
		res.put("existePas", new ArrayList<String>());
		res.put("erreurs", new ArrayList<String>());

		
		// Les regles sont regroupées par la terminaison du prédicat.
		// Il suffit donc de parcourir les clés de la Map pour avoir toutes les terminaisons qu'on peut transformer.
		Map<String, ArrayList<Rule>> categorie = new HashMap<String, ArrayList<Rule>>();
		String rulesFileName = "regles";
		generateRule(categorie, rulesFileName);
		System.out.println("- - - - - - - - - - Création des regles");
		System.out.println(categorie);
		
		
		System.out.println("\n- - - - - - - - - - Mise en place du mot");
		ArrayList<String> mot_gramm = new ArrayList<String>();
		mot_gramm.add("manger");
		mot_gramm.addAll(getClaGrammFromWord(rezo, getMot(rezo, mot_gramm.get(0), res.get("erreurs")), res.get("erreurs")));
		System.out.println("Mot : '" + mot_gramm.get(0) + "'");
		System.out.println("Classe grammaticale : '" + mot_gramm + "'");
		
		
		System.out.println("\n- - - - - - - - - - Création des mots dérivés de '" + mot_gramm.get(0) + "'");
		editWord(rezo, categorie, res, mot_gramm);
		
		
		System.out.println("\n- - - - - - - - - - Résultats");
		System.out.println("Les mots existent :");
		System.out.println(res.get("existe"));
		System.out.println("\nLes mots n'existent pas :");
		System.out.println(res.get("existePas"));
		System.out.println("\nLes mots non vérifiés (suite à une erreur) :");
		System.out.println(res.get("erreurs"));
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
	
	private static void editWord(RequeterRezo rezo, Map<String, ArrayList<Rule>> c, Map<String, ArrayList<String>> res, ArrayList<String> m) {
		String mot = m.get(0);
		ArrayList<String> existe = res.get("existe");
		ArrayList<String> existePas = res.get("existePas");
		ArrayList<String> err = res.get("erreurs");
		
		for (Entry<String, ArrayList<Rule>> entry : c.entrySet()) {
			if (mot.endsWith(entry.getKey())) {
				String base = mot.substring(0, mot.length()-entry.getKey().length());
				for (Rule r : entry.getValue()) { /* Parcours de toutes les regles qui match avec la terminaison */
					for (String class_gramm : m) {
					
						if (r.getTag().equals(class_gramm)) { /* Verification que la regle s'applique bien pour ce mot (de cette classe grammaticale) */
							/* Verification que le mot n'est pas dans les exceptions */
							boolean finExc = false; // le mot n'y est pas
							for (String s : r.getLstException()) { if (base.endsWith(s)) finExc = true; } // true si le mots est dans les exceptions
							
							if (!finExc) { // si pas exception
								String newWord = base + r.getNewSuffixe(); // creation du nouveau mot
								/* Verification que le nouveau mot existe bien */
								Mot newMot = getMot(rezo, newWord, err);
								if (newMot != null) {
									/* Verification que le nouveau mot a bien la bonne class grammaticale */
									ArrayList<String> classGram = getClaGrammFromWord(rezo, newMot, err);
								
									for (String class_gramm_new : classGram) {
										// si la classe de la regle est la même que celle de rezo ou si la classe de rezo est null (non renseigné par exemple) ET pas d'erreur
										if (r.getNewTag().equals(class_gramm_new) && !err.contains(newWord)) {
											// Le mot existe, enlever du existe pas
											if (!existe.contains(class_gramm_new + ":" + newWord))
												existe.add(class_gramm_new + ":" + newWord);
											if (existePas.contains(r.getNewTag() + ":" + newWord))
												existePas.remove(class_gramm_new + ":" + newWord);
										} else if (!err.contains(newWord)) {
											if (!existePas.contains(r.getNewTag() + ":" + newWord) &&
													!existe.contains(class_gramm_new + ":" + newWord))
												existePas.add(r.getNewTag() + ":" + newWord);
										}
									}
								} else if (!err.contains(newWord)) {
									if (!existePas.contains(r.getNewTag() + ":" + newWord))
										existePas.add(r.getNewTag() + ":" + newWord);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static Mot getMot(RequeterRezo rezo, String s, ArrayList<String> err) {
		try {
			Mot m = rezo.requete(s, true);
			return m;
		} catch (IOException | InterruptedException | ArrayIndexOutOfBoundsException e) {
			err.add(s);
		}
		return null;
	}
	
	
	public static ArrayList<String> getClaGrammFromWord(RequeterRezo r, Mot m, ArrayList<String> err) {
		HashMap<String, ArrayList<Terme>> req = m.getRelations_sortantes();
		ArrayList<Terme> termes = req.get("r_pos");
		ArrayList<String> sortie = new ArrayList<String>();
		for (Terme t : termes) {
			String classe = null;
			switch (t.getTerme()) {
			case "Adj:" :
				classe = "adj";
				break;
			case "Nom:" :
				classe = "nom";
				break;
			case "Ver:Inf" :
			case "Ver:PPre" :
			case "Ver:" :
				classe = "verbe";
				break;
//			default :
//				sortie.add(t.getTerme());
			}
			if (!sortie.contains(classe) && classe != null) sortie.add(classe);
		}
		return sortie;
	}
}
