package in.co.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.MarksheetBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.MarksheetModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * GetMarksheetCtl allows the user to retrieve a student's marksheet using their
 * Roll Number. It validates the input and fetches the record from the database
 * if it exists.
 * 
 * @author Amit
 * @version 1.0
 */

@WebServlet(name = "GetMarksheetCtl", urlPatterns = { "/ctl/GetMarksheetCtl" })
public class GetMarksheetCtl extends BaseCtl {
	
	Logger log = Logger.getLogger(GetMarksheetCtl.class);

	/**
	 * Validates the Roll Number input field.
	 *
	 * @param request the HttpServletRequest object
	 * @return true if valid, false otherwise
	 */

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.info("GetMarksheetCtl validate Method Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}

		log.info("GetMarksheetCtl validate Method Ended");
		return pass;
	}

	/**
	 * Populates the MarksheetBean with request parameters.
	 *
	 * @param request the HttpServletRequest object
	 * @return populated MarksheetBean
	 */

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.info("GetMarksheetCtl populateBean Method Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		log.info("GetMarksheetCtl populateBean Method Ended");
		return bean;
	}

	/**
	 * Handles the GET request. Forwards to the marksheet view page.
	 *
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("GetMarksheetCtl doGet Method Started");
		
		ServletUtility.forward(getView(), request, response);
		log.info("GetMarksheetCtl doGet Method Ended");
	}

	/**
	 * Handles the POST request. Fetches the marksheet based on roll number.
	 *
	 * @param request  the HttpServletRequest object
	 * @param response the HttpServletResponse object
	 * @throws ServletException
	 * @throws IOException
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("GetMarksheetCtl doPost Method Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		MarksheetModel model = new MarksheetModel();

		MarksheetBean bean = (MarksheetBean) populateBean(request);

		if (OP_GO.equalsIgnoreCase(op)) {

			try {
				bean = model.findByRollNo(bean.getRollNo());
				if (bean != null) {
					ServletUtility.setBean(bean, request);
				} else {
					ServletUtility.setErrorMessage("RollNo Does Not exists", request);
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		
		log.info("GetMarksheetCtl doPost Method Ended");
		ServletUtility.forward(getView(), request, response);
	}

	/**
	 * Returns the view path of GetMarksheet page.
	 *
	 * @return String view path
	 */

	@Override
	protected String getView() {

		return ORSView.GET_MARKSHEET_VIEW;
	}

}
