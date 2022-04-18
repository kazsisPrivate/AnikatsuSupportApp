package function;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;


public class Loading extends JPanel {
    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private JLabel loading_label;

    public ShowLoadingLabel show_loading_th;
    public HideLoadingLabel hide_loading_th;

    private boolean isExsisting = false;

    private Timer timer;


    public Loading() {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // loadingのgifアニメーション
        ImageIcon loading_icon = new ImageIcon("image/function/loading/loading.gif");
        loading_label = new JLabel(loading_icon);
        loading_label.setBounds(FRAME_WIDTH/2 - 220, FRAME_HEIGHT/2 - 145, 441, 291);
        loading_label.setOpaque(false);
    }


    public void initializeThreads() {
        show_loading_th = new ShowLoadingLabel();
        hide_loading_th = new HideLoadingLabel();
        isExsisting = true;
    }


    public class ShowLoadingLabel extends Thread {
        @Override
        public void run() {
            int cnt = 0;
            add(loading_label, 0);

            timer = new Timer();
            timer.schedule(new PaintTask(), 0, 50);
            while (true) {
                if (!isExsisting) {
                    break;
                }
            }
        }
    }


    public class PaintTask extends TimerTask {
        @Override
        public void run() {
            paintImmediately(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }
    }


    public class HideLoadingLabel extends Thread {
        @Override
        public void run() {
            isExsisting = false;
            timer.cancel();
            remove(loading_label);
            paintImmediately(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }
    }
}
