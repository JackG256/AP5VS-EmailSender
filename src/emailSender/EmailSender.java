package emailSender;
import java.net.*;
import java.io.*;

public class EmailSender {
    private final Socket s;
    private final byte[] buffer = new byte[1000];
    private int sum;
    private final InputStream inp;
    private final OutputStream outp;
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws IOException {
        //initiate socket and save stream addresses
        s = new Socket(host, port);
        inp = s.getInputStream();
        outp = s.getOutputStream();

        //Print conection status code (220)
        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);

        //Authenticate using HELO request
        outp.write("HELO homePC-l-kollar\r\n".getBytes());
        outp.flush();

        //Print Authentication status code (250 + server adress)
        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
    }
    /*
     * sends email from an email address to an email address with some subject and text.
     * If the Socket throws an exception during sending, the exception is not handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        //Setup sender adress using MAIL FROM: request
        outp.write(("MAIL FROM: "+ from +"\r\n").getBytes());
        outp.flush();
        //Print server response (250 2.1.0 Ok)
        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);

        //Setup recipient adress using RCPT TO: request
        outp.write(("RCPT TO: "+ to +"\r\n").getBytes());
        outp.flush();
        //Print server response (250 2.1.5 Ok)
        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);

        //Initiate data input with DATA request
        outp.write("DATA\r\n".getBytes());
        outp.flush();
        //Print server response (354 + info how to end data input)
        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);

        //Input data and finish data block with <CRLF>.<CRLF>
        outp.write(("Subject: "+subject+"\r\n").getBytes());
        outp.write((text+"\r\n").getBytes());
        outp.write(("\r\n.\r\n").getBytes());
        outp.flush();

        //Print queue status code (250 2.0.0 Ok + Queue ID)
        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
    }

    /*
     * sends QUIT and closes the socket
     */

    //Method slightly modified to avoid potential IOException
    //Totally not forced by IDE
    public void close() throws IOException {
        //Send QUIT request
        outp.write("QUIT\r\n".getBytes());
        outp.flush();

        //Print server response to QUIT (221 2.0.0 Bye)
        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);

        //Close the connection socket
        s.close();
    }
}