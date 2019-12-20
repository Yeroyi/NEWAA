package com.example.hongligs.ninegridimage;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;

public final class SimpleWeakObjectPool<T> {

    private WeakReference<T>[] mObjectsPool;
    private int mPointer = -1;

    public SimpleWeakObjectPool() {
        this(5);
    }

    public SimpleWeakObjectPool(int size) {
        mObjectsPool = (WeakReference<T>[]) Array.newInstance(WeakReference.class, size);
    }

    public synchronized T get() {
        if (mPointer == -1 || mPointer > mObjectsPool.length) {
            return null;
        }
        T obj = mObjectsPool[mPointer].get();
        mObjectsPool[mPointer] = null;
        mPointer--;
        return obj;
    }

    public synchronized boolean put(T t) {
        if (mPointer == -1 || mPointer < mObjectsPool.length - 1) {
            mPointer++;
            mObjectsPool[mPointer] = new WeakReference<>(t);
            return true;
        }
        return false;
    }

    public synchronized void clear() {
        for (int i = 0; i < mObjectsPool.length; i++) {
            mObjectsPool[i].clear();
            mObjectsPool[i] = null;
        }
        mPointer = -1;
    }

    public synchronized int size() {
        return mObjectsPool == null? 0 : mObjectsPool.length;
    }
}
