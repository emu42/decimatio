package name.emu.decimatio;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStatus;
import name.emu.decimatio.model.Legionnaire;
import name.emu.decimatio.model.Move;
import name.emu.decimatio.model.Player;
import name.emu.decimatio.timer.PerformTurnTimerTask;
import name.emu.decimatio.timer.ShowMoveTimerTask;

public class GameLogic {

    public static final int INPUT_TIME_MILLIS = 5000;

    public static final int MOVE_TIME_MILLIS = 2000;

    private static Timer TIMER = new Timer();

    public static synchronized String addPlayer(GameState gameState, Player player) {
        String error = null;
        if (!gameState.getPlayers().contains(player)) {
            if (gameState.getPlayers().size() <= 10) {
                gameState.getPlayers().add(player);
            } else {
                error = "Game session already full.";
            }
        }
        return error;
    }

    public static void launchGame(GameState gameState) {
        List<Legionnaire> legionnaireList = gameState.getLegionnaires();

        // create player characters
        for (Player player: gameState.getPlayers()) {
            Legionnaire legionnaire = Legionnaire.builder().playerCharacter(true).name(player.getName()).upcomingMove(Move.NONE).build();
            player.setCharacter(legionnaire);
            legionnaireList.add(legionnaire);
        }

        // create AI characters
        while (legionnaireList.size()<10) {
            Legionnaire legionnaire = Legionnaire.builder().playerCharacter(false).name("L"+legionnaireList.size()).upcomingMove(Move.NONE).build();
            legionnaireList.add(legionnaire);
        }

        Collections.shuffle(legionnaireList);
        gameState.setStatus(GameStatus.INPUT);

        incrementVersion(gameState);

        TIMER.schedule(new PerformTurnTimerTask(gameState), INPUT_TIME_MILLIS);
    }

    public static void showMove(GameState gameState) {
        gameState.setStatus(GameStatus.MOVING);
        incrementVersion(gameState);

        TIMER.schedule(new PerformTurnTimerTask(gameState), MOVE_TIME_MILLIS);
    }

    public static void performTurn(GameState gameState) {
        int moveVector = 0;

        for (Legionnaire legionnaire : gameState.getLegionnaires()) {
            switch (legionnaire.getUpcomingMove()) {
                case PUSH_LEFT:
                    moveVector--;
                    break;
                case PUSH_RIGHT:
                    moveVector++;
                    break;
                case NONE:
                    break;
            }
        }

        shiftPositions(gameState.getLegionnaires(), moveVector);

        for (Legionnaire legionnaire : gameState.getLegionnaires()) {
            legionnaire.setUpcomingMove(Move.NONE);
        }

        gameState.setStatus(GameStatus.INPUT);
        incrementVersion(gameState);

        // schedule next update
        TIMER.schedule(new ShowMoveTimerTask(gameState), INPUT_TIME_MILLIS);
    }

    private static void incrementVersion(final GameState gameState) {
        gameState.setVersion(gameState.getVersion() + 1);
    }

    private static void shiftPositions(final List<Legionnaire> legionnaires, int moveVector) {
        while (moveVector < 0) {
            legionnaires.add(legionnaires.remove(0));
            moveVector++;
        }

        while (moveVector > 0) {
            legionnaires.add(0, legionnaires.remove(legionnaires.size()-1));
            moveVector--;
        }
    }
}
