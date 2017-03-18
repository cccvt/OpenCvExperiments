package tomco.video;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import tomco.HelloOpenCV;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by tomco on 20/01/2017.
 */
public class VideoToImages extends HelloOpenCV {

    public static void main(String[] args) {
        new VideoToImages().run();
    }

    public void run() {
        String videoPath = getClass().getResource("/video.3gp").getPath().substring(1);
        VideoCapture camera = new VideoCapture(videoPath);

        Mat frame = new Mat();
        JFrame jframe = new JFrame("Title");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setVisible(true);

        int i = 0;
        while (true) {
            if (camera.read(frame)) {
                Imgcodecs.imwrite("C:\\repos\\opencv\\dumpfolder\\" + i++ + ".jpg", frame);
                ImageIcon image = new ImageIcon(Mat2BufferedImage(frame));
                vidpanel.setIcon(image);
                vidpanel.repaint();
            }
        }

    }

    public BufferedImage Mat2BufferedImage(Mat m){
        //source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
        //Fastest code
        //The output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}
