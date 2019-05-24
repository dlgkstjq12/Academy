<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <center>
        <h2>��ü �Խñ� ����</h2>
        <table width="700" border="1" bordercolor="skyblue">
            <tr height="40">
                <td colspan="5" align="right">
                    <button onclick="location.href='BoardWriteForm.jsp'">�۾���</button>
                </td>
            </tr>
 
            <tr height="40">
                <td width="50" align="center">��ȣ</td>
                <td width="320" align="center">����</td>
                <td width="100" align="center">�ۼ���</td>
                <td width="150" align="center">�ۼ���</td>
                <td width="80" align="center">��ȸ��</td>
            </tr>
            <!-- $�ȿ� number�� ����ϸ� ��� number�� ��µǱ� ������ forEach������ number�� ��� 1�� ���ҽ��Ѿ� �Ѵ�. -->
            <c:set var="number" value="${number}" />
            <c:forEach var="bean" items="${v}">
 
 
                <tr height="40">
                    <td width="50" align="left">${number }</td>
                    <td width="50" align="left">
                        <!-- �鿩���� ���� --> <!-- ù��° (1) �� �鿩������ �ʿ䰡 ����. --> <!-- ������ ������ ����ϱ� ���� ���� -->
                        <c:if test="${bean.re_step > 1 }">
                            <c:forEach var="j" begin="1" end="${(bean.re_step-1)*5}">
                &nbsp;
            </c:forEach>
                        </c:if> <!-- ��ư�� �������� ������ �ǰԲ� a�±׸� �Ǵ� --> <a
                        href="BoardInfoControl.do?num=${bean.num }">${bean.subject } </a>
 
                    </td>
                    <td width="50" align="left">${bean.writer }</td>
                    <td width="50" align="left">${bean.reg_date }</td>
                    <td width="50" align="left">${bean.readcount }</td>
                </tr>
                <c:set var="number" value="${number-1 }" />
            </c:forEach>
        </table>
 
    </center>
</body>
</html>
