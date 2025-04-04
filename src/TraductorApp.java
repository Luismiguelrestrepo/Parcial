import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class TraductorApp {
    private JFrame frame;
    private JComboBox<String> comboFrases, comboIdiomas;
    private JTextArea txtTraduccion;
    private JButton btnTraducir, btnEscuchar;

    public TraductorApp() {
        GestorJSON.cargarFrases();

        frame = new JFrame("Traductor de Frases");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

      
        JLabel lblFrase = new JLabel("Frase:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(lblFrase, gbc);

        
        comboFrases = new JComboBox<>(GestorJSON.getFrases().toArray(new String[0]));
        comboFrases.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(comboFrases, gbc);

      
        JLabel lblIdioma = new JLabel("Idioma:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(lblIdioma, gbc);

 
        comboIdiomas = new JComboBox<>();
        comboIdiomas.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(comboIdiomas, gbc);

     
        btnTraducir = new JButton("Traducir");
        btnTraducir.setPreferredSize(new Dimension(100, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; 
        frame.add(btnTraducir, gbc);

        
        JPanel panelTraduccion = new JPanel(new BorderLayout());

        
        txtTraduccion = new JTextArea(3, 25);
        txtTraduccion.setEditable(false);
        txtTraduccion.setLineWrap(true);
        txtTraduccion.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtTraduccion);
        panelTraduccion.add(scrollPane, BorderLayout.CENTER);

        
        btnEscuchar = new JButton();
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/sonido.png"));
            btnEscuchar.setIcon(icono);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen del botón de sonido.");
            e.printStackTrace();
        }
        btnEscuchar.setPreferredSize(new Dimension(50, 50));
        panelTraduccion.add(btnEscuchar, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(panelTraduccion, gbc);

        
        comboFrases.addActionListener(e -> actualizarIdiomas());
        btnTraducir.addActionListener(e -> mostrarTraduccion());
        btnEscuchar.addActionListener(e -> reproducirAudio());

        actualizarIdiomas();
        frame.setVisible(true);
    }

    private void actualizarIdiomas() {
        comboIdiomas.removeAllItems();
        String fraseSeleccionada = (String) comboFrases.getSelectedItem();
        if (fraseSeleccionada != null) {
            Map<String, String> traducciones = GestorJSON.getTraducciones(fraseSeleccionada);
            for (String idioma : traducciones.keySet()) {
                comboIdiomas.addItem(idioma);
            }
        }
    }

    private void mostrarTraduccion() {
        String frase = (String) comboFrases.getSelectedItem();
        String idioma = (String) comboIdiomas.getSelectedItem();
        if (frase != null && idioma != null) {
            txtTraduccion.setText(GestorJSON.getTraducciones(frase).get(idioma));
        }
    }

    private void reproducirAudio() {
        String frase = (String) comboFrases.getSelectedItem();
        String idioma = (String) comboIdiomas.getSelectedItem();
        if (frase != null && idioma != null) {
            ReproductorAudio.reproducirAudio(frase, idioma);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TraductorApp::new);
    }
}
