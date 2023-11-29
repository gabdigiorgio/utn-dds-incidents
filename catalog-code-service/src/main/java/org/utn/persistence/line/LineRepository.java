package org.utn.persistence.line;

import org.utn.domain.Line;
import java.util.List;

public interface LineRepository {
    Line getById(String id);

    List<Line> all();
}
