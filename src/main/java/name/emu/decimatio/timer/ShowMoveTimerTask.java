package name.emu.decimatio.timer;

import java.util.TimerTask;
import name.emu.decimatio.GameLogic;
import name.emu.decimatio.model.GameState;

public class ShowMoveTimerTask extends TimerTask {

    private GameState gameState;

    public ShowMoveTimerTask(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void run() {
        GameLogic.showMove(gameState);
    }
}
