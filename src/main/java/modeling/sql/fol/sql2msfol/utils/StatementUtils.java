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

package modeling.sql.fol.sql2msfol.utils;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;

public class StatementUtils {

	public static boolean noFromClause(Select select) throws Exception {
		SelectBody sb = select.getSelectBody();
		if (sb instanceof PlainSelect) {
			PlainSelect ps = (PlainSelect) sb;
			return ps.getFromItem() == null;
		}
		throw new Exception("Unfamiliar SQL pattern");
	}

	public static boolean noJoinClause(Select select) throws Exception {
		SelectBody sb = select.getSelectBody();
		if (sb instanceof PlainSelect) {
			PlainSelect ps = (PlainSelect) sb;
			if (ps.getJoins() == null) {
				return true;
			}
			if (ps.getJoins().size() == 0) {
				return true;
			}
			if (ps.getJoins().size() == 1) {
				return false;
			}
		}
		throw new Exception("Unfamiliar SQL pattern");
	}

	public static boolean noWhereClause(Select select) throws Exception {
		SelectBody sb = select.getSelectBody();
		if (sb instanceof PlainSelect) {
			PlainSelect ps = (PlainSelect) sb;
			return ps.getWhere() == null;
		}
		throw new Exception("Unfamiliar SQL pattern");
	}

	public static boolean noOnClause(Select select) throws Exception {
		SelectBody sb = select.getSelectBody();
		if (sb instanceof PlainSelect) {
			PlainSelect ps = (PlainSelect) sb;
			if (ps.getJoins().get(0).getOnExpressions() == null || 
					ps.getJoins().get(0).getOnExpressions().size() == 0) {
				return true;
			}
			return false;
		}
		throw new Exception("Unfamiliar SQL pattern");
	}

}
