package hr.hrg.hipster.dao.change;

import java.io.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;

import hr.hrg.hipster.dao.*;
import hr.hrg.hipster.dao.jackson.*;
import hr.hrg.hipster.sql.*;

@JsonSerialize(using=DirectSerializer.class)
public class EnumGetterUpdateDelta<T extends IEnumGetter<E>,E extends IColumnMeta> 
	implements IUpdateDelta<E>, IEnumGetter<E>, IDirectSerializerReady{

	protected long changeSet;
	protected T values;
	private E[] universe;

	public EnumGetterUpdateDelta(long changeSet, T values, E[] universe) {
		this.changeSet = changeSet;
		this.values = values;
		this.universe = universe;
	}

	@Override
	public final boolean isEmpty() {
		return changeSet == 0;
	}
	
	@Override
	public final boolean isChanged(E column) {
        return (changeSet & (1L << column.ordinal())) != 0;
	}

	@Override
	public final boolean isChanged(int ordinal) {
		return (changeSet & (1L << ordinal)) != 0;
	}

	@Override
	public final Object getValue(E column) {
		return values.getValue(column.ordinal());
	}

	@Override
	public final Object getValue(int ordinal) {
		return values.getValue(ordinal);
	}
	
	@Override
	public void serialize(JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
		jgen.writeStartObject();

		int count = universe.length;
		for(int i=0; i<count; i++){
			if((changeSet & (1L << i)) != 0) jgen.writeObjectField(universe[i].name(), values.getValue(i));
		}

		jgen.writeEndObject();		
	}

	public T getValueSource(){
		return values;
	}

}