package hr.hrg.hipster.dao.change;

import hr.hrg.hipster.sql.*;

public interface IBeforeChangeListener<T, ID, E extends IColumnMeta> {

	public void recordWillChange(ID id, IUpdate<T, IColumnMeta> update);
}
