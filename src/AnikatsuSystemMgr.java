import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;

import component.home.Home;


public class AnikatsuSystemMgr extends JFrame implements ActionListener {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private javax.swing.Timer upd_timer; // 更新update()を呼び出すためのタイマー
    private final int UPDATE_INTERVAL = 20; // 更新頻度(ミリ秒)

    private Home home; // 機能面に関しての表示のパネル


    public AnikatsuSystemMgr() {
        super("AnikatsuSystem");
        // ウインドウの作成
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // ウインドウを画面の中央に表示するようにする
        setLocationRelativeTo(null);
        // ウインドウのサイズを変更できないようにする
        setResizable(false);

        // レイアウトの設定をする
        setLayout(null);

        // 機能面のクラスのインスタンスを取得、設定
        home = new Home();
        home.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // パネルをフレームに追加する
        add(home);
        setVisible(true);

        // updateTimerの設定を行う
        upd_timer = new javax.swing.Timer(UPDATE_INTERVAL, this);
        upd_timer.start();

        // 終了ウインドウを表示させるように設定
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowProcesses());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }


    private void update() {
        home.update();
    }


    @Override
    public void paint(Graphics g) {
        // キャラクターの描画
        home.firstPaint();
    }


    private class WindowProcesses extends WindowAdapter {
        /* ウインドウの状態が変化する際の処理を行うためのクラス
         */
        @Override
        public void windowClosing(WindowEvent e) {
            // 終了するかどうかの確認の画面を表示
            home.addStopConfirmer();
        }

        @Override
        public void windowDeiconified(WindowEvent e) { // 非アイコン化された際の処理
            System.out.println("deiconified");
            // 更新タイマーを止める
            upd_timer.start();
            if (home.isHome()) { // homeにいるとき
                // homeのキャラ更新タイマーを止める
                home.startChgTimer();
            }
        }

        @Override
        public void windowIconified(WindowEvent e) { // アイコン化された際の処理
            System.out.println("iconified");
            // 更新タイマーを止める
            upd_timer.stop();
            if (home.isHome()) { // homeにいるとき
                // homeのキャラ更新タイマーを止める
                home.stopChgTimer();
            }
        }
    }
}
