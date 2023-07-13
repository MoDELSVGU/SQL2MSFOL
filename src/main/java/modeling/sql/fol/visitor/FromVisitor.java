/**************************************************************************
 Copyright 2020 Vietnamese-German-University 
 Copyright 2023 ETH Zurich
 
 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License. You may obtain a copy of
 the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 License for the specific language governing permissions and limitations under
 the License.
 
 @author: hoangnguyen (hoang.nguyen@inf.ethz.ch)
 ***************************************************************************/

package modeling.sql.fol.visitor;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesisFromItem;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;

public class FromVisitor implements FromItemVisitor {
	
	private Alias alias;

	@Override
	public void visit(Table tableName) {
//		NamingConvention.saveSelIndex(tableName.getName(), tableName);
	}

	@Override
	public void visit(SubSelect subSelect) {
		Select newSelect = new Select();
		newSelect.setSelectBody(subSelect.getSelectBody());
		SelectVisitor sv = new SelectVisitor(false);
		sv.setAlias(alias);
		newSelect.accept(sv);
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

	public void setAlias(Alias alias) {
		this.alias = alias;
	}

}
