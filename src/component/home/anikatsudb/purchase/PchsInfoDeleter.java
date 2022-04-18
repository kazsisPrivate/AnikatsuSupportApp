package component.home.anikatsudb.purchase;

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


public class PchsInfoDeleter extends DBOperator {

    private final int FRAME_WIDTH = 1920; // ウインドウの横幅
    private final int FRAME_HEIGHT = 1080; // ウインドウの縦幅

    private ImageIcon frame; // フレームの画像
    private JLabel frame_label; // フレームの画像のラベル
    private final int FM_WIDTH = 860; // フレームの画像の幅
    private final int FM_HEIGHT = 980; // フレームの画像の高さ
    private final int FM_X = 50; // フレームの左上端のx座標
    private final int FM_Y = 30; // フレームの左上端のy座標

    private JLabel dlt_icon_label; // Deleteのアイコンの画像のラベル
    private ImageIcon dlt_icon; // Deleteのアイコンの画像
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
    private JLabel year_label; // 入力フィールドの右に置く"年"というラベル
    private final int YEARLB_WIDTH = LB_FONT_SIZE * 1; // "年"というラベルの幅
    private final int YEARLB_HEIGHT = LB_FONT_SIZE; // "年"というラベルの高さ
    private final int YEARLB_X = YEARFD_X + YEARFD_WIDTH + LB_FONT_SIZE/3; // "年"というラベルの左上端のx座標
    private final int YEARLB_Y = YEARFD_Y - LB_FONT_SIZE/10; // "年"というラベルの左上端のy座標

    private JTextField month_field; // 月を表示するテキストフィールド
    private final int MONTHFD_WIDTH = LB_FONT_SIZE * 2; // 月を表示するテキストフィールドの幅
    private final int MONTHFD_HEIGHT = LB_FONT_SIZE; // 月を表示するテキストフィールドの高さ
    private final int MONTHFD_X = 420; // 月を表示するテキストフィールドの左上端のx座標
    private final int MONTHFD_Y = 90; // 月を表示するテキストフィールドの左上端のy座標
    private JLabel month_label; // 入力フィールドの右に置く"月の購入品情報"ラベル
    private final int MONTHLB_WIDTH = LB_FONT_SIZE * 7; // "月の購入品情報"というラベルの幅
    private final int MONTHLB_HEIGHT = LB_FONT_SIZE; // "月の購入品情報"というラベルの高さ
    private final int MONTHLB_X = MONTHFD_X + MONTHFD_WIDTH + LB_FONT_SIZE/3; // "月の購入品情報"というラベルの左上端のx座標
    private final int MONTHLB_Y = MONTHFD_Y - LB_FONT_SIZE/10; // "月の購入品情報"というラベルの左上端のy座標

    private JButton sch_button; // 検索ボタン
    private final int SCHBT_WIDTH = 90; // 検索ボタンの幅
    private final int SCHBT_HEIGHT = 50; // 検索ボタンの高さ
    private final int SCHBT_X = 730; // 検索ボタンの左上端のx座標
    private final int SCHBT_Y = 80; // 検索ボタンの左上端のy座標

    private DefaultTableModel pchs_table_model;
    private JTable pchs_table; // 購入品情報一覧表
    private JScrollPane pchs_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int PCHS_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int PCHS_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int PCHS_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int PCHS_AREA_HEIGHT = 750; // スクロールパネルの高さ

    private int dlt_row_num; // 削除対象の行
    private String dlt_data[]; // 削除対象の行の情報

    private Confirmer dlt_confirmer; // データベースの情報を変更するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったPchsDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public PchsInfoDeleter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("削除したい購入品情報の行の削除ボタンを押し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Deleteのアイコンのラベルのインスタンスを取得、設定
        dlt_icon = new ImageIcon("image/component/home/anikatsudb/purchase/deleter/icon/icon.png");
        dlt_icon_label = new JLabel(dlt_icon);
        dlt_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // "年"のラベルを作る
        year_label = new JLabel("年");
        // フォントを設定
        year_label.setFont(LABEL_FONT);
        // 位置設定
        year_label.setBounds(YEARLB_X, YEARLB_Y, YEARLB_WIDTH, YEARLB_HEIGHT);
        // 入学年度を入力するテキストフィールドを作成する
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

        // "月"のラベルを作る
        month_label = new JLabel("月の購入品情報");
        // フォントを設定
        month_label.setFont(LABEL_FONT);
        // 位置設定
        month_label.setBounds(MONTHLB_X, MONTHLB_Y, MONTHLB_WIDTH, MONTHLB_HEIGHT);
        // 月を入力するテキストフィールドを作成する
        month_field = new JTextField();
        // サイズを設定する
        month_field.setPreferredSize(new Dimension(MONTHFD_WIDTH, MONTHFD_HEIGHT));
        // フォントを設定
        month_field.setFont(FIELD_FONT);
        // 位置設定
        month_field.setBounds(MONTHFD_X, MONTHFD_Y, MONTHFD_WIDTH, MONTHFD_HEIGHT);
        // 右寄せで表示するようにする
        month_field.setHorizontalAlignment(JTextField.RIGHT);
        // 現在の月をデフォルトでセットする
        month_field.setText(String.valueOf(calendar.get(Calendar.MONTH)+1));

        // 検索ボタンの生成
        sch_button = new JButton();
        // サイズの設定
        sch_button.setPreferredSize(new Dimension(SCHBT_WIDTH, SCHBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon schb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/deleter/button/sch_button.png");
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
                        List<String[]> res_list = searchPchsInfo(data);
                        // 検索結果を表に追加
                        setPchsTable(res_list);
                    } catch (SQLException se) {
                        // キャラクターに報告させる
                        bc_func.charaSpeak("購入品情報を取得するのに失敗し", "報告");
                        System.out.println("検索に失敗しました。");
                        se.printStackTrace();
                    }
                }
            }
        });

        // 登録予定表のモデルを作成する
        pchs_table_model = new DefaultTableModel() {
            // ボタンの行だけ反応するようにする
            @Override
            public boolean isCellEditable(int row, int column) {
                switch (column) {
                    case 8:
                        return true;
                    default:
                        return false;
                }
            }
        };
        // 各列の列名をセットする
        String pchs_column_names[] = {"購入品番号", "購入日", "購入品名", "種類", "部員番号", "ハンドルネーム", "金額", "模擬店", "削除ボタン"};
        pchs_table_model.setColumnIdentifiers(pchs_column_names);

        // 上のモデルを使用した登録予定表を作成する
        pchs_table = new JTable(pchs_table_model);
        // 表のサイズをスクロールを使用する形にする
        pchs_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        pchs_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        pchs_table.getTableHeader().setForeground(new Color(255, 255, 255));
        pchs_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        pchs_table.setFont(TABLE_FONT);
        // 行の高さを設定
        pchs_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        pchs_table.getTableHeader().setReorderingAllowed(false);
        // 削除ボタンの設定
        pchs_table.getColumn("削除ボタン").setCellRenderer(new ButtonRenderer());
        pchs_table.getColumn("削除ボタン").setCellEditor(new PchsButtonEditor(new JTextField("x")));
        // 自動でソートできるようにする
        pchs_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> pchs_sorter = new TableRowSorter<DefaultTableModel>(pchs_table_model);
        pchs_sorter.setComparator(0, new Comparator<String>() { // 購入品番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        pchs_sorter.setComparator(4, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        pchs_sorter.setComparator(6, new Comparator<String>() { // 金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        pchs_table.setRowSorter(pchs_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(pchs_table, pchs_column_names);
        // 購入日の列を中央寄せにし、購入品番号と部員番号、金額の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel column_model = pchs_table.getColumnModel();
        column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        column_model.getColumn(1).setCellRenderer(centerCellRenderer);
        column_model.getColumn(4).setCellRenderer(rightCellRenderer);
        column_model.getColumn(6).setCellRenderer(rightCellRenderer);

        // 検索結果の表を表示するスクロールパネルのインスタンスを取得、設定
        pchs_table_spanel = new JScrollPane(pchs_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        pchs_table_spanel.setPreferredSize(new Dimension(PCHS_AREA_WIDTH, PCHS_AREA_HEIGHT));
        // パネルの配置を設定
        pchs_table_spanel.setBounds(PCHS_AREA_X, PCHS_AREA_Y, PCHS_AREA_WIDTH, PCHS_AREA_HEIGHT);

        // 確認の画面のインスタンスを生成
        String confirmation = "本当に、下の表に追加した部費情報を、データベースから削除しますか？";
        dlt_confirmer = new Confirmer(new DltResponse(), confirmation, CONF_FONT, Confirmer.YES_NO);
        // 位置設定
        dlt_confirmer.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/purchase/deleter/button/bk_button.png");
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
        add(month_label);
        add(month_field);
        add(sch_button);
        add(pchs_table_spanel);
        add(bk_button);
        add(dlt_icon_label);
        add(frame_label);
    }


    private void updateDB() throws SQLException {
        /* 削除対象の購入品番号をもとにして削除したい情報をデータベースから削除する
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        // データベースの部員情報を変更する
        stmt.execute("DELETE FROM purchases WHERE id = " + dlt_data[0] + ";");

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private List<String[]> searchPchsInfo(final String data[]) throws SQLException {
        /* パラメータで受け取ったデータをもとにして部員情報を検索し、検索結果を格納したリストを返す
        @param data[]: テキストフィールドに入力した検索条件
        @return res_list: 検索結果を格納したリスト
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        String start_date = data[0] + "-" + data[1] + "-01";
        String end_date = data[0] + "-" + data[1] + "-31";

        String sql = "SELECT purchases.*, h_name FROM purchases JOIN members"
                + " ON members.id = purchases.member_id"
                + " WHERE purchase_date BETWEEN '" + start_date + "' AND '" + end_date + "';";

        // 条件に沿って検索する
        ResultSet rs = stmt.executeQuery(sql);

        java.util.List<String[]> res_list = new ArrayList<>(); // 検索結果を格納するためのリスト
        // 検索結果を取得し、格納する
        while (rs.next()) {
            // 購入日の表示処理
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdFormat.format(rs.getDate("purchase_date"));

            String is_for_fes; // 工大祭の模擬店のためのものかどうかのデータを格納するための変数
            if (rs.getBoolean("is_for_festival")) { // 模擬店のためのものであるとき
                is_for_fes = "はい";
            }
            else { // 模擬店のためのものでないとき
                is_for_fes = "いいえ";
            }

            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("id")), // 購入品番号
                    date, // 購入日
                    rs.getString("name"), // 商品名
                    rs.getString("type"), // 購入品の種類
                    String.valueOf(rs.getInt("member_id")), // 部員番号
                    rs.getString("h_name"), // ハンドルネーム
                    rs.getString("price"), // 金額
                    is_for_fes, // 工大祭の模擬店のためのものかどうか
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


    private void setPchsTable(final List<String[]> list) {
        /* パラメータで受け取ったデータをもとにして検索結果の表を更新する
        @param data_list: 検索結果を格納しているリスト
         */
        // そのとき検索結果の表にセットされている行をすべて削除する
        pchs_table_model.setRowCount(0);

        // 検索結果のデータを表に加える
        for (String data[] : list) {
            // 表にデータを追加する
            pchs_table_model.addRow(data);
            // 表のサイズを調整
            resizeColumnsWidth(pchs_table);
        }
    }

    private class DltResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // 選択したデータを削除する
                updateDB();
                // ログの追加
                bc_func.addLog("購入品番号: " + dlt_data[0]
                        + " の購入品情報を、データベースから削除しました。");
                // 削除した情報を表から削除する
                pchs_table_model.removeRow(dlt_row_num);
                // キャラクターに報告させる
                bc_func.charaSpeak("購入品情報の削除に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("購入品情報の削除に失敗し", "報告");
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


    private class ButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            JButton jbutton = new JButton();
            jbutton.setText((value == null) ? "" : value.toString());

            return jbutton;
        }
    }


    private class PchsButtonEditor extends DefaultCellEditor {

        public PchsButtonEditor(JTextField txt_field) {
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
                    // 指定された行の情報を取り出す
                    dlt_data = getRowInfo(row);
                    // 削除対象の行番号を格納する
                    dlt_row_num = pchs_table.convertRowIndexToModel(row);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    pchs_table.setValueAt("x", row, 8);
                    // 本当に削除していいかの確認画面を生成する
                    String confirmation = "本当に、購入品番号: " + dlt_data[1] + " , 購入品名: " + dlt_data[2]
                            + " の購入品情報をデータベースから削除しますか？";
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
        String month = month_field.getText().trim();
        if (year.isEmpty() || month.isEmpty()) { // 入力し忘れている情報があるとき
            // キャラクターに注意させる
            bc_func.charaSpeak("入力していない枠を入力し", "命令2");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(year).find()) { // 数字出ないものが年の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("年には、数字を入力し", "命令2");
            System.out.println("年が数字じゃなくね");
            return false;
        }
        else if (year.length() != 4) { // 年の桁数が4桁ではないとき
            // キャラクターに注意させる
            bc_func.charaSpeak("年には、4桁の数字を入力し", "命令2");
            System.out.println("年間違い");
            return false;
        }
        else if (!Pattern.compile("^-?[0-9]+$").matcher(month).find()) { // 数字出ないものが年の欄に含まれるとき。正規表現を使って入学年度が数字であるかを確認している
            // キャラクターに注意させる
            bc_func.charaSpeak("月には、数字を入力し", "命令2");
            System.out.println("月が数字じゃなくね");
            return false;
        }
        else if (month.length() > 2) { // 年度の桁数が3桁以上のとき
            // キャラクターに注意させる
            bc_func.charaSpeak("月には、2桁以下の数字を入力し", "命令2");
            System.out.println("月間違い");
            return false;
        }
        else { // 入力し忘れている情報がないとき
            data[0] = year;
            data[1] = month;

            return true;
        }
    }


    private String[] getRowInfo(final JTable jtable, final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param jtable: 情報を取り出す対象のテーブル
        @param row: 情報を取り出す対象の行
        @return bgtarray: 指定された行の情報を格納した配列
         */
        int range = jtable.getColumnCount();
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = jtable.getValueAt(row, column).toString();
        }

        return res_array;
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        year_field.setEnabled(false);
        month_field.setEnabled(false);
        sch_button.setEnabled(false);
        pchs_table.setEnabled(false);
        pchs_table_spanel.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        year_field.setEnabled(true);
        month_field.setEnabled(true);
        sch_button.setEnabled(true);
        pchs_table.setEnabled(true);
        pchs_table_spanel.setEnabled(true);
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


    private void setFirstColumnsWidth(final JTable bgt_table, final String[] col_names) {
        TableColumnModel column_model = bgt_table.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(bgt_table.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
        }
    }


    private String[] getRowInfo(final int row) {
        /* 指定された表の指定された行の情報を取得し、配列で返す
        @param row: 情報を取り出す対象の行
        @return res_array: 指定された行の情報を格納した配列
         */
        int range = pchs_table.getColumnCount() - 1;
        String res_array[] = new String[range]; // 返す配列
        for (int column = 0; column < range; column++) { // ボタンの行以外の情報を格納する
            res_array[column] = pchs_table.getValueAt(row, column).toString();
        }

        return res_array;
    }
}
