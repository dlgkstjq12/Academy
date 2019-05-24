<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
    <center>
        <h2>전체 게시글 보기</h2>
        <table width="700" border="1" bordercolor="skyblue">
            <tr height="40">
                <td colspan="5" align="right">
                    <button onclick="location.href='BoardWriteForm.jsp'">글쓰기</button>
                </td>
            </tr>
 
            <tr height="40">
                <td width="50" align="center">번호</td>
                <td width="320" align="center">제목</td>
                <td width="100" align="center">작성자</td>
                <td width="150" align="center">작성일</td>
                <td width="80" align="center">조회수</td>
            </tr>
            <!-- $안에 number을 사용하면 계속 number만 출력되기 때문에 forEach문으로 number을 계속 1씩 감소시켜야 한다. -->
            <c:set var="number" value="${number}" />
            <c:forEach var="bean" items="${v}">
 
 
                <tr height="40">
                    <td width="50" align="left">${number }</td>
                    <td width="50" align="left">
                        <!-- 들여쓰기 구문 --> <!-- 첫번째 (1) 은 들여쓰기할 필요가 없다. --> <!-- 공백을 여러개 출력하기 위한 구문 -->
                        <c:if test="${bean.re_step > 1 }">
                            <c:forEach var="j" begin="1" end="${(bean.re_step-1)*5}">
                &nbsp;
            </c:forEach>
                        </c:if> <!-- 버튼을 눌렀을때 실행이 되게끔 a태그를 건다 --> <a
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
