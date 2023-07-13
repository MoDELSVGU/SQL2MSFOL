package modeling.sql.fol.sql2msfol.select;

import modeling.sql.fol.visitor.ExprType;
import net.sf.jsqlparser.expression.Expression;

public class Type {
	
	public static String get(Expression expr) {
		ExprType et = new ExprType();
		expr.accept(et);
		return et.getType();
	}

	public static String nullOf(Expression expr) {
		String type = get(expr);
		if ("String".equals(type)) {
			return "nullString";
		}
		if ("Int".equals(type)) {
			return "nullInt";
		}
		if ("Bool".equals(type)) {
			return "NULL";
		}
		return "nullClassifier";
	}
	
	public static String convert(String type) {
		if ("Integer".equals(type)) {
			return "Int";
		}
		if ("Boolean".equals(type)) {
			return "Bool";
		}
		else return type;
	}

}
