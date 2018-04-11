import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class ApplicationLemmatizer {

	public static void main(String[] args) throws FileNotFoundException 
	{
		ArrayList<Rule> grammaire = new ArrayList<Rule>();
		File f=new File("rules");
		
		generateRule(grammaire, f);
	}
	
	public static void generateRule(ArrayList<Rule> g, File f) throws FileNotFoundException
	{
		BufferedReader br = new BufferedReader(new FileReader(f));
	}

}
