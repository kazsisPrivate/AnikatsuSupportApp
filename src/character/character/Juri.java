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

public class Juri extends Character {

    public Juri(final Background bg, final Font font, final int mode) {
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
        chara_name = "紅林珠璃";

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


    public Juri(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "紅林珠璃";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/juri/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/juri/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/juri/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/juri/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/juri/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/juri/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/juri/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/juri/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/juri/frame.png"));
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
        chara_lines.put("紹介", "Mucho gusto!つまり、初めまして！今日の担当は私、紅林珠璃よ！よろしくね！");
        chara_lines.put("通常1", "あなたの炎はまだ消えていない！なぜなら私は、アニ研の熱い風があなたから吹きつけるのを確かに感じたのだから！");
        chara_lines.put("通常2", "Flotante!つまり、浮いてる！だけど、そういう人がいるからこそ陽キャは輝くの！");
        chara_lines.put("通常3", "Gracias!つまり、ありがとう！私は、この九州工業大学情報工学部アニ研部員の一員になれて幸せ！");
        chara_lines.put("通常4", "1からアニ研部員の勉強をする。アニ研部員としてやっていけると思えるその日まで、講義は受けないって決めたの。");
        chara_lines.put("通常5", "私は、アニ研部員。紅林可憐の娘でもなく、紅林珠璃でもなく、九州工業大学情報工学部のアニ研部員なんだ。");
        chara_lines.put("通常6", "九州工業大学情報工学部の平和は私が守ってみせる…、アニ研部員の名に懸けて！");
        chara_lines.put("通常7", "普段はぽわーんとしてて、控えめに見えるけど…、いざっていうときには熱いよね！もしかしたら私より熱いのかも。");
        chara_lines.put("通常8", "あなたの魂は、まだ冷え切ってない。あなたに、もう一度データベースSを受講する情熱を取り戻したい！");
        chara_lines.put("通常9", "自分がやりたいこと、好きなことを見つけたいっていうのは、すごくいいことだと思った。それが一番大切だから。");
        chara_lines.put("通常10", "まだまだ！私たちアニ研部員はもっと熱くなるよ！アニメに費やした時間がえぐいことは、すごい強みなんだ！");
        chara_lines.put("煽る", "あれ？あなたからは、アンダルシアの風を感じない…。");
        chara_lines.put("キチる", "多分、生粋の陽キャはこのアニ研にはいない……、風が違う。");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "ね。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "です！熱い！"); // 「～です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たわ！"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てね！"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "なさい！"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃない？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)

    }
}
