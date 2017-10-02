package hr.hrg.hipster.dao.change;

import hr.hrg.hipster.sql.*;

public interface IChangeListener<T, ID, E extends IColumnMeta> {

	public void recordChanged(DataUpdate<T,ID,E> update, long batchId, boolean batchDone);
}
