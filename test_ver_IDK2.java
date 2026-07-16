import java.util.InputMismatchException;
import java.util.Scanner;

class Board {

    public int SIZE = 15;//棋盤大小
    private int[][] grid=null;//預計增加更改棋盤設定
    int[] stonesColors = {0x25CF,0x25CB,0x25A0,0x25A01,0x2605,0x2606};//棋子種類
    String chooseColorB = String.valueOf((char)stonesColors[0]),chooseColorW = String.valueOf((char)stonesColors[1]);//設定初始棋子種類
    //char s ='\u26f6';
    public Board() {//建構方法 讓grid接收SIZE的值
        grid = new int[SIZE][SIZE];  
    }
    public void StonesColors(int a){//調整棋子樣式的方法
        //a =1、2 or 3
        a=(a-1)*2;//stoneColors中，以(0,1)(2,3)(4,5)各為一組
        //a = (1-1)*2 = 0 ，(2-1)*2 = 2 ，(3-1)*2 =4 即為每組的黑棋
        chooseColorB=String.valueOf((char)stonesColors[a]);
        chooseColorW=String.valueOf((char)stonesColors[a+1]);//a+1 = 白棋
    }
    
    public void place(Stone stone) {//建立方法可以輸出棋子樣式
        grid[stone.getX()][stone.getY()] = stone.getColor();
    }

    public int[][] getGrid() {//建立方法取得棋盤內容
        return grid;
    }
    
    public void show() {//刷新棋盤
        for (int i=0,k=0,y = 0; y < SIZE; y++) {
            i++;//棋盤旁邊的數字
            if(y<9)//為了讓棋盤看起來整齊
                System.out.print((char) (0xFF10 + i)+"　");
            else {
                System.out.print((char)0xFF11+""+(char) (0xFF10 + (i-10)));
                k++;
                }
            for (int x = 0; x < SIZE; x++) {//輸出棋子與格線至棋盤內容
                
                switch (grid[x][y]) {//判斷是黑棋or白棋
                    case 1:
                        System.out.print("　 "+chooseColorB);//+ 替換為黑棋
                        break;
                    case 2:
                        System.out.print("　 "+chooseColorW);//+ 替換為白棋
                        break;
                    default:
                        System.out.print("　＋");//沒有棋子
                        break;
                }
            }
            System.out.println();
        }
        System.out.print("　　");//讓英文與棋盤對齊
        for(int x=0;x<SIZE;x++) {//輸出棋盤下方的英文
            //全形A的ascll值 = 65313 、半形A的ascll值 = 65
            char a=(char)(65313+x);//轉換為字元
            System.out.print("　"+a);
        }
    }
    
    public boolean checkWin() {//檢查勝利條件
        int[][] grid = this.getGrid();//取得目前棋盤狀態
        int count;//是否有連線成功
        
        //檢查水平層
        for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE - 4; x++) {//外層迴圈是用來預覽整個棋盤
                count = 0;
                for (int i = 0; i < 5; i++) {
                    if (grid[x + i][y] == 1) {
                        count++;//同為黑棋，計數+1
                    }
                }
                if (count == 5) {//如果有5顆同在水平層上
                    return true;//結束判斷
                }
                //否則重新找棋子
                count = 0;//計數歸零
                for (int i = 0; i < 5; i++) {
                    if (grid[x + i][y] == 2) {
                        count++;//同為白棋，計數+1
                    }
                }
                if (count == 5) {
                    return true;
                }
            }
        }


        //檢查垂直
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE - 4; y++) {
                count = 0;
                for (int i = 0; i < 5; i++) {
                    if (grid[x][y + i] == 1) {
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
                count = 0;
                for (int i = 0; i < 5; i++) {
                    if (grid[x][y + i] == 2) {
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
            }
        }

        //檢查斜線(1)
        for (int x = 0; x < SIZE - 4; x++) {
            for (int y = 0; y < SIZE - 4; y++) {
                count = 0;
                for (int i = 0; i < 5; i++) {
                    if (grid[x + i][y + i] == 1) {//左上 至 右下
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
                count = 0;
                for (int i = 0; i < 5; i++) {
                    if (grid[x + i][y + i] == 2) {
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
            }
        }
        
        //檢查斜線(2)
        for (int x = 0; x < SIZE - 4; x++) {
            for (int y = 4; y < SIZE; y++) {
                count = 0;
                for (int i = 0; i < 5; i++) {
                    if (grid[x + i][y - i] == 1) {//左下 至 右上
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
                count = 0;
                for (int i = 0; i < 5; i++) {
                    if (grid[x + i][y - i] == 2) {
                        count++;
                    }
                }
                if (count == 5) {
                    return true;
                }
            }
        }
        return false;//如果以上都不符，則代表尚未分出勝負
    }
}

class Stone {
    protected static int stepB=0;//黑棋總步數
    protected static int stepW=0;//白棋總步數
    protected static int step=0;//總步數
    private int x,y,color;//color >> 1=black 2=white

    public Stone(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
        step++;
        if(color==1)
            stepB++;
        else
            stepW++;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }
    public String getStep() {
        return "\r\n總共下了："+step+"步\r\n黑棋總共下了："+stepB+"\t白棋總共下了："+stepW;
    }

    public static void resetSteps() {
        stepB=0;
        stepW=0;
        step=0;
    }
}

public class test_ver_IDK2 {
    public static void main(String[] args){//最一開始的畫面
        long time1=0;//建立時間變數
        String[] currentPlayer = new String[2];//建立兩格空間(黑棋玩家、白棋玩家)
        System.out.println("開始遊戲？\r\n1：yes  任意鍵：將有驚喜並結束遊戲");
        Scanner scn = new Scanner(System.in);
        
        try {
            int startOrJump = Integer.parseInt(scn.nextLine());
            while (startOrJump == 1) { // 輸入 1 開始遊戲
                Stone.resetSteps();//reset
                Board board = new Board();//選擇棋子樣式物件 stonesColors = {"●"，"○"，"■"，"□"，"★"，"☆"};
                /*參考資料
                 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
                 * */
                System.out.format("\33[31m開始遊戲！\33[0m \r\n");   //更改顏色\33 = (char)27 = 紅色、[4m = 加底線 [7m = 填滿背景
                System.out.println("\r\n你要換棋子形狀嗎？\r\n1：要 \t 任意鍵：不要");
                
                int choose = Integer.parseInt(scn.nextLine());
                board = changeStone(choose,board);
                
                System.out.print("黑子棋手名字：");
                currentPlayer[0] = scn.nextLine();
                
                System.out.print("白子棋手名字：");
                currentPlayer[1]= scn.nextLine();
                
                time1=System.currentTimeMillis();//create a time 
                board.show();
                int stoneColor = gameLogic(1,1,2,board,currentPlayer); //int x=1,y=1,stoneColor=2;
                System.out.println("\r\n\r\n\r\n"+currentPlayer[stoneColor-1] + endGame(time1));
                System.out.println("再玩一次？\r\n1：yes  任意鍵：將有驚喜並結束遊戲");
                startOrJump = Integer.parseInt(scn.nextLine()); 
            }
        } catch (NumberFormatException | InputMismatchException e) {}
        finally{closeProject();};
    }

    public static Board changeStone(int choose ,Board board){
        int shapeChoice = 1;
        if (choose == 1){
            Scanner sC = new Scanner(System.in);
            do{
                System.out.println(
                    "【輸入 1 到 3 之間的數字】\r\n"  +
                    "你喜歡哪個類型：\r\n"                  +
                    "1：( ● ， ○ ) (預設)\r\n"              +
                    "2：( ■ ， □ )\r\n"                           +
                    "3：( ★ ， ☆ )\r\n"
                );
                try {
                    shapeChoice = Integer.parseInt(sC.nextLine()); 
                }catch(NumberFormatException e) {   //抓錯
                    System.out.println("請輸入數字\r\n1：( ● ， ○ ) (預設)\r\n2：( ■ ， □ )\r\n3：( ★ ， ☆ )");
                    // sC.nextInt() 搭配 sC.next();，吃掉留下的垃圾字串
                    // 因為用了 sC.nextLine()，整行已被讀走並消耗掉，緩衝區已經乾淨
                }
            }while(shapeChoice<=0 || shapeChoice>3);
        }
        board.StonesColors(shapeChoice);
        return board;
    }

    public static int gameLogic(int x ,int y ,int stoneColor,Board board,String[] currentPlayer){
        Stone currentStone = null ; //呼叫(x，y，color)
        do {
            stoneColor = (stoneColor==2?1:2); //條件式 ? 成立時的值 : 不成立時的值
            
            // 由1開始
            System.out.println("\r\n"+currentPlayer[stoneColor-1] + " go go ");
            System.out.println("輸入的格式為：一個英文字母\t一個數字\r\n範例：M 5");
            boolean validInput = false;
            Scanner sc = new Scanner(System.in);
            do {//檢查落子
                String[] coordinates = sc.nextLine().trim().split(" ");
                
                if(coordinates.length != 2) {//判斷輸入是否為 (a b)
                    System.out.println("輸入格式不正確，請重新輸入\r\n格式範例：M 5");
                    continue;
                }
               
                String sx = coordinates[0].toUpperCase();//自動轉換大小寫
                String sy = coordinates[1];
                char c = sx.charAt(0);//取第一個位置
                
                if (sx.length() != 1 || !Character.isLetter(c)) {
                    System.out.println("您輸入的X必須為單個英文字母，請重新輸入\r\n格式範例：M 5");
                    continue;
                }
                
                x = c - 64;//由char轉換為integer
                y = 0;//本來就是數字所以維持
                
                try {
                    y = Integer.parseInt(sy);
                } catch(NumberFormatException e) {//判定y格式為數字
                    System.out.println("您輸入的Y必須為數字，請重新輸入\r\n格式範例：M 5");
                    continue;
                }
                
                if (x < 1 || x > board.SIZE || y < 1 || y > board.SIZE) {//判定格式是否超過
                    continue;
                }
                
                if (board.getGrid()[x-1][y-1] != 0) {//判定格式是否重複
                    System.out.println("該棋點已有棋子了，請重新輸入\r\n格式範例：M 5");
                    continue;
                }
                
                validInput = true;
                currentStone = new Stone(x-1, y-1, stoneColor);
            }while (!validInput);

            board.place(currentStone);
            System.out.println(currentStone.getStep());
            board.show();
        }while (!board.checkWin());
        return stoneColor;
    }
    
    public static String endGame(long time1) { // 1. 修正：加上 long 型態
        long time2 = System.currentTimeMillis(); // 2. 修正：加上 long 型態
        long totaltime = (time2 - time1) / 1000;
        
        // 3. 完美時間換算公式（完全不需 if-else 分流）
        long hour = totaltime / 3600;             // 總秒數除以 3600 得到小時
        long min = (totaltime % 3600) / 60;       // 不足一小時的秒數，除以 60 得到分鐘
        long sec = totaltime % 60;                // 不足一分鐘的秒數，就是剩餘的秒數
        
        String talk = " ";

        // 根據總秒數給予評語
        if (totaltime <= 120) {
            talk = "我們很看不起挑戰能力比自己弱太多的對手";
        }
        
        // 4. 修正：補上結尾的分號
        return " 贏了！" + "使用時間 " + hour + " 時 " + min + " 分 " + sec + " 秒    " + talk;
    }
    
    public static void closeProject(){
        int sleepTime=2000;
        String[] word = {"(這世界是沒有後悔的藥的，所以別想中斷倒數)",
                "(你知道嗎...ㄜ我要說啥來著？)",
                "(GUI...好難)",
                "(要猜猜我到底有幾句話嗎？)",
                "(五子棋的盤面是用我們的肝換的；五子棋的棋是用我們的san值換的 十分符合等價交換)",
                "⣿⣿⣿⠟⠛⠛⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⢋⣩⣉⢻⣿⡇\r\n"
                + "⣿⣿⣿⠀⣿⣶⣕⣈⠹⠿⠿⠿⠿⠟⠛⣛⢋⣰⠣⣿⣿⠀⣿⣿\r\n"
                + "⣿⣿⣿⡀⣿⣿⣿⣧⢻⣿⣶⣷⣿⣿⣿⣿⣿⣿⠿⠶⡝⠀⣿⣿\r\n"
                + "⣿⣿⣿⣷⠘⣿⣿⣿⢏⣿⣿⣋⣀⣈⣻⣿⣿⣷⣤⣤⣿⡐⢿⣿\r\n"
                + "⣿⣿⣿⣿⣆⢩⣝⣫⣾⣿⣿⣿⣿⡟⠿⠿⠦⠀⠸⠿⣻⣿⡄⢻\r\n"
                + "⣿⣿⣿⣿⣿⡄⢻⣿⣿⣿⣿⣿⣿⣿⣿⣶⣶⣾⣿⣿⣿⣿⠇⣼\r\n"
                + "⣿⣿⣿⣿⣿⣿⡄⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⣰⣿\r\n"
                + "⣿⣿⣿⣿⣿⣿⠇⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢀⣿⣿\r\n"
                + "⣿⣿⣿⣿⣿⠏⢰⣿⣿⣿⣿POP⣿⣿CAT⣿⣿⢸⣿⡇\r\n"
                + "⣿⣿⣿⣿⠟⣰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⣿⣿\r\n"
                + "⣿⣿⣿⠋⣴⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡄⣿⣿\r\n"
                + "⣿⣿⠋⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⢸⣿",
                "⣿⣿⣿⣿⣿⣿⠟⠋⠄⠄⠄⠄⠄⠄⠄⢁⠈⢻⢿⣿⣿⣿⣿⣿⣿⣿⡇\r\n"
                + "⣿⣿⣿⣿⣿⠃⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠈⡀⠭⢿⣿⣿⣿⣿⣿⡇\r\n"
                + "⣿當⣿⡟⠄⢀⣾⣿⣿⣿⣷⣶⣿⣷⣶⣶⡆⠄⠄⠄⣿⣿⣿⣿⣿⣿\r\n"
                + "⣿你⣿⡇⢀⣼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠄⠄⠄⢸凝視深淵\r\n"
                + "⣿⣿⣿⣿⣇⣼⣿⣿⠿⠶⠙⣿⡟⠡⣴⣿⣽⣿⣧⠄⢸⣿⣿⣿⣿⣿⡇\r\n"
                + "⣿深淵⣿⣿⣾⣿⣿⣟⣭⣾⣿⣷⣶⣶⣴⣶⣿⣿⢄⣿⣿⣿⣿⣿⡇\r\n"
                + "⣿⣿⣿⣿⣿⣿⣿⣿⡟⣩⣿⣿⣿⡏⢻⣿⣿⣿⣿害羞地⣿⣿⣿\r\n"
                + "⣿⣿⣿⣿⣿⣿⣹⡋⠘⠷⣦⣀⣠⡶⠁⠈⠁⠄⣿⣿別過了頭⣿\r\n"
                + "⣿⣿⣿⣿⣿⣿⣍⠃⣴⣶⡔⠒⠄⣠⢀⠄⠄⠄⡨⣿⣿⣿⣿⣿⣿⣿\r\n"
                + "⣿⣿⣿⣿⣿⣿⣿⣦⡘⠿⣷⣿⠿⠟⠃⠄⠄⣠⡇⠈⠻⣿⣿⣿⣿⣿\r\n"
                + "⣿⣿⣿⣿⡿⠟⠋⢁⣷⣠⠄⠄⠄⠄⣀⣠⣾⡟⠄⠄⠄⠄⠉⠙⠻⣿\r\n"
                + "⡿⠟⠋⠁⠄⠄⠄⢸⣿⣿⡯⢓⣴⣾⣿⣿⡟⠄⠄⠄⠄⠄⠄⠄⠄⠄\r\n"
                + "⠄⠄⠄⠄⠄⠄⠄⣿⡟⣷⠄⠹⣿⣿⣿⡿⠁⠄⠄⠄⠄⠄⠄⠄⠄⠄",
                "　　　　 　　 ＿＿＿\r\n"
                + "　　　 　　／ ＞　  フ \r\n"
                + "　　　 　　| 　_　 _ |\r\n"
                + "　 　　 　／` ミ＿xノ \r\n"
                + "　　 　 /　　　 　 | \r\n"
                + "　　　 /　 ヽ　　 ﾉ \r\n"
                + "　 　 │　　|　|　| \r\n"
                + "　／￣|　　 |　|　| \r\n"
                + "　| (￣ヽ＿_ヽ_)__) \r\n"
                + "　＼二つ 偷渡貓貓"};
        try {
            int rNum = (int)(Math.random()*word.length);
            System.out.println(sleepTime/1000+"秒後結束遊戲\r\n");
            char wordSpilt;
                for(int i=0;i<word[rNum].length();i++) {
                    wordSpilt = word[rNum].charAt(i);
                    System.out.print(wordSpilt);
                    Thread.sleep((sleepTime*3)/(word[rNum].length()*4));
                }
            sleepTime -= sleepTime/(word[rNum].length()*4);
            Thread.sleep(sleepTime);
        }catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }
}
