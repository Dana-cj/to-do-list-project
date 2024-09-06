<%@include file="../shared/header.jsp"%>
<%@ include file="../shared/nav.jsp" %>
<div class="container fluid mt-5">

  <form >
      <div class="form-group mt-5">
         <label for="project"><strong>Project:</strong></label>
         <input type="text" class="form-control" id="project" name="project" value="<c:out value= "${requestScope.task.project}"/>" disabled>
      </div>

      <div class="form-group mt-5">
        <label for="description"><strong>Description:</strong></label>
        <input type="text" class="form-control" id="description" name="description" value="<c:out value= "${requestScope.task.description}"/>" disabled>
      </div>

      <div class="form-group mt-5">
        <label for="dueDate"><strong>Due date:</strong></label>
        <input type="date" id="dueDate" name="dueDate" value= "<c:out value= "${requestScope.task.localDueDate}"/>" disabled>
      </div>


    <label class="my-1 mr-2 form-group mt-5" for="inlineFormCustomSelectPref"><strong>Priority:</strong></label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref" name="priority" disabled>
        <option selected><c:out value= "${requestScope.task.priority}"/></option>
      </select>

    <label class="my-1 mr-2 form-group mt-5" for="inlineFormCustomSelectPref2"><strong>Status:</strong></label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref2" name="status" disabled>
        <option selected><c:out value= "${requestScope.task.status}"/></option>
      </select>

    <div class="form-group mt-5">
        <p><strong>Email addresses:</strong></p>
           <c:forEach items="${requestScope.task.contacts}" var="contact">
                <div class="form-group mt-1">
                   <input type="email" class="form-control" id="email" name="email" value="<c:out value= "${contact.emailAddress}"/>" disabled>
                   <a href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=deleteContact&taskId=<c:out value= "${requestScope.task.id}"/>&contactId=<c:out value = "${contact.id}"/>">
                        Delete email address<i class="bi bi-trash"></i>
                   </a>
                </div>
           </c:forEach>
    </div>

    <div class="form-group mt-5">
        <p><strong>Files attached:</strong></p>
             <c:forEach items="${requestScope.task.files}" var="file">
                        <a href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=download&fileName=<c:out value = "${file.name}"/>">
                            <i class="bi bi-file-earmark"></i><c:out value = "${file.name}"/>
                        </a>
                        <a href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=deleteFile&taskId=<c:out value= "${requestScope.task.id}"/>&fileId=<c:out value = "${file.id}"/>">
                            -   Delete file<i class="bi bi-trash"></i>
                        </a>
             </c:forEach>
     </div>
    <div class="form-group mt-5">
         <a href="<c:out value="${pageContext.request.contextPath}"/>/tasks">
            <button class="btn btn-primary mt-5">Finish update</button>
         </a>
    </div>
  </form>
</div>

 <%@include file="../shared/footer.jsp"%>