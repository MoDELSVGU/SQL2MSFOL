package visitor;

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
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;
import sql2msfol.select.NamingConvention;
import sql2msfol.select.Type;
import sql2msfol.select.Value;

public class ExprVisitor implements ExpressionVisitor {

	private SelectBody sb;

	public void setSb(SelectBody sb) {
		this.sb = sb;
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
		Value.declareFunction(sb, nullValue, Type.get(nullValue));
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) NULL))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(nullValue)));
		return;
	}

	@Override
	public void visit(Function function) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SignedExpression signedExpression) {
		Expression expr = signedExpression.getExpression();
		if (expr instanceof LongValue) {
			Value.declareFunction(sb, signedExpression, Type.get(signedExpression));
			expr.accept(this);
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) (* (-1) (val-%1$s-%3$s x)))))))";
			System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(signedExpression),
					NamingConvention.getValName(expr)));
		}
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
		Value.declareFunction(sb, longValue, Type.get(longValue));
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) %3$s))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(longValue),
				String.valueOf(longValue.getValue())));
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
		Value.declareFunction(sb, stringValue, Type.get(stringValue));
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) %3$s))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(stringValue),
				stringValue));
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
		Expression left = andExpression.getLeftExpression();
		Expression right = andExpression.getRightExpression();
		Value.declareFunction(sb, andExpression, Type.get(andExpression));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(andExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (or (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(andExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) TRUE)))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(andExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
	}

	@Override
	public void visit(OrExpression orExpression) {
		Expression left = orExpression.getLeftExpression();
		Expression right = orExpression.getRightExpression();
		Value.declareFunction(sb, orExpression, Type.get(orExpression));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (or (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(orExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(orExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) FALSE)))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(orExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
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
		Expression left = equalsTo.getLeftExpression();
		Expression right = equalsTo.getRightExpression();
		Value.declareFunction(sb, equalsTo, Type.get(equalsTo));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(equalsTo),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(equalsTo),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(equalsTo),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		Expression left = greaterThan.getLeftExpression();
		Expression right = greaterThan.getRightExpression();
		Value.declareFunction(sb, greaterThan, Type.get(greaterThan));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (> (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(greaterThan),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (> (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(greaterThan),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(greaterThan),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		Expression left = greaterThanEquals.getLeftExpression();
		Expression right = greaterThanEquals.getRightExpression();
		Value.declareFunction(sb, greaterThanEquals, Type.get(greaterThanEquals));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (>= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(greaterThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (>= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(greaterThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(greaterThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));

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
		// TODO Auto-generated method stub

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
		Expression left = minorThan.getLeftExpression();
		Expression right = minorThan.getRightExpression();
		Value.declareFunction(sb, minorThan, Type.get(minorThan));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (< (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(minorThan),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (< (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(minorThan),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(minorThan),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		Expression left = minorThanEquals.getLeftExpression();
		Expression right = minorThanEquals.getRightExpression();
		Value.declareFunction(sb, minorThanEquals, Type.get(minorThanEquals));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (<= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(minorThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (<= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(minorThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(minorThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		Expression left = notEqualsTo.getLeftExpression();
		Expression right = notEqualsTo.getRightExpression();
		Value.declareFunction(sb, notEqualsTo, Type.get(notEqualsTo));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(notEqualsTo),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(notEqualsTo),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(notEqualsTo),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(Column tableColumn) {
		String columnName = tableColumn.getColumnName();
		if ("TRUE".equalsIgnoreCase(columnName)) {
			Value.declareFunction(sb, tableColumn, Type.get(tableColumn));
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) TRUE))))";
			System.out.println(
					String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(tableColumn)));
			return;
		}
		if ("FALSE".equalsIgnoreCase(columnName)) {
			Value.declareFunction(sb, tableColumn, Type.get(tableColumn));
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) FALSE))))";
			System.out.println(
					String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(tableColumn)));
			return;
		}
		{
			return;
		}
	}

	@Override
	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CaseExpression caseExpression) {
		// TODO Auto-generated method stub

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
		Expression left = bitwiseAnd.getLeftExpression();
		Expression right = bitwiseAnd.getRightExpression();
		Value.declareFunction(sb, bitwiseAnd, Type.get(bitwiseAnd));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(bitwiseAnd),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (or (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(bitwiseAnd),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) TRUE)))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(bitwiseAnd),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		Expression left = bitwiseOr.getLeftExpression();
		Expression right = bitwiseOr.getRightExpression();
		Value.declareFunction(sb, bitwiseOr, Type.get(bitwiseOr));
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (or (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(bitwiseOr),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(bitwiseOr),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) FALSE)))))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(bitwiseOr),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
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
		Expression expr = aThis.getExpression();
		Value.declareFunction(sb, aThis, Type.get(aThis));
		expr.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (= (val-%1$s-%3$s x) FALSE)))))";
		System.out.println(String.format(def, NamingConvention.getSelName(sb), NamingConvention.getValName(aThis),
				NamingConvention.getValName(expr)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (= (val-%1$s-%3$s x) TRUE)))))";
		System.out.println(String.format(def2, NamingConvention.getSelName(sb), NamingConvention.getValName(aThis),
				NamingConvention.getValName(expr)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (= (val-%1$s-%3$s x) NULL)))))";
		System.out.println(String.format(def3, NamingConvention.getSelName(sb), NamingConvention.getValName(aThis),
				NamingConvention.getValName(expr)));
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

}
