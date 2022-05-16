package sql2msfol.select;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.SelectBody;
import visitor.ExprVisitor;

public class Value {

	public static void declareFunction(SelectBody sb, Expression expr, String type) {
		String dec = "(declare-fun val-%1$s-%2$s (Int) %3$s)";
		String valName = NamingConvention.generateValName();
		NamingConvention.saveVal(valName, expr);
		System.out.println(String.format(dec, NamingConvention.getSelName(sb), valName, type));
	}

	public static void defineFunction(SelectBody sb, Expression expr) {
		ExprVisitor ev = new ExprVisitor();
		ev.setSb(sb);
		expr.accept(ev);
	}

}
