import java.util.*;

public class AStar
{
    private int[][] map;

    private LinkedList<Node> openList;
    private LinkedList<Node> closedList;
    private ArrayList<Node[]> allPaths;
    private ArrayList<ArrayList<Node>> allFoundPaths;
    private ArrayList<LinkedList<Node>> allClosedLists;
    private HashMap<String, Node> nodeSet;
    private HashMap<String, Integer> travelled;

    //to check the 4 positions around a node for its children
    private final static int[][] newPosition = {{0,-1},{0,1},{-1,0},{1,0}};



    public AStar()
    {
        allPaths = new ArrayList<>();
        allFoundPaths = new ArrayList<>();
        allClosedLists = new ArrayList<>();
        travelled = new HashMap<>();
        map = new int[12][20];//20X12 map
    }

    /*
    method create new path
    creates a new path based on start and end node and adds it to array of all the paths
     */
    public void createNewPath(Node start, Node end)
    {
        Node[] newPath = new Node[2];
        newPath[0] = start;
        newPath[1] = end;
        allPaths.add(newPath);
    }

    /*
    method search all paths
    calls search method on each path from all paths
    adds found path to all found paths
     */
    public void searchAllPaths() {
        for (Node[] path : allPaths)
            allFoundPaths.add(search(path[0],path[1]));
    }

    /*
    method search
    A Star search from start node to end node
     */
    public ArrayList<Node> search(Node startNode, Node endNode)
    {
        Node current = startNode;
        openList = new LinkedList<>();
        closedList = new LinkedList<>();
        nodeSet = new HashMap<>();

        openList.addLast(startNode);
        startNode.setOpen(true);


        while (openList.size() > 0)
        {
            current = openList.getFirst(); //sets current to min F in open list
            openList.removeFirst(); // removes current from open list
            current.setOpen(false);

            closedList.addLast(current); // adds current to closed list
            current.setClosed(true);

            if (!nodeSet.containsKey(current.getNodeID()))// adds current node to node set if not already in
                nodeSet.put(current.getNodeID(), current);

            if (!travelled.containsKey(current.getNodeID()))// incs. the number of times a path block has been travelled over
                travelled.put(current.getNodeID(), 0);
            travelled.replace(current.getNodeID(), travelled.get(current.getNodeID())+1);

            updateAges(); // updates the ages of all the nodes in the closed list

            if (nodeSet.containsKey(endNode.getNodeID()))// if end node has been added to closed list
                break;

            for (int[] i : newPosition) // for the N,E,S,W around current node
            {
                Node child;
                int[] nodePosition = new int[2];
                nodePosition[0] = current.getX() + i[0];
                nodePosition[1] = current.getY() + i[1];

                //if the node x or y is out of bounds or a wall
                if (nodePosition[0] > map[0].length -1 || nodePosition[0] < 0 || nodePosition[1] > map.length -1 || nodePosition[1] < 0 || map[nodePosition[1]][nodePosition[0]] == -1)
                    continue;

                //if child ID is already in node set, get the child
                if (nodeSet.containsKey(nodePosition[0]+""+nodePosition[1]))
                    child = nodeSet.get(nodePosition[0]+""+nodePosition[1]);

                else {// create new child and add it to node set
                    child = new Node(current, nodePosition[0], nodePosition[1]);
                    nodeSet.put(child.getNodeID(), child);
                }

                if (child.isClosed()) //if child is in the closed list
                    continue;

                else if (child.isOpen()) {// if child is in the open list

                    if (child.getG() < (getWeight(child, current) + current.getG()))// the new path to child is worse than its current path
                        continue;

                    else if (child.getG() > (getWeight(child, current) + current.getG())) {// the new path is better than its current path
                        updateValues(child,current,endNode);
                        Collections.sort(openList);//sort list because values have been changed
                    }

                }

                else if (!child.isOpen()) {//child is not in open list, add child to open list then resort the list
                    updateValues(child,current,endNode);
                    openList.addLast(child);
                    child.setOpen(true);
                    Collections.sort(openList);
                }

            }

        }
        allClosedLists.add(closedList);
        return findPath(current);
    }

    /*
    method update values
    updates the G,H,F of the child node
     */
    public void updateValues(Node current, Node parent, Node endNode){
        current.setG(getWeight(current,parent));
        current.setH(endNode);
        current.setF();
        current.setParent(parent);
    }

    /*
    method find path
    finds the path from current node by tracing their parents backwards
     */
    public ArrayList<Node> findPath(Node current)
    {
        ArrayList<Node> path = new ArrayList<>();

        while(current != null) {
            path.add(current);
            current = current.getParent();
        }
        Collections.reverse(path);//reverse path because it is from end to start right now
        return path;
    }

    /*
    method get weight
    returns weight of a block based on its weight, parents weight and times travelled
     */
    public int getWeight(Node current, Node parent) {
        int w = travelled.getOrDefault(current.getNodeID(), 0);
        return map[current.getY()][current.getX()] + map[parent.getY()][parent.getX()] + 10 + w*10;
    }

    /*
    method paths to string
    creates strings of each path for the list view
     */
    public String[] pathsToString(){
        String[] stringPaths = new String[allPaths.size()];

        for (int i = 0; i < allPaths.size(); i++)
            stringPaths[i] = "Path from: " + allPaths.get(i)[0] + " to " + allPaths.get(i)[1];

        return stringPaths;
    }

    //increases all the ages of nodes in the closed list by 1
    public void updateAges(){
        for (Node n: closedList)
            n.incAge();
    }

    //map updating methods
    public void toggleWall(int x, int y) {//toggles between a wall and not a wall
        if (map[y][x] == -1)
            map[y][x] = 0;
        else
            map[y][x] = -1;
    }

    public void increaseWeight(int x, int y) {//incs. weight of block until maxed
        if (map[y][x] < 10 && map[y][x] != -1)
            map[y][x]++;
    }

    public void decreaseWeight(int x, int y){//decs. weight of block until min
        if(map[y][x] > 0 && map[y][x] != -1)
            map[y][x]--;
    }

    //getter and setter methods
    public int[][] getMap() {
        return map;
    }

    public ArrayList<Node[]> getAllPaths() {
        return allPaths;
    }

    public ArrayList<ArrayList<Node>> getAllFoundPaths() {
        return allFoundPaths;
    }

    public ArrayList<LinkedList<Node>> getAllClosedLists(){
        return allClosedLists;
    }

}