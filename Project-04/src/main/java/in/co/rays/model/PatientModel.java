package in.co.rays.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.PatientBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.util.JDBCDataSource;

/**
 * Model class for Patient. Handles add, update, delete, find, list and search
 * operations.
 * 
 * author Amit version 1.0
 */
public class PatientModel {

	/**
	 * Returns the next primary key for patient table.
	 */
	public Integer nextPK() throws SQLException {

		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_patient");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {

			pk = rs.getInt(1);
		}

		rs.close();

		return pk + 1;
	}

	/**
	 * Adds a new patient record in the database.
	 * 
	 * @param bean patient details
	 * @return generated primary key
	 */
	public long add(PatientBean bean) throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			pk = nextPK();
			// factory desing pattern...
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_patient values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setDate(3, new java.sql.Date(bean.getDateofvisit().getTime()));
			pstmt.setString(4, bean.getMobileNo());
			pstmt.setString(5, bean.getDisease());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();
			System.out.println("data added successfully " + i);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;
	}

	/**
	 * Updates an existing patient record.
	 * 
	 * @param bean patient details to update
	 */
	public void update(PatientBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_patient set name = ?, dateofvisit = ?, mobileNo = ?, disease = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
			pstmt.setString(1, bean.getName());
			pstmt.setDate(2, new java.sql.Date(bean.getDateofvisit().getTime()));
			pstmt.setString(3, bean.getMobileNo());
			pstmt.setString(4, bean.getDisease());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());

			int i = pstmt.executeUpdate();
			System.out.println("data updated successfully" + i);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating User ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Deletes a patient record by id.
	 * 
	 * @param bean patient id
	 */
	public void delete(PatientBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_patient where id = ?");
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();

			System.out.println("data deleted successfully " + i);
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	/**
	 * Finds a patient by primary key.
	 * 
	 * @param pk patient id
	 * @return patient details
	 */
	public PatientBean findByPk(long pk) throws ApplicationException {

		PatientBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_patient where id = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new PatientBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDateofvisit(rs.getDate(3));
				bean.setMobileNo(rs.getString(4));
				bean.setDisease(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	/**
	 * Returns all patient records.
	 */
	public List list() throws ApplicationException {
		return search(null, 0, 0);
	}

	/**
	 * Searches patient records by given criteria.
	 * 
	 * @param bean     search criteria
	 * @param pageNo   page number
	 * @param pageSize number of records per page
	 * @return list of patients
	 */
	public List search(PatientBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		ArrayList list = new ArrayList();

		StringBuffer sql = new StringBuffer("SELECT * FROM st_patient WHERE 1=1 ");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getName() + "%'");
			}
			if (bean.getDisease() != null && bean.getDisease().length() > 0) {
				sql.append(" AND disease LIKE '" + bean.getDisease() + "%'");
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND mobileNo LIKE '" + bean.getMobileNo() + "%'");
			}
			if (bean.getDateofvisit() != null) {
				sql.append(" AND dateofvisit = '" + new java.sql.Date(bean.getDateofvisit().getTime()) + "'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" LIMIT " + pageNo + ", " + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				PatientBean pb = new PatientBean();
				pb.setId(rs.getLong(1));
				pb.setName(rs.getString(2));
				pb.setDateofvisit(rs.getDate(3));
				pb.setMobileNo(rs.getString(4));
				pb.setDisease(rs.getString(5));
				pb.setCreatedBy(rs.getString(6));
				pb.setModifiedBy(rs.getString(7));
				pb.setCreatedDatetime(rs.getTimestamp(8));
				pb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(pb);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search user " + e.getMessage());
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}

}
