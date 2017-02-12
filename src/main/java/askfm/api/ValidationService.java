package askfm.api;

public interface ValidationService<T, R> {
	R doValidation(T obj);
}
