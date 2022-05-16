import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import sql2msfol.SQL2MSFOL;

public class Main {
	public static void main (String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
		SQL2MSFOL sql2msfol = new SQL2MSFOL();
		sql2msfol.setUpDataModelFromURL("src/main/resources/datamodel.json");
		sql2msfol.formalizeDataModel();
		sql2msfol.map("SELECT TRUE");
	}
	
}
