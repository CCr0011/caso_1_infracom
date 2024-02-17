public class Cell extends Thread {
  private int row;
  private int col;
  private boolean status;
  private Buffer buff;
  private boolean informs;
  private int neighbors = 0;

  public Cell(int row, int col, boolean initialStatus, boolean informs, Buffer buff) {
    this.row = row;
    this.col = col;
    this.status = initialStatus;
    this.buff = buff;
    this.informs = informs;

    for (int i = Math.max(0, row - 1); i <= Math.min(GameLife.N - 1, row + 1); i++) {
      for (int j = Math.max(0, col - 1); j <= Math.min(GameLife.N - 1, col + 1); j++) {
        if (i != row || j != col) {
          neighbors++;
        }
      }
    }
  }

  public void run() {
    if (informs) {
      for (int i = Math.max(0, row - 1); i <= Math.min(GameLife.N - 1, row + 1); i++) {
        for (int j = Math.max(0, col - 1); j <= Math.min(GameLife.N - 1, col + 1); j++) {
          if (i != row || j != col) {
            GameLife.buffers.get(i + "," + j).add(status);
          }
        }
      }
    } else {

      int count = 0;

      int liveCells = 0;

      while (count != neighbors) {
        boolean life = buff.remove();
        if (life) {
          liveCells++;
        }
        count++;
      }

      if (!status && liveCells == 3) {
        status = true;
        GameLife.board[row][col] = true;
      } else if (status && (liveCells == 0 || liveCells > 3)) {
        status = false;
        GameLife.board[row][col] = false;
      }
    }
  }

}
