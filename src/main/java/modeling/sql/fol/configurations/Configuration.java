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

package modeling.sql.fol.configurations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Configuration {
	private String dataModel;
	private String sql;
	private List<Context> context;

	private static final String ENV_DATAMODEL = "DM";
	private static final String ENV_SQL = "SQL";
	private static final String ENV_CONTEXT = "CTX";

	public String getDataModel() {
		return dataModel;
	}

	public void setDataModel(String dataModel) {
		this.dataModel = dataModel;
	}

	public List<Context> getContext() {
		return context;
	}

	public void setContext(List<Context> context) {
		this.context = context;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Configuration() {
		final Map<String, String> env = System.getenv();

		final String dataModelPath = env.get(ENV_DATAMODEL);
		if (dataModelPath != null) {
			setDataModel(dataModelPath);
		}

		final String context = env.get(ENV_CONTEXT);
		List<Context> vars = new ArrayList<Context>();
		if (context != null && context != "") {
			List<String> sVars = Arrays.asList(context.split(","));
			for (String sVar : sVars) {
				String[] parts = sVar.split(":");
				Context var = new Context(parts[0], parts[1]);
				vars.add(var);
			}
		}
		setContext(vars);

		final String sql = env.get(ENV_SQL);
		if (sql != null) {
			setSql(sql);
		}
	}

}
