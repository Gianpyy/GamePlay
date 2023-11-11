package control;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageUtilities {

    public static String convertToBase64(InputStream coverImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;

        try (coverImage) {
            coverImage.reset(); // Assicura che l'InputStream sia posizionato all'inizio
            while ((bytesRead = coverImage.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(imageBytes);
        }
    }
}
