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
