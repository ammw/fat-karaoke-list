package eu.ammw.fatkaraoke.page;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import eu.ammw.fatkaraoke.model.Song;

public class PageParser {
    private static final String TAG = "FKA PARSER";

    public List<Song> parse(String input) {
        Document page = Jsoup.parse(input.trim());
        List<TextNode> textNodes = page.body().getElementsByTag("p").get(0).textNodes();
        return textNodesToSong(textNodes);
    }

    private List<Song> textNodesToSong(List<TextNode> textNodes) {
        return textNodes.stream()
                .map(TextNode::getWholeText)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(this::fromPageEntry)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Song fromPageEntry(final String entry) {
        String[] parts = entry.split("\\s*-\\s*");
        if (parts.length < 2) {
            Log.w(TAG, "Can't parse this: " + entry);
            return null;
        } else if (parts.length > 2) {
            final String probablyTitle = parts[parts.length - 1];
            return new Song(probablyTitle, Arrays.stream(parts).filter(part -> !part.equals(probablyTitle)).collect(Collectors.joining(" - ")));
        }
        return new Song(parts[1], parts[0]);
    }
}
