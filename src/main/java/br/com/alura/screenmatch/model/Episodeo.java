package br.com.alura.screenmatch.model;

import java.time.DateTimeException;
import java.time.LocalDate;

public class Episodeo {
    private Integer temporada;
    private String titulo;
    private Integer numeroEpisodeo;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodeo(Integer numeroTemporada, DadosEpisodeo dadosEpisodeo) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodeo.titulo();
        this.numeroEpisodeo = dadosEpisodeo.numero();
        try {
            this.avaliacao = Double.valueOf(dadosEpisodeo.avaliacao());
        } catch (NumberFormatException e) {
            this.avaliacao = 0.0;
        }
        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodeo.dataLancamento());
        } catch (DateTimeException e) {
            this.dataLancamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodeo() {
        return numeroEpisodeo;
    }

    public void setNumeroEpisodeo(Integer numeroEpisodeo) {
        this.numeroEpisodeo = numeroEpisodeo;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEpisodeo=" + numeroEpisodeo +
                ", avaliacao=" + avaliacao +
                ", dataLancamento=" + dataLancamento;
    }
}
