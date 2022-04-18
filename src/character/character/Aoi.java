package character.character;

import component.home.Background;
import character.Character;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import java.awt.Font;
import java.util.ArrayList;

public class Aoi extends Character {

    public Aoi(final Background bg, final Font font, final int mode) {
        super(bg, font, mode);

        // キャラクターの画像を設定する
        putCharaImages();

        // AUTO_MODEのときに話す台詞をセットする
        putCharaLines();
        // キャラの語尾を設定する
        putCharaEnds();

        // 最初に表示する画像を設定する
        crnt_img_key = "紹介";
        crnt_img = chara_images.get(crnt_img_key);

        // ※ キャラクターの名前を設定
        chara_name = "霧矢あおい";

        if (mode == AUTO_MODE) {
            // 最初に表示する台詞を設定する
            crnt_line_key = "紹介";
            crnt_line = chara_lines.get(crnt_line_key);
            crnt_line_list = createLineList(crnt_line);
        }
        else if (mode == INSTRUCTED_MODE) {

            // 最初に表示する台詞を設定する(コンストラクタで話す言葉の指定なしなので無言とする)
            crnt_line = " ";
            crnt_line_list = createLineList(crnt_line);
        }
    }


    public Aoi(final Background bg, final Font font, final int mode, final String line, final String end_key) {
        /* INSTRUCTED_MODEで最初に話す言葉を指定するコンストラクタ
         */
        super(bg, font, mode);

        // キャラクターの画像を設定する
        putCharaImages();

        // AUTO_MODEのときに話す台詞をセットする
        putCharaLines();
        // キャラの語尾を設定する
        putCharaEnds();

        // 最初に表示する画像を設定する
        crnt_img_key = "紹介";
        crnt_img = chara_images.get(crnt_img_key);

        // ※ キャラクターの名前を設定
        chara_name = "霧矢あおい";

        if (mode == AUTO_MODE) {
            // 最初に表示する台詞を設定する
            crnt_line_key = "紹介";
            crnt_line = chara_lines.get(crnt_line_key);
            crnt_line_list = createLineList(crnt_line);
        }
        else if (mode == INSTRUCTED_MODE) {
            // 最初に表示する台詞を設定する
            crnt_line = line + chara_ends.get(end_key);
            crnt_line_list = createLineList(crnt_line);
        }
    }


    private void putCharaImages() {
        try {
            // 全ての表情の画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            chara_images.put("紹介", ImageIO.read(new File("image/character/aoi/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/aoi/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/aoi/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/aoi/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/aoi/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/aoi/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/aoi/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/aoi/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/aoi/frame.png"));
        }
        catch (IOException e) {
            System.out.println("キャラクター画像の読み込みに失敗しました。");
            e.printStackTrace(); // 例外が発生したメソッドを確認
        }
    }


    private void putCharaLines() {
        // ホーム画面で定期的に更新される台詞を要素として追加
        // ※ それぞれの台詞を書き込む。50文字以内で書くように。
        // ※ 最初の「紹介」は最初だけ表示する紹介文
        // ※ 「通常1～10」と「煽る」、「キチる」はホーム画面でランダムに発する台詞
        chara_lines.put("紹介", "本日は私、霧矢あおいが担当します！よろしくお願いします！");
        chara_lines.put("通常1", "私、自分より周りを見ちゃうのは弱点だって思ってた。でも、そんな私を必要としてくれる誰かがいるのなら…。");
        chara_lines.put("通常2", "私は私であればいい。みんなの花を咲かせる支えとなる。そんな私に。");
        chara_lines.put("通常3", "私、うれしいんだよ…！あなたがもっと頑張って、もっとすごいアニオタになるの、楽しみだよ！");
        chara_lines.put("通常4", "好きなことなら目が輝くっていうのはアニオタならみ～んな知ってる常識！生き生きとした表情を心掛けるべし！");
        chara_lines.put("通常5", "全力を尽くして戦った。それでも負けたのは、絶対に勝ちたいっていう爆発力が足りなかったから……。");
        chara_lines.put("通常6", "今のシーン、撮り直しをお願いします！アニ研部員としての覚悟をのせきれなかった気がして…。");
        chara_lines.put("通常7", "叶うかどうかはわからないけれど…、夢があるからがんばれてるんだよね！きっと！");
        chara_lines.put("通常8", "こうなるかもって思ってた、いつかテストが始まるって。まさかこんなに早く来るとは思わなかったけど。");
        chara_lines.put("通常9", "自由に笑って、歌って、踊って、輝いてるアニ研部員に、私はあこがれたんだ。");
        chara_lines.put("通常10", "私たちの秘密は絶対守らなくちゃ！アニ研は老害比率が高めなこととか！");
        chara_lines.put("煽る", "も、もしかして……、お泊りモードですか？(ドン引き)");
        chara_lines.put("キチる", "アニ研のにおいがわかるなんて、アニオタの道を順調に進んでる証！穏やかじゃない！");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "です。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "だよ！穏やかじゃない！"); // 「～です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たよ。"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てね。"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "て！"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんだと思う。"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)

    }
}
