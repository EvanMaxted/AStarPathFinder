import java.lang.Math;

public class Node implements Comparable
{
    private int f;//total cost
    private int g;//distance from home
    private int h;//heuristic value
    private int x;//x pos in maze
    private int y;//y pos in maze
    private Node parent;//parent node
    private boolean open;
    private boolean closed;
    private int age;

    private String nodeID;

    public Node(int x, int y)
    {
        this(null, x, y);
    }

    public Node(Node parent, int x, int y)
    {
        this.parent = parent;
        this.x = x;
        this.y = y;
        g = 0;
        h = 0;
        f = 0;
        open = false;
        closed = false;
        nodeID = x + "" + y;
        age = 0;
    }

    public int compareTo(Object o) {
        Node n = (Node)o;
        return this.getF() - n.getF();
    }

    public String toString()
    {
        return "(" + getX() + "," + getY() + ")";
    }

    //getter and setter methods
    public int getG()
    {
        return g;
    }

    public void setG(int weight) {
        this.g = parent.getG() + weight;
    }

    public int getH()
    {
        return h;
    }

    public void setH(Node endNode) {
        this.h = 20 * (Math.abs(endNode.getX() - this.getX()) + Math.abs(endNode.getY() - this.getY()));
    }

    public int getF()
    {
        return f;
    }

    public void setF()
    {
        this.f = this.getG() + this.getH();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public void incAge()
    {
        age++;
    }

    public int getAge() {
        return age;
    }

    public String getNodeID() {
        return nodeID;
    }
}