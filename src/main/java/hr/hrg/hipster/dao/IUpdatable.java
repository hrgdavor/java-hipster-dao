package hr.hrg.hipster.dao;

import hr.hrg.hipster.dao.*;
import hr.hrg.hipster.sql.*;

public interface IUpdatable<E extends IColumnMeta> extends IEnumSetter<E>, IEnumGetter<E>, IUpdateDelta<E>{

}
