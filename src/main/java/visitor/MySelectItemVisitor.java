package visitor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import type.SQLTypeGetters;

public class MySelectItemVisitor implements SelectItemVisitor {

	@Override
	public void visit(AllColumns allColumns) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AllTableColumns allTableColumns) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SelectExpressionItem selectExpressionItem) {
		Expression selectExpression = selectExpressionItem.getExpression();
		MapTrueExpressionVisitor visitor = new MapTrueExpressionVisitor(selectExpressionItem.getAlias().getName(),
				SQLTypeGetters.getType(selectExpression));
		selectExpression.accept(visitor);
	}

}
