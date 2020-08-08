package com.security.authenticator.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.minlessika.db.Database;
import com.minlessika.exceptions.DatabaseException;

/**
 * User in Database
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public final class DbUser extends DbAbstractPerson implements User {

	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbUser(final Database source, final Long id) {
		super(source, id);
	}

	@Override
	public String login() {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT login FROM app_user WHERE id=?")
		){
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public String password() {
		try (
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("SELECT password FROM app_user WHERE id=?")
			){
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getString(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public void forceChangePassword(final String newPassword) {
		
		if(newPassword == null || newPassword.trim().isEmpty())
			throw new IllegalArgumentException("New password must be given !");
		
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE app_user SET password=? WHERE id=?")
		) {
			pstmt.setString(1, newPassword);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public boolean blocked() {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT blocked FROM app_user WHERE id=?")
		){
			pstmt.setLong(1, id);
			try (final ResultSet rs = pstmt.executeQuery()) {
				rs.next();
				return rs.getBoolean(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public void block(boolean enable) {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE app_user SET blocked=? WHERE id=?")
		) {
			pstmt.setBoolean(1, enable);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}

}
