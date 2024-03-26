package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodeo;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ce10fb74";

    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca: ");
        var nome = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO
                + nome.replace(" ", "+") + API_KEY);

        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i <= dados.totalTemporadas(); i++) {
			json = consumo.obterDados(ENDERECO
                    + nome.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++) {
//            List<DadosEpisodeo> episodeosTemporada = temporadas.get(i).episodeos();
//            for (int j = 0; j < episodeosTemporada.size(); j++) {
//                System.out.println(episodeosTemporada.get(j).titulo());
//            }
//        }

        temporadas.forEach(t -> t.episodeos().forEach(e -> System.out.println(e.titulo())));
    }
}
