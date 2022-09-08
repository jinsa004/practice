<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
<br/>
	<div class="d-flex justify-content-end">
		<div style="width: 300px">
			<form class="d-flex" method="get" action="/">
				<input class="form-control me-2" type="text" placeholder="Search" name="keyword">
				<button class="btn btn-primary" type="button">Search</button>
			</form>
		</div>
	</div>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>번호</th>
				<th>게시글제목</th>
				<th>작성자이름</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="boards" items="${boardsList}">
				<tr>
					<td>${boards.id}</td>
					<td><a href="/boards/${boards.id}">${boards.title}</a></td>
					<td>${boards.username}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="d-flex justify-content-center">
		<ul class="pagination">
			<li class='page-item ${paging.first ? "disabled":"" }'><a class="page-link"
				href="?page=${paging.currentPage-1}">Previous</a></li>
			<c:forEach var="pageNum" begin="${paging.startPageNum }" end="${paging.lastPageNum}">
				<li class='page-item ${paging.currentPage == pageNum-1 ? "active" : "" }'><a
					class="page-link" href="?page=${pageNum-1}">${pageNum }</a></li>
			</c:forEach>
			<li class='page-item ${paging.last ? "disabled":"" }'><a class="page-link"
				href="?page=${paging.currentPage+1}">Next</a></li>
		</ul>
	</div>

	<%-- <div style="background-color: 'grey';">
		<h3>currentBlock : ${paging.currentBlock }</h3>
		<h3>blockCount : 5</h3>
		<h3>startPageNum : ${paging.startPageNum }</h3>
		<h3>lastPageNum : ${paging.lastPageNum }</h3>
		<h3>totalCount : ${paging.totalCount}</h3>
		<h3>totalPage : ${paging.totalPage}</h3>
		<h3>currentPage : ${paging.currentPage}</h3>
		<h3>isLast : ${paging.last}</h3>
		<h3>isFirst : ${paging.first}</h3>
	</div> --%>
</div>

<%@ include file="../layout/footer.jsp"%>

