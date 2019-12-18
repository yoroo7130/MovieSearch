package searchProgram;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {

	public static Connection getCon() { // Java.sql
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 8.0 connect version url setting
			String url = "jdbc:mysql://localhost:3306/yuhandb2_3?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			String user = "root";
			String pwd = "1429";
			con = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	} 
	


	public static void main(String[] args) {
		Connection con = getCon();
		System.out.println("정상적으로 DB 연결이 되었습니다.");
		
		String sql = "SELECT r.review_number, m.movie_name, u.id, comment, grade " + 
					 "FROM review r, movie m, member u " + 
					 "WHERE r.movie_number=m.movie_number and r.id=u.id;";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			System.out.println("book DB Info");
			while(rs.next()) {	// rs.next() == false 이 될 때 까지 반복
				System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|"
						+ rs.getString(4) + "|" + rs.getInt(5));
			}
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
