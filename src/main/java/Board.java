
import java.awt.Color;
import java.util.Random;
import java.util.Scanner;

public class Board {

    Scanner s = new Scanner(System.in);
    public static int Length;
    public static int Width;
    static int length = Length;
    static int width = Width;
    private int count = 0;

    public int getCount() {
        return count;
    }

    private boolean showall = false;
    private Cell[][] board;
    private boolean game_over = false;

    public boolean isGame_over() {
        return game_over;
    }

    public int[][] getArr_index() {
        return arr_index;
    }
    private int arr_index[][]; // Any parameters...

    public boolean all_press() {
        int temp_count = 0;
        for (int i = 0; i < this.Length; i++) {
            for (int j = 0; j < this.Width; j++) {
                if (this.board[i][j].getShow() > 0 || this.board[i][j].isSelect() && this.board[i][j].isMine() == false) {
                    temp_count++;
                }
            }
        }
        if (temp_count == (this.Length * this.Width) - countmines) {
            return true;
        }
        return false;
    }

    public void Board(int Length, int Width) {
        this.Length = Length;
        this.Width = Width;
        this.board = new Cell[Length][Width];
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                this.board[i][j] = new Cell();
                this.board[i][j].setBackground(new Color(139, 219, 127));
            }
        }
        Start_arr_index();

    }  //Creat the board game int length X int length.

    void Start_arr_index() {
        this.arr_index = new int[2][this.Length * this.Width];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.Length * this.Width; j++) {
                arr_index[i][j] = 999;
            }
        }
    }  // This restart the array of indexs 999, this is stop the recursy in the next function.
    int z = 0;
    int m = 0;
    int temp_index = 0;
    int tempI = 0;
    int tempcount = 0;
    int tempJ = 0;
    int countmines = 0;
    int countflages = 0;
    int how_many_mines = 0;

    //GETTER AND SETTER
    public Cell[][] getboard() {
        return board;
    }

    public boolean isShowall() {
        return showall;
    }

    public void setShowall(boolean showall) {
        this.showall = showall;
    }

    public int getLength() {
        return Length;
    }

    public int getWidth() {
        return Width;
    }

    public int All_Mines() {//counter mines
        for (int i = 0; i < this.Length; i++) {
            for (int j = 0; j < this.Width; j++) {
                if (this.board[i][j].isMine()) {
                    countmines++;
                    countflages++;
                }
            }
        }
        return countmines;
    }

    boolean if_in_arr(int i, int j) {
        if (i >= 0 && i < this.board.length && j >= 0 && j < this.board[1].length) {
            return true;
        }
        return false;
    } //Checking if the local in arr.

    void Put_Mines(int num_of_mines) {
        Random rnd = new Random();
        for (int k = 0; k < num_of_mines; k++) {
            this.board[rnd.nextInt(this.Length)][rnd.nextInt(this.Width)].setMine(true);
        }
    }

    boolean if_not_in_arr_index(int i, int j, int[][] arr) {
        if (if_in_arr(i, j)) {
            for (int l = 0; l < count; l++) {
                if (arr[0][l] == i && arr[1][l] == j) {
                    return false;
                }
            }
            arr[0][count] = i;
            arr[1][count] = j;
            count++;
            return true;
        }
        return false;
    } // Enter to the arr of index just if not found here.

    int NumMines(int i, int j) {
        int count_mines = 0;
        int tempi = i - 1;
        int tempj = j - 1;
        int ii = i + 1;
        int jj = j + 1;
        for (int k = tempi; k <= ii; k++) {
            for (int t = tempj; t <= jj; t++) {
                if (if_in_arr(k, t)) {
                    if (k == i && t == j) {
                        continue;
                    }
                    if (this.board[k][t].isMine()) {
                        count_mines++;
                    }
                }
            }
        }
        this.board[i][j].setShow(count_mines);
        if (this.board[i][j].isMine() == false) {
            this.board[i][j].setSelect(true);
        }
        return count_mines;
    }  //Number MINES aroun local.

    void Move(int i, int j, int[][] arr) {
        if (i == 999) {
            return;
        }
        NumMines(i, j);
        if (this.board[i][j].getShow() == 0) {
            if_not_in_arr_index(i, j, arr_index);//local
            if_not_in_arr_index(i, j + 1, arr_index);//right
            if_not_in_arr_index(i, j - 1, arr_index);//left
            if_not_in_arr_index(i + 1, j, arr_index);//down
            if_not_in_arr_index(i - 1, j, arr_index);//up
            if_not_in_arr_index(i - 1, j - 1, arr_index);//up and left
            if_not_in_arr_index(i - 1, j + 1, arr_index);//up and right
            if_not_in_arr_index(i + 1, j + 1, arr_index);//down and right
            if_not_in_arr_index(i + 1, j - 1, arr_index);//down and left
        } else {
            if_not_in_arr_index(i, j, arr_index);//local

        }

        m++;
        Move(arr_index[0][m], arr_index[1][m], arr_index);
    } //Recursy function make move- the basic of game is here.

    void Press(int i, int j, int right_clik, int left_clik) {
        if (right_clik == 1) {// אם נלחץ מקש ימני  
            if (this.board[i][j].isFlag()) {//אם יש דגל
                this.board[i][j].setShow(-1);
                this.board[i][j].setFlag(false);
                this.countflages++;
            } else {
                this.board[i][j].setFlag(true);
                this.board[i][j].setShow(-1);
                this.countflages--;
            }
        }

        if (this.board[i][j].isMine() && left_clik == 1) {// אם נלחץ במקש שמאלי ויש מוקש נגמר משחק
            this.game_over = true;
            return;
        }
        if (left_clik == 1 && this.board[i][j].isMine() == false) {//אחרת אם אין מוקש ומקש ימני או שמאלי נלחצו תראה כמה מוקשים יש סביב הכפתור שנלחץ
            Move(i, j, arr_index);
            for (int k = 0; k < count; k++) {
                NumMines(arr_index[0][k], arr_index[1][k]);
                if (this.board[arr_index[0][k]][arr_index[1][k]].getShow() == 0) {
                    this.board[arr_index[0][k]][arr_index[1][k]].setSelect(true);//אם נלחץ

                }
            }

        }

    }  // Press on button...

}
