
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {

    private final String USER = "root";
    private final String PASSWORD = "";
    private final String URL = "jdbc:mysql://localhost/products?";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    Connection conn;

    Statement stmt = null;
    PreparedStatement ps;
    ResultSet rs;

    public Database() {
       connection();
    }
    public void connection(){
        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD); 
        }catch(SQLException ex){
            System.out.println("Error al conextarse a la base de datos");
        }finally{
            if(conn==null){
                System.out.println("Error al conectarse db. Vuelva a intentar");
                System.exit(0);
            }
            System.out.println("finalizo con exito. DB connectado");  
 
        }
    }

    public String read_db() {
        /*
        CON PREPARE "SELECT *FROM users WHERE id=?"
        */
        try {
           stmt = conn.createStatement();
           rs = stmt.executeQuery("SELECT * FROM users");   
           Object []user = new Object[2];
            while(rs.next()){
                user[0]=rs.getInt("id");
                user[1]=rs.getString("nombre");
                
            }
            System.out.println("----------- USERS --------------");
            for(Object n : user){
                System.out.println("> "+n);
            }
            System.out.println("----------- ***** --------------");
            rs.close();
            return "Ok";
         
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return "No se pudo encontrar la información requerida";
        }
    }
    public String insert_db(String data, int id){
        try{
            ps = conn.prepareStatement("INSERT INTO users values(?,?)");
            ps.setInt(1, id);
            ps.setString(2, data);
            ps.executeUpdate(); 
            ps.close();
            return "Se guardo correctamente";
            
        }catch(SQLException ex){
            System.out.println("Error al insertar");
            return "No se pudo guardar";
        }
    }
    public String update_db(String name, int id){
        try{
            ps = conn.prepareStatement("UPDATE users SET nombre=? WHERE id=?");
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
            ps.close();
            return "se actualizo con exito";
            //stmt = conn.createStatement(); 
            //stmt.executeUpdate("UPDATE users SET nombre='carol' WHERE id=1");
        }catch(SQLException ex){
            System.out.println("Error al actualizar");
            return "No se pudo actualizar";
        }
    }
    public String delete_db (int id){
         try{
            ps = conn.prepareStatement("DELETE FROM users WHERE id=?"); 
            ps.setInt(1, id);
            ps.executeUpdate();
            ps.close();
            return "Se elimino con exito";
        }catch(SQLException ex){
            System.out.println("Error al ALIMINAR");
            return "No se pudo eliminar";
        }
    }
}
//https://sites.google.com/a/espe.edu.ec/programacion-ii/home/estructuras-dinamicas