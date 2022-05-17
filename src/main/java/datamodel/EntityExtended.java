package datamodel;

import org.vgu.dm2schema.dm.Entity;

public class EntityExtended extends Entity {
	private Boolean isIntermediate;

	public Boolean getIsIntermediate() {
		return isIntermediate;
	}

	public void setIsIntermediate(Boolean isIntermediate) {
		this.isIntermediate = isIntermediate;
	}
}
