package name.emu.decimatio;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameState implements Serializable {

    private GameStatus status;

    private List<Player> players;

    private List<Legionnaire> legionnaires;

    private int tenthSlotPos;

    private int commanderPos;
}
