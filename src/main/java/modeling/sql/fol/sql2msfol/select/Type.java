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

package modeling.sql.fol.sql2msfol.select;

import modeling.sql.fol.visitor.ExprType;
import net.sf.jsqlparser.expression.Expression;

public class Type {
	
	public static String get(Expression expr) {
		ExprType et = new ExprType();
		expr.accept(et);
		return et.getType();
	}

	public static String nullOf(Expression expr) {
		String type = get(expr);
		if ("String".equals(type)) {
			return "nullString";
		}
		if ("Int".equals(type)) {
			return "nullInt";
		}
		if ("Bool".equals(type)) {
			return "NULL";
		}
		return "nullClassifier";
	}
	
	public static String convert(String type) {
		if ("Integer".equals(type)) {
			return "Int";
		}
		if ("Boolean".equals(type)) {
			return "Bool";
		}
		else return type;
	}

}
