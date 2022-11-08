import java.util.ArrayList;

public class MinHeap {
    public ArrayList<MyVertex> list;
    public ArrayList<MyVertex> ProcessedVertices;

    public MinHeap() {

        this.list = new ArrayList<MyVertex>();
        this.ProcessedVertices  = new ArrayList<MyVertex>();
    }

    // public void ClearHeap() {

    //     this.list.clear();
    //     this.ProcessedVertices.clear();
    // }

    public MinHeap(ArrayList<MyVertex> items) {

        this.list = items;
        buildHeap();
    }

    public void insert(MyVertex item) {
        for (MyVertex ver: ProcessedVertices) {
            if(item.pt.x == ver.pt.x && item.pt.y == ver.pt.y)
                return;
        }
        for (MyVertex ver: list) {
            if(item.pt.x == ver.pt.x && item.pt.y == ver.pt.y)
                return;
        }
            list.add(item);
            int i = list.size() - 1;
            int parent = parent(i);

            while (parent != i && list.get(i).f < list.get(parent).f) {

                swap(i, parent);
                i = parent;
                parent = parent(i);
            }
    }

    public void buildHeap() {

        for (int i = list.size() / 2; i >= 0; i--) {
            minHeapify(i);
        }
    }

    public MyVertex extractMin() {

        if (list.size() == 0) {
            return new MyVertex(new Point(0,0),0,-1,null);
        } else if (list.size() == 1) {
            MyVertex min = list.remove(0);
            return min;
        }

        // remove the last item ,and set it as new root
        MyVertex min = list.get(0);
        MyVertex lastItem = list.remove(list.size() - 1);
        list.set(0, lastItem);

        // bubble-down until heap property is maintained
        minHeapify(0);

        // return min key
        return min;
    }

    private void minHeapify(int i) {

        int left = left(i);
        int right = right(i);
        int smallest = -1;

        // find the smallest key between current node and its children.
        if (left <= list.size() - 1 && list.get(left).f < list.get(i).f) {
            smallest = left;
        } else {
            smallest = i;
        }

        if (right <= list.size() - 1 && list.get(right).f < list.get(smallest).f) {
            smallest = right;
        }

        // if the smallest key is not the current key then bubble-down it.
        if (smallest != i) {

            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    public MyVertex getMin() {

        return list.get(0);
    }

    public boolean isEmpty() {

        return list.size() == 0;
    }

    private int right(int i) {

        return 2 * i + 2;
    }

    private int left(int i) {

        return 2 * i + 1;
    }

    private int parent(int i) {

        if (i % 2 == 1) {
            return i / 2;
        }

        return (i - 1) / 2;
    }

    private void swap(int i, int parent) {
        MyVertex temp = list.get(parent);
        list.set(parent, list.get(i));
        list.set(i, temp);
    }
}
