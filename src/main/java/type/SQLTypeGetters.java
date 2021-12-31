package type;

import javax.lang.model.type.NullType;

import net.sf.jsqlparser.expression.Expression;
import visitor.MyTypeExpressionVisitor;

public class SQLTypeGetters {

	public static SortType getType(Expression selectExpression) {
		MyTypeExpressionVisitor visitor = new MyTypeExpressionVisitor();
		selectExpression.accept(visitor);
		return visitor.getSort();
	}
}
