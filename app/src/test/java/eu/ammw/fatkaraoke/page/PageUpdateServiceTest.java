package eu.ammw.fatkaraoke.page;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;

import eu.ammw.fatkaraoke.data.SongRepository;
import eu.ammw.fatkaraoke.model.Song;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PageUpdateServiceTest {

    private static final Song TEST_SONG_1 = new Song("Test Title", "Test Artist");
    private static final Song TEST_SONG_2 = new Song("Other Title", "Other Artist");

    @Mock
    private PageDownloadService downloadService;
    @Mock
    private SongRepository repository;
    @Mock
    private PageParser parser;

    private PageUpdateService service;

    @BeforeEach
    void setUp() throws IOException {
        openMocks(this);
        service = new PageUpdateService(downloadService, parser, repository);
        when(downloadService.download(anyString())).thenReturn("test");
        when(parser.parse("test")).thenReturn(Arrays.asList(TEST_SONG_1, TEST_SONG_2));
    }

    @Test
    void shouldUpdateSongsInDatabase() throws IOException {
        // WHEN
        service.performUpdate();

        // THEN
        verify(repository).updateSongs(TEST_SONG_1, TEST_SONG_2);
    }

    @Test
    void shouldUseProvidedUrl() throws IOException {
        // GIVEN
        service.setUrl("test.url.com");

        // WHEN
        service.performUpdate();

        // THEN
        verify(downloadService).download("test.url.com");
    }

    @Test
    void shouldThrowWhenDownloadThrows() throws IOException {
        when(downloadService.download(anyString())).thenThrow(IOException.class);
        assertThrows(IOException.class, service::performUpdate);
    }
}
