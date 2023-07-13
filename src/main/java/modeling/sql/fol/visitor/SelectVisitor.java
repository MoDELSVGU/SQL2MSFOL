package modeling.sql.fol.visitor;

import modeling.data.entities.Association;
import modeling.data.entities.Entity;
import modeling.data.utils.DmUtils;
import modeling.sql.fol.datamodel.DataModelHolder;
import modeling.sql.fol.sql2msfol.select.Index;
import modeling.sql.fol.sql2msfol.select.NamingConvention;
import modeling.sql.fol.sql2msfol.select.SelectPattern;
import modeling.sql.fol.sql2msfol.select.Value;
import modeling.sql.fol.sql2msfol.utils.StatementUtils;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
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
import net.sf.jsqlparser.statement.UnsupportedStatement;
import net.sf.jsqlparser.statement.UseStatement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterSession;
import net.sf.jsqlparser.statement.alter.AlterSystemStatement;
import net.sf.jsqlparser.statement.alter.RenameTableStatement;
import net.sf.jsqlparser.statement.alter.sequence.AlterSequence;
import net.sf.jsqlparser.statement.analyze.Analyze;
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
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.show.ShowIndexStatement;
import net.sf.jsqlparser.statement.show.ShowTablesStatement;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.upsert.Upsert;
import net.sf.jsqlparser.statement.values.ValuesStatement;

public class SelectVisitor implements StatementVisitor {

	private Boolean init;
	private Alias alias;

	private SelectVisitor() {
	}

	public SelectVisitor(boolean init) {
		this.init = init;
	}

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
		PlainSelect ps = (PlainSelect) select.getSelectBody();
		try {
			if (StatementUtils.noFromClause(select)) {
				/* SELECT */
				Index.declareFunction(select, alias, SelectPattern.ONLY_SELECT);
				Index.defineFunction(select, SelectPattern.ONLY_SELECT);
				String index = NamingConvention.getSelName(ps);
				boolean isRes = false;
				for (SelectItem si : ps.getSelectItems()) {
					SelectExpressionItem sei = (SelectExpressionItem) si;
					Expression expr = sei.getExpression();
					Alias siAlias;
					if (!isRes && init) {
						siAlias = new Alias("res");
						isRes = true;
					} else {
						siAlias = sei.getAlias();
					}
					Value.defineFunction(expr, siAlias, index);
				}
				return;
			}

			if (StatementUtils.noJoinClause(select)) {
				visitFromItem(select);
				FromItem fi = ps.getFromItem();
				if (StatementUtils.noWhereClause(select)) {
					/* SELECT FROM */
					Index.declareFunction(select, alias, SelectPattern.SELECT_FROM);
					Index.defineFunction(select, SelectPattern.SELECT_FROM);
					Entity sourceEntity = getEntity(fi);
					String index = NamingConvention.getSelName(ps);
					if (sourceEntity != null) {
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceEntity, expr, siAlias, index);
						}
						return;
					}
					Association sourceAssociation = getAssociation(fi);
					{
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceAssociation, expr, siAlias, index);
						}
						return;
					}
				}

				{
					/* SELECT FROM WHERE */
					Expression where = ps.getWhere();
					Entity sourceEntity = getEntity(fi);
					if (sourceEntity != null) {
						Value.defineFunction(getEntity(fi), where, null, getEntity(fi).getName());
					} else {
						Value.defineFunction(getAssociation(fi), where, null, getAssociation(fi).getName());
					}

					Index.declareFunction(select, alias, SelectPattern.SELECT_FROM_WHERE);
					Index.defineFunction(select, SelectPattern.SELECT_FROM_WHERE);
					String index = NamingConvention.getSelName(ps);
					if (sourceEntity != null) {
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceEntity, expr, siAlias, index);
						}
						return;
					}
					Association sourceAssociation = getAssociation(fi);
					{
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceAssociation, expr, siAlias, index);
						}
						return;
					}
				}
			}

			{
				visitFromItem(select);
				visitJoin(select);
				if (StatementUtils.noWhereClause(select)) {
					if (StatementUtils.noOnClause(select)) {
						/* SELECT FROM JOIN */
						Index.declareFunction(select, alias, SelectPattern.SELECT_FROM_JOIN);
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN);
						String index = NamingConvention.getSelName(ps);
						Association sourceAssociation = getAssociation(NamingConvention.getSelJoinName(index));
						Value.defineFunction(sourceAssociation);
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceAssociation, expr, siAlias, index);
						}
						return;
					}

					{
						/* SELECT FROM JOIN ON */
						Index.declareFunction(select, alias, SelectPattern.SELECT_FROM_JOIN_ON);
						String index = NamingConvention.getSelName(ps);
						Association sourceAssociation = getAssociation(NamingConvention.getSelJoinName(index));
						Value.defineFunction(sourceAssociation);
						Expression on = ps.getJoins().get(0).getOnExpression();
						Value.defineFunction(sourceAssociation, on, null, sourceAssociation.getName());
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN_ON);
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceAssociation, expr, siAlias, index);
						}
						return;
					}
				}

				{
					if (StatementUtils.noOnClause(select)) {
						Index.declareFunction(select, alias, SelectPattern.SELECT_FROM_JOIN_WHERE);
						String index = NamingConvention.getSelName(ps);
						Association sourceAssociation = getAssociation(NamingConvention.getSelJoinName(index));
						Value.defineFunction(sourceAssociation);
						Expression where = ps.getWhere();
						Value.defineFunction(sourceAssociation, where, null, sourceAssociation.getName());
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN_WHERE);
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceAssociation, expr, siAlias, index);
						}
						return;
					}

					{
						Index.declareFunction(select, alias, SelectPattern.SELECT_FROM_JOIN_ON_WHERE);
						String index = NamingConvention.getSelName(ps);
						Association sourceAssociation = getAssociation(NamingConvention.getSelJoinName(index));
						Value.defineFunction(sourceAssociation);
						Expression on = ps.getJoins().get(0).getOnExpression();
						Value.defineFunction(sourceAssociation, on, null, sourceAssociation.getName());
						Expression where = ps.getWhere();
						Value.defineFunction(sourceAssociation, where, null, sourceAssociation.getName());
						Index.defineFunction(select, SelectPattern.SELECT_FROM_JOIN_ON_WHERE);
						boolean isRes = false;
						for (SelectItem si : ps.getSelectItems()) {
							SelectExpressionItem sei = (SelectExpressionItem) si;
							Expression expr = sei.getExpression();
							Alias siAlias;
							if (!isRes && init) {
								siAlias = new Alias("res");
								isRes = true;
							} else {
								siAlias = sei.getAlias();
							}
							Value.defineFunction(sourceAssociation, expr, siAlias, index);
						}
						return;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Association getAssociation(String name) {
		try {
			return modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(DataModelHolder.getDataModel(), name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Association getAssociation(FromItem fi) {
		if (fi instanceof Table) {
			Table tb = (Table) fi;
			return modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(DataModelHolder.getDataModel(), tb.getName());
		} else {
			try {
				return modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(DataModelHolder.getDataModel(),
						NamingConvention.getSelName(fi));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private Entity getEntity(FromItem fi) {
		if (fi instanceof Table) {
			Table tb = (Table) fi;
			return modeling.sql.fol.sql2msfol.utils.DmUtils.getEntity(DataModelHolder.getDataModel(), tb.getName());
		} else {
			try {
				return modeling.sql.fol.sql2msfol.utils.DmUtils.getEntity(DataModelHolder.getDataModel(),
						NamingConvention.getSelName(fi));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void visitJoin(Select select) {
		PlainSelect ps = (PlainSelect) select.getSelectBody();
		FromItem fi = ps.getJoins().get(0).getRightItem();
		Alias alias = fi.getAlias();
		FromVisitor fiv = new FromVisitor();
		fiv.setAlias(alias);
		fi.accept(fiv);
	}

	private void visitFromItem(Select select) {
		PlainSelect ps = (PlainSelect) select.getSelectBody();
		FromItem fi = ps.getFromItem();
		Alias alias = fi.getAlias();
		FromVisitor fiv = new FromVisitor();
		fiv.setAlias(alias);
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

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

	@Override
	public void visit(Analyze analyze) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ShowIndexStatement showIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UnsupportedStatement unsupportedStatement) {
		// TODO Auto-generated method stub

	}
}
