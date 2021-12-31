package type;

public class MisinterpretPreventionUtils {
	public static void fixInterpret (Object object, String type) {
		System.out.println(String.format("(assert (distinct null%1$s %2$s))", type, object.toString()));
		System.out.println(String.format("(assert (distinct inval%1$s %2$s))", type, object.toString()));
	}
}
