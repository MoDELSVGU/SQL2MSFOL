package value;

import index.Index;
import type.Type;

public abstract class Value {

	private String name;
	private Index parentIndex; // parent is where the expression stands
	private Index sourceIndex; // source is where the expresison value can be found
	private Type type;

	final String declaration = "(declare-fun %1$s (Int) %2$s)";
	final String comment = "; %1$s = %2$s";

	public String getName() {
		return name;
	}
	
	public String getFuncName() {
		return String.format("val-%1$s-expr%2$s", parentIndex.getFuncName(), getName());
	}

	public void setName(String name) {
		this.name = name;
	}

	public void comment() {
		System.out.println(String.format(comment, getFuncName(), toString()));
	}

	public void declare() {
		System.out.println(String.format(declaration, getFuncName(), type.getName()));
	}

	public Index getParentIndex() {
		return parentIndex;
	}

	public void setParentIndex(Index source) {
		this.parentIndex = source;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Index getSourceIndex() {
		return sourceIndex;
	}

	public void setSourceIndex(Index sourceIndex) {
		this.sourceIndex = sourceIndex;
	}

	public abstract void define();

}
