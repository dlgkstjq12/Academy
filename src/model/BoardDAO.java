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
 
    // ������ ���̽��� ���� �޼ҵ�
    public void getCon() {
        try {
            Context initctx = new InitialContext();
            Context envctx = (Context) initctx.lookup("java:comp/env");
            // Ÿ���� ������ �ҽ��̹Ƿ� �����ͼҽ��� (Ÿ�Ժ�ȯ�ؼ�) �޴´�.
            DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
            // ���� �����ͼҽ��� ����� �����Ѵ�.
            con = ds.getConnection(); // Ŀ�ؼ� ���� ���ִ� �޼ҵ�
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    // ��ü �Խñ��� ������ �����ϴ� �޼ҵ�
    public int getAllCount() {
        getCon();
        // ���ñ��� ������ �����ϱ� ������ ī��Ʈ ������ �߰��ϰ� �ʱⰪ�� �����Ѵ�.
        int count = 0;
 
        try {
            // sql ���� �غ���
            String sql = "select count(*) from board";
            pstmt = con.prepareStatement(sql);
            // ?ǥ�� �����Ƿ� �ٷ� ��������� ���Ͻ����ָ� �ȴ�.
            rs = pstmt.executeQuery();
            // ��ü�Խñ��� ��ĭ���� �ۿ� ����� ���� �����Ƿ� 1ĭ�� ������ �ȴ�. �ݺ��� ���� if������ ����Ѵ�.
            if (rs.next()) { // �����Ͱ� �ִٸ� ī��Ʈ�� �ִ´�.
                // ��ü �Խñ� ��
                count = rs.getInt(1);
            }
            con.close();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
 
    // ȭ�鿡 ������ �����͸� 10���� �����ؼ� �����ϴ� �޼ҵ�
    public Vector<BoardBean> getAllBoard(int startRow, int endRow) {
        // �����Ұ�ü ����
        getCon();
        Vector<BoardBean> v = new Vector<>();
        try {
            // ���� �ۼ�
            String sql = "select * from (select A.*, Rownum Rnum from (select * from board order by ref desc, re_step asc)A)"
                    + "where Rnum >= ? and Rnum <= ?";
            // ���� ������ ��ü�� ����
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
            // ���������� ��� ����
            rs = pstmt.executeQuery();
            // ������ ������ ����� �𸣱⿡ �ݺ����� �̿��Ͽ� �����͸� ����
            while (rs.next()) {
                // �����͸� ��Ű¡ ( BoardBean Ŭ������ �̿�) ����
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
                // ��Ű¡�� �����͸� ���Ϳ� ����
                v.add(bean);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
 
}