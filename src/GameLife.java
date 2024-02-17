import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class GameLife {

    static int N;

    static boolean[][] board;
    static HashMap<String, Buffer> buffers = new HashMap<String, Buffer>();
    static Cell[][] cellsInform;
    static Cell[][] cellsReceive;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        GameLife.board = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            String[] line = br.readLine().split(",");
            for (int j = 0; j < N; j++) {
                GameLife.board[i][j] = Boolean.parseBoolean(line[j]);
            }
        }

        int turns = Integer.parseInt(br.readLine());

        System.out.println("Turno 0");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(GameLife.board[i][j] ? "*" : "-");
                if (j != N - 1)
                    System.out.print(" ");
            }
            System.out.println();
        }

        for (int i = 0; i < turns; i++) {
            System.out.println("Turno " + (i + 1));
            GameLife.cellsInform = new Cell[N][N];
            GameLife.cellsReceive = new Cell[N][N];

            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    Buffer buffCell = new Buffer(k);
                    GameLife.buffers.put(j + "," + k, buffCell);
                    GameLife.cellsInform[j][k] = new Cell(j, k, GameLife.board[j][k], true, buffCell);
                    GameLife.cellsReceive[j][k] = new Cell(j, k, GameLife.board[j][k], false, buffCell);
                }
            }

            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    GameLife.cellsInform[j][k].start();
                    GameLife.cellsReceive[j][k].start();

                }
            }

            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    GameLife.cellsInform[j][k].join();
                    GameLife.cellsReceive[j][k].join();
                }
            }

            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    System.out.print(GameLife.board[j][k] ? "*" : "-");
                    if (k != N - 1)
                        System.out.print(" ");
                }
                System.out.println();
            }
        }

    }
}
