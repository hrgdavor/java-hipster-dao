package hr.hrg.hipster.dao;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HipsterEntity {
	public String table();

	public boolean genMeta() default true;

	boolean genUpdate() default true;
}
