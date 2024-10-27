import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game_adona extends JFrame {
    public Game_adona() {
        setTitle("Game Menu");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        JButton ticTacToeButton = new JButton("Tic Tac Toe");
        JButton sudokuButton = new JButton("Sudoku");
        JButton hangmanButton = new JButton("Hangman");
        JButton exitButton = new JButton("Exit");

        ticTacToeButton.setFont(new Font("Arial", Font.BOLD, 16));
        ticTacToeButton.setBackground(Color.GREEN);
        ticTacToeButton.setForeground(Color.WHITE);

        sudokuButton.setFont(new Font("Arial", Font.BOLD, 16));
        sudokuButton.setBackground(Color.BLUE);
        sudokuButton.setForeground(Color.WHITE);

        hangmanButton.setFont(new Font("Arial", Font.BOLD, 16));
        hangmanButton.setBackground(Color.ORANGE);
        hangmanButton.setForeground(Color.WHITE);

        exitButton.setFont(new Font("Arial", Font.PLAIN, 12));
        exitButton.setForeground(Color.RED);

        panel.add(ticTacToeButton);
        panel.add(sudokuButton);
        panel.add(hangmanButton);
        panel.add(exitButton);

        add(panel);

        ticTacToeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TicTacToeGUI_1 ticTacToeGUI = new TicTacToeGUI_1();
                ticTacToeGUI.setVisible(true);
            }
        });

        sudokuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SudokuGUI_1 sudokuGUI = new SudokuGUI_1();
                sudokuGUI.setVisible(true);
            }
        });

        hangmanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HangmanGame hangmanGame = new HangmanGame();
                hangmanGame.setVisible(true);
            }
        });


        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game_adona gameMenuGUI = new Game_adona();
                gameMenuGUI.setVisible(true);
            }
        });
    }
}

class TicTacToeGUI_1 extends JFrame {
    private char[][] board = new char[3][3];
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';

    public TicTacToeGUI_1() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 3));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton("-");
                button.setFont(new Font("Arial", Font.BOLD, 40));
                buttons[i][j] = button;
                button.addActionListener(new ButtonListener(i, j));
                panel.add(button);
            }
        }

        add(panel);
    }

    private class ButtonListener implements ActionListener {
        private int row, col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[row][col] == '\u0000') {
                board[row][col] = currentPlayer;
                buttons[row][col].setText(Character.toString(currentPlayer));
                buttons[row][col].setEnabled(false);

                if (checkWin(row, col)) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetGame();
                } else if (checkDraw()) {
                    JOptionPane.showMessageDialog(null, "It's a draw!");
                    resetGame();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid move. Please try again.");
            }
        }

        private boolean checkWin(int row, int col) {
            return (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != '\u0000') || // Check row
                    (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != '\u0000') || // Check column
                    (row == col && board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '\u0000') || // Check diagonal
                    (row + col == 2 && board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '\u0000'); // Check anti-diagonal
        }

        private boolean checkDraw() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '\u0000') {
                        return false;
                    }
                }
            }
            return true;
        }

        private void resetGame() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = '\u0000';
                    buttons[i][j].setText("-");
                    buttons[i][j].setEnabled(true);
                }
            }
            currentPlayer = 'X';
        }
    }
}



class SudokuGUI_1 extends JFrame {
    private int[][] sudokuBoard = new int[9][9];
    private JTextField[][] textFields = new JTextField[9][9];

    public SudokuGUI_1() {
        setTitle("Sudoku");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(9, 9));

        generateSudokuBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField textField = new JTextField();
                textField.setHorizontalAlignment(JTextField.CENTER);
                textField.setFont(new Font("Arial", Font.BOLD, 20));
                if (sudokuBoard[i][j] != 0) {
                    textField.setText(Integer.toString(sudokuBoard[i][j]));
                    textField.setEditable(false);
                    textField.setBackground(Color.LIGHT_GRAY);
                }
                textFields[i][j] = textField;
                panel.add(textField);
            }
        }

        add(panel);

        JButton checkButton = new JButton("Check");
        checkButton.setFont(new Font("Arial", Font.BOLD, 16));
        checkButton.setForeground(Color.WHITE);
        checkButton.setBackground(Color.BLUE);
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isSudokuSolved()) {
                    JOptionPane.showMessageDialog(null, "Congratulations! Sudoku solved!");
                } else {
                    JOptionPane.showMessageDialog(null, "Sudoku is not solved yet.");
                }
            }
        });
        add(checkButton, BorderLayout.SOUTH);
    }

    private void generateSudokuBoard() {
        Random random = new Random();

        // Fill some cells randomly
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (random.nextInt(5) == 0) { // Approximately 20% of cells will be filled
                    sudokuBoard[i][j] = random.nextInt(9) + 1;
                } else {
                    sudokuBoard[i][j] = 0;
                }
            }
        }
    }

    private boolean isSudokuSolved() {
        // Check if each row, column, and 3x3 grid contains unique numbers
        for (int i = 0; i < 9; i++) {
            if (!isValidRow(i) || !isValidColumn(i) || !isValidGrid(i)) {
                return false;
            }
        }

        return true;
    }

    private boolean isValidRow(int row) {
        boolean[] seen = new boolean[10];
        for (int j = 0; j < 9; j++) {
            int num = sudokuBoard[row][j];
            if (num != 0) {
                if (seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }
        return true;
    }

    private boolean isValidColumn(int col) {
        boolean[] seen = new boolean[10];
        for (int i = 0; i < 9; i++) {
            int num = sudokuBoard[i][col];
            if (num != 0) {
                if (seen[num]) {
                    return false;
                }
                seen[num] = true;
            }
        }
        return true;
    }

    private boolean isValidGrid(int grid) {
        boolean[] seen = new boolean[10];
        int startRow = (grid / 3) * 3;
        int startCol = (grid % 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                int num = sudokuBoard[i][j];
                if (num != 0) {
                    if (seen[num]) {
                        return false;
                    }
                    seen[num] = true;
                }
            }
        }
        return true;
    }
}



class HangmanGame extends JFrame{
    private static final String[] WORDS = {"hello", "world", "java", "hangman", "programming"};
    private static final int MAX_TRIES = 6;
    private JLabel wordLabel;
    private JLabel triesLabel;
    private JTextField inputField;
    private JButton guessButton;
    private JLabel hangmanLabel; // Label to display Hangman diagram
    private ImageIcon[] hangmanImages; // Array to hold Hangman images
    private String wordToGuess;
    private char[] guessedWord;
    private int tries;
    public HangmanGame() {
        setTitle("Hangman Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500); // Increased width to accommodate Hangman diagram
        setLayout(new BorderLayout()); // Changed layout manager to BorderLayout

        wordToGuess = getRandomWord();
        guessedWord = new char[wordToGuess.length()];
        tries = 0;

        for (int i = 0; i < wordToGuess.length(); i++) {
            guessedWord[i] = '-';
        }

        JPanel topPanel = new JPanel(new GridLayout(3, 1)); // Panel for top components
        wordLabel = new JLabel("Current word: " + String.valueOf(guessedWord));
        topPanel.add(wordLabel);

        triesLabel = new JLabel("Tries left: " + (MAX_TRIES - tries));
        topPanel.add(triesLabel);

        inputField = new JTextField();
        topPanel.add(inputField);

        add(topPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guessLetter();
            }
        });
        buttonPanel.add(guessButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Initialize Hangman images
        hangmanImages = new ImageIcon[MAX_TRIES + 1];
        for (int i = 0; i <= MAX_TRIES; i++) {
            hangmanImages[i] = new ImageIcon("hangman_" + i + ".jpg"); // Replace with your image file names
        }
        hangmanLabel = new JLabel();
        add(hangmanLabel, BorderLayout.SOUTH);
        updateHangmanImage(); // Initially, display the Hangman diagram with no parts

        setVisible(true);
    }
    private void guessLetter() {
        char guess = inputField.getText().charAt(0);
        boolean found = false;

        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess) {
                guessedWord[i] = guess;
                found = true;
            }
        }

        if (!found) {
            tries++;
            updateHangmanImage(); // Update Hangman image for incorrect guess
            triesLabel.setText("Tries left: " + (MAX_TRIES - tries));
            if (tries >= MAX_TRIES) {
                JOptionPane.showMessageDialog(this, "Sorry, you've run out of tries. The word was: " + wordToGuess);
                resetGame();
                return;
            }
        }

        wordLabel.setText("Current word: " + String.valueOf(guessedWord));

        if (isWordGuessed(guessedWord)) {
            JOptionPane.showMessageDialog(this, "Congratulations! You've guessed the word: " + wordToGuess);
            resetGame();
            return; // Return after resetting the game
        }

        inputField.setText("");
    }


    private void resetGame() {
        wordToGuess = getRandomWord();
        guessedWord = new char[wordToGuess.length()];
        tries = 0;

        for (int i = 0; i < wordToGuess.length(); i++) {
            guessedWord[i] = '-';
        }

        wordLabel.setText("Current word: " + String.valueOf(guessedWord));
        triesLabel.setText("Tries left: " + (MAX_TRIES - tries));
        updateHangmanImage(); // Reset Hangman image
    }

    private static String getRandomWord() {
        return WORDS[(int) (Math.random() * WORDS.length)];
    }

    private static boolean isWordGuessed(char[] guessedWord) {
        for (char c : guessedWord) {
            if (c == '-') {
                return false;
            }
        }
        return true;
    }

    private void updateHangmanImage() {
        if (tries < hangmanImages.length) {
            System.out.println("Loading image: hangman_" + tries + ".jpg");
            hangmanLabel.setIcon(hangmanImages[tries]);        }
    }
}
