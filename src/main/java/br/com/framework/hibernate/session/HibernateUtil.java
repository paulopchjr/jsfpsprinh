package br.com.framework.hibernate.session;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.hibernate.engine.SessionFactoryImplementor;

import br.com.framework.implementacao.crud.VariavelConexaoUtil;

/*
 * 	Responsável por estabelecer uma conexao com hibernate
 * */

public class HibernateUtil implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	public static String JAVA_COMP_ENV_JDBC_DATA_SOURCE="java:/comp/env/jdbc/pjtec";

	private static SessionFactory sessionFactory = buildsSessionFactory();
	
	/*
	 * Responsável por ler o arquivo de configuração hibernate.cfg.xml
	 * 
	 * */
	private static SessionFactory buildsSessionFactory() {
		try {
			
			if(sessionFactory == null) {
				sessionFactory = new Configuration().configure().buildSessionFactory();
			}
			
			return sessionFactory;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError("Erro ao criar a conexao SessionFactory");
		
		}
		
	}
	
	/*
	 * Retorna o SessioFactory corrente;
	 * 
	 * */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
		
	}
	
	/*
	 * Retorna a Sessao do SessioFactory ;
	 * @return Session
	 * */
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/*
	 * Abre uma nova sessao no sessionFactory
	 * @return Session
	 * */
	public static Session openSession() {
		if(sessionFactory == null) {
			buildsSessionFactory();
		}
		return sessionFactory.openSession();
	}
	
	/*
	 * Obetendo a conexao do hibernate
	 * @return Connection SQL
	 * @throws SQLException
	 * */
	public static Connection getConnectionProvider() throws SQLException {
		
		return ((SessionFactoryImplementor) sessionFactory).getConnectionProvider().getConnection();
	}
	
	

	/*
	 * Obetendo a conexao do hibernate
	 * @return Connection  no InitialContext java:/com/env/jdbc/datasource
	 * @throws Exception
	 * */
	public static Connection getConnection() throws Exception {
		InitialContext context = new InitialContext();
		DataSource dataSourcer = (DataSource) context.lookup(JAVA_COMP_ENV_JDBC_DATA_SOURCE);
		return dataSourcer.getConnection();
	}
	
	
	/*
	 * @return dataSource
	 * @throws NamingException
	 * */
	public DataSource getDataSourceJndi() throws NamingException{
		InitialContext context =  new InitialContext();
		return(DataSource) context.lookup(VariavelConexaoUtil.JAVA_COMP_ENV_JDBC_DATA_SOURCE);
	}
	
	
	
	
	
	
}
