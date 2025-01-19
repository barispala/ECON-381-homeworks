import java.util.*;

public class PasswordGenerator {

    // Keyboard layout: Map each key to its position (row, column)
    private static final Map<Character, int[]> keyboard = new HashMap<>();

    static {
        String[] rows = {
                "1234567890",
                "qwertyuiop",
                "asdfghjkl",
                "zxcvbnm"
        };

        // Populate the keyboard map with positions
        for (int row = 0; row < rows.length; row++) {
            for (int col = 0; col < rows[row].length(); col++) {
                keyboard.put(rows[row].charAt(col), new int[]{row, col});
            }
        }
    }

    // Calculate Manhattan distance between two keys
    private static int calculateDistance(char key1, char key2) {
        int[] pos1 = keyboard.get(key1);
        int[] pos2 = keyboard.get(key2);
        return Math.abs(pos1[0] - pos2[0]) + Math.abs(pos1[1] - pos2[1]);
    }

    // Generate a password based on the rules
    public static String generatePassword(char start) {
        StringBuilder password = new StringBuilder();
        password.append(start);

        Random random = new Random();
        char current = start;

        while (password.length() < 8) {
            List<Character> validCharacters = new ArrayList<>();

            // Find characters 2 or 3 moves away
            for (char key : keyboard.keySet()) {
                int distance = calculateDistance(current, key);
                if (distance == 2 || distance == 3) {
                    validCharacters.add(key);
                }
            }

            // Randomly select the next character
            current = validCharacters.get(random.nextInt(validCharacters.size()));
            password.append(current);
        }

        return password.toString();
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the starting character: ");
        char start = scanner.next().charAt(0);

        if (!keyboard.containsKey(start)) {
            System.out.println("Invalid character! Please enter a valid alphanumeric character.");
        } else {
            String password = generatePassword(start);
            System.out.println("Generated password: " + password);
        }

        scanner.close();
    }
}