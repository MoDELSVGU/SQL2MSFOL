package mappings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.vgu.dm2schema.dm.Association;
import org.vgu.dm2schema.dm.Attribute;
import org.vgu.dm2schema.dm.DataModel;
import org.vgu.dm2schema.dm.End;
import org.vgu.dm2schema.dm.Entity;

import datamodel.DataModelUtils;
import index.Index;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import type.Type;
import type.TypeUtils;
import value.AssociationEndValue;
import value.AttributeValue;
import value.ExpressionValue;
import value.Value;

public class ValueMapping {
	
	private static Integer counter;
	private static List<Value> values;

	public static void reset() {
		ValueMapping.counter = 0;
		ValueMapping.values = new ArrayList<Value>();
		valueDatamodel();
	}
	
	private static void valueDatamodel() {
		DataModel dataModel = DataModelUtils.getDataModel();
		dataModel.getEntities().values().forEach(e -> valueEntity(e));
		dataModel.getAssociations().forEach(a -> valueAssociation(a));
	}

	private static void valueEntity(Entity e) {
		Set<Attribute> atts = e.getAttributes();
		for (Attribute att : atts) {
			AttributeValue av = new AttributeValue();
			av.setSourceIndex(IndexMapping.getEntityIndex(e));
			av.setSource(att);
			av.setType(new Type(TypeUtils.convert(att.getType())));
			av.setName(att.getName());
			ValueMapping.add(av);
		}
	}
	
	private static void valueAssociation(Association a) {
		End left = a.getLeft();
		End right = a.getRight();
		AssociationEndValue aev_left = new AssociationEndValue();
		aev_left.setSourceIndex(IndexMapping.getAssociationIndex(a));
		aev_left.setSource(left);
		aev_left.setIsLeft(true);
		aev_left.setType(new Type("Classifier"));
		aev_left.setName(left.getName());
		ValueMapping.add(aev_left);
		AssociationEndValue aev_right = new AssociationEndValue();
		aev_right.setSourceIndex(IndexMapping.getAssociationIndex(a));
		aev_right.setSource(right);
		aev_right.setIsLeft(false);
		aev_right.setType(new Type("Classifier"));
		aev_right.setName(right.getName());
		ValueMapping.add(aev_right);
	}

	private static String generateName() {
		return String.valueOf(counter++);
	}

	public static void add(Value value) {
		value.setName(generateName());
		values.add(value);
	}

	public static void declare() {
		ValueMapping.values.forEach(v -> {
			v.comment();
			v.declare();
		});
	}

	public static Value getValue(Index index, String columnName) {
		for (Value v : values) {
			if (v instanceof ExpressionValue) {
				ExpressionValue ev = (ExpressionValue) v;
				if (ev.getParentIndex().equals(index)) {
					Expression expr = ev.getExpr();
					if (expr instanceof Column) {
						Column c = (Column) expr;
						if (c.getColumnName().equals(columnName)) {
							return v;
						}
					}
				}
			}
		}
		return null;
	}
	
	public static Value getValue(Expression expr) {
		for (Value v : values) {
			if (v instanceof ExpressionValue) {
				ExpressionValue ev = (ExpressionValue) v;
				if (ev.getExpr().equals(expr)) {
					return v;
				}
			}
		}
		return null;
	}

	public static void define() {
		ValueMapping.values.forEach(v -> {
			v.define();
		});
	}

}
