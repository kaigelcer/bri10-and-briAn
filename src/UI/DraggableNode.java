package UI;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

import java.util.LinkedList;

public class DraggableNode extends StackPane {

    // node position
    private double x = 0;
    private double y = 0;

    // mouse position
    private double mousex = 0;
    private double mousey = 0;
    private LinkedList<DraggableNode> allDraggableNodes;
    private Node view;
    private boolean selected = false;
    private boolean dragging = true;
    private boolean moveToFront = true;
    private Scale scaleTransform;
    private Rotate rotateTransform;
    private double rotationAngle = 0;
    private boolean zoomable = false;
    private double minScale = 0.1;
    private double maxScale = 10;
    private double scaleIncrement = 0.001;
    private ResizeMode resizeMode;
    private boolean RESIZE_TOP;
    private boolean RESIZE_LEFT;
    private boolean RESIZE_BOTTOM;
    private boolean RESIZE_RIGHT;
    private String url;

    public DraggableNode() {
        init();
    }

    public DraggableNode(Node view) {
        this.view = view;
        getChildren().add(view);
        init();
    }

    private void init() {

        scaleTransform = new Scale(1, 1);
        scaleTransform.setPivotX(0);
        scaleTransform.setPivotY(0);
        scaleTransform.setPivotZ(0);

        // ADDED BY ME
        rotateTransform = new Rotate();
        rotateTransform.setPivotX(300);
        rotateTransform.setPivotY(100);

        getTransforms().add(scaleTransform);
        getTransforms().add(rotateTransform);



        onMousePressedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setSelected();

                final Node n = DraggableNode.this;

                final double parentScaleX = n.getParent().localToSceneTransformProperty().getValue().getMxx();
                final double parentScaleY = n.getParent().localToSceneTransformProperty().getValue().getMyy();

                final double scaleX = n.localToSceneTransformProperty().getValue().getMxx();
                final double scaleY = n.localToSceneTransformProperty().getValue().getMyy();

                // record the current mouse X and Y position on Node
                mousex = event.getSceneX();
                mousey = event.getSceneY();

                x = n.getLayoutX() * parentScaleX;
                y = n.getLayoutY() * parentScaleY;

                if (isMoveToFront()) {
                    toFront();
                }

            }
        });

        //Event Listener for MouseDragged
        onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                setSelected();
                System.out.println("X: " + DraggableNode.this.x);
                System.out.println("Y: " + DraggableNode.this.y);

                final Node n = DraggableNode.this;

                final double parentScaleX = n.getParent().localToSceneTransformProperty().getValue().getMxx();
                final double parentScaleY = n.getParent().localToSceneTransformProperty().getValue().getMyy();

                final double scaleX = n.localToSceneTransformProperty().getValue().getMxx();
                final double scaleY = n.localToSceneTransformProperty().getValue().getMyy();

                // Get the exact moved X and Y

                double offsetX = event.getSceneX() - mousex;
                double offsetY = event.getSceneY() - mousey;
                if (resizeMode == ResizeMode.NONE) {

                    x += offsetX;
                    y += offsetY;

                    double scaledX = x * 1 / parentScaleX;
                    double scaledY = y * 1 / parentScaleY;

                    n.setLayoutX(scaledX);
                    n.setLayoutY(scaledY);

                    dragging = true;

                } else {

                    double width = n.getBoundsInLocal().getMaxX()
                            - n.getBoundsInLocal().getMinX();
                    double height = n.getBoundsInLocal().getMaxY()
                            - n.getBoundsInLocal().getMinY();

                    if (RESIZE_TOP) {
                        double newHeight =
                                getBoundsInLocal().getHeight()
                                        - offsetY / scaleY
                                        - getInsets().getTop();
                        y += offsetY;
                        double scaledY = y / parentScaleY;
                        setLayoutY(scaledY);
                        setPrefHeight(newHeight);
                    }
                    if (RESIZE_LEFT) {
                        double newWidth =
                                getBoundsInLocal().getWidth()
                                        - offsetX / scaleX
                                        - getInsets().getLeft();
                        x += offsetX;
                        double scaledX = x / parentScaleX;
                        setLayoutX(scaledX);
                        setPrefWidth(newWidth);

                    }

                    if (RESIZE_BOTTOM) {
                        double newHeight =
                                getBoundsInLocal().getHeight()
                                        + offsetY / scaleY
                                        - getInsets().getBottom();
                        setPrefHeight(newHeight);
                    }
                    if (RESIZE_RIGHT) {
                        double newWidth =
                                getBoundsInLocal().getWidth()
                                        + offsetX / scaleX
                                        - getInsets().getRight();
                        setPrefWidth(newWidth);
                    }
                }

                // again set current Mouse x AND y position
                mousex = event.getSceneX();
                mousey = event.getSceneY();

                event.consume();
            }
        });

        onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                dragging = false;
                setSelected();
            }
        });

        onMouseMovedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {

                final Node n = DraggableNode.this;

                final double parentScaleX = n.getParent().localToSceneTransformProperty().getValue().getMxx();
                final double parentScaleY = n.getParent().localToSceneTransformProperty().getValue().getMyy();

                final double scaleX = n.localToSceneTransformProperty().getValue().getMxx();
                final double scaleY = n.localToSceneTransformProperty().getValue().getMyy();

                final double border = 10;

                double diffMinX = Math.abs(n.getBoundsInLocal().getMinX() - t.getX());
                double diffMinY = Math.abs(n.getBoundsInLocal().getMinY() - t.getY());
                double diffMaxX = Math.abs(n.getBoundsInLocal().getMaxX() - t.getX());
                double diffMaxY = Math.abs(n.getBoundsInLocal().getMaxY() - t.getY());

                boolean left = diffMinX * scaleX < border;
                boolean top = diffMinY * scaleY < border;
                boolean right = diffMaxX * scaleX < border;
                boolean bottom = diffMaxY * scaleY < border;

                RESIZE_TOP = true;
                RESIZE_LEFT = false;
                RESIZE_BOTTOM = true;
                RESIZE_RIGHT = false;

                if (left && !top && !bottom) {
                    n.setCursor(Cursor.W_RESIZE);
                    resizeMode = ResizeMode.LEFT;
                    RESIZE_LEFT = true;
                } else if (left && top && !bottom) {
                    n.setCursor(Cursor.NW_RESIZE);
                    resizeMode = ResizeMode.TOP_LEFT;
                    RESIZE_LEFT = true;
                    RESIZE_TOP = true;
                } else if (left && !top && bottom) {
                    n.setCursor(Cursor.SW_RESIZE);
                    resizeMode = ResizeMode.BOTTOM_LEFT;
                    RESIZE_LEFT = true;
                    RESIZE_BOTTOM = true;
                } else if (right && !top && !bottom) {
                    n.setCursor(Cursor.E_RESIZE);
                    resizeMode = ResizeMode.RIGHT;
                    RESIZE_RIGHT = true;
                } else if (right && top && !bottom) {
                    n.setCursor(Cursor.NE_RESIZE);
                    resizeMode = ResizeMode.TOP_RIGHT;
                    RESIZE_RIGHT = true;
                    RESIZE_TOP = true;
                } else if (right && !top && bottom) {
                    n.setCursor(Cursor.SE_RESIZE);
                    resizeMode = ResizeMode.BOTTOM_RIGHT;
                    RESIZE_RIGHT = true;
                    RESIZE_BOTTOM = true;
                } else if (top && !left && !right) {
                    n.setCursor(Cursor.N_RESIZE);
                    resizeMode = ResizeMode.TOP;
                    RESIZE_TOP = true;
                } else if (bottom && !left && !right) {
                    n.setCursor(Cursor.S_RESIZE);
                    resizeMode = ResizeMode.BOTTOM;
                    RESIZE_BOTTOM = true;
                } else {
                    n.setCursor(Cursor.DEFAULT);
                    resizeMode = ResizeMode.NONE;
                }
            }
        });

        setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {

                if (!isZoomable()) {
                    return;
                }

                double scaleValue =
                        scaleTransform.getY() + event.getDeltaY() * getScaleIncrement();

                scaleValue = Math.max(scaleValue, getMinScale());
                scaleValue = Math.min(scaleValue, getMaxScale());

                scaleTransform.setX(scaleValue);
                scaleTransform.setY(scaleValue);

                scaleTransform.setPivotX(0);
                scaleTransform.setPivotX(0);
                scaleTransform.setPivotZ(0);

//                setScaleX(scaleValue);
//                setScaleY(scaleValue);

                event.consume();
            }
        });

    }

    public void setAllDraggableNodes(LinkedList<DraggableNode> nodes){
        allDraggableNodes = nodes;
        allDraggableNodes.add(this);
    }

    public void rotate90(){
        rotationAngle += 90;
        setRotate(rotationAngle);
    }

    public void setSelected(){
        selected = true;
        for (DraggableNode node : allDraggableNodes){
            if(node != this){
                node.notSelected();
            }
        }
    }

    public void flipHorizontal(){
        this.setScaleX(this.getScaleX() * -1);
    }

    public void flipVertical(){
        this.setScaleY(this.getScaleY() * -1);
    }

    public void notSelected(){
        this.selected = false;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the zoomable
     */
    public boolean isZoomable() {
        return zoomable;
    }

    /**
     * @param zoomable the zoomable to set
     */
    public void setZoomable(boolean zoomable) {
        this.zoomable = zoomable;
    }

    /**
     * @return the dragging
     */
    protected boolean isDragging() {
        return dragging;
    }

    /**
     * @return the view
     */
    public Node getView() {
        return view;
    }

    /**
     * @param moveToFront the moveToFront to set
     */
    public void setMoveToFront(boolean moveToFront) {
        this.moveToFront = moveToFront;
    }

    /**
     * @return the moveToFront
     */
    public boolean isMoveToFront() {
        return moveToFront;
    }

    public void removeNode(Node n) {
        getChildren().remove(n);
    }

    /**
     * @return the minScale
     */
    public double getMinScale() {
        return minScale;
    }

    /**
     * @param minScale the minScale to set
     */
    public void setMinScale(double minScale) {
        this.minScale = minScale;
    }

    /**
     * @return the maxScale
     */
    public double getMaxScale() {
        return maxScale;
    }

    /**
     * @param maxScale the maxScale to set
     */
    public void setMaxScale(double maxScale) {
        this.maxScale = maxScale;
    }

    /**
     * @return the scaleIncrement
     */
    public double getScaleIncrement() {
        return scaleIncrement;
    }

    /**
     * @param scaleIncrement the scaleIncrement to set
     */
    public void setScaleIncrement(double scaleIncrement) {
        this.scaleIncrement = scaleIncrement;
    }
}

enum ResizeMode {

    NONE,
    TOP,
    LEFT,
    BOTTOM,
    RIGHT,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT
}
