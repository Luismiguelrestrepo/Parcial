import javax.swing.*;
import java.awt.*;
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
        frame.setSize(500, 240);

        // Panel principal con margen
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel para frase
        JPanel panelFrase = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFrase.add(new JLabel("Frase:"));
        comboFrases = new JComboBox<>(GestorJSON.getFrases().toArray(new String[0]));
        panelFrase.add(comboFrases);
        panelPrincipal.add(panelFrase);

        // Panel para idioma
        JPanel panelIdioma = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelIdioma.add(new JLabel("Idioma:"));
        comboIdiomas = new JComboBox<>();
        panelIdioma.add(comboIdiomas);
        panelPrincipal.add(panelIdioma);

        // Botón Traducir
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnTraducir = new JButton("Traducir");
        panelBoton.add(btnTraducir);
        panelPrincipal.add(panelBoton);

        // Panel de traducción (más arriba y más compacto)
        JPanel panelTraduccion = new JPanel(new BorderLayout());
        panelTraduccion.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0)); // espacio superior pequeño

        txtTraduccion = new JTextArea(2, 30); // Menor altura
        txtTraduccion.setEditable(false);
        txtTraduccion.setLineWrap(true);
        txtTraduccion.setWrapStyleWord(true);
        txtTraduccion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtTraduccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtTraduccion.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // margenes internos
        txtTraduccion.setOpaque(true);
        txtTraduccion.setBackground(Color.WHITE);

        // Simulación de centrado de texto (rudimentario)
        txtTraduccion.setText(" "); // Espacio inicial para dejar margen
        txtTraduccion.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(txtTraduccion);
        scrollPane.setPreferredSize(new Dimension(350, 60)); // Ajusta tamaño exacto
        panelTraduccion.add(scrollPane, BorderLayout.CENTER);

        // Botón escuchar (más arriba y ajustado)
        btnEscuchar = new JButton();
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource("/recursos/sonido.png"));
            Image img = icono.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH); // reducir tamaño
            btnEscuchar.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.out.println("No se pudo cargar el icono de sonido.");
        }
        btnEscuchar.setPreferredSize(new Dimension(40, 40));
        panelTraduccion.add(btnEscuchar, BorderLayout.EAST);

        panelPrincipal.add(panelTraduccion);

        frame.add(panelPrincipal);
        frame.setVisible(true);

        // Listeners
        comboFrases.addActionListener(e -> actualizarIdiomas());
        btnTraducir.addActionListener(e -> mostrarTraduccion());
        btnEscuchar.addActionListener(e -> reproducirAudio());

        actualizarIdiomas();
    }

    private void actualizarIdiomas() {
        comboIdiomas.removeAllItems();
        String frase = (String) comboFrases.getSelectedItem();
        if (frase != null) {
            Map<String, String> traducciones = GestorJSON.getTraducciones(frase);
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
