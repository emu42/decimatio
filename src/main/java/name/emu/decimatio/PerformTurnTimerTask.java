package name.emu.decimatio;

import java.util.TimerTask;

public class PerformTurnTimerTask extends TimerTask {

    private GameState gameState;

    public PerformTurnTimerTask(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void run() {
        GameLogic.performTurn(gameState);
    }
}
