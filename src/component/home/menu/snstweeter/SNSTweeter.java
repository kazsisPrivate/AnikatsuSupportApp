package component.home.menu.snstweeter;

import javax.swing.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import function.Loading;
import twitter4j.TwitterException;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.sns.LineUser;
import function.sns.TwitterUser;
import function.Loading;

public class SNSTweeter extends JPanel {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private JLabel black_bg_label; // 画面の半透明の黒色の背景
    private final int BLBG_WIDTH = 1920; // 画面の半透明の黒色の背景の高さ
    private final int BLBG_HEIGHT = 1080; // 画面の半透明の黒色の背景の幅
    private final int BLBG_X = 0; // 画面の半透明の黒色の背景の左上端のx座標
    private final int BLBG_Y = 0; // 画面の半透明の黒色の背景の左上端のy座標

    private ImageIcon frame; // 何か質問等を行う画面のフレームの画像
    private JLabel frame_label; // 何か質問等を行う画面のフレームの画像のラベル
    private final int FM_WIDTH = 1440; // 何か質問等を行う画面のフレームの画像の幅
    private final int FM_HEIGHT = 800; // 何か質問等を行う画面のフレームの画像の高さ
    private final int FM_X = 240; // 何か質問等を行う画面のフレームの左上端のx座標
    private final int FM_Y = FRAME_HEIGHT/2 - FM_HEIGHT/2; // 何か質問等を行う画面のフレームの左上端のy座標

    private final int LB_FONT_SIZE = 50;
    private final Font LB_FONT = new Font("MS Pゴシック", Font.BOLD, LB_FONT_SIZE); // 確認の文のフォント

    private final int SLBT_FONT_SIZE = 50; // (複数の選択肢から)選択ボタンの文字の大きさ
    private final Font SLBT_FONT = new Font("HGPゴシックM", Font.PLAIN, SLBT_FONT_SIZE); // 入力のフィールドのフォント
    
    private JLabel slc_label; // "ツイートするアプリケーション"というラベル
    private final int SLCLB_WIDTH = LB_FONT_SIZE * 16; // "ツイートするアプリケーション"というラベルの幅
    private final int SLCLB_HEIGHT = LB_FONT_SIZE; // "ツイートするアプリケーション"というラベルの高さ
    private final int SLCLB_X = 260; // "ツイートするアプリケーション"というラベルの左上端のx座標
    private final int SLCLB_Y = 160; // "ツイートするアプリケーション"というラベルの左上端のy座標

    private JRadioButton tw_button; // "Twitter"というボタン
    private final int TWBT_WIDTH = SLBT_FONT_SIZE * 4; // "Twitter"ボタンの幅
    private final int TWBT_HEIGHT = SLBT_FONT_SIZE; // "Twitter"ボタンの高さ
    private final int TWBT_X = 530; // "Twitter"ボタンの左上端のx座標
    private final int TWBT_Y = 250; // "Twitter"ボタンの左上端のy座標
    private JRadioButton ln_button; // "LINE"というボタン
    private final int LNBT_WIDTH = SLBT_FONT_SIZE * 3; // "LINE"ボタンの幅
    private final int LNBT_HEIGHT = SLBT_FONT_SIZE; // "LINE"ボタンの高さ
    private final int LNBT_X = TWBT_X + TWBT_WIDTH + 10; // "LINE"ボタンの左上端のx座標
    private final int LNBT_Y = TWBT_Y; // "LINE"ボタンの左上端のy座標
    private JRadioButton both_button; // "Twitter & LINE"というボタン
    private final int BOTHBT_WIDTH = SLBT_FONT_SIZE * 7; // "Twitter & LINE"ボタンの幅
    private final int BOTHBT_HEIGHT = SLBT_FONT_SIZE; // "Twitter & LINE"ボタンの高さ
    private final int BOTHBT_X = LNBT_X + LNBT_WIDTH + 20; // "Twitter & LINE"ボタンの左上端のx座標
    private final int BOTHBT_Y = TWBT_Y; // "Twitter & LINE"ボタンの左上端のy座標

    private final int FD_FONT_SIZE = 25; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private JTextArea msg_area; // 投稿するメッセージを入力するテキストフィールド
    private JScrollPane msg_spanel; // 上のJTextAreaにスクロールバーを付けたパネル
    private final int MSGAR_WIDTH = 750; // 投稿するメッセージを入力するテキストフィールドの幅
    private final int MSGAR_HEIGHT = 400; // 投稿するメッセージを入力するテキストフィールドの高さ
    private final int MSGAR_X = FRAME_WIDTH/2 - MSGAR_WIDTH/2; // 投稿するメッセージを入力するテキストフィールドの左上端のx座標
    private final int MSGAR_Y = 340; // 投稿するメッセージを入力するテキストフィールドの左上端のy座標

    private JButton post_button; // 投稿ボタン
    private final int POSTBT_WIDTH = 110; // 投稿ボタンの幅
    private final int POSTBT_HEIGHT = 60; // 投稿ボタンの高さ
    private final int POSTBT_X = 1310; // 投稿ボタンの左上端のx座標
    private final int POSTBT_Y = 250; // 投稿ボタンの左上端のy座標

    private JLabel error_tw_label; // "※ Twitterへの投稿に失敗しました。"というラベル
    private final int ERTWLB_WIDTH = LB_FONT_SIZE * 18; // "※ Twitterへの投稿に失敗しました。"というラベルの幅
    private final int ERTWLB_HEIGHT = LB_FONT_SIZE; // "※ Twitterへの投稿に失敗しました。"というラベルの高さ
    private final int ERTWLB_X = FRAME_WIDTH/2 - ERTWLB_WIDTH/2; // "※ Twitterへの投稿に失敗しました。"というラベルの左上端のx座標
    private final int ERTWLB_Y = 760; // "※ Twitterへの投稿に失敗しました。"というラベルの左上端のy座標

    private JLabel error_ln_label; // "※ LINEへの投稿に失敗しました。"というラベル
    private final int ERLNLB_WIDTH = LB_FONT_SIZE * 17; // "※ LINEへの投稿に失敗しました。"というラベルの幅
    private final int ERLNLB_HEIGHT = LB_FONT_SIZE; // "※ LINEへの投稿に失敗しました。"というラベルの高さ
    private final int ERLNLB_X = FRAME_WIDTH/2 - ERLNLB_WIDTH/2; // "※ LINEへの投稿に失敗しました。"というラベルの左上端のx座標
    private final int ERLNLB_Y = 760; // "※ LINEへの投稿に失敗しました。"というラベルの左上端のy座標

    private JLabel error_both_label; // "※ TwitterとLINEへの投稿に失敗しました。"というラベル
    private final int ERBHLB_WIDTH = LB_FONT_SIZE * 21; // "※ TwitterとLINEへの投稿に失敗しました。"というラベルの幅
    private final int ERBHLB_HEIGHT = LB_FONT_SIZE; // "※ TwitterとLINEへの投稿に失敗しました。"というラベルの高さ
    private final int ERBHLB_X = FRAME_WIDTH/2 - ERBHLB_WIDTH/2; // "※ TwitterとLINEへの投稿に失敗しました。"というラベルの左上端のx座標
    private final int ERBHLB_Y = 760; // "※ TwitterとLINEへの投稿に失敗しました。"というラベルの左上端のy座標

    private JButton cl_button; // 閉じるボタン
    private final int CLBT_WIDTH = 250; // 閉じるボタンの幅
    private final int CLBT_HEIGHT = 80; // 閉じるボタンの高さ
    private final int CLBT_X = FRAME_WIDTH/2 - CLBT_WIDTH/2; // 閉じるボタンの左上端のx座標
    private final int CLBT_Y = 840; // 閉じるボタンの左上端のy座標

    private JButton twop_button; // ツイッターを開くボタン
    private final int TWOPBT_WIDTH = 270; // ツイッターを開くボタンの幅
    private final int TWOPBT_HEIGHT = 60; // ツイッターを開くボタンの高さ
    private final int TWOPBT_X = 1340; // ツイッターを開くボタンの左上端のx座標
    private final int TWOPBT_Y = 850; // ツイッターを開くボタンの左上端のy座標

    private LineUser ln_user; // LineUserクラスのインスタンス
    private TwitterUser tw_user; // TwitterUserクラスのインスタンス

    private ButtonResponse bt_resp; // コンストラクタで受け取ったmenuでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったmenuでの処理を入れる

    private Loading loading; // Loadingクラスのインスタンス


    public SNSTweeter(final ButtonResponse br, final BasicFunction bf, final TwitterUser tu, final LineUser lu) {
        /* @param bt_resp: 各ボタンを押したときの対応のインターフェイス
        @param pswd: パスワード
         */
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったMenuでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // SNS関連のクラスのオブジェクトの取得
        ln_user = lu;
        tw_user = tu;

        // Loaingクラスのオブジェクトを取得
        loading = new Loading();
        // パネルの位置の設定
        loading.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        add(loading, 0);

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

        // 何か質問等の文のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/menu/snstweeter/frame/frame.png");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // "ツイートするアプリケーション"のラベルを作る
        slc_label = new JLabel("ツイートするアプリケーション");
        // フォント設定
        slc_label.setFont(LB_FONT);
        // 位置設定
        slc_label.setBounds(SLCLB_X, SLCLB_Y, SLCLB_WIDTH, SLCLB_HEIGHT);
        // 可視化する
        slc_label.setOpaque(false);

        // "Twitter"ボタンの生成
        tw_button = new JRadioButton("Twitter");
        // ボタンの表示設定
        tw_button.setContentAreaFilled(false);
        tw_button.setBorderPainted(false);
        tw_button.setFocusPainted(false);
        // フォントの設定
        tw_button.setFont(SLBT_FONT);
        // ボタンの位置設定
        tw_button.setBounds(TWBT_X, TWBT_Y, TWBT_WIDTH, TWBT_HEIGHT);
        // ボタンをデフォルトで選択している状態にする
        tw_button.setSelected(true);
        // ボタンを押した際の処理
        tw_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("twitter_button pressed");

            }
        });

        // "LINE"ボタンの生成
        ln_button = new JRadioButton("LINE");
        // ボタンの表示設定
        ln_button.setContentAreaFilled(false);
        ln_button.setBorderPainted(false);
        ln_button.setFocusPainted(false);
        // フォントの設定
        ln_button.setFont(SLBT_FONT);
        // ボタンの位置設定
        ln_button.setBounds(LNBT_X, LNBT_Y, LNBT_WIDTH, LNBT_HEIGHT);
        // ボタンを押した際の処理
        ln_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("line_button pressed");

            }
        });

        // "Twitter & LINE"ボタンの生成
        both_button = new JRadioButton("Twitter & LINE");
        // ボタンの表示設定
        both_button.setContentAreaFilled(false);
        both_button.setBorderPainted(false);
        both_button.setFocusPainted(false);
        // フォントの設定
        both_button.setFont(SLBT_FONT);
        // ボタンの位置設定
        both_button.setBounds(BOTHBT_X, BOTHBT_Y, BOTHBT_WIDTH, BOTHBT_HEIGHT);
        // ボタンを押した際の処理
        both_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("both_button pressed");

            }
        });

        // 投稿するアプリケーションのボタンをグループ化する
        ButtonGroup app_grp = new ButtonGroup();
        app_grp.add(tw_button);
        app_grp.add(ln_button);
        app_grp.add(both_button);

        // msgを入力するためのTextAreaのオブジェクトを取得、設定する
        msg_area = new JTextArea();
        // 文字の色を設定
        msg_area.setDisabledTextColor(Color.BLACK);
        // 文字をtext area内で折り返すように設定
        msg_area.setLineWrap(true);
        msg_area.setWrapStyleWord(true);
        // フォントを設定
        msg_area.setFont(FIELD_FONT);
        // msgを表示するスクロールパネルのオブジェクトを取得、設定
        msg_spanel = new JScrollPane(msg_area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // サイズの設定
        msg_spanel.setPreferredSize(new Dimension(MSGAR_WIDTH, MSGAR_HEIGHT));
        // 位置設定
        msg_spanel.setBounds(MSGAR_X, MSGAR_Y, MSGAR_WIDTH, MSGAR_HEIGHT);

        // 投稿ボタンの生成
        post_button = new JButton();
        // サイズの設定
        post_button.setPreferredSize(new Dimension(POSTBT_WIDTH, POSTBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon postb_icon = new ImageIcon("image/component/home/menu/snstweeter/button/post_button.png");
        post_button.setIcon(postb_icon);
        post_button.setContentAreaFilled(false);
        post_button.setBorderPainted(false);
        post_button.setFocusPainted(false);
        // ボタンの位置設定
        post_button.setBounds(POSTBT_X, POSTBT_Y, POSTBT_WIDTH, POSTBT_HEIGHT);
        // ボタンを押した際の処理
        post_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("post_button pressed");

                // 指定したSNSに入力内容を投稿するスレッド処理
                Thread post_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        tweetToSNS();
                    }
                });
                // 指定したSNSに入力内容を投稿する
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    post_th.start();
                    post_th.join();
                    //loading.show_loading_th.stop();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                // 入力フィールドを空にする
                msg_area.setText("");
            }
        });

        // "※ Twitterへの投稿に失敗しました。"のラベルを作る
        error_tw_label = new JLabel("※ Twitterへの投稿に失敗しました。");
        // フォント設定
        error_tw_label.setFont(LB_FONT);
        // 位置設定
        error_tw_label.setBounds(ERTWLB_X, ERTWLB_Y, ERTWLB_WIDTH, ERTWLB_HEIGHT);
        // 可視化する
        error_tw_label.setOpaque(false);
        // 赤色にする
        error_tw_label.setForeground(Color.RED);

        // "※ LINEへの投稿に失敗しました。"のラベルを作る
        error_ln_label = new JLabel("※ LINEへの投稿に失敗しました。");
        // フォント設定
        error_ln_label.setFont(LB_FONT);
        // 位置設定
        error_ln_label.setBounds(ERLNLB_X, ERLNLB_Y, ERLNLB_WIDTH, ERLNLB_HEIGHT);
        // 可視化する
        error_ln_label.setOpaque(false);
        // 赤色にする
        error_ln_label.setForeground(Color.RED);

        // "※ TwitterとLINEへの投稿に失敗しました。"のラベルを作る
        error_both_label = new JLabel("※ TwitterとLINEへの投稿に失敗しました。");
        // フォント設定
        error_both_label.setFont(LB_FONT);
        // 位置設定
        error_both_label.setBounds(ERBHLB_X, ERBHLB_Y, ERBHLB_WIDTH, ERBHLB_HEIGHT);
        // 可視化する
        error_both_label.setOpaque(false);
        // 赤色にする
        error_both_label.setForeground(Color.RED);

        // 閉じるボタンの生成
        cl_button = new JButton();
        // サイズの設定
        cl_button.setPreferredSize(new Dimension(CLBT_WIDTH, CLBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon clb_icon = new ImageIcon("image/component/home/menu/snstweeter/button/cl_button.png");
        cl_button.setIcon(clb_icon);
        cl_button.setContentAreaFilled(false);
        cl_button.setBorderPainted(false);
        cl_button.setFocusPainted(false);
        // ボタンの位置設定
        cl_button.setBounds(CLBT_X, CLBT_Y, CLBT_WIDTH, CLBT_HEIGHT);
        // ボタンを押した際の処理
        cl_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("cl_button pressed");
                // 状態を初期状態に戻しておく
                msg_area.setText("");
                remove(error_tw_label);
                remove(error_ln_label);
                remove(error_both_label);
                // 閉じるボタンを押した際の処理をする
                bt_resp.pushClose();
            }
        });

        // ツイッターを開くボタンの生成
        twop_button = new JButton();
        // サイズの設定
        twop_button.setPreferredSize(new Dimension(TWOPBT_WIDTH, TWOPBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon twopb_icon = new ImageIcon("image/component/home/menu/snstweeter/button/twop_button.png");
        twop_button.setIcon(twopb_icon);
        twop_button.setContentAreaFilled(false);
        twop_button.setBorderPainted(false);
        twop_button.setFocusPainted(false);
        // ボタンの位置設定
        twop_button.setBounds(TWOPBT_X, TWOPBT_Y, TWOPBT_WIDTH, CLBT_HEIGHT);
        // ボタンを押した際の処理
        twop_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("twop_button pressed");

                // サイトを開くスレッド処理
                Thread twop_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI("https://twitter.com/"));
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        } catch (URISyntaxException urie) {
                            urie.printStackTrace();
                        }
                    }
                });
                // Twitterのサイトを開く
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    twop_th.start();
                    twop_th.join();
                    //loading.show_loading_th.stop();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        add(slc_label);
        add(tw_button);
        add(ln_button);
        add(both_button);
        add(msg_spanel);
        add(post_button);
        add(cl_button);
        add(twop_button);
        add(frame_label);
        add(black_bg_label);
    }


    private void tweetToSNS() {
        /* 指定したSNSに入力した内容を投稿するメソッド
         */
        // 表示されているエラー文を消す
        remove(error_tw_label);
        remove(error_ln_label);
        remove(error_both_label);

        if (tw_button.isSelected()) { // Twitterを選択したとき
            try {
                tw_user.post(msg_area.getText());
            } catch (TwitterException e) {
                add(error_tw_label, 0);
            }
        }
        else if (ln_button.isSelected()) { // LINEを選択したとき
            try {
                ln_user.post(msg_area.getText());
            } catch (IOException e) {
                add(error_ln_label, 0);
            }
        }
        else { // Twitter&LINEを選択したとき
            // 各SNSに投稿できたかどうかのフラグ
            boolean has_tweeted_to_tw = true; // twitterに投稿したかどうか
            boolean has_tweeted_to_ln = true; // lineに投稿したかどうか

            // Twitterに投稿する
            try {
                tw_user.post(msg_area.getText());
            } catch (TwitterException e) {
                has_tweeted_to_tw = false;
            }

            // LINEに投稿する
            try {
                ln_user.post(msg_area.getText());
            } catch (IOException e) {
                has_tweeted_to_ln = false;
            }

            // ログの追加とエラー表示
            if (has_tweeted_to_tw) {
                if (has_tweeted_to_ln) { // TwitterとLineにツイートを投稿したとき
                    bc_func.addLog("入力したツイートを、TwitterとLINEに投稿しました。");
                }
                else { // Twitterのみにツイートを投稿したとき
                    add(error_ln_label, 0);
                    bc_func.addLog("入力したツイートを、Twitterに投稿しました。LINEへの投稿は、失敗しました。");
                }
            }
            else if (has_tweeted_to_ln) { // Lineのみにツイートを投稿したとき
                add(error_tw_label, 0);
                bc_func.addLog("入力したツイートを、LINEに投稿しました。Twitterへの投稿は、失敗しました。");
            }
            else { // TwitterとLine両方に投稿できなかったとき
                add(error_both_label, 0);
                bc_func.addLog("入力したツイートの投稿に失敗しました。");
            }

        }

        repaint();
    }
}
