/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import ClaseBD.Conexion;
import Entidades.TAsignatura;
import Entidades.TDetMatricula;
import Entidades.TEstudiante;
import Entidades.TMatricula;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Nuria
 */
public class BLMatricula {
    
    public BLMatricula(){
        
    }
    
    public static String GenerarRegistroMatricula(){
        String respuesta="";
        try {
        ArrayList<TEstudiante> listaEstudiantes= BLEstudiante.ListaEstudiantes(); 
        ArrayList<TAsignatura> listaAsignaturas= BLAsignatura.ListaAsignaturas();
        int nE=listaEstudiantes.size();
        int nA=listaAsignaturas.size();
        respuesta =""+nE*nA;
        TMatricula oMatricula;
        TDetMatricula oDetMatricula;
            for (int i = 0; i < nE; i++) {
                
                oMatricula= new TMatricula();
                oMatricula.setEstado(1);
                oMatricula.setIdestudiante(listaEstudiantes.get(i).getIdestudiante());
                oMatricula.setSemestre("III");
                oMatricula.setTotalcreditos(i);
                InsertMatricula(oMatricula);
                String idMatricula=getMaxIdMatricula();
                int totalCredito=0;
                for (int j = 0; j < nA; j++) {
                    oDetMatricula= new TDetMatricula();
                    oDetMatricula.setIdasignatura(listaAsignaturas.get(j).getIdasignatura());
                    oDetMatricula.setEstado(1);
                    oDetMatricula.setIdmatricula(Integer.parseInt(idMatricula));
                    oDetMatricula.setCredito(listaAsignaturas.get(j).getCredito());
                    BLDetMatricula.RegistrarDetMatricula(oDetMatricula);
                    totalCredito=totalCredito+listaAsignaturas.get(j).getCredito();
                }
                ActualizaCreditoMatricula(idMatricula, totalCredito);
            }
            
        
        } catch (Exception e) {
            respuesta = respuesta+e.getMessage();
        }
        return respuesta;
    }
    
    public static void ActualizaCreditoMatricula(String idMatricula, int totalCredito ){
         String resp="";
        try {
            Conexion.AbrirBD();
            String consulta="update  tmatricula set totalcredito="+totalCredito+" where idmatricula="+idMatricula;
            if(Conexion.Ejecutar(consulta)==1)
                resp="OK";
            Conexion.CerradBD();
            resp="OK";
        } catch (Exception e) {
            resp=e.getMessage();
        }
        
    }
    public static String InsertMatricula(TMatricula oMatricula){
        String resp="";
        try {
            Conexion.AbrirBD();
            String consulta="INSERT INTO  tmatricula ( semestre ,  idestudiante ,  totalcredito ,  fechamtricula ,  estado )"
                    + "VALUES ('"+ oMatricula.getSemestre()+"' ,"+ oMatricula.getIdestudiante()+" ,"+oMatricula.getTotalcreditos()+ ", getdate(), 1)";
            if(Conexion.Ejecutar(consulta)==1)
                resp="OK";
            Conexion.CerradBD();
            resp="OK";
        } catch (Exception e) {
            resp=e.getMessage();
        }
        return resp;
    }
    
    public static String  getMaxIdMatricula(){
      String resp="";
        try {
           Conexion.AbrirBD();
           ResultSet rs =Conexion.EjecutarConsulta("select max(idmatricula) as idmatricula from tmatricula");
           rs.next();
           resp= ""+rs.getInt(1);
           rs.close();
           Conexion.CerradBD();
        } catch (Exception e) {
            resp=e.getMessage();
        }
        return resp;
    }
//    
// public static void main(String arg[]){
//     try {
//         
//         String res= BLMatricula.GenerarRegistroMatricula();  
//         System.out.print(res);
//      
//     } catch (Exception e) {
//      System.out.println(e.getMessage());   
//     }
// }
    
}
