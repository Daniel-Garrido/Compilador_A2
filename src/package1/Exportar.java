package package1;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Exportar {
    /*
    Esta clase nos va a permitir agregar los triplos a un archivo CSV
    */
    public void Imprimir(String ruta, String texto) throws IOException{
       File archivo = new File(ruta);
       try(FileWriter imp = new FileWriter(archivo)){
           imp.write(texto);
       }
       catch(IOException e){
           JOptionPane.showMessageDialog(null,"Ocurrio un error al exportar el archivo "+archivo);
       }
    }
}
