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

    <ul class="nav nav-underline justify-content-end">
      <li class="nav-item">
        <a class="nav-link <c:out value="${requestScope.notificationsActive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=sendEmails">
        <i class="bi bi-gear"></i> Enable  reminders
        </a>
      </li>
    </ul>

    <ul class="nav nav-underline justify-content-end">
          <li class="nav-item">
            <a class="nav-link <c:out value="${requestScope.notificationsInactive}"/>" href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=doNotSendEmails">
            <i class="bi bi-gear"></i> Disable reminders
            </a>
          </li>
    </ul>
</div>