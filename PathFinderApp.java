import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.*;

public class PathFinderApp extends Application {
    private AStar model;
    private PathFinderView view;
    private SelectionStage stage2;
    private Pane aPane;

    private int x;
    private int y;

    //constant numbers to represent the button modes
    private final static int addStartNodeMode = 0;
    private final static int addEndNodeMode = 1;
    private final static int addWallMode = 2;
    private final static int slowPathMode = 3;
    private final static int fastPathMode = 4;
    private final static int doneMode = 5;

    public PathFinderApp() {
        model = new AStar();
        aPane = new Pane();
        view = new PathFinderView(model);
    }

    public void start(Stage pathFinderStage) {

        aPane.getChildren().add(view);

        pathFinderStage.setScene(new Scene(aPane, model.getMap()[0].length * 40, model.getMap().length * 40));
        pathFinderStage.setResizable(false);
        pathFinderStage.setTitle("A Star Path Finder");
        pathFinderStage.setX(100);
        pathFinderStage.show();

        stage2 = new SelectionStage(model, view);

        view.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                x = Math.floorDiv((int) mouseEvent.getX(), 40);
                y = Math.floorDiv((int) mouseEvent.getY(), 40);
                handleMousePressed();
            }
        });
    }

    /*
    method handle mouse pressed
    goes through and fins which mode is active
     */
    public void handleMousePressed(){
        switch (stage2.getMode()) {
            case addStartNodeMode: {
                handleAddStartNode();
                return;
            }
            case addEndNodeMode: {
                handleAddEndNode();
                return;
            }
            case addWallMode: {
                handleWall();
                break;
            }
            case slowPathMode: {
                handleSlowPath();
                break;
            }
            case fastPathMode: {
                handleFastPath();
                break;
            }
            case doneMode: {
                stage2.setMode(-1);
                stage2.enableAll();
                return;
            }
            default: return;
        }

    }

    /*
    method handle add start node
    if start node already exist, clear the current circle
    set to new node
    draw new node
    set mode to -1 to exit the "add start node" mode
    call enable all buttons
     */
    public void handleAddStartNode() {
        if (stage2.getStartNode()!=null)
            view.update(stage2.getStartNode().getX(), stage2.getStartNode().getY());
        stage2.setStartNode(new Node(x, y));
        view.drawNode(x, y, model.getAllPaths().size());
        stage2.setMode(-1);
        stage2.enableAll();
    }

    /*
    method handle add end node
    if end node already exists, clear the current circle
    set to new node
    draw new node
    set mode to -1 to exit "add end node" mode
    call enable all buttons
     */
    public void handleAddEndNode() {
        if (stage2.getEndNode()!=null)
            view.update(stage2.getEndNode().getX(), stage2.getEndNode().getY());
        stage2.setEndNode(new Node(x, y));
        view.drawNode(x, y, model.getAllPaths().size());
        stage2.setMode(-1);
        stage2.enableAll();
    }

    /*
    method handle wall
    call toggle wall
    update view at x,y
     */
    public void handleWall(){
        model.toggleWall(x, y);
        view.update(x,y);
    }

    /*
    method handle slow path
    call inc. weight
    update view at x,y
     */
    public void handleSlowPath() {
        model.increaseWeight(x, y);
        view.update(x,y);
    }

    /*
    method handle fast path
    call dec. weight
    update view at x,y
     */
    public void handleFastPath() {
        model.decreaseWeight(x, y);
        view.update(x,y);
    }

    public static void main(String[] args) {
        launch(args);
    }
}