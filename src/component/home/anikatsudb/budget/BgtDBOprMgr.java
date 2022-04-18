package component.home.anikatsudb.budget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import function.BasicFunction;
import function.Loading;
import function.button.ButtonResponse;
import function.db.*;


public class BgtDBOprMgr extends DBOprMgr {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 280; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 200; // 検索ボタンの高さ
    private final int SCHBT_X = 160; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 240; // 検索ボタンの左上端のy座標

    private JButton add_button; // 登録ボタン
    private final int ADDBT_WIDTH = 280; // 登録ボタンの幅
    private final int ADDBT_HEIGHT = 200; // 登録ボタンの高さ
    private final int ADDBT_X = 510; // 登録ボタンの左上端のx座標
    private final int ADDBT_Y = SCHBT_Y; // 登録ボタンの左上端のy座標

    private JButton upd_button; // 更新ボタン
    private final int UPDBT_WIDTH = 280; // 更新ボタンの幅
    private final int UPDBT_HEIGHT = 200; // 更新ボタンの高さ
    private final int UPDBT_X = SCHBT_X; // 更新ボタンの左上端のx座標
    private final int UPDBT_Y = 510; // 更新ボタンの左上端のy座標

    private JButton dlt_button; // 削除ボタン
    private final int DLTBT_WIDTH = 280; // 削除ボタンの幅
    private final int DLTBT_HEIGHT = 200; // 削除ボタンの高さ
    private final int DLTBT_X = ADDBT_X; // 削除ボタンの左上端のx座標
    private final int DLTBT_Y = UPDBT_Y; // 削除ボタンの左上端のy座標

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 750; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 760; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったHomeでの処理を入れる
    private BasicFunction bc_func;

    private Loading loading; // Loadingクラスのインスタンス


    public BgtDBOprMgr(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // Loaingクラスのオブジェクトを取得
        loading = new Loading();
        // パネルの位置の設定
        loading.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/bgt_frame.png");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // 検索ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon schb_icon = new ImageIcon("image/component/home/anikatsudb/button/sch_button.png");
        sch_button.setIcon(schb_icon);
        sch_button.setContentAreaFilled(false);
        sch_button.setBorderPainted(false);
        sch_button.setFocusPainted(false);
        // ボタンの位置設定
        sch_button.setBounds(SCHBT_X, SCHBT_Y, SCHBT_WIDTH, SCHBT_HEIGHT);
        // ボタンを押した際の処理
        sch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sch_button pressed");

                // 検索のフォーマットを表示するスレッド処理
                Thread sch_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setDBOperator(SEARCH_FORMAT);
                    }
                });
                // 検索のフォーマットを表示する
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    sch_th.start();
                    sch_th.join();
                    //loading.show_loading_th.stop();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        // 登録ボタンの生成
        add_button = new JButton();
        // サイズの設定
        add_button.setPreferredSize(new Dimension(ADDBT_WIDTH, ADDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon addb_icon = new ImageIcon("image/component/home/anikatsudb/button/add_button.png");
        add_button.setIcon(addb_icon);
        add_button.setContentAreaFilled(false);
        add_button.setBorderPainted(false);
        add_button.setFocusPainted(false);
        // ボタンの位置設定
        add_button.setBounds(ADDBT_X, ADDBT_Y, ADDBT_WIDTH, ADDBT_HEIGHT);
        // ボタンを押した際の処理
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("add_button pressed");

                // 登録のフォーマットを表示するスレッド処理
                Thread add_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setDBOperator(ADD_FORMAT);
                    }
                });
                // 登録のフォーマットを表示する
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    add_th.start();
                    add_th.join();
                    //loading.show_loading_th.stop();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        // 更新ボタンの生成
        upd_button = new JButton();
        // サイズの設定
        upd_button.setPreferredSize(new Dimension(UPDBT_WIDTH, UPDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon updb_icon = new ImageIcon("image/component/home/anikatsudb/button/upd_button.png");
        upd_button.setIcon(updb_icon);
        upd_button.setContentAreaFilled(false);
        upd_button.setBorderPainted(false);
        upd_button.setFocusPainted(false);
        // ボタンの位置設定
        upd_button.setBounds(UPDBT_X, UPDBT_Y, UPDBT_WIDTH, UPDBT_HEIGHT);
        // ボタンを押した際の処理
        upd_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("upd_button pressed");

                // 更新のフォーマットを表示するスレッド処理
                Thread upd_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setDBOperator(UPDATE_FORMAT);
                    }
                });
                // 更新のフォーマットを表示する
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    upd_th.start();
                    upd_th.join();
                    //loading.show_loading_th.stop();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        // 削除ボタンの生成
        dlt_button = new JButton();
        // サイズの設定
        dlt_button.setPreferredSize(new Dimension(DLTBT_WIDTH, DLTBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon dltb_icon = new ImageIcon("image/component/home/anikatsudb/button/dlt_button.png");
        dlt_button.setIcon(dltb_icon);
        dlt_button.setContentAreaFilled(false);
        dlt_button.setBorderPainted(false);
        dlt_button.setFocusPainted(false);
        // ボタンの位置設定
        dlt_button.setBounds(DLTBT_X, DLTBT_Y, DLTBT_WIDTH, DLTBT_HEIGHT);
        // ボタンを押した際の処理
        dlt_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("dlt_button pressed");

                // 削除のフォーマットを表示するスレッド処理
                Thread dlt_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setDBOperator(DELETE_FORMAT);
                    }
                });
                // 削除のフォーマットを表示する
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    dlt_th.start();
                    dlt_th.join();
                    //loading.show_loading_th.stop();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/button/bk_button.png");
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

        // 画面に選択画面を表示する
        addMbrDBOprMgr();
    }


    private class OprResponse implements ButtonResponse {
        @Override
        public void pushYes() { }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            // キャラクターに最初の説明を話させる
            bc_func.charaSpeak("行いたい操作を、左の枠の中から選択し", "命令1");
            // 画面に表示しているフォーマットを取り除く
            remove(db_opr);
            // 選択画面に切り替える
            addMbrDBOprMgr();
            repaint();
        }
    }


    @Override
    protected void setDBOperator(final int format) {
        /* パラメータで受け取ったフォーマットの種類にフォーマットをセットする
        @param format:
         */
        OprResponse opr_resp = new OprResponse();

        if (format == SEARCH_FORMAT) { // 検索のフォーマットを表示するとき
            db_opr = new BgtInfoSearcher(opr_resp, bc_func);
        } else if (format == ADD_FORMAT) { // 追加のフォーマットを表示するとき
            db_opr = new BgtInfoInserter(opr_resp, bc_func);
        } else if (format == UPDATE_FORMAT) { // 更新のフォーマットを表示するとき
            db_opr = new BgtInfoUpdater(opr_resp, bc_func);
        } else { // 削除のフォーマットを表示するとき
            db_opr = new BgtInfoDeleter(opr_resp, bc_func);
        }

        // 位置設定
        db_opr.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        // MbrDBOprMgrの画面を取り除く
        removeMbrDBOprMgr();
        // 画面に指定したフォーマットを表示する
        add(db_opr, 0);
        repaint();
    }


    private void addMbrDBOprMgr() {
        add(frame_label, 0);
        add(sch_button, 0);
        add(add_button, 0);
        add(upd_button, 0);
        add(dlt_button, 0);
        add(bk_button, 0);
        add(loading, 0);
    }


    private void removeMbrDBOprMgr() {
        remove(sch_button);
        remove(add_button);
        remove(upd_button);
        remove(dlt_button);
        remove(bk_button);
        remove(frame_label);
        remove(loading);
    }
}