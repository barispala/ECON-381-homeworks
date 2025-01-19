import java.util.ArrayList;
import java.util.List;

public class Main {
    // OkeyKey sınıfı (nested class olarak tanımlandı)
    static class OkeyKey {
        public enum Color { RED, BLACK, BLUE, YELLOW }

        private final int number;
        private final Color color;

        public OkeyKey(int number, Color color) {
            if (number < 1 || number > 13) {
                throw new IllegalArgumentException("Number must be between 1 and 13.");
            }
            if (color == null) {
                throw new IllegalArgumentException("Color cannot be null.");
            }
            this.number = number;
            this.color = color;
        }

        public int getNumber() {
            return number;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public String toString() {
            return "OkeyKey{" + "number=" + number + ", color=" + color + '}';
        }
    }

    // OkeyBoard sınıfı (nested class olarak tanımlandı)
    static class OkeyBoard {
        private final List<OkeyKey> board;

        public OkeyBoard() {
            this.board = new ArrayList<>();
        }

        public void addKey(OkeyKey key) {
            if (board.size() < 14) {
                board.add(key);
            } else {
                throw new IllegalStateException("Board already has 14 keys.");
            }
        }

        public void removeKey(OkeyKey key) {
            if (!board.remove(key)) {
                throw new IllegalArgumentException("Key not found on the board.");
            }
        }

        public void displayBoard() {
            System.out.println("Current board:");
            for (OkeyKey key : board) {
                System.out.println(key);
            }
        }

        public boolean isBoardFull() {
            return board.size() == 14;
        }
    }

    // main metodu
    public static void main(String[] args) {
        OkeyBoard okeyBoard = new OkeyBoard();

        OkeyKey key1 = new OkeyKey(1, OkeyKey.Color.RED);
        OkeyKey key2 = new OkeyKey(2, OkeyKey.Color.BLUE);
        OkeyKey key3 = new OkeyKey(3, OkeyKey.Color.RED);

        okeyBoard.addKey(key1);
        okeyBoard.addKey(key2);
        okeyBoard.addKey(key3);

        okeyBoard.displayBoard();

        okeyBoard.removeKey(key2);

        okeyBoard.displayBoard();

        System.out.println("Is board full? " + okeyBoard.isBoardFull());
    }
}
