package tomco.demos;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import tomco.HelloOpenCV;
import tomco.video.camera.facedetection.helpers.Colors;
import tomco.video.camera.facedetection.helpers.UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static java.util.Comparator.comparing;
import static tomco.video.camera.facedetection.helpers.MatHelpers.mat2BufferedImage;

/**
 * Created by tomco on 10/02/2018.
 */
public class TankDetection {
    UI ui = new UI();

    //setup orange HSV upper and lower levels.
    private static final Scalar LOWER = new Scalar(5, 100, 50);
    private static final Scalar UPPER = new Scalar(25, 255, 255);


    public void run() throws InterruptedException, IOException {
        while (true) {
            Mat frame;
            URL url = new URL("http://192.168.1.14/cam_pic.php");
            BufferedImage image = ImageIO.read(url);

            // Create a face detector from the cascade file in the resources
            // directory.
            frame = bufferedImageToMat(image);
            //Mat resized = new Mat(new Size(2000,2000),1);
            //Mat resized = matImg;
            //Mat resized = new Mat();
            //Imgproc.resize(frame, resized,new Size(1280,960));
            //Imgproc.pyrUp(frame,resized);

            Mat hsv = new Mat();
            Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);

            //Transformations
            Mat orangeMask = new Mat();
            Core.inRange(hsv, LOWER, UPPER, orangeMask);


            int erosionSize = 5;
            int dilationSize = 5;

            Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * erosionSize + 1, 2 * erosionSize + 1));
            Imgproc.erode(orangeMask, orangeMask, structuringElement); //Make it two iterations


            Mat dilationElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * dilationSize + 1, 2 * dilationSize + 1));
            Imgproc.erode(orangeMask, orangeMask, dilationElement);

            java.util.List<MatOfPoint> points = new ArrayList<>();
            Imgproc.findContours(orangeMask.clone(), points, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            points.stream()
                    .max((a, b) -> Double.valueOf(b.size().area())
                            .compareTo(Double.valueOf(a.size().area())))
                    .ifPresent(p -> {
                        Rect rect = Imgproc.boundingRect(p);
                        //System.out.println(rect);
                        drawRectangle(frame, rect, Colors.GREEN);

                        Point center = new Point((rect.x + rect.width / 2), (rect.y + rect.height / 2));
                        if (center.x < 400) {
                            System.out.println("To the right!");
                        } else if (center.x > 800) {
                            System.out.println("To the left!");
                        } else {

                        }
                        Imgproc.circle(frame, center, 50, Colors.RED, 5);
                    });

            Mat viewSize = new Mat();
            Imgproc.resize(frame, viewSize, new Size(640, 400));
            render(viewSize);
        }

    }


    private void drawRectangle(Mat frame, Rect rect, Scalar color) {
        Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), color, 5);
    }

    private void render(Mat frame) {
        BufferedImage bufferedImage = mat2BufferedImage(frame);
        ui.render(bufferedImage);
    }

    public static Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }
}
