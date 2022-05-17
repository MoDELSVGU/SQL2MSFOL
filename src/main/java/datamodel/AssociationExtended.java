package datamodel;

import java.util.HashSet;
import java.util.Set;

import org.vgu.dm2schema.dm.Association;
import org.vgu.dm2schema.dm.Attribute;
import org.vgu.dm2schema.dm.End;

public class AssociationExtended extends Association {

	public AssociationExtended(End left, End right) {
		super(left, right);
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
