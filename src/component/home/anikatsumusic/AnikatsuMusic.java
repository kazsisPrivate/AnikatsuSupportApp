package component.home.anikatsumusic;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import component.home.Background;
import function.BasicFunction;
import function.button.ButtonResponse;
import function.music.MusicPlayer;
import function.music.MusicChooser;


public class AnikatsuMusic extends JPanel implements ActionListener, ChangeListener {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel msc_icon_label; // AnikatsuMusicのアイコンの画像のラベル
    private ImageIcon msc_icon; // AnikatsuMusicのアイコンの画像
    private final int ICON_WIDTH = 270; // アイコン画像の幅
    private final int ICON_HEIGHT = 130; // アイコン画像の高さ
    private final int ICON_X = 5; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private JButton pl_button; // 音楽の再生ボタン
    private final int PLBT_WIDTH = 150; // 音楽の再生ボタンの高さ
    private final int PLBT_HEIGHT = 100; // 音楽の再生ボタンの幅
    private final int PLBT_X = 480 - PLBT_WIDTH/2; // 音楽の再生ボタンの左上端のx座標
    private final int PLBT_Y = 720; // 音楽の再生ボタンの左上端のy座標

    private JButton st_button; // 音楽の一時停止ボタン
    private final int STBT_WIDTH = 150; // 音楽の一時停止ボタンの高さ
    private final int STBT_HEIGHT = 100; // 音楽の一時停止ボタンの幅
    private final int STBT_X = 480 - STBT_WIDTH/2; // 音楽の一時停止ボタンの左上端のx座標
    private final int STBT_Y = 720; // 音楽の一時停止ボタンの左上端のy座標

    private JButton slc_button; // 音楽の選択ボタン
    private final int SLCBT_WIDTH = 200; // 音楽の選択ボタンの高さ
    private final int SLCBT_HEIGHT = 80; // 音楽の選択ボタンの幅
    private final int SLCBT_X = 230; // 音楽の選択ボタンの左上端のx座標
    private final int SLCBT_Y = 880; // 音楽の選択ボタンの左上端のy座標

    private JButton reg_button; // 楽曲登録ボタン
    private final int REGBT_WIDTH = 100; // 楽曲登録ボタンの高さ
    private final int REGBT_HEIGHT = 80; // 楽曲登録ボタンの幅
    private final int REGBT_X = 780; // 楽曲登録ボタンの左上端のx座標
    private final int REGBT_Y = 70; // 楽曲登録ボタンの左上端のy座標

    private JButton bk_button; // メニューに戻るボタン
    private final int BKBT_WIDTH = 200; // メニューに戻るボタンの高さ
    private final int BKBT_HEIGHT = 80; // メニューに戻るボタンの幅
    private final int BKBT_X = 530; // メニューに戻るボタンの左上端のx座標
    private final int BKBT_Y = 880; // メニューに戻るボタンの左上端のy座標

    private JSlider slider; // 音楽の現在再生している場所を表すスライダー
    private final int SLD_WIDTH = 660; // 画面の半透明の白色の背景の高さ
    private final int SLD_HEIGHT = 150; // 画面の半透明の白色の背景の幅
    private final int SLD_X = 150; // 画面の半透明の白色の背景の左上端のx座標
    private final int SLD_Y = 590; // 画面の半透明の白色の背景の左上端のy座標

    private MusicPlayer msc_player;
    private MusicChooser msc_chooser;

    private String crnt_music_name; // 現在の曲の入っているディレクトリの番号
    private String[] msc_names; // 曲名を入れた配列
    private List<String> cvrfile_name_list; // 曲のジャケット画像のファイル名を入れたリスト


    private JLabel msc_cover_label; // 曲のジャケットの画像のラベル
    private ImageIcon msc_cover; // 曲のジャケットの画像
    private final int MSCCV_WIDTH = 520; // 曲のジャケットの画像の幅
    private final int MSCCV_HEIGHT = 520; // 曲のジャケットの画像の高さ
    private final int MSCCV_X = 220; // 曲のジャケットの画像の左上端のx座標
    private final int MSCCV_Y = 90; // 曲のジャケットの画像の左上端のy座標

    private JLabel crnt_time_label; // 再生時間文字列のラベル
    private final int CRNTTM_WIDTH = 120; // 再生時間の文字列の幅
    private final int CRNTTM_HEIGHT = 30; // 再生時間の文字列の高さ
    private final int CRNTTM_X = 720; // 再生時間の文字列の左上端のx座標
    private final int CRNTTM_Y = 680; // 再生時間の文字列の左上端のy座標

    private final Font CRNTTM_FONT = new Font("MS Pゴシック", Font.BOLD, CRNTTM_HEIGHT); // キャラクターの台詞を表示する際のフォント

    private javax.swing.Timer upd_timer; // 更新update()を呼び出すためのタイマー
    private final int UPDATE_INTERVAL = 100; // 更新頻度(ミリ秒)

    private int msc_duration; // 現在の曲の長さ

    private ButtonResponse bt_resp; // 引数で受け取ったボタンを押した際のhomeでの処理
    private BasicFunction bc_func;


    public AnikatsuMusic(final ButtonResponse br, final BasicFunction bf, final Background bg) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // 引数で受け取ったボタンを押した際のhomeでの処理を格納
        bt_resp = br;
        bc_func = bf;

        // AnikatsuMusicのアイコンのラベルのインスタンスを取得、設定
        msc_icon = new ImageIcon("image/component/home/anikatsumusic/icon/icon.png");
        msc_icon_label = new JLabel(msc_icon);
        msc_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // menu画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsumusic/frame/frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // MusicChooserクラスのインスタンスを取得
        msc_chooser = new MusicChooser(new MCResponse());
        // 位置設定
        msc_chooser.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // MusicPlayerクラスのインスタンスを取得
        msc_names = msc_chooser.getMusicNames();
        msc_player = new MusicPlayer(msc_names); //, msc_chooser.getMusicMap());

        // 現在の曲名を取得する
        crnt_music_name = msc_player.getCrntMusicName();
        // MusicChooserに現在の曲名を渡す
        msc_chooser.setCrntMusicName(crnt_music_name);
        // 現在の曲の入っているディレクトリの番号をセットする
        //crnt_music_dir = msc_chooser.getNextMusicDirNum();

        // 現在の曲のジャケットの画像ファイルのパスを格納
        cvrfile_name_list = new ArrayList<>();
        String cvr_dir_path = "image/component/home/anikatsumusic/cover/";
        File cvr_dir = new File(cvr_dir_path);
        File[] cvr_file_array = cvr_dir.listFiles();
        // ジャケット画像のファイル名一覧を作成
        if(cvr_file_array != null) {
            for(int i = 0; i < cvr_file_array.length; i++) {
                cvrfile_name_list.add(cvr_file_array[i].getName());
            }
        }

        // 現在の曲のカバー画像をセット
        setCrntMusicCover();

        // 音楽の才再生位置を表すスライダーのインスタンスを作成
        slider = new JSlider();
        // スライダーのサイズを設定
        slider.setPreferredSize(new Dimension(SLD_WIDTH, SLD_HEIGHT));
        // スライダーの位置を設定
        slider.setBounds(SLD_X, SLD_Y, SLD_WIDTH, SLD_HEIGHT);
        // スライダーの背景を消す
        slider.setOpaque(false);
        // スライダーの最小値、最大値を設定する
        msc_duration = msc_player.getMusicDuration();
        slider.setMinimum(0);
        slider.setMaximum(msc_duration);
        // スライダーの初期値を設定
        slider.setValue(0);
        // スライダーが変更されたときにメソッドstateChangedを呼び出すようにする
        slider.addChangeListener(this);

        // 最初の再生時間のラベルを生成
        createCrntTimeLabel(0);

        // 音楽の再生ボタンの生成
        pl_button = new JButton();
        // サイズの設定
        pl_button.setPreferredSize(new Dimension(PLBT_WIDTH, PLBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon plb_icon = new ImageIcon("image/component/home/anikatsumusic/button/pl_button.png");
        pl_button.setIcon(plb_icon);
        pl_button.setContentAreaFilled(false);
        pl_button.setBorderPainted(false);
        pl_button.setFocusPainted(false);
        // ボタンの位置設定
        pl_button.setBounds(PLBT_X, PLBT_Y, PLBT_WIDTH, PLBT_HEIGHT);
        // ボタンを押した際の処理
        pl_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pl_button pressed");
                // 再生ボタンを取り除き、一時停止ボタンを追加する
                remove(pl_button);
                add(st_button, 0);
                repaint();
                // 曲を再生する
                msc_player.play();
            }
        });

        // 音楽の一時停止ボタンの生成
        st_button = new JButton();
        // サイズの設定
        st_button.setPreferredSize(new Dimension(STBT_WIDTH, STBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon stb_icon = new ImageIcon("image/component/home/anikatsumusic/button/st_button.png");
        st_button.setIcon(stb_icon);
        st_button.setContentAreaFilled(false);
        st_button.setBorderPainted(false);
        st_button.setFocusPainted(false);
        // ボタンの位置設定
        st_button.setBounds(STBT_X, STBT_Y, STBT_WIDTH, STBT_HEIGHT);
        // ボタンを押した際の処理
        st_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("st_button pressed");
                // 一時停止ボタンを取り除き、再生ボタンを追加する
                remove(st_button);
                add(pl_button, 0);
                repaint();
                // 曲を一時停止する
                msc_player.pause();
            }
        });

        // 曲選択ボタンの生成
        slc_button = new JButton();
        // サイズの設定
        slc_button.setPreferredSize(new Dimension(SLCBT_WIDTH,SLCBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon slcb_icon = new ImageIcon("image/component/home/anikatsumusic/button/slc_button.png");
        slc_button.setIcon(slcb_icon);
        slc_button.setContentAreaFilled(false);
        slc_button.setBorderPainted(false);
        slc_button.setFocusPainted(false);
        // ボタンの位置設定
        slc_button.setBounds(SLCBT_X, SLCBT_Y, SLCBT_WIDTH, SLCBT_HEIGHT);
        // ボタンを押した際の処理
        slc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("slc_button pressed");
                // 部品を一時的にいじれない状態にする
                makeComponetsDisabled();
                // upd_timerを一時的に止める
                upd_timer.stop();

                // 曲の選択画面を表示する
                add(msc_chooser, 0);
                repaint();
            }
        });

        // 楽曲登録ボタンの生成
        reg_button = new JButton();
        // サイズの設定
        reg_button.setPreferredSize(new Dimension(REGBT_WIDTH, REGBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon regb_icon = new ImageIcon("image/component/home/anikatsumusic/button/reg_button.png");
        reg_button.setIcon(regb_icon);
        reg_button.setContentAreaFilled(false);
        reg_button.setBorderPainted(false);
        reg_button.setFocusPainted(false);
        // ボタンの位置設定
        reg_button.setBounds(REGBT_X, REGBT_Y, REGBT_WIDTH, REGBT_HEIGHT);
        // ボタンを押した際の処理
        reg_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("reg_button pressed");
                // 楽曲の追加処理
                addMusicFile();
            }
        });

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsumusic/button/bk_button.png");
        bk_button.setIcon(bkb_icon);
        bk_button.setContentAreaFilled(false);
        bk_button.setBorderPainted(false);
        bk_button.setFocusPainted(false);
        // ボタンの位置設定
        bk_button.setBounds(BKBT_X, BKBT_Y, BKBT_WIDTH, BKBT_HEIGHT);
        // ボタンを押した際の処理
        bk_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bk_button pressed");

                // homeでの処理をする
                bc_func.changeChrModeToAutoMode();
                bt_resp.pushClose();
            }
        });

        // updateTimerの設定を行う
        upd_timer = new javax.swing.Timer(UPDATE_INTERVAL, this);
        upd_timer.start();

        add(crnt_time_label);
        add(slider);
        add(pl_button);
        add(slc_button);
        add(reg_button);
        add(bk_button);
        add(msc_cover_label);
        add(msc_icon_label);
        add(frame_label);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        /* タイマーの更新イベント
         */
        updateSlider();
    }


    private void updateSlider() {
        int crnt_time = msc_player.getMusicCrntTime();

        if (crnt_time >= msc_duration) { // 音楽が終了したとき
            if (!slider.getValueIsAdjusting() && st_button.isShowing()) { // スライダーを手動で動かされてなく、再生中のとき
                // ループ処理
                slider.setValue(0);
                msc_player.setMusicCrntTime(0);
                msc_player.play();
                // 初期化が終わるまで待つ（再生できる状態になるまで待つ）
                do {
                    crnt_time = msc_player.getMusicCrntTime();
                } while(crnt_time != 0);
            }
        }
        else {
            slider.setValue(crnt_time);
        }

        // 再生時間を更新する
        remove(crnt_time_label);
        createCrntTimeLabel(slider.getValue());
        add(crnt_time_label,0);
        repaint();
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        /* スライダーをいじられたとき
         */
        msc_player.setMusicCrntTime(slider.getValue());
    }


    private void makeComponetsDisabled() {
        /* 部品をいじれない状態にする
         */
        pl_button.setEnabled(false);
        st_button.setEnabled(false);
        slc_button.setEnabled(false);
        reg_button.setEnabled(false);
        bk_button.setEnabled(false);
        slider.setEnabled(false);
    }


    private void makeComponetsEnabled() {
        /* 部品をいじれる状態にする
         */
        pl_button.setEnabled(true);
        st_button.setEnabled(true);
        slc_button.setEnabled(true);
        reg_button.setEnabled(true);
        bk_button.setEnabled(true);
        slider.setEnabled(true);
    }


    private class MCResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            // 現在の曲のリソースを解放する
            msc_player.disposeCrntMusic();
            // 次の曲をセットする
            crnt_music_name = msc_chooser.getNextMusicName();
            msc_player.setCrntMusic(crnt_music_name);

            // 次の曲のカバー画像をセットする
            remove(msc_cover_label);
            setCrntMusicCover();
            add(msc_cover_label, 0);

            // MusicChooserに現在の曲名を渡す
            msc_chooser.setCrntMusicName(msc_player.getCrntMusicName());

            // スライダーの最小値、最大値を設定する
            do {
                msc_duration = msc_player.getMusicDuration();
            } while(msc_duration == 0);
            slider.setMinimum(0);
            slider.setMaximum(msc_duration);
            slider.setValue(0);

            // キャラクターに現在の曲名を渡す
            bc_func.charaSpeak(msc_player.getCrntMusicName()+"   ", "誇張");

            // 選択画面を取り除く
            remove(msc_chooser);
            // 一時停止ボタンを取り除き、再生ボタンを追加する
            remove(st_button);
            add(pl_button, 0);

            // 部品をいじれる状態に戻す
            makeComponetsEnabled();
            // upd_timerを再スタートさせる
            upd_timer.start();

            repaint();
        }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            // MusicChooserに現在の曲名を渡す
            // (コンボボックスの選択されているやつを元に戻すため)
            msc_chooser.setCrntMusicName(msc_player.getCrntMusicName());
            // 選択画面を取り除く
            remove(msc_chooser);

            // 部品をいじれる状態に戻す
            makeComponetsEnabled();
            // upd_timerを再スタートさせる
            upd_timer.start();

            repaint();
        }
    }


    private void createCrntTimeLabel(int time) {
        /* 現在の再生時間を表す文字列のラベルを作成する
        @param time: 現在の時間(秒)
         */
        // 秒から分,秒に変える
        int minute, second;
        if (time != 0) {
            minute = time / 60;
            second = time - minute*60;
        }
        else {
            minute = 0;
            second = 0;
        }

        // 時間を文字列に変える
        String crnt_time;
        if (second >= 10) {
            crnt_time = minute + ":" + second;
        }
        else {
            crnt_time = minute + ":0" + second;
        }

        // 再生時間のラベルを作る
        crnt_time_label = new JLabel(crnt_time);
        // フォントを設定
        crnt_time_label.setFont(CRNTTM_FONT);
        // 位置設定
        crnt_time_label.setBounds(CRNTTM_X, CRNTTM_Y, CRNTTM_WIDTH, CRNTTM_HEIGHT);
    }


    private void setCrntMusicCover() {
        // 現在の曲のジャケットの画像ファイルのパスを格納
        String cvr_dir_path = "image/component/home/anikatsumusic/cover/";

        if (cvrfile_name_list.contains(crnt_music_name + ".jpg")) {
            String cvr_path = cvr_dir_path + crnt_music_name + ".jpg";

            // 画像を取得し、リサイズする
            ImageIcon icon = new ImageIcon(cvr_path);
            Image img = icon.getImage().getScaledInstance(MSCCV_WIDTH, MSCCV_HEIGHT, Image.SCALE_SMOOTH);
            msc_cover = new ImageIcon(img);
            // ラベルを作成
            msc_cover_label = new JLabel(msc_cover);
            // 位置設定
            msc_cover_label.setBounds(MSCCV_X, MSCCV_Y, MSCCV_WIDTH, MSCCV_HEIGHT);
        }
        else if (cvrfile_name_list.contains(crnt_music_name + ".png")) {
            String cvr_path = cvr_dir_path + crnt_music_name + ".png";

            // 画像を取得し、リサイズする
            ImageIcon icon = new ImageIcon(cvr_path);
            Image img = icon.getImage().getScaledInstance(MSCCV_WIDTH, MSCCV_HEIGHT, Image.SCALE_SMOOTH);
            msc_cover = new ImageIcon(img);
            // ラベルを作成
            msc_cover_label = new JLabel(msc_cover);
            // 位置設定
            msc_cover_label.setBounds(MSCCV_X, MSCCV_Y, MSCCV_WIDTH, MSCCV_HEIGHT);
        }
        else {
            JLabel label = new JLabel("ジャケット画像が見当たりません");
            label.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, label);
        }
    }


    public String getCrntMusicName() {
        /* 現在セットされている曲の曲名を返す
         */
        return msc_player.getCrntMusicName();
    }


    private class MscFileFilter extends FileFilter {
        /* 楽曲登録の際のファイルエクスプローラでのフィルタ処理を行うクラス
         */
        public boolean accept(File f){
            if (f.isDirectory()){
                return true;
            }

            String ext = getExtension(f);
            if (ext != null){
                if (ext.equals("m4a") ){
                    return true;
                }else{
                    return false;
                }
            }

            return false;
        }

        public String getDescription(){
            return "音楽ファイル(mp3 or m4a)";
        }

        private String getExtension(File f){
            String ext = null;
            String filename = f.getName();
            int dotIndex = filename.lastIndexOf('.');

            if ((dotIndex > 0) && (dotIndex < filename.length() - 1)){
                ext = filename.substring(dotIndex + 1).toLowerCase();
            }

            return ext;
        }
    }


    private class CvrFileFilter extends FileFilter {
        /* 楽曲ジャケット画像登録の際のファイルエクスプローラでのフィルタ処理を行うクラス
         */
        public boolean accept(File f){
            if (f.isDirectory()){
                return true;
            }

            String ext = getExtension(f);
            if (ext != null){
                if (ext.equals("jpg") || ext.equals("png") ){
                    return true;
                }else{
                    return false;
                }
            }

            return false;
        }

        public String getDescription(){
            return "画像ファイル(jpg or png)";
        }

        private String getExtension(File f){
            String ext = null;
            String filename = f.getName();
            int dotIndex = filename.lastIndexOf('.');

            if ((dotIndex > 0) && (dotIndex < filename.length() - 1)){
                ext = filename.substring(dotIndex + 1).toLowerCase();
            }

            return ext;
        }
    }


    private void addMusicFile() {
        /* 楽曲の追加処理
         */
        // 登録する楽曲のファイルを選ぶ
        // 表示するファイルエクスプローラの設定
        JFileChooser msc_filechooser = new JFileChooser();
        msc_filechooser.addChoosableFileFilter(new MscFileFilter());
        msc_filechooser.setAcceptAllFileFilterUsed(false);
        msc_filechooser.setDialogTitle("登録したい音楽ファイル(mp3 or m4a)の選択");

        // 楽曲ファイルを選択した後の処理
        int is_selected_msc = msc_filechooser.showOpenDialog(frame_label);
        if (is_selected_msc == JFileChooser.APPROVE_OPTION){
            File msc_file = msc_filechooser.getSelectedFile();
            String msc_file_name = msc_file.getName();
            String msc_name = msc_file_name.substring(0, msc_file_name.lastIndexOf('.'));
            System.out.println(msc_file_name);

            if (Arrays.asList(msc_names).contains(msc_name)) {
                JLabel label = new JLabel("選択した楽曲はすでに登録されています");
                label.setForeground(Color.RED);
                JOptionPane.showMessageDialog(this, label);
            }
            else {
                // 登録するジャケット画像のファイルを選ぶ
                // 表示するファイルエクスプローラの設定
                JFileChooser cvr_filechooser = new JFileChooser();
                cvr_filechooser.addChoosableFileFilter(new CvrFileFilter());
                cvr_filechooser.setAcceptAllFileFilterUsed(false);
                cvr_filechooser.setDialogTitle("登録したいジャケット画像(jpg or png)の選択");

                // 楽曲ファイルを選択した後の処理
                int is_selected_cvr = cvr_filechooser.showOpenDialog(frame_label);
                if (is_selected_cvr == JFileChooser.APPROVE_OPTION) {
                    // 登録する楽曲の音声ファイルとジャケット画像をコピーして登録する
                    String crnt_dir_path = Paths.get("").toAbsolutePath().toString();
                    String from_mscdir_path = msc_file.getParent();
                    String to_mscdir_path = crnt_dir_path + "\\music";
                    String to_cvrdir_path = crnt_dir_path + "\\image\\component\\home\\anikatsumusic\\cover";

                    File cvr_file = cvr_filechooser.getSelectedFile();
                    String from_cvr_file_name = cvr_file.getName();
                    String to_cvr_file_name = msc_name
                            + from_cvr_file_name.substring(from_cvr_file_name.lastIndexOf('.'));
                    String from_cvrdir_path = cvr_file.getParent();

                    // 選択した楽曲とジャケットのファイルをコピーする
                    copyFile(from_mscdir_path, to_mscdir_path, msc_file_name);
                    copyFile(from_cvrdir_path, to_cvrdir_path, from_cvr_file_name, to_cvr_file_name);

                    // ジャケットのファイル名一覧に追加する
                    cvrfile_name_list.add(to_cvr_file_name);

                    // 追加した楽曲を選択できるようにする
                    msc_chooser.updateMusicNames(msc_name);
                    msc_player.setMscNames(msc_chooser.getMusicNames());
                }
                else if (is_selected_cvr == JFileChooser.CANCEL_OPTION) {
                    System.out.println("キャンセルされました");
                }
                else if (is_selected_cvr == JFileChooser.ERROR_OPTION) {
                    JLabel label = new JLabel("ファイル選択でなんかエラーありました");
                    label.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(this, label);
                    System.out.println("エラー又は取消しがありました");
                }
            }
        }
        else if (is_selected_msc == JFileChooser.CANCEL_OPTION){
            System.out.println("キャンセルされました");
        }
        else if (is_selected_msc == JFileChooser.ERROR_OPTION){
            JLabel label = new JLabel("ファイル選択でなんかエラーありました");
            label.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, label);
            System.out.println("エラー又は取消しがありました");
            //label.setText("エラー又は取消しがありました");
        }
    }


    private void copyFile(String from_dir_path, final String to_dir_path, final String file_name) {
        /* 指定されたパスのファイルをコピーする
        @param from_dir_path: コピーするファイルのあるディレクトリのpath
        @param to_dir_path: コピーするファイルを置くディレクトリのpath
        @param file_name: コピーするファイル名（拡張子込み）
         */
        char last_char = from_dir_path.charAt(from_dir_path.length() - 1);
        if (last_char == '\\' ) {   // ドライブ直下のファイルのとき
            from_dir_path = from_dir_path.substring(0, from_dir_path.length()-1);
        }
        String from_file_path = from_dir_path + "\\" + file_name;
        String to_file_path = to_dir_path + "\\" + file_name;

        try {
            Files.copy(Paths.get(from_file_path), Paths.get(to_file_path));
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private void copyFile(String from_dir_path, final String to_dir_path,
                          final String from_file_name, final String to_file_name) {
        /* 指定されたパスのファイルをコピーする
        @param from_dir_path: コピーするファイルのあるディレクトリのpath
        @param to_dir_path: コピーするファイルを置くディレクトリのpath
        @param from_file_name: コピーするファイル名（拡張子込み）
        @param to_file_name: コピー後のファイル名（拡張子込み）
         */
        char last_char = from_dir_path.charAt(from_dir_path.length() - 1);
        if (last_char == '\\' ) {   // ドライブ直下のファイルのとき
            from_dir_path = from_dir_path.substring(0, from_dir_path.length()-1);
        }
        String from_file_path = from_dir_path + "\\" + from_file_name;
        String to_file_path = to_dir_path + "\\" + to_file_name;

        try {
            Files.copy(Paths.get(from_file_path), Paths.get(to_file_path));
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private void copyMusicFiles() {
        String crnt_dir_path = Paths.get("").toAbsolutePath().toString();
        String to_mscdir_path = crnt_dir_path + "\\music";
        String to_cvrdir_path = crnt_dir_path + "\\image\\component\\home\\anikatsumusic\\cover";
        String from_cvrdir_path = to_cvrdir_path;

        for (int i = 0; i < 29; i++) {
            String from_mscdir_path = crnt_dir_path + "\\music\\" + i;
            String from_cvr_file_name = i + ".jpg";
            //System.out.println(from_mscdir_path);
            File dir = new File(from_mscdir_path);
            File[] list = dir.listFiles();
            if(list != null) {

                for(int j=0; j<list.length; j++) {

                    //ファイルの場合
                    if(list[j].isFile()) {
                        String msc_file_name = list[j].getName();
                        String to_cvr_file_name = msc_file_name.substring(0, msc_file_name.lastIndexOf('.'))
                                 + ".jpg";

                        copyFile(from_mscdir_path, to_mscdir_path, msc_file_name);
                        copyFile(from_cvrdir_path, to_cvrdir_path, from_cvr_file_name, to_cvr_file_name);
                    }
                }
            } else {
                System.out.println("null");
            }
        }

        System.out.println("fin");
    }
}
