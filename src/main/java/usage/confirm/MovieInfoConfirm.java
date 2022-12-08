package usage.confirm;

import java.time.LocalDate;

public class MovieInfoConfirm {

    private static final MovieInfoConfirm INSTANCE = new MovieInfoConfirm();

    public static MovieInfoConfirm getInstance() {
        return INSTANCE;
    }

    private MovieInfoConfirm() {
    }

    private static final String NAME_FORMAT_REGEX = ".{2,30}";
    //private static final String GENRE_REGEX = ".{2,30}";

    public boolean validate(String movieName,
                            String movieGenre,
                            String movieCountry,
                            String movieDuration) {
        return
                movieName.matches(NAME_FORMAT_REGEX) &&
                movieGenre.matches(NAME_FORMAT_REGEX) &&
                movieCountry.matches(NAME_FORMAT_REGEX) &&
                movieDuration.matches(NAME_FORMAT_REGEX);
    }
}




































