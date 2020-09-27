package eu.ammw.fatkaraoke.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eu.ammw.fatkaraoke.common.Callback;
import eu.ammw.fatkaraoke.db.SongDatabase;
import eu.ammw.fatkaraoke.db.dao.SongDao;
import eu.ammw.fatkaraoke.model.Song;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class SongRepositoryTest {

    private static final List<Song> SONG_LIST = Arrays.asList(
            new Song(1, "What's Up", "4 Non Blondes"),
            new Song(2, "Let It Go", "Idina Menzel"),
            new Song(3, "Wonderwall", "Oasis")
    );

    private static final ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Mock
    private SongDatabase songDatabase;
    @Mock
    private SongDao songDao;
    @Mock
    private Callback<List<Song>> callback;

    private SongRepository songRepository;

    @BeforeEach
    public void setUp() {
        openMocks(this);
        when(songDatabase.songDao()).thenReturn(songDao);
        songRepository = new SongRepository(executorService, songDatabase);
    }

    @Test
    public void shouldSearchSongs() {
        // GIVEN
        when(songDao.find("%test%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("test", callback);
    }

    @Test
    public void shouldReturnAllWhenQueryNull() {
        // GIVEN
        when(songDao.getAll()).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs(null, callback);
    }

    @Test
    public void shouldReturnAllWhenQueryEmpty() {
        // GIVEN
        when(songDao.getAll()).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("", callback);
    }
}
