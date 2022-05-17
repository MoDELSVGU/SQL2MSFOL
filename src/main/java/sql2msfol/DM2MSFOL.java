package sql2msfol;

import org.vgu.dm2schema.dm.Association;
import org.vgu.dm2schema.dm.Attribute;
import org.vgu.dm2schema.dm.DataModel;
import org.vgu.dm2schema.dm.Entity;

public class DM2MSFOL {

	public static void formalize(DataModel dataModel) {
		init();
		dataModel.getEntities().values().forEach(c -> formalize(c));
		dataModel.getAssociations().forEach(a -> formalize(a));
	}

	private static void formalize(Association a) {
		String associationName = a.getName();
		String leftEnd = a.getRightEnd();
		String rightEnd = a.getLeftEnd();
		String assoc_dec = "(declare-fun index-%s (Int) Bool)";
		System.out.println(String.format(assoc_dec, associationName));
		String assoc_dec2 = "(declare-fun val-%1$s-%2$s (Int) Classifier)";
		System.out.println(String.format(assoc_dec2, associationName, leftEnd));
		String assoc_dec3 = "(declare-fun val-%1$s-%2$s (Int) Classifier)";
		System.out.println(String.format(assoc_dec3, associationName, rightEnd));
		String assoc_def = "(assert (forall ((x Int)) (=> (index-%1$s x) (exists ((c1 Classifier) (c2 Classifier)) (and (%1$s c1 c2) (= c1 (id (left x))) (= c2 (id (right x))))))))";
		System.out.println(String.format(assoc_def, associationName));
		String assoc_def2 = "(assert (forall ((c1 Classifier) (c2 Classifier)) (=> (%1$s c1 c2) (exists ((x Int)) (and (index-%1$s x) (= c1 (id (left x))) (= c2 (id (right x))))))))";
		System.out.println(String.format(assoc_def2, associationName));
		String assoc_def3 = "(assert (forall ((x Int) (y Int)) (=> (and (index-%1$s x) (index-%1$s y) (not (= x y))) (not (and (= (left x) (left y)) (= (right x) (right y)))))))";
		System.out.println(String.format(assoc_def3, associationName));
		String assoc_def4 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) (id (left x))))))";
		System.out.println(String.format(assoc_def4, associationName, leftEnd));
		String assoc_def5 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) (id (right x))))))";
		System.out.println(String.format(assoc_def5, associationName, rightEnd));
	}

	private static void formalize(Entity c) {
		String name = c.getName();
		String index_dec = "(declare-fun index-%s (Int) Bool)";
		System.out.println(String.format(index_dec, name));
		String id_dec = "(declare-fun val-%1$s-%1$s_id (Int) Classifier)";
		System.out.println(String.format(id_dec, name));
		String id_def = "(assert (forall ((x Int)) (=> (index-%1$s x) (exists ((c Classifier)) (and (%1$s c) (= c (id x)))))))";
		System.out.println(String.format(id_def, name));
		String id_def2 = "(assert (forall ((c Classifier)) (=> (%1$s c) (exists ((x Int)) (and (index-%1$s x) (= c (id x)))))))";
		System.out.println(String.format(id_def2, name));
		String id_def3 = "(assert (forall ((x Int) (y Int)) (=> (and (index-%1$s x) (index-%1$s y) (not (= x y))) (not (= (id x) (id y))))))";
		System.out.println(String.format(id_def3, name));
		String id_def4 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%1$s_id x) (id x)))))";
		System.out.println(String.format(id_def4, name));
		c.getAttributes().forEach(a -> formalize(c, a));
	}

	private static void formalize(Entity c, Attribute a) {
		String entityName = c.getName();
		String attributeName = a.getName();
		String type = "String".equals(a.getType()) ? "String" : "Int";
		String att_dec = "(declare-fun val-%s-%s (Int) %s)";
		System.out.println(String.format(att_dec, entityName, attributeName, type));
		String att_def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%2$s x) (%2$s_%1$s (id x))))))";
		System.out.println(String.format(att_def, entityName, attributeName));
	}

	private static void init() {
		defineSort_BOOL();
		declareFunction_id();
		declareFunction_left();
		declareFunction_right();
	}

	private static void declareFunction_right() {
		System.out.println("(declare-fun right (Int) Int)");
	}

	private static void declareFunction_left() {
		System.out.println("(declare-fun left (Int) Int)");
	}

	private static void declareFunction_id() {
		System.out.println("(declare-fun id (Int) Classifier)");
	}

	private static void defineSort_BOOL() {
 		System.out.println("(declare-sort BOOL 0)");
		System.out.println("(declare-const TRUE BOOL)");
		System.out.println("(declare-const FALSE BOOL)");
		System.out.println("(declare-const NULL BOOL)");
		System.out.println("(assert (not (= TRUE FALSE)))");
		System.out.println("(assert (not (= TRUE NULL)))");
		System.out.println("(assert (not (= FALSE NULL)))");
		System.out.println("(assert (forall ((x BOOL)) (or (= x TRUE) (or (= x FALSE) (= x NULL)))))");
	}
	
	

}
