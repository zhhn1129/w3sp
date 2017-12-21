<%@page pageEncoding="utf-8"%>
<style>
<!--
/* This is the style for the informational messages presented to the user */

div.error, span.error, li.error, div.message {
    background: #ffffcc;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
}

/* use a different color for the errors */
div.error, span.error, li.error {
    border: 2px solid red;
}

/* For Spring MVC */
span.error {
    display: block;
}

label.error {
    display: none; /* Replace the label in error with the validation message */
    font-weight: bold;
}

/* IE fix, followed by the rest of the world fix */
li.error {
    padding: 3px !important;
}

ul>li.error {
    padding: 0 !important;
}

div.message p, div.message p {
    margin-bottom: 0;
}

img.validationWarning, div.error img.icon, div.message img.icon, li.error img.icon {
    border: 0 !important;
    width: 14px;
    height: 13px;
    vertical-align: middle;
    margin-left: 3px;
    background: transparent !important;
    /* important added because some themes define div#main img */
}

/* Replaces label with validation error */
div li img.validationWarning { float: left; margin-top: 5px; margin-right: 3px; }
/* hides the icon when message is below input fields */
li div p img.validationWarning { /*margin-top: -17px; float: right*/ display: none}

div.message a {
    background: transparent;
    color: #0000FF;
}

div.message a:visited {
    background: transparent;
    color: #0000FF;
}

div.message a:hover {
    background: transparent;
    color: #008000;
}

div.message a:active {
    text-decoration: underline overline;
}

div.message img.icon {
    vertical-align: middle;
}

img.calIcon {
    vertical-align: middle;
    padding-bottom: 6px;
}

span.fieldError, .errorMessage {
    color: red;
    font-weight: normal;
    display: block;
}

/* Tapestry-specific messages: http://tapestry.apache.org/tapestry4.1/usersguide/clientside-validation.html */
.fieldMissing {
    background: #FFCA7A !important;
}

.fieldInvalid {
    background: #FF887A !important;
    font-weight: bold;
}

.alertDialog {
    width: 30em;
    border: 2px solid red;
    padding: 2em;
    text-align: left;
    background: #fff;
    -moz-border-radius: 10px;
}

.alertContent .alertButton {
    float: right;
    position: relative;
    bottom: .75em;
}

.missingList, .invalidList {
    padding-bottom: 1em;
    padding-top: 0.2em;
    padding-left: 0.1em;
    padding-right: 0.2em;
    margin: 0;
}

.missingList {
    border-top: 4px solid #FFCA7A;
}

.invalidList {
    border-top: 4px solid #FF887A;
}

.missingList li, .invalidList li {
    list-style: none;
    line-height: 1.2;
}

-->
</style>
<% if (request.getAttribute("struts.valueStack") != null) { %>
<%-- ActionError Messages - usually set in Actions --%>
<s:if test="hasActionErrors()">
    <div class="error" id="errorMessages">    
      <s:iterator value="actionErrors">
        <img src="<c:url value="/images/iconWarning.gif"/>"
            alt="<fmt:message key="icon.warning"/>" class="icon" />
        <s:property escape="false"/><br />
      </s:iterator>
   </div>
</s:if>

<%-- FieldError Messages - usually set by validation rules --%>
<s:if test="hasFieldErrors()">
    <div class="error" id="errorMessages">    
      <s:iterator value="fieldErrors">
          <s:iterator value="value">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="信息" class="icon" />
             <s:property escape="false"/><br />
          </s:iterator>
      </s:iterator>
   </div>
</s:if>
<% } %>

<%-- Success Messages --%>
<c:if test="${not empty messages}">
    <div class="message" id="successMessages">    
        <c:forEach var="msg" items="${messages}">
            <img src="<c:url value="/images/iconInformation.gif"/>"
                alt="信息" class="icon" />
            <c:out value="${msg}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    <c:remove var="messages" scope="session"/>
</c:if>