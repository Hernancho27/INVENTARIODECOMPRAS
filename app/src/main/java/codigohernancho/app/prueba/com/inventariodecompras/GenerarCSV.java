package codigohernancho.app.prueba.com.inventariodecompras;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import codigohernancho.app.prueba.com.inventariodecompras.modelo.ProductoInventario;

/**
 * Created by validColombia on 28/06/17.
 */

public class GenerarCSV {
    private static final char DEFAULT_SEPARATOR = ',';


    public static File expFile= null;


    public static void generarInforme(Context context,ArrayList<ProductoInventario> productoInventarios) throws IOException {
        FileWriter writer = null;
        String filename = "myfile";

        File file = new File(context.getFilesDir(), filename);
        try {
          writer = new FileWriter(file);
                ArrayList<ProductoInventario> productos = productoInventarios;

                //for header
                writeLine(writer, Arrays.asList("Id", "Nombre", "Marca","lugar_Compra","fecha_vencimiento","fecha_ingreso","cantidad","categoria","precio" ), ',', '"');

                for (ProductoInventario d : productos) {

                    List<String> list = new ArrayList<>();
                    list.add(d.getId());
                    list.add(d.getNombre());
                    list.add(d.getMarca());
                    list.add(d.getLugar_Compra());
                    list.add(d.getFecha_vencimiento());
                    list.add(d.getFecha_ingreso());
                    list.add(d.getCantidad());
                    list.add(d.getCategoria());
                    list.add(d.getPrecio());


                    writeLine(writer, list, ',', '"');
                }

                writer.flush();
                writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }


    }


    public static File  mostrarReporte(Context context){
        //File external memory

        String dstPath = Environment.getExternalStorageDirectory().toString();
        File dst = new File(dstPath);

        //File Internal Memory
        File file = new File(context.getFilesDir() + "/myfile");
        try {
            exportFile(file, dst);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());


    }



    private static File exportFile(File src, File dst) throws IOException {

        //if folder does not exist
        if (!dst.exists()) {
            if (!dst.mkdir()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        expFile = new File(dst.getPath() + File.separator + "informe_" + timeStamp + ".csv");
        FileChannel inChannel = null;
        FileChannel outChannel = null;


        try {
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(expFile).getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            if (outChannel != null)
                outChannel.close();
        }

        return expFile;
    }
}
