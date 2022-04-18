package component.home.anikatsudb.presentation;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;
import function.db.DBOperator;


public class PstInfoDeleter extends DBOperator {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel upd_icon_label; // Updateのアイコンの画像のラベル
    private ImageIcon upd_icon; // Updateのアイコンの画像
    private final int ICON_WIDTH = 270; // アイコン画像の幅
    private final int ICON_HEIGHT = 130; // アイコン画像の高さ
    private final int ICON_X = 5;//650; // アイコンの左上端のx座標
    private final int ICON_Y = 5; // アイコンの左上端のy座標

    private final int LB_FONT_SIZE = 30; // 表の上のラベルの文字の大きさ
    private final Font LABEL_FONT = new Font("HGPゴシックM", Font.PLAIN, LB_FONT_SIZE); // 表の上のラベルのフォント

    private final int ROW_HEIGHT = 25; // 表の一行の高さ
    private final Font TABLE_FONT = new Font("游明朝", Font.PLAIN, ROW_HEIGHT-5); // 表のフォント

    private HashMap<String, String[]> bgt_info_map; // 各部員の部員番号をキーとし、氏名とハンドルネームを格納しているマップ

    private final int FD_FONT_SIZE = LB_FONT_SIZE - LB_FONT_SIZE/3; // 入力のフィールドの文字の大きさ
    private final Font FIELD_FONT = new Font("游明朝", Font.PLAIN, FD_FONT_SIZE); // 入力のフィールドのフォント

    private JTextField year_field; // 年を表示するテキストフィールド
    private final int YEARFD_WIDTH = LB_FONT_SIZE * 3; // 年を表示するテキストフィールドの幅
    private final int YEARFD_HEIGHT = LB_FONT_SIZE; // 年を表示するテキストフィールドの高さ
    private final int YEARFD_X = 280; // 年を表示するテキストフィールドの左上端のx座標
    private final int YEARFD_Y = 90; // 年を表示するテキストフィールドの左上端のy座標
    private JLabel year_label; // 入力フィールドの右に置く"年の研究発表情報"というラベル
    private final int YEARLB_WIDTH = LB_FONT_SIZE * 8; // "年の研究発表情報"というラベルの幅
    private final int YEARLB_HEIGHT = LB_FONT_SIZE; // "年の研究発表情報"というラベルの高さ
    private final int YEARLB_X = YEARFD_X + YEARFD_WIDTH + LB_FONT_SIZE/3; // "年の研究発表情報"というラベルの左上端のx座標
    private final int YEARLB_Y = YEARFD_Y - LB_FONT_SIZE/10; // "年の研究発表情報"というラベルの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 90; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 50; // 検索ボタンの高さ
    private final int SCHBT_X = 650; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 80; // 検索ボタンの左上端のy座標

    private DefaultTableModel pst_table_model;
    private JTable pst_table; // 研究発表情報一覧表
    private JScrollPane pst_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int PST_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int PST_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int PST_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int PST_AREA_HEIGHT = 750; // スクロールパネルの高さ

    private int dlt_row_num; // 削除対象の行
    private String dlt_data[]; // 削除対象の行の情報

    private Confirmer dlt_confirmer; // データベースの情報を削除するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったPstDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public PstInfoDeleter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("削除したい研究発表情報の行の削除ボタンを押し", "命令1");


        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Updateのアイコンのラベルのインスタンスを取得、設定
        upd_icon = new ImageIcon("image/component/home/anikatsudb/presentation/deleter/icon/icon.png");
        upd_icon_label = new JLabel(upd_icon);
        upd_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // "年の研究発表情報"のラベルを作る
        year_label = new JLabel("年の研究発表情報");
        // フォントを設定
        year_label.setFont(LABEL_FONT);
        // 位置設定
        year_label.setBounds(YEARLB_X, YEARLB_Y, YEARLB_WIDTH, YEARLB_HEIGHT);
        // 発表年を入力するテキストフィールドを作成する
        year_field = new JTextField();
        // サイズを設定する
        year_field.setPreferredSize(new Dimension(YEARFD_WIDTH, YEARFD_HEIGHT));
        // フォントを設定
        year_field.setFont(FIELD_FONT);
        // 位置設定
        year_field.setBounds(YEARFD_X, YEARFD_Y, YEARFD_WIDTH, YEARFD_HEIGHT);
        // 右寄せで表示するようにする
        year_field.setHorizontalAlignment(JTextField.RIGHT);
        // 現在の年度をデフォルトでセットする
        Calendar calendar = Calendar.getInstance();
        year_field.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        // 検索ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon schb_icon = new ImageIcon("image/component/home/anikatsudb/presentation/updater/button/sch_button.png");
        sch_button.setIcon(schb_icon);
        sch_button.setContentAreaFilled(false);
        sch_button.setBorderPainted(false);
        sch_button.setFocusPainted(false);
        // ボタンの位置設定
        sch_button.setBounds(SCHBT_X, SCHBT_Y, SCHBT_WIDTH, SCHBT_HEIGHT);
        // ボタンを押した際の処理
        sch_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sch_button pressed");
                // 検索条件のデータを格納する配列を生成する
                String data[] = new String[2];
                // 入力情報をもとにして検索をし、検索結果表にデータを追加する
                if (getInputSchData(data)) { // 入力した情報に不備がないとき
                    // 入力情報をもとにして検索する
                    try {
                        // 検索結果のリストを生成
                        List<String[]> res_list = searchPstInfo(data);
                        // 検索結果を表に追加
                        updateBgtTable(res_list);
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak("研究発表情報を取得するのに失敗し", "報告");
                        System.out.println("検索に失敗しました。");
                        se.printStackTrace();
                    }
                    // テキストフィールドの更新
                    //updateOnNextTextFields();
                }
            }
        });

        // 検索結果の表のモデルを作成する
        pst_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 5:
                        return true;
                    default:
                        return false;
                }
            }
        };
        // 各列の列名をセットする
        String res_column_names[] = {"発表日", "部員番号", "ハンドルネーム", "タイトル", "URL", "削除ボタン"};
        pst_table_model.setColumnIdentifiers(res_column_names);

        // 上のモデルを使用した部費情報表を作成する
        pst_table = new JTable(pst_table_model);
        // 表のサイズをスクロールを使用する形にする
        pst_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        pst_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        pst_table.getTableHeader().setForeground(new Color(255, 255, 255));
        pst_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        pst_table.setFont(TABLE_FONT);
        // 行の高さを設定
        pst_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        pst_table.getTableHeader().setReorderingAllowed(false);
        // 削除ボタンの設定
        pst_table.getColumn("削除ボタン").setCellRenderer(new ButtonRenderer());
        pst_table.getColumn("削除ボタン").setCellEditor(new PstButtonEditor(new JTextField("x")));
        // 自動でソートできるようにする
        pst_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> res_sorter = new TableRowSorter<DefaultTableModel>(pst_table_model);
        res_sorter.setComparator(1, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        pst_table.setRowSorter(res_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(pst_table, res_column_names);
        // 発表日の列を中央寄せに、部員番号の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel res_column_model = pst_table.getColumnModel();
        res_column_model.getColumn(0).setCellRenderer(centerCellRenderer);
        res_column_model.getColumn(1).setCellRenderer(rightCellRenderer);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        pst_table_spanel = new JScrollPane(pst_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        pst_table_spanel.setPreferredSize(new Dimension(PST_AREA_WIDTH, PST_AREA_HEIGHT));
        // パネルの配置を設定
        pst_table_spanel.setBounds(PST_AREA_X, PST_AREA_Y, PST_AREA_WIDTH, PST_AREA_HEIGHT);

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/presentation/updater/button/bk_button.png");
        bk_button.setIcon(bkb_icon);
        bk_button.setContentAreaFilled(false);
        bk_button.setBorderPainted(false);
        bk_button.setFocusPainted(false);
        // ボタンの位置設定
        bk_button.setBounds(BKBT_X, BKBT_Y, BKBT_WIDTH, BKBT_HEIGHT);
        // ボタンを押した際の処理
        bk_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("bk_button pressed");
                // 選択画面に戻る
                bt_resp.pushClose();
            }
        });

        add(year_label);
        add(year_field);
        add(sch_button);
        add(pst_table_spanel);
        add(bk_button);
        add(upd_icon_label);
        add(frame_label);
    }


    private void updateDB() throws SQLException {
        /* 削除対象の日付と部員番号をもとにして削除したい情報をデータベースから削除する
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        // データベースの部員情報を変更する
        stmt.execute("DELETE FROM presentations WHERE presentation_date = '" + dlt_data[0]
                + "' AND member_id = " + dlt_data[1] + ";");

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private List<String[]> searchPstInfo(final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにして部員情報を検索し、検索結果を格納したリストを返す
        @param data[]: テキストフィールドに入力した検索条件
        @return res_list: 検索結果を格納したリスト
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        String start_date = data[0] + "-01-01";
        String end_date = data[0] + "-12-31";

        String sql = "SELECT presentations.*, h_name FROM presentations JOIN members ON id = member_id"
                + " WHERE presentation_date BETWEEN '" + start_date + "' AND '" + end_date
                + "' ORDER BY presentation_date ASC;";

        // 条件に沿って検索する
        ResultSet rs = stmt.executeQuery(sql);

        java.util.List<String[]> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
        // 検索結果を取得し、格納する
        while (rs.next()) {
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdFormat.format(rs.getDate("presentation_date"));

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    date, // 発表日
                    String.valueOf(rs.getInt("member_id")), // 部員番号
                    rs.getString("h_name"), // ハンドルネーム
                    rs.getString("title"), // タイトル
                    rs.getString("url"), // URL
                    "x" // 削除ボタン
            };
            // リストに追加する
            res_list.add(res_array);
        }

        // リソースを解放する
        stmt.close();
        // データベースを閉じる
        closeDB();

        return res_list;
    }


    private class DltResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // 削除対象の研究発表情報を削除する
                updateDB();
                // ログの追加
                bc_func.addLog("発表日: " + dlt_data[0] + " の、部員番号: " +dlt_data[1]
                        + " の研究発表情報を、データベースから削除しました。");
                // 削除した情報を表から削除する
                pst_table_model.removeRow(dlt_row_num);
                // キャラクターに報告させる
                bc_func.charaSpeak("研究発表情報の削除に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("研究発表情報の削除に失敗し", "報告");
                System.out.println("研究発表情報の削除に失敗しました。");
                e.printStackTrace();
            }

            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // 確認画面を削除
            remove(dlt_confirmer);
            repaint();
        }

        @Override
        public void pushNo() {
            // 部品をいじれる状態に戻す
            makeComponentsEnabled();
            // 確認画面を削除
            remove(dlt_confirmer);
            repaint();
        }

        @Override
        public void pushClose() { }
    }


    private void updateBgtTable(final List<String[]> list) {
        /* パラメータで受け取ったデータをもとにして検索結果の表を更新する
        @param data_list: 検索結果を格納しているリスト
         */
        // そのとき検索結果の表にセットされている行をすべて削除する
        pst_table_model.setRowCount(0);

        // 検索結果のデータを表に加える
        for (String data[] : list) {
            // 表にデータを追加する
            pst_table_model.addRow(data);
            // 表のサイズを調整
            resizeColumnsWidth(pst_table);
        }
    }


    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JButton jbutton = new JButton();
            jbutton.setText((value == null) ? "" : value.toString());

            return jbutton;
        }
    }


    private class PstButtonEditor extends DefaultCellEditor {

        public PstButtonEditor(JTextField txt_field) {
            super(txt_field);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            String label = (value == null) ? "" : value.toString();
            JButton button = new JButton();
            button.setText(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    System.out.println("yeah");
                    // 指定された行の年度を取り出す
                    dlt_data = getRowInfo(row);
                    // 削除対象の行番号を格納する
                    dlt_row_num = pst_table.convertRowIndexToModel(row);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    pst_table.setValueAt("x", row, 5);
                    // 本当に削除していいかの確認画面を生成する
                    String confirmation = "本当に、部員番号: " + dlt_data[1] + " , HN: " + dlt_data[2]
                            + " の " + dlt_data[0] + " の研究発表情報をデータベースから削除しますか？";
                    dlt_confirmer = new Confirmer(new DltResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
                    dlt_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
                    // 部品をいじれない状態にする
                    makeComponentsDisenabled();
                    // 確認画面を表示する
                    add(dlt_confirmer, 0);
                    repaint();
                }
            });
            return button;
        }
    }


    private boolean getInputSchData(final String data[]) {
        /* 検索の際の入力データを引数で受け取った配列に格納する
        @param data[]: 登録する部費情報を格納する配列
         */
        String year = year_field.getText().trim();
        if (year.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(year).find()) { // 数字出ないものが年の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("発表年には、数字を入力し", "命令2");
            return false;
        }
        else if (year.length() != 4) { // 年の桁数が4桁ではないとき
            // キャラクターに注意させる
            bc_func.charaSpeak("発表年には、4桁の数字を入力し", "命令2");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = year;

            return true;
        }
    }


    private String[] getRowInfo(final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param row: 情報を取り出す対象の行
        @return res_array: 指定された行の情報を格納した配列
         */
        int range = pst_table.getColumnCount() - 1;
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = pst_table.getValueAt(row, column).toString();
        }

        return res_array;
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        year_field.setEnabled(false);
        sch_button.setEnabled(false);
        pst_table.setEnabled(false);
        pst_table_spanel.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        year_field.setEnabled(true);
        sch_button.setEnabled(true);
        pst_table.setEnabled(true);
        pst_table_spanel.setEnabled(true);
        bk_button.setEnabled(true);
    }


    private void resizeColumnsWidth(final JTable jtable) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
         */
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getFont());

        int row = jtable.getRowCount() - 1; // 追加された行の行番号

        for (int column = 0; column < jtable.getColumnCount()-1; column++) { // -1しているのは5行目のボタンのところはリサイズの必要がないから
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(jtable.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

            if (col_width < value_width) { // 新しい文字列の幅の方が列の幅より大きいとき
                // 新しい文字列の幅に列の幅を合わせる
                column_model.getColumn(column).setPreferredWidth(value_width);
                column_model.getColumn(column).setWidth(value_width);
            }
        }
    }


    private void setFirstColumnsWidth(final JTable pst_table, final String[] col_names) {
        TableColumnModel column_model = pst_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(pst_table.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }
}
