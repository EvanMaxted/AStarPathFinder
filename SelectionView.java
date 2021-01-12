import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class SelectionView extends Pane
{
    private Button addStartButton;
    private Button addEndButton;
    private Button wallButton;
    private Button slowButton;
    private Button fastButton;
    private Button doneButton;
    private Button startButton;
    private Button createPathButton;
    private ListView pathListView;
    private Button deletePathButton;

    public SelectionView()
    {
        addStartButton = new Button("Add Start Node");
        addStartButton.setPrefSize(100,50);
        addStartButton.relocate(15,15);

        addEndButton = new Button("Add End Node");
        addEndButton.setPrefSize(100,50);
        addEndButton.relocate(15,80);

        wallButton = new Button("Toggle Wall");
        wallButton.setPrefSize(100,50);
        wallButton.relocate(130,15);

        slowButton = new Button("Slow Path Down");
        slowButton.setPrefSize(100,50);
        slowButton.relocate(130,80);

        fastButton = new Button("Speed Path Up");
        fastButton.setPrefSize(100,50);
        fastButton.relocate(130,145);

        doneButton = new Button("DONE");
        doneButton.setPrefSize(100,50);
        doneButton.relocate(15,145);

        startButton = new Button("START PATHFINDER");
        startButton.setPrefSize(100,50);
        startButton.relocate(15,325);

        createPathButton = new Button("Create Path");
        createPathButton.setPrefSize(100,50);
        createPathButton.relocate(15,145);

        pathListView = new ListView();
        pathListView.setPrefSize(215,100);
        pathListView.relocate(15,210);

        deletePathButton = new Button("Delete Path");
        deletePathButton.setPrefSize(100,50);
        deletePathButton.relocate(130,325);

        //initialize which buttons start disabled
        getDoneButton().setDisable(true);
        getCreatePathButton().setVisible(false);
        getCreatePathButton().setDisable(true);
        getStartButton().setDisable(true);
        getDeletePathButton().setDisable(true);

        getChildren().addAll(addStartButton,addEndButton,wallButton, slowButton, fastButton, doneButton, startButton, createPathButton, pathListView, deletePathButton);
    }

    //getter methods

    public Button getAddStartButton() {
        return addStartButton;
    }

    public Button getAddEndButton() {
        return addEndButton;
    }

    public Button getWallButton(){
        return wallButton;
    }

    public Button getSlowButton() {
        return slowButton;
    }

    public Button getFastButton() {
        return fastButton;
    }

    public Button getDoneButton() {
        return doneButton;
    }

    public Button getStartButton() {
        return startButton;
    }

    public Button getCreatePathButton() {
        return createPathButton;
    }

    public ListView getPathListView() {
        return pathListView;
    }

    public Button getDeletePathButton() {
        return deletePathButton;
    }

}
