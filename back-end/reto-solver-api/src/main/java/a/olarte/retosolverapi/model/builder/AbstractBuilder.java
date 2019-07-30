package a.olarte.retosolverapi.model.builder;

public abstract class AbstractBuilder<T> {

	protected T instance;

	public AbstractBuilder(T instance) {
		this.instance = instance;
	}
	
	public T build() {
		inject();
		validate();
		return instance;
	}

	public abstract void validate();

	public abstract void inject();
	
	
}
