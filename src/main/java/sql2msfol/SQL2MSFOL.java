package sql2msfol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.vgu.dm2schema.dm.DataModel;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import visitor.SelectVisitor;

public class SQL2MSFOL {
	public DataModel getDataModel() {
		return dataModel;
	}

	private DataModel dataModel;

	public void setUpDataModelFromURL(String url) throws FileNotFoundException, IOException, ParseException, Exception {
		File dataModelFile = new File(url);
		JSONArray dataModelJSONArray = (JSONArray) new JSONParser().parse(new FileReader(dataModelFile));
		DataModel context = new DataModel(dataModelJSONArray);
		this.dataModel = context;
	}
	
	public void formalizeDataModel() {
		DM2MSFOL.formalize(this.dataModel);
	}
	
	public void map(String sql) throws JSQLParserException {
		Statement statementSql = CCJSqlParserUtil.parse(sql);
		SelectVisitor visitor = new SelectVisitor(true);
		visitor.setAlias(new Alias("main"));
		statementSql.accept(visitor);
//		visitor.formalize();
	}

}
