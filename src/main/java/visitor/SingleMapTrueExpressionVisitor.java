package visitor;

import java.util.List;

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
import net.sf.jsqlparser.statement.select.SubSelect;
import sql2msfol.validator.ToBeContinuedException;
import type.MisinterpretPreventionUtils;
import type.NullTypeGetters;
import type.SQLTypeGetters;
import type.SortType;

public class SingleMapTrueExpressionVisitor implements ExpressionVisitor {

	private String alias;
	private String definition;
	private SortType sort;

	public SingleMapTrueExpressionVisitor(String alias, SortType sort) {
		this.alias = alias;
		this.sort = sort;
		System.out.println(String.format("(declare-const %s %s)", alias, sort.getName()));
	}

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
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Function function) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SignedExpression signedExpression) {
		// TODO Auto-generated method stub

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
		this.definition = String.valueOf(longValue.getValue());
		MisinterpretPreventionUtils.fixInterpret(longValue.getValue(), SQLTypeGetters.getType(longValue).getName());
		System.out.println(String.format("(assert (= %s %s))", alias, definition));
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
		this.definition = stringValue.toString();
		MisinterpretPreventionUtils.fixInterpret(stringValue.toString(), SQLTypeGetters.getType(stringValue).getName());
		System.out.println(String.format("(assert (= %s %s))", alias, definition));
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
		Expression leftExpression = andExpression.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = andExpression.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (and %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(OrExpression orExpression) {
		Expression leftExpression = orExpression.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = orExpression.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (or %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(XorExpression orExpression) {
		Expression leftExpression = orExpression.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = orExpression.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (xor %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(Between between) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EqualsTo equalsTo) {
		Expression leftExpression = equalsTo.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = equalsTo.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (= %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		Expression leftExpression = greaterThan.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = greaterThan.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (> %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		Expression leftExpression = greaterThanEquals.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = greaterThanEquals.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (>= %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
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
		Expression expression = isNullExpression.getLeftExpression();
		SingleMapTrueExpressionVisitor visitor = new SingleMapTrueExpressionVisitor(String.format("%s_isnull", this.alias),
				SQLTypeGetters.getType(expression));
		expression.accept(visitor);

		if (isNullExpression.isNot()) {
			System.out.println(String.format("(assert (distinct %s %s))", alias, visitor.getAlias(),
					NullTypeGetters.getNullType(SQLTypeGetters.getType(expression))));
		} else {
			System.out.println(String.format("(assert (= %s %s))", alias, visitor.getAlias(),
					NullTypeGetters.getNullType(SQLTypeGetters.getType(expression))));
		}

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
		Expression leftExpression = minorThan.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = minorThan.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (< %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		Expression leftExpression = minorThanEquals.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = minorThanEquals.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (<= %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		Expression leftExpression = notEqualsTo.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = notEqualsTo.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(String.format("(assert (= %s (distinct %s %s)))", alias, leftVisitor.getAlias(),
				rightVisitor.getAlias()));
	}

	@Override
	public void visit(Column tableColumn) {
		String columnName = tableColumn.getColumnName();
		if ("TRUE".equals(columnName)) {
			this.definition = "true";
		} else if ("FALSE".equals(columnName)) {
			this.definition = "false";
		} else {
			throw new ToBeContinuedException();
		}
		System.out.println(String.format("(assert (= %s %s))", this.alias, definition));
		
	}

	@Override
	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CaseExpression caseExpression) {
		List<WhenClause> whenClauses = caseExpression.getWhenClauses();
		String definition = "%s";
		for (int i = 0; i < whenClauses.size(); i++) {
			WhenClause whenClause = whenClauses.get(i);
			Expression whenExpression = whenClause.getWhenExpression();
			SingleMapTrueExpressionVisitor whenExpressionVisitor = new SingleMapTrueExpressionVisitor(
					String.format("%s_when_%s", this.alias, String.valueOf(i)), SQLTypeGetters.getType(whenExpression));
			whenExpression.accept(whenExpressionVisitor);
			Expression thenExpression = whenClause.getThenExpression();
			SingleMapTrueExpressionVisitor thenExpressionVisitor = new SingleMapTrueExpressionVisitor(
					String.format("%s_then_%s", this.alias, String.valueOf(i)), SQLTypeGetters.getType(thenExpression));
			thenExpression.accept(thenExpressionVisitor);
			String temp = String.format("(or (=> %s %s) (=> (not %s) %s))", whenExpressionVisitor.getAlias(),
					thenExpressionVisitor.getAlias(), whenExpressionVisitor.getAlias(), "%s");
			definition = String.format(definition, temp);
		}
		Expression elseExpression = caseExpression.getElseExpression();
		SingleMapTrueExpressionVisitor elseExpressionVisitor = new SingleMapTrueExpressionVisitor(
				String.format("%s_else", this.alias), SQLTypeGetters.getType(elseExpression));
		elseExpression.accept(elseExpressionVisitor);
		definition = String.format(definition, elseExpressionVisitor.getAlias());

		System.out.println(String.format("(assert (= %s %s))", alias, definition));

	}

	@Override
	public void visit(WhenClause whenClause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		// TODO Auto-generated method stub

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
		Expression leftExpression = bitwiseAnd.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = bitwiseAnd.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (and %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		Expression leftExpression = bitwiseOr.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = bitwiseOr.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (or %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
	}

	@Override
	public void visit(BitwiseXor bitwiseXor) {
		Expression leftExpression = bitwiseXor.getLeftExpression();
		SingleMapTrueExpressionVisitor leftVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_left", this.alias),
				SQLTypeGetters.getType(leftExpression));
		leftExpression.accept(leftVisitor);
		Expression rightExpression = bitwiseXor.getRightExpression();
		SingleMapTrueExpressionVisitor rightVisitor = new SingleMapTrueExpressionVisitor(String.format("%s_right", this.alias),
				SQLTypeGetters.getType(rightExpression));
		rightExpression.accept(rightVisitor);
		System.out.println(
				String.format("(assert (= %s (xor %s %s)))", alias, leftVisitor.getAlias(), rightVisitor.getAlias()));
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
		Expression expression = aThis.getExpression();
		SingleMapTrueExpressionVisitor visitor = new SingleMapTrueExpressionVisitor(String.format("%s_neg", this.alias),
				SQLTypeGetters.getType(expression));
		expression.accept(visitor);
		System.out.println(String.format("(assert (= %s (not %s))", alias, visitor.getAlias()));
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

	public String getDefinition() {
		return this.definition;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public SortType getSort() {
		return sort;
	}

	public void setSort(SortType sort) {
		this.sort = sort;
	}

}
