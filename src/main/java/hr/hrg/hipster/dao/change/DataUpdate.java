package hr.hrg.hipster.dao.change;

import hr.hrg.hipster.dao.*;
import hr.hrg.hipster.sql.*;

public class DataUpdate<T, ID, E extends IColumnMeta> {

	private T old;
	private T updated;
	private ID id;
	private IUpdateDelta<E> delta;
	private IEntityMeta<T,ID,E> meta;

	public DataUpdate(ID id, T old, T updated, IUpdateDelta<E> delta, IEntityMeta<T,ID,E> meta){
		this.old = old;
		this.updated = updated;
		this.delta = delta;
		this.meta = meta;
	}
	
	public T getOld() {
		return old;
	}
	
	public T getUpdated() {
		return updated;
	}
	
	public IUpdateDelta<E> getDelta() {
		return delta;
	}
	
	public ID getId() {
		return id;
	}
	
	public IEntityMeta<T,ID,E> getMeta() {
		return meta;
	}

	public boolean isNew() {
		return old == null;
	}
}
