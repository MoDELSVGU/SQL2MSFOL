package type;

import sql2msfol.validator.ToBeContinuedException;

public class NullTypeGetters {
	public static NullType getNullType(SortType type) {
		if (type == SortType.BOOL) {
			throw new ToBeContinuedException();
		}
		if (type == SortType.INTEGER) {
			return NullType.INTEGER;
		}
		if (type == SortType.STRING) {
			return NullType.STRING;
		}
		return NullType.CLASSIFIER;
	}
}
