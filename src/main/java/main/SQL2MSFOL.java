package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.vgu.dm2schema.dm.DataModel;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import sql2msfol.validator.SQL2MSFOLUnsupportedException;
import sql2msfol.validator.SQLValidator;
import visitor.MySelectVisitor;

public class SQL2MSFOL {
	public static final List<String> testcases = Arrays.asList(
			"SELECT 1 AS test"
			,"SELECT 'Hoang' AS test"
			,"SELECT TRUE AS test"
			,"SELECT TRUE AS test1, 'HOANG' AS test2"
			,"SELECT TRUE & TRUE AS test"
			,"SELECT NOT TRUE AS test"
			,"SELECT TRUE | TRUE AS test"
			,"SELECT CASE WHEN TRUE THEN TRUE ELSE FALSE END AS test"
			,"SELECT CASE WHEN TRUE THEN CASE WHEN TRUE THEN FALSE ELSE TRUE END ELSE FALSE END AS test"
			,"SELECT 1 IS NULL AS test"
			,"SELECT 1 IS NOT NULL AS test"
			,"SELECT 1 = 1 AS test"
			,"SELECT 1 > 1 AS test"
			,"SELECT 1 < 1 AS test"
			,"SELECT 1 >= 1 AS test"			
			,"SELECT 1 <= 1 AS test"
			,"SELECT 1 <> 1 AS test"
			,"SELECT EXISTS (SELECT 1 AS test) AS test"
			,"SELECT l.name AS lname FROM Lecturer l", 
			"SELECT l.name = 'Hoang' AS test FROM Lecturer l",
			"SELECT l.name AS lname, l.age AS lage FROM Lecturer l", 
			"SELECT TRUE AS test FROM Lecturer l",
			"SELECT CASE WHEN l.name IS NOT NULL THEN l.name ELSE 'no-name' END as lname FROM Lecturer l",
			"SELECT MAX(l.age) AS max FROM Lecturer l");

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
		for (String testcase : testcases) {
			System.out.println(String.format("; %s", testcase));

			Environment.initialize();

			DatamodelHolder.initialize();
			File dataModelFile = new File("src/main/resources/Uni.json");
			DatamodelHolder.getInstance().setDatamodel(
					new DataModel(new JSONParser().parse(new FileReader(dataModelFile.getAbsolutePath()))));

			final String sqlString = testcase;
			Statement sqlStatement = CCJSqlParserUtil.parse(sqlString);
			if (!SQLValidator.validate(sqlStatement)) {
				throw new SQL2MSFOLUnsupportedException();
			}
			PlainSelect plainSelect = (PlainSelect) ((Select) sqlStatement).getSelectBody();
			MySelectVisitor visitor = new MySelectVisitor();
			plainSelect.accept(visitor);

			System.out.println();
		}
	}
}