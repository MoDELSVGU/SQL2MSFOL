package visitor;

import org.vgu.dm2schema.dm.DataModel;

import net.sf.jsqlparser.statement.Block;
import net.sf.jsqlparser.statement.Commit;
import net.sf.jsqlparser.statement.CreateFunctionalStatement;
import net.sf.jsqlparser.statement.DeclareStatement;
import net.sf.jsqlparser.statement.DescribeStatement;
import net.sf.jsqlparser.statement.ExplainStatement;
import net.sf.jsqlparser.statement.IfElseStatement;
import net.sf.jsqlparser.statement.PurgeStatement;
import net.sf.jsqlparser.statement.ResetStatement;
import net.sf.jsqlparser.statement.RollbackStatement;
import net.sf.jsqlparser.statement.SavepointStatement;
import net.sf.jsqlparser.statement.SetStatement;
import net.sf.jsqlparser.statement.ShowColumnsStatement;
import net.sf.jsqlparser.statement.ShowStatement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.UseStatement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterSession;
import net.sf.jsqlparser.statement.alter.AlterSystemStatement;
import net.sf.jsqlparser.statement.alter.RenameTableStatement;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.comment.Comment;
import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.schema.CreateSchema;
import net.sf.jsqlparser.statement.create.sequence.CreateSequence;
import net.sf.jsqlparser.statement.create.synonym.CreateSynonym;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.AlterView;
import net.sf.jsqlparser.statement.create.view.CreateView;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.execute.Execute;
import net.sf.jsqlparser.statement.grant.Grant;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.merge.Merge;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;
import sql2msfol.select.Index;
import sql2msfol.select.SelectPattern;
import sql2msfol.utils.StatementUtils;

public class SelectVisitor implements StatementVisitor {
	private DataModel dataModel;

	@Override
	public void visit(SavepointStatement savepointStatement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RollbackStatement rollbackStatement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Comment comment) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Commit commit) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Delete delete) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Update update) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Insert insert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Replace replace) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Drop drop) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Truncate truncate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CreateIndex createIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CreateSchema aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CreateTable createTable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CreateView createView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AlterView alterView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Alter alter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Statements stmts) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Execute execute) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SetStatement set) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ResetStatement reset) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ShowColumnsStatement set) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ShowTablesStatement showTables) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Merge merge) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Select select) {
		try {
			if (StatementUtils.noFromClause(select)) {
				Index.declareFunction(select, SelectPattern.ONLY_SELECT);
				Index.defineFunction(select, SelectPattern.ONLY_SELECT);
				return;
			}

			if (StatementUtils.noJoinClause(select)) {
				visitFromItem(select);
				if (StatementUtils.noWhereClause(select)) {
					Index.declareFunction(select, SelectPattern.SELECT_FROM);
					Index.defineFunction(select, SelectPattern.SELECT_FROM);
					return;
				}
				
				{
					Index.declareFunction(select, SelectPattern.SELECT_FROM_WHERE);
					Index.defineFunction(select, SelectPattern.SELECT_FROM_WHERE);
					return;
				}
			}
			
			{
				visitFromItem(select);
				visitJoin(select);
				if (StatementUtils.noWhereClause(select)) {
					if (StatementUtils.noOnClause(select)) {
						Index.declareFunction(select, SelectPattern.SELECT_FROM_JOIN);
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN);
						return;
					}
					
					{
						Index.declareFunction(select, SelectPattern.SELECT_FROM_JOIN_ON);
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN_ON);
						return;
					}
				}
				
				{
					if (StatementUtils.noOnClause(select)) {
						Index.declareFunction(select, SelectPattern.SELECT_FROM_JOIN_WHERE);
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN_WHERE);
						return;
					}
					
					{
						Index.declareFunction(select, SelectPattern.SELECT_FROM_JOIN_ON_WHERE);
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN_ON_WHERE);
						return;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		
		
	}

	private void visitJoin(Select select) {
		PlainSelect ps = (PlainSelect) select.getSelectBody();
		FromItem fi = ps.getJoins().get(0).getRightItem();
		FromVisitor fiv = new FromVisitor();
		fi.accept(fiv);
	}

	private void visitFromItem(Select select) {
		PlainSelect ps = (PlainSelect) select.getSelectBody();
		FromItem fi = ps.getFromItem();
		FromVisitor fiv = new FromVisitor();
		fi.accept(fiv);
	}

	@Override
	public void visit(Upsert upsert) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UseStatement use) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Block block) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ValuesStatement values) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DescribeStatement describe) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ExplainStatement aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ShowStatement aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(DeclareStatement aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(Grant grant) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CreateSequence createSequence) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AlterSequence alterSequence) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CreateFunctionalStatement createFunctionalStatement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CreateSynonym createSynonym) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AlterSession alterSession) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IfElseStatement aThis) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(RenameTableStatement renameTableStatement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PurgeStatement purgeStatement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AlterSystemStatement alterSystemStatement) {
		// TODO Auto-generated method stub

	}

	public DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

}
