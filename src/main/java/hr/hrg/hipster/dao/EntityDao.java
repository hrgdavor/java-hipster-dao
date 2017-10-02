package hr.hrg.hipster.dao;

import java.util.*;

import hr.hrg.hipster.sql.*;

public class EntityDao <T, ID>{

	protected IHipsterConnection conn;
	protected IEntityMeta<T, ID, ? extends IColumnMeta> meta;
	protected String byIdQuery;
	protected String selectQuery;

	public EntityDao(IEntityMeta<T, ID, ? extends IColumnMeta> meta, IHipsterConnection conn){
		this.meta = meta;
		this.conn = conn;
		selectQuery = "select "+meta.getColumnNamesStr()+" FROM "+meta.getTableName()+" ";

		if(meta.getPrimaryColumn() != null){
			this.byIdQuery = selectQuery+"WHERE "+meta.getPrimaryColumn().getColumnName()+"=?";
		}
	}

	public IHipsterConnection getConnection() {
		return conn;
	}
	
	public IEntityMeta<T, ID, ? extends IColumnMeta> getMeta() {
		return meta;
	}
	
	public T byId(ID id){
		if(byIdQuery == null) throw new NullPointerException("Entity "+meta.getEntityClass().getName()+" does not have a primary column defined");
		
		try(Result res = new Result(conn);){
		
			res.executePrepared(byIdQuery, id);
        	
			return res.fetchEntity(meta);
		}		
	}

	public List<T> byCriteria(Object ...queryParts){
		return conn.entities(meta, new Query(selectQuery).append(queryParts));		
	}

}
