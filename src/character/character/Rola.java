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

public class Rola extends Character {

    public Rola(final Background bg, final Font font, final int mode) {
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
        chara_name = "桜庭ローラ";

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


    public Rola(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "桜庭ローラ";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/rola/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/rola/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/rola/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/rola/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/rola/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/rola/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/rola/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/rola/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/rola/frame.png"));
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
        chara_lines.put("紹介", "今日は私、桜庭ローラが担当するわ！よろしくね！");
        chara_lines.put("通常1", "だけど、次に勝つのは私！私の原点は、アニ研だから！");
        chara_lines.put("通常2", "気の向くままチャレンジして、自分だけのアニメを見つければいい。自分を信じて！ゴーイングマイウェイ！");
        chara_lines.put("通常3", "先に言っとくけど、あなたがどんなにうまくなってもトップアニオタは無理よ。だってわたしがなるから。");
        chara_lines.put("通常4", "私も知ってるよ、アニ研の今までの頑張り。だから…、頼んだよ。");
        chara_lines.put("通常5", "どうして…、あんなに頑張ったのに…。一緒じゃだめだと思って、アニ研の何倍もボドゲしたのに…！");
        chara_lines.put("通常6", "弱気なアニオタなんてつまらない。前よりもっと成長して、プレミアムアニオタになってかかってきて！");
        chara_lines.put("通常7", "アニカツは勝ち負けじゃない。自分らしく輝いたその先に、未来があると思うんだ。");
        chara_lines.put("通常8", "私やったよ！点数なんかどうでもいい！こんなに気持ちよくテストを終えたのは、生まれて初めて！");
        chara_lines.put("通常9", "これが最後じゃない。まだまだもっと先がある。私がアニオタである限り。私…、もっともっと…、強くなる。");
        chara_lines.put("通常10", "誰かが見てくれているんですね…。努力してれば…、誰かが…！");
        chara_lines.put("煽る", "あきれた。やる気ない人といっしょにやってられないんだけど。");
        chara_lines.put("キチる", "酢昆布を馬鹿にしないで。酢昆布は芸術なんだから。やみつきになる！最高！");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "です。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "よ！おもしろいじゃない！"); // 「～(名詞)です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たよ。"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てね。"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "て！"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃない？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)
    }
}
