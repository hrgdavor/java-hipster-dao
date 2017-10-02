package hr.hrg.hipster.dao.change;

import hr.hrg.hipster.sql.*;

public interface IUpdate<T,E extends IColumnMeta> {
	public IUpdateDelta<E> getDelta();
}
