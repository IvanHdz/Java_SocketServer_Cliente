
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

public class ClienteSuma extends Frame {

    private Label l;
    private Button b;
    private TextField t;
    private final int port;
    private final String host;
    private final String nombre;
    private int x;
    private int y;
    private int r;
    private int contador;
    //private BufferedReader reader;
    private BufferedWriter writer;

    public ClienteSuma(String servidor, int puerto, String nombre) {
        super("¿Qué tan rápido puedes multiplicar?");
        setSize(300, 300);
        setBackground(Color.cyan);
        setLayout(null);
        port = puerto;
        host = servidor;
        this.nombre = nombre;
        addComponentes();
        addEventos();
        setVisible(true);
    }

    private void addComponentes() {
        l = new Label();
        t = new TextField(30);
        b = new Button("Aceptar");
        x = (int) (Math.random() * 20);
        y = (int) (Math.random() * 20);
        r = x * y;
        l.setText("¿Cuanto es " + x + " por " + y + "?");
        l.setBounds(100, 50, 200, 20);
        t.setBounds(100, 100, 100, 20);
        b.setBounds(100, 150, 60, 20);
        add(l);
        add(t);
        add(b);
    }

    private void addEventos() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (r == Integer.parseInt(t.getText())) {
                        contador++;
                        writer.write(contador + "\n");
                        writer.flush();
                    }
                } catch (IOException e1) {
                } catch (NumberFormatException e1) {
                }
                System.out.println(contador);
                x = (int) (Math.random() * 20);
                y = (int) (Math.random() * 20);
                r = x * y;
                l.setText("¿Cuando es " + x + " por " + y + "?");
                t.setText("");
            }
        });
    }

    public void iniciarConexion() {
        try {
            Socket cliente = new Socket(host, port);
            //InputStream flujoBE = cliente.getInputStream();
            //Reader flujoCE = new InputStreamReader(flujoBE);
            //reader = new BufferedReader(flujoCE);

            //OutputStream flujoBS = cliente.getOutputStream();
            Writer flujoCS = new OutputStreamWriter(cliente.getOutputStream());
            writer = new BufferedWriter(flujoCS);

            writer.write(nombre + "\n");
            writer.flush();

        } catch (IOException ioe1) {
            System.err.println("Fallo la conexion");
            System.exit(0);
        }
    }
}
