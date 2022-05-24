package value;

import index.JoinIndex;
import mappings.ValueMapping;
import net.sf.jsqlparser.expression.Expression;

public class ExpressionValue extends Value {
	private Expression expr;

	public Expression getExpr() {
		return expr;
	}

	public void setExpr(Expression expr) {
		this.expr = expr;
	}
	
	@Override
	public String toString() {
		return expr.toString();
	}

	@Override
	public void define() {
		if (getSourceIndex() instanceof JoinIndex) {
			
		} else {
			String def = "(assert (forall ((x Int)) (=> (%1$s x) (= (%2$s x) (%3$s x)))))";
			System.out.println(String.format(def, getSourceIndex().getFuncName(), getFuncName(), ValueMapping.getValue(expr).getFuncName()));
		}
	}
}
