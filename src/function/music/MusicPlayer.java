package function.music;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;
import java.util.Random;
import javafx.util.Duration;


public class MusicPlayer extends JFXPanel {

    private MediaPlayer mplayer;

    private String msc_names[]; // 曲名を入れた配列

    private String crnt_msc_name; // そのとき流している曲の名前を入れる

    private HashMap<String, Integer> msc_map; // 曲名をキーとした、その曲の入っているディレクトリの番号のマップ


    public MusicPlayer(final String names[]) {  //, final HashMap<String, Integer> map) {
        // パラメータで受け取った曲名一覧をmsc_namesに格納する
        msc_names = names;

        // 最初に流す曲をセットする
        crnt_msc_name = chooseFirstMusic();
        setCrntMusic(crnt_msc_name);
    }


    public void play() {
        /* 曲の再生
         */
        mplayer.play();
    }


    public void pause() {
        /* 曲の一時停止
         */
        mplayer.pause();
    }


    public void stop() {
        /* 曲の停止
         */
        mplayer.stop();
    }


    private String chooseFirstMusic() {
        /* 最初に流す曲をランダムで決める
         */
        // 乱数で最初に流す曲を決める
        Random random = new Random();
        int msc_num = random.nextInt(msc_names.length);

        return msc_names[msc_num];
    }


    public void setCrntMusic(final String name) {
        /* 現在の曲をmplayerにセットする
        @param name: 次の曲のタイトル
         */
        // 現在の曲の名前にセットする
        crnt_msc_name = name;

        // その曲のパスを格納
        String path = "music/" + crnt_msc_name + ".m4a";
        // mplayerに曲をセットする
        Media media = new Media(new File(path).toURI().toString());
        mplayer = new MediaPlayer(media);
        mplayer.setCycleCount(MediaPlayer.INDEFINITE);
        // 音量調節
        mplayer.setVolume(0.5);
    }


    public void disposeCrntMusic() {
        /* 現在の曲のリソースを解放する
         */
        mplayer.dispose();
    }


    public String getCrntMusicName() {
        return crnt_msc_name;
    }


    public int getMusicDuration() {
        /* 現在の曲の長さを返す
         */
        System.out.println((int)mplayer.getStopTime().toSeconds());
        return (int)mplayer.getMedia().getDuration().toSeconds();
    }


    public int getMusicCrntTime() {
        /* 現在の曲の位置を返す
         */
        return (int)mplayer.getCurrentTime().toSeconds();
    }


    public void setMusicCrntTime(int time) {
        /* 現在の曲の位置をセットする
         */
        mplayer.setStartTime(Duration.seconds(time));
    }


    public void setMscNames(final String[] msc_names) {
        this.msc_names = msc_names;
    }
}
