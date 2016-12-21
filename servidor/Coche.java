import java.io.*;

class Coche implements Serializable {
     private String matricula;
     private String codigoTarjeta;

     Coche(String mat, String cod) {
         matricula = mat;
         codigoTarjeta = cod;
     }
     public String getMatricula() {
         return matricula;
     }
     public String getCodigoTarjeta() {
         return codigoTarjeta;
     }
     public String toString() {
         return matricula + " | " + codigoTarjeta;
     }
}
