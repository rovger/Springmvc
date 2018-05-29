package com.rovger.demo;

/**
 * Created by weijlu on 2018/5/3.
 */
public class EasyMap<K, V> {
    private static int arraySize = 10;
    private EasyEntry[] keys = null;

    public EasyMap() {
        keys = new EasyEntry[arraySize];
    }

    public V put(K key, V value) {
        int hash = getHash(key);
        EasyEntry<K, V> entry = keys[hash];
        while (entry != null) {
            if (entry.getKey().hashCode()==key.hashCode() &&
                    (entry.getKey()==key || entry.getKey().equals(key))) {
                V oldVal = entry.getValue();
                entry.setValue(value);
                return oldVal;
            }
            entry = entry.getNext();
        }
        //add new
        EasyEntry<K, V> newEntry = new EasyEntry<>(key, value, keys[hash]);
        keys[hash] = newEntry;
        return null;
    }

    public V get(K key) {
        int hash = getHash(key);
        EasyEntry<K, V> entry = keys[hash];
        return entry == null ? null : entry.getValue();
    }

    public EasyEntry<K, V> remove(K key) {
        int hash = getHash(key);
        EasyEntry<K, V> entry = keys[hash];
        EasyEntry pre = null;
        while (entry != null) {
            if (entry.getKey().hashCode()==key.hashCode() &&
                    (entry.getKey()==key || entry.getKey().equals(key))) {
                if (pre == null) {
                    keys[hash] = entry.getNext();
                } else {
                    pre.setNext(entry.getNext());
                }
            }
            pre = entry;
            entry = entry.getNext();
        }
        return null;
    }

    private int getHash(K key) {
        int hash = key.hashCode();
        return  hash % arraySize;
    }

}

class EasyEntry<K, V> {
    final K key;
    V value;
    EasyEntry<K, V> next;

    public EasyEntry(K key, V value, EasyEntry<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public EasyEntry<K, V> getNext() {
        return next;
    }

    public void setNext(EasyEntry<K, V> next) {
        this.next = next;
    }
}