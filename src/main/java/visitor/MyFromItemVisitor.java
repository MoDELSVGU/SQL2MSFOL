package visitor;

import fol.Predicate;
import main.Environment;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesisFromItem;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;
import type.SortType;

public class MyFromItemVisitor implements FromItemVisitor {

	@Override
	public void visit(Table tableName) {
		System.out.println(
				String.format("(declare-fun %s_from (%s) Bool)", tableName.getName(), SortType.CLASSIFIER.getName()));
		System.out.println(String.format("(assert ((forall %s %s)) (= (%s_from %s) (%s %s)))",
				tableName.getAlias().getName(), SortType.CLASSIFIER.getName(), tableName.getName(),
				tableName.getAlias().getName(), tableName.getName(), tableName.getAlias().getName()));
		
		Predicate predicate = new Predicate(String.format("%s_from", tableName.getName()));
		predicate.getParameters().put(tableName.getAlias().getName(), SortType.CLASSIFIER);
		predicate.getReferedObjects().put(tableName.getAlias().getName(), tableName.getName());
		
		Environment.getInstance().getFromMappings().put(tableName.getAlias().getName(), predicate);
		Environment.getInstance().getFromParams().put(tableName.getAlias().getName(), SortType.CLASSIFIER);
	}

	@Override
	public void visit(SubSelect subSelect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SubJoin subjoin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LateralSubSelect lateralSubSelect) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ValuesList valuesList) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(TableFunction tableFunction) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ParenthesisFromItem aThis) {
		// TODO Auto-generated method stub

	}

}
