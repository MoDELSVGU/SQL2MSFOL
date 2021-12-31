package main;

import org.vgu.dm2schema.dm.DataModel;

public class DatamodelHolder {
	private DataModel datamodel;
	private static DatamodelHolder instance;

	private DatamodelHolder() {
	}

	public static DatamodelHolder initialize() {
		if (instance == null) {
			instance = new DatamodelHolder();
		}
		return instance;
	}

	public static DatamodelHolder getInstance() {
		return instance;
	}

	public DataModel getDatamodel() {
		return datamodel;
	}

	public void setDatamodel(DataModel datamodel) {
		this.datamodel = datamodel;
	}
}
