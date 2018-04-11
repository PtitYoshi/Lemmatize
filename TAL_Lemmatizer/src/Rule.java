import java.util.ArrayList;

public class Rule {

	private String tag;
	private String suffixe;
	private String newTag;
	private String newSuffixe;
	private ArrayList<String> lstException;
	

// Constructeurs
	public Rule(String tag, String suf, String newTag, String newSuf, ArrayList<String> al) {
		this.tag = tag;
		this.suffixe = suf;
		this.newTag = newTag;
		this.newSuffixe = newSuf;
		this.lstException = al;
	}
	
	
// Accesseurs
	// Get
	public String getTag() { return tag; }
	public String getSuffixe() { return suffixe; }
	public String getNewTag() { return newTag; }
	public String getNewSuffixe() { return newSuffixe; }
	public ArrayList<String> getLstException() { return lstException; }

	//Set
	public void setTag(String tag) { this.tag = tag; }
	public void setSuffixe(String suffixe) { this.suffixe = suffixe; }
	public void setNewTag(String newTag) { this.newTag = newTag; }
	public void setNewSuffixe(String newSuffixe) { this.newSuffixe = newSuffixe; }
	public void setLstException(ArrayList<String> lstException) { this.lstException = lstException; }

	public String toString() {
		if (lstException.size() > 0) {
			return tag + ":" + suffixe + " => " + newTag + ":" + newSuffixe + " (exc:" + lstException + ")";
		} else {
			return tag + ":" + suffixe + " => " + newTag + ":" + newSuffixe;
		}
	}
	
// Methodes
}
