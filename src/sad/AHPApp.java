package sad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class AHPApp extends JFrame {
    private JComboBox<String> alternativesComboBox;
    private JButton compareButton;
    private JButton reprocessButton;
    private JButton displayButton;
    private JTable resultTable;
    private JTextField priceField;
    private JComboBox<String> lookComboBox;
    private JComboBox<String> cameraComboBox;

    private int currentAlternativeIndex = 0;
    private int numberOfAlternatives = 4;
    private int numberOfCriteria = 3;

    private String[] alternatives = { "Samsung", "iPhone XR", "Realme", "Redmi A9" };
    private double[][][] comparisonMatrices = new double[numberOfAlternatives][numberOfCriteria][numberOfCriteria];

    public AHPApp() {
        // Welcome Window
        showWelcomeScreen();
    }

    private void showWelcomeScreen() {
        JFrame welcomeFrame = new JFrame("Welcome");
        welcomeFrame.setSize(500, 400);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLayout(new BorderLayout());

        JPanel welcomePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), getHeight(), new Color(0, 150, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JLabel welcomeLabel = new JLabel("WELCOME TO OUR APP ! <3");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        JLabel creditsLabel = new JLabel("Realized by Kourtiche Kawther & Benyakoub Mohammed Ryad");
        creditsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        creditsLabel.setHorizontalAlignment(JLabel.CENTER);
        creditsLabel.setForeground(Color.WHITE);
        welcomePanel.add(creditsLabel, BorderLayout.SOUTH);

        JButton continueButton = new JButton("Continue");
        continueButton.setBackground(new Color(200, 200, 255));
        continueButton.setFont(new Font("Arial", Font.BOLD, 16));
        continueButton.addActionListener(e -> {
            welcomeFrame.setVisible(false);
            showInstructionPage();
        });

        welcomeFrame.add(welcomePanel, BorderLayout.CENTER);
        welcomeFrame.add(continueButton, BorderLayout.SOUTH);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setVisible(true);
    }

    private void showInstructionPage() {
        JFrame instructionFrame = new JFrame("How It Works");
        instructionFrame.setSize(600, 400);
        instructionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionFrame.setLayout(new BorderLayout());

        JPanel instructionPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0, 102, 204), getWidth(), getHeight(), new Color(0, 150, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        JTextArea instructionText = new JTextArea();
        instructionText.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionText.setForeground(Color.BLACK); // Black text
        instructionText.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent white background
        instructionText.setEditable(false);
        instructionText.setLineWrap(true);
        instructionText.setWrapStyleWord(true);
        instructionText.setText(
                "How to Use the AHP Decision Maker:\n\n" +
                        "1. Select an alternative from the dropdown menu.\n" +
                        "2. Enter the price for the selected alternative.\n" +
                        "3. Rate the look and camera quality using the dropdown menus.\n" +
                        "4. Click 'Compare' to add the alternative to the comparison.\n" +
                        "5. Repeat the process for all alternatives.\n" +
                        "6. Once all alternatives are added, the app will determine the best option.\n" +
                        "7. Use 'Reprocess' to reset the comparison or 'Export to PDF' to save the results.\n\n" +
                        "Thank you for using our app!"
        );

        JButton startButton = new JButton("Start");
        startButton.setBackground(new Color(200, 200, 255));
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.addActionListener(e -> {
            instructionFrame.setVisible(false);
            setupMainWindow();
            setVisible(true);
        });

        instructionPanel.add(new JScrollPane(instructionText), BorderLayout.CENTER);
        instructionPanel.add(startButton, BorderLayout.SOUTH);

        instructionFrame.add(instructionPanel, BorderLayout.CENTER);
        instructionFrame.setLocationRelativeTo(null);
        instructionFrame.setVisible(true);
    }

    private void setupMainWindow() {
        setTitle("AHP App");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        alternativesComboBox = new JComboBox<>(alternatives);
        compareButton = new JButton("Compare");
        reprocessButton = new JButton("Reprocess");
        displayButton = new JButton("Export to PDF");

        priceField = new JTextField();
        lookComboBox = new JComboBox<>(new String[]{"Good", "Average", "Bad"});
        cameraComboBox = new JComboBox<>(new String[]{"Perfect", "Good", "Blur"});

        resultTable = new JTable(new DefaultTableModel(new Object[]{"Alternative", "Price", "Look", "Camera", "Score"}, 0));
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.add(new JLabel("Alternative:"));
        inputPanel.add(alternativesComboBox);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Look:"));
        inputPanel.add(lookComboBox);
        inputPanel.add(new JLabel("Camera:"));
        inputPanel.add(cameraComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        compareButton.setBackground(new Color(173, 216, 230));
        reprocessButton.setBackground(new Color(255, 182, 193));
        displayButton.setBackground(new Color(144, 238, 144));
        buttonPanel.add(compareButton);
        buttonPanel.add(reprocessButton);
        buttonPanel.add(displayButton);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        compareButton.addActionListener(e -> performAHP());
        reprocessButton.addActionListener(e -> resetAHP());
        displayButton.addActionListener(e -> displayResultsInPDF());
    }

    private void performAHP() {
        if (currentAlternativeIndex < numberOfAlternatives) {
            String selectedAlternative = (String) alternativesComboBox.getSelectedItem();

            try {
                double priceRating = Double.parseDouble(priceField.getText().replaceAll("[^\\d.]", ""));
                fillComparisonMatrix(comparisonMatrices[currentAlternativeIndex][0], priceRating);

                int lookRating = lookComboBox.getSelectedIndex() + 1;
                fillComparisonMatrix(comparisonMatrices[currentAlternativeIndex][1], lookRating);

                int cameraRating = cameraComboBox.getSelectedIndex() + 1;
                fillComparisonMatrix(comparisonMatrices[currentAlternativeIndex][2], cameraRating);

                DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
                model.addRow(new Object[]{selectedAlternative, priceRating, lookComboBox.getSelectedItem(), cameraComboBox.getSelectedItem(), calculateTotalScore(currentAlternativeIndex)});

                currentAlternativeIndex++;

                if (currentAlternativeIndex < numberOfAlternatives) {
                    alternativesComboBox.setSelectedIndex(currentAlternativeIndex);
                    compareButton.setText(currentAlternativeIndex < numberOfAlternatives - 1 ? "Next Alternative" : "Calculate");
                } else {
                    determineBestAlternative();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numerical values for prices.", "Input Error", JOptionPane.ERROR_MESSAGE);
                priceField.setBackground(Color.PINK);
            }
        }
    }

    private void determineBestAlternative() {
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        int bestAlternativeIndex = 0;
        double highestScore = (double) model.getValueAt(0, 4);

        for (int i = 1; i < model.getRowCount(); i++) {
            double score = (double) model.getValueAt(i, 4);
            if (score > highestScore) {
                highestScore = score;
                bestAlternativeIndex = i;
            }
        }

        JOptionPane.showMessageDialog(this, "Best Alternative: " + model.getValueAt(bestAlternativeIndex, 0), "AHP Results", JOptionPane.INFORMATION_MESSAGE);
    }

    private double calculateTotalScore(int alternativeIndex) {
        double[][] normalizedMatrices = normalizeMatrices(comparisonMatrices[alternativeIndex]);
        double[] weights = calculateWeights(normalizedMatrices);
        double totalScore = 0.0;

        for (double weight : weights) {
            totalScore += weight;
        }

        return totalScore;
    }

    private double[][] normalizeMatrices(double[][] matrix) {
        int n = matrix.length;
        double[] columnSum = new double[n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                columnSum[j] += matrix[i][j];
            }
        }

        double[][] normalizedMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                normalizedMatrix[i][j] = matrix[i][j] / columnSum[j];
            }
        }

        return normalizedMatrix;
    }

    private double[] calculateWeights(double[][] matrix) {
        int n = matrix.length;
        double[] rowProduct = new double[n];
        for (int i = 0; i < n; i++) {
            rowProduct[i] = 1.0;
            for (int j = 0; j < n; j++) {
                rowProduct[i] *= matrix[i][j];
            }
        }

        double productNthRoot = Math.pow(rowProduct[0], 1.0 / n);

        double[] weights = new double[n];
        for (int i = 0; i < n; i++) {
            weights[i] = rowProduct[i] / productNthRoot;
        }

        return weights;
    }

    private void fillComparisonMatrix(double[] matrix, double rating) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = rating;
        }
    }

    private void resetAHP() {
        currentAlternativeIndex = 0;
        priceField.setText("");
        alternativesComboBox.setSelectedIndex(0);
        lookComboBox.setSelectedIndex(0);
        cameraComboBox.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
        model.setRowCount(0);
        compareButton.setText("Compare");
        for (int i = 0; i < numberOfAlternatives; i++) {
            for (int j = 0; j < numberOfCriteria; j++) {
                for (int k = 0; k < numberOfCriteria; k++) {
                    comparisonMatrices[i][j][k] = 0.0;
                }
            }
        }
    }

    private void displayResultsInPDF() {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("AHP Results");
            contentStream.newLineAtOffset(0, -20);

            DefaultTableModel model = (DefaultTableModel) resultTable.getModel();
            for (int i = 0; i < model.getRowCount(); i++) {
                String result = model.getValueAt(i, 0) + ": " + model.getValueAt(i, 4);
                contentStream.showText(result);
                contentStream.newLineAtOffset(0, -15);
            }

            contentStream.endText();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("AHP_Results.pdf"));
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                document.save(fileToSave);
                JOptionPane.showMessageDialog(this, "PDF saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AHPApp::new);
    }
}