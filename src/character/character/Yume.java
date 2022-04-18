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

public class Yume extends Character {

    public Yume(final Background bg, final Font font, final int mode) {
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
        chara_name = "虹野ゆめ";

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


    public Yume(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "虹野ゆめ";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/yume/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/yume/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/yume/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/yume/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/yume/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/yume/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/yume/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/yume/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/yume/frame.png"));
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
        chara_lines.put("紹介", "本日は私、虹野ゆめが担当しまーす！よろしくお願いします！");
        chara_lines.put("通常1", "わたしもここが、新しいスタートライン。ゆめの夢は、九州工業大学情報工学部アニ研部員になること。");
        chara_lines.put("通常2", "怖い…、だから嫌です。でも…、夢(トップアニオタ)をあきらめるのはもっと嫌。諦めたくないっ！");
        chara_lines.put("通常3", "あの頃描いた私の夢(トップアニオタ)に近づいているはずなのに…、怖いだなんて、悔しいよ…！");
        chara_lines.put("通常4", "もう絶対に(教授に)頼らない！私は自分を信じる！");
        chara_lines.put("通常5", "今日は負けちゃったけど、これも強くなるためのチャンスなのかもしれない。……やっぱりひめカレー、辛いなあ。");
        chara_lines.put("通常6", "私が心を輝かせたいって思っていた中には、アニ研部員も入っているんですよ。私はみんなと一緒に輝きたい！");
        chara_lines.put("通常7", "あんな風になりたいな…！誰よりも高いところで、誰よりもキラキラ輝く、一番星みたいに！");
        chara_lines.put("通常8", "私は歌が好き。たとえ、他の人より劣っていても…、歌いたい！この気持ちだけは、誰にも負けない！");
        chara_lines.put("通常9", "もっともっと、素敵な自分になれる。輝けるって心から思えた。私が一番大切にしたいこと。");
        chara_lines.put("通常10", "大丈夫…、自分を信じようよ、ゆめ。みんながいれば私は最強だよ！みんなと一緒に輝いてみせる！");
        chara_lines.put("煽る", "アニ研部員でも、迷うことがあるんですね。");
        chara_lines.put("キチる", "あっ、そうだ！友情の証に、あれやろうよ！S4のお茶会、通称、星々の集いごっこだよ！");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "です。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "だよ！"); // 「～です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たよ。"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てね！"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "てよね！"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃないの～？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)
    }
}
