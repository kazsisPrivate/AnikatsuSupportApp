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

public class Sumire extends Character {

    public Sumire(final Background bg, final Font font, final int mode) {
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
        chara_name = "氷上スミレ";

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

    public Sumire(final Background bg, final Font font, final int mode, final String line, final String end_key) {
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
        chara_name = "氷上スミレ";

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
            chara_images.put("紹介", ImageIO.read(new File("image/character/sumire/紹介.png")));
            chara_images.put("通常1", ImageIO.read(new File("image/character/sumire/通常1.png")));
            chara_images.put("通常2", ImageIO.read(new File("image/character/sumire/通常2.png")));
            chara_images.put("通常3", ImageIO.read(new File("image/character/sumire/通常3.png")));
            chara_images.put("通常4", ImageIO.read(new File("image/character/sumire/通常4.png")));
            chara_images.put("通常5", ImageIO.read(new File("image/character/sumire/通常5.png")));
            chara_images.put("煽る", ImageIO.read(new File("image/character/sumire/煽る.png")));
            chara_images.put("キチる", ImageIO.read(new File("image/character/sumire/キチる.png")));
            // キャラの台詞の吹き出しの画像を読み込む
            // ※ 画像のパスを書いているところの"character"の後ろの"//"の間に画像のディレクトリ名を書く
            frame_img = ImageIO.read(new File("image/character/sumire/frame.png"));
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
        chara_lines.put("紹介", "本日の担当は、氷上スミレ、です。よろしくお願いします。");
        chara_lines.put("通常1", "確かに、今はテストに敵わないかもしれない。でも、それでも、いつかはきっと！");
        chara_lines.put("通常2", "入試に合格するかはわかりません…、でも、行ってみたいっていう自分の気持ちを信じたいんです！");
        chara_lines.put("通常3", "アニ研には運命を感じてるんだ。アニ研は、私の、私たちの進むべき先を導いてくれると思ったから。");
        chara_lines.put("通常4", "やれるだけのことはやった。あとは…、私の思いを歌にのせて。");
        chara_lines.put("通常5", "アニ研に入って、競うことで新しい自分に近づけることがわかったの！それが私に吹いた、最初の風。");
        chara_lines.put("通常6", "歌でトップになる！私は、歌でアイドルを極める！そんなスターライトアニ研クイーンに…、なりたい！");
        chara_lines.put("通常7", "私はあのときの風を、歌でトップになるという想いを大事にしたい…。このコーデで、クイーンになる！");
        chara_lines.put("通常8", "いろんな出会いのおかげで、今の私ができてる。明日の研究発表…、今の私のすべてをぶつけなきゃ！");
        chara_lines.put("通常9", "今日できなかった分も、来年はもっともっと生まれ変わった私になります。約束します！楽しみにしていてください！");
        chara_lines.put("通常10", "誰かが合格すれば、誰かが落ちる。傷付けあってしまうこともあるかも…、そう思ったら、一人でいるようになって。");
        chara_lines.put("煽る", "アニ研にいて、力が湧いてきたんだ！アニ研はどんどん未来()に向かってるって感じて。");
        chara_lines.put("キチる", "覚悟はいいかしら？あなたの血を、吸わせて。");
    }


    private void putCharaEnds() {
        // ※ キャラの台詞の語尾（句読点込み）を設定
        chara_ends.put("通常", "です。"); // 「～です。」に代わる語尾
        chara_ends.put("誇張", "です。"); // 「～です。」に代わって、誇張する語尾(例: ですよ！、だよ！)
        chara_ends.put("報告", "たよ。"); // 「～(し)ました。」に代わる語尾(例: たよ。)
        chara_ends.put("命令1", "てね。"); // 「～(し)てください。」に代わる語尾(例: てね。)
        chara_ends.put("命令2", "てね。"); // 「～(し)ろ！」に代わる語尾(例: なさい！)
        chara_ends.put("疑惑", "たんじゃないかな？"); // 「～(し)たのではないか？」に代わる語尾(例: たんじゃない？)
    }
}
