package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodeo;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodeo;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

        for (int i = 0; i < dados.totalTemporadas(); i++) {
            List<DadosEpisodeo> episodeosTemporada = temporadas.get(i).episodeos();
            for (int j = 0; j < episodeosTemporada.size(); j++) {
                System.out.println(episodeosTemporada.get(j).titulo());
            }
        }

        temporadas.forEach(t -> t.episodeos().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodeo> dadosEpisodeos = temporadas.stream()
                .flatMap(t -> t.episodeos().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episodeos: ");
        dadosEpisodeos.stream()
                .filter(e -> !e.avaliacao().equals("N/A"))
                .sorted(Comparator.comparing(DadosEpisodeo::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodeo> episodeos = temporadas.stream()
                .flatMap(t -> t.episodeos()
                        .stream()
                        .map(d -> new Episodeo(t.numero(), d))
                ).collect(Collectors.toList());

        episodeos.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódeos? ");
        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodeos.stream()
                .filter(e -> e.getDataLancamento() != null
                        && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                        " Episódeo: " + e.getTitulo() +
                        " Data lançamento: " + e.getDataLancamento().format(formatador)
                ));

    }
}
