import java.io.BufferedReader;
import java.io.FileInputStream;
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
        System.out.print("Ingrese el nombre del archivo del estado inicial: ");
        String fileName = br.readLine();

        BufferedReader fileR = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        N = Integer.parseInt(fileR.readLine());

        GameLife.board = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            String[] line = fileR.readLine().split(",");
            for (int j = 0; j < N; j++) {
                GameLife.board[i][j] = Boolean.parseBoolean(line[j]);
            }
        }

        fileR.close();

        System.out.print("Ingrese el número de turnos: ");

        int turns = Integer.parseInt(br.readLine());

        br.close();

        System.out.println("\nTurno 0");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(GameLife.board[i][j] ? "*" : "-");
                if (j != N - 1)
                    System.out.print(" ");
            }
            System.out.println();
        }

        for (int i = 0; i < turns; i++) {
            System.out.println("\nTurno " + (i + 1));
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
