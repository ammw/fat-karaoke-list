package eu.ammw.fatkaraoke.util;

public final class TestDataSource {
    private static final String HEADER = "\n" +
            "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1250\"></head><body bgcolor=\"#000000\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "\n" +
            "\n" +
            "<title>Karaoke z FAAK Music Productions - Lista utworów</title>\n" +
            "\n" +
            "<link rel=\"icon\" href=\"http://faak.org/cli/favicon.png\">\n" +
            "<span style=\"font-family: georgia,palatino; font-size: 24pt; color: #ffffff;\">\n" +
            "<b>Lista utworów:</b><br>\n" +
            "<span style=\"font-family: georgia,palatino; font-size: 10pt; color: #ff0000;\">\n" +
            "<b>Lista bez liter charakterystycznych dla danego jêzyka</b><br>\n" +
            "This list does not support language specific characters<br><br>\n" +
            "\n" +
            "\n" +
            "<span style=\"font-family: georgia,palatino; font-size: 12pt; color: #ffffff;\">\n" +
            "Stan z dnia 17.07.2020 r.\n" +
            "<br>\n" +
            "<span style=\"font-family: georgia,palatino; font-size: 10pt; color: #ffffff;\">\n" +
            "<p>\n" +
            "\n" +
            "\n" +
            "<br>\t";
    private static final String FOOTER = "\n" +
            "<br>\t\n" +
            "<br>\t\n" +
            "</p></span></span></span></span></body></html>";
    private static final String SEPARATOR = "\n<br>\t";

    private static final String ALL_ARTISTS_PAGE = generatePage("101 Dalmations - Cruella De Vill",
            "2+1 - Isc W Strone Slonca",
            "3 Doors Down - Here Without You",
            "4 Non Blondes - What's Up",
            "50 Cent &amp; Eminem - Patiently Waiting",
            "6LACK - Pretty Little Fears",
            "8 Stops 7 - Question Everything",
            "98 Degrees - Why (Are We Still Friends)",
            "Adele - Chasing Pavements",
            "Bjork - Army Of Me",
            "Clean Bandit &amp; Jess Glynne - Rather Be",
            "Dua Lipa - Bang Bang",
            "Enej - Kamien z napisem LOVE",
            "Frank Sinatra - Something Stupid",
            "George Michael - Freedom '90",
            "Happysad - Zanim Pojde",
            "Imagine Dragons - Believer",
            "Jamal - Policeman",
            "Killers - Mr. Brightside",
            "Lily Allen - Somewhere Only We Know",
            "Maryla Rodowicz - Ale To Juz Bylo",
            "Nat King Cole - Nature Boy",
            "One Republic - Counting Stars",
            "Pawel Domagala - Wez Nie Pytaj",
            "Queen - Bohemian Rhapsody",
            "Rocío Dúrcal - La gata bajo la lluvia",
            "Selah Sue - This World",
            "Tiësto &amp; Mabel - God is a Dancer",
            "U2 - Ordinary Love",
            "Virgin - Dwie Bajki",
            "Wyclef Jean - Pussycat",
            "X - White Girl",
            "Yugoton - Malcziki",
            "Zdzisława Sośnicka - Aleja Gwiazd");

    static String generatePage(String... songs) {
        return HEADER + String.join(SEPARATOR, songs) + FOOTER;
    }

    public static String getTestAllArtistsPage() {
        return ALL_ARTISTS_PAGE;
    }

    public static String getTestPageWithBadRecords() {
        return HEADER + String.join(SEPARATOR, "Artist 1 - Artist 2 - Title", "Some Random Text") + FOOTER;
    }
}
