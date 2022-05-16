package sql2msfol.select;

import java.util.HashMap;

import org.vgu.dm2schema.dm.DataModel;
import org.vgu.dm2schema.dm.DmUtils;
import org.vgu.dm2schema.dm.Entity;

import datamodel.AttributeExtended;
import datamodel.DataModelHolder;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;

public class NamingConvention {
	private static int selCounter = 0;
	private static int valCounter = 0;
	private static HashMap<SelectBody, String> selIndices = new HashMap<SelectBody, String>();
	private static HashMap<Expression, String> valIndices = new HashMap<Expression, String>();

	public static void increaseSelCounter() {
		selCounter++;
	}

	public static void increaseValCounter() {
		valCounter++;
	}

	public static String generateSelName() {
		return String.format("sel%s", String.valueOf(selCounter++));
	}
	
	public static String generateValName() {
		return String.format("val%s", String.valueOf(valCounter++));
	}
	
	public static void saveSelIndex(String name, SubSelect subSelect) {
		selIndices.put(subSelect.getSelectBody(), name);
	}
	
	public static void saveVal(String index, String name, Expression expr) {
		valIndices.put(expr, name);
		addNewAttribute(index, name, expr);
	}

	private static void addNewAttribute(String index, String name, Expression expr) {
		AttributeExtended att = new AttributeExtended();
		att.setName(name);
		att.setType(Type.get(expr));
		Entity e = DmUtils.getEntity(DataModelHolder.getDataModel(), index);
		e.getAttributes().add(att);
	}

	public static void saveSelIndex(String name, SelectBody selectBody) {
		selIndices.put(selectBody, name);
		addNewEntity(name);
	}

	private static void addNewEntity(String name) {
		DataModel dm = DataModelHolder.getDataModel();
		Entity e = new Entity();
		e.setClazz(name);
		dm.getEntities().put(name, e);
	}

	public static String getSelName(FromItem fromItem) throws Exception {
		if (fromItem instanceof Table) {
			Table t = (Table) fromItem;
			return t.getName();
		}
		if (fromItem instanceof SubSelect) {
			SubSelect ss = (SubSelect) fromItem;
			return selIndices.get(ss.getSelectBody());
		}
		throw new Exception("There is no suitable fromItem name in the storage.");
	}
	
	public static String getSelName(SelectBody selectBody) {
		return selIndices.get(selectBody);
	}

	public static String getValName(Expression expr) {
		return valIndices.get(expr);
	}

	public static void reset() {
		valCounter = 0;
		selCounter = 0;
		valIndices.clear();
		selIndices.clear();
	}
}
