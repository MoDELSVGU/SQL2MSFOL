package type;

public enum SortType {
    INTEGER("Int"),
    STRING("String"),
    BOOL("Bool"),
    CLASSIFIER("Classifier");

    private String name;

	SortType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
