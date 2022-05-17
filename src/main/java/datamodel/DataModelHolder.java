package datamodel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.vgu.dm2schema.dm.Association;
import org.vgu.dm2schema.dm.DataModel;

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

}
