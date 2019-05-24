package model;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
 
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
 
public class BoardDAO {
 
    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;
 
    // 데이터 베이스에 연결 메소드
    public void getCon() {
        try {
            Context initctx = new InitialContext();
            Context envctx = (Context) initctx.lookup("java:comp/env");
            // 타입이 데이터 소스이므로 데이터소스로 (타입변환해서) 받는다.
            DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
            // 얻은 데이터소스를 사용해 연결한다.
            con = ds.getConnection(); // 커넥션 연결 해주는 메소드
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // 전체 게시글의 갯수를 리턴하는 메소드
    public int getAllCount() {
        getCon();
        // 개시글의 갯수를 세야하기 때문에 카운트 변수를 추가하고 초기값을 선언한다.
        int count = 0;
 
        try {
            // sql 쿼리 준비함
            String sql = "select count(*) from board";
            pstmt = con.prepareStatement(sql);
            // ?표가 없으므로 바로 결과실행후 리턴시켜주면 된다.
            rs = pstmt.executeQuery();
            // 전체게시글은 한칸에서 밖에 출력이 되지 않으므로 1칸만 있으면 된다. 반복문 말고 if문으로 사용한다.
            if (rs.next()) { // 데이터가 있다면 카운트에 넣는다.
                // 전체 게시글 수
                count = rs.getInt(1);
            }
            con.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
 
    // 화면에 보여질 데이터를 10개씩 추출해서 리턴하는 메소드
    public Vector<BoardBean> getAllBoard(int startRow, int endRow) {
        // 리턴할객체 선언
        getCon();
        Vector<BoardBean> v = new Vector<>();
        try {
            // 쿼리 작성
            String sql = "select * from (select A.*, Rownum Rnum from (select * from board order by ref desc, re_step asc)A)"
                    + "where Rnum >= ? and Rnum <= ?";
            // 쿼리 실행할 객체를 선언
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
            // 쿼리실행후 결과 저장
            rs = pstmt.executeQuery();
            // 데이터 개수가 몇개인지 모르기에 반복문을 이용하여 데이터를 추출
            while (rs.next()) {
                // 데이터를 패키징 ( BoardBean 클래스를 이용) 해줌
                BoardBean bean = new BoardBean();
                bean.setNum(rs.getInt(1));
                bean.setWriter(rs.getString(2));
                bean.setEmail(rs.getString(3));
                bean.setSubject(rs.getString(4));
                bean.setPassword(rs.getString(5));
                bean.setReg_date(rs.getDate(6).toString());
                bean.setRef(rs.getInt(7));
                bean.setRe_step(rs.getInt(8));
                bean.setRe_level(rs.getInt(9));
                bean.setReadcount(rs.getInt(10));
                bean.setContent(rs.getString(11));
                // 패키징한 데이터를 벡터에 저장
                v.add(bean);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
 
}