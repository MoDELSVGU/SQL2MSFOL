package visitor;

import org.vgu.dm2schema.dm.Association;
import org.vgu.dm2schema.dm.DmUtils;
import org.vgu.dm2schema.dm.Entity;

import configurations.Context;
import datamodel.DataModelHolder;
import net.sf.jsqlparser.expression.Alias;
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
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubSelect;
import sql2msfol.select.NamingConvention;
import sql2msfol.select.Type;
import sql2msfol.select.Value;

public class ExprVisitor implements ExpressionVisitor {

	private String index;
	private Alias alias;
	private Entity sourceEntity;
	private Association sourceAssociation;

	public void setIndex(String index) {
		this.index = index;
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
		Value.declareFunction(index, nullValue, Type.get(nullValue), alias);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) NULL))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(nullValue)));
		return;
	}

	@Override
	public void visit(Function function) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SignedExpression signedExpression) {
		Value.declareFunction(index, signedExpression, Type.get(signedExpression), alias);
		Expression expr = signedExpression.getExpression();
		if (expr instanceof LongValue) {
			expr.accept(this);
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) (* (-1) (val-%1$s-%3$s x)))))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(signedExpression),
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
		Value.declareFunction(index, longValue, Type.get(longValue), alias);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) %3$s))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(longValue),
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
		Value.declareFunction(index, stringValue, Type.get(stringValue), alias);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) %3$s))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(stringValue), stringValue));
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
		Value.declareFunction(index, andExpression, Type.get(andExpression), alias);
		Expression left = andExpression.getLeftExpression();
		Expression right = andExpression.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(andExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (or (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, index, NamingConvention.getValName(andExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) TRUE)))))))";
		System.out.println(String.format(def3, index, NamingConvention.getValName(andExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
	}

	@Override
	public void visit(OrExpression orExpression) {
		Value.declareFunction(index, orExpression, Type.get(orExpression), alias);
		Expression left = orExpression.getLeftExpression();
		Expression right = orExpression.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (or (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(orExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, index, NamingConvention.getValName(orExpression),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) FALSE)))))))";
		System.out.println(String.format(def3, index, NamingConvention.getValName(orExpression),
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
		Value.declareFunction(index, equalsTo, Type.get(equalsTo), alias);
		Expression left = equalsTo.getLeftExpression();
		Expression right = equalsTo.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(
				String.format(def, index, NamingConvention.getValName(equalsTo), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(
				String.format(def2, index, NamingConvention.getValName(equalsTo), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(
				String.format(def3, index, NamingConvention.getValName(equalsTo), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(GreaterThan greaterThan) {
		Value.declareFunction(index, greaterThan, Type.get(greaterThan), alias);
		Expression left = greaterThan.getLeftExpression();
		Expression right = greaterThan.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (> (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(
				String.format(def, index, NamingConvention.getValName(greaterThan), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (> (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(
				String.format(def2, index, NamingConvention.getValName(greaterThan), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(
				String.format(def3, index, NamingConvention.getValName(greaterThan), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(GreaterThanEquals greaterThanEquals) {
		Value.declareFunction(index, greaterThanEquals, Type.get(greaterThanEquals), alias);
		Expression left = greaterThanEquals.getLeftExpression();
		Expression right = greaterThanEquals.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (>= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(greaterThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right),
				Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (>= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def2, index, NamingConvention.getValName(greaterThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right),
				Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, index, NamingConvention.getValName(greaterThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right),
				Type.nullOf(right)));
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
		Value.declareFunction(index, isNullExpression, Type.get(isNullExpression), alias);
		Expression source = isNullExpression.getLeftExpression();
		source.accept(this);
		if (isNullExpression.isNot()) {
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (= (val-%1$s-%3$s x) %4$s)))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(isNullExpression),
					NamingConvention.getValName(source), Type.nullOf(source)));
			String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (not (= (val-%1$s-%3$s x) %4$s))))))";
			System.out.println(String.format(def2, index, NamingConvention.getValName(isNullExpression),
					NamingConvention.getValName(source), Type.nullOf(source)));
			return;
		}
		{
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (= (val-%1$s-%3$s x) %4$s)))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(isNullExpression),
					NamingConvention.getValName(source), Type.nullOf(source)));
			String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (not (= (val-%1$s-%3$s x) %4$s))))))";
			System.out.println(String.format(def2, index, NamingConvention.getValName(isNullExpression),
					NamingConvention.getValName(source), Type.nullOf(source)));
			return;
		}
	}

	@Override
	public void visit(IsBooleanExpression iindexooleanExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LikeExpression likeExpression) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MinorThan minorThan) {
		Value.declareFunction(index, minorThan, Type.get(minorThan), alias);
		Expression left = minorThan.getLeftExpression();
		Expression right = minorThan.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (< (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(
				String.format(def, index, NamingConvention.getValName(minorThan), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (< (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(
				String.format(def2, index, NamingConvention.getValName(minorThan), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(
				String.format(def3, index, NamingConvention.getValName(minorThan), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(MinorThanEquals minorThanEquals) {
		Value.declareFunction(index, minorThanEquals, Type.get(minorThanEquals), alias);
		Expression left = minorThanEquals.getLeftExpression();
		Expression right = minorThanEquals.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (<= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(minorThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right),
				Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (<= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(String.format(def2, index, NamingConvention.getValName(minorThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right),
				Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(String.format(def3, index, NamingConvention.getValName(minorThanEquals),
				NamingConvention.getValName(left), Type.nullOf(left), NamingConvention.getValName(right),
				Type.nullOf(right)));
	}

	@Override
	public void visit(NotEqualsTo notEqualsTo) {
		Value.declareFunction(index, notEqualsTo, Type.get(notEqualsTo), alias);
		Expression left = notEqualsTo.getLeftExpression();
		Expression right = notEqualsTo.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (not (= (val-%1$s-%3$s x) (val-%1$s-%5$s x))))))))";
		System.out.println(
				String.format(def, index, NamingConvention.getValName(notEqualsTo), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (not (= (val-%1$s-%3$s x) %4$s)) (not (= (val-%1$s-%5$s x) %6$s)) (= (val-%1$s-%3$s x) (val-%1$s-%5$s x)))))))";
		System.out.println(
				String.format(def2, index, NamingConvention.getValName(notEqualsTo), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (= (val-%1$s-%3$s x) %4$s) (= (val-%1$s-%5$s x) %6$s))))))";
		System.out.println(
				String.format(def3, index, NamingConvention.getValName(notEqualsTo), NamingConvention.getValName(left),
						Type.nullOf(left), NamingConvention.getValName(right), Type.nullOf(right)));
	}

	@Override
	public void visit(Column tableColumn) {
		String columnName = tableColumn.getColumnName();
		if ("TRUE".equalsIgnoreCase(columnName)) {
			Value.declareFunction(index, tableColumn, Type.get(tableColumn), new Alias(columnName));
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) TRUE))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(tableColumn)));
			return;
		}
		if ("FALSE".equalsIgnoreCase(columnName)) {
			Value.declareFunction(index, tableColumn, Type.get(tableColumn), new Alias(columnName));
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) FALSE))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(tableColumn)));
			return;
		}
		if (DataModelHolder.matchContext(tableColumn)) {
			Context ctx = DataModelHolder.get(tableColumn);
			String type;
			if (ctx.getType() == "String" || ctx.getType() == "Int") {
				type = ctx.getType();
			} else {
				type = "Classifier";
			}
			Value.declareFunction(index, tableColumn, type, new Alias(columnName));
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) %3$s))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(tableColumn), tableColumn));
			return;
		}
		{
			referTableToColumn(tableColumn);
			String tableName = tableColumn.getTable().getName();
			String sourceName = (sourceEntity != null) ? sourceEntity.getName() : sourceAssociation.getName();
			if (!index.equals(sourceName)) {
				Value.declareFunction(index, tableColumn, Type.get(tableColumn), new Alias(columnName));
				if (columnName.equals(tableName + "_id")) {
					String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) (val-%3$s-id x)))))";
					System.out.println(String.format(def, index, NamingConvention.getValName(tableColumn), tableName));
					return;
				}
				{
					String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) (val-%3$s-%4$s x)))))";
					System.out.println(
							String.format(def, index, NamingConvention.getValName(tableColumn), tableName, columnName));
					return;
				}
			} else {
				if (DataModelHolder.getAssociationExtended(tableName) != null) {
					if (DmUtils.getAssociation(DataModelHolder.getDataModel(), tableName) == null) {
						NamingConvention.saveVal(columnName, tableColumn);
					}
				} else {
					NamingConvention.saveVal(tableName+"-"+columnName, tableColumn);
				}
			}
		}
	}

	private void referTableToColumn(Column tableColumn) {
		if (sourceEntity != null) {
			tableColumn.setTable(new Table(sourceEntity.getName()));
		} else {
			tableColumn.setTable(new Table(sourceAssociation.getName()));
		}
	}

	@Override
	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CaseExpression caseExpression) {
		Value.declareFunction(index, caseExpression, Type.get(caseExpression), alias);
		Expression when = caseExpression.getWhenClauses().get(0).getWhenExpression();
		Expression then = caseExpression.getWhenClauses().get(0).getThenExpression();
		Expression elze = caseExpression.getElseExpression();
		when.accept(this);
		then.accept(this);
		elze.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (= (val-%1$s-%3$s x) (val-%1$s-%4$s x))))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(when),
				NamingConvention.getValName(caseExpression), NamingConvention.getValName(then)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (or (= (= (val-%1$s-%2$s x) FALSE) (= (= (val-%1$s-%2$s x) NULL)) (= (val-%1$s-%3$s x) (val-%1$s-%4$s x))))))";
		System.out.println(String.format(def2, index, NamingConvention.getValName(when),
				NamingConvention.getValName(caseExpression), NamingConvention.getValName(elze)));
	}

	@Override
	public void visit(WhenClause whenClause) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExistsExpression existsExpression) {
		Value.declareFunction(index, existsExpression, Type.get(existsExpression), alias);
		Expression source = existsExpression.getRightExpression();
		SubSelect ss = (SubSelect) source;
		SelectVisitor sv = new SelectVisitor();
		Select sel = new Select();
		sel.setSelectBody(ss.getSelectBody());
		sel.accept(sv);
		if (existsExpression.isNot()) {
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (exists ((y Int)) (index-%3$s y))))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(existsExpression),
					NamingConvention.getSelName(ss.getSelectBody())));
			String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (not (exists ((y Int)) (index-%3$s y)))))))";
			System.out.println(String.format(def2, index, NamingConvention.getValName(existsExpression),
					NamingConvention.getSelName(ss.getSelectBody())));
			return;
		}
		{
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (exists ((y Int)) (index-%3$s y))))))";
			System.out.println(String.format(def, index, NamingConvention.getValName(existsExpression),
					NamingConvention.getSelName(ss.getSelectBody())));
			String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (not (exists ((y Int)) (index-%3$s y)))))))";
			System.out.println(String.format(def2, index, NamingConvention.getValName(existsExpression),
					NamingConvention.getSelName(ss.getSelectBody())));
			return;
		}
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
		Value.declareFunction(index, bitwiseAnd, Type.get(bitwiseAnd), alias);
		Expression left = bitwiseAnd.getLeftExpression();
		Expression right = bitwiseAnd.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(bitwiseAnd),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (or (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, index, NamingConvention.getValName(bitwiseAnd),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) TRUE)))))))";
		System.out.println(String.format(def3, index, NamingConvention.getValName(bitwiseAnd),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
	}

	@Override
	public void visit(BitwiseOr bitwiseOr) {
		Value.declareFunction(index, bitwiseOr, Type.get(bitwiseOr), alias);
		Expression left = bitwiseOr.getLeftExpression();
		Expression right = bitwiseOr.getRightExpression();
		left.accept(this);
		right.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (or (= (val-%1$s-%3$s x) TRUE) (= (val-%1$s-%4$s x) TRUE))))))";
		System.out.println(String.format(def, index, NamingConvention.getValName(bitwiseOr),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) FALSE))))))";
		System.out.println(String.format(def2, index, NamingConvention.getValName(bitwiseOr),
				NamingConvention.getValName(left), NamingConvention.getValName(right)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (or (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) FALSE) (= (val-%1$s-%4$s x) NULL)) (and (= (val-%1$s-%3$s x) NULL) (= (val-%1$s-%4$s x) FALSE)))))))";
		System.out.println(String.format(def3, index, NamingConvention.getValName(bitwiseOr),
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
		Value.declareFunction(index, aThis, Type.get(aThis), alias);
		Expression expr = aThis.getExpression();
		expr.accept(this);
		String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) TRUE) (= (val-%1$s-%3$s x) FALSE)))))";
		System.out.println(
				String.format(def, index, NamingConvention.getValName(aThis), NamingConvention.getValName(expr)));
		String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) FALSE) (= (val-%1$s-%3$s x) TRUE)))))";
		System.out.println(
				String.format(def2, index, NamingConvention.getValName(aThis), NamingConvention.getValName(expr)));
		String def3 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (= (val-%1$s-%2$s x) NULL) (= (val-%1$s-%3$s x) NULL)))))";
		System.out.println(
				String.format(def3, index, NamingConvention.getValName(aThis), NamingConvention.getValName(expr)));
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

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	public void setSourceEntity(Entity entity) {
		this.sourceEntity = entity;
	}

	public void setSourceAssociation(Association association) {
		this.sourceAssociation = association;
	}

}
