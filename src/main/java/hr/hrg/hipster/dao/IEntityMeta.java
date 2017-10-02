package hr.hrg.hipster.dao;

import java.util.*;

import hr.hrg.hipster.sql.*;


public interface IEntityMeta<T,ID,E extends IColumnMeta> extends IReadMeta<T,E>{

	Class<E> getEntityEnum();

	List<String> getColumnNames();

	E getPrimaryColumn();

	E getColumn(String name);
	
	E getColumn(int ordinal);

	Object[] entityGetValues(T v);

	T entityFromValues(Object[] v);

	ID entityGetPrimary(T instance);

}
