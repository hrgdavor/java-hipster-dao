package hr.hrg.hipster.dao.change;

import hr.hrg.hipster.sql.*;

public interface IDeletedListener<T, ID, E extends IColumnMeta> {

	public void recordDeleted(ID id, T old, long batchId, boolean batchDone);	
}
