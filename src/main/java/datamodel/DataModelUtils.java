package datamodel;

import java.util.List;

import org.vgu.dm2schema.dm.Association;
import org.vgu.dm2schema.dm.Attribute;
import org.vgu.dm2schema.dm.DataModel;
import org.vgu.dm2schema.dm.DmUtils;
import org.vgu.dm2schema.dm.Entity;

import configurations.Context;
import net.sf.jsqlparser.schema.Column;

public class DataModelUtils {
	private static DataModel dataModel;
	private static List<Context> context;

	public static DataModel getDataModel() {
		return dataModel;
	}

	public static void setDataModel(DataModel dm) {
		dataModel = dm;
	}
	
	public static List<Context> getContext() {
		return context;
	}

	public static void setContext(List<Context> context) {
		DataModelUtils.context = context;
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

	public static boolean isEntity(String tableName) {
		return DmUtils.isClass(getDataModel(), tableName);
	}
	
	public static Entity getEntity(String tableName) {
		return DmUtils.getEntity(getDataModel(), tableName);
	}
	
	public static Association getAssociation(String tableName) {
		return DmUtils.getAssociation(getDataModel(), tableName);
	}

	public static String getType(String columnName, Entity source) {
		for (Attribute att : source.getAttributes()) {
			if (att.getName().equals(columnName)) {
				return att.getType();
			}
		}
		return null;
	}

	public static Entity getEntity(Attribute source) {
		for (Entity e : getDataModel().getEntities().values()) {
			if (e.getAttributes().contains(source)) {
				return e;
			}
		}
		return null;
	}

}
