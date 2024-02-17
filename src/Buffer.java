import java.util.ArrayList;

public class Buffer {
  private int capacity;
  private ArrayList<Boolean> buff;

  public Buffer(int row) {
    this.capacity = row + 1;
    this.buff = new ArrayList<Boolean>();
  }

  public synchronized void add(Boolean status) {
    while (buff.size() == capacity) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    buff.add(status);

    notifyAll();
  }

  public synchronized Boolean remove() {
    while (buff.size() == 0) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    Boolean status = buff.remove(0);

    notifyAll();

    return status;
  }
}
