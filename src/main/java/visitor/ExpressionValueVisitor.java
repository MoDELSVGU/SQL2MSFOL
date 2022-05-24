package visitor;

import index.Index;
import mappings.ValueMapping;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.ArrayConstructor;
import net.sf.jsqlparser.expression.ArrayExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.CollateExpression;
import net.sf.jsqlparser.expression.ConnectByRootOperator;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonAggregateFunction;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.JsonFunction;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NextValExpression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.OracleNamedFunctionParameter;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.RowGetExpression;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.TimezoneExpression;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.VariableAssignment;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.XMLSerializeExpr;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseLeftShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.IntegerDivision;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.FullTextSearch;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.expression.operators.relational.SimilarToExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import type.TypeUtils;
import value.ExpressionValue;

public class ExpressionValueVisitor implements ExpressionVisitor {

	private Index source;
	private Index parent;

	@Override
	public void visit(BitwiseRightShift aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BitwiseLeftShift aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NullValue nullValue) {
		valueExpression(nullValue);
	}

	@Override
	public void visit(Function function) {
		// Currently, we accept one parameter in the function (i.e., function as
		// aggregation)
		valueExpression(function);
		Expression expr = function.getParameters().getExpressions().get(0);
		expr.accept(this);
	}

	private void valueExpression(Expression expr) {
		ExpressionValue ev = new ExpressionValue();
		ev.setExpr(expr);
		ev.setParentIndex(parent);
		ev.setSourceIndex(source);
		ev.setType(TypeUtils.get(expr, source));
		ValueMapping.add(ev);
	}

	@Override
	public void visit(SignedExpression signedExpression) {
		valueExpression(signedExpression);
		Expression expr = signedExpression.getExpression();
		expr.accept(this);
	}

	@Override
	public void visit(JdbcParameter jdbcParameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JdbcNamedParameter jdbcNamedParameter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DoubleValue doubleValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LongValue longValue) {
		valueExpression(longValue);
	}

	@Override
	public void visit(HexValue hexValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DateValue dateValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TimeValue timeValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TimestampValue timestampValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Parenthesis parenthesis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringValue stringValue) {
		valueExpression(stringValue);
	}

	@Override
	public void visit(Addition addition) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Division division) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntegerDivision division) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Multiplication multiplication) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Subtraction subtraction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AndExpression andExpression) {
		valueExpression(andExpression);
		Expression left = andExpression.getLeftExpression();
		left.accept(this);
		Expression right = andExpression.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(OrExpression orExpression) {
		valueExpression(orExpression);
		Expression left = orExpression.getLeftExpression();
		left.accept(this);
		Expression right = orExpression.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(XorExpression orExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Between between) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EqualsTo equalsTo) {
		valueExpression(equalsTo);
		Expression left = equalsTo.getLeftExpression();
		left.accept(this);
		Expression right = equalsTo.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		valueExpression(greaterThan);
		Expression left = greaterThan.getLeftExpression();
		left.accept(this);
		Expression right = greaterThan.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		valueExpression(greaterThanEquals);
		Expression left = greaterThanEquals.getLeftExpression();
		left.accept(this);
		Expression right = greaterThanEquals.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(InExpression inExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FullTextSearch fullTextSearch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IsNullExpression isNullExpression) {
		valueExpression(isNullExpression);
		Expression expr = isNullExpression.getLeftExpression();
		expr.accept(this);
	}

	@Override
	public void visit(IsBooleanExpression isBooleanExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LikeExpression likeExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MinorThan minorThan) {
		valueExpression(minorThan);
		Expression left = minorThan.getLeftExpression();
		left.accept(this);
		Expression right = minorThan.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		valueExpression(minorThanEquals);
		Expression left = minorThanEquals.getLeftExpression();
		left.accept(this);
		Expression right = minorThanEquals.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		valueExpression(notEqualsTo);
		Expression left = notEqualsTo.getLeftExpression();
		left.accept(this);
		Expression right = notEqualsTo.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(Column tableColumn) {
		valueExpression(tableColumn);
	}

	@Override
	public void visit(SubSelect subSelect) {
		PlainSelect ps = (PlainSelect) subSelect.getSelectBody();
		Select s = new Select();
		s.setSelectBody(ps);
		StatementValueVisitor vsv = new StatementValueVisitor();
		s.accept(vsv);
		valueExpression(subSelect);
	}

	@Override
	public void visit(CaseExpression caseExpression) {
		valueExpression(caseExpression);
		Expression when = caseExpression.getWhenClauses().get(0).getWhenExpression();
		when.accept(this);
		Expression then = caseExpression.getWhenClauses().get(0).getThenExpression();
		then.accept(this);
		Expression elze = caseExpression.getElseExpression();
		elze.accept(this);
	}

	@Override
	public void visit(WhenClause whenClause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		valueExpression(existsExpression);
		Expression expr = existsExpression.getRightExpression();
		expr.accept(this);
	}

	@Override
	public void visit(AnyComparisonExpression anyComparisonExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Concat concat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Matches matches) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BitwiseAnd bitwiseAnd) {
		valueExpression(bitwiseAnd);
		Expression left = bitwiseAnd.getLeftExpression();
		left.accept(this);
		Expression right = bitwiseAnd.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		valueExpression(bitwiseOr);
		Expression left = bitwiseOr.getLeftExpression();
		left.accept(this);
		Expression right = bitwiseOr.getRightExpression();
		right.accept(this);
	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CastExpression cast) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Modulo modulo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AnalyticExpression aexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExtractExpression eexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntervalExpression iexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OracleHierarchicalExpression oexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RegExpMatchOperator rexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JsonExpression jsonExpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JsonOperator jsonExpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RegExpMySQLOperator regExpMySQLOperator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UserVariable var) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NumericBind bind) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(KeepExpression aexpr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MySQLGroupConcat groupConcat) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ValueListExpression valueList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RowConstructor rowConstructor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RowGetExpression rowGetExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OracleHint hint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TimeKeyExpression timeKeyExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DateTimeLiteralExpression literal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NotExpression aThis) {
		valueExpression(aThis);
		Expression expr = aThis.getExpression();
		expr.accept(this);
	}

	@Override
	public void visit(NextValExpression aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CollateExpression aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SimilarToExpression aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayExpression aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ArrayConstructor aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(VariableAssignment aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(XMLSerializeExpr aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TimezoneExpression aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JsonAggregateFunction aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(JsonFunction aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ConnectByRootOperator aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OracleNamedFunctionParameter aThis) {
		// TODO Auto-generated method stub

	}

	public Index getSource() {
		return source;
	}

	public void setSource(Index source) {
		this.source = source;
	}

	public Index getParent() {
		return parent;
	}

	public void setParent(Index parent) {
		this.parent = parent;
	}

}
