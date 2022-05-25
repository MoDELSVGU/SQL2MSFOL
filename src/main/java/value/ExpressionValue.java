package value;

import java.util.ArrayList;
import java.util.List;

import index.JoinIndex;
import net.sf.jsqlparser.expression.Expression;

public class ExpressionValue extends Value {
	private Expression expr;
	private List<String> meanings;

	public ExpressionValue() {
		meanings = new ArrayList<String>();
	}

	public Expression getExpr() {
		return expr;
	}

	public List<String> getMeanings() {
		return meanings;
	}

	public void setMeanings(List<String> meanings) {
		this.meanings = meanings;
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
			for (String s : meanings) {
				String def = "(assert (forall ((x Int)) (=> (%1$s x) (= (%2$s x) %3$s))))";
				System.out.println(String.format(def, getParentIndex().getFuncName(), getFuncName(), s));
			}
		}
	}
}
