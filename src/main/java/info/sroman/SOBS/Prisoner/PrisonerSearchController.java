package info.sroman.SOBS.Prisoner;

import info.sroman.SOBS.SearchController;
import info.sroman.SOBS.Model.Prisoner;
import info.sroman.SOBS.SearchModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class PrisonerSearchController extends SearchController {
		
	PrisonerSearchModel model;
	
	@Override
    public SearchModel makeQuery(SearchModel model, ActionEvent event) {
		
        this.model = (PrisonerSearchModel) model;
		
		Connection conn = null;
		Statement statement = null;
		ObservableList<Prisoner> prisoners = FXCollections.observableArrayList();
    
		try {
			conn = DriverManager.getConnection(
					"jdbc:sqlite:./src/main/resources/db/SOBS.db"
			);
			statement = conn.createStatement();
			statement.setQueryTimeout(10);
			ResultSet rs = statement.executeQuery(constructStatement());
						
			while (rs.next()) {
				prisoners.add(new Prisoner(
						rs.getInt("PERSON_ID"), rs.getString("first_name"), 
						rs.getString("last_name"), rs.getInt("height"), rs.getInt("weight"), 
						rs.getString("date_of_birth"), rs.getString("race"), rs.getInt("PRISONER_ID"),
						rs.getString("arrest_date"), rs.getString("release_date"), rs.getInt("bunk_ID")
					)
				);
			}
						
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		} finally {
			try {
				if(conn != null)
					conn.close();
			} catch(SQLException ex) {
				System.err.println(ex);
			}
		}
		model.setResultsList(prisoners);
		return this.model;
    }
	
	/*
	*	Check each TextField to see if it has input. If it does create a 
	*	statement that includes a WHERE clause including that field's value and
	*	which references that field's column in the table
	*/
	@Override
	public String constructStatement() {
		
		StringBuilder baseStatement = new StringBuilder(
				"SELECT * FROM Person "
						+ "INNER JOIN "
							+ "Prisoner ON Person.PERSON_ID = Prisoner.PERSON_ID "
						+ "WHERE "
		);
		
		
		// parallel arrays that associate a TextField with its relevant table column
		String[] fieldValues = {
			model.getPersonId(), model.getFirstName(), model.getLastName(), 
			model.getHeight(), model.getWeight(), model.getDob(), model.getRace(), 
			model.getPrisonerId(), model.getArrestDate(), model.getReleaseDate(), 
			model.getBunkId()
		};
		
		String[] columns = {
			"PERSON_ID", "first_name", "last_name", "height", "weight", "date_of_birth", 
			"race", "PRISONER_ID", "arrest_date", "release_date", "bunk_id"
		};
		
		StringBuilder stmt = new StringBuilder();
		
		// if the statement has multiple WHERE clauses include an "AND" between them
		for (int i = 0; i < fieldValues.length; i++) {
			if (stmt.length() == 0)
				stmt.append(checkFieldForAndStatement(fieldValues[i], columns[i]));
			else if (!fieldValues[i].equals(""))
				stmt.append(" AND ").append(checkFieldForAndStatement(fieldValues[i], columns[i]));
		}
		
		baseStatement.append(stmt);	
		
		// Prevent "Ambugious column" error by adding Table name
		if (!model.getPersonId().equals(""))
			baseStatement.insert(baseStatement.indexOf(" PERSON_ID ") + 1, "Person.", 0, 7);
		
		String completedStatement = new String(baseStatement);
		System.out.println(completedStatement);

		return completedStatement;
	}
}
