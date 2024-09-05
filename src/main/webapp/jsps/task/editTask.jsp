<%@include file="../shared/header.jsp"%>
<%@ include file="../shared/nav.jsp" %>
<div class="container fluid mt-5">

  <form method="post" action="tasks?action=editTask&taskId=<c:out value = "${requestScope.task.id}"/>" enctype="multipart/form-data">
      <div class="form-group mt-5">
         <label for="project">Project: </label>
         <input type="text" class="form-control" id="project" name="project" value="<c:out value= "${requestScope.task.project}"/>">
      </div>

      <div class="form-group mt-5">
        <label for="description">Description: </label>
        <input type="text" class="form-control" id="description" name="description" value="<c:out value= "${requestScope.task.description}"/>">
      </div>

      <div class="form-group mt-5">
        <label for="dueDate">Due date: </label>
        <input type="date" id="dueDate" name="dueDate" value= "<c:out value= "${requestScope.task.localDueDate}"/>">
      </div>


    <label class="my-1 mr-2 form-group mt-5" for="inlineFormCustomSelectPref">Priority: </label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref" name="priority">
        <option selected><c:out value= "${requestScope.task.priority}"/></option>
        <option value="HIGH">HIGH</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="LOW">LOW</option>
      </select>

    <label class="my-1 mr-2 form-group mt-5" for="inlineFormCustomSelectPref2">Status: </label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref2" name="status">
        <option selected><c:out value= "${requestScope.task.status}"/></option>
        <option value="TO_DO">TO DO</option>
        <option value="IN_PROGRESS">IN PROGRESS</option>
        <option value="DONE">DONE</option>
      </select>

    <h6 class="mt-5">Email addresses:</h6>
       <c:forEach items="${requestScope.task.contacts}" var="contact">
            <div class="form-group mt-5">
               <input type="email" class="form-control" id="email" name="email" value="<c:out value= "${contact.emailAddress}"/>">
               <a href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=deleteContact&taskId=<c:out value= "${requestScope.task.id}"/>&contactId=<c:out value = "${contact.id}"/>">
                    Delete email address<i class="bi bi-trash"></i>
               </a>
            </div>
       </c:forEach>

    <h6 class="mt-5">Add email addresses of persons involved:</h6>
    <div class="form-group mt-5">
          <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com">
          <p class="help-block">*******You can enter email addresses where reminders will be sent ******</p>
     </div>

     <h6 class="mt-5">Files attached:</h6>

     <c:forEach items="${requestScope.task.files}" var="file">

                <a href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=download&fileName=<c:out value = "${file.name}"/>">
                    <i class="bi bi-file-earmark"></i><c:out value = "${file.name}"/>
                </a>

                <a href="<c:out value="${pageContext.request.contextPath}"/>/tasks?action=deleteFile&taskId=<c:out value= "${requestScope.task.id}"/>&fileId=<c:out value = "${file.id}"/>">
                    -   Delete file<i class="bi bi-trash"></i>
                </a>

     </c:forEach>

     <h6 class="mt-5">Add files:</h6>
     <div class="form-group mt-5">
            <label for="files">File input</label>
            <input type="file" id="files" name="files" />
            <input type="file" name="files" />
            <input type="file" name="files" />
            <p class="help-block">*******You can save here new files that help you solve the task******</p>
      </div>
      <button type="submit" class="btn btn-primary mt-5">Update task</button>
    </form>
</div>

 <%@include file="../shared/footer.jsp"%>