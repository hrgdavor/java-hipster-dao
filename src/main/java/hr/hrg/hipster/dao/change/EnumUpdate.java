package hr.hrg.hipster.dao.change;

import hr.hrg.hipster.sql.*;

/** 
 * Implementation of DataUpdater that collects the changes and can return apropriate {@link IUpdateDelta} object.
 * Default implementation returns immutable {@link IUpdateDelta} that uses internal data of this updater and to
 * be really immutable this updater must not be updated afterwards. 
 * 
 * @author hrg
 *
 * @param <E> Enum containing entity information
 */
public class EnumUpdate<T,E extends IColumnMeta> implements IUpdate<T,E>{
	
	protected long _changeSet;
	protected Object[] _values;
	protected boolean skipCompare = true;
	private E[] universe;
	
	public EnumUpdate(E[] universe) {
		this._changeSet = 0;
		this._values = new Object[universe.length];
		this.universe = universe;
	}

	public EnumUpdate(long changeSet, Object[] values, E[] universe) {
		this._changeSet = changeSet;
		this._values = values;
		this.universe = universe;
	}
	
	/** Set column value and return true if the value actually changed.
	 * 
	 * @param column
	 * @param value
	 * @return
	 */
	public boolean setValue(E column, Object value) {
		return this.setValue(column.ordinal(), value);
	}

	public boolean setValue(int index, Object value) {
		
		Object old = _values[index];
		
		if(skipCompare || changed(old,value)){
			_values[index] = value;
			_changeSet |= (1L << index);
			return true;			
		}
		
		return false;
	}

	public boolean isSkipCompare() {
		return skipCompare;
	}
	
	public void setSkipCompare(boolean compareWhenSet) {
		this.skipCompare = compareWhenSet;
	}
	
	public static final boolean changed(Object old, Object value) {
		
//		return !((old == value) || !(old != null && value != null && old.equals(value))); 
		
		
		// both null or both are same Object by the pointer
		if(old == value) return false;

		// if both are non-null, then compare using equals
		if(old != null && value != null && old.equals(value)) return false;
		
		// also if one is not null and other is, they are different ;)
		return true;
	}

	@Override
	public IUpdateDelta<E> getDelta() {
		return new EnumArrayUpdateDelta<>(_changeSet, _values, universe);
	}
	
};