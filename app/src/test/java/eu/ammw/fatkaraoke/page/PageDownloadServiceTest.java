package eu.ammw.fatkaraoke.page;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class PageDownloadServiceTest {

    private static final String TEST_URL = "http://localhost:666";

    @Mock
    private HttpURLConnection connection;

    private PageDownloadService service;

    @BeforeEach
    void setUp() throws IOException {
        openMocks(this);
        service = new PageDownloadService(url -> connection);
        when(connection.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));
    }

    @Test
    void shouldDownloadPage() throws IOException {
        // GIVEN
        when(connection.getResponseCode()).thenReturn(200);

        // WHEN - THEN
        assertThat(service.download(TEST_URL)).isEqualTo("test");
    }

    @Test
    void shouldSetConnectionParameters() throws IOException {
        // GIVEN
        when(connection.getResponseCode()).thenReturn(200);

        // WHEN
        service.download(TEST_URL);

        // THEN
        verify(connection).setReadTimeout(300_000);
        verify(connection).setConnectTimeout(15_000);
        verify(connection).setRequestMethod("GET");
        verify(connection).setDoInput(true);
    }

    @Test
    void shouldThrowOnConnectionError() throws IOException {
        when(connection.getResponseCode()).thenReturn(500);

        assertThrows(IOException.class, () ->
                service.download(TEST_URL)
        );
    }
}
