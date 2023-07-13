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

package modeling.sql.fol.sql2msfol.utils;

import java.util.Map;

import modeling.data.entities.Association;
import modeling.data.entities.DataModel;
import modeling.data.entities.Entity;

public class DmUtils {

	// TODO: Remove same function in SQLSI and SQL2MSFOL and others if any
	public static Association getAssociation(DataModel dataModel, String association) {
		if (dataModel.getAssociations() == null || dataModel.getAssociations().isEmpty()) {
			return null;
		} else {
			for (Association as : dataModel.getAssociations()) {
				if (as.getName().equalsIgnoreCase(association)) {
					return as;
				} else {
					continue;
				}
			}
		}
		return null;
	}

	// TODO: Remove same function in SQL2MSFOL and others if any
	public static Entity getEntity(DataModel dataModel, String entityName) {
		if (dataModel.getEntities() == null || dataModel.getEntities().isEmpty()) {
			return null;
		} else {
			for (Map.Entry<String, Entity> entry : dataModel.getEntities().entrySet()) {
				if (entityName.equals(entry.getValue().getName())) {
					return entry.getValue();
				}
			}
		}
		return null;
	}
}
