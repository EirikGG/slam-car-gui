import videoStream.StreamVideo;
import videoStream.ReadWebcam;

/**
 * @author: Eirik G. Gustafsson
 * @version: 25.09.2018.
 */
public class RunServer {
    public static void main(String[] args) {

        StreamVideo streamVideo = null;

        try {
            streamVideo = new StreamVideo();
        } catch (Exception exc) {
            System.out.println("Unknown host or socket exception.");
        }
        ReadWebcam readWebcam = new ReadWebcam();

        while (true) {
            try {
                streamVideo.doSendImage(readWebcam.doGetImage());
            } catch (Exception exc) {

            }
        }
    }
}
