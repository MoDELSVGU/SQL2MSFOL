package sql2msfol.select;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class Index {

	public static void declareFunction(Select select, SelectPattern pattern) throws Exception {
		switch (pattern) {
		case ONLY_SELECT:
		case SELECT_FROM:
		case SELECT_FROM_WHERE: {
			String declareFunction = "(declare-fun %1$s (Int) Bool)";
			System.out.println(String.format(declareFunction, NamingConvention.generateSelName()));
			break;
		}
		case SELECT_FROM_JOIN:
		case SELECT_FROM_JOIN_WHERE:
		case SELECT_FROM_JOIN_ON:
		case SELECT_FROM_JOIN_ON_WHERE: {
			String declareFunction = "(declare-fun %1$s (Int) Bool)";
			System.out.println(String.format(declareFunction, NamingConvention.generateSelName()));
			String declareFunction_intermediate = "(declare-fun %1$s-join (Int) Bool)";
			System.out.println(String.format(declareFunction_intermediate, NamingConvention.generateSelName()));
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
			String definition = "(assert (exists ((x Int)) (and (%1$s x) (forall ((y Int)) (=> (not (= x y)) (not (%1$s y)))))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName()));
			break;
		}
		case SELECT_FROM: {
			String definition = "(assert (forall ((x Int)) (= (%1$s x) (%2$s x))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName(),
					NamingConvention.getSelName(ps.getFromItem())));
			break;
		}
		case SELECT_FROM_WHERE: {
			String definition = "(assert (forall ((x Int)) (= (%1$s x) (and (%2$s x) (= (%3$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName(),
					NamingConvention.getSelName(ps.getFromItem()),
					String.format("val-%s-%s", NamingConvention.getSelName(ps.getFromItem()),
							NamingConvention.getValName(ps.getWhere()))));
			break;
		}
		case SELECT_FROM_JOIN: {
			String definition = "(assert (forall ((x Int)) (= (%1$s x) (%2$s x))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName(), NamingConvention.generateSelIntermediateName()));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (%1$s x) (%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.generateSelIntermediateName()));
			String definition3 = "(assert (forall ((x Int)) (=> (%1$s x) (exists ((y Int) (z Int)) (and (%2$s y) (%3$s z) (= y (left x) (= z (right x))))))))";
			System.out.println(String.format(definition3, NamingConvention.generateSelIntermediateName(), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (%1$s y) (%2$s z)) (exists ((x Int)) (and ($3$s x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.generateSelIntermediateName()));
			break;
		}
		case SELECT_FROM_JOIN_ON: {
			String definition = "(assert (forall ((x Int)) (= (%1$s x) (and (%2$s x) (= (%3$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName(), NamingConvention.generateSelIntermediateName(), NamingConvention.getValName(ps.getJoins().get(0).getOnExpression())));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (%1$s x) (%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.generateSelIntermediateName()));
			String definition3 = "(assert (forall ((x Int)) (=> (%1$s x) (exists ((y Int) (z Int)) (and (%2$s y) (%3$s z) (= y (left x) (= z (right x))))))))";
			System.out.println(String.format(definition3, NamingConvention.generateSelIntermediateName(), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (%1$s y) (%2$s z)) (exists ((x Int)) (and ($3$s x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.generateSelIntermediateName()));
			break;
		}
		case SELECT_FROM_JOIN_WHERE: {
			String definition = "(assert (forall ((x Int)) (= (%1$s x) (and (%2$s x) (= (%3$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName(), NamingConvention.generateSelIntermediateName(), NamingConvention.getValName(ps.getWhere())));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (%1$s x) (%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.generateSelIntermediateName()));
			String definition3 = "(assert (forall ((x Int)) (=> (%1$s x) (exists ((y Int) (z Int)) (and (%2$s y) (%3$s z) (= y (left x) (= z (right x))))))))";
			System.out.println(String.format(definition3, NamingConvention.generateSelIntermediateName(), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (%1$s y) (%2$s z)) (exists ((x Int)) (and ($3$s x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.generateSelIntermediateName()));
			break;
		}
		case SELECT_FROM_JOIN_ON_WHERE: {
			String definition = "(assert (forall ((x Int)) (= (%1$s x) (and (%2$s x) (= (%3$s x) TRUE) (= (%4$s x) TRUE)))))";
			System.out.println(String.format(definition, NamingConvention.generateSelName(), NamingConvention.generateSelIntermediateName(), NamingConvention.getValName(ps.getJoins().get(0).getOnExpression()), NamingConvention.getValName(ps.getWhere())));
			String definition2 = "(assert (forall ((x Int) (y Int)) (=> (and (%1$s x) (%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
			System.out.println(String.format(definition2, NamingConvention.generateSelIntermediateName()));
			String definition3 = "(assert (forall ((x Int)) (=> (%1$s x) (exists ((y Int) (z Int)) (and (%2$s y) (%3$s z) (= y (left x) (= z (right x))))))))";
			System.out.println(String.format(definition3, NamingConvention.generateSelIntermediateName(), NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem())));
			String definition4 = "(assert (forall ((y Int) (z Int)) (=> (and (%1$s y) (%2$s z)) (exists ((x Int)) (and ($3$s x) (= y (left x)) (= z (right x)))))))";
			System.out.println(String.format(definition4, NamingConvention.getSelName(ps.getFromItem()), NamingConvention.getSelName(ps.getJoins().get(0).getRightItem()), NamingConvention.generateSelIntermediateName()));
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + pattern);
		}

	}

}
