package util;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T>T excecute(String sql , Object...paras) throws SQLException, ClassNotFoundException {
            PreparedStatement statement = DBConnection.getInstace().getConection().prepareStatement(sql);
        for (int i = 0; i <paras.length; i++) {
            statement.setObject((i+1),paras[i]);
        }
        if(sql.startsWith("SELECT")){
            return (T)statement.executeQuery();
        }else {
            return (T)(Boolean)(statement.executeUpdate()>0);
        }
    }
}
