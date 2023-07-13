package modeling.sql.fol.datamodel;

import java.util.HashSet;
import java.util.Set;

import modeling.data.entities.Association;
import modeling.data.entities.Attribute;
import modeling.data.entities.End;

public class AssociationExtended extends Association {

	public AssociationExtended(String name, End left, End right) {
		super(name, left, right);
		attributes = new HashSet<Attribute>();
	}

	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}

	private Set<Attribute> attributes;

}
