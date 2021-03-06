import java.awt.*;
import java.util.Stack;

public class AlgoVisualizer {
    //刷新频率和砖块大小
    private static int DELAY = 20;
    private static int blockSide = 8;
    //初始化数据和视图,这个类是控制层，底层修改全在数据层中进行
    private MazeData data;
    private AlgoFrame frame;
    //寻路的四个方向
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

        Stack<Position> stack = new Stack<Position>();
        Position entrance = new Position(data.getEntranceX(),data.getEntranceY());
        stack.push(entrance);
        data.visited[entrance.getX()][entrance.getY()]=true;
        //如果栈空了，说明找不到出口，为了看起来更直观加入布尔变量isSolved
        boolean isSolved=false;
        while(!stack.empty()){
            Position curPos=stack.pop();
            setData(curPos.getX(), curPos.getY(), true);

            if(curPos.getX()==data.getExitX()&&curPos.getY()==data.getExitY()){
                isSolved=true;
                findPath(curPos);
                break;
                
            }
            
            for(int i=0;i<4;i++){
                int newX=curPos.getX()+d[i][0];
                int newY=curPos.getY()+d[i][1];

                if(data.inArea(newX, newY)
                        && !data.visited[newX][newY]
                        && data.getMaze(newX, newY)==MazeData.RODA){
                    stack.push(new Position(newX, newY,curPos));
                    data.visited[newX][newY]=true;
                }
            }
        }

        if(!isSolved){
            System.out.println("The maze has no solution!");
        }

        setData(-1,-1,false);
    }

    private void findPath(Position des){

        Position cur=des;
        while(cur!=null){
            data.result[cur.getX()][cur.getY()]=true;
            cur=cur.getPrev();
        }

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