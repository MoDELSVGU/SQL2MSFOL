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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import modeling.sql.fol.datamodel.DataModelHolder;
import modeling.sql.fol.sql2msfol.SQL2MSFOL;
import modeling.sql.fol.sql2msfol.select.NamingConvention;

/**************************************************************************
 * Copyright 2020 Vietnamese-German-University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * @author: ngpbh
 ***************************************************************************/

public class Runner {
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, Exception {
		Configuration c = new Configuration();

		SQL2MSFOL sql2msfol = new SQL2MSFOL();
		sql2msfol.setUpDataModelFromURL(c.getDataModel());
		sql2msfol.formalizeDataModel();
		NamingConvention.reset();
		DataModelHolder.setDataModel(sql2msfol.getDataModel());
		DataModelHolder.setContext(c.getContext());
		sql2msfol.map(c.getSql());

	}
}
