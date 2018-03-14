package tomco.video.camera.facedetection.helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * Created by tomco on 22/01/2017.
 */
public class UI {

    private JFrame jFrame;
    private JTextField jTextField;
    private JLabel videoPanel;
    private JButton jButton;

    public UI() {
        jFrame = new JFrame("Face Adder");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        videoPanel = new JLabel();
        jButton = new JButton("SNAP PICTURE");

        jTextField = new JTextField();

        Container container = jFrame.getContentPane();

        container.add(videoPanel, BorderLayout.CENTER);
        container.add(jTextField, BorderLayout.NORTH);
        container.add(jButton, BorderLayout.SOUTH);

        jFrame.setVisible(true);
        jFrame.setSize(800, 500);
    }

    public void render(BufferedImage bufferedImage) {
        ImageIcon image = new ImageIcon(bufferedImage);
        videoPanel.setIcon(image);
        videoPanel.repaint();
    }

    public void setButtonListener(ActionListener actionListener) {
        this.jButton.addActionListener(actionListener);
    }

    public String getNameValue() {
        return jTextField.getText();
    }
}
