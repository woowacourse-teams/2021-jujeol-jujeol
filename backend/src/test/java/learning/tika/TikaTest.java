package learning.tika;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@DisplayName("Tika 라이브러리에서")
public class TikaTest {

    private static final Tika tika = new Tika();

    @DisplayName("detect 메소드를 사용할 때")
    @Nested
    static class DetectTest {

        @DisplayName("파일의 타입을 확인 할 수 있다.")
        @ParameterizedTest
        @MethodSource("imageMultipartSources")
        void detectMimeTypeTest(String expectedType, MultipartFile multipartFile)
            throws IOException {
            //given
            //when
            String detect = tika.detect(multipartFile.getInputStream());
            //then

            assertThat(detect).isEqualTo(expectedType);
        }

        static Stream<Arguments> imageMultipartSources() throws IOException {
            final String pngFileName = "static/test.png";
            final String jpegFileName = "static/test.jpeg";

            return Stream.of(
                Arguments.of(MediaType.IMAGE_PNG_VALUE, createMultipartFile(pngFileName)),
                Arguments.of(MediaType.IMAGE_JPEG_VALUE, createMultipartFile(jpegFileName))
            );
        }

        @DisplayName("detect한 데이터를 확장자로 변환할 수 있다")
        @ParameterizedTest
        @CsvSource({"image/png,.png", "image/jpeg,.jpg"})
        void mimeTypesConvertTest(String mediaType, String expectedExtension)
            throws MimeTypeException {
            MimeTypes defaultMimeTypes = MimeTypes.getDefaultMimeTypes();
            MimeType mimeType = defaultMimeTypes.forName(mediaType);
            String extension = mimeType.getExtension();

            assertThat(extension).isEqualTo(expectedExtension);
        }

        private static MultipartFile createMultipartFile(String fileName) throws IOException {
            URL resource = DetectTest.class.getClassLoader().getResource(fileName);
            assert resource != null;
            Path filePath = new File(resource.getPath()).toPath();

            return new MockMultipartFile(
                "test",
                "test.fake",
                MediaType.TEXT_PLAIN_VALUE,
                Files.readAllBytes(filePath)
            );
        }
    }
}
