public class Rule {

	private String tag;
	private String suffixe;
	private String newTag;
	private String newSuffixe;
	

// Constructeurs
	public Rule(String tag, String suf, String newTag, String newSuf) {
		this.tag = tag;
		this.suffixe = suf;
		this.newTag = newTag;
		this.newSuffixe = newSuf;
	}
	
	
// Accesseurs
	// Get
	public String getTag() { return tag; }
	public String getSuffixe() { return suffixe; }
	public String getNewTag() { return newTag; }
	public String getNewSuffixe() { return newSuffixe; }

	//Set
	public void setTag(String tag) { this.tag = tag; }
	public void setSuffixe(String suffixe) { this.suffixe = suffixe; }
	public void setNewTag(String newTag) { this.newTag = newTag; }
	public void setNewSuffixe(String newSuffixe) { this.newSuffixe = newSuffixe; }

	public String toString() {
		return tag + ":" + suffixe + " => " + newTag + ":" + newSuffixe;
	}
	
// Methodes
	// Yen a pas encore
}
