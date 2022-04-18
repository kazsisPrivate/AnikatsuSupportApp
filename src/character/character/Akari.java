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

public class Akari extends Character {

    public Akari(final Background bg, final Font font, final int mode) {
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
        chara_name = "大空あかり";

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

    public Akari(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "大空あかり";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/akari/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/akari/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/akari/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/akari/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/akari/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/akari/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/akari/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/akari/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/akari/frame.png"));
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
        chara_lines.put("紹介", "きょっーうのお空はどーんなそらっ？大空お天気の時間です！今日の担当は、私、大空あかりです。");
        chara_lines.put("通常1", "自分だけの光…、小さくたって私にも自分の光があるってアニ研に言ってもらったのに…、私、輝けないんです。");
        chara_lines.put("通常2", "外せないんです！私も、ステップアップするために、絶対に研究発表を成功させないといけないから！");
        chara_lines.put("通常3", "先輩、私、スターライトアニ研クイーンになります。");
        chara_lines.put("通常4", "(講義)苦しかったのに…、苦しかったのに……、でも、全部すてきだ！(苦し紛れ)");
        chara_lines.put("通常5", "クイーンになりたい、そしてもちろん、アイカツを見てくれる人たちにも喜んでもらいたい。");
        chara_lines.put("通常6", "私、笑顔のきっかけになれるようなスターライトアニ研クイーンに、アイドルになりたかったんです。");
        chara_lines.put("通常7", "話で聞くより、アニメーション活動、アニカツって、ほんとは熱いんだ！");
        chara_lines.put("通常8", "私……、私…、あのときに決めたんだ！九州工業大学情報工学部アニ研部員になるって！");
        chara_lines.put("通常9", "ここからが、本当の私のスタート。トップアニ研部員のスタートラインに、今、私は立ったんだ。");
        chara_lines.put("通常10", "アニ研に来て良かった。スタートラインに立ってみないと、わからないことばっかりだ。");
        chara_lines.put("煽る", "いえ、いいです。アニ研部員のこと、信じてますから()");
        chara_lines.put("キチる", "私…、ツリーを切りに行きたい(唐突)！アイッカ～ツ！アイッッカ～ツ！");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "ですよ。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "だよ！"); // 「～です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たよ！"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てね。"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "て！"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃないかな？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)
    }
}
