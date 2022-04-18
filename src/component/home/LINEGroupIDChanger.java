package component.home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;

import function.Loading;
import function.button.ButtonResponse;
import function.BasicFunction;


public class LINEGroupIDChanger extends JPanel {

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

    private JButton fwd_button; // →ボタン
    private final int FWDBT_WIDTH = 100; // →ボタンの高さ
    private final int FWDBT_HEIGHT = 60; // →ボタンの幅
    private final int FWDBT_X = FRAME_WIDTH/2 + 10; // →ボタンの左上端のx座標
    private final int FWDBT_Y = 840; // →ボタンの左上端のy座標

    private JButton bk_button; // ←ボタン
    private final int BKBT_WIDTH = 100; // ←ボタンの高さ
    private final int BKBT_HEIGHT = 60; // ←ボタンの幅
    private final int BKBT_X = FRAME_WIDTH/2 - BKBT_WIDTH - 10; // ←ボタンの左上端のx座標
    private final int BKBT_Y = FWDBT_Y; // ←ボタンの左上端のy座標

    private JButton cl_button; // 閉じるボタン
    private final int CLBT_WIDTH = 150; // 閉じるボタンの高さ
    private final int CLBT_HEIGHT = 50; // 閉じるボタンの幅
    private final int CLBT_X = 1470; // 閉じるボタンの左上端のx座標
    private final int CLBT_Y = 850; // 閉じるボタンの左上端のy座標

    private int crnt_page_num = 0;  // 現在開いているページ番号（0～）
    private int prev_page_num = 0;  // 前に開いていたページ番号
    private final int LAST_PAGE_NUM = 12; // 最後のページ番号

    private List<List<JLabel>> sts_list = new ArrayList<>();; // 各ページの文を入れたリスト
    private final Font ST_FONT1 = new Font("MS Pゴシック", Font.BOLD, 40); // 文のフォント1
    private final int ST_WIDTH = 1350; // 何か質問等の文の幅
    private final int ST_HEIGHT = ST_FONT1.getSize(); // 何か質問等の文の高さ
    private final int ST_X = 290; // 何かの文の左上端のx座標
    private final int ST_Y = 310; // 何かの文の左上端のy座標
    private int one_line_minimum; // 上のフォントでの上の幅における台詞1行の最低文字数

    private final Font ST_FONT2 = new Font("MS Pゴシック", Font.PLAIN, 40); // 文のフォント2
    private JButton url_button; // LINE Developerのサイトに飛ばすボタン
    private JLabel ldmail_label; // LINE Developerのログインで使用するメールアドレス
    private JLabel ldpswd_label; // LINE Developerのログインで使用するパスワード
    private JLabel fbmail_label; // facebookのログインで使用するメールアドレス
    private JLabel fbpswd_label; // facebookのログインで使用するパスワード
    private JLabel whimg_label; // webhookの説明で使用する画像
    private JLabel repimg_label; // LINEのグループidの説明で使用する画像
    private JLabel crnt_lnid_label; // 現在使用しているLINEのグループID
    private JLabel next_lnid_label; // 次に使用するLINEのグループID
    private JTextField next_lnid_field; // 次に使用するLINEのグループIDを入力するテキストフィールド
    private JButton next_lnid_button; // 入力した次に使用するLINEのグループIDを決定するボタン

    private ButtonResponse bt_resp; // コンストラクタで受け取ったHomeでの処理を入れる
    private BasicFunction bc_func;

    private Loading loading; // Loadingクラスのインスタンス


    public LINEGroupIDChanger(final ButtonResponse br, final BasicFunction bf) {
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
        frame = new ImageIcon("image/component/home/linegroupid_changer/frame/frame.png");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // →ボタンの生成
        fwd_button = new JButton();
        // サイズの設定
        fwd_button.setPreferredSize(new Dimension(FWDBT_WIDTH, FWDBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon fwdb_icon = new ImageIcon("image/component/home/linegroupid_changer/button/fwd_button.png");
        fwd_button.setIcon(fwdb_icon);
        fwd_button.setContentAreaFilled(false);
        fwd_button.setBorderPainted(false);
        fwd_button.setFocusPainted(false);
        // ボタンの位置設定
        fwd_button.setBounds(FWDBT_X, FWDBT_Y, FWDBT_WIDTH, FWDBT_HEIGHT);
        // →ボタンを押した際の処理
        fwd_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("fwd_button pressed");

                // 説明のページを進める
                prev_page_num = crnt_page_num;
                crnt_page_num++;
                // 次のページが最後のページだったらボタンを押せないようにする
                if (crnt_page_num == LAST_PAGE_NUM) {
                    fwd_button.setEnabled(false);
                }
                else if (prev_page_num == 0) { // 前のページが最初のページのとき
                    // ←ボタンを押せるようにする
                    bk_button.setEnabled(true);
                }

                // 開いているページの部品を表示する
                showPageComponents();
            }
        });

        // ←ボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/linegroupid_changer/button/bk_button.png");
        bk_button.setIcon(bkb_icon);
        bk_button.setContentAreaFilled(false);
        bk_button.setBorderPainted(false);
        bk_button.setFocusPainted(false);
        // ボタンの位置設定
        bk_button.setBounds(BKBT_X, BKBT_Y, BKBT_WIDTH, BKBT_HEIGHT);
        // ←ボタンを押した際の処理
        bk_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bk_button pressed");

                // 説明のページを戻る
                prev_page_num = crnt_page_num;
                crnt_page_num--;
                // 次のページが最初のページだったらボタンを押せないようにする
                if (crnt_page_num == 0) {
                    bk_button.setEnabled(false);
                }
                else if (prev_page_num == LAST_PAGE_NUM) { // 前のページが最後のページのとき
                    // →ボタンを押せるようにする
                    fwd_button.setEnabled(true);
                }

                // 開いているページの部品を表示する
                showPageComponents();
            }
        });
        // 最初のページなので押せないようにしておく
        bk_button.setEnabled(false);

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

                crnt_page_num = 0;
                prev_page_num = 0;
                // homeでの処理をする
                bt_resp.pushClose();
            }
        });

        // 各ページに表示する部品の初期化および設定をする
        setUpPageComponents();

        add(fwd_button);
        add(bk_button);
        add(cl_button);
        add(frame_label);
        add(black_bg_label);

        // 文を一行ずつJLabelとして表示する
        showPageComponents();
    }


    private void setUpPageComponents() {
        /* 各ページで表示する部品の初期化，設定を行う
         */
        // 上のフォントでの上の幅における台詞1行の最低文字数を設定
        one_line_minimum = createOneLineMinimum();

        String[] exp_list = new String[LAST_PAGE_NUM+1];
        exp_list[0] = "このLINE通知グループ変更処理では，iosかAndroid端末（スマホ端末）および、その端末内のLINEアプリ" +
                "を高確率で使用する羽目になります。LINEアプリ内で使用しているのと別のLINEアカウントでログインするため" +
                "にはLINEアプリをアンインストールする必要があります。トーク履歴のバックアップ（超簡単、ググれ）をしておいてください。";
        exp_list[1] = "①下記のボタンを押してLINE Developersサイトに行き、「LINEアカウントでログイン」を押し、" +
                "ログイン画面で下記のメールアドレスと" +
                "パスワードを入力してください（入力したらログインボタンを押す前に次の説明ページへ）。";
        exp_list[2] = "②ログインボタンを押し、「認証番号で本人確認」の表示が出てきてしまったらスマホ端末" +
                "でLINEを再インストールしてください（笑）。";
        exp_list[3] = "③再インストールしたら，下記のメールアドレスとパスワードを使用して「Facebookで続ける」からログイン" +
                "してください。";
        exp_list[4] = "④スマホ端末ログインした後に再度3ページ前の①の処理を行い、ログインボタンを押してください。" +
                "その後、スマホ端末で再インストールしたLINEアプリで認証コードの入力を要求されるので、表示されている" +
                "認証コードを入力してください。";
        exp_list[5] = "⑤開いているサイトの方でログインできたら、再度スマホ端末のLINEアプリを再インストールし、" +
                "自身のアカウントでログインしなおしてください。";
        exp_list[6] = "⑥開いているサイトのページの\"Messaging API settings\"欄にあるQRコードをスマホ端末の" +
                "LINEアプリで読み取り、通知アカウントを友達登録してください。その後、友達登録した通知アカウントを" +
                "新しく通知するようにしたいグループに招待してください。招待すると自動的にグループのメンバーに追加されます。";
        exp_list[7] = "⑦開いているサイトのページの\"Webhook settings\"欄にある\"Use webhook\"をオン（緑色）" +
                "にしてください。";
        exp_list[8] = "⑧スマホ端末のLINEアプリ内で⑥で通知アカウントを招待したグループで何か適当に入力して" +
                "つぶやいてください。そうすると、通知アカウントから下記のような記号の羅列が返ってきます。";
        exp_list[9] = "⑨返ってきた記号の羅列はいくつかのカンマで区切られており、\"C\"から始まる記号の羅列（Cを含む）を" +
                "下のボックスに入力し、隣の決定ボタンを押してください。「現在のGroupID」の欄が入力したものに" +
                "変更されていたら次へ進んでください。";
        exp_list[10] = "⑩開いているサイトのページ内の⑦でオンにした\"Use webhook\"をオフ（灰色）にしてください。";
        exp_list[11] = "⑪スマホ端末のアプリ内の新しく通知を行うグループでもう一度何か適当に入力してつぶやき、" +
                "通知アカウントから何も返信が来ないことを確認してください。また、本アプリケーション内のメニューのSNS" +
                "を通してLINEに何かつぶやいてみて、登録した通知グループに通知が行われるかどうかを確認してください。";
        exp_list[12] = "⑫以上でLINE通知グループ変更処理は終了です。ほんとにお疲れさまでした( ^)o(^ )b ";

        // 表示する文（改行処理済み）を入れるためのリストを作成
        for (int i = 0; i < exp_list.length; i++) {
            sts_list.add(createStLabelList(exp_list[i]));
        }

        // 説明で使用する説明文以外のラベルを作成する
        // LINE Developerのサイトに飛ばすボタン
        final int URLBT_WIDTH = 600;
        final int URLBT_HEIGHT = 100;
        url_button = new JButton();
        url_button.setPreferredSize(new Dimension(URLBT_WIDTH, URLBT_HEIGHT));
        ImageIcon urlb_icon = new ImageIcon("image/component/home/linegroupid_changer/expimg/url_button.png");
        url_button.setIcon(urlb_icon);
        url_button.setContentAreaFilled(false);
        url_button.setBorderPainted(false);
        url_button.setFocusPainted(false);
        url_button.setBounds(ST_X, 600, URLBT_WIDTH, URLBT_HEIGHT);
        url_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("url_button pressed");

                // サイトを開くスレッド処理
                Thread url_th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI(""));    // 使用するラインアカウントのurl
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        } catch (URISyntaxException urie) {
                            urie.printStackTrace();
                        }
                    }
                });
                // LINE Developerのサイトを開く
                try {
                    loading.initializeThreads();
                    loading.show_loading_th.start();
                    url_th.start();
                    url_th.join();
                    loading.hide_loading_th.start();
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        });

        // LINE Developerのログインに使用するメールアドレス
        final int ST_HEIGHT2 = ST_FONT2.getSize(); // 何か質問等の文の高さ
        ldmail_label = new JLabel("mail: anikatsu.kyutech@gmail.com");
        ldmail_label.setFont(ST_FONT2);
        ldmail_label.setBounds(ST_X+URLBT_WIDTH+50, 600, ST_WIDTH, ST_HEIGHT2);
        ldmail_label.setOpaque(false);

        // LINE Developerのログインに使用するパスワード
        ldpswd_label = new JLabel("password: ");
        ldpswd_label.setFont(ST_FONT2);
        ldpswd_label.setBounds(ST_X+URLBT_WIDTH+50, 650, ST_WIDTH, ST_HEIGHT2);
        ldpswd_label.setOpaque(false);

        // facebookのログインに使用するメールアドレス
        fbmail_label = new JLabel("mail: ");
        fbmail_label.setFont(ST_FONT2);
        fbmail_label.setBounds(ST_X, 500,  ST_WIDTH, ST_HEIGHT2);
        fbmail_label.setOpaque(false);

        // facebookのログインに使用するパスワード
        fbpswd_label = new JLabel("password: ");
        fbpswd_label.setFont(ST_FONT2);
        fbpswd_label.setBounds(ST_X, 550, ST_WIDTH, ST_HEIGHT2);
        fbpswd_label.setOpaque(false);

        // webhookの説明で使用する画像
        ImageIcon webhook_icon = new ImageIcon("image/component/home/linegroupid_changer/expimg/webhook.png");
        whimg_label = new JLabel(webhook_icon);
        whimg_label.setBounds(FRAME_WIDTH/2 - 211, 450, 422, 352);
        whimg_label.setOpaque(false);

        // LINEのgroupidの説明で使用する画像
        ImageIcon lnrep_icon = new ImageIcon("image/component/home/linegroupid_changer/expimg/line_reply.png");
        repimg_label = new JLabel(lnrep_icon);
        repimg_label.setBounds(FRAME_WIDTH/2 - 341, 550, 682, 256);
        repimg_label.setOpaque(false);

        // 現在使用しているLINEのグループID
        crnt_lnid_label = new JLabel("現在のGroupID: " + getLINEID());
        crnt_lnid_label.setFont(ST_FONT2);
        crnt_lnid_label.setBounds(ST_X, 600, ST_WIDTH, ST_HEIGHT2);
        crnt_lnid_label.setOpaque(false);

        // 現在使用しているLINEのグループID
        final int NLNIDLB_Y = 650;
        next_lnid_label = new JLabel("変更後のGroupID: ");
        next_lnid_label.setFont(ST_FONT2);
        next_lnid_label.setBounds(ST_X, NLNIDLB_Y, ST_WIDTH, ST_HEIGHT2);
        next_lnid_label.setOpaque(false);

        // 次に使用するLINEのグループIDを入力するテキストフィールド
        final int NLNIDFD_WIDTH = 700;
        final int NLNIDFD_HEIGHT = ST_HEIGHT2+10;
        next_lnid_field = new JTextField();
        next_lnid_field.setPreferredSize(new Dimension(NLNIDFD_WIDTH, NLNIDFD_HEIGHT));
        next_lnid_field.setFont(ST_FONT2);
        next_lnid_field.setBounds(630, NLNIDLB_Y, NLNIDFD_WIDTH, NLNIDFD_HEIGHT);

        // 入力した次に使用するLINEのグループIDを決定するボタン
        final int NLNIDBT_WIDTH = 90;
        final int NLNIDBT_HEIGHT = 50;
        next_lnid_button = new JButton();
        next_lnid_button.setPreferredSize(new Dimension(NLNIDBT_WIDTH, NLNIDBT_HEIGHT));
        ImageIcon next_lnidb_icon = new ImageIcon("image/component/home/linegroupid_changer/expimg/next_lnid_button.png");
        next_lnid_button.setIcon(next_lnidb_icon);
        next_lnid_button.setContentAreaFilled(false);
        next_lnid_button.setBorderPainted(false);
        next_lnid_button.setFocusPainted(false);
        next_lnid_button.setBounds(1350, NLNIDLB_Y, NLNIDBT_WIDTH, NLNIDBT_HEIGHT);
        next_lnid_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("next_lnid_button pressed");

                // パスワードの変更処理を行う
                changeLINEID(next_lnid_field.getText());
            }
        });
    }

    private int createOneLineMinimum() {
        /* 一行の長さ限界まで入る最低文字数を設定する(createLineListメソッドを呼び出すの際の効率化のため)
        文字によって大きさが異なり、アルファベットとかは一文字が特に小さい
        ここでは一番多き文字を"あ"と考えて一行に入り切る最低文字数を取得できるようにした
         */
        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(ST_FONT1);

        String str = "あ"; // 文字列の幅を計るのに使う
        int width = fm.stringWidth(str); // 切り出した文の横幅
        int minimum = 0; // 設定する最低文字数

        // 文字列の幅が吹き出しの限界サイズLINE_LENGTH以上になったら抜けるようにする
        while (width < ST_WIDTH) {
            // 一文字増やす
            str = str + "あ";
            width = fm.stringWidth(str);

            minimum++;
        }

        return minimum;
    }


    protected java.util.List<JLabel> createStLabelList(final String sentence) {
        /* 引数で渡された情報を基にして、st_listを作成する
        @param sentence: 質問等の文
        @param length: 1行の文字数
         */
        int start_point = 0; // substringで切り出し始める位置
        int end_point = one_line_minimum; // substringで切り出し終える位置
        int width; // 切り出した文の横幅
        java.util.List<String> list = new ArrayList<>(); // 切り出した各行を格納するためのリスト

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(ST_FONT1);

        while (start_point+one_line_minimum <= sentence.length()) {
            // 文の切り出し
            String subst = sentence.substring(start_point, end_point);
            // 文の横幅を取得する
            width = fm.stringWidth(subst);

            // 横幅がST_WIDTHをギリギリ超える場所を探す
            while (width <= ST_WIDTH && end_point != sentence.length()) {
                end_point += 1;
                subst = sentence.substring(start_point, end_point);
                width = fm.stringWidth(subst);
            }
            // 切り出し終了位置を横幅がST_WIDTHをギリギリ超えない場所にする
            if (width > ST_WIDTH) {
                end_point -= 1;
            }

            // 横幅がST_WIDTHを超えないの文を作成する
            subst = sentence.substring(start_point, end_point);
            // 横幅がST_WIDTHを超えないの文をリストに格納する
            list.add(subst);

            // 次に切り出し始める位置と切り出し終えるの設定
            start_point = end_point;
            end_point += one_line_minimum;
        }
        // 最後の入れ切れていない分をリストに追加する
        int max = sentence.length();
        String rest = sentence.substring(start_point, max);
        // 横幅がST_WIDTHを超えないの文をリストに格納する
        list.add(rest);

        // 各行のJLabelを作成していく
        List<JLabel> label_list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JLabel label = new JLabel(list.get(i));
            // フォント設定
            label.setFont(ST_FONT1);
            // 位置設定
            label.setBounds(ST_X, ST_Y + (ST_HEIGHT+25)*i, ST_WIDTH, ST_HEIGHT);
            // 可視化する
            label.setOpaque(false);
            // label_listに作成した一文のラベルを追加する
            label_list.add(label);
        }

        return label_list;
    }


    private void showPageComponents() {
        /* crnt_page_numのページのcomponentsを表示し，
        prev_page_numのページのcomponentsを非表示にする
         */
        makePageComponentsDisabled(prev_page_num);
        makePageComponentsEnabled(crnt_page_num);
        repaint();
    }


    private void makePageComponentsDisabled(final int page_num) {
        /* 指定したページの部品を非表示にする
         */
        // ページの説明文のラベルを非表示にする
        List<JLabel> sents = sts_list.get(page_num);
        for (JLabel lb : sents) {
            remove(lb);
        }

        // 説明文以外の部品を非表示にする
        if (page_num == 1) {
            remove(ldmail_label);
            remove(ldpswd_label);
            remove(url_button);
            remove(loading);
        }
        else if (page_num == 3) {
            remove(fbmail_label);
            remove(fbpswd_label);
        }
        else if (page_num == 7) {
            remove(whimg_label);
        }
        else if (page_num == 8) {
            remove(repimg_label);
        }
        else if (page_num == 9) {
            remove(crnt_lnid_label);
            remove(next_lnid_label);
            remove(next_lnid_field);
            remove(next_lnid_button);
        }
    }


    private void makePageComponentsEnabled(final int page_num) {
        /* 指定したページの部品を表示にする
         */
        // ページの説明文のラベルを表示にする
        List<JLabel> sents = sts_list.get(page_num);
        for (JLabel lb : sents) {
            add(lb, 0);
        }

        // 説明文以外の部品を表示にする
        if (page_num == 1) {
            add(ldmail_label, 0);
            add(ldpswd_label, 0);
            add(url_button, 0);
            add(loading, 0);
        }
        else if (page_num == 3) {
            add(fbmail_label, 0);
            add(fbpswd_label, 0);
        }
        else if (page_num == 7) {
            add(whimg_label, 0);
        }
        else if (page_num == 8) {
            add(repimg_label, 0);
        }
        else if (page_num == 9) {
            add(crnt_lnid_label, 0);
            add(next_lnid_label, 0);
            add(next_lnid_field, 0);
            add(next_lnid_button, 0);
        }
    }


    private String getLINEID() {
        String line_id = "";
        try {
            File f = new File("line_groupid.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));

            line_id = br.readLine();

            br.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        return line_id;
    }


    private void changeLINEID(String line_id) {
        // 空白文字を取り除く
        line_id = line_id.replaceAll("　", "").replaceAll(" ", "");
        if (line_id.equals("")) { // 未入力のとき
            JLabel label = new JLabel("未入力です。変更後のGroupIDを入力してください。");
            label.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, label);
        }
        else {
            System.out.println(line_id);
            next_lnid_field.setText("");
            crnt_lnid_label.setText("現在のGroupID: " + line_id);
            saveLINEID(line_id);
            bc_func.addLog("LINEの通知グループ（GroupID）を変更しました。");
        }
    }


    private void saveLINEID(final String line_id) {
        try {
            File f = new File("line_groupid.txt");
            FileWriter fw = new FileWriter(f);

            fw.write(line_id);

            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private void makeComponentsDisabled() {
        /* 部品をいじれない状態にする
         */
        fwd_button.setEnabled(false);
        bk_button.setEnabled(false);
        cl_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        fwd_button.setEnabled(true);
        bk_button.setEnabled(true);
        cl_button.setEnabled(true);
    }

}

