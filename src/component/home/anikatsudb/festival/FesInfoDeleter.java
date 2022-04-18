package component.home.anikatsudb.festival;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import function.BasicFunction;
import function.button.ButtonResponse;
import function.confirmation.Confirmer;
import function.db.DBOperator;


public class FesInfoDeleter extends DBOperator {

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

    private JLabel fes_table_label; // 表の上に置く"過去の模擬店情報"というラベル
    private final int MBRTBLB_WIDTH = LB_FONT_SIZE * 11; // "過去の模擬店情報"というラベルの幅
    private final int MBRTBLB_HEIGHT = LB_FONT_SIZE; // "過去の模擬店情報"というラベルの高さ
    private final int MBRTBLB_X = FRAME_WIDTH/4 - MBRTBLB_WIDTH/2; // "過去の模擬店情報"というラベルの左上端のx座標
    private final int MBRTBLB_Y = 100; // "過去の模擬店情報"というラベルの左上端のy座標

    private DefaultTableModel fes_table_model;
    private JTable fes_table; // 過去の工大祭の模擬店情報一覧表
    private JScrollPane fes_table_spanel; // 上の表ににスクロールバーを付けたパネル
    private final int FES_AREA_X = 80; // スクロールパネルの左上端のx座標
    private final int FES_AREA_Y = 140; // スクロールパネルの左上端のy座標
    private final int FES_AREA_WIDTH = 800; // スクロールパネルの幅
    private final int FES_AREA_HEIGHT = 750; // スクロールパネルの高さ

    private int dlt_row_num; // 削除対象の行
    private String dlt_year; // 削除対象とする年度

    private Confirmer dlt_confirmer; // データベースの情報を削除するかの最終確認の画面
    private final Font CONF_FONT = new Font("MS Pゴシック", Font.BOLD, 50); // 確認の文のフォント

    private JButton bk_button; // 戻るボタン
    private final int BKBT_WIDTH = 90; // 戻るボタンの幅
    private final int BKBT_HEIGHT = 50; // 戻るボタンの高さ
    private final int BKBT_X = 780; // 戻るボタンの左上端のx座標
    private final int BKBT_Y = 910; // 戻るボタンの左上端のy座標

    private ButtonResponse bt_resp; // コンストラクタで受け取ったFesDBOprMgrでの処理を入れる
    private BasicFunction bc_func; // コンストラクタで受け取ったHomeでの処理を入れる


    public FesInfoDeleter(final ButtonResponse br, final BasicFunction bf) {
        super.setOpaque(false);
        // レイアウトの設定
        setLayout(null);

        // パラメータで受け取ったHomeでの処理を格納する
        bt_resp = br;
        bc_func = bf;

        // キャラクターに最初の説明を話させる
        bc_func.charaSpeak("削除したい年度の、工大祭の模擬店情報の行の削除ボタンを押し", "命令1");

        // 画面のフレームの画像のラベルの取得、設定
        frame = new ImageIcon("image/component/home/anikatsudb/frame/opr_frame.jpg");
        frame_label = new JLabel(frame);
        frame_label.setBounds(FM_X, FM_Y, FM_WIDTH, FM_HEIGHT);

        // Deleteのアイコンのラベルのインスタンスを取得、設定
        dlt_icon = new ImageIcon("image/component/home/anikatsudb/festival/deleter/icon/icon.png");
        dlt_icon_label = new JLabel(dlt_icon);
        dlt_icon_label.setBounds(ICON_X, ICON_Y, ICON_WIDTH, ICON_HEIGHT);

        // "過去の模擬店情報"のラベルを作る
        fes_table_label = new JLabel("《 過去の模擬店情報 》");
        // フォントを設定
        fes_table_label.setFont(LABEL_FONT);
        // 位置設定
        fes_table_label.setBounds(MBRTBLB_X, MBRTBLB_Y, MBRTBLB_WIDTH, MBRTBLB_HEIGHT);

        // 検索結果表のモデルを作成する
        fes_table_model = new DefaultTableModel() {
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
        String fes_column_names[] = {"年度", "会計の部員番号", "ハンドルネーム", "食品名", "売上金額(売上高)", "削除ボタン"};
        fes_table_model.setColumnIdentifiers(fes_column_names);

        // 上のモデルを使用した登録予定表を作成する
        fes_table = new JTable(fes_table_model);
        // 表のサイズをスクロールを使用する形にする
        fes_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // 列名のフォントの設定
        fes_table.getTableHeader().setFont(new Font("HGPゴシックM", Font.BOLD, 25));
        // 列名の色を設定
        fes_table.getTableHeader().setForeground(new Color(255, 255, 255));
        fes_table.getTableHeader().setBackground(new Color(255, 150, 180));
        // 表の文字のフォントを設定
        fes_table.setFont(TABLE_FONT);
        // 行の高さを設定
        fes_table.setRowHeight(ROW_HEIGHT);
        // 列を動かせないようする
        fes_table.getTableHeader().setReorderingAllowed(false);
        // 削除ボタンの設定
        fes_table.getColumn("削除ボタン").setCellRenderer(new ButtonRenderer());
        fes_table.getColumn("削除ボタン").setCellEditor(new FesButtonEditor(new JTextField("x")));
        // 自動でソートできるようにする
        fes_table.setAutoCreateRowSorter(true);
        // 数字（文字列）のソートの設定だけ行う
        TableRowSorter<DefaultTableModel> fes_sorter = new TableRowSorter<DefaultTableModel>(fes_table_model);
        fes_sorter.setComparator(0, new Comparator<String>() { // 年度の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        fes_sorter.setComparator(1, new Comparator<String>() { // 部員番号の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        fes_sorter.setComparator(4, new Comparator<String>() { // 売上金額の列
            public int compare(String a, String b) {
                return Integer.parseInt(a) - Integer.parseInt(b);
            }
        });
        fes_table.setRowSorter(fes_sorter);
        // 列のヘッダーのサイズに列の幅を合わせる
        setFirstColumnsWidth(fes_table, fes_column_names);

        // 過去の模擬店情報の表を表示するスクロールパネルのインスタンスを取得、設定
        fes_table_spanel = new JScrollPane(fes_table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        // サイズの設定
        fes_table_spanel.setPreferredSize(new Dimension(FES_AREA_WIDTH, FES_AREA_HEIGHT));
        // パネルの配置を設定
        fes_table_spanel.setBounds(FES_AREA_X, FES_AREA_Y, FES_AREA_WIDTH, FES_AREA_HEIGHT);

        // 在部している部員一覧を作成
        try {
            // 過去の模擬店情報をデータベースから取得
            List<String[]> data_list= getFesInfo();
            // 過去の模擬店一覧表を作成
            setTable(fes_table, fes_table_model ,data_list);
        } catch (SQLException se) {
            // キャラクターに報告させる
            bc_func.charaSpeak("過去の工大祭の模擬店情報を取得するのに失敗し", "報告");
//            System.out.println("過去の模擬店情報を取得するのに失敗しました。");
            se.printStackTrace();
        }

        // 年度と部員番号、売上金額の列を右寄せ表示にする
        DefaultTableCellRenderer rightCellRenderer = new DefaultTableCellRenderer();
        rightCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        TableColumnModel fes_column_model = fes_table.getColumnModel();
        fes_column_model.getColumn(0).setCellRenderer(rightCellRenderer);
        fes_column_model.getColumn(1).setCellRenderer(rightCellRenderer);
        fes_column_model.getColumn(3).setCellRenderer(rightCellRenderer);


        // 戻るボタンの生成
        bk_button = new JButton();
        // サイズの設定
        bk_button.setPreferredSize(new Dimension(BKBT_WIDTH, BKBT_HEIGHT));
        // ボタンのアイコンの設定
        ImageIcon bkb_icon = new ImageIcon("image/component/home/anikatsudb/member/updater/button/bk_button.png");
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

        add(fes_table_label);
        add(fes_table_spanel);
        add(bk_button);
        add(dlt_icon_label);
        add(frame_label);
    }


    private void updateDB() throws SQLException {
        /* 削除対象の年度をもとにして削除したい情報をデータベースから削除する
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();

        // データベースの部員情報を変更する
        stmt.execute("DELETE FROM festivals WHERE fes_year = " + dlt_year + ";");

        // リソースを解放する
        stmt.close();
        // SQL操作をデータベースに反映する
        con.commit();
        // データベースを閉じる
        closeDB();
    }


    private List<String[]> getFesInfo() throws SQLException {
        /* 現在、過去の模擬店の情報を取り出し、情報(削除ボタン付き)を格納したリストを返す
        @return res_list: 在部している部員情報を格納した
         */
        // データベースに接続する
        openDB();

        // SQL文の実行のためにStatementクラスのインスタンスを生成
        Statement stmt = con.createStatement();
        // 現在、在部している部員の部員情報を取得
        //        ResultSet rs = stmt.executeQuery("SELECT * FROM festivals");
        ResultSet rs = stmt.executeQuery("SELECT member_id, h_name, fes_year, food_name, revenue "
                + "FROM members JOIN festivals ON id = member_id ORDER BY fes_year ASC;");

        List<String[]> res_list = new ArrayList<>(); // 取得した模擬店情報を格納するためのリスト
        // 取得した部員情報をリストに格納する
        while (rs.next()) {
            // 検索結果として追加する情報の配列を生成
            String res_array[] = {
                    String.valueOf(rs.getInt("fes_year")), // 年度
                    String.valueOf(rs.getInt("member_id")), // 会計の部員番号
                    rs.getString("h_name"), // ハンドルネーム
                    rs.getString("food_name"), // 食品名
                    String.valueOf(rs.getInt("revenue")), // 売上金額
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


    private void setFirstColumnsWidth(final JTable jtable, final String[] col_names) {
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getTableHeader().getFont());

        for (int i = 0; i < col_names.length; i++) {
            int width = fm.stringWidth(col_names[i]) + 20;
            column_model.getColumn(i).setPreferredWidth(width);
            column_model.getColumn(i).setWidth(width);
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


    private class FesButtonEditor extends DefaultCellEditor {

        public FesButtonEditor(JTextField txt_field) {
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
                    dlt_year = fes_table.getValueAt(row, 0).toString();
                    // 削除対象の行数を格納する
                    dlt_row_num = fes_table.convertRowIndexToModel(row);
                    // ボタンを押した後にボタンが"false"という表示に代わるの防ぐ
                    fes_table.setValueAt("x", row, 4);
                    // 本当に削除していいかの確認画面を生成する
                    String confirmation = "本当に、" + dlt_year + "年度の工大祭の模擬店情報をデータベースから削除しますか？";
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


    private class DltResponse implements ButtonResponse {
        @Override
        public void pushYes() {
            try {
                // 削除対象の工大祭の模擬店情報を削除する
                updateDB();
                // ログの追加
                bc_func.addLog(dlt_year + "年度の工大祭の模擬店情報を、データベースから削除しました。");
                // 削除した情報を表から削除する
                fes_table_model.removeRow(dlt_row_num);
                // キャラクターに報告させる
                bc_func.charaSpeak("工大祭の模擬店情報削除に成功し", "報告");
            } catch (SQLException e) {
                // キャラクターに報告させる
                bc_func.charaSpeak("工大祭の模擬店情報削除に失敗し", "報告");
                //System.out.println("工大祭の模擬店情報削除に失敗しました。");
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


    private void resizeColumnsWidth(final JTable jtable) {
        /* 各列の幅を各列の要素の最大の幅に合わせる
        @param jtable: 幅調整をする表
         */
        TableColumnModel column_model = jtable.getColumnModel();

        // 文の幅を得るためのFontMetricsを取得
        FontMetrics fm = getFontMetrics(jtable.getFont());

        int row = jtable.getRowCount() - 1; // 追加された行の行番号

        for (int column = 0; column < jtable.getColumnCount()-1; column++) { // -1しているのはボタンのところはリサイズの必要がないから
            int col_width = column_model.getColumn(column).getWidth(); // 現在の列の幅
            int value_width = fm.stringWidth(jtable.getValueAt(row, column).toString()) + 20; // 新しく追加された文字列の横幅(+10は余白の分)

            if (col_width < value_width) { // 新しい文字列の幅の方が列の幅より大きいとき
                // 新しい文字列の幅に列の幅を合わせる
                column_model.getColumn(column).setPreferredWidth(value_width);
                column_model.getColumn(column).setWidth(value_width);
            }
        }
    }


    private void setTable(final JTable jtable, final DefaultTableModel table_model, final List<String[]> data_list) {
        /* パラメータで受け取ったデータをもとにして表を作成する
        @param jtable: 作成する表
        @param table_model: 作成する表のモデル
        @param data_list: 表のデータを格納しているリスト
         */
        if (data_list.isEmpty()) { // 表に追加するデータが見つからないとき

        }
        else { // 表に追加するデータがあるとき
            // それぞれのデータを表に追加する
            for (String data[]: data_list) {
                // 検索結果のうちの1行を追加する
                table_model.addRow(data);
                // 表のサイズを調整
                resizeColumnsWidth(jtable);
            }
        }
    }


    private void makeComponentsDisenabled() {
        /* 部品をいじれない状態にする
         */
        fes_table_spanel.setEnabled(false);
        fes_table.setEnabled(false);
        bk_button.setEnabled(false);
    }


    private void makeComponentsEnabled() {
        /* 部品をいじれる状態にする
         */
        fes_table_spanel.setEnabled(true);
        fes_table.setEnabled(true);
        bk_button.setEnabled(true);
    }
}
