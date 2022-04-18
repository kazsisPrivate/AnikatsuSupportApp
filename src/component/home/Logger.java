package component.home;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.Font;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;


public class Logger extends JPanel {

    private JTextArea jtarea; // logを表示するtext area
    private JScrollPane jspanel; // 上のJTextAreaにスクロールバーを付けたパネル
    JScrollBar jsbar;
    private final int AREA_WIDTH = 800; // JTextAreaの幅
    private final int AREA_HEIGHT = 450; // JTextAreaの高さ
    private final int AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int AREA_Y = 200; // スクロールパネルの左上端のy座標

    private JLabel log_icon_label; // logのアイコンの画像のラベル
    private ImageIcon log_icon; // logのアイコンの画像
    private final int ICON_WIDTH = 367; // アイコン画像の幅
    private final int ICON_HEIGHT = 200; // アイコン画像の高さ
    private final int ICON_X = 30; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private JLabel log_frame_label; // logのフレームの画像のラベル
    private ImageIcon log_frame; // logのフレームの画像
    private final int FRAME_WIDTH = 860; // フレームの幅
    private final int FRAME_HEIGHT = 510; // フレームの高さ
    private final int FRAME_X = 50; // フレームの左上端のx座標
    private final int FRAME_Y = 170; // フレームの左上端のy座標


    public Logger() {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // logを表示するスクロールパネルのインスタンスを取得、設定
        jtarea = new JTextArea();
        jspanel = new JScrollPane(jtarea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // サイズの設定
        jspanel.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        // 編集等を直接text areaに行えないように設定
        jtarea.setEnabled(false);
        // 文字の色を設定
        jtarea.setDisabledTextColor(Color.BLACK);
        // 文字をtext area内で折り返すように設定
        jtarea.setLineWrap(true);
        jtarea.setWrapStyleWord(true);
        // フォントの設定
        jtarea.setFont(new Font("HGPゴシックM", Font.PLAIN, 18));
        // パネルの配置を設定
        jspanel.setBounds(AREA_X, AREA_Y, AREA_WIDTH, AREA_HEIGHT);

        jsbar = jspanel.getVerticalScrollBar();

        // ログをセットする
        setLog();

        // logのアイコンのラベルのインスタンスを取得、設定
        log_icon = new ImageIcon("image/component/home/logger/icon/icon.png");
        log_icon_label = new JLabel(log_icon);
        log_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // logの枠のラベルのインスタンスを取得、設定
        log_frame = new ImageIcon("image/component/home/logger/frame/frame.jpg");
        log_frame_label = new JLabel(log_frame);
        log_frame_label.setBorder(new LineBorder(Color.BLACK, 2, true));
        log_frame_label.setBounds(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);

        add(log_icon_label);
        add(jspanel);
        add(log_frame_label);
    }


    public void addLog(final String log) {
        /* logをtext areaに追加する
         */
        String log_msg = "[" + getLogTime() + "]\n" +log + "\n\n";
        jtarea.append(log_msg);

        // スクロールバーを下にする
        jspanel.getViewport().scrollRectToVisible(new Rectangle(0, Integer.MAX_VALUE - 1, 1, 1));

    }


    private String getLogTime() {
        /* logが追加された年月日、時刻を返す
         */
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String now = sdf.format(calendar.getTime());

        return now;
    }


    private void setLog() {
        /* jtextareaにログをセットするメソッド
         */
        File log_file = new File("log.txt");

        if (log_file.exists()) { // log.txtファイルがあるとき
            try {
                // 一か月以上前のログを削除したログを作成する
                List<String> log_list = createNewLog(log_file);
                // logを表示するエリアに新しいログを追加する
                for (String line: log_list) {
                    jtarea.append(line+"\n");
                }
            } catch (IOException e) {
                System.out.println("過去のログの削除に失敗しました。");
                e.printStackTrace();
            }
        }
    }


    private List<String> createNewLog(final File log_file) throws IOException {
        /* log.txtから読み込んだログの中で、一か月以上前のログを削除し、削除し終えたログをリストとして返す
        @param log_file: log.txtファイル
        @return log_list: 一か月以上前のログを削除したログの各行のリスト
         */
        // log.txtを読み込むためのFileReaderクラスとBufferedReaderクラスのオブジェクトを生成
        FileReader fr = new FileReader(log_file);
        BufferedReader br = new BufferedReader(fr);

        // 先月の日付を取得
        Calendar calendar_last = Calendar.getInstance();
        calendar_last.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        //String last_month_date = sdf.format(calendar_last.getTime());

        String prev_line = "";
        String crnt_line = br.readLine(); // ファイルから読み込んだ一行
        while (crnt_line != null) {
            if (prev_line.equals("") && !crnt_line.equals("")) { // 読み込んだ行が日付の行のとき
                // ログの日付の部分だけ取り出す
                String log_date = crnt_line.substring(1, 11); // ファイルから読み込む一つ一つのログの日付
                Calendar calendar_log = Calendar.getInstance();
                try {
                    calendar_log.setTime(sdf.parse(log_date));
                } catch (ParseException e) {
                    System.out.println("logの日付の変更失敗。");
                }

                if (calendar_last.compareTo(calendar_log) != -1) { // そのログの日付が一か月以上前であるとき
                    while (!crnt_line.equals("")) { // それぞれのログの区切りとしている空行が見つかるまで
                        crnt_line = br.readLine();
                    }
                }
                else { // そのログの日付が一か月以内のものであるとき
                    break;
                }
            }

            // 前の行を現在読み込んだ行に変更する
            prev_line = crnt_line;
            // ファイルから次の一行を読み込む
            crnt_line = br.readLine();
        }

        // 一か月以上前のログを削除したログの各行のリストを作成する
        List<String> log_list = new ArrayList<>(); // 返り値として返すリスト
        while (crnt_line != null) { // log.txtの最後の行まで
            log_list.add(crnt_line);
            crnt_line = br.readLine();
        }

        if (log_list.size() != 0) { // リストに何か入っているとき
            log_list.remove(log_list.size()-1); // なぜか書き込みの際に本来ないはずなのに
                                                    // 最後に追加されてしまっている余計な一行を取り除く
        }

        // ファイルを閉じてリソースを解放する
        br.close();

        return log_list;
    }


    public void createNewLogFile() throws IOException {
        /* アプリケーションを閉じる際にログを作成するために呼び出すメソッド
         */
        // log.txtファイル作成のためのFileWriterクラスのオブジェクトとPrintWriterクラスのオブジェクトを生成
        FileWriter fw = new FileWriter("log.txt");
        PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

        // ログの内容をファイルに書き込む
        pw.println(jtarea.getText());

        // ファイルを閉じてリソースを解放する
        pw.close();
    }


    public void setEnabled(final boolean f) {
        if (f) {
            add(jspanel, 0);
            jspanel.getViewport().setView(jtarea);
        }
        else {
            remove(jspanel);
        }
    }
}
