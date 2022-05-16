package datamodel;

import org.vgu.dm2schema.dm.DataModel;

public class DataModelHolder {
	private static DataModel dataModel;

	public static DataModel getDataModel() {
		return dataModel;
	}

	public static void setDataModel(DataModel dm) {
		dataModel = dm;
	}
	
	
}
