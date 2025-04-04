import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.Normalizer;

public class ReproductorAudio {

    public static void reproducirAudio(String frase, String idioma) {
        frase = normalizarTexto(frase);
        idioma = normalizarTexto(idioma);

        String nombreArchivo = frase + "-" + idioma + ".mp3";
        String rutaArchivo = "src" + File.separator + "audios" + File.separator + nombreArchivo;
        File archivoAudio = new File(rutaArchivo);

        if (archivoAudio.exists()) {
            try {
                Desktop.getDesktop().open(archivoAudio);
            } catch (IOException e) {
                mostrarMensaje("Error al reproducir el audio.", "Error");
            }
        } else {
            mostrarMensaje("No hay audio disponible en este idioma.", "Audio no encontrado");
        }
    }

    private static String normalizarTexto(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .replaceAll("[?]", "")
                .replaceAll(" ", "");
    }

    private static void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.WARNING_MESSAGE);
    }

    public static void crearInterfaz() {
        JFrame frame = new JFrame("Reproductor de Audio");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton botonEscuchar = new JButton("Escuchar");
        botonEscuchar.setIcon(new ImageIcon("src/iconos/audio.png"));                                                            

        botonEscuchar.addActionListener(e -> {
            reproducirAudio("Hola", "Ingles");
        });

        frame.add(botonEscuchar);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReproductorAudio::crearInterfaz);
    }
}
