package com.y.player;

import com.leewyatt.rxcontrols.controls.RXAudioSpectrum;
import com.leewyatt.rxcontrols.controls.RXLrcView;
import com.leewyatt.rxcontrols.controls.RXMediaProgressBar;
import com.leewyatt.rxcontrols.controls.RXToggleButton;
import com.leewyatt.rxcontrols.pojo.LrcDoc;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 播放器控制器
 *
 * @author Y
 * @date 2023/02/03
 */
public class PlayerController {

    private final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private RXAudioSpectrum audioSpectrum;
    /**
     * 音频频谱发生改变的时候,修改频谱可视化组件的数据
     */
    private final AudioSpectrumListener audioSpectrumListener = (v, v1, floats, floats1) -> {
        audioSpectrum.setMagnitudes(floats);
    };
    @FXML
    private AnchorPane drawerPane;
    @FXML
    private ListView<File> listView;
    @FXML
    private RXLrcView lrcView;
    @FXML
    private ToggleButton playBtn;
    @FXML
    private RXMediaProgressBar progressBar;
    @FXML
    private BorderPane sliderPane;
    @FXML
    private Label timeLabel;
    private Timeline showAnim;
    private Timeline hideAnim;
    private ContextMenu soundPopup;
    private ContextMenu skinPopup;
    /**
     * 声音btn
     */
    @FXML
    private StackPane soundBtn;
    @FXML
    private StackPane skinBtn;
    private MediaPlayer player;
    /**
     * 进度条拖动或者点击
     */
    private final EventHandler<MouseEvent> progressBarHandler = event -> {
        if (player != null) {
            player.seek(progressBar.getCurrentTime());
            changeTimeLabel(progressBar.getCurrentTime());
        }
    };
    /**
     * 时间改变监听器
     */
    private final ChangeListener<Duration> durationChangeListener = (observableValue, duration, t1) -> {
        progressBar.setCurrentTime(t1);
        changeTimeLabel(duration);
    };
    private Slider soundSlider;
    private float[] emptyArray = new float[128];

    /**
     * 在播放进度发生改变的时候修改进度条的播放进度,修改时间的显示标签
     *
     * @param duration 当前时间
     */
    private void changeTimeLabel(Duration duration) {
        //当前的播放时间
        String currentTime = sdf.format(duration.toMillis());
        //总时间
        String bufferedTimer = sdf.format(player.getBufferProgressTime().toMillis());
        timeLabel.setText(currentTime + "/" + bufferedTimer);
    }

    @FXML
    void initialize() {
        initAnim();
        initSoundPopup();
        initSkinPopup();
        initListView();
        Arrays.fill(emptyArray, -60.0f);
        initProgressBar();
    }

    /**
     * 进度条的调整
     */
    private void initProgressBar() {
        //进度条的调整
        progressBar.setOnMouseClicked(progressBarHandler);
        progressBar.setOnMouseDragged(progressBarHandler);
    }

    /**
     * 初始化列表视图
     */
    private void initListView() {
        listView.setCellFactory(fileListView -> new MusicListCell());
        listView.getSelectionModel().selectedItemProperty().addListener(
                (ob, oldFile, newFile) -> {
                    //如果文件不为空则进行播放
                    if (newFile != null) {
                        if (player != null) {
                            disposeMediaPlayer();
                        }
                        player = new MediaPlayer(new Media(newFile.toURI().toString()));
                        player.setVolume(soundSlider.getValue() / 100);
                        //设置歌词
                        String lrcPath = newFile.getAbsolutePath().replaceAll("mp3$", "lrc");
                        File lrcFile = new File(lrcPath);
                        if (lrcFile.exists()) {
                            try {
                                byte[] bytes = Files.readAllBytes(lrcFile.toPath());
                                //解析歌词
                                lrcView.setLrcDoc(LrcDoc.parseLrcDoc(new String(bytes, EncodingDetect.detect(bytes))));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        //绑定歌词进度
                        lrcView.currentTimeProperty().bind(player.currentTimeProperty());
                        //设置频谱可视化
                        player.setAudioSpectrumListener(audioSpectrumListener);
                        //设置进度条的总时长
                        progressBar.durationProperty().bind(player.getMedia().durationProperty());
                        //播放器的进度修改监听器
                        player.currentTimeProperty().addListener(durationChangeListener);

                        //如果播放完当前歌曲,自动播放下一首
                        player.setOnEndOfMedia(this::playNextMusic);

                        //设置播放按钮播放
                        playBtn.setSelected(true);
                        player.play();
                    } else {
                        disposeMediaPlayer();
                    }
                });
    }

    /**
     * 处理媒体播放器
     */
    private void disposeMediaPlayer() {
        player.stop();
        lrcView.setLrcDoc(null);
        lrcView.currentTimeProperty().unbind();
        lrcView.setCurrentTime(Duration.ZERO);
        player.setAudioSpectrumListener(null);
        progressBar.durationProperty().unbind();
        progressBar.setCurrentTime(Duration.ZERO);
        player.currentTimeProperty().removeListener(durationChangeListener);
        audioSpectrum.setMagnitudes(emptyArray);
        timeLabel.setText("00:00 / 00:00");
        //设置播放按钮停止
        playBtn.setSelected(false);
        player.setOnEndOfMedia(null);
        player.dispose();
        player = null;
    }

    /**
     * 初始化皮肤弹出
     */
    private void initSkinPopup() {
        skinPopup = new ContextMenu(new SeparatorMenuItem());
        Parent skinRoot = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/skin.fxml"));
            skinRoot = fxmlLoader.load();

            ObservableMap<String, Object> namespace = fxmlLoader.getNamespace();
            ToggleGroup skinGroup = (ToggleGroup) namespace.get("skinGroup");
            skinGroup.selectedToggleProperty().addListener(
                    (observableValue, toggle, t1) -> {
                        RXToggleButton btn = (RXToggleButton) t1;
                        String skinName = btn.getText();
                        String skinUrl = getClass().getResource("/css/" + skinName + ".css").toExternalForm();
                        //主题切换
                        drawerPane.getScene().getRoot().getStylesheets().setAll(skinUrl);
                        skinPopup.getScene().getRoot().getStylesheets().setAll(skinUrl);
                        soundPopup.getScene().getRoot().getStylesheets().setAll(skinUrl);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        skinPopup.getScene().setRoot(skinRoot);
    }

    /**
     * 初始化弹出声音
     */
    private void initSoundPopup() {
        soundPopup = new ContextMenu(new SeparatorMenuItem());
        Parent soundRoot = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/sound.fxml"));
            soundRoot = fxmlLoader.load();

            //获取所有的组件id和组件
            ObservableMap<String, Object> namespace = fxmlLoader.getNamespace();
            //根据组件fx-id获取组件
            soundSlider = (Slider) namespace.get("soundSlider");
            Label soundNum = (Label) namespace.get("soundNum");
            //将文本的内容和音量的数值绑定                                        对值进行转换
            soundNum.textProperty().bind(soundSlider.valueProperty().asString("%.0f%%"));
            //声音滑块改变时改变player的音量
            soundSlider.valueProperty().addListener(
                    (ob, ov, nv) -> {
                        if (player != null) {
                            player.setVolume(nv.doubleValue() / 100);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        soundPopup.getScene().setRoot(soundRoot);
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        showAnim = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(sliderPane.translateXProperty(), 0)));

        hideAnim = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(sliderPane.translateXProperty(), 300)));
        hideAnim.setOnFinished(e -> drawerPane.setVisible(false));
    }

    @FXML
    void onHideSliderPaneAction(MouseEvent event) {
        showAnim.stop();
        hideAnim.play();
    }

    @FXML
    void onShowSilderPaneAction(MouseEvent event) {
        drawerPane.setVisible(true);
        hideAnim.stop();
        showAnim.play();
    }


    /**
     * 最小化
     */
    @FXML
    void onMiniAction(MouseEvent event) {
        //获取窗口
        Stage stage = findStage();
        //设置最小化
        stage.setIconified(true);
    }

    /**
     * 全屏
     */
    @FXML
    void onFullAction(MouseEvent event) {
        //获取窗口
        Stage stage = findStage();
        //设置与当前状态取反(例如当前是全屏则退出全屏)
        stage.setFullScreen(!stage.isFullScreen());
    }

    /**
     * 关闭
     */
    @FXML
    void onCloseAction(MouseEvent event) {
        Platform.exit();
        //System.exit(0);
    }

    /**
     * 声音弹出行动
     *
     * @param event 事件
     */
    @FXML
    void onSoundPopupAction(MouseEvent event) {
        //获取窗口
        Stage stage = findStage();
        Bounds bounds = soundBtn.localToScreen(soundBtn.getBoundsInLocal());
        soundPopup.show(stage, bounds.getMinX() - 20, bounds.getMinY() - 165);
    }

    /**
     * 皮肤上弹出行动
     *
     * @param event 事件
     */
    @FXML
    void onSkinPopupAction(MouseEvent event) {
        //获取窗口
        Stage stage = findStage();
        Bounds bounds = skinBtn.localToScreen(skinBtn.getBoundsInLocal());
        skinPopup.show(stage, bounds.getMinX() - 135, bounds.getMinY() + 20);
    }

    private Stage findStage() {
        return (Stage) drawerPane.getScene().getWindow();
    }

    /**
     * 添加音乐行动
     *
     * @param event 事件
     */
    @FXML
    void onAddMusicAction(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("mp3", "*.mp3"));
        List<File> filesList = fileChooser.showOpenMultipleDialog(findStage());
        ObservableList<File> items = listView.getItems();
        if (filesList != null) {
            filesList.forEach(file -> {
                if (!items.contains(file)) {
                    items.add(file);
                }
            });
        }
    }


    @FXML
    void onPlayAction(ActionEvent event) {
        if (player != null) {
            if (playBtn.isSelected()) {
                player.play();
            } else {
                player.pause();
            }
        } else {
            playBtn.setSelected(false);
        }
    }

    /**
     * 播放上一首歌曲
     *
     * @param event 事件
     */
    @FXML
    void onPlayPrevAction(MouseEvent event) {
        playPrevMusic();
    }

    private void playPrevMusic() {
        int size = listView.getItems().size();
        if (size < 2) {
            return;
        }
        int index = listView.getSelectionModel().getSelectedIndex();
        //如果是第一首歌,那么上一首歌曲就是播放最后一首歌曲
        index = (index == 0) ? size - 1 : index - 1;
        listView.getSelectionModel().select(index);
    }

    /**
     * 播放上一首歌曲
     *
     * @param event 事件
     */
    @FXML
    void onPlayNextAction(MouseEvent event) {
        playNextMusic();
    }

    private void playNextMusic() {
        int size = listView.getItems().size();
        if (size < 2) {
            return;
        }
        int index = listView.getSelectionModel().getSelectedIndex();
        //如果是最后一首歌,那么下一首歌曲就是播放第一首歌曲
        index = (index == size - 1) ? 0 : index + 1;
        listView.getSelectionModel().select(index);
    }


}
