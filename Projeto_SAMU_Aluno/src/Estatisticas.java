/**
 * Registra e calcula os indicadores de desempenho do atendimento:
 * tempo de resposta (do chamado até a chegada do veículo), chamados
 * perdidos, redirecionamentos e superlotações.
 * Também define as metas que a frota deve atingir.
 */
public class Estatisticas
{
    /** Meta: tempo médio de resposta geral, em etapas. */
    public static final double META_TEMPO_MEDIO = 14.0;
    /** Meta: tempo médio de resposta para casos GRAVES, em etapas. */
    public static final double META_TEMPO_GRAVE = 12.0;
    /** Meta: proporção máxima de chamados perdidos. */
    public static final double META_TAXA_PERDA = 0.02;

    private int chamadosLeve;
    private int chamadosModerado;
    private int chamadosGrave;
    private int perdidos;
    private int respostasLeve;
    private int respostasModerado;
    private int respostasGrave;
    private int somaTempoLeve;
    private int somaTempoModerado;
    private int somaTempoGrave;
    private int atendimentosConcluidos;
    private int redirecionamentos;
    private int superlotacoes;

    public Estatisticas()
    {
        chamadosLeve = 0;
        chamadosModerado = 0;
        chamadosGrave = 0;
        perdidos = 0;
        respostasLeve = 0;
        respostasModerado = 0;
        respostasGrave = 0;
        somaTempoLeve = 0;
        somaTempoModerado = 0;
        somaTempoGrave = 0;
        atendimentosConcluidos = 0;
        redirecionamentos = 0;
        superlotacoes = 0;
    }

    public void registraChamado(int gravidade)
    {
        if(gravidade == Gravidade.LEVE) {
            chamadosLeve++;
        }
        else if(gravidade == Gravidade.MODERADO) {
            chamadosModerado++;
        }
        else {
            chamadosGrave++;
        }
    }

    public void registraChamadoPerdido(int gravidade)
    {
        perdidos++;
    }

    /**
     * @param tempo Etapas entre o chamado e a chegada do veículo.
     */
    public void registraTempoResposta(int gravidade, int tempo)
    {
        if(gravidade == Gravidade.LEVE) {
            respostasLeve++;
            somaTempoLeve += tempo;
        }
        else if(gravidade == Gravidade.MODERADO) {
            respostasModerado++;
            somaTempoModerado += tempo;
        }
        else {
            respostasGrave++;
            somaTempoGrave += tempo;
        }
    }

    public void registraAtendimentoConcluido()
    {
        atendimentosConcluidos++;
    }

    public void registraRedirecionamento()
    {
        redirecionamentos++;
    }

    public void registraSuperlotacao()
    {
        superlotacoes++;
    }

    /**
     * @return O tempo médio de resposta considerando todas as gravidades
     *         (0 se nenhum paciente foi alcançado ainda).
     */
    public double getTempoMedioResposta()
    {
        int soma = somaTempoLeve + somaTempoModerado + somaTempoGrave;
        int quantidade = getTotalRespostas();
        if(quantidade == 0) {
            return 0;
        }
        return (double) soma / quantidade;
    }

    /**
     * @return O tempo médio de resposta para uma gravidade específica.
     */
    public double getTempoMedioResposta(int gravidade)
    {
        if(gravidade == Gravidade.LEVE && respostasLeve > 0) {
            return (double) somaTempoLeve / respostasLeve;
        }
        if(gravidade == Gravidade.MODERADO && respostasModerado > 0) {
            return (double) somaTempoModerado / respostasModerado;
        }
        if(gravidade == Gravidade.GRAVE && respostasGrave > 0) {
            return (double) somaTempoGrave / respostasGrave;
        }
        return 0;
    }

    /**
     * @return A proporção de chamados perdidos (entre 0 e 1).
     */
    public double getTaxaPerda()
    {
        int total = getTotalChamados();
        if(total == 0) {
            return 0;
        }
        return (double) perdidos / total;
    }

    /**
     * @return true se TODAS as metas de desempenho foram atingidas.
     */
    public boolean metasAtingidas()
    {
        return getTempoMedioResposta() <= META_TEMPO_MEDIO
            && getTempoMedioResposta(Gravidade.GRAVE) <= META_TEMPO_GRAVE
            && getTaxaPerda() <= META_TAXA_PERDA;
    }

    public int getTotalChamados()
    {
        return chamadosLeve + chamadosModerado + chamadosGrave;
    }

    public int getChamados(int gravidade)
    {
        if(gravidade == Gravidade.LEVE) {
            return chamadosLeve;
        }
        if(gravidade == Gravidade.MODERADO) {
            return chamadosModerado;
        }
        return chamadosGrave;
    }

    public int getTotalPerdidos()
    {
        return perdidos;
    }

    public int getTotalRespostas()
    {
        return respostasLeve + respostasModerado + respostasGrave;
    }

    public int getAtendimentosConcluidos()
    {
        return atendimentosConcluidos;
    }

    public int getRedirecionamentos()
    {
        return redirecionamentos;
    }

    public int getSuperlotacoes()
    {
        return superlotacoes;
    }
}
