package configurations;

import java.util.Map;

public class Configuration {
	private String dataModel;
	private String sql;

	private static final String ENV_DATAMODEL = "DM";
	private static final String ENV_SQL = "SQL";

	public String getDataModel() {
		return dataModel;
	}

	public void setDataModel(String dataModel) {
		this.dataModel = dataModel;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Configuration() {
		final Map<String, String> env = System.getenv();

		final String dataModelPath = env.get(ENV_DATAMODEL);
		if (dataModelPath != null) {
			setDataModel(dataModelPath);
		}

		final String sql = env.get(ENV_SQL);
		if (sql != null) {
			setSql(sql);
		}
	}

}
