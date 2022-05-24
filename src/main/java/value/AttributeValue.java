package value;

import org.vgu.dm2schema.dm.Attribute;
import org.vgu.dm2schema.dm.Entity;

import datamodel.DataModelUtils;

public class AttributeValue extends Value {
	private Attribute source;

	public Attribute getSource() {
		return source;
	}

	public void setSource(Attribute source) {
		this.source = source;
	}

	@Override
	public void define() {
		String def = "(assert (forall ((x Int)) (%1$s x) (= (%2$s x) (%3$s (id x)))))";
		Entity e = DataModelUtils.getEntity(source);
		System.out.println(String.format(def, getSourceIndex().getFuncName(), getFuncName(), source.getName()+"_"+e.getName()));
	}
}
