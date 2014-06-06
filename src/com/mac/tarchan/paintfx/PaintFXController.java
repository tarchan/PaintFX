/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mac.tarchan.paintfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * PaintFXController
 * 
 * @author tarchan
 */
public class PaintFXController implements Initializable {

    /** ログ */
    private static final Logger logger = Logger.getLogger(PaintFXController.class.getName());
    /** 環境設定 */
    private final Preferences init = Preferences.userRoot().node(getClass().getName());
    /** 開始点 */
    private Point2D o;
    private final Canvas canvas = new Canvas();
    private final FileChooser fileChooser = new FileChooser();
    private File savedFile;
//    private List<Float> widths;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Pane group;
    @FXML
    private Label status;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ComboBox<Float> widthPicker;
    @FXML
    private Slider rotateSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        logger.info(() -> "init: " + init);
        int width = init.getInt("width", 512);
        int height = init.getInt("height", 512);
        String file = init.get("file", null);
        if (file != null) {
            initFileChooser(new File(file));
        }
//        widths = Arrays.asList(0.1f, 0.5f, 1.0f, 2.0f);
        widthPicker.setItems(FXCollections.observableArrayList(0.1f, 0.5f, 1f, 2f, 5f, 10f, 20f));
        widthPicker.selectionModelProperty().get().select(1.0f);
//        widthPicker.setCellFactory((ListView<Float> param) -> new LineWidthCell());
        widthPicker.setButtonCell(new LineWidthCell());
//        canvas = new Canvas(1024, 1024);
        StackPane.setAlignment(canvas, Pos.CENTER);
        group.getChildren().add(canvas);
        colorPicker.setValue(Color.BLACK);
        canvas.rotateProperty().bind(rotateSlider.valueProperty());
//        canvas.setRotationAxis(new Point3D(0, 1, 0));
        rotateSlider.valueProperty().addListener(this::onRotate);
//        canvas.setStyle("-fx-background-color:white;");
//        GraphicsContext g = canvas.getGraphicsContext2D();
//        g.setFill(Color.WHITE);
//        g.fillRect(0, 0, 1024, 1024);
//        group.setStyle("-fx-background-color:white;");
        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scroll.viewportBoundsProperty().addListener((ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) -> {
            double prefWidth = Math.max(canvas.getWidth(), newValue.getWidth());
            double prefHeight = Math.max(canvas.getHeight(), newValue.getHeight());
            group.setPrefSize(prefWidth, prefHeight);
        });
//        scroll.setOpacity(0.5);
//        Parent root = scroll.getScene().getRoot();
//        root.setStyle("-fx-background-color:transparent; -fx-background:transparent;");
//        root.setOpacity(0.5);
//        scroll.setStyle("-fx-background: gray;");
//        scroll.setStyle("-fx-control-inner-background: gray;");
//        Window window = scroll.getScene().getWindow();
//        window.setOpacity(0.5);
        newImage(width, height);

//        SVGPath svg = new SVGPath();
//        svg.setContent("M70,50 L90,50 L120,90 L150,50 L170,50"
//            + "L210,90 L180,120 L170,110 L170,200 L70,200 L70,110 L60,120 L30,90"
//            + "L70,50");
//        svg.setStroke(Color.DARKGREY);
//        svg.setStrokeWidth(2);
//        svg.setEffect(new DropShadow());
//        svg.setFill(colorPicker.getValue());
//        group.getChildren().add(svg);
        KeyCombination accelerator = KeyCombination.valueOf("ctrl+n");
        logger.info(() -> "accelerator: " + accelerator);
    }    

    private void onRotate(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        logger.log(Level.CONFIG, () -> "回転: " + newValue);
    }

    private void newImage(int width, int height) {
        canvas.setWidth(width);
        canvas.setHeight(height);
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    private void newImage(Image image) {
        canvas.setWidth(image.getWidth());
        canvas.setHeight(image.getHeight());
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.drawImage(image, 0, 0);
    }

    private void saveImage(Image image, File file) throws IOException {
        BufferedImage saveImage = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(saveImage, "png", file);
    }

    private void saveFile(File file) {
        if (file == null) {
            file = fileChooser.showSaveDialog(group.getScene().getWindow());
            if (file == null) {
                // キャンセル
                return;
            }
        }
        try {
            SnapshotParameters params = new SnapshotParameters();
            WritableImage image = canvas.snapshot(params, null);
            saveImage(image, file);
            savedFile = file;
            init.put("file", file.getPath());
            initFileChooser(file);
            logger.info(() -> "file: " + savedFile);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "イメージを保存できません。: " + file, ex);
        }
    }

    @FXML
    private void onPressed(MouseEvent event) {
//        o = new Point2D(event.getX(), event.getY());
        o = canvas.sceneToLocal(event.getSceneX(), event.getSceneY());
//        GraphicsContext g = canvas.getGraphicsContext2D();
//        g.moveTo(o.getX(), o.getY());
    }

    @FXML
    private void onDragged(MouseEvent event) {
//        PixelWriter px = canvas.getPixelWriter();
//        px.setColor((int)event.getSceneX(), (int)event.getSceneY(), Color.BLACK);
        GraphicsContext g = canvas.getGraphicsContext2D();
//        BoxBlur blur = new BoxBlur();
//        blur.setWidth(1);
//        blur.setHeight(1);
//        blur.setIterations(1);
//        g.setEffect(blur);
//        Point2D p = group.getLocalToParentTransform().transform(event.getSceneX(), event.getSceneY());
//        Point2D p2 = group.getLocalToSceneTransform().transform(event.getSceneX(), event.getSceneY());
        Point2D p = canvas.sceneToLocal(event.getSceneX(), event.getSceneY());
//        Point2D p = new Point2D(event.getX(), event.getY());
//        g.fillRect(p.getX(), p.getY(), 1, 1);
//        g.setFill(Color.RED);
//        g.setStroke(Color.BLACK);
        g.setStroke(colorPicker.getValue());
//        g.setLineWidth(5);
        g.setLineWidth(widthPicker.getSelectionModel().getSelectedItem());
        g.setLineCap(StrokeLineCap.ROUND);
        g.setLineJoin(StrokeLineJoin.ROUND);
//        g.lineTo(p.getX(), p.getY());
//        g.stroke();
        g.strokeLine(o.getX(), o.getY(), p.getX(), p.getY());
        o = p;
        status.setText(String.format("(%.2f,%.2f)", p.getX(), p.getY()));
//        group.getParent().getLocalToSceneTransform();
    }

    @FXML
    private void onMoved(MouseEvent event) {
        Point2D p = canvas.sceneToLocal(event.getSceneX(), event.getSceneY());
        status.setText(String.format("(%.2f,%.2f)", p.getX(), p.getY()));
    }

    @FXML
    private void onNew(ActionEvent event) {
        // TODO キャンバスサイズを設定するダイアログ
        FX fx = FX.build(this.getClass(), "New").dialog("新規作成", group);
        NewController newController = fx.getController();
        newController.widthProperty().set((int)canvas.getWidth());
        newController.heightProperty().set((int)canvas.getHeight());
        newController.dpiProperty().set(300);
//        fx.showDialog();
        Dimension2D size = fx.showDialog();
        logger.info(() -> "size: " + size);
        if (size == null) {
            return;
        }
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();
//        int width = newController.widthProperty().get();
//        int height = newController.heightProperty().get();
//        if (width <= 0 || height <= 0) {
//            return;
//        }
//        logger.info(() -> "width: " + width);
//        logger.info(() -> "height: " + height);
//        logger.info(() -> "dpi: " + newController.dpiProperty().get());
        init.putInt("width", width);
        init.putInt("height", height);
        newImage(width, height);
    }

    private void initFileChooser(File file) {
        if (file != null) {
            fileChooser.setInitialDirectory(file.getParentFile());
            fileChooser.setInitialFileName(file.getName());
        }        
    }

    @FXML
    private void onOpen(ActionEvent event) {
        // 保存したイメージを開く
//        if (savedFile != null) {
//            fileChooser.setInitialDirectory(savedFile.getParentFile());
//            fileChooser.setInitialFileName(savedFile.getName());
//        }
        File file = fileChooser.showOpenDialog(group.getScene().getWindow());
        if (file != null) {
            initFileChooser(file);
            if (file.getName().endsWith(".svg")) {
//                SVGPath svg = new SVGPath();
//                try {
//                    String content = new String(Files.readAllBytes(file.toPath()), "utf-8");
//                    svg.setContent(content);
//                    logger.log(Level.INFO, "SVG: " + svg.getId());
//                } catch (IOException ex) {
//                    logger.log(Level.SEVERE, "SVGを読み込めません。", ex);
//                    throw new RuntimeException("SVGを読み込めません。", ex);
//                }
//                GraphicsContext g = canvas.getGraphicsContext2D();
//                g.appendSVGPath(svg.getContent());
                // TODO SVGのイメージをキャンバスにコピー
                WebView web = new WebView();
                WebEngine engine = web.getEngine();
                engine.getLoadWorker().stateProperty().addListener((ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
                    logger.info(() -> "state=" + newValue);
                    if (newValue == Worker.State.SUCCEEDED) {
                        Image image = web.snapshot(null, null);
//                        group.getChildren().remove(web);
                        newImage(image);
                        init.put("file", file.getPath());
                    }
                });
                group.getChildren().add(web);
                engine.load(file.toURI().toString());
//                Image image = web.snapshot(null, null);
//                group.getChildren().remove(web);
                return;
            }
//            fileChooser.setInitialDirectory(file.getParentFile());
//            fileChooser.setInitialFileName(file.getName());
            Image image = new Image(file.toURI().toString());
            logger.info(() -> "dir=" + file.getParentFile() + ", name=" + file.getName() + ", image=" + image);
//            canvas.setWidth(image.getWidth());
//            canvas.setHeight(image.getHeight());
//            GraphicsContext g = canvas.getGraphicsContext2D();
//            g.drawImage(image, 0, 0);
            newImage(image);
            init.put("file", file.getPath());
        }
    }

    @FXML
    private void onSave(ActionEvent event) {
        saveFile(null);
    }

    /**
     * アプリケーション情報ダイアログを表示します。
     */
    @FXML
    private void onAbout(ActionEvent event) {
        FX.build(this.getClass(), "About").dialog("PaintFX について", group).showDialog();
    }
}
