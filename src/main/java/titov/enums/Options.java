package titov.enums;

public enum Options {
    PREFIX ("-p"),
    PATH ("-o"),
    ADD_TO_FILE("-a"),
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