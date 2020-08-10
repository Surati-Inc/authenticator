package com.security.authenticator.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.minlessika.db.Database;
import com.minlessika.exceptions.DatabaseException;

/**
 * Users in Database
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public final class DbUsers implements Users {

	private final Database source;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbUsers(final Database source) {
		this.source = source;
	}
	
	@Override
	public User authenticate(String login, String password) {
		
		if(! has(login))
			throw new IllegalArgumentException("Ce login n'existe pas !");
		
		final User user = get(login);
		if(! user.password().equals(password))
			throw new IllegalArgumentException("Le mot de passe est invalide !");
		
		if(user.blocked())
			throw new IllegalArgumentException("Vous avez été bloqué ! Veuillez SVP contacter votre administrateur.");
		
		return user;
	}

	@Override
	public User get(String login) {
		
		try(
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT id FROM app_user WHERE login=?");
		){
			pstmt.setString(1, login);
		
			try(final ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					Long id = rs.getLong(1);
					return new DbUser(source, id);
				} else {
					throw new IllegalArgumentException(String.format("User with login %s not found !", login));
				}			
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public User get(Long id) {
		try(
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM app_user WHERE id=?");
		){
			pstmt.setLong(1, id);
		
			try(final ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return new DbUser(source, id);
				} else {
					throw new IllegalArgumentException(String.format("User with ID %s not found !", id));
				}			
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public void register(String name, String login, String password) {
		
		if(name == null || name.trim().isEmpty()) 
			throw new IllegalArgumentException("User name cannot be empty !");
		
		if(login == null || login.trim().isEmpty())
			throw new IllegalArgumentException("User login cannot be empty !");
		
		if(password == null || password.trim().isEmpty())
			throw new IllegalArgumentException("User password cannot be empty !");
		
		if(has(login))
			throw new IllegalArgumentException(String.format("Login %s is already used !", login));
		
		try(
				final Connection connection = source.getConnection();
				final PreparedStatement pstmt = connection.prepareStatement("INSERT INTO person (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
				final PreparedStatement pstmt1 = connection.prepareStatement("INSERT INTO app_user (id, login, password, blocked) VALUES (?, ?, ?, false)")
		){
			
			pstmt.setString(1, name);
			
			pstmt.executeUpdate();
			
			final Long id;
			try (final ResultSet rs = pstmt.getGeneratedKeys()) {
				rs.next();
				id = rs.getLong(1);
			}
			
			pstmt1.setLong(1, id);
			pstmt1.setString(2, login);
			pstmt1.setString(3, password);
			
			pstmt1.executeUpdate();
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public Iterable<User> iterate() {
		
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT id FROM app_user")
		){
			final Collection<User> items = new ArrayList<>();
			
			try(final ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					items.add(new DbUser(source, rs.getLong(1)));
				}
			}
			
			return items;
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}

	@Override
	public boolean has(String login) {
		try (
			final Connection connection = source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) as nb FROM app_user WHERE login=?")
		){
			pstmt.setString(1, login);
		
			try(final ResultSet rs = pstmt.executeQuery()){
				rs.next();
				Long nb = rs.getLong(1);
				return nb > 0;
			}
		} catch(SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
}
