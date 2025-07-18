import java.awt.*;
import java.util.*;
import javax.swing.*;

public class ChatBot extends JFrame {
    JTextArea chatArea;
    JTextField inputField;
    JButton sendButton;
    Map<String, String> faqResponses;

    /**
     * 
     */
    public ChatBot() {
        setTitle("AI Chatbot ");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(chatArea);
        add(scroll, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Pre-defined FAQ responses (training simulation)
        faqResponses = new HashMap<>();
        faqResponses.put("hello", "Hi there! How can I assist you?");
        faqResponses.put("what is your name", "I'm a Java Chatbot created by Kriti!");
        faqResponses.put("how are you", "I'm doing great! Thanks for asking.");
        faqResponses.put("what is java", "Java is a high-level, object-oriented programming language.");
        faqResponses.put("thanks", "You're welcome! ");

        sendButton.addActionListener(e -> handleUserInput());
        inputField.addActionListener(e -> handleUserInput());

        setVisible(true);
    }

    private void handleUserInput() {
        String input = inputField.getText().trim().toLowerCase();
        chatArea.append("You: " + input + "\n");
        String response = generateResponse(input);
        chatArea.append("Bot: " + response + "\n\n");
        inputField.setText("");
    }

    private String generateResponse(String input) {
        for (String keyword : faqResponses.keySet()) {
            if (input.contains(keyword)) {
                return faqResponses.get(keyword);
            }
        }
        return "Sorry, I did not understand that. Try asking something else.";
    }

    public static void main(String[] args) {
        new ChatBot();
    }
}