package com.nostra13.universalimageloader.cache.memory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Limited cache. Provides object storing. Size of all stored bitmaps will not to exceed size limit (
 * {@link #getSizeLimit()}).
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @see MemoryCache
 */
public abstract class LimitedCache<K, V> extends MemoryCache<K, V> {

	private final int sizeLimit;

	private int cacheSize = 0;

	/**
	 * Contains strong references to stored objects. Each next object is added last. If hard cache size will exceed
	 * limit then first object is deleted (but it continue exist at {@link #softMap} and can be collected by GC at any
	 * time)
	 */
	private final List<V> hardCache = Collections.synchronizedList(new LinkedList<V>());

	public LimitedCache(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	@Override
	public boolean put(K key, V value) {
		boolean putSuccessfully = false;
		// Try to add value to hard cache
		int valueSize = getSize(value);
		int sizeLimit = getSizeLimit();
		if (valueSize < sizeLimit) {
			while (cacheSize + valueSize > sizeLimit) {
				V removedValue = removeNext();
				if (hardCache.remove(removedValue)) {
					cacheSize -= getSize(removedValue);
				}
			}
			hardCache.add(value);
			cacheSize += valueSize;

			putSuccessfully = true;
		}
		// Add value to soft cache
		super.put(key, value);
		return putSuccessfully;
	}

	@Override
	public V get(K key) {
		return super.get(key);
	}

	@Override
	public void clear() {
		hardCache.clear();
		cacheSize = 0;
		super.clear();
	}

	protected int getSizeLimit() {
		return sizeLimit;
	}

	protected abstract int getSize(V value);

	protected abstract V removeNext();
}
