package com.security.authenticator.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.minlessika.db.Database;
import com.minlessika.exceptions.DatabaseException;

/**
 * Person in Database
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public abstract class DbAbstractPerson implements Person {

	/**
	 * Data source
	 */
	protected final Database source;
	
	/**
	 * Id of person
	 */
	protected final Long id;
	
	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbAbstractPerson(final Database source, final Long id) {
		this.source = source;
		this.id = id;
	}
	
	@Override
	public Long id() {
		return id;
	}

	@Override
	public String name() {
		try (
				final Connection connection = source.getConnection(); 
				final PreparedStatement pstmt = connection.prepareStatement("SELECT name FROM person WHERE id=?")
		) {
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
	public void update(final String name) {
		
		if(name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Person's name must be given !");
		
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("UPDATE person SET name=? WHERE id=?")
		) {
			pstmt.setString(1, name);
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		
	}

}
