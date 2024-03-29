package com.nostra13.universalimageloader.cache.disc;

import java.io.File;

/**
 * Interface for cache on file system
 * 
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public abstract class DiscCache {

	private File cacheDir;

	public DiscCache(File cacheDir) {
		this.cacheDir = cacheDir;
	}

	/**
	 * Returns {@linkplain File file object} appropriate incoming URL.<br />
	 * <b>NOTE:</b> Must <b>not to return</b> a null. Method must return specific {@linkplain File file object} for
	 * incoming URL whether file exists or not.
	 */
	public abstract File getFile(String url);

	/** Clears cache directory */
	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}

	protected File getCacheDir() {
		return cacheDir;
	}
}