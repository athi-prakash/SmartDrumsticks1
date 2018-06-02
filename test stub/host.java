import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

class host
{
    public static void main(String srgs[])
    {   
        try{
            ServerSocket serverSocket = new ServerSocket(8081);
            for (;;)
            {
            System.out.println("I'm waiting here: " + serverSocket.getLocalPort());            
                                
            Socket socket = serverSocket.accept();
            System.out.println("from " + 
                socket.getInetAddress() + ":" + socket.getPort());
            
            InputStream inputStream = socket.getInputStream();
            
            //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            int i=0;
            while ((bytesRead = inputStream.read(buffer)) != -1){
            	ByteArrayOutputStream byteArrayOutputStream = 
                        new ByteArrayOutputStream(1024);
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                if (i==0)
                {
                	System.out.print("     ");
                	i=1;
                }
                else
                	i=0;
                System.out.println(byteArrayOutputStream.toString("UTF-8"));
            }
               
            socket.close();
            }
            //System.out.println(byteArrayOutputStream.toString("UTF-8"));
            
        }catch(IOException e){
            System.out.println(e.toString());
        }
        
    }
}