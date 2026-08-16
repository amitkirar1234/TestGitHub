package in.co.rays.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.PatientBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.PatientModel;
import in.co.rays.util.JDBCDataSource;

public class TestPatientModel {

	public static void main(String[] args) throws Exception {

//		testNextPk();
		// testAdd();
		// testUpdate();
//		testDelete();
//		testFindByPk();
		testSearch();

	}

	public static void testSearch() throws Exception {
		PatientBean bean = new PatientBean();

		PatientModel model = new PatientModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//		bean.setDisease("malaria");
		
		bean.setDateofvisit(sdf.parse("2007-12-04"));
		List list = model.search(bean, 1, 10);

		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (PatientBean) it.next();
			System.out.print(bean.getId());
			System.out.print("\t" + bean.getName());
			System.out.print("\t" + bean.getDateofvisit());
			System.out.print("\t" + bean.getMobileNo());
			System.out.print("\t" + bean.getDisease());
			System.out.print("\t" + bean.getCreatedBy());
			System.out.print("\t" + bean.getModifiedBy());
			System.out.print("\t" + bean.getCreatedDatetime());
			System.out.println("\t" + bean.getModifiedDatetime());
		}
	}

}
