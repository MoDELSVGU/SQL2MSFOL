package fol;

import java.util.List;

import org.vgu.dm2schema.dm.DmUtils;

import main.DatamodelHolder;
import main.Environment;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import sql2msfol.validator.ToBeContinuedException;

public class FOLUtils {

	public static String def(String alias, Object referedObject) {
		if (referedObject instanceof Column) {
			Column tableColumn = (Column) referedObject;
			String columnName = tableColumn.getColumnName();
			String sourceAlias = tableColumn.getTable().getName();
			Predicate source = Environment.getInstance().getAliasMapping().get(sourceAlias);
			Object sourceType = source.getReferedObjects().get(sourceAlias);
			if (sourceType instanceof String) {
				if (DmUtils.isClass(DatamodelHolder.getInstance().getDatamodel(), (String) sourceType)) {
					String attribute = columnName;
					String attributeFunction = String.format("%s_%s", attribute, (String) sourceType);
					String template = "(= %s (%s %s))";
					return String.format(template, alias, attributeFunction, sourceAlias);
				} else {
					throw new ToBeContinuedException();
				}
			} else {
				throw new ToBeContinuedException();
			}
		} else if (referedObject instanceof Predicate) {
			// I.e. I want this to be the name of the predicate.
			// TODO: Maybe to create a FOL Predicate.java class in the future?
			Predicate predicate = (Predicate) referedObject;
//			if (DmUtils.isClass(DatamodelHolder.getInstance().getDatamodel(), string)) {
//				String template = "(%s %s)";
//				return String.format(template, string, alias);
//			} else {
//				throw new ToBeContinuedException();
//			}
			return String.format("(%s)", predicate.toString());
		} else if (referedObject instanceof LongValue) {
			LongValue longValue = (LongValue) referedObject;
			String template = "(= %s %s)";
			return String.format(template, alias, longValue.getStringValue());
		} else if (referedObject instanceof StringValue) {
			StringValue stringValue = (StringValue) referedObject;
			String template = "(= %s %s)";
			return String.format(template, alias, stringValue.toString());
		} else if (referedObject instanceof EqualsTo) {
			String template = "(= %1$s (= %1$s_left %1$s_right))";
			return String.format(template, alias);
		} else {
			throw new ToBeContinuedException();
		}
	}

	public static String and(List<String> defs) {
		if (defs.size() < 2) {
			return defs.get(0);
		} else {
			String core = "(and %s)";
			String template = String.format("%s %s", defs.get(0), "%s");
			for (int i = 1; i < defs.size(); i++) {
				if (i != defs.size() - 1) {
					template = String.format(template, String.format("%s %s", defs.get(i), "%s"));
				} else {
					template = String.format(template, defs.get(i));
				}
			}
			return String.format(core, template);
		}
	}

}
