package index;

public abstract class Index {
	private String name;
	final String declaration = "(declare-fun %1$s (Int) Bool)";
	final String comment = "; %1$s = %2$s";

	public String getName() {
		return name;
	}
	
	public String getFuncName() {
		return String.format("index%1$s", name);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract String toString();
	
	public void comment() {
		System.out.println(String.format(comment, getFuncName(), toString()));
	}
	
	public void declare() {
		System.out.println(String.format(declaration, getFuncName()));
	}

	public abstract void define();
}
