//package ro.exampledana.service;
//
//import ro.exampledana.entity.Doable;
//import ro.exampledana.entity.Status;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//public class DoableService {
//    private Connection dbConnection;
//
//    public DoableService(Connection dbConnection) {
//        this.dbConnection = dbConnection;
//    }
//
//    public List<Doable> findAll() {
//        List<Doable> doableList= new ArrayList<>();
//        try {
//            Statement statement = dbConnection.createStatement();
//            ResultSet results = statement.executeQuery("select * from tasks");
//
//            while (results.next()) {
//
//                doableList.add(
//                        new Doable(results.getInt(1),
//                                results.getString(2),
//                                Status.valueOf(results.getString(6).replace(" ", "_"))) {
//                        });
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(System.err);
//        }
//        return doableList;
//    }
//}
