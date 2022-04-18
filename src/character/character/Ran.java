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

public class Ran extends Character {

    public Ran(final Background bg, final Font font, final int mode) {
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
        chara_name = "紫吹蘭";

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


    public Ran(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "紫吹蘭";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/ran/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/ran/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/ran/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/ran/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/ran/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/ran/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/ran/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/ran/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/ran/frame.png"));
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
        chara_lines.put("紹介", "今日は私、紫吹蘭が担当する。よろしく頼む。");
        chara_lines.put("通常1", "それぞれのやり方やたどる道は違っても、目指すゴールは1つ。これからも、もっとアニカツしていけると思います！");
        chara_lines.put("通常2", "私にとっては、アニ研が私の居場所なんです。隣に誰が来ても、誰と組んでも、紫吹蘭は紫吹蘭です。");
        chara_lines.put("通常3", "「がんばってね」じゃなくて「がんばろう」。私が探してた言葉……！");
        chara_lines.put("通常4", "大きな夢が何か、何をつかみ取りたいかを決めると、そのためにもっともっと、走りたくなるよな！");
        chara_lines.put("通常5", "友達か…、正直私も驚いている。応援してくれる友達のために頑張ろうって思ったのは初めてだ。");
        chara_lines.put("通常6", "ランウェイは、アニ研部員のあらゆる思いを飛び立たせる道であって、あたしたちモデルの飛び立つ道じゃない。");
        chara_lines.put("通常7", "あたしは当たり前のことを言ったまで。ランウェイから羽ばたいたのは、あんた自身だ。");
        chara_lines.put("通常8", "やっぱりすごかったよ、アニ研部員は。トップ教授が立ち上がって拍手してた。あんなのまずないことだ。");
        chara_lines.put("通常9", "まだまだ先は長いぞ、アニオタの道は。行くぞ。");
        chara_lines.put("通常10", "私は、講義を抜け出してきました！一緒にボドゲをしたい、仲間がいるから…！");
        chara_lines.put("煽る", "アニ研から、一緒に消えていかないようにな。");
        chara_lines.put("キチる", "お前のことが好きだって言いそびれてたよ…、知られたら恥ずかしいって思ってたのかも。ごめん、えびぽん！");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "です。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "だ。"); // 「～(名詞)です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たぞ。"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てくれ。"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "ろ！"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃないか？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)

    }
}
