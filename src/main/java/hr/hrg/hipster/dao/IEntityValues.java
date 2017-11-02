package hr.hrg.hipster.dao;

public interface IEntityValues<T> {

	Object[] entityGetValues(T v);

	T entityFromValues(Object[] v);
	
}
