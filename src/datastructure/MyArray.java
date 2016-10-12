package datastructure;

import java.io.PrintStream;
import java.util.Arrays;

public class MyArray<E>
        implements Cloneable {

    private static final int INIT_CAPACITY = 1;
    private Object[] Data = new Object[0];
    private int size = 0;

    public MyArray() {
        this.Data = new Object[1];
    }

    public synchronized void add(E e) {
        if (this.size == this.Data.length) {
            ensureCapacity();
        }
        this.Data[(this.size++)] = e;
    }

    public void add(int index, E e) {
        while (this.Data.length <= index) {
            ensureCapacity();
        }

        this.Data[index] = e;
        this.size += 1;
    }

    public Object clone() {
        try {
            MyArray<?> v = (MyArray) super.clone();
            v.Data = Arrays.copyOf(this.Data, this.size);
            return v;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public E get(int index) {
        if ((index < 0) || (index >= this.size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }
        return (E) this.Data[index];
    }

    public Object remove(int index) {
        if ((index < 0) || (index >= this.size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }

        Object removedElement = this.Data[index];
        for (int i = index; i < this.size; i++) {
            this.Data[i] = this.Data[(i + 1)];
        }
        this.size -= 1;
        return removedElement;
    }

    public boolean removeByIndex(int index) {
        if ((index < 0) || (index >= this.size)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }

        for (int i = index; i < this.size; i++) {
            this.Data[i] = this.Data[(i + 1)];
        }
        this.size -= 1;
        return true;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < this.size; index++) {
                if (this.Data[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        } else {
            for (int index = 0; index < this.size; index++) {
                if (o.equals(this.Data[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int numMoved = this.size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(this.Data, index + 1, this.Data, index, numMoved);
        }

        this.Data[(--this.size)] = null;
    }

    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.Data[i] = null;
        }

        this.size = 0;
    }

    private void ensureCapacity() {
        int newIncreasedCapacity = this.Data.length * 2;
        this.Data = Arrays.copyOf(this.Data, newIncreasedCapacity);
    }

    public void display() {
        System.out.print("Displaying list : ");
        for (int i = 0; i < this.size; i++) {
            System.out.print(this.Data[i] + " ");
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("[");
        for (int i = 0; i < this.size; i++) {
            str.append(this.Data[i]).append(", ");
        }
        str.append("]");
        return str.toString();
    }

    public int size() {
        return this.size;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < this.size; i++) {
                if (this.Data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < this.size; i++) {
                if (o.equals(this.Data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = this.size - 1; i >= 0; i--) {
                if (this.Data[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = this.size - 1; i >= 0; i--) {
                if (o.equals(this.Data[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Object[] toArray() {
        return Arrays.copyOf(this.Data, this.size);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            return (T[]) (Object[]) Arrays.copyOf(this.Data, this.size, a.getClass());
        }
        System.arraycopy(this.Data, 0, a, 0, this.size);
        if (a.length > this.size) {
            a[this.size] = null;
        }
        return a;
    }

    public void set(int index, E element) {
        this.Data[index] = element;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }
}
