package eu.ammw.fatkaraoke.page;

import org.junit.jupiter.api.Test;

import java.util.List;

import eu.ammw.fatkaraoke.model.Song;
import eu.ammw.fatkaraoke.util.TestDataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class PageParserTest {
    private static final String TEST_PAGE = TestDataSource.getTestAllArtistsPage();
    private static final String TEST_PAGE_WITH_BAD_RECORDS = TestDataSource.getTestPageWithBadRecords();

    private final PageParser parser = new PageParser();

    @Test
    void shouldParseTestPage() {
        List<Song> songs = parser.parse(TEST_PAGE);
        assertThat(songs)
                .hasSize(34)
                .extracting(Song::getArtist, Song::getTitle)
                .containsExactlyInAnyOrder(
                        tuple("101 Dalmations", "Cruella De Vill"),
                        tuple("2+1", "Isc W Strone Slonca"),
                        tuple("3 Doors Down", "Here Without You"),
                        tuple("4 Non Blondes", "What's Up"),
                        tuple("50 Cent & Eminem", "Patiently Waiting"),
                        tuple("6LACK", "Pretty Little Fears"),
                        tuple("8 Stops 7", "Question Everything"),
                        tuple("98 Degrees", "Why (Are We Still Friends)"),
                        tuple("Adele", "Chasing Pavements"),
                        tuple("Bjork", "Army Of Me"),
                        tuple("Clean Bandit & Jess Glynne", "Rather Be"),
                        tuple("Dua Lipa", "Bang Bang"),
                        tuple("Enej", "Kamien z napisem LOVE"),
                        tuple("Frank Sinatra", "Something Stupid"),
                        tuple("George Michael", "Freedom '90"),
                        tuple("Happysad", "Zanim Pojde"),
                        tuple("Imagine Dragons", "Believer"),
                        tuple("Jamal", "Policeman"),
                        tuple("Killers", "Mr. Brightside"),
                        tuple("Lily Allen", "Somewhere Only We Know"),
                        tuple("Maryla Rodowicz", "Ale To Juz Bylo"),
                        tuple("Nat King Cole", "Nature Boy"),
                        tuple("One Republic", "Counting Stars"),
                        tuple("Pawel Domagala", "Wez Nie Pytaj"),
                        tuple("Queen", "Bohemian Rhapsody"),
                        tuple("Rocío Dúrcal", "La gata bajo la lluvia"),
                        tuple("Selah Sue", "This World"),
                        tuple("Tiësto & Mabel", "God is a Dancer"),
                        tuple("U2", "Ordinary Love"),
                        tuple("Virgin", "Dwie Bajki"),
                        tuple("Wyclef Jean", "Pussycat"),
                        tuple("X", "White Girl"),
                        tuple("Yugoton", "Malcziki"),
                        tuple("Zdzisława Sośnicka", "Aleja Gwiazd"));
    }

    @Test
    void shouldHandleInvalidRecords() {
        List<Song> songs = parser.parse(TEST_PAGE_WITH_BAD_RECORDS);
        assertThat(songs)
                .hasSize(1)
                .extracting(Song::getArtist, Song::getTitle)
                .containsExactly(tuple("Artist 1 - Artist 2", "Title"));
    }
}
