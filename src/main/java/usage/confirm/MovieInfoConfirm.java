package usage.confirm;

import java.time.LocalDate;

public class MovieInfoConfirm {

    private static final MovieInfoConfirm INSTANCE = new MovieInfoConfirm();

    public static MovieInfoConfirm getInstance() {
        return INSTANCE;
    }

    private MovieInfoConfirm() {
    }

    private static final String PRODUCT_NAME_FORMAT = ".{2,50}";
    private static final String SALARY_FORMAT = "[1-9][0-9]{0,3}([.][0-9]{1,2})?";
    private static final String AMOUNT_FORMAT = "[1-9][0-9]{0,4}";
    private static final String FILE_PATH_FORMAT = ".+\\.(jpg|png|jpeg|bmp|gif)";
    private static final String NAME_FORMAT_REGEX = ".{2,30}";
    private static final String PHONE_NUMBER_REGEX = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$";

    public boolean validate(String movieName,
                            String movieGenre,
                            String movieCountry,
                            String movieDuration) {
        return
                movieName.matches(NAME_FORMAT_REGEX) &&
                movieGenre.matches(NAME_FORMAT_REGEX) &&
                movieCountry.matches(NAME_FORMAT_REGEX) &&
                movieDuration.matches(PHONE_NUMBER_REGEX);
    }
}
