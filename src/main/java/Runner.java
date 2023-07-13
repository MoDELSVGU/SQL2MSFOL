
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import modeling.sql.fol.configurations.Context;
import modeling.sql.fol.datamodel.DataModelHolder;
import modeling.sql.fol.sql2msfol.SQL2MSFOL;
import modeling.sql.fol.sql2msfol.select.NamingConvention;

public class Runner {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {

		String dataModelPath = null;
		List<Context> context = new ArrayList<Context>();
		String sql = null;

		for (int i = 0; i < args.length; i++) {
			if ("-dm".equalsIgnoreCase(args[i])) {
				dataModelPath = args[++i];
			} else if ("-ctx".equalsIgnoreCase(args[i])) {
				while (!args[i + 1].contains("-")) {
					String[] s = args[i + 1].split(":");
					Context ctx = new Context(s[0], s[1]);
					context.add(ctx);
					i++;
				}
			} else if ("-sql".equalsIgnoreCase(args[i])) {
				sql = args[i + 1];
			}
		}

		if (dataModelPath == null) {
			throw new Exception("Missing input datamodel file path!");
		}

		if (sql == null) {
			throw new Exception("Missing SQL statement!");
		}

		SQL2MSFOL sql2msfol = new SQL2MSFOL();
		sql2msfol.setUpDataModelFromURL(dataModelPath);
		sql2msfol.formalizeDataModel();
		NamingConvention.reset();
		DataModelHolder.setDataModel(sql2msfol.getDataModel());
		DataModelHolder.setContext(context);

		System.out.println(String.format("--- %1$s ---", sql));
		sql2msfol.map(sql);
	}

}