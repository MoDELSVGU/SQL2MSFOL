import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import net.sf.jsqlparser.JSQLParserException;
import sql2msfol.SQL2MSFOL;
import sql2msfol.select.NamingConvention;

public class Main {
	public static void main (String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
		SQL2MSFOL sql2msfol = new SQL2MSFOL();
		sql2msfol.setUpDataModelFromURL("src/main/resources/datamodel.json");
		sql2msfol.formalizeDataModel();
		List<String> tests = Arrays.asList(
				/* A boolean literal */
				"SELECT TRUE",
				"SELECT FALSE",
				"SELECT NULL",
				/* A integer literal */
				"SELECT 0",
				"SELECT -1",
				"SELECT 1",
				/* A string literal */
				"SELECT 'a string'",
				/* logical operations */
				"SELECT NOT TRUE",
				"SELECT NOT FALSE",
				"SELECT NOT NOT TRUE",
				"SELECT TRUE & TRUE",
				"SELECT FALSE | FALSE",
				/* comparison operations */
				"SELECT 1 = 1",
				"SELECT 1 >= 1",
				"SELECT 1 > 1",
				"SELECT 1 <= 1",
				"SELECT 1 <> 1",
				"SELECT 1 < 1"
				
		);
		tests.forEach(expr -> {
			try {
				NamingConvention.reset();
				System.out.println("--- %s ---".formatted(expr));
				sql2msfol.map(expr);
			} catch (JSQLParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
}
