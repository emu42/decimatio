package name.emu.decimatio.ui.model;

import name.emu.decimatio.model.Commander;
import name.emu.decimatio.model.CommanderStatus;
import name.emu.decimatio.model.GameState;
import name.emu.decimatio.model.GameStatus;
import org.apache.wicket.model.IModel;

public class CommanderPositionModel implements IModel<Commander> {

    private IModel<GameState> gameState;

    private int pos;
    public CommanderPositionModel(final IModel<GameState> gameState, final int pos) {
        this.gameState = gameState;
        this.pos = pos;
    }

    @Override
    public Commander getObject() {
        Commander commander;
        if (pos != gameState.getObject().getCommanderPos()) {
            commander = Commander.builder().status(CommanderStatus.ABSENT).build();
        } else if (gameState.getObject().getStatus() == GameStatus.ENDROUND) {
            commander = Commander.builder().status(CommanderStatus.STABBING).build();
        } else if (gameState.getObject().getStatus() == GameStatus.SCORE) {
            commander = Commander.builder().status(CommanderStatus.FACING_TROOPS).build();
        } else if (gameState.getObject().getStatus() == GameStatus.INPUT) {
            commander = Commander.builder().status(CommanderStatus.FACING_TROOPS).build();
        } else {
            commander = Commander.builder().status(CommanderStatus.FACING_AWAY).build();
        }
        return commander;
    }
}
