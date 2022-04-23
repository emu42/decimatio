package name.emu.decimatio.timer;

import java.util.TimerTask;
import name.emu.decimatio.GameLogic;
import name.emu.decimatio.model.GameState;

public class ShowScoreTimerTask extends TimerTask {

    private GameState gameState;

    public ShowScoreTimerTask(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void run() {
        GameLogic.showScore(gameState);
    }
}
