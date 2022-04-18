package character;

import component.home.Background;
import character.Character;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import java.awt.Font;
import java.util.ArrayList;

public class Template extends Character {

    public Template(final Background bg, final Font font, final int mode) {
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
        chara_name = "";

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
        else {

        }

    }

    public Template(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "";

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
        else {

        }

    }

    private void putCharaImages() {
        try {
            // 全ての表情の画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            chara_images.put("紹介", ImageIO.read(new File("image/character//紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character//通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character//通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character//通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character//通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character//通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character//煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character//キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character//frame.png"));
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
        chara_lines.put("紹介", "");
        chara_lines.put("通常1", "");
        chara_lines.put("通常2", "");
        chara_lines.put("通常3", "");
        chara_lines.put("通常4", "");
        chara_lines.put("通常5", "");
        chara_lines.put("通常6", "");
        chara_lines.put("通常7", "");
        chara_lines.put("通常8", "");
        chara_lines.put("通常9", "");
        chara_lines.put("通常10", "");
        chara_lines.put("煽る", "");
        chara_lines.put("キチる", "");
    }

    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", ""); // 「～です。」に代わる語尾
        chara_ends.put("誇張", ""); // 「～(名詞)です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", ""); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", ""); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", ""); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", ""); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)

    }
}
