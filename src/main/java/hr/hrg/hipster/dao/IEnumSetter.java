package hr.hrg.hipster.dao;

import hr.hrg.hipster.sql.*;

public interface IEnumSetter<E extends IColumnMeta>{

	/** Type safe version for coded usage.
	 * 
	 * @param column Enum for column
	 * @param value new value
	 */	
	public void setValue(E column, Object value);

	/** Index based version for looping.
	 * 
	 * @param ordinal column index
	 * @param value new value
	 * 
	 */	
	public void setValue(int ordinal, Object value);
}
