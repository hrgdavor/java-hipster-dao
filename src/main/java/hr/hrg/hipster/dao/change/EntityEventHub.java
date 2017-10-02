package hr.hrg.hipster.dao.change;

import java.util.*;

import org.slf4j.*;

import hr.hrg.hipster.sql.*;

@SuppressWarnings({"rawtypes","unchecked"})
public class EntityEventHub {
	
	Logger log = LoggerFactory.getLogger(EntityEventHub.class);
	
	Map<Class, List<IChangeListener>> changeListeners = new HashMap<>();
	Map<Class, List<IBeforeChangeListener>> beforeChangeListeners = new HashMap<>();
	Map<Class, List<IDeletedListener>> deleteListeners = new HashMap<>();
	Map<Class, List<IDeletedDetailListener>> deleteDataListeners = new HashMap<>();
	Map<Class, List<IAddListener>> addListeners = new HashMap<>();

	public <ID, O, E extends IColumnMeta> void addChangeListener(IChangeListener<ID, O, E> listener, Class<O> clazz){
		synchronized (clazz) {
			List<IChangeListener> list = changeListeners.get(clazz);
			if(list == null) {
				list = new ArrayList<>();
				changeListeners.put(clazz, list);
			}
			list.add(listener);
		}
	}

	public boolean hasChangeListener(Class<?> clazz){
		return changeListeners.containsKey(clazz);
	}
	
	public <ID, O, E extends IColumnMeta> void addBeforeChangeListener(IBeforeChangeListener<ID, O, E> listener, Class<O> clazz){
		synchronized (clazz) {
			List<IBeforeChangeListener> list = beforeChangeListeners.get(clazz);
			if(list == null) {
				list = new ArrayList<>();
				beforeChangeListeners.put(clazz, list);
			}
			list.add(listener);
		}
	}

	public boolean hasBeforeChangeListener(Class<?> clazz){
		return beforeChangeListeners.containsKey(clazz);
	}
	
	public <ID, O, E extends IColumnMeta> void addDeleteListener(IDeletedListener<ID, O, E> listener, Class<O> clazz){
		synchronized (clazz) {
			List<IDeletedListener> list = deleteListeners.get(clazz);
			if(list == null) {
				list = new ArrayList<>();
				deleteListeners.put(clazz, list);
			}
			list.add(listener);
		}
	}
	
	public boolean hasDeleteListener(Class<?> clazz){
		return deleteListeners.containsKey(clazz);
	}
	
	public <ID, O, E extends IColumnMeta> void addDeleteDataListener(IDeletedDetailListener<ID, O, E> listener, Class<O> clazz){
		synchronized (clazz) {
			List<IDeletedDetailListener> list = deleteDataListeners.get(clazz);
			if(list == null) {
				list = new ArrayList<>();
				deleteDataListeners.put(clazz, list);
			}
			list.add(listener);
		}
	}

	public boolean hasDeleteDataListener(Class<?> clazz){
		return deleteDataListeners.containsKey(clazz);
	}
	
	public <ID, O, E extends IColumnMeta> void addAddListener(IAddListener<ID, O, E> listener, Class<O> clazz){
		synchronized (clazz) {
			List<IAddListener> list = addListeners.get(clazz);
			if(list == null) {
				list = new ArrayList<>();
				addListeners.put(clazz, list);
			}
			list.add(listener);
		}
	}

	public boolean hasAddListener(Class<?> clazz){
		return addListeners.containsKey(clazz);
	}
		
	
	

	public <T,ID,E extends IColumnMeta> void fireUpdate(DataUpdate<T,ID,E> update, long batchId, boolean batchDone){
		List<IChangeListener> list = changeListeners.get(update.getMeta().getEntityClass());
		if(list != null){
			for (IChangeListener listener : list) {
				try {
					listener.recordChanged(update, batchId, batchDone);
				} catch (Throwable e) {
					log.error("Error notifying a listener: "+e.getMessage(),e);
				}
			}
		}
	}

	public <T> void fireDelete(Object id, T old, Class<T> clazz, long batchId, boolean batchDone){
		List<IDeletedListener> list = deleteListeners.get(clazz);
		if(list != null){
			for (IDeletedListener listener : list) {
				try {
					listener.recordDeleted(id, old, batchId, batchDone);
				} catch (Throwable e) {
					log.error("Error notifying a listener: "+e.getMessage(),e);
				}
			}
		}
	}
	
	public <T> void fireDeleteData(Object id, T old, Class<T> clazz, long batchId, boolean batchDone){
		List<IDeletedDetailListener> list = deleteDataListeners.get(clazz);
		if(list != null){
			for (IDeletedDetailListener listener : list) {
				try {
					listener.recordDeletedDetails(id, old, batchId, batchDone);
				} catch (Throwable e) {
					log.error("Error notifying a listener: "+e.getMessage(),e);
				}
			}
		}
	}

	public <T> void fireAdded(Object id, T obj, Class<T> clazz, long batchId, boolean batchDone){
		List<IAddListener> list = addListeners.get(clazz);
		if(list != null){
			for (IAddListener listener : list) {
				try {
					listener.recordAdded(id, obj, batchId, batchDone);
				} catch (Throwable e) {
					log.error("Error notifying a listener: "+e.getMessage(),e);
				}
			}
		}
	}
	
}
