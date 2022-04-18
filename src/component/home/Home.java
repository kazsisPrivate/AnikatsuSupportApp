package component.home;

import javax.swing.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import function.Loading;
import twitter4j.TwitterException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.net.URL;
import java.net.URLConnection;

import character.Character;
import component.home.anikatsudb.AnikatsuDB;
import function.BasicFunction;
import function.button.ButtonResponse;
import function.sns.LineUser;
import component.home.twitterchecker.TwitterChecker;
import component.home.anikatsumusic.AnikatsuMusic;
import function.sns.TwitterUser;
import component.home.menu.Menu;
import function.confirmation.Confirmer;


public class Home extends JPanel {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private Background bg; // 使用する背景のパネルのインスタンス

    private JButton tc_button; // twitter_checkerボタン
    private final int TCBT_WIDTH = 400; // twitter_checkerボタンの幅
    private final int TCBT_HEIGHT = 200; // twitter_checkerボタンの高さ
    private final int TCBT_X = 50; // twitter_checkerボタンの左上端のx座標
    private final int TCBT_Y = 730; // twitter_checkerボタンの左上端のy座標

    private JButton am_button; // anikatsu_musicボタン
    private final int AMBT_WIDTH = 400; // anikatsu_musicボタンの高さ
    private final int AMBT_HEIGHT = 200; // anikatsu_musicボタンの幅
    private final int AMBT_X = 510; // anikatsu_musicボタンの左上端のx座標
    private final int AMBT_Y = 730; // anikatus_musicボタンの左上端のy座標

    private JButton idchg_button; // anikatsu_musicボタン
    private final int IDCHGBT_WIDTH = 250; // anikatsu_musicボタンの高さ
    private final int IDCHGBT_HEIGHT = 40; // anikatsu_musicボタンの幅
    private final int IDCHGBT_X = 660; // anikatsu_musicボタンの左上端のx座標
    private final int IDCHGBT_Y = 950; // anikatus_musicボタンの左上端のy座標

    private JButton mn_button; // menuボタン
    private final int MNBT_WIDTH = 100; // menuボタンの高さ
    private final int MNBT_HEIGHT = 100; // menuボタンの幅
    private final int MNBT_X = 1800; // menuボタンの左上端のx座標
    private final int MNBT_Y = 20; // menuボタンの左上端のy座標

    private boolean is_updating_chara = true; // キャラクター画像の更新処理を行っているときはtrue
    private boolean is_home = false; // homeにいるときはtrue

    private Logger logger; // 表示するLogのパネルのインスタンス

    private TwitterChecker twckr; // TwitterCheckerクラスのインスタンス

    private Menu menu; // Menuクラスのインスタンス

    private AnikatsuMusic animsc; // AnikatsuMusicクラスのインスタンス

    private AnikatsuDB anidb; // AnikatsuDBクラスのインスタンス

    private LINEGroupIDChanger idchg; // LINEのグループ変更を行うクラスのインスタンス

    private Character character; // 使用するキャラクターのパネルのインスタンス
    private Timer chg_timer; // キャラクターの通常画像を一定時間毎に交換するためのTimer
    private final int CHG_INTERVAL = 60000;
    private final Font HOME_CHARA_FONT = new Font("MS Pゴシック", Font.BOLD, 35); // ホームでのキャラクターの台詞を表示する際のフォント
    private final Font ANIMSC_CHARA_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // anikatsu_musicでのキャラクターの台詞を表示する際のフォント
    private final Font ANIDB_CHARA_FONT = new Font("MS Pゴシック", Font.BOLD, 35); // anikatsu_dbでのキャラクターの台詞を表示する際のフォント

    private LineUser ln_user; // LineUserクラスのインスタンス
    private TwitterUser tw_user; // TwitterUserクラスのインスタンス

    private Confirmer open_confirmer; // 部室解放のツイートをするか確認の画面
    private Confirmer stop_confirmer; // AnikatsuSystemを終了するか確認の画面
    private Confirmer close_confirmer; // 部室閉鎖のツイートをするか確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private Loading loading; // Loadingクラスのインスタンス


    public Home() {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // Loaingクラスのオブジェクトを取得
        loading = new Loading();
        // パネルの位置の設定
        loading.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        add(loading, 0);

        // 背景のクラスのオブジェクトの取得、設定
        bg = new Background();
        bg.setBounds(0, -5, FRAME_WIDTH, FRAME_HEIGHT); // yを-5しているのはなぜかlabelの座標とdrawImageの座標には5のずれあるから

        // ログのクラスのインスタンスを取得、設定
        logger = new Logger();
        logger.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        // AnikatsuSystemを起動したことをログに書き込む
        logger.addLog("AnikatsuSystemを起動しました。");
        // ログを直接いじれない状態にする
        //logger.setEnabled(false);

        // SNS関連のクラスのオブジェクトの取得
        tw_user = new TwitterUser();
        ln_user = new LineUser();

        // 部室解放ツイートをツイートするかの確認画面を生成する
        String open_conf = "部室を開けたことを、TwitterとLINEにツイートますか？";
        open_confirmer = new Confirmer(new OpResponse(), open_conf, CONF_FONT, Confirmer.YES_NO);
        open_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 部室閉鎖ツイートをツイートするかの確認画面を生成する
        String stop_conf = "本当に、AnikatsuSystemを終了しますか？";
        stop_confirmer = new Confirmer(new StResponse(), stop_conf, CONF_FONT, Confirmer.YES_NO);
        stop_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 部室閉鎖ツイートをツイートするかの確認画面を生成する
        String close_conf = "部室を閉めたことを、TwitterとLINEにツイートますか？";
        close_confirmer = new Confirmer(new ClResponse(), close_conf, CONF_FONT, Confirmer.YES_NO);
        close_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // TwitterCheckerクラスのオブジェクトを取得
        twckr = new TwitterChecker(new TCResponse(), new TCFunction(), tw_user, ln_user);
        // パネルの位置の設定
        twckr.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // twitter_checkerボタンの生成
        tc_button = new JButton();
        // サイズの設定
        tc_button.setPreferredSize(new Dimension(TCBT_WIDTH, TCBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon tcb_icon = new ImageIcon("image/component/home/button/tc_button.png");
        tc_button.setIcon(tcb_icon);
        tc_button.setContentAreaFilled(false);
        tc_button.setBorderPainted(false);
        tc_button.setFocusPainted(false);
        // ボタンの位置設定
        tc_button.setBounds(TCBT_X, TCBT_Y, TCBT_WIDTH, TCBT_HEIGHT);
        // twitter_checkerボタンを押した際の処理
        tc_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("tc_button pressed");
                // キャラクターの更新を行わない状態にする
                is_updating_chara = false;
                // ホームにいない状態にする
                is_home = false;
                // chg_timerを止める
                stopChgTimer();
                // 部品を一時的にいじれない状態にする
                makeComponentsDisabled();
                // twittercheckerのスレッド処理
                Thread tc_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        twckr.addConfirmer();
                    }
                });
                // 画面に対象とするツイートを投稿するかどうかを表示
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    tc_th.start();
                    tc_th.join();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }

                add(twckr, 0);

                repaint();
            }
        });

        // AnikatsuMusicクラスのオブジェクトの取得、設定
        animsc = new AnikatsuMusic(new AMResponse(), new AMFunction(), bg);
        animsc.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // anikatsu_musicボタンの生成
        am_button = new JButton();
        // サイズの設定
        am_button.setPreferredSize(new Dimension(AMBT_WIDTH, AMBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon amb_icon = new ImageIcon("image/component/home/button/am_button.png");
        am_button.setIcon(amb_icon);
        am_button.setContentAreaFilled(false);
        am_button.setBorderPainted(false);
        am_button.setFocusPainted(false);
        // ボタンの位置設定
        am_button.setBounds(AMBT_X, AMBT_Y, AMBT_WIDTH, AMBT_HEIGHT);
        // AnikatsuMusicボタンを押した際の処理
        am_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("am_button pressed");
                // キャラクターのモードをINSTRUCTED_MODEに変更する
                character.changeModeToInstMode(animsc.getCrntMusicName() + "   ", "誇張");
                // キャラクターの台詞のフォントを変える
                character.setCharaFont(ANIMSC_CHARA_FONT);
                // ホームにいない状態にする
                is_home = false;
                // chg_timerを止める
                stopChgTimer();
                // Homeの部品を取り除く
                removeHome();
                // 画面にAnikatsuMusicを表示
                add(animsc, 0);
                repaint();
            }
        });

        // LINEGroupIDChangerクラスのオブジェクトを取得
        idchg = new LINEGroupIDChanger(new IDChgResponse(), new IDChgFunction());
        // パネルの位置の設定
        idchg.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // groupid変更ボタンの生成
        idchg_button = new JButton();
        // サイズの設定
        idchg_button.setPreferredSize(new Dimension(IDCHGBT_WIDTH, IDCHGBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon idchgb_icon = new ImageIcon("image/component/home/button/idchg_button.png");
        idchg_button.setIcon(idchgb_icon);
        idchg_button.setContentAreaFilled(false);
        idchg_button.setBorderPainted(false);
        idchg_button.setFocusPainted(false);
        // ボタンの位置設定
        idchg_button.setBounds(IDCHGBT_X, IDCHGBT_Y, IDCHGBT_WIDTH, IDCHGBT_HEIGHT);
        // menuボタンを押した際の処理
        idchg_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("idchg_button pressed");
                // キャラクターの更新を行わない状態にする
                is_updating_chara = false;
                // ホームにいない状態にする
                is_home = false;
                // chg_timerを止める
                stopChgTimer();
                // 部品を一時的にいじれない状態にする
                makeComponentsDisabled();
                // 画面にメニューを表示
                add(idchg, 0);
                repaint();
            }
        });

        // Menuクラスのオブジェクトを取得
        menu = new Menu(new MnResponse(), new MnFunction(), tw_user, ln_user);
        // パネルの位置の設定
        menu.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // menuボタンの生成
        mn_button = new JButton();
        // サイズの設定
        mn_button.setPreferredSize(new Dimension(MNBT_WIDTH, MNBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon mnb_icon = new ImageIcon("image/component/home/button/mn_button.png");
        mn_button.setIcon(mnb_icon);
        mn_button.setContentAreaFilled(false);
        mn_button.setBorderPainted(false);
        mn_button.setFocusPainted(false);
        // ボタンの位置設定
        mn_button.setBounds(MNBT_X, MNBT_Y, MNBT_WIDTH, MNBT_HEIGHT);
        // menuボタンを押した際の処理
        mn_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("mn_button pressed");
                // キャラクターの更新を行わない状態にする
                is_updating_chara = false;
                // ホームにいない状態にする
                is_home = false;
                // chg_timerを止める
                stopChgTimer();
                // 部品を一時的にいじれない状態にする
                makeComponentsDisabled();
                // 画面にメニューを表示
                add(menu, 0);
                repaint();
            }
        });


        // AnikatsuDBクラスのオブジェクトの取得、設定
        anidb = new AnikatsuDB(new ADBResponse(), new ADBFunction());
        anidb.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // キャラクターのクラスのインスタンスを取得、設定
        character = getTodayCharacter();//new Mahiru(bg, HOME_CHARA_FONT, Character.AUTO_MODE);
        character.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 最初はキャラクターの更新を行わない状態にする
        is_updating_chara = false;
        // 部品を一時的にいじれない状態にする
        makeComponentsDisabled();

        add(open_confirmer);
        add(character);
        add(logger);
        add(tc_button);
        add(am_button);
        add(idchg_button);
        add(mn_button);
        add(bg);

        // SNSTweeterのJScrollPaneを使えるようにするための処理
        menu.addSNSTweeter();
        add(menu);

        setVisible(true);
    }


    public void firstPaint() {
        /* 起動時の描画
         */
        // キャラクターの描画
        character.repaint();
    }


    public void update() {
        if (is_updating_chara) { // homeを開いているとき
            character.update();
        }
    }


    public void startChgTimer() {
        /* キャラクターの更新タイマーの開始処理をする
         */
        System.out.println("start_chg_timer");
        // キャラクターの通常画像を交換するタスクのタイマーを設定する
        chg_timer = new Timer();
        chg_timer.schedule(new ChangeTask(), CHG_INTERVAL, CHG_INTERVAL);
    }


    public void stopChgTimer() {
        /* キャラクターの更新タイマーの終了（一時停止）処理をする
         */
        System.out.println("stop_chg_timer");
        chg_timer.cancel();
    }


    private class ChangeTask extends TimerTask {
        @Override
        public void run() {
            character.setTrueIntoHasChanged();
        }
    }


    private void makeComponentsDisabled() {
        /* 部品をいじれない状態にする
         */
        logger.setEnabled(false);
        tc_button.setEnabled(false);
        am_button.setEnabled(false);
        idchg_button.setEnabled(false);
        mn_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        logger.setEnabled(true);
        tc_button.setEnabled(true);
        am_button.setEnabled(true);
        idchg_button.setEnabled(true);
        mn_button.setEnabled(true);
    }


    private void removeHome() {
        /* Homeの部品を取り除く
         */
        remove(logger);
        remove(tc_button);
        remove(am_button);
        remove(idchg_button);
        remove(mn_button);
        remove(loading);
    }


    private void addHome() {
        /* Homeの部品を追加する
         */
        add(logger, 0);
        add(tc_button, 0);
        add(am_button, 0);
        add(idchg_button, 0);
        add(mn_button, 0);
        add(loading, 0);
    }


    private class OpResponse implements ButtonResponse {
        /* 部室解放ツイートを行うかの確認画面でのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() {
            String msg = "#" + character.getCharaName()
                    + "\n部室を開け" + character.getCharaEnd("報告");

            // 各SNSに投稿できたかどうかのフラグ
            boolean has_tweeted_to_tw = true; // twitterに投稿したかどうか
            boolean has_tweeted_to_ln = true; // lineに投稿したかどうか

            // twitterに投稿する
            try {
                tw_user.post(msg);
            } catch (TwitterException e) {
                System.out.println("twitterへの投稿に失敗しました。");
                has_tweeted_to_tw = false;
            }

            // lineに投稿する
            try {
                ln_user.post(msg);
            } catch (IOException e) {
                System.out.println("lineへの投稿に失敗しました。");
                has_tweeted_to_ln = false;
            }

            // ログの追加
            if (has_tweeted_to_tw) {
                if (has_tweeted_to_ln) { // TwitterとLineに部室解放ツイートを投稿したとき
                    logger.addLog("部室解放ツイートを、TwitterとLINEに投稿しました。");
                }
                else { // Twitterのみに部室解放ツイートを投稿したとき
                    logger.addLog("部室解放ツイートを、Twitterに投稿しました。LINEへの投稿は、失敗しました。");
                }
            }
            else if (has_tweeted_to_ln) { // Lineのみに部室解放ツイートを投稿したとき
                logger.addLog("部室解放ツイートを、LINEに投稿しました。Twitterへの投稿は、失敗しました。");
            }
            else { // TwitterとLine両方に投稿できなかったとき
                logger.addLog("部室解放ツイートの投稿に失敗しました。");
            }

            // キャラクターを更新している状態にする
            is_updating_chara = true;
            // ホームにいる状態にする
            is_home = true;
            // chg_timerを再開する
            startChgTimer();
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();

            // menuを消す
            remove(menu);
            menu.removeSNSTweeter();

            // 確認画面を取り除く
            remove(open_confirmer);
            repaint();
        }

        @Override
        public void pushNo() {
            // キャラクターを更新している状態にする
            is_updating_chara = true;
            // ホームにいる状態にする
            is_home = true;
            // chg_timerを再開する
            startChgTimer();
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();

            // menuを消す
            remove(menu);
            menu.removeSNSTweeter();

            // 確認画面を取り除く
            remove(open_confirmer);
            repaint();
        }

        @Override
        public void pushClose() { }
    }


    private class StResponse implements ButtonResponse {
        /* AnikatsuSystemを終了するかの確認画面でのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() {
            // 確認画面を取り除く
            remove(stop_confirmer);
            // 部室閉鎖のツイートをするかどうかの確認画面を表示する
            add(close_confirmer, 0);
            repaint();
        }

        @Override
        public void pushNo() {
            // キャラクターを更新している状態にする
            is_updating_chara = true;
            // ホームにいる状態にする
            is_home = true;
            // chg_timerを再開する
            startChgTimer();
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();

            // 確認画面を取り除く
            remove(stop_confirmer);
            repaint();
        }

        @Override
        public void pushClose() { }
    }


    private class ClResponse implements ButtonResponse {
        /* 部室解放ツイートを行うかの確認画面でのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() {
            // インターネットに接続しているかどうかの確認
            boolean has_connected = true;
            int count = 0; // 一定時間以上失敗し続けたらインターネットへの接続をあきらめる
            do {
                try {
                    URL url = new URL("http://google.com");
                    URLConnection con = url.openConnection();
                    con.getInputStream();
                    has_connected = true;
                } catch (IOException e) {
                    has_connected = false;
                }
                count++;
            } while(!has_connected || count == 1000);
            // インターネットへの接続が可能、もしくは一定時間以上経過で送信処理

            String msg = "#" + character.getCharaName()
                    + "\n部室を閉め" + character.getCharaEnd("報告");

            // 各SNSに投稿できたかどうかのフラグ
            boolean has_tweeted_to_tw = true; // twitterに投稿したかどうか
            boolean has_tweeted_to_ln = true; // lineに投稿したかどうか

            // twitterに投稿する
            try {
                tw_user.post(msg);
            } catch (TwitterException e) {
                System.out.println("twitterへの投稿に失敗しました。");
                has_tweeted_to_tw = false;
            }

            // lineに投稿する
            try {
                ln_user.post(msg);
            } catch (IOException e) {
                System.out.println("lineへの投稿に失敗しました。");
                has_tweeted_to_ln = false;
            }

            // ログの追加
            if (has_tweeted_to_tw) {
                if (has_tweeted_to_ln) { // TwitterとLineに部室閉鎖ツイートを投稿したとき
                    logger.addLog("部室閉鎖ツイートを、TwitterとLINEに投稿しました。");
                }
                else { // Twitterのみに部室閉鎖ツイートを投稿したとき
                    logger.addLog("部室閉鎖ツイートを、Twitterに投稿しました。LINEへの投稿は、失敗しました。");
                }
            }
            else if (has_tweeted_to_ln) { // Lineのみに部室閉鎖ツイートを投稿したとき
                logger.addLog("部室閉鎖ツイートを、LINEに投稿しました。Twitterへの投稿は、失敗しました。");
            }
            else { // TwitterとLine両方に投稿できなかったとき
                logger.addLog("部室閉鎖ツイートの投稿に失敗しました。");
            }

            // 確認画面を取り除く
            remove(close_confirmer);
            repaint();

            // AnikatsuSystemを終了することをログに追加する
            logger.addLog("AnikatsuSystemを終了しました。\n"
                    + "---------------------------------------------------------------------------------------------------------------------------------");

            // log.txtファイルを作成する
            createNewLogFile();
            // AnikatsuSystemを終了する
            System.exit(0);
        }

        @Override
        public void pushNo() {
            // 確認画面を取り除く
            remove(close_confirmer);
            repaint();

            // AnikatsuSystemを終了することをログに追加する
            logger.addLog("AnikatsuSystemを終了しました。\n"
                    + "---------------------------------------------------------------------------------------------------------------------------------");

            // log.txtファイルを作成する
            createNewLogFile();
            // AnikatsuSystemを終了する
            System.exit(0);
        }

        @Override
        public void pushClose() { }
    }


    private class TCResponse implements ButtonResponse {
        /* TwitterCheckerでのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() { }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            /* TwitterCheckerを閉じる際の処理
             */
            // キャラクターを更新している状態にする
            is_updating_chara = true;
            // ホームにいる状態にする
            is_home = true;
            // chg_timerを再開する
            startChgTimer();
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // TwitterCheckerを取り除く
            remove(twckr);
            repaint();
        }
    }


    private class TCFunction implements BasicFunction {
        @Override
        public void addLog(final String msg) {
            logger.addLog(msg);
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


    private class MnResponse implements ButtonResponse {
        /* Menuでのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() {
            /* Home以外の他の画面(AnikatsuDB)に移る際の処理
             */
            // キャラクターを更新している状態にする
            is_updating_chara = true;
            // menu画面を取り除く
            remove(menu);
            repaint();
        }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            /* Menuを閉じる際の処理
             */
            // キャラクターを更新している状態にする
            is_updating_chara = true;
            // ホームにいる状態にする
            is_home = true;
            // chg_timerを再開する
            startChgTimer();
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // メニューを取り除く
            remove(menu);
            repaint();
        }
    }


    private class MnFunction implements BasicFunction {
        @Override
        public void addLog(final String msg) {
            logger.addLog(msg);
        }

        @Override
        public void charaSpeak(final String line, final String end_key) { }

        @Override
        public void charaSpeak(final String line1, final String end_key1, final String line2, final String end_key2) { }

        @Override
        public void changeChrModeToAutoMode() { }

        @Override
        public void setDBMgr(final int mgr) {
            /* 指定された種類の情報に関する選択画面に変える
             */
            // キャラクターのモードをINSTRUCTED_MODEに変更する
            character.changeModeToInstMode( "行いたい操作を、左の枠の中から選択", "命令1");
            // キャラクターの台詞のフォントを変える
            character.setCharaFont(ANIDB_CHARA_FONT);
            // chg_timerを止める
            stopChgTimer();
            // ホームにいない状態にする
            is_home = false;
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // Homeの部品を取り除く
            removeHome();

            // 指定された種類の情報に関する選択画面をセットする
            anidb.setDBManager(mgr);
            // 画面に表示する
            add(anidb, 0);
            repaint();
        }
    }


    private class IDChgResponse implements ButtonResponse {
        /* groupid変更でのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() { }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            /* Menuを閉じる際の処理
             */
            // キャラクターを更新している状態にする
            is_updating_chara = true;
            // ホームにいる状態にする
            is_home = true;
            // chg_timerを再開する
            startChgTimer();
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // メニューを取り除く
            remove(idchg);
            repaint();
        }
    }


    private class IDChgFunction implements BasicFunction {
        @Override
        public void addLog(final String msg) {
            logger.addLog(msg);
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


    private class AMResponse implements ButtonResponse {
        /* AnikatsuMusicでのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() { }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            /* AnikatsuMusicを閉じる際の処理
             */
            // chg_timerを再開する
            startChgTimer();
            // ホームにいる状態にする
            is_home = true;
            // 部品を戻す
            addHome();
            // AnikatsuMusicの画面を取り除く
            remove(animsc);
            repaint();
        }
    }


    private class AMFunction implements BasicFunction {
        @Override
        public void addLog(final String msg) { }

        @Override
        public void charaSpeak(final String line, final String end_key) {
            character.setTrueIntoHasChanged(line, end_key);
        }

        @Override
        public void charaSpeak(final String line1, final String end_key1, final String line2, final String end_key2) {
            character.setTrueIntoHasChanged(line1, end_key1, line2, end_key2);
        }

        @Override
        public void changeChrModeToAutoMode() {
            /* キャラクターのモードを変えるときの処理
             */
            character.changeModeToAutoMode();
            // キャラクターの台詞のフォントを変える
            character.setCharaFont(HOME_CHARA_FONT);
        }

        @Override
        public void setDBMgr(final int mgr) { }
    }


    private class ADBResponse implements ButtonResponse {
        /* Menuでのボタン処理(このクラス内でする処理)
         */
        @Override
        public void pushYes() { }

        @Override
        public void pushNo() { }

        @Override
        public void pushClose() {
            /* AnikatsuDBを閉じる際の処理
             */
            // chg_timerを再開する
            startChgTimer();
            // ホームにいる状態にする
            is_home = true;
            // 部品を戻す
            addHome();
            // 情報処理の選択画面(AnikatsuDB)を取り除く
            remove(anidb);
            repaint();
        }
    }


    private class ADBFunction implements BasicFunction {
        @Override
        public void addLog(final String msg) {
            logger.addLog(msg);
        }

        @Override
        public void charaSpeak(final String line, final String end_key) {
            character.setTrueIntoHasChanged(line, end_key);
        }

        @Override
        public void charaSpeak(final String line1, final String end_key1, final String line2, final String end_key2) {
            character.setTrueIntoHasChanged(line1, end_key1, line2, end_key2);
        }

        @Override
        public void changeChrModeToAutoMode() {
            /* キャラクターのモードを変えるときの処理
             */
            character.changeModeToAutoMode();
            // キャラクターの台詞のフォントを変える
            character.setCharaFont(HOME_CHARA_FONT);
        }

        @Override
        public void setDBMgr(final int mgr) { }
    }


    public void addStopConfirmer() {
        /* AnikatsuSystemを終了するかどうかの確認画面を表示する
         */
        // キャラクターの更新を行わない状態にする
        is_updating_chara = false;
        // chg_timerを止める
        stopChgTimer();
        // 部品を一時的にいじれない状態にする
        makeComponentsDisabled();
        // 確認画面を表示する
        add(stop_confirmer, 0);
        repaint();
    }


    private void createNewLogFile() {
        /* 終了する際にlog.txtファイルにログを書き込むようにする
        AnikatsuSystemMgrクラスから呼び出す
         */
        try {
            logger.createNewLogFile();
        } catch (IOException e) {
            System.out.println("log.txtファイルの作成に失敗しました。");
            e.printStackTrace();
        }
    }


    public boolean isHome() {
        /* Homeにいるかどうかを返す
         */
        return is_home;
    }


    private Character getTodayCharacter() {
        /* 表示するキャラクターをランダムで決めて、そのキャラクターのオブジェクトを返すメソッド
         */
        // キャラクターを入れているディレクトリ内のそれぞれのソースファイル名を取得
        String path = "src\\character\\character\\";
        File chara_file = new File(path);
        File chara_file_array[] = chara_file.listFiles();

        // 各キャラクター名(pathと.javaを取り除いたファイル名)をリストに格納する
        List<String> chara_name_list = new ArrayList<>();
        for (File f : chara_file_array) {
            String name = "character.character." + f.toString().replace(path, "").replace(".java", "");
            chara_name_list.add(name);
        }

        // ランダムでキャラクターを選択して返す
        Random random = new Random();
        int chara_num = random.nextInt(chara_name_list.size());

        // 選ばれたキャラクターのオブジェクトを取得
        Character selected_chara = null;
        try {
            Class chara = Class.forName(chara_name_list.get(chara_num));
            Constructor chara_cstrs[] = chara.getConstructors();//(Background.class, Font.class, Integer.class);
            selected_chara = (Character)chara_cstrs[0].newInstance(bg, HOME_CHARA_FONT, Character.AUTO_MODE);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("キャラのクラス取得に失敗。");
            cnfe.printStackTrace();
        } catch (InstantiationException ie) {
            System.out.println("ie");
            ie.printStackTrace();
        } catch (IllegalAccessException iae) {
            System.out.println("iae");
            iae.printStackTrace();
        } catch (InvocationTargetException ite) {
            System.out.println("ite");
            ite.printStackTrace();
        }

        return selected_chara;
    }
}
