package function.music;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;
import javax.swing.*;

import function.button.ButtonResponse;


public class MusicChooser extends JPanel {

    private JLabel black_bg_label; // 画面の半透明の黒色の背景
    private final int BLBG_WIDTH = 1920; // 画面の半透明の黒色の背景の高さ
    private final int BLBG_HEIGHT = 1080; // 画面の半透明の黒色の背景の幅
    private final int BLBG_X = 0; // 画面の半透明の黒色の背景の左上端のx座標
    private final int BLBG_Y = 0; // 画面の半透明の黒色の背景の左上端のy座標

    private ImageIcon frame; // 曲選択のフレームの画像
    private JLabel frame_label; // 曲選択のフレームの画像のラベル
    private final int FM_WIDTH = 1000; // 曲選択のフレームの画像の幅
    private final int FM_HEIGHT = 340; // 曲選択のフレームの画像の高さ
    private final int FM_X = 460; // 曲選択のフレームの左上端のx座標
    private final int FM_Y = 540 - FM_HEIGHT/2; // 曲選択のフレームの左上端のy座標

    private String[] msc_names; // 曲名を入れた配列

    private JComboBox msc_combo; // 曲名の選択をさせるコンボボックス
    private final int MSCBX_WIDTH = 800; // 曲名の選択をさせるコンボボックスの高さ
    private final int MSCBX_HEIGHT = 50; // 曲名の選択をさせるコンボボックスの幅
    private final int MSCBX_X = 960 - MSCBX_WIDTH/2; // 曲名の選択をさせるコンボボックスの左上端のx座標
    private final int MSCBX_Y = 450; // 曲名の選択をさせるコンボボックスの左上端のy座標
    protected final Font MSCBX_FONT = new Font("MS Pゴシック", Font.BOLD, 25); // ボックスの中の文字のフォント

    private JButton bk_button; // 曲が流れる画面に戻るボタン
    private final int BKBT_WIDTH = 200; // 曲が流れる画面に戻るボタンの高さ
    private final int BKBT_HEIGHT = 80; // 曲が流れる画面に戻るボタンの幅
    private final int BKBT_X = 1010; // 曲が流れる画面に戻るボタンの左上端のx座標
    private final int BKBT_Y = 590; // 曲が流れる画面に戻るボタンの左上端のy座標

    private JButton dc_button; // 決定ボタン
    private final int DCBT_WIDTH = 200; // 決定ボタンの高さ
    private final int DCBT_HEIGHT = 80; // 決定ボタンの幅
    private final int DCBT_X = 710; // 決定ボタンの左上端のx座標
    private final int DCBT_Y = 590; // 決定ボタンの左上端のy座標

    private String crnt_msc_name; // 現在の曲の名前


    public MusicChooser(ButtonResponse bt) {
        /* @param bt: ボタンが押された際の処理を記述したインターフェース
         */
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // 画面の半透明の黒色の背景のラベルの生成
        black_bg_label = new JLabel();
        // サイズ設定
        black_bg_label.setPreferredSize(new Dimension(BLBG_WIDTH, BLBG_HEIGHT));
        // 色設定
        black_bg_label.setBackground(new Color(0, 0, 0, 128));
        // 位置設定
        black_bg_label.setBounds(BLBG_X, BLBG_Y, BLBG_WIDTH, BLBG_HEIGHT);
        // 可視化する
        black_bg_label.setOpaque(true);

        // フレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsumusic/musicchooser/frame/frame.png");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // 全楽曲名の配列を生成
        createMusicNameArray();

        // 曲名の選択をさせるコンボボックスを生成
        msc_combo = new JComboBox(msc_names);
        // サイズ設定
        msc_combo.setPreferredSize(new Dimension(MSCBX_WIDTH, MSCBX_HEIGHT));
        // 位置設定
        msc_combo.setBounds(MSCBX_X, MSCBX_Y, MSCBX_WIDTH, MSCBX_HEIGHT);
        // フォント設定
        msc_combo.setFont(MSCBX_FONT);

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsumusic/musicchooser/button/bk_button.png");
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
                bt.pushClose();
            }
        });

        // 決定ボタンの生成
        dc_button = new JButton();
        // サイズの設定
        dc_button.setPreferredSize(new Dimension(DCBT_WIDTH, DCBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon dcb_icon = new ImageIcon("image/component/home/anikatsumusic/musicchooser/button/dc_button.png");
        dc_button.setIcon(dcb_icon);
        dc_button.setContentAreaFilled(false);
        dc_button.setBorderPainted(false);
        dc_button.setFocusPainted(false);
        // ボタンの位置設定
        dc_button.setBounds(DCBT_X, DCBT_Y, DCBT_WIDTH, DCBT_HEIGHT);
        // ボタンを押した際の処理
        dc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dc_button pressed");
                bt.pushYes();
            }
        });


        add(msc_combo);
        add(dc_button);
        add(bk_button);
        add(frame_label);
        add(black_bg_label);
    }


    private void createMusicNameArray() {
        /* 曲名のリスト作成を行う
         */
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // 曲名のリストを作成
        List<String> msc_name_list = new ArrayList<String>();
        // 楽曲の音声ファイルを入れたディレクトリから全ての楽曲名を取得していく
        File msc_dir = new File("music\\");
        File msc_file_list[] = msc_dir.listFiles();

        for (int i = 0; i < msc_file_list.length; i++) {
            if(msc_file_list[i].isFile()) {
                String msc_file_name = msc_file_list[i].getName();
                String msc_name = msc_file_name.substring(0, msc_file_name.lastIndexOf('.'));

                msc_name_list.add(msc_name);
            }
        }

        // 配列に変換
        msc_names = msc_name_list.toArray(new String[msc_name_list.size()]);
        // 名前順にソート
        Arrays.sort(msc_names);
    }


    public String[] getMusicNames() {
        return msc_names;
    }


    public String getNextMusicName() {
        /* コンボボックスで指定された次の曲の曲名を取得する
         */
        return (String)msc_combo.getSelectedItem();
    }


    public void setCrntMusicName(final String name) {
        /* 現在の曲の名前をセットする
        @param name: 現在の曲の名前
         */
        crnt_msc_name = name;
        msc_combo.setSelectedItem(crnt_msc_name);
    }


    public void updateMusicNames(final String add_msc_name) {
        /* 楽曲追加の時に呼び出し，msc_namesに追加された楽曲を追加する
         */
        // 楽曲を追加する
        List<String> msc_name_list = new ArrayList<>(Arrays.asList(msc_names));
        //System.out.println(add_msc_name);
        msc_name_list.add(add_msc_name);
        msc_names = msc_name_list.toArray(new String[msc_name_list.size()]);
        // 名前順にソート
        Arrays.sort(msc_names);

        remove(msc_combo);

        // 曲名の選択をさせるコンボボックスを生成
        msc_combo = new JComboBox(msc_names);
        // サイズ設定
        msc_combo.setPreferredSize(new Dimension(MSCBX_WIDTH, MSCBX_HEIGHT));
        // 位置設定
        msc_combo.setBounds(MSCBX_X, MSCBX_Y, MSCBX_WIDTH, MSCBX_HEIGHT);
        // フォント設定
        msc_combo.setFont(MSCBX_FONT);

        add(msc_combo, 0);
    }
}
