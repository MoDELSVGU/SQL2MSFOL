import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import configurations.Context;
import datamodel.DataModelUtils;
import net.sf.jsqlparser.JSQLParserException;
import sql2msfol.SQL2MSFOL;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
		SQL2MSFOL sql2msfol = new SQL2MSFOL();
		sql2msfol.setUpDataModelFromURL("resources/datamodel.json");
		List<String> tests = Arrays.asList(
				/* A boolean literal */
//				"SELECT TRUE", "SELECT FALSE", "SELECT NULL"
				/* A integer literal */
//				, "SELECT 0", "SELECT -1", "SELECT 1"
//				/* A string literal */
//				, "SELECT 'a string'"
//				/* logical operations */
//				, "SELECT NOT TRUE", "SELECT NOT FALSE", "SELECT NOT NOT TRUE", "SELECT TRUE & TRUE", "SELECT FALSE | FALSE"
//				/* comparison operations */
//				, "SELECT 1 = 1", "SELECT 1 >= 1", "SELECT 1 > 1", "SELECT 1 <= 1", "SELECT 1 <> 1", "SELECT 1 < 1"
//				/* CASE */
//				, "SELECT CASE WHEN TRUE THEN 1 ELSE 0 END"
//				, "SELECT CASE WHEN TRUE THEN CASE WHEN FALSE THEN 0 ELSE 1 END ELSE 2 END"
//				/* IS NULL */
//				, "SELECT 1 IS NULL", "SELECT 'a string' IS NULL", "SELECT NULL IS NULL", "SELECT 1 IS NOT NULL"
//				/* EXISTS subselect */
//				, "SELECT EXISTS (SELECT 1)"
				/* single-valued subselect*/
//				, "SELECT 2 = (SELECT 1)"
//				/* Context dependent */
//				, "SELECT 1 FROM Student", "SELECT name FROM Student", "SELECT name, age FROM Student"
//				, "SELECT s.name FROM Student AS s", "SELECT age = 18 FROM Student"
//				, "SELECT EXISTS (SELECT age FROM Student)", "SELECT age FROM (SELECT age, name FROM Student)"
//				, "SELECT Student_id FROM Student", "SELECT students FROM Enrolment"
//				/* WHERE */
//				, "SELECT age FROM Student WHERE Student_id = 1 AND name = 'Hoang'"
//				, "SELECT name FROM (SELECT name, age FROM Student) AS temp WHERE age > 19"
//				, "SELECT name FROM (SELECT name, age FROM Student WHERE age > 19) AS temp WHERE age > 19"
//				/* JOIN */
//				, "SELECT e.lecturers, s.name FROM Student AS s JOIN Enrolment AS e"
//				, "SELECT 1, s1.name, s2.age FROM Student AS s1 JOIN Student AS s2"
//				, "SELECT temp2.age FROM (SELECT 1 AS res) AS temp1 JOIN (SELECT age FROM Student) AS temp2"
//				/* JOIN ON */
//				, "SELECT e.lecturers, s.name FROM Student AS s JOIN Enrolment AS e ON e.students = s.Student_id"
//				/* JOIN WHERE */
//				, "SELECT e.lecturers, s.name FROM Student AS s JOIN Enrolment AS e WHERE e.students = s.Student_id"
//				/* JOIN ON WHERE */
//				, "SELECT e.lecturers, s.name FROM Student AS s JOIN Enrolment AS e ON e.students = s.Student_id WHERE s.age > 19"
//				"SELECT TRUE"
//				"SELECT NOT EXISTS (SELECT students FROM Enrolment WHERE lecturers = caller)"
//				"SELECT age >= 18 FROM Student WHERE Student_id = self"
//				"SELECT NOT EXISTS (SELECT 1 FROM (SELECT s.age, e.lecturers FROM Student AS s JOIN Enrolment AS e ON e.students = s.Student_id) AS temp JOIN Lecturer AS l WHERE temp.age >= l.age AND l.Lecturer_id = temp.lecturers)"
//				, "SELECT (SELECT age FROM Student WHERE Student_id = self) = (SELECT age FROM Lecturer WHERE Lecturer_id = caller)"
//				"SELECT (SELECT name FROM Student WHERE Student_id = self) = user"
//				"SELECT name = user FROM Student WHERE Student_id = self"
				"SELECT CASE WHEN name IS NULL THEN user IS NULL ELSE CASE WHEN user IS NULL THEN FALSE ELSE name = user END END FROM Student WHERE Student_id = self"
		);
		tests.forEach(expr -> {
			try {
				DataModelUtils.setDataModel(sql2msfol.getDataModel());
				DataModelUtils
						.setContext(Arrays.asList(new Context("user", "String"), new Context("self", "Student"), new Context("caller", "Lecturer")));
				System.out.println(String.format("--- %s ---", expr));
				sql2msfol.map(expr);
			} catch (JSQLParserException e) {
				e.printStackTrace();
			}
		});
	}

}