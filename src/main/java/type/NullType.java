package type;

public enum NullType {
	INTEGER("nullInt"),
    STRING("nullString"),
    CLASSIFIER("nullClassifier");

    private String name;

    NullType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
