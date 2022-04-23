package name.emu.decimatio;

import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class GameLogic {

    public static final int INPUT_TIME_MILLIS = 5000;

    public static final int MOVE_TIME_MILLIS = 5000;

    private static Timer TIMER = new Timer();

    public static synchronized void addPlayer(GameState gameState, Player player) {
        if (!gameState.getPlayers().contains(player)) {
            gameState.getPlayers().add(player);
        }
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

        TIMER.schedule(new PerformTurnTimerTask(gameState), INPUT_TIME_MILLIS);
    }

    public static void showMove(GameState gameState) {
        gameState.setStatus(GameStatus.MOVING);
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

        // schedule next update
        TIMER.schedule(new ShowMoveTimerTask(gameState), INPUT_TIME_MILLIS);
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
