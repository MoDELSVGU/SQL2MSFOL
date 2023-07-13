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

import java.util.HashMap;
import java.util.Set;

import modeling.data.entities.Association;
import modeling.data.entities.DataModel;
import modeling.data.entities.End;
import modeling.data.entities.Entity;
import modeling.data.entities.Multiplicity;
import modeling.data.utils.DmUtils;
import modeling.sql.fol.datamodel.AssociationExtended;
import modeling.sql.fol.datamodel.AttributeExtended;
import modeling.sql.fol.datamodel.DataModelHolder;
import modeling.sql.fol.datamodel.EntityExtended;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SubSelect;

public class NamingConvention {
	private static int selCounter = 0;
	private static int valCounter = 0;
	private static HashMap<SelectBody, String> selIndices = new HashMap<SelectBody, String>();
	private static HashMap<String, String> selJoinIndices = new HashMap<String, String>();
	private static HashMap<Expression, String> valIndices = new HashMap<Expression, String>();

	public static void increaseSelCounter() {
		selCounter++;
	}

	public static void increaseValCounter() {
		valCounter++;
	}

	public static String generateSelName() {
		return String.format("sel%s", String.valueOf(selCounter++));
	}

	public static String generateValName() {
		return String.format("val%s", String.valueOf(valCounter++));
	}

	public static void saveSelIndex(String name, SubSelect subSelect) {
		selIndices.put(subSelect.getSelectBody(), name);
	}

	public static void saveVal(String index, String name, Expression expr) {
		valIndices.put(expr, name);
		addNewAttribute(index, name, expr);
	}

	public static void saveVal(String name, Expression expr) {
		valIndices.put(expr, name);
	}

	private static void addNewAttribute(String index, String name, Expression expr) {
		AttributeExtended att = new AttributeExtended();
		att.setName(name);
		att.setType(Type.get(expr));
		if (DmUtils.isClass(DataModelHolder.getDataModel(), index)) {
			Entity e = modeling.sql.fol.sql2msfol.utils.DmUtils.getEntity(DataModelHolder.getDataModel(), index);
			e.getAttributes().add(att);
			return;
		}
		{
			Association a = modeling.sql.fol.sql2msfol.utils.DmUtils.getAssociation(DataModelHolder.getDataModel(), index);
			if (a instanceof AssociationExtended) {
				((AssociationExtended) a).getAttributes().add(att);
			} else {
				Set<AssociationExtended> assocs = DataModelHolder.getAssociations();
				for (AssociationExtended assoc : assocs) {
					if (assoc.getName().equals(a.getName())) {
						assoc.getAttributes().add(att);
						break;
					}
				}
			}
			return;
		}
	}

	public static void saveSelIndexEntity(String name, SelectBody selectBody) {
		selIndices.put(selectBody, name);
		addNewEntity(name);
	}

	public static void saveSelIndexAssociation(String name, SelectBody selectBody) throws Exception {
		selJoinIndices.put(getSelName(selectBody), name);
		End left = getLeft(selectBody, name);
		End right = getRight(selectBody, name);
		left.setOpp(right.getName());
		right.setOpp(left.getName());
		addNewAssociation(left, right);
	}

	private static End getRight(SelectBody selectBody, String name) throws Exception {
		End end = new End();
		PlainSelect ps = (PlainSelect) selectBody;
		FromItem fi = ps.getFromItem();
		FromItem fi2 = ps.getJoins().get(0).getRightItem();
		end.setAssociation(name);
		end.setMult(Multiplicity.MANY);
		end.setName("right");
		end.setCurrentClass(getSelName(fi2));
		end.setTargetClass(getSelName(fi));
		return end;
	}

	private static End getLeft(SelectBody selectBody, String name) throws Exception {
		End end = new End();
		PlainSelect ps = (PlainSelect) selectBody;
		FromItem fi = ps.getFromItem();
		FromItem fi2 = ps.getJoins().get(0).getRightItem();
		end.setAssociation(name);
		end.setMult(Multiplicity.MANY);
		end.setName("left");
		end.setCurrentClass(getSelName(fi));
		end.setTargetClass(getSelName(fi2));
		return end;
	}

	private static void addNewAssociation(End left, End right) {
		DataModel dm = DataModelHolder.getDataModel();
		AssociationExtended a = new AssociationExtended(left.getAssociation(), left, right);
		dm.getAssociations().add(a);
	}

	private static void addNewEntity(String name) {
		DataModel dm = DataModelHolder.getDataModel();
		EntityExtended e = new EntityExtended();
		e.setClazz(name);
		dm.getEntities().put(name, e);
	}

	public static String getSelName(FromItem fromItem) throws Exception {
		if (fromItem instanceof Table) {
			Table t = (Table) fromItem;
			return t.getName();
		}
		if (fromItem instanceof SubSelect) {
			SubSelect ss = (SubSelect) fromItem;
			return selIndices.get(ss.getSelectBody());
		}
		throw new Exception("There is no suitable fromItem name in the storage.");
	}

	public static String getSelName(SelectBody selectBody) {
		return selIndices.get(selectBody);
	}

	public static String getSelJoinName(String index) {
		return selJoinIndices.get(index);
	}

	public static String getValName(Expression expr) {
		return valIndices.get(expr);
	}

	public static void reset() {
		valCounter = 0;
		selCounter = 0;
		valIndices.clear();
		selIndices.clear();
		selJoinIndices.clear();
	}

}
