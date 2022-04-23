package name.emu.decimatio;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Legionnaire implements Serializable {

    private boolean playerCharacter;

    private String name;

    private Legionnaire nemesis;

    private Move upcomingMove;
}
