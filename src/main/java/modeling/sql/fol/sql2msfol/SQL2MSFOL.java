/**************************************************************************
 Copyright 2020 Vietnamese-German-University 
 Copyright 2023 ETH Zurich
 
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy of
 the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 License for the specific language governing permissions and limitations under
 the License.
 
 @author: hoangnguyen (hoang.nguyen@inf.ethz.ch)
 ***************************************************************************/

package modeling.sql.fol.sql2msfol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import modeling.data.entities.DataModel;
import modeling.sql.fol.visitor.SelectVisitor;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

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
