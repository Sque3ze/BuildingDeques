package deque;
import java.util.Iterator;



/** LinkedListDeque Class */
public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class ObjetoNode {
        private T item;
        private ObjetoNode next;
        private ObjetoNode last;

        public ObjetoNode(T i, ObjetoNode k, ObjetoNode n) {
            item = i;
            next = n;
            last = k;
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private ObjetoNode wizPos = sentinel.next;

        public boolean hasNext() {
            if (isEmpty()) {
                return false;
            } else {
                return wizPos == sentinel;
            }
        }

        public T next() {
            if (!hasNext()) {
                return null;
            }
            T returnItem = wizPos.item;
            wizPos = wizPos.next;
            return returnItem;
        }
    }

    private ObjetoNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new ObjetoNode(null, sentinel, sentinel);
        size = 0;
    }

    /** Adds x to the front of the list. */
    public void addFirst(T x) {
        sentinel.next = new ObjetoNode(x, sentinel, sentinel.next);
        //sentinel.next.last = sentinel;
        if (size == 0) {
            sentinel.last = sentinel.next;
            sentinel.next.next = sentinel;
        }
        if (size != 0) {
            sentinel.next.next.last = sentinel.next;
        }
        size += 1;
    }
    /** Adds an item to the end of the list. */
    public void addLast(T x) {
        if (size == 0) {
            addFirst(x);
        } else {
            sentinel.last.next = new ObjetoNode(x, sentinel.last, sentinel);
            sentinel.last = sentinel.last.next;
            size += 1;
        }
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        ObjetoNode p = sentinel.next;
        while (p.next != sentinel) {

            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        ObjetoNode p = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.last = sentinel;
        size -= 1;
        return p.item;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        ObjetoNode p = sentinel.last;
        sentinel.last = sentinel.last.last;
        sentinel.last.next = sentinel;
        size -= 1;
        return p.item;
    }

    public T get(int index) {
        ObjetoNode p = sentinel.next;
        for (int counter = 0; counter < index; counter++) {
            p = p.next;
        }
        return p.item;
    }

    public T getRecursive(int index) {
        return recursiveHelper(sentinel, index);
    }

    private T recursiveHelper(ObjetoNode p, int index) {
        if (index == 0) {
            return p.next.item;
        } else {
            return recursiveHelper(p.next, index - 1);
        }
    }

    //equals now moved to project part c
    public boolean equals(Object o) {
        if (o instanceof Deque<?>) {
            Deque newo = (Deque) o;
            if (this.size != newo.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!(newo.get(i).equals(get(i)))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    /**
    private static void main(String[] args) {
    //Creates a list of one integer, namely 10
    LinkedListDeque<Integer>L = new LinkedListDeque<>();
    L.addLast(50);
    L.addFirst(55);
    L.addFirst(1002);
    System.out.println(L.getRecursive(2));
    }*/
}
