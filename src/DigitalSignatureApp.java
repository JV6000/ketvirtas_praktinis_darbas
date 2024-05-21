
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigitalSignatureApp {
//atlieka hachinima teksto SHA 256 algoritmu
    private static String hashText(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(text.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Digital Signature App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextField inputField1 = new JTextField(30);
        JTextField signatureField2 = new JTextField(30);
        JTextField inputField3 = new JTextField(30);
        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);

        JButton generateButton = new JButton("Generuoti Parašą");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputText = inputField1.getText();
                    String signature = hashText(inputText);
                    signatureField2.setText(signature);
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton verifyButton = new JButton("Patvirtinti Parašą");
        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String inputText = inputField3.getText();
                    String computedSignature = hashText(inputText);
                    String providedSignature = signatureField2.getText();
                    if (computedSignature.equals(providedSignature)) {
                        resultLabel.setText("Parašas patvirtintas!");
                        resultLabel.setForeground(Color.GREEN);
                    } else {
                        resultLabel.setText("Parašas nepatvirtintas!");
                        resultLabel.setForeground(Color.RED);
                    }
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(createLabeledComponent("Įvestas tekstas:", inputField1));
        panel.add(Box.createVerticalStrut(15));
        panel.add(generateButton);
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Parašas:", signatureField2));
        panel.add(Box.createVerticalStrut(15));
        panel.add(createLabeledComponent("Patvirtintas tekstas:", inputField3));
        panel.add(Box.createVerticalStrut(15));
        panel.add(verifyButton);
        panel.add(Box.createVerticalStrut(30));
        panel.add(resultLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

    private static JPanel createLabeledComponent(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 30));
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.add(label, BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }
}
