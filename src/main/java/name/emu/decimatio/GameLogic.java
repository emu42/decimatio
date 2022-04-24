package name.emu.decimatio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStatus;
import name.emu.decimatio.model.Legionnaire;
import name.emu.decimatio.model.Move;
import name.emu.decimatio.model.Player;
import name.emu.decimatio.timer.PerformTurnTimerTask;
import name.emu.decimatio.timer.ShowMoveTimerTask;
import name.emu.decimatio.timer.ShowScoreTimerTask;

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
        if (gameState.getStatus() == GameStatus.LOBBY) {

            // create player characters
            for (Player player : gameState.getPlayers()) {
                Legionnaire legionnaire = Legionnaire.builder().playerCharacter(true).name(player.getName()).upcomingMove(Move.NONE).build();
                player.setCharacter(legionnaire);
                legionnaireList.add(legionnaire);
            }

            // randomly assign nemesisss..s?
            if (gameState.getPlayers().size() >= 2) {
                List<Player> nemesisAssignmentList = new ArrayList<>(gameState.getPlayers());
                Collections.shuffle(nemesisAssignmentList);

                for (int i = 0; i < nemesisAssignmentList.size() - 1; i++) {
                    nemesisAssignmentList.get(i).getCharacter().setNemesis(nemesisAssignmentList.get(i + 1).getCharacter());
                }
                nemesisAssignmentList.get(nemesisAssignmentList.size() - 1).getCharacter().setNemesis(nemesisAssignmentList.get(0).getCharacter());
            }

            // create AI characters
            while (legionnaireList.size() < 10) {
                Legionnaire legionnaire = Legionnaire.builder().playerCharacter(false).name("L" + legionnaireList.size()).upcomingMove(Move.NONE).build();
                legionnaireList.add(legionnaire);
            }
        } else {
            // RELAUNCH
            // reset everything but nemesises and score
            gameState.getLegionnaires().forEach(legionnaire -> legionnaire.setUpcomingMove(Move.NONE));
            gameState.setCommanderPos(9);
        }

        Collections.shuffle(legionnaireList);
        gameState.setStatus(GameStatus.INPUT);

        incrementVersion(gameState);

        schedule(new PerformTurnTimerTask(gameState), INPUT_TIME_MILLIS);
    }

    private static void schedule(final TimerTask timerTask, final int millis) {
        TIMER.schedule(timerTask, millis);
    }

    public static void showMove(GameState gameState) {
        gameState.setStatus(GameStatus.MOVING);
        incrementVersion(gameState);

        schedule(new PerformTurnTimerTask(gameState), MOVE_TIME_MILLIS);
    }

    public static void showScore(GameState gameState) {
        gameState.setStatus(GameStatus.SCORE);

        Legionnaire decimated = gameState.getLegionnaires().get(gameState.getTenthSlotPos()-1);
        decimated.setUpcomingMove(Move.DEAD);

        updateScore(gameState);

        incrementVersion(gameState);
    }

    private static void updateScore(final GameState gameState) {
        for (Player player: gameState.getPlayers()) {
            if (player.getCharacter().getUpcomingMove() != Move.DEAD) {
                player.setScore(player.getScore() + 10);
            }
            if (player.getCharacter().getNemesis()!=null && player.getCharacter().getNemesis().getUpcomingMove() == Move.DEAD) {
                player.setScore(player.getScore() + 20);
            }
        }
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

        commanderMove(gameState);

        if (gameState.getStatus() == GameStatus.INPUT) {   // may be overruled by commander
            // schedule next update
            schedule(new ShowMoveTimerTask(gameState), INPUT_TIME_MILLIS);
        } else {
            schedule(new ShowScoreTimerTask(gameState), MOVE_TIME_MILLIS);
        }
    }

    private static void commanderMove(final GameState gameState) {

        if (gameState.getCommanderPos() == gameState.getTenthSlotPos() - 1) {
            // execution
            gameState.setStatus(GameStatus.ENDROUND);

        } else {
            // move on
            gameState.setCommanderPos(gameState.getCommanderPos()-1);
        }
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
