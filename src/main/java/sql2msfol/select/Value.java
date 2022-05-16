package sql2msfol.select;

import org.vgu.dm2schema.dm.Entity;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import visitor.ExprVisitor;

public class Value {
	
	public static void declareFunction(String index, Expression expr, String type, Alias alias) {
		String dec = "(declare-fun val-%1$s-%2$s (Int) %3$s)";
		String valName;
		if (alias != null) {
			valName = alias.getName();
		} else {
			valName = NamingConvention.generateValName();
		}
		NamingConvention.saveVal(index, valName, expr);
		System.out.println(String.format(dec, index, valName, type));
	}
	
	public static void defineFunction(Entity tb, Expression expr, Alias alias) {
		ExprVisitor ev = new ExprVisitor();
		ev.setAlias(alias);
		ev.setIndex(tb.getName());
		ev.setSource(tb);
		expr.accept(ev);
	}
}
