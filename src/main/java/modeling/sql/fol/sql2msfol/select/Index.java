package modeling.sql.fol.sql2msfol.select;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class Index {

	public static void declareFunction(Select select, Alias alias, SelectPattern pattern) throws Exception {
		PlainSelect ps = (PlainSelect) select.getSelectBody();
		String selName = (alias == null) ? NamingConvention.generateSelName() : alias.getName();
		switch (pattern) {
		case ONLY_SELECT:
		case SELECT_FROM:
		case SELECT_FROM_WHERE: {
			String declareFunction = "(declare-fun index-%1$s (Int) Bool)";
			NamingConvention.saveSelIndexEntity(selName, ps);
			System.out.println(String.format(declareFunction, selName));
			break;
		}
		case SELECT_FROM_JOIN:
		case SELECT_FROM_JOIN_WHERE:
		case SELECT_FROM_JOIN_ON:
		case SELECT_FROM_JOIN_ON_WHERE: {
			String declareFunction = "(declare-fun index-%1$s (Int) Bool)";
			NamingConvention.saveSelIndexEntity(selName, ps);
			System.out.println(String.format(declareFunction, selName));
			String declareFunction_intermediate = "(declare-fun index-%1$s-join (Int) Bool)";
			NamingConvention.saveSelIndexAssociation(selName+"-join", ps);
			System.out.println(String.format(declareFunction_intermediate, selName));
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + pattern);
		}
	}

	public static void defineFunction(Select select, SelectPattern pattern) throws Exception {
		PlainSelect ps = (PlainSelect) select.getSelectBody();
		switch (pattern) {
		case ONLY_SELECT: {
			String definition = "(assert (exists ((x Int)) (and (index-%1$s x) (forall ((y Int)) (=> (not (= x y)) (not (index-%1$s y)))))))";
			System.out.println(String.format(definition, NamingConvention.getSelName(ps)));
			break;
		}
		case SELECT_FROM: {
			String definition = "(assert (forall ((x Int)) (= (index-%1$s x) (index-%2$s x))))";
			System.out.println(String.format(definition, NamingConvention.getSelName(ps),
					NamingConvention.getSelName(ps.getFromItem())));
			break;
		}
		case SELECT_FROM_WHERE: {
			String definition = "(assert (forall ((x Int)) (= (index-%1$s x) (and (index-%2$s x) (= (%3$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.getSelName(ps),
					NamingConvention.getSelName(ps.getFromItem()),
					String.format("val-%s-%s", NamingConvention.getSelName(ps.getFromItem()),
							NamingConvention.getValName(ps.getWhere()))));
			break;
		}
		case SELECT_FROM_JOIN: {
			String definition = "(assert (forall ((x Int)) (= (index-%1$s x) (index-%1$s-join x))))";
			System.out.println(String.format(definition, NamingConvention.getSelName(ps)));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (index-%1$s-join x) (index-%1$s-join y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.getSelName(ps)));
			String definition3 = "(assert (forall ((x Int)) (=> (index-%1$s-join x) (exists ((y Int) (z Int)) (and (index-%2$s y) (index-%3$s z) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition3, NamingConvention.getSelName(ps), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (index-%1$s y) (index-%2$s z)) (exists ((x Int)) (and (index-%3$s-join x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.getSelName(ps)));
			break;
		}
		case SELECT_FROM_JOIN_ON: {
			String definition = "(assert (forall ((x Int)) (= (index-%1$s x) (and (index-%1$s-join x) (= (val-%1$s-join-%2$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.getSelName(ps), NamingConvention.getValName(ps.getJoins().get(0).getOnExpression())));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (index-%1$s-join x) (index-%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.getSelName(ps)));
			String definition3 = "(assert (forall ((x Int)) (=> (index-%1$s-join x) (exists ((y Int) (z Int)) (and (index-%2$s y) (index-%3$s z) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition3, NamingConvention.getSelName(ps), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (index-%1$s y) (index-%2$s z)) (exists ((x Int)) (and (index-%3$s-join x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.getSelName(ps)));
			break;
		}
		case SELECT_FROM_JOIN_WHERE: {
			String definition = "(assert (forall ((x Int)) (= (index-%1$s x) (and (index-%1$s-join x) (= (val-%1$s-join-%2$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.getSelName(ps), NamingConvention.getValName(ps.getWhere())));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (index-%1$s-join x) (index-%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.getSelName(ps)));
			String definition3 = "(assert (forall ((x Int)) (=> (index-%1$s-join x) (exists ((y Int) (z Int)) (and (index-%2$s y) (index-%3$s z) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition3, NamingConvention.getSelName(ps), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (index-%1$s y) (index-%2$s z)) (exists ((x Int)) (and (index-%3$s-join x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.getSelName(ps)));
			break;
		}
		case SELECT_FROM_JOIN_ON_WHERE: {
			String definition = "(assert (forall ((x Int)) (= (index-%1$s x) (and (index-%1$s-join x) (= (val-%1$s-join-%2$s x) TRUE) (= (val-%1$s-join-%3$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName(), NamingConvention.getValName(ps.getJoins().get(0).getOnExpression()), NamingConvention.getValName(ps.getWhere())));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (index-%1$s-join x) (index-%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.getSelName(ps)));
			String definition3 = "(assert (forall ((x Int)) (=> (index-%1$s-join x) (exists ((y Int) (z Int)) (and (index-%2$s y) (index-%3$s z) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition3, NamingConvention.getSelName(ps), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (index-%1$s y) (index-%2$s z)) (exists ((x Int)) (and (index-%3$s-join x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.getSelName(ps)));
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + pattern);
		}

	}

}
