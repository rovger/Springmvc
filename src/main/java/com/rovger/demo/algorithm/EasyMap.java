package com.rovger.demo.algorithm;

/**
 * Created by weijlu on 2018/5/3.
 */
public class EasyMap<K, V> {
    private static int arraySize = 16;
    private EasyEntry[] entryArray;

    public EasyMap() {
        entryArray = new EasyEntry[arraySize];
    }

    public V put(K key, V value) {
        int hash = getHash(key);
        for (EasyEntry<K, V> entry = entryArray[hash]; entry != null; entry = entry.next) {
            if (entry.key.hashCode()==key.hashCode() &&
                    (entry.key==key || entry.key.equals(key))) {
                V oldVal = entry.value;
                entry.value = value;
                return oldVal;
            }
        }
        //add new
        EasyEntry<K, V> newEntry = new EasyEntry<>(key, value, entryArray[hash]);
        entryArray[hash] = newEntry;
        return null;
    }

    public V get(K key) {
        int hash = getHash(key);
        for (EasyEntry<K, V> entry = entryArray[hash]; entry != null; entry = entry.next) {
            if (entry.key.hashCode()==key.hashCode() &&
                    (entry.key==key || entry.key.equals(key))) {
                return entry == null ? null : entry.value;
            }
        }
        return null;
    }

    public EasyEntry<K, V> remove(K key) {
        int hash = getHash(key);
        EasyEntry pre = null;
        for (EasyEntry<K, V> entry = entryArray[hash]; entry != null; entry = entry.next) {
            if (entry.key.hashCode()==key.hashCode() &&
                    (entry.key==key || entry.key.equals(key))) {
                if (pre == null) {
                    entryArray[hash] = entry.next;
                } else {
                    pre.next = entry.next;
                }
            }
            pre = entry;
            entry = entry.next;
        }
        return null;
    }

    private int getHash(K key) {
        int hash = key.hashCode();
        return  hash % arraySize;
    }

    /**
     * 静态内部类
     * @param <K>
     * @param <V>
     */
    static class EasyEntry<K, V> {
        final K key;
        V value;
        EasyEntry<K, V> next;

        public EasyEntry(K key, V value, EasyEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
