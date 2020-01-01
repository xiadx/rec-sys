package container;

public class CustomHashMapIII<K, V> {

    HashMapNodeII[] table;
    int size;

    public CustomHashMapIII() {
        table = new HashMapNodeII[16];
    }

    public V get(K key) {
        int h = hash(key.hashCode(), table.length);
        V value = null;
        if (table[h] != null) {
            HashMapNodeII q = table[h];
            while (q != null) {
                if (q.key.equals(key)) {
                    value = (V)q.value;
                    break;
                } else {
                    q = q.next;
                }
            }
        }
        return value;
    }

    public void put(K key, V value) {
        HashMapNodeII node = new HashMapNodeII();
        node.hash = hash(key.hashCode(), table.length);
        node.key = key;
        node.value = value;

        HashMapNodeII q = table[node.hash];
        HashMapNodeII p = null;
        if (q == null) {
            table[node.hash] = node;
        } else {
            while (q != null) {
                if (q.key.equals(node.key)) {
                    q.value = node.value;
                    break;
                } else {
                    p = q;
                    q = q.next;
                }
            }
            if (q == null) {
                p.next = node;
            }
        }
    }

    public int hash(int v, int length) {
        return v & (length - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < table.length; i++) {
            HashMapNodeII q = table[i];
            while (q != null) {
                sb.append(q.key + ":" + q.value + ",");
                q = q.next;
            }
        }
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomHashMapIII<String, Integer> m = new CustomHashMapIII<String, Integer>();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        System.out.println(m);
        System.out.println(m.get("b"));
    }

}
