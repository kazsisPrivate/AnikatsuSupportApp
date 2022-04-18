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

public class Yurika extends Character {

    public Yurika(final Background bg, final Font font, final int mode) {
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
        chara_name = "藤堂ユリカ";

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


    public Yurika(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "藤堂ユリカ";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/yurika/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/yurika/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/yurika/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/yurika/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/yurika/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/yurika/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/yurika/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/yurika/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/yurika/frame.png"));
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
        chara_lines.put("紹介", "今日は、600年生き続けるドラキュラ伯爵の末裔、この藤堂ユリカ様が担当してあげるわ！");
        chara_lines.put("通常1", "私は…、もう一度あの評価(秀)をもらえればファンの前に立てると思うの！だからお願い！秀を私にください！");
        chara_lines.put("通常2", "でも私、アイカツが大好きなの！今はまだ、すべてを捧げられてないかもしれない…、でも！いつかきっとそうなる！");
        chara_lines.put("通常3", "どんな努力だってする！私は、誰よりも九州工業大学アニ研にふさわしいアニオタになります！");
        chara_lines.put("通常4", "ユリカ様からのお恵みよ！べっ、別にあなたが元気がないとかそんなこと思ってなくもなくもないんだからねっ！");
        chara_lines.put("通常5", "うっ、うるさいわよ！今宵が満月であれば、ヴァンパイアの力でアイカツおじさんに変身できたのにっ…！");
        chara_lines.put("通常6", "失礼ね。私はいつでも大真面目よ。吸血鬼(ニート)にとって太陽の光がいかに危険か……。");
        chara_lines.put("通常7", "そしてある日、鏡を見るの。きっと違う自分、あこがれていた自分(アニ研部員)に会えるから。");
        chara_lines.put("通常8", "血を吸われたい下部たちよ、九州工業大学情報工学部アニメーション研究部で待ってるわ！");
        chara_lines.put("通常9", "だ・か・ら～、ヴァンパイアは早起きが苦手だって言ってるのに～。1限に出席させるなんて、600年早くってよ！");
        chara_lines.put("通常10", "あんまり甘えてると血を吸うわよ！アニ研は九工大の魂！その世界の部員になっていいのは、アイカツの住人だけ。");
        chara_lines.put("煽る", "結構よ！やはり吸血鬼は孤独なもの！ユリカ様にふさわしいアニ研部員なんて存在しないのよ。");
        chara_lines.put("キチる", "このシリアルが、ユリカ様にふさわしい朝食だっていうわけ？おいしくなかったらミルク、じゃなくて血を吸うわよ！");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "ね。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "よ！"); // 「～です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たわ。"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "なさい。"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "なさい！でないと血を吸うわよ！"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃなくって？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)
    }
}
