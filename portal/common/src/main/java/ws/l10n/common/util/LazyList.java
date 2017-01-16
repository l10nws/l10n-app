package ws.l10n.common.util;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Partition list.
 *
 * @param <T> generic object
 * @author Anton Mokshyn
 */
public abstract class LazyList<T> implements List<T>, Iterable<T> {

    private static final int DEFAULT_OFFSET = 10;

    private final long fCount;
    private final int fOffset;

    private long fStart = 0L;
    private List<T> list;

    /**
     * Instantiates a new Lazy list.
     */
    public LazyList() {
        this(DEFAULT_OFFSET);
    }

    /**
     * Instantiates a new Lazy list.
     *
     * @param offset the offset
     */
    public LazyList(int offset) {
        this.fOffset = offset;
        this.fCount = count();
    }

    /**
     * Count long.
     *
     * @return the long
     */
    public abstract long count();

    /**
     * Gets list.
     *
     * @param start  the start
     * @param offset the offset
     * @return the list
     */
    public abstract List<T> getList(long start, int offset);

    @Override
    public int size() {
        return (int) fCount;
    }

    @Override
    public boolean isEmpty() {
        return fCount == 0;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("index cannot be negative value");
        }
        if (index < fOffset && list == null) {
            list = getList(0, fOffset);
        } else if (index > (fStart + fOffset) - 1 || index < fStart) {
            fStart = index / fOffset * fOffset;
            list = getList(fStart, fOffset);
        }
        return list.get(index % fOffset);
    }

    @Override
    public Iterator<T> iterator() {
        return new LazyIterator<T>(fCount, fOffset) {
            @Override
            public List<T> getList(long start, int offset) {
                return LazyList.this.getList(start, offset);
            }
        };
    }


    @Override
    public Object[] toArray() {
        return ImmutableList.builder().addAll(this).build().toArray();
    }

    @Override
    public <T> T[] toArray(T[] arr) {
        return ImmutableList.builder().addAll(this).build().toArray(arr);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        List<T> subList = new ArrayList<>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(get(i));
        }
        return subList;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    public abstract static class LazyIterator<T> implements Iterator<T> {

        private final long fCount;
        private final int fOffset;
        private long i;
        private long fStart;
        private int index;
        private List<T> list;

        /**
         * Instantiates a new Lazy iterator.
         *
         * @param count  the count
         * @param offset the offset
         */
        protected LazyIterator(long count, int offset) {
            this.fCount = count;
            this.fOffset = offset;
            this.list = getList(fStart, offset);
        }

        @Override
        public boolean hasNext() {
            if (index == list.size()) {
                fStart = ++i * fOffset;
                if (fStart < fCount) {
                    list = getList(fStart, fOffset);
                    index = 0;
                }
            }
            return index < list.size();
        }

        /**
         * Gets list.
         *
         * @param start  the start
         * @param offset the offset
         * @return the list
         */
        public abstract List<T> getList(long start, int offset);

        @Override
        public T next() {
            return list.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
