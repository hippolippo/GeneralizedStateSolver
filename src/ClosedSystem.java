import net.jcip.annotations.Immutable;

import java.util.List;

@Immutable
public interface ClosedSystem {

    public default boolean is_reflexive(){
        return false;
    }

    public List<Operation<ClosedSystem>> operations();
}
