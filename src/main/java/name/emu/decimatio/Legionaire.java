package name.emu.decimatio;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Legionaire implements Serializable {

    private boolean playerCharacter;

    private String name;

    private Legionaire nemesis;

    private Move upcomingMove;
}
