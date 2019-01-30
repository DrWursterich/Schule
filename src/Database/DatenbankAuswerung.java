package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatenbankAuswerung {
	private final Connection connection;

	public static void main(final String...args) throws Exception {
		new DatenbankAuswerung();
	}

	public DatenbankAuswerung() throws SQLException {
		this.connection = DriverManager.getConnection(
				"jdbc:sqlite:resources/db/Database/office.db");
		this.printArticles();
		this.printImports();
		this.printArticles("EDV-001");
	}

	private void printArticles() throws SQLException {
		final Statement statement = this.connection.createStatement();
		final ResultSet result = statement.executeQuery(
				"SELECT NUMBER, NAME FROM ARTICLE;");
		while (result.next()) {
			System.out.println(String.format(
					"Article Nb.: %8s Name: %-16s",
					result.getString("NUMBER"),
					result.getString("NAME")));
		}
		result.close();
		statement.close();
	}

	private void printArticles(final String articleNumber) throws SQLException {
		final PreparedStatement statement = this.connection.prepareStatement(
				"SELECT E.NUMBER AS E_NUMBER, "
					+ "A.NUMBER AS A_NUMBER, "
					+ "A.PRICE AS PRICE, "
					+ "E.AMOUNT AS AMOUNT "
					+ "FROM EXPORTS AS E "
					+ "JOIN ARTICLE AS A "
					+ "ON E.ARTICLE = A.NUMBER "
					+ "WHERE A_NUMBER = ?;");
		statement.setString(1, articleNumber);
		final ResultSet result = statement.executeQuery();
		while (result.next()) {
			System.out.println(String.format(
					"%2s: Article Nb.: %8s Price: %3.2f Amount: %2d Total Price: %4.2f",
					result.getString("E_NUMBER"),
					result.getString("A_NUMBER"),
					result.getDouble("PRICE"),
					result.getInt("AMOUNT"),
					result.getInt("AMOUNT") * result.getDouble("PRICE")));
		}
		result.close();
		statement.close();
	}

	private void printImports() throws SQLException {
		final Statement statement = this.connection.createStatement();
		final ResultSet result = statement.executeQuery(
				"SELECT NUMBER, DATE FROM IMPORTS;");
		while (result.next()) {
			System.out.println(String.format(
					"Import Nb.: %8s Date: %-16s",
					result.getString("NUMBER"),
					result.getString("DATE")));
		}
		result.close();
		statement.close();
	}

	@Override
	protected void finalize() throws SQLException {
		this.connection.close();
	}
}
