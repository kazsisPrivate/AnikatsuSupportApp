package component.home;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.*;
import java.util.Random;

public class Background extends JPanel  {

    private BufferedImage bg_img; // 背景画像(characterクラスに渡すために作るだけ)
    private JLabel bg_img_label; // 背景画像のラベル
    private ImageIcon bg_img_icon; // 背景画像
    private final int BG_WIDTH = 1920; // 背景画像の幅
    private final int BG_HEIGHT = 1080; // 背景画像の高さ


    public Background() {
        System.out.println("コンストラクタ");
        setBgImg();
        bg_img_label.setBounds(0, 0, BG_WIDTH, BG_HEIGHT);

        add(bg_img_label);
    }


    private void setBgImg() {
        /* ランダムで背景に使用する画像を選択し、返り値で返す
         */
        // 背景画像を入れているディレクトリにあるファイルの数を調べる（乱数で使用）
        File dir = new File("image/component/home/background/");
        File list[] = dir.listFiles();

        // 背景に使用する画像の番号を入れる
        Random random = new Random();
        int img_num = random.nextInt(list.length);

        // 選択した画像のパスを入れる
        String path = "image/component/home/background/" + img_num + ".jpg";
        System.out.println("背景画像番号" + img_num);

        // 選択した背景画像を読み込む
        try {
            // 画像を読み込む
            bg_img = ImageIO.read(new File(path));
        }
        catch (IOException e) {
            System.out.println("背景画像の読み込みに失敗しました。");
            e.printStackTrace(); // 例外が発生したメソッドを確認
        }

        bg_img_icon = new ImageIcon(path);
        bg_img_label = new JLabel(bg_img_icon);
    }


    public BufferedImage getSubBackImg(final int x, final int y, final int width, final int height) {
        return bg_img.getSubimage(x, y, width, height);
    }
}
