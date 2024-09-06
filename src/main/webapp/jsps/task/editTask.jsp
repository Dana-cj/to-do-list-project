<%@include file="../shared/header.jsp"%>
<%@ include file="../shared/nav.jsp" %>
<div class="container fluid mt-5">

  <form method="post" action="tasks?action=editTask&taskId=<c:out value = "${requestScope.task.id}"/>" enctype="multipart/form-data">
      <div class="form-group mt-5">
         <label for="project"><strong>Project:</strong></label>
         <input type="text" class="form-control" id="project" name="project" value="<c:out value= "${requestScope.task.project}"/>">
      </div>

      <div class="form-group mt-5">
        <label for="description"><strong>Description:</strong></label>
        <input type="text" class="form-control" id="description" name="description" value="<c:out value= "${requestScope.task.description}"/>">
      </div>

      <div class="form-group mt-5">
        <label for="dueDate"><strong>Due date:</strong></label>
        <input type="date" id="dueDate" name="dueDate" value= "<c:out value= "${requestScope.task.localDueDate}"/>">
      </div>


    <label class="my-1 mr-2 form-group mt-5" for="inlineFormCustomSelectPref"><strong>Priority:</strong></label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref" name="priority">
        <option selected><c:out value= "${requestScope.task.priority}"/></option>
        <option value="HIGH">HIGH</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="LOW">LOW</option>
      </select>

    <label class="my-1 mr-2 form-group mt-5" for="inlineFormCustomSelectPref2"><strong>Status:</strong></label>
      <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref2" name="status">
        <option selected><c:out value= "${requestScope.task.status}"/></option>
        <option value="TO_DO">TO DO</option>
        <option value="IN_PROGRESS">IN PROGRESS</option>
        <option value="DONE">DONE</option>
      </select>

    <p class="mt-5"><strong>Email addresses:</strong></p>
       <c:forEach items="${requestScope.task.contacts}" var="contact">
            <div class="form-group mt-1">
               <input type="email" class="form-control" id="email" name="email" value="<c:out value= "${contact.emailAddress}"/>" disabled>
            </div>
       </c:forEach>

    <div class="form-group mt-5">
    <p><strong>Add new email addresses of persons involved:</strong></p>
          <label for="email">Choose one of your existing contacts: </label>
          <select class="custom-select my-1 mr-sm-2" id="email" name="email">
            <option value="">                         </option>
            <c:forEach items="${requestScope.allContacts}" var="contact">
                   <option value="<c:out value= "${contact}"/>"><c:out value= "${contact}"/></option>
            </c:forEach>
          </select>
          <p class="help-block">*******You can enter email addresses where reminders will be sent ******</p>
     </div>

     <div class="form-group mt-1">
          <label for="newEmail">New contact: </label>
          <input type="email" class="form-control" id="newEmail" name="newEmail" placeholder="name@example.com">
          <p class="help-block">*******You can enter email addresses where reminders will be sent ******</p>
     </div>

     <p class="mt-5"><strong>Files attached:</strong></p>

     <c:forEach items="${requestScope.task.files}" var="file">
                    <i class="bi bi-file-earmark"></i><c:out value = "${file.name}"/>
     </c:forEach>

     <div class="form-group mt-5">
            <label for="files"><strong>Add new files:</strong></label>
            <input type="file" id="files" name="files" />
            <input type="file" name="files" />
            <input type="file" name="files" />
            <p class="help-block">*******You can save here new files that help you solve the task******</p>
      </div>
      <button type="submit" class="btn btn-primary mt-5">Update task</button>
    </form>
</div>

 <%@include file="../shared/footer.jsp"%>