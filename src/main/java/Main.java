import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;

import configurations.Context;
import datamodel.DataModelHolder;
import net.sf.jsqlparser.JSQLParserException;
import sql2msfol.SQL2MSFOL;
import sql2msfol.select.NamingConvention;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
		SQL2MSFOL sql2msfol = new SQL2MSFOL();
		sql2msfol.setUpDataModelFromURL("resources/datamodel.json");
		sql2msfol.formalizeDataModel();
		List<String> tests = Arrays.asList(
//				"SELECT TRUE",
//				"SELECT NOT EXISTS (SELECT students FROM Enrolment WHERE lecturers = caller)",
//				"SELECT NOT EXISTS (SELECT 1 FROM (SELECT s.age, e.lecturers FROM Student s JOIN Enrolment e ON e.students = s.Student_id) AS temp JOIN Lecturer l WHERE temp.age >= l.age AND l.Lecturer_id = temp.lecturers)"
//				"SELECT (SELECT age FROM Student WHERE Student_id = self) = (SELECT age FROM Lecturer WHERE Lecturer_id = caller)"
//				"SELECT (SELECT name FROM Student WHERE Student_id = self) = user"
//				"SELECT name = user FROM Student WHERE Student_id = self"
				"SELECT CASE WHEN name IS NULL THEN user IS NULL ELSE CASE WHEN user IS NULL THEN FALSE ELSE name = user END END FROM Student WHERE Student_id = self"
		);
		tests.forEach(expr -> {
			try {
				NamingConvention.reset();
				DataModelHolder.setDataModel(sql2msfol.getDataModel());
				DataModelHolder
						.setContext(Arrays.asList(new Context("user", "String"), new Context("self", "Student")));
				System.out.println("--- %s ---".formatted(expr));
				sql2msfol.map(expr);
			} catch (JSQLParserException e) {
				e.printStackTrace();
			}
		});
	}

}