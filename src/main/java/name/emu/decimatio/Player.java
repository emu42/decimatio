package name.emu.decimatio;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Player implements Serializable {
    private String id;

    private String name;

    private Legionnaire character;
}
