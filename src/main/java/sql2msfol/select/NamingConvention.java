package sql2msfol.select;

import java.util.HashMap;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;

public class NamingConvention {
	private static int selCounter = 0;
	private static int valCounter = 0;
	private static HashMap<FromItem, String> selIndices = new HashMap<FromItem, String>();
	private static HashMap<Expression, String> valIndices = new HashMap<Expression, String>();

	public static void resetValCounter() {
		valCounter = 0;
	}

	public static void increaseSelCounter() {
		selCounter++;
	}

	public static void increaseValCounter() {
		valCounter++;
	}

	public static String generateSelName() {
		return String.format("index-sel%s", String.valueOf(selCounter));
	}
	
	public static String generateSelIntermediateName() {
		return String.format("index-sel%s-join", String.valueOf(selCounter));
	}

	public static String generateValName() {
		return String.format("index-sel%s-val%s", String.valueOf(selCounter), String.valueOf(valCounter));
	}

	public static void saveSelIndex(String name, FromItem fromItem) {
		selIndices.put(fromItem, name);
	}

	public static String getSelName(FromItem fromItem) {
		if (fromItem instanceof Table) {
			return selIndices.get(fromItem);
		}
		return String.format("sel%s", selIndices.get(fromItem));
	}

	public static String getValName(Expression expr) {
		return String.format("expr%s", valIndices.get(expr));
	}
}
