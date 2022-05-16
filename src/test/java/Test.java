import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import datamodel.DataModelHolder;
import net.sf.jsqlparser.JSQLParserException;
import sql2msfol.SQL2MSFOL;
import sql2msfol.select.NamingConvention;

public class Test {
	public static void main (String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
		SQL2MSFOL sql2msfol = new SQL2MSFOL();
		sql2msfol.setUpDataModelFromURL("src/main/resources/datamodel.json");
		sql2msfol.formalizeDataModel();
		List<String> tests = Arrays.asList(
				/* A boolean literal */
//				"SELECT TRUE",
//				"SELECT FALSE",
//				"SELECT NULL",
//				/* A integer literal */
//				"SELECT 0",
//				"SELECT -1",
//				"SELECT 1",
//				/* A string literal */
//				"SELECT 'a string'",
//				/* logical operations */
//				"SELECT NOT TRUE",
//				"SELECT NOT FALSE",
//				"SELECT NOT NOT TRUE",
//				"SELECT TRUE & TRUE",
//				"SELECT FALSE | FALSE",
//				/* comparison operations */
//				"SELECT 1 = 1",
//				"SELECT 1 >= 1",
//				"SELECT 1 > 1",
//				"SELECT 1 <= 1",
//				"SELECT 1 <> 1",
//				"SELECT 1 < 1",
//				/* CASE */
//				"SELECT CASE WHEN TRUE THEN 1 ELSE 0 END",
//				"SELECT CASE WHEN TRUE THEN CASE WHEN FALSE THEN 0 ELSE 1 END ELSE 2 END",
//				/* IS NULL */
//				"SELECT 1 IS NULL",
//				"SELECT 'a string' IS NULL",
//				"SELECT NULL IS NULL",
//				"SELECT 1 IS NOT NULL",
//				/* EXISTS subselect */
//				"SELECT EXISTS (SELECT 1)",
//				/* Context dependent */
//				"SELECT 1 FROM Student",
//				"SELECT name FROM Student",
//				"SELECT name, age FROM Student",
//				"SELECT age = 18 FROM Student",
//				"SELECT EXISTS (SELECT age FROM Student)",
//				"SELECT age FROM (SELECT age, name FROM Student)",
//				"SELECT Student_id FROM Student",
				/* WHERE */
				"SELECT age FROM Student WHERE Student_id = 1 AND name = 'Hoang'"
		);
		tests.forEach(expr -> {
			try {
				NamingConvention.reset();
				DataModelHolder.setDataModel(sql2msfol.getDataModel());
				System.out.println("--- %s ---".formatted(expr));
				sql2msfol.map(expr);
			} catch (JSQLParserException e) {
				e.printStackTrace();
			}
		});
	}
	
}
