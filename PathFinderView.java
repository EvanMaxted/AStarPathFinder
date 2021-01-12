import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

public class PathFinderView extends Pane
{
    private AStar model;
    //colors for paths at different weights
    private final static Color[] colorWeight = {
            Color.rgb(250,250,250),Color.rgb(225,225,225), Color.rgb(200,200,200),
            Color.rgb(175,175,175),Color.rgb(150,150,150),Color.rgb(125,125,125),
            Color.rgb(100,100,100),Color.rgb(75,75,75),Color.rgb(50,50,50),Color.rgb(25,25,25),Color.rgb(0,0,0)};
    private Color[] pathColorList;
    private int[][] closedColorList;

    public PathFinderView(AStar model)
    {
        this.model = model;
        initMap();
        pathColorList = new Color[]{Color.rgb(0, 119, 255), Color.rgb(222, 54, 255), Color.rgb(255, 98, 0), Color.rgb(0, 238, 255), Color.rgb(102, 0, 255)};
        closedColorList = new int[][]{{0, 119, 255}, {222, 54, 255}, {255, 98, 0}, {0, 238, 255}, {102, 0, 255}};
    }

    /*
    method init map
    goes through the entire map and draws rectangles
     */
    public void initMap()
    {
        for (int i=0; i<model.getMap().length; i++)
        {
            for (int j=0; j<model.getMap()[0].length; j++)
            {
                Rectangle block = new Rectangle(j*40,i*40,40,40);
                if (model.getMap()[i][j] == -1)
                    block.setFill(Color.rgb(0,0,0));
                else
                    block.setFill(colorWeight[model.getMap()[i][j]]);

                getChildren().add(block);
            }
        }
    }

    /*
    method update
    updates block at x,y
     */
    public void update(int x, int y){
        Rectangle block = new Rectangle(x*40,y*40,40,40);
        if (model.getMap()[y][x] == -1)
            block.setFill(Color.rgb(0,0,0));
        else
            block.setFill(colorWeight[model.getMap()[y][x]]);
        getChildren().add(block);
    }

    /*
    method draw node
    draws new start/end node at x,y
    color is decided by i index
     */
    public void drawNode(int x, int y, int i){
        Circle c = new Circle(x*40 + 20,y*40 + 20,20);
        c.setFill(pathColorList[i]);
        getChildren().add(c);
    }

    /*
    method draw paths
    goes through all the found paths
    draws all the visited nodes in a closed list
    draws the path
     */
    public void drawPaths(){
        for (int i = 0; i < model.getAllFoundPaths().size(); i++)
        {
            for (Node n: model.getAllClosedLists().get(i))
            {
                Rectangle block = new Rectangle(n.getX()*40, n.getY()*40, 40, 40);
                block.setFill(getColor(n, i));
                getChildren().add(block);
            }
            for (Node n : model.getAllFoundPaths().get(i))
            {
                Rectangle block = new Rectangle(n.getX()*40 + 15, n.getY()*40 + 15, 10, 10);
                block.setFill(Color.WHITE);
                getChildren().add(block);
            }
        }
    }

    /*
    method get color
    updates the color for a block in the closed list based on its age
    the older the node, the darker it is
     */
    public Color getColor(Node n, int i){
        int c1 = closedColorList[i][0];
        int c2 = closedColorList[i][1];
        int c3 = closedColorList[i][2];

        c1 -= n.getAge()*2.5;
        c2 -= n.getAge()*2.5;
        c3 -= n.getAge()*2.5;

        if (c1<0)
            c1=5;
        if (c2<0)
            c2=5;
        if (c3<0)
            c3=5;

        return Color.rgb(c1, c2, c3);
    }

}