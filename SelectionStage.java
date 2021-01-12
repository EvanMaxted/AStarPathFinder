import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.input.*;

class SelectionStage{

    private AStar model;
    private SelectionView view;
    private PathFinderView pathView;
    private Pane aPane;
    private Stage subStage;

    private Node startNode;
    private Node endNode;

    private int mode;


    public SelectionStage(AStar model, PathFinderView pathView)
    {
        mode = -1;
        view = new SelectionView();
        aPane = new Pane();
        subStage = new Stage();
        this.pathView = pathView;
        this.model = model;

        subStage.setTitle("Action Selector");
        aPane.getChildren().add(view);
        subStage.setScene(new Scene(aPane,245,390));
        subStage.setX(400);
        subStage.setY(100);
        subStage.show();

        view.getAddStartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                setMode(0);
                disableAll();
            }
        });

        view.getAddEndButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                setMode(1);
                disableAll();
            }
        });

        view.getWallButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                setMode(2);
                disableAll();
            }
        });

        view.getSlowButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                setMode(3);
                disableAll();
            }
        });

        view.getFastButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                setMode(4);
                disableAll();
            }
        });

        view.getDoneButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                setMode(5);
                enableAll();
            }
        });

        view.getCreatePathButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleCreatePathButtonClicked();
            }
        });

        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleStartButtonCLicked();
            }
        });

        view.getPathListView().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handlePathListViewSelected();
            }
        });

        view.getDeletePathButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleDeletePathButton();
            }
        });
    }


    /*
    method handle create path button clicked
    updates the list view of all paths
    resets start and end nodes to null
    calls enable buttons
     */
    public void handleCreatePathButtonClicked()
    {
        model.createNewPath(startNode, endNode);
        view.getPathListView().setItems(FXCollections.observableArrayList(model.pathsToString()));
        startNode = null;
        endNode = null;
        enableAll();
    }

    /*
    method handle start button clicked
    searches all the created paths
    draws all of the paths
    closes action selector stage
     */
    public void handleStartButtonCLicked()
    {
        model.searchAllPaths();
        pathView.drawPaths();
        subStage.close();
    }

    /*
    method handle path list view select
    if an item in the list view is selected enable the delete path button
     */
    public void handlePathListViewSelected(){
        if (view.getPathListView().getSelectionModel().getSelectedIndex() > -1)
            view.getDeletePathButton().setDisable(false);
    }

    /*
    method handle delete path button
    deletes path from array of paths
    updates the view to clear the nodes
    updates list view
    redraws all of the nodes at the right index color
    disables the delete button
    if there are no more paths, it disables the start button
    calls enable buttons
     */
    public void handleDeletePathButton(){
        int index = view.getPathListView().getSelectionModel().getSelectedIndex();
        Node[] deletes = model.getAllPaths().get(index);
        Node delStart = deletes[0];
        Node delEnd = deletes[1];
        pathView.update(delStart.getX(), delStart.getY());
        pathView.update(delEnd.getX(), delEnd.getY());
        model.getAllPaths().remove(index);
        view.getPathListView().setItems(FXCollections.observableArrayList(model.pathsToString()));
        for (int i=0; i< model.getAllPaths().size(); i++)
        {
            Node[] path = model.getAllPaths().get(i);
            Node n1 = path[0];
            Node n2 = path[1];
            pathView.drawNode(n1.getX(), n1.getY(), i);
            pathView.drawNode(n2.getX(), n2.getY(), i);
        }
        view.getDeletePathButton().setDisable(true);
        if (model.getAllPaths().size() < 1)
            view.getStartButton().setDisable(true);
        enableAll();
    }

    //enables buttons
    public void enableAll()
    {
        //always get enabled
        view.getWallButton().setDisable(false);
        view.getSlowButton().setDisable(false);
        view.getFastButton().setDisable(false);
        view.getDoneButton().setDisable(true);

        //max amount of paths, new nodes and new paths can't be created
        if (model.getAllPaths().size() >= 5){
            view.getAddStartButton().setDisable(true);
            view.getAddEndButton().setDisable(true);
            view.getCreatePathButton().setVisible(false);
            view.getCreatePathButton().setDisable(true);
            view.getDoneButton().setVisible(true);
        }
        else {
            view.getAddStartButton().setDisable(false);
            view.getAddEndButton().setDisable(false);
        }
        //start and end node both exist, so the new path can be created
        if (startNode!=null && endNode!=null){
            view.getCreatePathButton().setVisible(true);
            view.getCreatePathButton().setDisable(false);
            view.getDoneButton().setVisible(false);
        }
        else{
            view.getCreatePathButton().setVisible(false);
            view.getCreatePathButton().setDisable(true);
            view.getDoneButton().setVisible(true);
        }
        //at least 1 path exists so the search can run
        if (model.getAllPaths().size() > 0)
            view.getStartButton().setDisable(false);
    }

    /*
    method disable all
    disables all of the buttons except the done button
     */
    public void disableAll(){
        view.getAddStartButton().setDisable(true);
        view.getAddEndButton().setDisable(true);
        view.getWallButton().setDisable(true);
        view.getSlowButton().setDisable(true);
        view.getFastButton().setDisable(true);
        view.getCreatePathButton().setVisible(false);
        view.getCreatePathButton().setDisable(true);
        view.getDoneButton().setVisible(true);
        view.getDoneButton().setDisable(false);
        view.getDeletePathButton().setDisable(true);
    }

    //getter and setter methods
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

}
