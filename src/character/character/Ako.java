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

public class Ako extends Character {

    public Ako(final Background bg, final Font font, final int mode) {
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

        // キャラクターの名前を設定
        chara_name = "早乙女あこ";

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

    public Ako(final Background bg, final Font font, final int mode, final String line, final String end_key) {
        /* INSTRUCTED_MODEで最初に話す言葉を指定するコンストラクタ
         */
        super(bg, font, mode);

        // キャラクターの画像を設定する
        putCharaImages();

        // AUTO_MODEのときに話す台詞をセットする
        putCharaLines();
        // キャラの語尾を設定する
        putCharaEnds();

        // キャラクターの名前を設定
        chara_name = "早乙女あこ";

        // 最初に表示する画像を設定する
        crnt_img_key = "紹介";
        crnt_img = chara_images.get(crnt_img_key);

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/ako/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/ako/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/ako/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/ako/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/ako/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/ako/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/ako/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/ako/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            frame_img = ImageIO.read(new File("image/character/ako/frame.png"));
        }
        catch (IOException e) {
            System.out.println("キャラクター画像の読み込みに失敗しました。");
            e.printStackTrace(); // 例外が発生したメソッドを確認
        }
    }


    private void putCharaLines() {
        // ホーム画面で定期的に更新される台詞を要素として追加
        chara_lines.put("紹介", "本日はこのわたくし、早乙女あこが担当いたしますわ！よろしくお願い致しますわ！");
        chara_lines.put("通常1", "あとは自分で何とかしますわ。あなたは家に帰って自分の勉強をしなさい。");
        chara_lines.put("通常2", "ニャっ！？突然何を言い出しますの！？か…可愛いだなんて……そ、そんなことわかってますわ！");
        chara_lines.put("通常3", "アイドルと数学は切っても切れぬ関係なのですのよ。九州工業大学情報工学部ならなおさらですわ。");
        chara_lines.put("通常4", "本気で見てほしいなら、(教授を)全力で振り向かせて見せなさいな。きっと単位をくれますわ。");
        chara_lines.put("通常5", "このアニ研の神ポジはわたくしただ1人！絶対に奪い返して見せますわ！");
        chara_lines.put("通常6", "わたくし………今ならできる気がしますわ！ボドゲの練習、付き合ってくださいませ！");
        chara_lines.put("通常7", "わたくしは、大好きなみんなのために、大切な萌え豚たちのために歌いますわ！早乙女あこ、輝いてまいりますわ！");
        chara_lines.put("通常8", "悔しいんですの…アニ研のすごさはわたくしがよく知ってますわ！いやになるくらいに！だからこそ腹が立ちますの！");
        chara_lines.put("通常9", "いやですわ、空気の読めない人って。…自分のこと？なんですって！シャーッ！");
        chara_lines.put("通常10", "そうですわ。どんな時も素直が一番。自分の性癖をさらけ出しなさいな。");
        chara_lines.put("煽る", "わたくしの脳内データベースによるとカタカタカタカタ……ピンポン！あなた、ぼっちですわね！");
        chara_lines.put("キチる", "すすすすすすすすすすすす、すばるきゅん！？ニャハァ～～～～～～～！！！！！！");
    }


    private void putCharaEnds() {
        // キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "ですわ。");
        chara_ends.put("誇張", "ですわ！"); // 「～です」に代わって、誇張する語尾(例: ですよ！)
        chara_ends.put("報告", "ましたわ。"); // 「～(し)ました」に代わる語尾
        chara_ends.put("命令1", "てくださいまし。"); // 「～(し)てください」に代わる語尾
        chara_ends.put("命令2", "なさいな！"); // 「～(し)ろ！」に代わる語尾
        chara_ends.put("疑惑", "たんじゃないですの？"); // 「～(し)たのではないか？」に代わる語尾

    }
}
