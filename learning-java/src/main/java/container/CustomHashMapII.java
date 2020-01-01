package container;

public class CustomHashMapII {

    HashMapNode[] table;
    int size;

    public CustomHashMapII() {
        table = new HashMapNode[16];
    }

    public Object get(Object key) {
        int h = hash(key.hashCode(), table.length);
        Object value = null;
        if (table[h] != null) {
            HashMapNode q = table[h];
            while (q != null) {
                if (q.key.equals(key)) {
                    value = q.value;
                    break;
                } else {
                    q = q.next;
                }
            }
        }
        return value;
    }

    public void put(Object key, Object value) {
        HashMapNode node = new HashMapNode();
        node.hash = hash(key.hashCode(), table.length);
        node.key = key;
        node.value = value;

        HashMapNode q = table[node.hash];
        HashMapNode p = null;
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
            HashMapNode q = table[i];
            while (q != null) {
                sb.append(q.key + ":" + q.value + ",");
                q = q.next;
            }
        }
        sb.setCharAt(sb.length() - 1, '}');
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomHashMapII m = new CustomHashMapII();
        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        System.out.println(m);
        System.out.println(m.get("b"));
    }

}
