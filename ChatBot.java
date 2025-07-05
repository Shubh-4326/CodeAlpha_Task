import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class ChatBot {

    private static final Map<String, String> knowledgeBase = new HashMap<>();

    public static void main(String[] args) {
        initializeKnowledgeBase();
        createGUI();
    }

    // NLP + Rule-based FAQ system
    private static void initializeKnowledgeBase() {
        knowledgeBase.put("hello", "Hello! How can I help you?");
        knowledgeBase.put("hi", "Hi there! What can I do for you?");
        knowledgeBase.put("how are you", "I'm just a bot, but I'm functioning perfectly!");
        knowledgeBase.put("your name", "I'm ChatBot, your virtual assistant.");
        knowledgeBase.put("bye", "Goodbye! Take care.");
        knowledgeBase.put("help", "I can answer simple questions about Java or programming.");
        knowledgeBase.put("what is java", "Java is a high-level, class-based, object-oriented programming language.");
        knowledgeBase.put("what is oops", "OOP stands for Object-Oriented Programming, which uses concepts like classes and objects.");
        knowledgeBase.put("features of java", "Some features of Java: Platform Independent, Object-Oriented, Secure, Robust, and Multithreaded.");
    }

    // GUI for chatbot interface
    private static void createGUI() {
        JFrame frame = new JFrame("Java ChatBot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);

        // Action listeners
        ActionListener sendAction = e -> {
            String userText = inputField.getText().trim();
            if (!userText.isEmpty()) {
                chatArea.append("You: " + userText + "\n");
                String botResponse = getBotResponse(userText);
                chatArea.append("Bot: " + botResponse + "\n\n");
                inputField.setText("");
            }
        };

        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);

        frame.setVisible(true);
    }

    // NLP-like response using keyword matching
    private static String getBotResponse(String input) {
        input = input.toLowerCase();
        for (String key : knowledgeBase.keySet()) {
            if (input.contains(key)) {
                return knowledgeBase.get(key);
            }
        }
        return "I'm not sure how to respond to that. Try asking something else.";
    }
}

