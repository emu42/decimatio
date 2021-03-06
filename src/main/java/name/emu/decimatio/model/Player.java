package name.emu.decimatio.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player implements Serializable {
    private String id;

    private String name;

    private String gameSessionId;

    private boolean hoster;

    private Legionnaire character;

    private int lastGameStateVersionRendered;

    private int score;
}
