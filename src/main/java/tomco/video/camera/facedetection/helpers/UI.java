package tomco.video.camera.facedetection.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;

/**
 * Created by tomco on 22/01/2017.
 */
public class UI {

    private JFrame jFrame;
    private JLabel videoPanel;

    public UI() {
        jFrame = new JFrame("Do something");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        videoPanel = new JLabel();

        JCheckBox checkBox = new JCheckBox("Backend calls?");
        checkBox.setSelected(Config.BACKEND_ENABLED);
        checkBox.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                Config.BACKEND_ENABLED = false;
            } else {
                Config.BACKEND_ENABLED = true;
            }
        });
        Container container = jFrame.getContentPane();

        container.add(videoPanel, BorderLayout.CENTER);
        container.add(checkBox, BorderLayout.EAST);

        jFrame.setVisible(true);
        jFrame.setSize(800, 500);
    }

    public void render(BufferedImage bufferedImage) {
        ImageIcon image = new ImageIcon(bufferedImage);
        videoPanel.setIcon(image);
        videoPanel.repaint();
    }
}
