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

public class Hime extends Character {

    public Hime(final Background bg, final Font font, final int mode) {
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
        chara_name = "白鳥ひめ";

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


    public Hime(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "白鳥ひめ";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/hime/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/hime/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/hime/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/hime/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/hime/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/hime/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/hime/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/hime/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/hime/frame.png"));
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
        chara_lines.put("紹介", "本日は私、白鳥ひめが担当します。みなさん、よろしくお願いします。");
        chara_lines.put("通常1", "アイドル白鳥ひめを待ってくれてる人がいる。待っている人が1人でもいる限り、私は行くわ。");
        chara_lines.put("通常2", "いい？大事なのは、今まで頑張ってきた自分を信じること。そして、その力をすべて出し切って立ち向かうの！");
        chara_lines.put("通常3", "私にできることはなんだって…、アニ研を助けたい！");
        chara_lines.put("通常4", "ごめんなさい。アニ研がこんなに苦しんでいるのに、私何もしてあげられない。わかるの私。アニ研部員だったもの。");
        chara_lines.put("通常5", "お気に入りのコスプレをすると自然と強い気持ちになれる。一緒にこのコスチュームを着ましょう！");
        chara_lines.put("通常6", "私がS4として、アニ研部員の道を照らす星になる。ひとりにはさせない。だから安心して！");
        chara_lines.put("通常7", "皆がいろいろ力になってくれるのは、アニ研部員ならきっとできるって信じてるから。もちろん私もその1人。");
        chara_lines.put("通常8", "みんなの光になれるように！みんなの道を示せるように！今日は私が、一番輝く星になる！");
        chara_lines.put("通常9", "泣いても笑ってもいい。たくさんの経験をして、もっともっと輝いてね。星の頂で待ってるわ。");
        chara_lines.put("通常10", "まだまだ世界には、私の知らないアニカツが待っていたわ！");
        chara_lines.put("煽る", "私を目標にしてくれてうれしいわ。でも、本当にそれでいいのかしら");
        chara_lines.put("キチる", "辛いカレーは、美容と健康にもいいのよ。体が温まって。");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "です。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "です。"); // 「～(名詞)です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "ました。"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てくださいね。"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "てください。"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃないかしら？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)
    }
}
