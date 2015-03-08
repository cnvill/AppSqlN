/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import ClaseBD.Conexion;
import Entidades.TAsignatura;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 *
 * @author Nuria
 */
public class BLAsignatura {
    
    public static String RegistrarAsignatura(TAsignatura oAsignatura){
        String Res="No";
        try {
            Conexion.AbrirBD();
            String consulta="INSERT INTO tasignatura(idasignatura, nombre, creditos, horasteorica, horaspractica, horaslaboratorio, totalhoras, ciclo, estado) \n" +
                            "VALUES ( '"+oAsignatura.getIdasignatura()+"','"+oAsignatura.getNombre()+"',"+oAsignatura.getCredito()+","+oAsignatura.getHorasteorica()+","+oAsignatura.getHoraspractica()+","+oAsignatura.getHoraslaboratorio()+"," +(oAsignatura.getHoraslaboratorio()+oAsignatura.getHoraspractica()+oAsignatura.getHorasteorica()) +", '"+oAsignatura.getCiclo()+ "' ,1)";
            if(Conexion.Ejecutar(consulta)==1)
                Res="OK";
            Conexion.CerradBD();
            
        } catch (Exception e) {
            Res="NO"+e.getMessage();
        }
        return Res;
    }
    
    public static ArrayList<TAsignatura> ListaAsignaturas(){
        ArrayList<TAsignatura> tasignatura= new ArrayList<TAsignatura>();
        try {
            Conexion.AbrirBD();
            ResultSet rs=Conexion.EjecutarConsulta("select * from tasignatura");
            TAsignatura oAsignatura;
            while(rs.next()){
                oAsignatura= new TAsignatura();
                oAsignatura.setIdasignatura(rs.getString("idasignatura"));
                oAsignatura.setNombre(rs.getString("nombre"));
                oAsignatura.setCredito(rs.getInt("creditos"));
                oAsignatura.setHorasteorica(rs.getInt("horasteorica"));
                oAsignatura.setHoraslaboratorio(rs.getInt("horaslaboratorio"));                
                oAsignatura.setCiclo(rs.getString("ciclo"));
                oAsignatura.setEstado(rs.getInt("estado"));
                tasignatura.add(oAsignatura);
            }
            Conexion.CerradBD();
        } catch (Exception e) {
            tasignatura=null;
            System.out.print(""+e.getMessage());
        }
     return tasignatura;
    }
   
// public static void main(String arg[]){
//     try {
//         BLAsignatura bl=new BLAsignatura();  
//         
//      System.out.println("Hello World!"+bl.ListaAsignaturas().size());
//     } catch (Exception e) {
//      System.out.println(e.getMessage());   
//     }
// }
}
