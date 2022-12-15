package usage;


import cooper.ClientRequest;
import main.Runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;

public class ImageSender {

    private static final ImageSender INSTANCE = new ImageSender();

    public static ImageSender getInstance() {

        return INSTANCE;
    }

    private ImageSender() {
    }

    public void sendImage(File image, String movieName) throws IOException {
        String imageString = getImageUrl(image);
        // Map.of("image", imageString, "name", name)));
        Runner.sendData(new ClientRequest("sendImage",
                new HashMap<String, Object>() {
                    {
                        put("image", imageString);
                        put("movieName", movieName);
                    }
                })
        );
    }

    public String getImageUrl(File image) throws IOException {
        InputStream imageStream = new FileInputStream(image);
        byte[] imageData = new byte[(int) image.length()];
        imageStream.read(imageData);
        String imageString = Base64.getEncoder().encodeToString(imageData);
        imageStream.close();
        return imageString;
    }
}