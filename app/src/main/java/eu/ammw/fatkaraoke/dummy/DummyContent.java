package eu.ammw.fatkaraoke.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import eu.ammw.fatkaraoke.model.Song;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Song> ITEMS = new ArrayList<>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Song> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(i);
        }
    }

    private static void addItem(int position) {
        Song item = createItem(position);
        ITEMS.add(item);
        ITEM_MAP.put("" + position, item);
    }

    private static Song createItem(int position) {
        return new Song(UUID.randomUUID(), "Song " + position, "Artist " + position);
    }
}
