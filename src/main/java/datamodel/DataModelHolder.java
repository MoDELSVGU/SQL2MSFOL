package datamodel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vgu.dm2schema.dm.Association;
import org.vgu.dm2schema.dm.DataModel;
import org.vgu.dm2schema.dm.DmUtils;

import configurations.Context;
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
		for(Association assoc : associations) {
			AssociationExtended assocExtended = new AssociationExtended(assoc.getLeft(), assoc.getRight());
			associations.add(assocExtended);
		}
	}

	public static Set<AssociationExtended> getAssociations() {
		return associations;
	}
	
	public static AssociationExtended getAssociationExtended(String name) {
		for (Association assoc : DataModelHolder.getDataModel().getAssociations()) {
			if(assoc.getName().equals(name)) {
				if (assoc instanceof AssociationExtended) {
					return (AssociationExtended) assoc;
				}
			}
		}
		return null;
	}
	
	public static Association getAssociation(String name) {
		for (Association assoc : DataModelHolder.getDataModel().getAssociations()) {
			if(assoc.getName().equals(name)) {
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
		for(Context ctx : context) {
			if (ctx.getVar().equals(tableColumn.getColumnName())) {
				return true;
			}
		}
		return false;
	}

	public static Context get(Column tableColumn) {
		for(Context ctx : context) {
			if (ctx.getVar().equals(tableColumn.getColumnName())) {
				return ctx;
			}
		}
		return null;
	}

	public static String getEndName(String tableName, String columnName) {
		AssociationExtended assoc = getAssociationExtended(tableName);
		if (DmUtils.isClass(getDataModel(), assoc.getLeftEntityName())) {
			if (columnName.equals(assoc.getLeftEntityName()+"_id") ||
					DmUtils.isPropertyOfClass(getDataModel(), assoc.getLeftEntityName(), columnName)) {
				return assoc.getLeftEntityName();
			}
		}  
		if (DmUtils.getAssociation(getDataModel(), assoc.getLeftEntityName()) != null) {
			Association association = DmUtils.getAssociation(getDataModel(), assoc.getLeftEntityName());
			if (association.getLeftEnd().equals(columnName)
					|| association.getRightEnd().equals(columnName)) {
				return assoc.getLeftEntityName();
			}
		}
		if (DmUtils.isClass(getDataModel(), assoc.getRightEntityName())) {
			if (columnName.equals(assoc.getRightEntityName()+"_id") ||
					DmUtils.isPropertyOfClass(getDataModel(), assoc.getRightEntityName(), columnName)) {
				return assoc.getRightEntityName();
			}
		}  
		if (DmUtils.getAssociation(getDataModel(), assoc.getRightEntityName()) != null) {
			Association association = DmUtils.getAssociation(getDataModel(), assoc.getRightEntityName());
			if (association.getLeftEnd().equals(columnName)
					|| association.getRightEnd().equals(columnName)) {
				return assoc.getRightEntityName();
			}
		}
		return null;
	}

}
