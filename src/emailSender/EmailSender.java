package emailSender;
import java.net.*;
import java.io.*;

public class EmailSender {
    Socket s;
    byte[] buffer = new byte[1000];
    int sum;
    InputStream inp;
    OutputStream outp;
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        s = new Socket(host, port);
    }
    /*
     * sends email from an email address to an email address with some subject and text.
     * If the Socket throws an exception during sending, the exception is not handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        inp = s.getInputStream();
        outp = s.getOutputStream();

        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
        outp.write("HELO homePC-l-kollar\r\n".getBytes());
        outp.flush();

        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
        outp.write(("MAIL FROM: "+ from +"\r\n").getBytes());
        outp.flush();

        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
        outp.write(("RCPT TO: "+ to +"\r\n").getBytes());
        outp.flush();

        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
        outp.write("DATA\r\n".getBytes());
        outp.flush();

        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
        outp.write(("Subject: "+subject+"\r\n").getBytes());
        outp.write((text+"\r\n").getBytes());
        outp.write(("\r\n.\r\n").getBytes());
        outp.flush();
    }

    /*
     * sends QUIT and closes the socket
     */

    //Method slightly modified to avoid potential IOException
    //Totally not forced by IDE
    public void close() throws IOException {
        inp = s.getInputStream();
        outp = s.getOutputStream();

        sum = inp.read(buffer);
        System.out.write(buffer, 0, sum);
        outp.write("QUIT\r\n".getBytes());
        outp.flush();

        s.close();
    }
}