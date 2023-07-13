package modeling.sql.fol.datamodel;

import modeling.data.entities.Entity;

public class EntityExtended extends Entity {
	private Boolean isIntermediate;

	public Boolean getIsIntermediate() {
		return isIntermediate;
	}

	public void setIsIntermediate(Boolean isIntermediate) {
		this.isIntermediate = isIntermediate;
	}
}
