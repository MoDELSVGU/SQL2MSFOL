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

import java.util.Set;

import modeling.data.entities.Association;
import modeling.data.entities.Attribute;
import modeling.data.entities.End;
import modeling.data.entities.Entity;
import modeling.data.utils.DmUtils;
import modeling.sql.fol.datamodel.DataModelHolder;
import modeling.sql.fol.datamodel.EntityExtended;
import modeling.sql.fol.visitor.ExprVisitor;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;

public class Value {

	public static void declareFunction(String index, Expression expr, String type, Alias alias) {
		String dec = "(declare-fun val-%1$s-%2$s (Int) %3$s)";
		String valName;
		if (alias != null) {
			valName = alias.getName();
		} else {
			valName = NamingConvention.generateValName();
		}
		if (DmUtils.isClass(DataModelHolder.getDataModel(), index)) {
			Entity e = modeling.sql.fol.sql2msfol.utils.DmUtils.getEntity(DataModelHolder.getDataModel(), index);
			Set<Attribute> atts = e.getAttributes();
			boolean isDuplicated = false;
			for (Attribute att : atts) {
				if (att.getName().equals(valName)) {
					isDuplicated = true;
					break;
				}
			}
			if (isDuplicated) {
				NamingConvention.saveVal(valName, expr);
			} else {
				NamingConvention.saveVal(index, valName, expr);
				System.out.println(String.format(dec, index, valName, type));
			}
		} else {
			NamingConvention.saveVal(index, valName, expr);
			System.out.println(String.format(dec, index, valName, type));
		}
	}

	public static void defineFunction(Expression expr, Alias alias, String index) {
		ExprVisitor ev = new ExprVisitor();
		ev.setAlias(alias);
		ev.setIndex(index);
		ev.setSourceEntity(null);
		ev.setSourceAssociation(null);
		expr.accept(ev);
	}

	public static void defineFunction(Entity sourceEntity, Expression expr, Alias alias, String index) {
		ExprVisitor ev = new ExprVisitor();
		ev.setAlias(alias);
		ev.setIndex(index);
		ev.setSourceEntity(sourceEntity);
		ev.setSourceAssociation(null);
		expr.accept(ev);
	}

	public static void defineFunction(Association sourceAssociation, Expression expr, Alias alias, String index) {
		ExprVisitor ev = new ExprVisitor();
		ev.setAlias(alias);
		ev.setIndex(index);
		ev.setSourceEntity(null);
		ev.setSourceAssociation(sourceAssociation);
		expr.accept(ev);
	}

	public static void defineFunction(Association sourceAssociation) {
		End leftEnd = sourceAssociation.getLeftEnd();
		defineEnd(sourceAssociation, leftEnd, "left");
		End rightEnd = sourceAssociation.getRightEnd();
		defineEnd(sourceAssociation, rightEnd, "right");
	}

	private static void defineEnd(Association sourceAssociation, End end, String direction) {
		if (DmUtils.isClass(DataModelHolder.getDataModel(), end.getTargetClass())) {
			Entity e = modeling.sql.fol.sql2msfol.utils.DmUtils.getEntity(DataModelHolder.getDataModel(), end.getTargetClass());
			if (!(e instanceof EntityExtended)) {
				String dec = "(declare-fun val-%1$s-%3$s-%2$s (Int) Classifier)";
				System.out.println(String.format(dec, sourceAssociation.getName(), e.getName() + "_id", e.getName()));
				String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%3$s-%2$s x) (val-%3$s-%2$s (%4$s x))))))";
				System.out.println(
						String.format(def, sourceAssociation.getName(), e.getName() + "_id", e.getName(), direction));
			}
			for (Attribute att : e.getAttributes()) {
				String dec2 = "(declare-fun val-%1$s-%4$s-%2$s (Int) %3$s)";
				System.out.println(String.format(dec2, sourceAssociation.getName(), att.getName(),
						Type.convert(att.getType()), e.getName()));
				String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%3$s-%2$s x) (val-%3$s-%2$s (%4$s x))))))";
				System.out.println(
						String.format(def2, sourceAssociation.getName(), att.getName(), e.getName(), direction));
			}
			return;
		}
		{
			Association a = modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(DataModelHolder.getDataModel(),
					end.getTargetClass());
			End left = a.getLeftEnd();
			String dec = "(declare-fun val-%1$s-%4$s-%2$s (Int) %3$s)";
			System.out.println(
					String.format(dec, sourceAssociation.getName(), left.getName(), "Classifier", a.getName()));
			String def = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%3$s-%2$s x) (val-%3$s-%2$s (%4$s x))))))";
			System.out.println(String.format(def, sourceAssociation.getName(), left.getName(), a.getName(), direction));
			End right = a.getRightEnd();
			String dec2 = "(declare-fun val-%1$s-%4$s-%2$s (Int) %3$s)";
			System.out.println(
					String.format(dec2, sourceAssociation.getName(), right.getName(), "Classifier", a.getName()));
			String def2 = "(assert (forall ((x Int)) (=> (index-%1$s x) (= (val-%1$s-%3$s-%2$s x) (val-%3$s-%2$s (%4$s x))))))";
			System.out
					.println(String.format(def2, sourceAssociation.getName(), right.getName(), a.getName(), direction));
		}
	}
}
