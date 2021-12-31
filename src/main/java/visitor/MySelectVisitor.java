package visitor;

import java.util.List;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectVisitor;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.WithItem;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import sql2msfol.validator.ToBeContinuedException;

public class MySelectVisitor implements SelectVisitor {

	@Override
	public void visit(PlainSelect plainSelect) {
		List<SelectItem> selectItems = plainSelect.getSelectItems();
		FromItem fromItem = plainSelect.getFromItem();
		Expression whereExp = plainSelect.getWhere();
//		if (fromItem != null) {
//			// I.e. this else-branch includes the FROM-clause (and possibly a WHERE-clause)
//			MyFromItemVisitor visitor = new MyFromItemVisitor();
//			fromItem.accept(visitor);
//			if (whereExp != null) {
//				throw new ToBeContinuedException();
//			}
//			for (SelectItem selectItem : selectItems) {
//				MySelectItemVisitor singleVisitor = new MySelectItemVisitor();
//				selectItem.accept(singleVisitor);
//			}
//		} else {
//			// I.e. this is a single-tuple SELECT-statement
//			for (SelectItem selectItem : selectItems) {
//				MySingleSelectItemVisitor visitor = new MySingleSelectItemVisitor();
//				selectItem.accept(visitor);
//			}
//		}
		if (fromItem != null) {
			MyFromItemVisitor visitor = new MyFromItemVisitor();
			fromItem.accept(visitor);
			if (whereExp != null) {
				throw new ToBeContinuedException();
			}
		}
		for (SelectItem selectItem : selectItems) {
			MySelectItemVisitor singleVisitor = new MySelectItemVisitor();
			selectItem.accept(singleVisitor);
		}
	}

	@Override
	public void visit(SetOperationList setOpList) {

	}

	@Override
	public void visit(WithItem withItem) {

	}

	@Override
	public void visit(ValuesStatement aThis) {
	}

}
