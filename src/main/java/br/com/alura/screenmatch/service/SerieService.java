package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.SerieDTO;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converterDados(repository.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converterDados(repository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converterDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(
                        s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getAvaliacao(), s.getPoster(), s.getGenero(),
                        s.getAtores(), s.getSinopse()
                )).toList();
    }

    public List<SerieDTO> obterLancamentos() {
        return converterDados(repository.findTop5ByOrderByEpisodiosDataLancamentoDesc());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                    s.getAvaliacao(), s.getPoster(), s.getGenero(),
                    s.getAtores(), s.getSinopse());
        }
        return null;
    }
}
