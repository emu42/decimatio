package name.emu.decimatio;

import java.util.TimerTask;

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
