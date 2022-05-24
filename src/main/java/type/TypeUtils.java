package type;

import index.Index;
import net.sf.jsqlparser.expression.Expression;
import visitor.ExpressionTypeVisitor;

public class TypeUtils {
	
	public static Type get(Expression expr, Index source) {
		ExpressionTypeVisitor etv = new ExpressionTypeVisitor();
		etv.setSource(source);
		expr.accept(etv);
		return new Type(etv.getType());
	}

	public static String nullOf(Expression expr) {
		String type = get(expr).getName();
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
