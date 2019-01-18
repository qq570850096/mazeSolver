import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class MazeData{

    private int N,M;
    private char[][] maze;
    public static final char WALL='#';
    public static final char RODA=' ';

    private int entranceX,entranceY;
    private int exitX,exitY;
    public boolean[][] visited;
    //标记路径
    public boolean[][] path;
    
    public MazeData(String filename){
        if(filename==null)
            throw new IllegalArgumentException("file name is null!");
        Scanner scanner=null;
        try{
            File file = new File(filename);
            if(!file.exists())
                throw new IllegalArgumentException("File " + filename + " doesn't exist");

            FileInputStream fis=new FileInputStream(file);
            scanner =new Scanner(new BufferedInputStream(fis),"utf-8");
            //读取第一行
            String nmline=scanner.nextLine();
            String[] nm = nmline.trim().split("\\s+");
            N=Integer.parseInt(nm[0]);
            M=Integer.parseInt(nm[1]);

            maze = new char[N][M];
            //布尔数组默认初始值为false
            visited=new boolean[N][M];
            path=new boolean[N][M];

            for(int i=0;i<N;i++){
                String line =scanner.nextLine();

                //保证每行有M个字符
                if(line.length()!=M)
                    throw new IllegalArgumentException("Maze file"+ filename +"error");
                
                for(int j=0;j<M;j++){
                    maze[i][j]=line.charAt(j);
                }
            }


        }catch(IOException e){
            e.printStackTrace();

        }
        finally{
            if(scanner!=null)
                scanner.close();
        }    
        entranceX=1;
        entranceY=0;
        exitX=N-2;
        exitY=M-1;
    }

    public int N(){return N;}
    public int M(){return M;}
    public int getEntranceX(){return entranceX;}
    public int getEntranceY(){return entranceY;}
    public int getExitX(){return exitX;}
    public int getExitY(){return exitY;}

    public char getMaze(int i,int j){
        if(!inArea(i, j))
            throw new IllegalArgumentException("i or j out of index in getMaze");
        return maze[i][j];
    }
    public boolean inArea(int x,int y){
        return x>=0 && x<N &&y>=0&&y<M;
    }

    public void print(){
        System.out.println(N+" "+M);
        
        for(int i=0;i<N;i++){
            for(int j=0;j<M;j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        return;

    }
}