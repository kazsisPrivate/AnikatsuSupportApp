package function.button;

public interface ButtonResponse {
    /* クラスに各ボタンを押したときの応答として何をするかを
    渡すためのインターフェース
     */
    void pushYes(); // はいボタンを押したときのメソッド
    void pushNo(); // いいえボタンを押したときのメソッド
    void pushClose(); // 閉じるボタンを押したときのメソッド
}
