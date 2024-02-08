package titov;

public enum Options {
    PREFIX ("-p"),
    PATH ("-o"),
    FULL_STAT ("-f"),
    SHORT_STAT ("-s");

    private final String option;

    Options(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }


}