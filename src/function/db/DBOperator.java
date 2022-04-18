package function.db;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBOperator extends JPanel {

    private final String URL = "";  // データベースのURL
    private final String USERNAME = ""; // mariadbにログインする際のユーザー名
    private final String PASSWORD = ""; // mariadbに上のユーザー名でログインする際のパスワード

    protected Connection con; // データベースとの接続のためのインスタンス


    protected void openDB() throws SQLException {
        // データベースに接続する
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        // 自動コミットモードを解除する
        con.setAutoCommit(false);
    }


    protected void closeDB() throws SQLException {
        // データベースを閉じる
        con.close();
    }
}
