package machine_coding.tic_tac_toe;

import machine_coding.tic_tac_toe.controllers.GameController;
import machine_coding.tic_tac_toe.models.*;

import java.util.*;

import static machine_coding.tic_tac_toe.utils.Delay.delay;

public class TicTacToe {
    private static final Scanner input = new Scanner(System.in);
    private static List<Player> players = new ArrayList<>();
    private static Set<Character> symbolSet = new HashSet<>();
    private static GameController gameController = new GameController();
    private static Game game;
    private static boolean errorOccurred = false;

    public static void main(String[] args) {
        System.out.println("\n\033[1;33mHello,\033[0m \033[1;34mWorld!\033[0m\n");
        delay(500);
        System.out.println("\033[3mPress \033[32mEnter\033[0m \033[3mto begin setup.\033[0m");
        input.nextLine();
        setupGame();

        if (!errorOccurred) {
            askForGameRecap();
            System.out.println("Do you want to play again?");
            char reply = input.next().charAt(0);
            while (reply == 'y' || reply == 'Y') {
                setupGame();
                if (!errorOccurred) {
                    askForGameRecap();
                    System.out.println("Do you want to play again? (Y/N)");
                    reply = input.next().charAt(0);
                }
            }
        }
    }

    private static void setupGame() {
        gatherInfoOfHumanPlayers();
        gatherInfoOfBotPlayers();
        delay(500);
        System.out.println("\nHow many undos can a player make?");
        int undoLimitPerPlayer = input.nextInt();

        try {
            game = gameController.createGame(players, undoLimitPerPlayer);
        }
        catch (Exception e) {
            System.out.println("Error occurred while creating the game: " + e.getMessage());
            errorOccurred = true;
            return;
        }
        playGame();
    }

    private static void gatherInfoOfHumanPlayers() {
        delay(500);
        System.out.println("Enter the number of human players:");
        int numOfHumanPlayers = input.nextInt();

        for (int i = 1; i <= numOfHumanPlayers ; i++) {
            delay((500));
            System.out.println("\nEnter the name and symbol for Player " + i + " (eg. Abrar X):");
            String playerName = input.next();
            char symbol = input.next().charAt(0);

            while (symbolSet.contains(symbol)) {
                delay(500);
                System.out.println("The entered symbol has already been taken. Please enter a different one.");
                symbol = input.next().charAt(0);
            }

            symbolSet.add(symbol);
            players.add(new PlayerHuman(playerName, new Symbol(symbol)));
        }
    }

    private static void gatherInfoOfBotPlayers() {
        delay(500);
        System.out.println("\nEnter the number of bots (0 or 1):");
        int numOfBotPlayers = input.nextInt();
        String symbolPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123467890";
        Random random = new Random();

        for (int i = 1; i <= numOfBotPlayers; i++) {
            delay(500);
            System.out.println("\nPlease select difficulty level for Bot " + i + "(\033[1;32mE\033[0masy/\033[1;33mM\033[0medium/\033[1;31mH\033[0mard):");
            char botDifficulty = input.next().charAt(0);
            BotLevel botLevel = null;

            switch(botDifficulty) {
                case 'E':
                    botLevel = BotLevel.EASY;
                    break;
                case 'M':
                    botLevel = BotLevel.MEDIUM;
                    break;
                case 'H':
                    botLevel = BotLevel.HARD;
                    break;
            }

            int x = random.nextInt(symbolPool.length());
            char symbol = symbolPool.charAt(x);
            while (symbolSet.contains(symbol)) {
                x = random.nextInt(symbolPool.length());
                symbol = symbolPool.charAt(x);
            }

            symbolSet.add(symbol);
            players.add(new PlayerBot("Bot " + i, new Symbol(symbol), botLevel));
        }
    }

    private static void playGame() {
        System.out.println("\n\033[32mSetup successful! Starting new game in\033[0m");
        delay(1000);
        System.out.println("\n3..");
        delay(1000);
        System.out.println("\n2..");
        delay(1000);
        System.out.println("\n1..");
        delay(1000);
        System.out.println("Bhavik's handwriting sucks!");
        delay(300);
        for (int i = 0; i < 50; i++) {
            System.out.print("\n");
        }

        while (gameController.getGameStatus(game) == GameStatus.IN_PROGRESS) {
            gameController.printBoard(game);
            gameController.makeMove(game);
            gameController.askUndo(game);
        }

        GameStatus gameStatus = gameController.getGameStatus(game);
        if (gameStatus == GameStatus.ENDED) {
            Player winner = gameController.getCurrentPlayer(game);
            delay(500);
            System.out.println("\nGAME OVER! \033[1;42m " + winner.getName() + " \033[0m" + " has won the game!");
        }
        else {
            System.out.println("\nThe game has ended in a draw!");
        }
        gameController.printBoard(game);
    }

    private static void askForGameRecap() {
        delay(1000);
        System.out.println("\nDo you want a recap of the previous game? (Y/N)");
        char reply = input.next().charAt(0);
        if (reply == 'y' || reply == 'Y') {
            gameController.recapGame(game);
        }
    }
}
