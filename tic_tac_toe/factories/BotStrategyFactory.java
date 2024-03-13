package machine_coding.tic_tac_toe.factories;

import machine_coding.tic_tac_toe.models.BotLevel;
import machine_coding.tic_tac_toe.strategies.bot_algorithm.BotStrategy;
import machine_coding.tic_tac_toe.strategies.bot_algorithm.EasyBotStrategy;

public class BotStrategyFactory {
    public static BotStrategy getBotStrategy(BotLevel botLevel) {
        switch (botLevel) {
            case EASY:
            case MEDIUM:
            case HARD:
                return new EasyBotStrategy();
            default:
                throw new UnsupportedOperationException("The given bot level is not supported.");
        }
    }
}
