package me.ranol.capjit.utils;


public class Result<T> {
	T obj;

	public static <T> Result<T> create() {
		return new Result<T>();
	}

	public static <T> void fill(Result<T>[] target) {
		for (int i = 0; i < target.length; i++) {
			target[i] = create();
		}
	}

	public T get() {
		return obj;
	}

	public void set(T t) {
		obj = t;
	}
}
