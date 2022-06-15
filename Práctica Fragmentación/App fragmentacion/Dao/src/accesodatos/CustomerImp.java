//En esta clase se definen los metodos de su respectiva interface. 

package accesodatos;

import conexion.Conexion;
import domain.SalesCustomer;
import java.sql.*;
import java.util.*;

public class CustomerImp implements IAccesoDatosCustomer {

    PreparedStatement ps = null;
    ResultSet rs = null;
    ResultSetMetaData md = null;

    //Metodo que recupera todos los registros de su respectiva tabla.
    @Override
    public List<SalesCustomer> listar() {
        System.out.println("Listar desde SqlServer");
        List<SalesCustomer> lista = null;
        SalesCustomer salCust = null;

        try {
            Conexion con = new Conexion();
            ps = con.Conectar().prepareStatement("{call sp_RecuperarCustomer}");

            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                salCust = new SalesCustomer();
                salCust.setCustomerID(rs.getInt(1));
                salCust.setPersonID(rs.getInt(2));
                salCust.setStoreID(rs.getInt(3));
                salCust.setTerritoryID(rs.getInt(4));
                salCust.setAccountNumber(rs.getString(5));
                salCust.setRowguid(rs.getString(6));
                salCust.setModifiedDate(rs.getString(7));

                lista.add(salCust);
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return lista;
    }

    //Metodo que actualiza todos los registros de su respectiva tabla.
    @Override
    public void actualizar(SalesCustomer salCust) {
        System.out.println("\nActualizar desde SqlServer");

        try {
            Conexion con = new Conexion();
            ps = con.Conectar().prepareStatement("{call sp_ActualizarCustomer(?,?)}");

            ps.setInt(1, salCust.getCustomerID());
            ps.setInt(2, salCust.getPersonID());

            ps.execute();
            System.out.println("Actualizacion Exitosa");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public List<SalesCustomer> listarNorthAmerica() {
        List<SalesCustomer> lista = null;
        SalesCustomer salCust = null;

        try {
            Conexion con = new Conexion();
            ps = con.Conectar().prepareStatement("{call sp_listarClienteNorthAmerica}");

            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                salCust = new SalesCustomer();
                salCust.setCustomerID(rs.getInt(1));
                salCust.setNumberOfOrders(rs.getInt(2));
                lista.add(salCust);
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return lista;
    }

    @Override
    public List<SalesCustomer> listarClientesTerritorio() {
        List<SalesCustomer> lista = null;
        SalesCustomer salCust = null;

        try {
            Conexion con = new Conexion();
            ps = con.Conectar().prepareStatement("{call sp_listarClientesTerritorio1a4}");

            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                salCust = new SalesCustomer();
                salCust.setCustomerID(rs.getInt(1));
                lista.add(salCust);
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }finally{
            Conexion.close(rs);
            Conexion.close(ps);
        }

        return lista;
        
    }

    @Override
    public List<SalesCustomer> listarProductoEuropa() {
        List<SalesCustomer> lista = null;
        SalesCustomer salCust = null;

        try {
            Conexion con = new Conexion();
            ps = con.Conectar().prepareStatement("{call sp_productoMasSolicitadoEurope}");

            rs = ps.executeQuery();
            lista = new ArrayList();
            while (rs.next()) {
                salCust = new SalesCustomer();
                salCust.setCustomerID(rs.getInt(1));
                salCust.setPersonID(rs.getInt(2));
                salCust.setStoreID(rs.getInt(3));
                salCust.setTerritoryID(rs.getInt(4));
                salCust.setAccountNumber(rs.getString(5));
                salCust.setRowguid(rs.getString(6));
                salCust.setModifiedDate(rs.getString(7));
                lista.add(salCust);
            }

            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }finally{
            Conexion.close(rs);
            Conexion.close(ps);
        }

        return lista;
        
    }

}
