package bullscows;

public class Game {
    private int turn = 1;

    protected int start() {
        int length;
        int symbols;

        do {
            length = UserInteraction.sendNumOfLength();
        } while (length == 0);

        do {
            symbols = UserInteraction.sendNumOfSymbols();
        } while (symbols == 0);


        char[] secretCode = UserInteraction.generateNewCode(length, symbols);
        UserInteraction.preparedMessage(length, symbols);
        System.out.println("Okay, let's start a game!");

        int result = 0;
        do {
            result = guess(secretCode);
        }
        while (result != 1);
        return 1;
    }

    protected int guess(char[] secretCode) {
        System.out.printf("Turn %d:\n", this.turn);
        char[] playerCode = null;
        do {
            playerCode = UserInteraction.sendCode(secretCode.length);
        } while (playerCode == null);
        char[] tmpCode = secretCode.clone();
        int bulls = UserInteraction.checkBulls(tmpCode, playerCode);
        int cows = UserInteraction.checkCows(tmpCode, playerCode);
        if (UserInteraction.sendResult(playerCode.length, cows, bulls) == 1) {
            System.out.println("Congratulations! You guessed the secret code.");
            return 1;
        }
        this.turn++;
        return 0;
    }
}
