package name.emu.decimatio.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Commander implements Serializable {
    private CommanderStatus status;
}
