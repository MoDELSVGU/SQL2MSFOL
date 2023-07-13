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

package modeling.sql.fol.datamodel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import modeling.data.entities.Association;
import modeling.data.entities.DataModel;
import modeling.data.utils.DmUtils;
import modeling.sql.fol.configurations.Context;
import net.sf.jsqlparser.schema.Column;

public class DataModelHolder {
	private static DataModel dataModel;
	private static Set<AssociationExtended> associations;
	private static List<Context> context;

	public static DataModel getDataModel() {
		return dataModel;
	}

	public static void setDataModel(DataModel dm) {
		dataModel = dm;
		associations = new HashSet<AssociationExtended>();
		Set<Association> associations = dm.getAssociations();
		for (Association assoc : associations) {
			String name = assoc.getLeftEnd().getAssociation();
			AssociationExtended assocExtended = new AssociationExtended(name, assoc.getLeftEnd(), assoc.getRightEnd());
			associations.add(assocExtended);
		}
	}

	public static Set<AssociationExtended> getAssociations() {
		return associations;
	}

	public static AssociationExtended getAssociationExtended(String name) {
		for (Association assoc : DataModelHolder.getDataModel().getAssociations()) {
			if (assoc.getName().equals(name)) {
				if (assoc instanceof AssociationExtended) {
					return (AssociationExtended) assoc;
				}
			}
		}
		return null;
	}

	public static Association getAssociation(String name) {
		for (Association assoc : DataModelHolder.getDataModel().getAssociations()) {
			if (assoc.getName().equals(name)) {
				if (!(assoc instanceof AssociationExtended)) {
					return (AssociationExtended) assoc;
				}
			}
		}
		return null;
	}

	public static List<Context> getContext() {
		return context;
	}

	public static void setContext(List<Context> context) {
		DataModelHolder.context = context;
	}

	public static boolean matchContext(Column tableColumn) {
		for (Context ctx : context) {
			if (ctx.getVar().equals(tableColumn.getColumnName())) {
				return true;
			}
		}
		return false;
	}

	public static Context get(Column tableColumn) {
		for (Context ctx : context) {
			if (ctx.getVar().equals(tableColumn.getColumnName())) {
				return ctx;
			}
		}
		return null;
	}

	public static String getEndName(String tableName, String columnName) {
		AssociationExtended assoc = getAssociationExtended(tableName);
		if (DmUtils.isClass(getDataModel(), assoc.getLeftEnd().getCurrentClass())) {
			if (columnName.equals(assoc.getLeftEnd().getCurrentClass() + "_id")
					|| DmUtils.isPropertyOfClass(getDataModel(), assoc.getLeftEnd().getCurrentClass(), columnName)) {
				return assoc.getLeftEnd().getCurrentClass();
			}
		}
		if (modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(getDataModel(), assoc.getLeftEnd().getCurrentClass()) != null) {
			Association association = modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(getDataModel(), assoc.getLeftEnd().getCurrentClass());
			if (association.getLeftEnd().equals(columnName) || association.getRightEnd().equals(columnName)) {
				return assoc.getLeftEnd().getCurrentClass();
			}
		}
		if (DmUtils.isClass(getDataModel(), assoc.getRightEnd().getCurrentClass())) {
			if (columnName.equals(assoc.getRightEnd().getCurrentClass() + "_id")
					|| DmUtils.isPropertyOfClass(getDataModel(), assoc.getRightEnd().getCurrentClass(), columnName)) {
				return assoc.getRightEnd().getCurrentClass();
			}
		}
		if (modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(getDataModel(), assoc.getRightEnd().getCurrentClass()) != null) {
			Association association = modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(getDataModel(), assoc.getRightEnd().getCurrentClass());
			if (association.getLeftEnd().equals(columnName) || association.getRightEnd().equals(columnName)) {
				return assoc.getRightEnd().getCurrentClass();
			}
		}
		return null;
	}

}
