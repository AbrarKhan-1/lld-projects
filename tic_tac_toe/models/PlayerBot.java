package machine_coding.tic_tac_toe.models;

import machine_coding.tic_tac_toe.strategies.bot_algorithm.BotStrategy;
import machine_coding.tic_tac_toe.factories.BotStrategyFactory;

import static machine_coding.tic_tac_toe.utils.Delay.delay;

public class PlayerBot extends Player {
    private BotLevel botLevel;
    private BotStrategy botStrategy;

    public PlayerBot(String name, Symbol symbol, BotLevel botLevel) {
        super(name, symbol);
        this.botLevel = botLevel;
        this.botStrategy = BotStrategyFactory.getBotStrategy(botLevel);
    }

    @Override
    public Position makeMove(Board board) {
        System.out.println("\nIt's " + this.getName() + "'s turn.\nStratergising next move...");
        delay(1000);
        return botStrategy.makeMove(board);
    }
}