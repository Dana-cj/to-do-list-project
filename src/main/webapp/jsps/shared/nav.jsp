<div class="container fluid mt-1">
    <nav class="nav nav-pills flex-column flex-sm-row">
        <a class="flex-sm-fill text-sm-center nav-link <c:out value="${requestScope.tasksActive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/tasks">
        Tasks
        </a>

        <a class="flex-sm-fill text-sm-center nav-link <c:out value="${requestScope.historyActive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/history">
        History
        </a>


         <div class="nav justify-content-end">
                        <a href="<c:out value="${pageContext.request.contextPath}"/>/logout">
                            <button type="button" class="btn btn-outline-primary">Logout</button>
                        </a>
         </div>
    </nav>

</div>