package HelloJava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Hellomysql {

	  public static void main(String[] args) {
	    // データベース接続情報
	    String url      = "jdbc:mysql://localhost:3306/sampledb?useSSL=false";
	    String user     = "ourat";
	    String password = "kawachi1415";

	    try(Connection conn = DriverManager.getConnection(url, user, password);
	      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employees WHERE code = ?");) {

	      // コマンドライン引数の不備があった場合
	      if(args.length != 1) {
	        throw new ArrayIndexOutOfBoundsException();
	      }
	      // コマンドライン引数を取得
	      String input_code = args[0];

	      // コマンドライン引数をプレースホルダにセット
	      pstmt.setString(1, input_code);
	      // SELECT文の実行
	      ResultSet res = pstmt.executeQuery();
	      String result = "";  // 結果格納用
	      String message = ""; // メッセージ用変数
	      // ResultSetが空行かどうか判定用のカウンター変数
	      int resCount = 0;
	      // SELECT文が成立した場合
	      while(res.next()) {
	        result +=
	            "\n ==> "      +
	            "code: "    + res.getString("code") + " " +
	            "name: "    + res.getString("name") + " " +
	            "age: "     + res.getInt("age")  + " " +
	            "section: " + res.getString("section")
	        ;
	        resCount++;
	      }
	      // SELECT文が成立しなくてもエラーにはならないので、ResultSetが空かどうかで判定
	      if(resCount == 0) {
	        message = "次のレコードを抽出できませんでした。";
	      } else {
	        message = "次のレコードを抽出しました。";
	      }
	      System.out.println(message + "  CODE: " + input_code + result);
	    } catch(ArrayIndexOutOfBoundsException e) {
	      System.out.println("引数に従業員コードを指定してください。");
	    } catch(Exception e) {
	      // 他のエラー
	    }
	  }
}
