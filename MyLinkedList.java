import java.util.NoSuchElementException;

public class MyLinkedList {
    /*******************************************************/
    class Node {
        private long data;
        private Node next;
        public Node(long data, Node next) {
            this.data = data;
            this.next = next;
        }
        public String toString() {
            return "" + this.data;
        }
    }
    /********************************************************/

    private Node head;
    private int size;

    public MyLinkedList() {
        head = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    // Returns true if the list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Inserts a new node at the beginning of this list
    public void addFirst(long item) {
        head = new Node(item, head);
        size++;
    }

    // Inserts a new node at the end of this list
    public void addLast(long item) {
        if (isEmpty()) {
            addFirst(item);
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            // Now current points to the last node
            current.next = new Node(item, null);
            size++;
        }
    }

    // Returns the first element (data) in the list
    public long getFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return head.data;
    }

    // Returns the last element (data) in the list
    public long getLast() {
        if (head == null) throw new NoSuchElementException();
        Node tmp = head;
        while (tmp.next != null) {
            tmp = tmp.next;
        }
        return tmp.data;
    }

    // Returns a reference to the Node at the given position (0-indexed)
    public Node get(int pos) {
        if (head == null) throw new IndexOutOfBoundsException();
        Node tmp = head;
        for (int k = 0; k < pos; k++) {
            tmp = tmp.next;
            if (tmp == null) throw new IndexOutOfBoundsException();
        }
        return tmp;
    }

    // Converts the linked list into an array of long values
    public long[] toArray() {
        if (head == null) throw new IndexOutOfBoundsException();
        long[] result = new long[getSize()];
        int i = 0;
        for (Node tmp = head; tmp != null; tmp = tmp.next) {
            result[i++] = tmp.data;
        }
        return result;
    }

    // Removes and returns the first element (data) in the list.
    public long removeFirst() {
        long temp = getFirst();
        head = head.next;
        size--;
        return temp;
    }

    // Removes the first occurrence of the specified element in this list.
    public void remove(long key) {
        if (head == null) throw new RuntimeException("cannot delete");

        if (head.data == key) {
            head = head.next;
            size--;
            return;
        }
        Node cur = head;
        Node prev = null;
        while (cur != null && cur.data != key) {
            prev = cur;
            cur = cur.next;
        }
        if (cur == null) throw new RuntimeException("cannot delete");
        // Remove cur node by adjusting the previous node's next pointer
        prev.next = cur.next;
        size--;
    }

    // Returns a string representation of the list
    public String toString() {
        if (head == null) throw new NoSuchElementException();
        StringBuilder output = new StringBuilder();
        Node tmp = head;
        while (tmp != null) {
            output.append(tmp.data).append(" -> ");
            tmp = tmp.next;
        }
        output.append("[NULL]");
        return output.toString();
    }

    //-------------------------------------------------------------------------
    //----- MergeSort algorithm to sort the nodes in this linked list -----
    //-------------------------------------------------------------------------
    // Find the middle node of the list starting at 'node'
    public Node getMiddle(Node node) {
        if (node == null) {
            return null;
        }
        Node slow = node;
        Node fast = node;
        // Move fast pointer two steps and slow pointer one step until fast reaches end
        while (fast != null && fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // slow now points to the middle node (for even length, to the end of the first half)
        return slow;
    }

    // Public mergeSort method to sort the entire list
    public void mergeSort() {
        // Sort the linked list using merge sort and update head to new sorted head
        head = sort(head);
    }

    private Node sort(Node node) {
        // Recursively split and sort the list starting from node
        if (node == null || node.next == null) {
            // Base case: 0 or 1 node is already sorted
            return node;
        }
        // Find middle and split the list into two halves
        Node middle = getMiddle(node);
        Node secondHalfStart = middle.next;
        middle.next = null;  // break the list into two parts
        // Recursively sort each half
        Node leftSorted = sort(node);
        Node rightSorted = sort(secondHalfStart);
        // Merge the sorted halves and return the merged list's head
        return merge(leftSorted, rightSorted);
    }

    // Merge two sorted linked lists (left and right) and return the head of the merged list
    public Node merge(Node left, Node right) {
        // If one list is empty, return the other
        if (left == null) return right;
        if (right == null) return left;
        // Use a dummy head to simplify merging
        Node dummy = new Node(0, null);
        Node tail = dummy;
        // Iterate through both lists, linking the smaller node each time
        while (left != null && right != null) {
            if (left.data <= right.data) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }
        // Attach any remaining nodes (at most one list is non-empty now)
        if (left != null) {
            tail.next = left;
        } else if (right != null) {
            tail.next = right;
        }
        // Return the head of the merged list (skip dummy node)
        return dummy.next;
    }

    public static void main(String[] args) {
        // Simple test for merge sort on linked list
        MyLinkedList list = new MyLinkedList();
        list.addLast(3);
        list.addLast(2);
        list.addLast(8);
        list.addLast(10);
        list.addLast(5);
        System.out.println("Before list.mergeSort():");
        System.out.println(list);
        list.mergeSort();
        System.out.println("\nAfter list.mergeSort():");
        System.out.println(list);
    }
}
