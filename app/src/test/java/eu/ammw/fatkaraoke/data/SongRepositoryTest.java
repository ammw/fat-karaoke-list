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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class SongRepositoryTest {

    private static final List<Song> SONG_LIST = Arrays.asList(
            new Song(1L, "What's Up", "4 Non Blondes"),
            new Song(2L, "Let It Go", "Idina Menzel"),
            new Song(3L, "Wonderwall", "Oasis")
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
        when(songDao.find("%testTitle%", "%testArtist%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("testTitle", "testArtist", callback);
    }

    @Test
    public void shouldSearchSongsTrimInputs() {
        // GIVEN
        when(songDao.find("%testTitle%", "%testArtist%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs(" testTitle\t", "\ntestArtist  ", callback);
    }

    @Test
    public void shouldSearchSongsWhenTitleNull() {
        // GIVEN
        when(songDao.find("%", "%testArtist%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs(null, "testArtist", callback);
    }

    @Test
    public void shouldSearchSongsWhenTitleEmpty() {
        // GIVEN
        when(songDao.find("%", "%testArtist%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("", "testArtist", callback);
    }

    @Test
    public void shouldSearchSongsWhenTitleWhitespaceOnly() {
        // GIVEN
        when(songDao.find("%", "%testArtist%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("\t \n", "testArtist", callback);
    }

    @Test
    public void shouldSearchSongsWhenArtistNull() {
        // GIVEN
        when(songDao.find("%testTitle%", "%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("testTitle", null, callback);
    }

    @Test
    public void shouldSearchSongsWhenArtistEmpty() {
        // GIVEN
        when(songDao.find("%testTitle%", "%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("testTitle", "", callback);
    }

    @Test
    public void shouldSearchSongsWhenArtistWhitespaceOnly() {
        // GIVEN
        when(songDao.find("%testTitle%", "%")).thenReturn(SONG_LIST);
        doAnswer(invocation -> {
            // THEN
            List<Song> songs = invocation.getArgument(0);
            assertThat(songs).isEqualTo(SONG_LIST);
            return null;
        }).when(callback).onComplete(any());

        // WHEN
        songRepository.searchSongs("testTitle", " \t\n", callback);
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
        songRepository.searchSongs(null, null, callback);
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
        songRepository.searchSongs("", "", callback);
    }

    @Test
    public void shouldUpdateSongs() {
        // GIVEN
        Song[] songs = SONG_LIST.toArray(new Song[0]);

        // WHEN
        songRepository.updateSongs(songs);

        // THEN
        verify(songDatabase).runInTransaction(any(Runnable.class));
    }
}
