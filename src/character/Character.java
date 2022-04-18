package character;

import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.List;
import java.util.Timer;

import component.home.Background;


public class Character extends JPanel {

    protected HashMap<String, BufferedImage> chara_images; /* 様々な場合における画像を入れる
                                                    種類は 通常(複数あり), 驚く,
                                                    笑う(複数あり), 怒る, 煽る, 悲しむ,
                                                    困る であり、複数あるものに関しては
                                                    下記の定数の分だけ用意した
                                                  */
    private final int NORMAL_NUM = 3; // 表情(通常)の画像の種類の数
    private final int SMILE_NUM = 2; // 表情(笑う)の数
    private final int CHARA_WIDTH = 650; // キャラクターの画像の幅、ただし、"紹介"の画像だけはサイズが違う
    private final int CHARA_HEIGHT = 600; // キャラクターの画像の高さ、ただし、"紹介"の画像だけはサイズが違う

    protected BufferedImage frame_img; // キャラの台詞を入れる吹き出しの画像
    private final int FRAME_WIDTH = 960; // 台詞の吹き出し画像の幅
    private final int FRAME_HEIGHT = 288; // 台詞の吹き出し画像の高さ

    private BufferedImage bg_img; // キャラの画像変換の際に使用する

    protected BufferedImage crnt_img, next_img; // その時表示している画像と、次に表示する画像
    protected String crnt_img_key, next_img_key; // その時表示している画像のキーと、次に表示する画像のキー

    protected String crnt_line, next_line; // その時表示している台詞と、次に表示する台詞
    protected String crnt_line_key, next_line_key; // その時表示している台詞のキーと、次に表示する台詞のキー
    protected List<String> crnt_line_list; // その時表示する台詞を改行するためにわけものを格納するList

    protected String chara_name; // キャラクターの名前
    protected HashMap<String, String> chara_lines; // キャラのそれぞれの場合の台詞(AUTO_MODEで使用)
    protected HashMap<String, String> chara_ends; // キャラのそれぞれの場合の台詞(INSTRUCTED_MODEで使用)

    protected List<String> instructed_line_list; // 指示された台詞を改行するためにわけものを格納するList

    private final int CHARA_X = 1500; // キャラクター画像の中心のx座標
    private final int CHARA_Y = 450; // キャラクター画像の中心のy座標

    private final int LINE_X = 1150; // 台詞の文字の左上端のx座標
    private final int LINE_Y = 840; // 台詞の文字の左上端のy座標
    private final int LINE_WIDTH = 600; // 台詞一行の最大横幅
    private Font line_font;// = new Font("MS Pゴシック", Font.BOLD, 35); // キャラクターの台詞を表示する際のフォント
    private int one_line_minimum; // 上のフォントでの上の幅における台詞1行の最低文字数

    private boolean has_changed = true; // 画像、台詞の変更をし終わっているときはtrue, 変更中のときはfalse

    private final int FST_DELTA_WIDTH = 25; // 1から25までの整数を足し合わせた数×2はCHARA_WIDTH(650)となる
    private final int FST_DELTA_HEIGHT = 24; // 1から24までの整数を足し合わせた数×2はCHARA_HEIGHT(600)となる

    private final int FRAME_X = 1440; // 台詞の吹き出し画像の中心のx座標
    private final int FRAME_Y = 890; // 台詞の吹き出し画像の中心のy座標

    private final int DELTA_FRAME_WIDTH = FRAME_WIDTH / FST_DELTA_HEIGHT; // 台詞変更の際に使用する
    private final int DELTA_FRAME_HEIGHT = FRAME_HEIGHT / FST_DELTA_HEIGHT;

    private int next_x, next_y; // 次のキャラの画像の切り替えの際に使用する
    private int next_width, next_height;
    private int delta_width, delta_height;

    private Timer timer; // タスクの処理に使うタイマー
    private TimerTask change_exp_task, change_talk_task; // 表情, 話の変更を一定時間ごとに行うようにするためのタスク

    private int mode; // このインスタンスにおけるモードを入れる
    public static final int AUTO_MODE = 1; // 自動で画像や台詞を更新するモード
    public static final int INSTRUCTED_MODE = 2; // 指示されたタイミングで指示された内容に更新するモード
    public static final int STARTING_MODE = 3; // 指示されたタイミングで指示された内容に更新するモード


    public Character(final Background bg, final Font font, final int mode) {
        /* @param bg: キャラの画像変更に使用する際の切り取った背景画像
        @param font: キャラの台詞のフォント
         */
        super.setOpaque(false);
        // キャラの画像、台詞、語尾のマップの生成
        chara_images = new HashMap<>();
        chara_lines = new HashMap<>();
        chara_ends = new HashMap<>();
        // キャラの画像変更に使用する際の切り取った背景画像を代入
        bg_img = bg.getSubBackImg(CHARA_X-CHARA_WIDTH/2, CHARA_Y-CHARA_HEIGHT/2, CHARA_WIDTH, CHARA_HEIGHT);
        // 台詞の文字のフォントを設定
        line_font = font;
        setFont(line_font);
        // 上のフォントでの上の幅における台詞1行の最低文字数を設定
        one_line_minimum = createOneLineMinimum();
        // モードを設定する
        this.mode = mode;
    }


    public void update() {
        /* 更新処理をする
         */

        if (!has_changed) {
            changeExpression();
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        /* 描画処理をする
         */
        super.paint(g);
        // キャラクター画像を描画
        g.drawImage(crnt_img, CHARA_X-CHARA_WIDTH/2, CHARA_Y-CHARA_HEIGHT/2, this);

        if (mode != STARTING_MODE) { // STARTING_MODE以外のとき
            if (has_changed) { // 通常の状態の時に行う処理
                // 台詞の吹き出し画像の描画
                g.drawImage(frame_img, FRAME_X-FRAME_WIDTH/2, FRAME_Y-FRAME_HEIGHT/2, this);
                // 台詞の描画
                for (int i = 0; i < crnt_line_list.size(); i++) {
                    g.drawString(crnt_line_list.get(i), LINE_X, LINE_Y + 50*i);
                }
            }
            else { // 画像と台詞の変更中にのみ行う処理
                paintChangingChara(g);
            }
        }
    }


    private void paintChangingChara(Graphics g) {
        System.out.println("passhere");
        // 変更する際のエフェクトでだんだん次の画像に変化していくようにする
        // その際に表示する次の画像の一部を作成する
        BufferedImage bg_subimg = bg_img.getSubimage(
                CHARA_WIDTH/2-(CHARA_X-next_x),
                CHARA_HEIGHT/2-(CHARA_Y-next_y),
                next_width,
                next_height
        );
        BufferedImage next_subimg = next_img.getSubimage(
                CHARA_WIDTH/2-(CHARA_X-next_x),
                CHARA_HEIGHT/2-(CHARA_Y-next_y),
                next_width,
                next_height
        );
        // 次の画像の一部描画する
        g.drawImage(bg_subimg, next_x, next_y, this);
        g.drawImage(next_subimg, next_x, next_y, this);

        // 台詞の吹き出し画像の描画
        g.drawImage(frame_img,
                FRAME_X-FRAME_WIDTH/2 + (int)(DELTA_FRAME_WIDTH*delta_height*((double)3/4)), //3/4かけたのは更新時の吹き出し表示位置の調整のため
                FRAME_Y-FRAME_HEIGHT/2,// + (DELTA_FRAME_HEIGHT*delta_height),
                (int)(FRAME_WIDTH * (1-(double)delta_height/FST_DELTA_HEIGHT)),
                (int)(FRAME_HEIGHT * (1-(double)delta_height/FST_DELTA_HEIGHT)),
                this
        );

        if (delta_height == 1) {
            // 表示する画像と台詞を次に表示する予定のものに取り換える
            changeCrntVariables();
            // 後ろに描いている前のキャラの画像を更新して消すためのrepaint()
            repaint();
        }
    }

    private void changeCrntVariables() {
        // 表示する画像と台詞を次に表示する予定のものに取り換える
        crnt_img = next_img;
        crnt_img_key = next_img_key;
        crnt_line = next_line;
        if (mode == AUTO_MODE) {
            // AUTO_MODEのときはマップchara_lineの言葉を使用しているため、更新する
            crnt_line_key = next_line_key;
        }
        // 更新した情報をもとに表示する台詞のListを作成（改行をさせるため）
        crnt_line_list = createLineList(crnt_line);
        // 次の画像と台詞に交換が終わった状態にする
        has_changed = true;
    }


    protected void changeChara() {
        /* キャラクターの画像と台詞の変更を行う前準備を行う
         */
        // 画像、台詞の変更中にする
        has_changed = false;
        // 次の画像と台詞をどれにするかを決め、設定する
        setNextChara();
        // 画像、台詞の変更をするときに使用する変数の設定
        delta_width = FST_DELTA_WIDTH;
        delta_height = FST_DELTA_HEIGHT;
        next_x = CHARA_X;
        next_y = CHARA_Y;
        next_width = 0;
        next_height = 0;

        changeExpression();
        repaint();

        System.out.println("changeChara");
    }


    protected void changeChara(final String line, final String end_key) {
        /* キャラクターの画像と、指定された台詞の変更を行う前準備を行う
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        // 画像、台詞の変更中にする
        has_changed = false;
        // 次の画像と台詞をどれにするかを決め、設定する
        setNextChara(line, end_key);
        // 画像、台詞の変更をするときに使用する変数の設定
        delta_width = FST_DELTA_WIDTH;
        delta_height = FST_DELTA_HEIGHT;
        next_x = CHARA_X;
        next_y = CHARA_Y;
        next_width = 0;
        next_height = 0;

        changeExpression();
        repaint();
    }


    protected void changeChara(final String line1, final String end_key1, final String line2, final String end_key2) {
        /* キャラクターの画像と、指定された台詞の変更を行う前準備を行う
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        // 画像、台詞の変更中にする
        has_changed = false;
        // 次の画像と台詞をどれにするかを決め、設定する
        setNextChara(line1, end_key1, line2, end_key2);
        // 画像、台詞の変更をするときに使用する変数の設定
        delta_width = FST_DELTA_WIDTH;
        delta_height = FST_DELTA_HEIGHT;
        next_x = CHARA_X;
        next_y = CHARA_Y;
        next_width = 0;
        next_height = 0;

        changeExpression();
        repaint();
    }


    protected void changeChara(final String img_key, final String line, final String end_key) {
        /* 指定されたキャラクターの画像と台詞の変更を行う前準備を行う
        @param img_key: 指定された次の画像のキー
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        // 画像、台詞の変更中にする
        has_changed = false;
        // 次の画像と台詞をどれにするかを決め、設定する
        setNextChara(img_key, line, end_key);
        // 画像、台詞の変更をするときに使用する変数の設定
        delta_width = FST_DELTA_WIDTH;
        delta_height = FST_DELTA_HEIGHT;
        next_x = CHARA_X;
        next_y = CHARA_Y;
        next_width = 0;
        next_height = 0;

        changeExpression();
        repaint();
    }


    protected void changeExpression() {
        /* 指定された表情(next_expression)の画像に変化する処理をする
         */
        next_x -= delta_width;
        next_y -= delta_height;
        next_width += delta_width * 2;
        next_height += delta_height * 2;
        delta_width -= 1;
        delta_height -= 1;

        System.out.println("changeExpression");
    }


    public void setFirstImage() {
        crnt_img = chara_images.get("通常1");
        next_img = chara_images.get("通常2");
    }


    protected void setNextChara() {
        /* 次に表示する画像と台詞を設定する
         */
        // 乱数で次に表示する画像を決める
        setNextRandomImg();
        // 台詞を決める
        setRandomLine();
    }

    protected void setNextChara(final String line, final String end_key) {
        /* 次に表示する画像をランダムで決め、台詞を指定されたものに設定する
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        // 乱数で次に表示する画像を決める
        setNextRandomImg();
        // 次の台詞の設定
        next_line = line + chara_ends.get(end_key);
    }

    protected void setNextChara(final String line1, final String end_key1, final String line2, final String end_key2) {
        /* 次に表示する画像をランダムで決め、台詞を指定されたものに設定する
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        // 乱数で次に表示する画像を決める
        setNextRandomImg();
        // 次の台詞の設定
        next_line = line1 + chara_ends.get(end_key1) + line2 + chara_ends.get(end_key2);
    }

    protected void setNextChara(final String img_key, final String line, final String end_key) {
        /* 次に表示する画像と台詞を指定されたものに設定する
        @param img_key: 指定された次の画像のキー
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        // 次の画像の設定
        next_img_key = img_key;
        next_img = chara_images.get(next_img_key);
        // 次の台詞の設定
        next_line = line + chara_ends.get(end_key);
    }


    protected void setNextRandomImg() {
        /* 次に表示する画像をランダムで設定する
         */
        // 乱数で次に表示する画像を決める
        Random random = new Random();
        next_img_key = crnt_img_key;
        // 前の画像とは同じにならないようにする
        while (next_img_key.equals(crnt_img_key)) {
            int num = random.nextInt(12);

            System.out.println(num);

            switch (num) {
                case 0:
                case 1:
                    next_img_key = "通常1";
                    break;
                case 2:
                case 3:
                    next_img_key = "通常2";
                    break;
                case 4:
                case 5:
                    next_img_key = "通常3";
                    break;
                case 6:
                case 7:
                    next_img_key = "通常4";
                    break;
                case 8:
                case 9:
                    next_img_key = "通常5";
                    break;
                case 10:
                    next_img_key = "煽る";
                    break;
                case 11:
                    next_img_key = "キチる";
                    break;
            }
        }
        // 上で決めたキーの画像を次の画像として設定する
        next_img = chara_images.get(next_img_key);
    }


    private void setRandomLine() {
        // 台詞を決める
        if (next_img_key.equals("煽る")) {
            next_line_key = "煽る";
            next_line = chara_lines.get(next_line_key);
        }
        else if (next_img_key.equals("キチる")) {
            next_line_key = "キチる";
            next_line = chara_lines.get(next_line_key);
        }
        else {
            setNormalLine();
        }
    }


    protected void setNormalLine() {
        /* キャラの台詞の中のキーが"通常*"の台詞の中から一つを選ぶ
         */
        Random random = new Random();
        next_line_key = crnt_line_key;

        while (next_line_key.equals(crnt_line_key)) {
            int num = random.nextInt(10);

            switch (num) {
                case 0:
                    next_line_key = "通常1";
                    break;
                case 1:
                    next_line_key = "通常2";
                    break;
                case 2:
                    next_line_key = "通常3";
                    break;
                case 3:
                    next_line_key = "通常4";
                    break;
                case 4:
                    next_line_key = "通常5";
                    break;
                case 5:
                    next_line_key = "通常6";
                    break;
                case 6:
                    next_line_key = "通常7";
                    break;
                case 7:
                    next_line_key = "通常8";
                    break;
                case 8:
                    next_line_key = "通常9";
                    break;
                case 9:
                    next_line_key = "通常10";
                    break;
            }
        }
        // 上で決めたキーの台詞を次の台詞として設定する
        next_line = chara_lines.get(next_line_key);
    }


    private int createOneLineMinimum() {
        /* 一行の長さ限界まで入る最低文字数を設定する(createLineListメソッドを呼び出すの際の効率化のため)
        文字によって大きさが異なり、アルファベットとかは一文字が特に小さい
        ここでは一番多き文字を"あ"と考えて一行に入り切る最低文字数を取得できるようにした
         */
        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(line_font);

        String str = "あ"; // 文字列の幅を計るのに使う
        int width = fm.stringWidth(str); // 切り出した文の横幅
        int minimum = 0; // 設定する最低文字数

        // 文字列の幅が吹き出しの限界サイズLINE_LENGTH以上になったら抜けるようにする
        while (width < LINE_WIDTH) {
            // 一文字増やす
            str = str + "あ";
            width = fm.stringWidth(str);

            minimum++;
        }

        return minimum;
    }


    protected List<String> createLineList(final String line) {
        /* 引数で渡された情報を基にして、line_listを作成する
        @param line: キャラクターの台詞
        @param font: 文字のフォント
        @param one_line_minimum: 一行の長さ限界まで入る最低文字数
         */
        int start_point = 0; // substringで切り出し始める位置
        int end_point = one_line_minimum; // substringで切り出し終える位置
        int width; // 切り出した文の横幅
        List<String> list = new ArrayList<>(); // 切り出した各行を格納するためのリスト

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(line_font);

        while (start_point+one_line_minimum <= line.length()) {
            // 文の切り出し
            String subst = line.substring(start_point, end_point);
            // 文の横幅を取得する
            width = fm.stringWidth(subst);

            // 横幅がST_WIDTHをギリギリ超える場所を探す
            while (width <= LINE_WIDTH && end_point != line.length()) {
                end_point += 1;
                subst = line.substring(start_point, end_point);
                width = fm.stringWidth(subst);
            }
            // 切り出し終了位置を横幅がST_WIDTHをギリギリ超えない場所にする
            if (width > LINE_WIDTH) {
                end_point -= 1;
            }

            // 横幅がST_WIDTHを超えないの文を作成する
            subst = line.substring(start_point, end_point);
            // 横幅がST_WIDTHを超えないの文をリストに格納する
            list.add(subst);

            // 次に切り出し始める位置と切り出し終えるの設定
            start_point = end_point;
            end_point += one_line_minimum;
        }
        // 最後の入れ切れていない分をリストに追加する
        int max = line.length();
        String rest = line.substring(start_point, max);
        // 横幅がST_WIDTHを超えないの文をリストに格納する
        list.add(rest);

        return list;
    }


    public void setTrueIntoHasChanged() {
        /* 次の画像と台詞に変える処理を始めるようにする(AUTO_MODE)
         */
        System.out.println("setHasChanged_AUTO");
        changeChara();
    }


    public void setTrueIntoHasChanged(final String line, final String end_key) {
        /* 次の画像と台詞に変える処理を始めるようにする(INSTRUCTED_MODE)
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        System.out.println("setHasChanged_INSTRUCTED_a");
        changeChara(line, end_key);
    }


    public void setTrueIntoHasChanged(final String line1, final String end_key1, final String line2, final String end_key2) {
        /* 次の画像と台詞に変える処理を始めるようにする(INSTRUCTED_MODE)
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        System.out.println("setHasChanged_INSTRUCTED_a");
        changeChara(line1, end_key1, line2, end_key2);
    }


    public void setTrueIntoHasChanged(final String img_key, final String line, final String end_key) {
        /* 次の画像と台詞に変える処理を始めるようにする(INSTRUCTED_MODE)
        @param img_key: 指定された次の画像のキー
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        System.out.println("setHasChanged_INSTRUCTED_b");
        changeChara(img_key, line, end_key);
    }


    public void changeModeToAutoMode(){
        /* モードをAUTO_MODEに変更し、変更する際の処理を行う
         */
        // モードをAUTO_MODEに変更する
        this.mode = AUTO_MODE;

        // 台詞をAUTO_MODEのものに変える
        if (!has_changed) { // 画像の変更中のとき
            // 台詞だけ変える
            setRandomLine();
        }
        else { // 画像の変更中でないとき
            // 画像、台詞ともに変える
            changeChara();
        }
    }


    public void changeModeToInstMode(final String line, final String end_key) {
        /* モードをINSTRUCTED_MODEに変更し、変更する際の処理を行う
        @param line: 指定された次の台詞(語尾抜き)
        @param end_key: 指定された台詞の語尾のキー
         */
        // モードをINSTRUCTED_MODEに変更する
        this.mode = INSTRUCTED_MODE;

        // 台詞をINSTRUCTED_MODEのものに変更する
        if (!has_changed) { // 画像の変更中のとき
            // 台詞だけ変える
            // 次の台詞の設定
            next_line = line + chara_ends.get(end_key);
        }
        else { // 画像の変更中でないとき
            // 画像、台詞ともに変える
            changeChara(line, end_key);
        }
    }


    public void setCharaFont(final Font font) {
        /* フォントを変更する
         */
        line_font = font;
        setFont(font);
        // 上のフォントでの上の幅における台詞1行の最低文字数を設定
        one_line_minimum = createOneLineMinimum();
    }


    public String getCharaEnd(final String end_key) {
        return chara_ends.get(end_key);
    }


    public String getCharaName() {
        return chara_name;
    }
}
