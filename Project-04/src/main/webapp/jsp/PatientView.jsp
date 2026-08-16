<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="in.co.rays.controller.PatientCtl" %>
<%@ page import="in.co.rays.util.HTMLUtility" %>
<%@ page import="in.co.rays.util.DataUtility" %>
<%@ page import="in.co.rays.util.ServletUtility" %>
<%@ page import="in.co.rays.bean.PatientBean" %>
<%@ page import="in.co.rays.controller.ORSView" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Add Patient</title>
    <link rel="icon" type="image/png"
          href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>

    <form action="<%=ORSView.PATIENT_CTL%>" method="post">
    
    <%
			HashMap<String, String> map = (HashMap<String, String>) request.getAttribute("diseaseMap");
		%>

        <%@ include file="Header.jsp" %>

        <jsp:useBean id="bean" class="in.co.rays.bean.PatientBean" scope="request" />

        

        <div align="center">

            <!-- Heading -->
            <h1 align="center" style="margin-bottom: -15; color: navy">
                <%
                    if (bean != null && bean.getId() > 0) {
                %>
                    UPDATE
                <%
                    } else {
                %>
                    ADD
                <%
                    }
                %>
                PATIENT
            </h1>

            <!-- Messages -->
            <div style="height: 15px; margin-bottom: 12px">
                <h3 align="center">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage(request)%>
                    </font>
                </h3>
                <h3 align="center">
                    <font color="green">
                        <%=ServletUtility.getSuccessMessage(request)%>
                    </font>
                </h3>
            </div>

            <!-- Hidden Fields -->
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime"
                   value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime"
                   value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <!-- Table Starts -->
            <table>

                <!-- Name -->
                <tr>
                    <th align="left">Name<span style="color: red">*</span></th>
                    <td>
                        <input type="text" name="name" placeholder="Enter Name"
                               value="<%=DataUtility.getStringData(bean.getName())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("name", request)%>
                        </font>
                    </td>
                </tr>

                <!-- Disease -->
                <tr>
                    <th align="left">Disease<span style="color: red">*</span></th>
                    <td>
                        <%
                           
                            String diseaseList =
                                HTMLUtility.getList("disease", bean.getDisease(),map);
                        %>
                        <%=diseaseList%>
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("disease", request)%>
                        </font>
                    </td>
                </tr>

                <!-- Date Of Visit -->
                <tr>
                    <th align="left">Date Of Visit<span style="color: red">*</span></th>
                    <td>
                        <input type="text" id="udate" name="dateofvisit"
                               placeholder="Select date Of visit"
                               value="<%=DataUtility.getDateString(bean.getDateofvisit())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("dateOfVisit", request)%>
                        </font>
                    </td>
                </tr>

                <!-- Mobile No -->
                <tr>
                    <th align="left">Mobile No<span style="color: red">*</span></th>
                    <td>
                        <input type="text" name="mobileNo" maxlength="10"
                               placeholder="Enter Mobile No."
                               value="<%=DataUtility.getStringData(bean.getMobileNo())%>">
                    </td>
                    <td style="position: fixed;">
                        <font color="red">
                            <%=ServletUtility.getErrorMessage("mobileNo", request)%>
                        </font>
                    </td>
                </tr>

                <!-- Action Buttons -->
                <tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=PatientCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=PatientCtl.OP_CANCEL%>">
						<%
							} else {
						%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=PatientCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=PatientCtl.OP_RESET%>">
						<%
							}
						%>
				</tr>

            </table>
            <!-- Table Ends -->

        </div>
    </form>

    <%@ include file="Footer.jsp" %>

</body>
</html>
