package sample;

import javafx.scene.control.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final static String URL = "https://www.vagalume.com.br";
    public Button AutoWriteInfo;

    public CheckBox AutoWrite;

    public TextField InputArtista;
    public TextField InputMusic;

    public Button Confirmar;

    public TextArea musicaletra;

    public static List<String> getSongLyrics(String Artista, String Musica) throws IOException {
        List<String> lyrics = new ArrayList<>();

        Document doc = Jsoup.connect(URL + "/"
                + Artista.replace(" ", "-").toLowerCase() + "/"
                + Musica.replace(" ", "-").toLowerCase()).get();

        String title = doc.title();
        System.out.println(title);
        Element p = doc.select("#lyrics").get(0);
        p.childNodes().stream().filter((e) -> (e instanceof TextNode)).forEachOrdered((e) -> lyrics.add(((TextNode) e).getWholeText()));
        return lyrics;
    }



    public void ProcurarLetra() {
        List<String> LetraMusica;
        try {
            LetraMusica = Controller.getSongLyrics(InputArtista.getText(), InputMusic.getText());
            String LetraFormat = LetraMusica.toString().replace(",", "\n").replace("[", "").replace("]", "");
            musicaletra.setText(LetraFormat);

            if (AutoWrite.isSelected()) {
                int contar = musicaletra.getText().length();
                System.out.println(LetraMusica);
                for (int i = 0; i < contar; i++) {
                    String frase = LetraMusica.get(i);
                    System.out.println(frase);
                }
            }

        } catch (IOException ioException) {
            musicaletra.setText(String.format("Parece que algo deu errado\n" +
                    "Certifique-se Que o \n" +
                    "nome artista '%s' e \n" +
                    "nome da musica '%s' esteja CORRETO", InputArtista.getText(), InputMusic.getText()));
        }
    }

    public void AutoWriteInfoAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, String.format("Ativando a funcao '%s'" +
                " Voce Ira mandar automaticamente a letra da musica" +
                " para algum lugar com Caixa de texto, como "+
                " (Discord, WebWhatsapp Entre outros)", AutoWrite.getText()), ButtonType.OK);
        alert.showAndWait();

        alert.getResult();//do stuff
    }
}