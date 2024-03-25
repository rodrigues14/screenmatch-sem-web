package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConverteDados converteDados = new ConverteDados();
		ConsumoApi consumoApi = new ConsumoApi();
		String json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=ce10fb74");
		DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
