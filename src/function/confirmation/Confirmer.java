package function.confirmation;

import javax.swing.*;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import function.button.ButtonResponse;


public class Confirmer extends JPanel {

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

    private JButton yes_button; // はいボタン
    private final int YESBT_WIDTH = 250; // はいボタンの高さ
    private final int YESBT_HEIGHT = 80; // はいボタンの幅
    private final int YESBT_X = 660; // はいボタンの左上端のx座標
    private final int YESBT_Y = 690; // はいボタンの左上端のy座標

    private JButton no_button; // いいえボタン
    private final int NOBT_WIDTH = 250; // いいえボタンの高さ
    private final int NOBT_HEIGHT = 80; // いいえボタンの幅
    private final int NOBT_X = 1010; // いいえボタンの左上端のx座標
    private final int NOBT_Y = 690; // いいえボタンの左上端のy座標

    private JButton cl_button; // 閉じるボタン
    private final int CLBT_WIDTH = 250; // 閉じるボタンの高さ
    private final int CLBT_HEIGHT = 80; // 閉じるボタンの幅
    private final int CLBT_X = 960 - CLBT_WIDTH/2; // 閉じるボタンの左上端のx座標
    private final int CLBT_Y = 690; // 閉じるボタンの左上端のy座標

    // コンストラクタのパラメータのformatに入れるものとして使用する
    static final public int YES_NO = 0;
    static final public int CLOSE = 1;
    private int format; // コンストラクタで受け取ったformatを格納する

    private List<JLabel> st_list; // 文の改行処理を施したもの(1行ずつのJLabel)を入れるリスト
    private final int ST_WIDTH = 1400; // 何か質問等の文の幅
    private int st_height; // 何か質問等の文の高さ
    private final int ST_X = 260; // 何かの文の左上端のx座標
    private final int ST_Y = 310; // 何かの文の左上端のy座標
    private Font st_font; // 文のフォント
    private int one_line_minimum; // 上のフォントでの上の幅における台詞1行の最低文字数


    public Confirmer(final ButtonResponse btres, final String sentence, final Font font, final int format) {
        /* @param btres: 各ボタンを押したときの対応のインターフェイス
        @param stc: 確認として表示する文
        @param format: "はいいいえ"のフレームか、"閉じる"のフレームかどちらを使用するか
         */
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // 使用するフォントを格納
        st_font = font;
        st_height = st_font.getSize();
        // 上のフォントでの上の幅における台詞1行の最低文字数を設定
        one_line_minimum = createOneLineMinimum();

        // 使用するフレームの種類の記録
        this.format = format;

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
        frame = new ImageIcon("image/function/confirmer/frame/frame.png");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // 閉じるボタンの生成
        cl_button = new JButton();
        // サイズの設定
        cl_button.setPreferredSize(new Dimension(CLBT_WIDTH, CLBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon clb_icon = new ImageIcon("image/function/confirmer/button/cl_button.png");
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
                // 閉じるボタンを押した際の処理をする
                btres.pushClose();
            }
        });

        // はいボタンの生成
        yes_button = new JButton();
        // サイズの設定
        yes_button.setPreferredSize(new Dimension(YESBT_WIDTH, YESBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon yesb_icon = new ImageIcon("image/function/confirmer/button/yes_button.png");
        yes_button.setIcon(yesb_icon);
        yes_button.setContentAreaFilled(false);
        yes_button.setBorderPainted(false);
        yes_button.setFocusPainted(false);
        // ボタンの位置設定
        yes_button.setBounds(YESBT_X, YESBT_Y, YESBT_WIDTH, YESBT_HEIGHT);
        // ボタンを押した際の処理
        yes_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yes_button pressed");
                // はいボタンを推した際の処理をする
                btres.pushYes();
            }
        });

        // いいえボタンの生成
        no_button = new JButton();
        // サイズの設定
        no_button.setPreferredSize(new Dimension(NOBT_WIDTH, NOBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon nob_icon = new ImageIcon("image/function/confirmer/button/no_button.png");
        no_button.setIcon(nob_icon);
        no_button.setContentAreaFilled(false);
        no_button.setBorderPainted(false);
        no_button.setFocusPainted(false);
        // ボタンの位置設定
        no_button.setBounds(NOBT_X, NOBT_Y, NOBT_WIDTH, NOBT_HEIGHT);
        // ボタンを押した際の処理
        no_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("no_button pressed");
                // いいえボタンを推した際の処理をする
                btres.pushNo();
            }
        });

        // 表示する文（改行処理済み）を入れるためのリストを作成
        st_list = createStLabelList(sentence);

        // 後ろの背景、文を入れるフレームを配置
        add(black_bg_label, 0);
        add(frame_label, 0);

        if (format == YES_NO) { // 指定されたフレームが"はいいいいえ"のとき
            // はい、いいえを選択させるためのボタンを配置
            add(yes_button, 0);
            add(no_button, 0);
        }
        else { // 指定されたフレームが"閉じる"のとき
            // 閉じるボタンを配置
            add(cl_button, 0);
        }

        // 文を一行ずつJLabelとして表示する
        for (JLabel lb : st_list) {
            add(lb, 0);
        }
    }


    private int createOneLineMinimum() {
        /* 一行の長さ限界まで入る最低文字数を設定する(createLineListメソッドを呼び出すの際の効率化のため)
        文字によって大きさが異なり、アルファベットとかは一文字が特に小さい
        ここでは一番多き文字を"あ"と考えて一行に入り切る最低文字数を取得できるようにした
         */
        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(st_font);

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
        FontMetrics fm = getFontMetrics(st_font);

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
            label.setFont(st_font);
            // 位置設定
            label.setBounds(ST_X, ST_Y + (st_height+25)*i, ST_WIDTH, st_height);
            // 可視化する
            label.setOpaque(false);
            // label_listに作成した一文のラベルを追加する
            label_list.add(label);
        }

        return label_list;
    }
}
