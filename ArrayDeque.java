package deque;
import java.util.Iterator;


/** Deque class */
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextLast;
    private int nextFirst;

    private static final int STARTSIZE = 8;

    private static final int HardNumber = 4;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[STARTSIZE];
        size = 0;
        nextFirst = 0;
        nextLast = 0;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;
        public ArrayDequeIterator() {
            wizPos = 0;
        }
        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            if (!hasNext()) {
                return null;
            }
            T returnItem = get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize() {
        if (size < (items.length / HardNumber) && size != 0) {
            T[] a = (T[]) new Object[items.length / 2];
            copyArray(a);
        }
        if (size == items.length) {
            T[] a = (T[]) new Object[items.length * 2];
            copyArray(a);
        }
    }
    /** copys the array after resizing*/
    private void copyArray(T[] a) {
        int pointer = nextFirst;
        int counter = 0;
        while (pointer != nextLast) {
            a[counter++] = items[pointer];
            pointer = (pointer + 1) % items.length;
        }
        a[counter] = items[pointer];
        nextFirst = 0;
        nextLast = size - 1;
        items = a;
    }
    /** adds to the beggining of the array */
    public void addFirst(T x) {
        resize();
        if (checkSizeZero(x)) {
            return;
        }
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        items[nextFirst] = x;
        size += 1;
    }
    /** handles array for an array with size 0 */
    private boolean checkSizeZero(T x) {
        if (size == 0) {
            nextFirst = 0;
            nextLast = 0;
            items[0] = x;
            size += 1;
            return true;
        }
        return false;
    }

    /** Inserts X into the back of the list. */
    public void addLast(T x) {
        resize();
        if (checkSizeZero(x)) {
            return;
        }
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
        items[nextLast] = x;
    }

    /** Deletes item from back of the list and returns deleted item. */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T x = items[nextLast];
        items[nextLast] = null;
        if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast -= 1;
        }
        size -= 1;
        if (size == 0) {
            nextFirst = 0;
            nextLast = 0;
        }
        resize();
        return x;
    }

    /** Deletes item from front of the list and returns deleted item. */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T x = items[nextFirst];
        items[nextFirst] = null;
        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }
        size = size - 1;
        if (size == 0) {
            nextFirst = 0;
            nextLast = 0;
        }
        resize();
        return x;
    }

    /** Gets the ith item in the list (0 is the front). */
    public T get(int i) {
        return items[(nextFirst + i) % items.length];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public void printDeque() {
        for (T item : items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
    public boolean equals(Object o) {
        if (o instanceof Deque<?>) {
            Deque newo = (Deque) o;
            if (this.size != newo.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (newo.get(i) != get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
    private boolean checkNullEquals(Object o){
        if (o == null){
            return false;
        }
        if (o == this){
            return true;
        }
        return true;
    }
    /**
    private static void main(String[] args) {
        /* Creates a list of one integer, namely 10
        ArrayDeque<Integer>Arr = new ArrayDeque<>();
        Arr.addLast(1);
        Arr.addLast(1);
        Arr.removeLast();
        Arr.addLast(1);
        Arr.printDeque();
    }
     */
}
