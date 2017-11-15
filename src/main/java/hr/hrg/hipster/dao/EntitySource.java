package hr.hrg.hipster.dao;

import java.util.*;
import java.util.concurrent.*;

import hr.hrg.hipster.sql.*;

public class EntitySource {
	protected Map<Class<? extends Object>, IEntityMeta<?,?,?>> registered = new ConcurrentHashMap<>(); 
	protected Map<String, IEntityMeta<?,?,?>> named = new ConcurrentHashMap<>();

	private ResultGetterSource resultGetterSource;

	public EntitySource() {
	}
	
	public EntitySource(ResultGetterSource resultGetterSource) {
		this.resultGetterSource = resultGetterSource;
	}
	
	public <T> void registerFor(IEntityMeta<T, ?,?> meta){
		Class<T> clazz = meta.getEntityClass();		
		registerFor(meta, clazz);
	}

	public <T> void registerFor(IEntityMeta<T, ?,?> meta, Class<T> clazz){
		registered.put(clazz, meta);
		named.put(clazz.getSimpleName(), meta);
	}

	@SuppressWarnings("unchecked")
	public <T> IEntityMeta<T, ?,? extends IColumnMeta> getFor( Class<T> clazz){
		IEntityMeta<T, ?, ?> ret = (IEntityMeta<T, ?,? extends IColumnMeta>) registered.get(clazz);
		if(ret == null){
			ret = (IEntityMeta<T, ?, ?>) loadHandlerFromClass(HipsterSqlUtil.entityNamesPrefix(clazz)+"Meta");
			if(ret != null) registerFor(ret, clazz);
		}
		return ret;
	}
	
	public <T> IEntityMeta<T, ?,? extends IColumnMeta> getForRequired( Class<T> clazz){
		IEntityMeta<T, ?, ?> ret = getFor(clazz);
		
		if(ret == null) throw new RuntimeException("Meta not found for "+clazz.getName());
		
		return ret;
	}

	public IEntityMeta<?, ?,? extends IColumnMeta> getFor(String name){
		return named.get(name);
	}

	public IEntityMeta<?, ?,? extends IColumnMeta> getForRequired(String name){
		IEntityMeta<?, ?, ?> ret = named.get(name);
		
		if(ret == null) throw new RuntimeException("Meta not found for "+name);

		return ret;
	}

	public IEntityMeta<?,?,?> loadHandlerFromClass(String cName) {
		if(resultGetterSource == null) return null;
		try {
			Class<?> meta = Class.forName(cName);
			// found if no exception, now we just need to construct new instance
			if(IEntityMeta.class.isAssignableFrom(meta))
				return (IEntityMeta<?, ?, ?>) meta.getConstructor(new Class<?>[]{ResultGetterSource.class}).newInstance(resultGetterSource);
		} catch (ClassNotFoundException e) {
			// ok, no Meta generated by hipster-processor
		} catch (Exception e) {
			throw new RuntimeException("Found possible generated Visitor handler class, but faild to instantiate ",e);
		}
		return null;
	}

	@SafeVarargs
	public static final void registerBoth(EntitySource entitySource, ReaderSource readerSource, IEntityMeta<?, ?,? extends IColumnMeta> ...metas ){
		for (IEntityMeta<?, ?, ? extends IColumnMeta> meta : metas) {
			entitySource.registerFor(meta);
			readerSource.registerFor(meta);
		}
	}
	
}
