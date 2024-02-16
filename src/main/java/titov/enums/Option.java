package titov.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Option {
    PREFIX ("-p"),
    PATH ("-o"),
    ADD_TO_FILE("-a"),
    FULL_STAT ("-f"),
    SHORT_STAT ("-s");

    private final String option;

    @Override
    public String toString() {
        return option;
    }

}