
public class Rule {

	private String tag;
	private String suffixe;
	private String newTag;
	private String newSuffixe;
	
	public Rule() {
		// TODO Auto-generated constructor stub
	}
	
	public Rule(String tag, String suf, String newTag, String newSuf)
	{
		this.tag=tag;
		this.suffixe=suf;
		this.newTag=newTag;
		this.newSuffixe=newSuf;
	}

	
	//Accesseurs
	
	public String getSuffixe() {
		return suffixe;
	}

	public void setSuffixe(String suffixe) {
		this.suffixe = suffixe;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNewTag() {
		return newTag;
	}

	public void setNewTag(String newTag) {
		this.newTag = newTag;
	}

	public String getNewSuffixe() {
		return newSuffixe;
	}

	public void setNewSuffixe(String newSuffixe) {
		this.newSuffixe = newSuffixe;
	}

}
