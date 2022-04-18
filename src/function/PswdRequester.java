package function;

import javax.swing.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import function.button.ButtonResponse;


public class PswdRequester extends JPanel{

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
    private final int FM_HEIGHT = 500; // 何か質問等を行う画面のフレームの画像の高さ
    private final int FM_X = 240; // 何か質問等を行う画面のフレームの左上端のx座標
    private final int FM_Y = 290; // 何か質問等を行う画面のフレームの左上端のy座標

    private final int LB_FONT_SIZE = 50;
    private final Font LB_FONT = new Font("MS Pゴシック", Font.BOLD, LB_FONT_SIZE); // 確認の文のフォント

    private JLabel request_label; // "パスワードを入力してください。"というラベル
    private final int RQTLB_WIDTH = LB_FONT_SIZE * 16; // "パスワードを入力してください。"というラベルの幅
    private final int RQTLB_HEIGHT = LB_FONT_SIZE; // "パスワードを入力してください。"というラベルの高さ
    private final int RQTLB_X = 260; // "パスワードを入力してください。"というラベルの左上端のx座標
    private final int RQTLB_Y = 310; // "パスワードを入力してください。"というラベルの左上端のy座標

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント
    
    private JPasswordField pswd_field; // パスワードを入力するテキストフィールド
    private final int PSWDFD_WIDTH = LB_FONT_SIZE * 15; // パスワードを入力するテキストフィールドの幅
    private final int PSWDFD_HEIGHT = LB_FONT_SIZE; // パスワードを入力するテキストフィールドの高さ
    private final int PSWDFD_X = FRAME_WIDTH/2 - PSWDFD_WIDTH/2 - PSWDFD_WIDTH/10; // パスワードを入力するテキストフィールドの左上端のx座標
    private final int PSWDFD_Y = 450; // パスワードを入力するテキストフィールドの左上端のy座標

    private JButton conf_button; // 確認ボタン
    private final int CONFBT_WIDTH = 110; // 確認ボタンの幅
    private final int CONFBT_HEIGHT = 60; // 確認ボタンの高さ
    private final int CONFBT_X = PSWDFD_X + PSWDFD_WIDTH + 20; // 確認ボタンの左上端のx座標
    private final int CONFBT_Y = 445; // 確認ボタンの左上端のy座標

    private JLabel error_label; // "※ パスワードが違います。"というラベル
    private final int ERLB_WIDTH = LB_FONT_SIZE * 14; // "※ パスワードが違います。"というラベルの幅
    private final int ERLB_HEIGHT = LB_FONT_SIZE; // "※ パスワードが違います。"というラベルの高さ
    private final int ERLB_X = PSWDFD_X; // "※ パスワードが違います。"というラベルの左上端のx座標
    private final int ERLB_Y = 550; // "※ パスワードが違います。"というラベルの左上端のy座標

    private JButton cl_button; // 閉じるボタン
    private final int CLBT_WIDTH = 250; // 閉じるボタンの高さ
    private final int CLBT_HEIGHT = 80; // 閉じるボタンの幅
    private final int CLBT_X = FRAME_WIDTH/2 - CLBT_WIDTH/2; // 閉じるボタンの左上端のx座標
    private final int CLBT_Y = 690; // 閉じるボタンの左上端のy座標

    private String password; // パスワード


    public PswdRequester(final ButtonResponse bt_resp, final String pswd) {
        /* @param bt_resp: 各ボタンを押したときの対応のインターフェイス
        @param pswd: パスワード

         */
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);
        
        // パスワードをセット
        password = pswd;

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
        frame = new ImageIcon("image/function/pswdrequester/frame/frame.png");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // "パスワードを入力してください。"のラベルを作る
        request_label = new JLabel("パスワードを入力してください。");
        // フォント設定
        request_label.setFont(LB_FONT);
        // 位置設定
        request_label.setBounds(RQTLB_X, RQTLB_Y, RQTLB_WIDTH, RQTLB_HEIGHT);
        // 可視化する
        request_label.setOpaque(false);

        // パスワードを入力するテキストフィールドを作成する
        pswd_field = new JPasswordField();
        // サイズを設定する
        pswd_field.setPreferredSize(new Dimension(PSWDFD_WIDTH, PSWDFD_HEIGHT));
        // フォントを設定
        pswd_field.setFont(FIELD_FONT);
        // 位置設定
        pswd_field.setBounds(PSWDFD_X, PSWDFD_Y, PSWDFD_WIDTH, PSWDFD_HEIGHT);
        // エコー文字を設定
        pswd_field.setEchoChar('＊');

        // 検索ボタンの生成
        conf_button = new JButton();
        // サイズの設定
        conf_button.setPreferredSize(new Dimension(CONFBT_WIDTH, CONFBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon confb_icon = new ImageIcon("image/function/pswdrequester/button/conf_button.png");
        conf_button.setIcon(confb_icon);
        conf_button.setContentAreaFilled(false);
        conf_button.setBorderPainted(false);
        conf_button.setFocusPainted(false);
        // ボタンの位置設定
        conf_button.setBounds(CONFBT_X, CONFBT_Y, CONFBT_WIDTH, CONFBT_HEIGHT);
        // ボタンを押した際の処理
        conf_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("conf_button pressed");
                // パスワードがあっているかを見る
                String input = String.valueOf(pswd_field.getPassword());
                if (input.equals(password)) { // パスワードが正しいとき
                    
                    bt_resp.pushYes();
                    System.out.println("うまくいったぜ");
                    // エラー文を消す
                    remove(error_label);
                    repaint();
                }
                else { // パスワードが間違っているとき
                    // エラー文を表示する
                    add(error_label, 0);
                    repaint();
                }
                // 入力フィールドを空にする
                pswd_field.setText("");
            }
        });

        // "※ パスワードが違います。"のラベルを作る
        error_label = new JLabel("※ パスワードが違います。");
        // フォント設定
        error_label.setFont(LB_FONT);
        // 位置設定
        error_label.setBounds(ERLB_X, ERLB_Y, ERLB_WIDTH, ERLB_HEIGHT);
        // 可視化する
        error_label.setOpaque(false);
        // 赤色にする
        error_label.setForeground(Color.RED);

        // 閉じるボタンの生成
        cl_button = new JButton();
        // サイズの設定
        cl_button.setPreferredSize(new Dimension(CLBT_WIDTH, CLBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon clb_icon = new ImageIcon("image/function/pswdrequester/button/cl_button.png");
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
                pswd_field.setText("");
                remove(error_label);
                // 閉じるボタンを押した際の処理をする
                bt_resp.pushClose();
            }
        });

        add(black_bg_label, 0);
        add(frame_label, 0);
        add(request_label, 0);
        add(pswd_field, 0);
        add(conf_button, 0);
        add(cl_button, 0);
    }
}
