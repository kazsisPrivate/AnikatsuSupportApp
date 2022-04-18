package component.home.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import component.home.anikatsudb.AnikatsuDB;
import component.home.menu.snstweeter.SNSTweeter;
import function.BasicFunction;
import function.PswdRequester;
import function.button.ButtonResponse;
import function.sns.LineUser;
import function.sns.TwitterUser;
import function.Loading;


public class Menu extends JPanel {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅
    
    private JLabel black_bg_label; // 画面の半透明の黒色の背景
    private final int BLBG_WIDTH = 1920; // 画面の半透明の黒色の背景の高さ
    private final int BLBG_HEIGHT = 1080; // 画面の半透明の黒色の背景の幅
    private final int BLBG_X = 0; // 画面の半透明の黒色の背景の左上端のx座標
    private final int BLBG_Y = 0; // 画面の半透明の黒色の背景の左上端のy座標

    private ImageIcon frame; // menu画面のフレームの画像
    private JLabel frame_label; // menu画面のフレームの画像のラベル
    private final int FM_WIDTH = 1440; // menu画面のフレームの画像の幅
    private final int FM_HEIGHT = 800; // menu画面のフレームの画像の高さ
    private final int FM_X = 240; // menu画面のフレームの左上端のx座標
    private final int FM_Y = 140; // menu画面のフレームの左上端のy座標

    private JButton mbr_button; // 部員ボタン
    private final int MBRBT_WIDTH = 400;//540; // 部員ボタンの高さ
    private final int MBRBT_HEIGHT = 200; // 部員ボタンの幅
    private final int MBRBT_X = 300; // 部員ボタンの左上端のx座標
    private final int MBRBT_Y = 320; // 部員ボタンの左上端のy座標

    private JButton bgt_button; // 部費ボタン
    private final int BGTBT_WIDTH = 400;//540; // 部費ボタンの高さ
    private final int BGTBT_HEIGHT = 200; // 部費ボタンの幅
    private final int BGTBT_X = 760; // 部費ボタンの左上端のx座標
    private final int BGTBT_Y = MBRBT_Y; // 部費ボタンの左上端のy座標
    private PswdRequester pswd_rqtr; // PswdRequesterクラスのインスタンス

    private JButton pchs_button; // 購入品ボタン
    private final int PCHSBT_WIDTH = 400; // 購入品ボタンの高さ
    private final int PCHSBT_HEIGHT = 200; // 購入品ボタンの幅
    private final int PCHSBT_X = 1220; // 購入品ボタンの左上端のx座標
    private final int PCHSBT_Y = MBRBT_Y; // 購入品ボタンの左上端のy座標

    private JButton pst_button; // 研究発表ボタン
    private final int PSTBT_WIDTH = 400;//540; // 研究発表ボタンの高さ
    private final int PSTBT_HEIGHT = 200; // 研究発表ボタンの幅
    private final int PSTBT_X = 300; // 研究発表ボタンの左上端のx座標
    private final int PSTBT_Y = 590; // 研究発表ボタンの左上端のy座標

    private JButton fes_button; // 工大祭ボタン
    private final int FESBT_WIDTH = 400;//540; // 工大祭ボタンの高さ
    private final int FESBT_HEIGHT = 200; // 工大祭ボタンの幅
    private final int FESBT_X = 760; // 工大祭ボタンの左上端のx座標
    private final int FESBT_Y = PSTBT_Y; // 工大祭ボタンの左上端のy座標

    private SNSTweeter sns_tweeter; // SNSTweeterクラスのインスタンス

    private JButton sns_button; // SNSボタン
    private final int SNSBT_WIDTH = 400;//540; // SNSボタンの高さ
    private final int SNSBT_HEIGHT = 200; // SNSボタンの幅
    private final int SNSBT_X = 1220; // SNSボタンの左上端のx座標
    private final int SNSBT_Y = PSTBT_Y; // SNSボタンの左上端のy座標
    
    private JButton cl_button; // メニューの閉じるボタン
    private final int CLBT_WIDTH = 150; // メニューの閉じるボタンの高さ
    private final int CLBT_HEIGHT = 50; // メニューの閉じるボタンの幅
    private final int CLBT_X = FRAME_WIDTH/2 - CLBT_WIDTH/2; // メニューの閉じるボタンの左上端のx座標
    private final int CLBT_Y = 850; // メニューの閉じるボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったHomeでの処理を入れる
    private BasicFunction bc_func;

    
    public Menu(final ButtonResponse br, final BasicFunction bf, final TwitterUser tu, final LineUser lu) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

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

        // menu画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/menu/frame/frame.png");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // 部員ボタンの生成
        mbr_button = new JButton();
        // サイズの設定
        mbr_button.setPreferredSize(new Dimension(MBRBT_WIDTH, MBRBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon mbrb_icon = new ImageIcon("image/component/home/menu/button/mbr_button.png");
        mbr_button.setIcon(mbrb_icon);
        mbr_button.setContentAreaFilled(false);
        mbr_button.setBorderPainted(false);
        mbr_button.setFocusPainted(false);
        // ボタンの位置設定
        mbr_button.setBounds(MBRBT_X, MBRBT_Y, MBRBT_WIDTH, MBRBT_HEIGHT);
        // 部員ボタンを押した際の処理
        mbr_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("mbr_button pressed");
                // 部員情報の操作を行うための選択画面を表示する
                bc_func.setDBMgr(AnikatsuDB.MEMBER_MGR);
                // menu画面を取り除く
                bt_resp.pushYes();
            }
        });

        // PswdRequesterクラスのオブジェクトを取得
        pswd_rqtr = new PswdRequester(new PswdResponse(), "kaikeimoanikatsu");
        // パネルの位置の設定
        pswd_rqtr.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 部費ボタンの生成
        bgt_button = new JButton();
        // サイズの設定
        bgt_button.setPreferredSize(new Dimension(BGTBT_WIDTH, BGTBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bgtb_icon = new ImageIcon("image/component/home/menu/button/bgt_button.png");
        bgt_button.setIcon(bgtb_icon);
        bgt_button.setContentAreaFilled(false);
        bgt_button.setBorderPainted(false);
        bgt_button.setFocusPainted(false);
        // ボタンの位置設定
        bgt_button.setBounds(BGTBT_X, BGTBT_Y, BGTBT_WIDTH, BGTBT_HEIGHT);
        // menuボタンを押した際の処理
        bgt_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bgt_button pressed");
                // 部品を一時的にいじれない状態にする
                makeComponentsDisabled();
                // パスワード画面を表示する
                add(pswd_rqtr, 0);
                repaint();
            }
        });

        // 購入品ボタンの生成
        pchs_button = new JButton();
        // サイズの設定
        pchs_button.setPreferredSize(new Dimension(PCHSBT_WIDTH, PCHSBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon pchsb_icon = new ImageIcon("image/component/home/menu/button/pchs_button.png");
        pchs_button.setIcon(pchsb_icon);
        pchs_button.setContentAreaFilled(false);
        pchs_button.setBorderPainted(false);
        pchs_button.setFocusPainted(false);
        // ボタンの位置設定
        pchs_button.setBounds(PCHSBT_X, PCHSBT_Y, PCHSBT_WIDTH, PCHSBT_HEIGHT);
        // 購入品ボタンを押した際の処理
        pchs_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pchs_button pressed");
                // 購入品情報の操作を行うための選択画面を表示する
                bc_func.setDBMgr(AnikatsuDB.PURCHASE_MGR);
                // menu画面を取り除く
                bt_resp.pushYes();
            }
        });
        
        // 研究発表ボタンの生成
        pst_button = new JButton();
        // サイズの設定
        pst_button.setPreferredSize(new Dimension(PSTBT_WIDTH, PSTBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon pstb_icon = new ImageIcon("image/component/home/menu/button/pst_button.png");
        pst_button.setIcon(pstb_icon);
        pst_button.setContentAreaFilled(false);
        pst_button.setBorderPainted(false);
        pst_button.setFocusPainted(false);
        // ボタンの位置設定
        pst_button.setBounds(PSTBT_X, PSTBT_Y, PSTBT_WIDTH, PSTBT_HEIGHT);
        // 研究発表ボタンを押した際の処理
        pst_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pst_button pressed");
                // 研究発表情報の操作を行うための選択画面を表示する
                bc_func.setDBMgr(AnikatsuDB.PRESENTATION_MGR);
                // menu画面を取り除く
                bt_resp.pushYes();
            }
        });
        
        // 工大祭ボタンの生成
        fes_button = new JButton();
        // サイズの設定
        fes_button.setPreferredSize(new Dimension(FESBT_WIDTH, FESBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon fesb_icon = new ImageIcon("image/component/home/menu/button/fes_button.png");
        fes_button.setIcon(fesb_icon);
        fes_button.setContentAreaFilled(false);
        fes_button.setBorderPainted(false);
        fes_button.setFocusPainted(false);
        // ボタンの位置設定
        fes_button.setBounds(FESBT_X, FESBT_Y, FESBT_WIDTH, FESBT_HEIGHT);
        // 工大祭ボタンを押した際の処理
        fes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("fes_button pressed");
                // 工大祭情報の操作を行うための選択画面を表示する
                bc_func.setDBMgr(AnikatsuDB.FESTIVAL_MGR);
                // menu画面を取り除く
                bt_resp.pushYes();
            }
        });

        // SNSTweeterクラスのオブジェクトを取得
        sns_tweeter = new SNSTweeter(new SNSResponse(), new SNSFunction(), tu, lu);
        // 位置設定
        sns_tweeter.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // SNSボタンの生成
        sns_button = new JButton();
        // サイズの設定
        sns_button.setPreferredSize(new Dimension(FESBT_WIDTH, FESBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon snsb_icon = new ImageIcon("image/component/home/menu/button/sns_button.png");
        sns_button.setIcon(snsb_icon);
        sns_button.setContentAreaFilled(false);
        sns_button.setBorderPainted(false);
        sns_button.setFocusPainted(false);
        // ボタンの位置設定
        sns_button.setBounds(SNSBT_X, SNSBT_Y, SNSBT_WIDTH, SNSBT_HEIGHT);
        // SNSボタンを押した際の処理
        sns_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sns_button pressed");
                // 部品を一時的にいじれない状態にする
                makeComponentsDisabled();
                // SNS画面を表示する
                add(sns_tweeter, 0);
                repaint();
            }
        });
        
        // メニューの閉じるボタンの生成
        cl_button = new JButton();
        // サイズの設定
        cl_button.setPreferredSize(new Dimension(CLBT_WIDTH, CLBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon mnclb_icon = new ImageIcon("image/component/home/menu/button/cl_button.png");
        cl_button.setIcon(mnclb_icon);
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
                // homeでの処理をする
                bt_resp.pushClose();
            }
        });

        add(mbr_button);
        add(bgt_button);
        add(pst_button);
        add(fes_button);
        add(pchs_button);
        add(sns_button);
        add(cl_button);
        add(frame_label);
        add(black_bg_label);
    }


    private class PswdResponse implements ButtonResponse {
        /* 部費ボタンを押した際のパスワード画面でのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() {
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // パスワード画面を取り除く
            remove(pswd_rqtr);
            // 部費情報の操作を行うための選択画面を表示する
            bc_func.setDBMgr(AnikatsuDB.BUDGET_MGR);
            // menu画面を取り除く
            bt_resp.pushYes();
        }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // パスワード画面を取り除く
            remove(pswd_rqtr);
            repaint();
        }
    }


    private class SNSResponse implements ButtonResponse {
        /* SNSボタンを押した際のパスワード画面でのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() { }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // SNS画面を取り除く
            remove(sns_tweeter);
            repaint();
        }
    }


    private class SNSFunction implements BasicFunction {
        @Override
        public void addLog(final String msg) {
            bc_func.addLog(msg);
        }

        @Override
        public void charaSpeak(final String line, final String end_key) { }

        @Override
        public void charaSpeak(final String line1, final String end_key1, final String line2, final String end_key2) { }

        @Override
        public void changeChrModeToAutoMode() { }

        @Override
        public void setDBMgr(final int mgr) { }
    }


    private void makeComponentsDisabled() {
        /* 部品をいじれない状態にする
         */
        mbr_button.setEnabled(false);
        bgt_button.setEnabled(false);
        fes_button.setEnabled(false);
        pst_button.setEnabled(false);
        pchs_button.setEnabled(false);
        cl_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        mbr_button.setEnabled(true);
        bgt_button.setEnabled(true);
        fes_button.setEnabled(true);
        pst_button.setEnabled(true);
        pchs_button.setEnabled(true);
        cl_button.setEnabled(true);
    }


    public void addSNSTweeter() {
        add(sns_tweeter, 0);
    }


    public void removeSNSTweeter() {
        remove(sns_tweeter);
    }

}
