import java.awt.*;

public class AlgoVisualizer {

    private static int DELAY = 8;
    private static int blockSide = 8;

    private MazeData data;
    private AlgoFrame frame;

    private static final int d[][]={{-1,0},{0,1},{1,0},{0,-1}};

    public AlgoVisualizer(String mazeFile){

        // 初始化数据
        data = new MazeData(mazeFile);
        int sceneHeight = data.N() * blockSide;
        int sceneWidth = data.M() * blockSide;

        // 初始化视图
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze Solver Visualization", sceneWidth, sceneHeight);

            new Thread(() -> {
                run();
            }).start();
        });
    }

    public void run(){


        setData(-1,-1,false);

        if(!go(data.getEntranceY(),data.getEntranceY()))
            System.out.println("maze has no solution!");

        setData(-1,-1,false);
    }

    //从x，y开始走迷宫如果成功返回true
    private boolean go(int x,int y){
        if(!data.inArea(x, y))
            throw new IllegalArgumentException("(x,y) not in this maze!");
        
        data.visited[x][y]=true;
        setData(x,y,true);

        if(x==data.getExitX() && y==data.getExitY())
            return true;

        for(int i=0;i<4;i++){
            int newX=x+d[i][0];
            int newy=y+d[i][1];
            if(data.inArea(newX, newy)&&
                    data.getMaze(newX, newy)==MazeData.RODA&&
                    !data.visited[newX][newy])
                if(go(newX,newy))
                    return true;    
        }

        setData(x, y,false);
        return false;
    }

    private void setData(int x,int y,boolean isPath){
        if(data.inArea(x, y)){
            data.path[x][y]=isPath;
        }
        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }

    public static void main(String[] args) {

        String mazeFile = "maze_101_101.txt";

        AlgoVisualizer vis = new AlgoVisualizer(mazeFile);

    }
}