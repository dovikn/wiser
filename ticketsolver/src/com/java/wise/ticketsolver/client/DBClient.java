package com.java.wise.ticketsolver.client;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.wise.ticketsolver.triggers.Action;
import com.java.wise.ticketsolver.triggers.Component;
import com.java.wise.ticketsolver.triggers.ContainsRule;
import com.java.wise.ticketsolver.triggers.RegexRule;
import com.java.wise.ticketsolver.triggers.ResolutionActions;
import com.java.wise.ticketsolver.triggers.Rule;
import com.java.wise.ticketsolver.triggers.Trigger;
import com.java.wise.ticketsolver.triggers.TriggerType;


public class DBClient {
	
	private static Connection conn = null;
	
	private static final String QUERY_GET_ALL_TRIGGER_TYPES = "select * from wise_trigger_types;";
	private static final String QUERY_GET_ALL_COMPONENTS = "select * from wise_components;";
	private static final String QUERY_GET_ALL_ACTIONS = "select * from wise_actions;";
	private static final String QUERY_GET_ALL_RULES = "select * from wise_rules;";
	private static final String QUERY_GET_ALL_TRIGGERS = "select * from wiser_triggers;";
	private static final String QUERY_GET_ALL_RESOLUTION_ACTIONS = "select * from wise_resolution_actions;";
	private static final String QUERY_GET_ALL_TRIGGER_RULES = "select * from wise_trigger_rules where trigger_id=";
	private static final String QUERY_INCREMENT_TICKETS_SOLVED = "update wiser_triggers set tickets_solved = tickets_solved + 1 where id =";
	
	private static Map<Integer, TriggerType> types = new HashMap <Integer, TriggerType>();
	private static Map<Integer, Component> components = new HashMap<Integer, Component>();
	private static Map<Integer, Action> actions = new HashMap<Integer, Action>();
	private static Map<Integer, Rule> rules = new HashMap<Integer, Rule>();
	private static Map<Integer, ResolutionActions> resolution_actions = new HashMap<Integer, ResolutionActions>();
	private static List<Trigger> triggers = new ArrayList<Trigger>();
	
	
//	public static void getDBClient() {
//	
//		// Load the JDBC Driver
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Cannot find the driver in the classpath!", e);
//		}
//
//		Connection conn = null;
//		Statement readStatement = null;
//		ResultSet resultSet = null;
//		String results = "";
//
//		try {
//			conn =   DriverManager.getConnection("jdbc:mysql://wise01.ci3bocy1y9eq.us-west-2.rds.amazonaws.com:3306/wisemandb_00",
//					"wiseman", "wisemansays");
//			readStatement = conn.createStatement();
//			resultSet = readStatement.executeQuery("SELECT * FROM rules;");
//			resultSet.first();
//			results = resultSet.getString("name");
//			resultSet.next();
//
//			resultSet.close();
//			readStatement.close();
//			conn.close();
//
//		} catch (SQLException ex) {
//			// handle any errors
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
//		} finally {
//			if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
//		}
//	}
	
	public static Connection getConnection() {
		if (null != conn) {
			return conn;
		}
		try {
			conn =   DriverManager.getConnection("jdbc:mysql://wise01.ci3bocy1y9eq.us-west-2.rds.amazonaws.com:3306/wisemandb_00",
					"wiseman", "wisemansays");

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException while getting connection: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			throw new RuntimeException("[ERROR] Failed to get connection due to exception:\n",ex);
		} 
		return conn;
	}
	
	private static void closeConnection() {
		if (conn != null) {
			try { 
				conn.close(); 
			} catch (SQLException ignore) {}
		}
	}
	
	public static List<Trigger> loadAllTriggers() {
		Connection conn = DBClient.getConnection();
		loadTriggerTypes(conn);
		loadComponents(conn);
		loadActions(conn);
		loadResolutionActions(conn);
		loadRules(conn);
		List<Trigger> allTriggers = loadTriggers(conn);
		//DBClient.closeConnection();
		return allTriggers;
	}
	
	public static void incrementCountOfTicketsToSolveBy1(Integer trigger_id) {
		Connection conn = DBClient.getConnection();
		incrementCountOfTicketsSolvedBy1(conn,trigger_id);
		//DBClient.closeConnection();
		System.out.println("[INFO]: Incremented the number of of tickets resolved for trigger." + trigger_id.intValue()) ;
	}
	
	public static Map<Integer, TriggerType> loadTriggerTypes(Connection conn) {
		Statement getStatement = null;
		ResultSet rs = null;
		try {
			getStatement = conn.createStatement();
			rs = getStatement.executeQuery(QUERY_GET_ALL_TRIGGER_TYPES);
			while (rs.next()) {
				Integer id = Integer.valueOf(rs.getString("id"));
				TriggerType tp = new TriggerType(rs.getString("name"));
				
				types.put(id,tp);
			}
			rs.close();
			getStatement.close();
			System.out.println("[INFO]: Found " + types.size() + " types of triggers.") ;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return types;
	}
	
	public static Map<Integer, Component> loadComponents(Connection conn) {
		Statement getStatement = null;
		ResultSet rs = null;
		try {
			getStatement = conn.createStatement();
			rs = getStatement.executeQuery(QUERY_GET_ALL_COMPONENTS);
			while (rs.next()) {
				Integer id = Integer.valueOf(rs.getString("id"));
				Component co = new Component(id, rs.getString("name"));
				components.put(id,co);
			}
			rs.close();
			getStatement.close();
			System.out.println("[INFO]: Found " + components.size() + " components.") ;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return components;
	}
	
	public static Map<Integer, Action> loadActions(Connection conn) {
		Statement getStatement = null;
		ResultSet rs = null;
		try {
			getStatement = conn.createStatement();
			rs = getStatement.executeQuery(QUERY_GET_ALL_ACTIONS);
			while (rs.next()) {
				Integer id = Integer.valueOf(rs.getString("id"));
				Action a = new Action(id, rs.getString("name"));
				actions.put(id,a);
			}
			rs.close();
			getStatement.close();
			System.out.println("[INFO]: Found " + actions.size() + " actions.") ;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return actions;
	}
	
	public static Map<Integer, ResolutionActions> loadResolutionActions(Connection conn) {
		Statement getStatement = null;
		ResultSet rs = null;
		try {
			getStatement = conn.createStatement();
			rs = getStatement.executeQuery(QUERY_GET_ALL_RESOLUTION_ACTIONS);
			while (rs.next()) {
				Integer id = Integer.valueOf(rs.getString("id"));
				ResolutionActions a = new ResolutionActions(id, rs.getString("name"));
				resolution_actions.put(id,a);
			}
			rs.close();
			getStatement.close();
			System.out.println("[INFO]: Found " + actions.size() + " resolution actions.") ;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return resolution_actions;
	}
	
	public static Map<Integer, Rule> loadRules(Connection conn) {
		Statement getStatement = null;
		ResultSet rs = null;
		try {
			getStatement = conn.createStatement();
			rs = getStatement.executeQuery(QUERY_GET_ALL_RULES);
			while (rs.next()) {
				Integer id = Integer.valueOf(rs.getString("id"));
				Integer cId = Integer.valueOf(rs.getString("component_id"));
				Integer aId = Integer.valueOf(rs.getString("action_id"));
				Action a = actions.get(aId);
				boolean isEnabled = (rs.getString("is_enabled").equals("1") ? true: false);
				
				if (!isEnabled) {
					continue;
				}
				
				Rule r = null;
				
				if (a.getAction().equals(Action.CONTAINS.getAction())) {
					r = new ContainsRule(id, 
							rs.getString("name"),
							components.get(cId),
							a,
							rs.getString("target"));
					
				} else if (a.getAction().equals(Action.REGEX.getAction())) { 
					r = new RegexRule(id, 
							rs.getString("name"),
							components.get(cId),
							a,
							rs.getString("target"));	
				}
				rules.put(id,r);
			}
			rs.close();
			getStatement.close();
			System.out.println("[INFO]: Found " + rules.size() + " rules.") ;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return rules;
	}
	
	public static List<Trigger> loadTriggers(Connection conn) {
		Statement getStatement = null;
		ResultSet rs = null;
		try {
			getStatement = conn.createStatement();
			rs = getStatement.executeQuery(QUERY_GET_ALL_TRIGGERS);
			while (rs.next()) {
				Integer id = Integer.valueOf(rs.getString("id"));
				String name = rs.getString("name"); 
				TriggerType tt = types.get(Integer.valueOf(rs.getString("type_id")));
				boolean isEnabled = (rs.getString("is_enabled").equals("1") ? true: false);
				String comment = rs.getString("solution");
				ResolutionActions ra = resolution_actions.get(Integer.valueOf(rs.getString("resolution_actions_id")));
				List<Rule> triggerRules = getTriggerRules(conn, id);
				Trigger t = new Trigger(id,name, tt, triggerRules, isEnabled , comment, ra);
				triggers.add(t);
			}
			rs.close();
			getStatement.close();
			System.out.println("[INFO]: Found " + triggers.size() + " triggers.") ;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return triggers;
	}
	
	public static List<Rule> getTriggerRules(Connection conn, Integer triggerId) {
		Statement getStatement = null;
		ResultSet rs = null;
		List<Rule> triggerRules = new ArrayList<Rule>();
		try {
			getStatement = conn.createStatement();
			rs = getStatement.executeQuery(QUERY_GET_ALL_TRIGGER_RULES + triggerId.toString() + ";");
			while (rs.next()) {
				boolean isEnabled = (rs.getString("is_enabled").equals("1") ? true: false);
				if (isEnabled) {
					Integer ruleId = Integer.valueOf(rs.getString("rule_id"));
					Rule r = rules.get(ruleId);
					triggerRules.add(r);
				}
			}
			rs.close();
			getStatement.close();
			System.out.println("[INFO]: Found " + triggerRules.size() + " trigger rules.") ;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
		return triggerRules;
	}
	
	private static void incrementCountOfTicketsSolvedBy1(Connection conn, Integer triggerId) {
		Statement setStatement = null;
		int result= 0;
		try {
			setStatement = conn.createStatement();
			result = setStatement.executeUpdate(QUERY_INCREMENT_TICKETS_SOLVED + triggerId.toString() + ";");
			if (result == 0) {
				System.out.println("[ERROR]:  incrementCountOfTicketsSolvedBy1 failed!"); 
			}
			if (result == 1) {
				System.out.println("[INFO]:  incrementCountOfTicketsSolvedBy1 succeeded!");
			}			
			setStatement.close();
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} 
	}
}


