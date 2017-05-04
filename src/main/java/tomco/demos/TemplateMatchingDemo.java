package tomco.demos;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.minMaxLoc;

/**
 * Created by tomco on 20/01/2017.
 */
public class TemplateMatchingDemo {

    public static void main(String[] args) {
        new TemplateMatchingDemo().run();
    }
    public void run() {
        System.out.println("\nRunning TemplateMatching");

        Mat template = Imgcodecs.imread(getClass().getResource("/tiny-logo.png").getPath().substring(1));
        Mat image = Imgcodecs.imread(getClass().getResource("/compare.jpg").getPath().substring(1));

        double maxValue = 0.0;
        Mat bestMatchingSize = null;
        for (int i = 1; i < 90; i++) {
            Mat resizer = new Mat();
            Imgproc.resize(image, resizer, new Size(image.width() - (image.width() * i / 100), image.height() - (image.height() * i / 100)));
            Mat resizeResult = new Mat();
            Imgproc.matchTemplate(resizer, template, resizeResult, 4);
            Core.MinMaxLocResult locResult = minMaxLoc(resizeResult);
            System.out.println("Resizer: " + resizer.width() + "x" + resizer.height() + ":" + locResult.maxLoc.x + ":" + locResult.maxLoc.y);

            Imgproc.rectangle(resizer, locResult.maxLoc, new Point(locResult.maxLoc.x + 80, locResult.maxLoc.y + 80), new Scalar(0, 255, 0));

            String filename = "resize" + System.currentTimeMillis() + "templateMatch.png";
            //System.out.println(String.format("Writing %s", filename));
            // Imgcodecs.imwrite(filename, resizer);

            if (bestMatchingSize == null || locResult.maxVal > maxValue) {
                bestMatchingSize = resizer;
                maxValue = locResult.maxVal;
            }
        }

        System.out.println("Best Matching size: " + bestMatchingSize.width() + "x" + bestMatchingSize.height());
        Mat result = new Mat();
        Imgproc.matchTemplate(image, template, result, 1);

        Core.MinMaxLocResult locResult = minMaxLoc(result);
        System.out.println(locResult.maxVal);
        double ratio = (image.width() * image.height()) / (bestMatchingSize.width() * bestMatchingSize.height());
        Point maxLoc = locResult.maxLoc;
        Point minLoc = locResult.minLoc;
        Imgproc.rectangle(image, new Point(maxLoc.x, maxLoc.y), new Point(maxLoc.x + 80, maxLoc.y + 80), new Scalar(0, 255, 0));
        Imgproc.rectangle(image, new Point(minLoc.x, minLoc.y), new Point(minLoc.x + 80, minLoc.y + 80), new Scalar(255, 255, 255));
        // Save the visualized detection.
        String filename = System.currentTimeMillis() + "templateMatch.png";
        System.out.println(String.format("Writing %s", filename));
        Imgcodecs.imwrite(filename, image);
    }
}
